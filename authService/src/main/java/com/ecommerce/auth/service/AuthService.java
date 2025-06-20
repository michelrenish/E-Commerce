package com.ecommerce.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.auth.dto.AuthRequest;
import com.ecommerce.auth.dto.RegisterRequest;
import com.ecommerce.auth.model.User;
import com.ecommerce.auth.repository.UserRepository;
import com.ecommerce.auth.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	  private final UserRepository userRepo;
	    private final PasswordEncoder passwordEncoder;
	    private final JwtUtil jwtUtil;

	    public String register(RegisterRequest request) {
	        User user = new User();
	        user.setUsername(request.getUsername());
	        user.setEmail(request.getEmail());
	        user.setPassword(passwordEncoder.encode(request.getPassword()));
	        userRepo.save(user);
	        return "User registered successfully!";
	    }

	    public String login(AuthRequest request) {
	        User user = userRepo.findByUsername(request.getUsername())
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	            return jwtUtil.generateToken(user.getUsername());
	        } else {
	            throw new RuntimeException("Invalid credentials");
	        }
	    }
}
