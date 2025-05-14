package com.se.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.se.auth.Service.UserService;
import com.se.auth.aspects.LogExecutionTime;
import com.se.auth.dto.AuthResponse;
import com.se.auth.dto.LoginRequest;
import com.se.auth.dto.RefreshTokenRequest;
import com.se.auth.dto.RegisterRequest;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @LogExecutionTime
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @LogExecutionTime
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            String message = userService.register(registerRequest);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    @LogExecutionTime
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            AuthResponse response = userService.refreshToken(refreshTokenRequest.getRefreshToken());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}