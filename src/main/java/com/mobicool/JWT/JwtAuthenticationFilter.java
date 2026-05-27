package com.mobicool.JWT;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mobicool.entity.User;
import com.mobicool.repository.UserRepo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserRepo userRepo;

	public JwtAuthenticationFilter(JwtService jwtService, UserRepo userRepo) {
		super();
		this.jwtService = jwtService;
		this.userRepo = userRepo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");

		// check if Authorization is present or startsWith "Bearer "
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;

		}

		// Extract JWT Token From Header

		final String jwtToken = authHeader.substring(7);
		final String userName = jwtService.extractUserName(jwtToken);
		// check if we have a username and no authentication exist
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// get Details from Database

			User user = userRepo.findByUsername(userName).orElseThrow(() -> new RuntimeException("username not found"));

//			validate the token
			if (jwtService.isTokenValid(jwtToken, user)) {
//				Create Authentication with User roles

				List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,
						authorities);

				// Set Authentication Details
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// update Security Context

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			}
		}
		filterChain.doFilter(request, response);

	}

}
