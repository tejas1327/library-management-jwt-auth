package com.mobicool.controller;
import com.mobicool.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobicool.dto.RegisterRequestDto;
import com.mobicool.entity.User;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AuthenticationService authenticationService;

    AdminController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registeradmin")
	public ResponseEntity<User> registerAdmin(@RequestBody RegisterRequestDto registerRequestDto){
		
		return new ResponseEntity<User>(authenticationService.registerAdminUser(registerRequestDto),HttpStatus.CREATED);
		
	}
}
