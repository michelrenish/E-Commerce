package com.productService.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationTime;
    
    public JwtUtil(@Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationTime) {
 this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
 this.expirationTime = expirationTime;
}

    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token)
            .getSubject();
    }

    public boolean validateToken(String token) {
        try {
        	extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
