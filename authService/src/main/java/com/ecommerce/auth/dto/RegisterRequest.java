package com.ecommerce.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
	private String username;
    private String password;
    private String email;
}
