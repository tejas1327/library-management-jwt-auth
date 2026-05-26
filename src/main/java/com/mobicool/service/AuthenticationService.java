
package com.mobicool.service;

import com.mobicool.repository.UserRepo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobicool.JWT.JwtService;
import com.mobicool.dto.LoginRequestDto;
import com.mobicool.dto.LoginResponseDto;
import com.mobicool.dto.RegisterRequestDto;
import com.mobicool.entity.User;

@Service
public class AuthenticationService {

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthenticationService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;

	}

	public User registerUser(RegisterRequestDto registerRequestDto) {
		if (userRepo.findByUsername(registerRequestDto.getUsername()).isPresent()) {
			throw new RuntimeException("User Already Registered");

		}
		Set<String> roles = new HashSet<String>();
		roles.add("ROLE_USER");
		User user = new User();
		user.setEmail(registerRequestDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
		user.setUsername(registerRequestDto.getUsername());
		user.setRoles(roles);

		return userRepo.save(user);
	}

	public User registerAdminUser(RegisterRequestDto registerRequestDto) {

		if (userRepo.findByUsername(registerRequestDto.getUsername()).isPresent()) {
			throw new RuntimeException("User Already Registered");

		}

		Set<String> roles = new HashSet<String>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		User user = new User();
		user.setEmail(registerRequestDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
		user.setUsername(registerRequestDto.getUsername());
		user.setRoles(roles);

		return userRepo.save(user);

	}

	public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));

		User user = userRepo.findByUsername(loginRequestDto.getUsername())
				.orElseThrow(() -> new RuntimeException("Signup Requires"));

		String token = jwtService.generateToken(user);

		return LoginResponseDto.builder().token(token).username(user.getUsername()).roles(user.getRoles()).build();
	}

}
