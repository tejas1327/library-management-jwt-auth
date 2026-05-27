package com.mobicool.JWT;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mobicool.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${JwtService.secretKey}")
	private String secretKey;

	@Value("${JwtService.expiration}")
	private Long expiration;

	// Generate Token
	public String generateToken(User user) {

		Map<String, Object> claims = new HashMap<>();

		claims.put("roles", user.getRoles());

		return createToken(claims, user.getUsername());
	}

	// Create Token
	private String createToken(Map<String, Object> claims, String username) {

		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	// Extract Username
	public String extractUserName(String jwtToken) {

		return extractClaim(jwtToken, Claims::getSubject);
	}

	// Extract Expiration
	public Date extractExpiration(String jwtToken) {

		return extractClaim(jwtToken, Claims::getExpiration);
	}

	// Generic Claim Extractor
	public <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {

		final Claims claims = extractAllClaims(jwtToken);

		return claimResolver.apply(claims);
	}

	// Extract All Claims
	private Claims extractAllClaims(String jwtToken) {

		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwtToken).getBody();
	}

	// Validate Token
	public boolean isTokenValid(String jwtToken, User user) {

		final String username = extractUserName(jwtToken);

		return (username.equals(user.getUsername()) && !isTokenExpired(jwtToken));
	}

	// Check Expiration
	private boolean isTokenExpired(String jwtToken) {

		return extractExpiration(jwtToken).before(new Date());
	}

	// Generate Signing Key
	private Key getSignInKey() {

		byte[] keyBytes = Decoders.BASE64.decode(secretKey);

		return Keys.hmacShaKeyFor(keyBytes);
	}
}