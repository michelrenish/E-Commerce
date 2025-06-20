package com.ecommerce.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auth.dto.AuthRequest;
import com.ecommerce.auth.dto.RegisterRequest;
import com.ecommerce.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

		private final AuthService authService;
		/*register api*/
		
		@PostMapping("/register")
	    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
	        return ResponseEntity.ok(authService.register(request));
	    }

	    @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
	        String token = authService.login(request);
	        return ResponseEntity.ok(token);
	    }
}
