package com.mobicool.controller;

import com.mobicool.service.AuthenticationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobicool.dto.LoginRequestDto;
import com.mobicool.dto.LoginResponseDto;
import com.mobicool.dto.RegisterRequestDto;
import com.mobicool.entity.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationService authenticationService;

	AuthController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {

		return new ResponseEntity<User>(authenticationService.registerUser(registerRequestDto), HttpStatus.OK);

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
		return new ResponseEntity<LoginResponseDto>(authenticationService.loginUser(loginRequestDto), HttpStatus.OK);

	}
}
