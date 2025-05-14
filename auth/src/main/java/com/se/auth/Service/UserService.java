package com.se.auth.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.se.auth.Repository.UserRepository;
import com.se.auth.configuration.JwtTokenProvider;
import com.se.auth.dto.AuthResponse;
import com.se.auth.dto.LoginRequest;
import com.se.auth.dto.RegisterRequest;
import com.se.auth.model.UserModel;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;
  private final UserDetailService userDetailService;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      JwtTokenProvider tokenProvider, UserDetailService userDetailService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.userDetailService = userDetailService;
  }

  public UserModel registerUser(UserModel user) {
    try {
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPasswordHash(encodedPassword);
      return userRepository.save(user);
    } catch (Exception e) {
      logger.error("Error during user registration", e);
      throw new RuntimeException("Error during registration", e);
    }
  }

  public AuthResponse login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(), request.getPassword()));

    String accessToken = tokenProvider.generateAccessToken(authentication);
    String refreshToken = tokenProvider.generateRefreshToken(authentication);
    long expiresIn = tokenProvider.getAccessTokenExpirationInSeconds();

    return new AuthResponse(accessToken, refreshToken, expiresIn);
  }

  public String register(RegisterRequest registerRequest) {
    if (userDetailService.existsByUsername(registerRequest.getUsername())) {
      throw new RuntimeException("Username is already taken!");
    }

    if (userDetailService.existsByEmail(registerRequest.getEmail())) {
      throw new RuntimeException("Email is already in use!");
    }

    if (registerRequest.getPassword().length() < 8) {
      throw new RuntimeException("Password must be at least 8 characters long");
    }

    UserModel user = new UserModel();
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(registerRequest.getPassword());
    registerUser(user);

    return "User registered successfully";
  }

  public AuthResponse refreshToken(String refreshToken) {
    if (!tokenProvider.validateToken(refreshToken)) {
      throw new RuntimeException("Invalid refresh token");
    }

    Long userId = tokenProvider.getUserIdFromToken(refreshToken);
    UserDetails userDetails = userDetailService.loadUserById(userId);

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());

    String newAccessToken = tokenProvider.generateAccessToken(authentication);
    return new AuthResponse(newAccessToken, refreshToken);
  }

  public Optional<Double> getBalance(Long userId) {
    return userRepository.findBalanceByUserId(userId);
  }

}
