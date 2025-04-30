package com.se.personal_budget_tracker.Service;

import com.se.personal_budget_tracker.model.UserModel;
import com.se.personal_budget_tracker.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel registerUser(UserModel user) {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }

            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new RuntimeException("Username already exists");
            }
            
            if (user.getPassword().length() < 8) {
                throw new RuntimeException("Password must be at least 8 characters long");
            }

            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPasswordHash(encodedPassword);

            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error during user registration", e);
            throw new RuntimeException("Error during registration", e);
        }
    }
}
