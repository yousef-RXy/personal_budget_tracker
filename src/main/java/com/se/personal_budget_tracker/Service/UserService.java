package com.se.personal_budget_tracker.Service;

import org.springframework.stereotype.Service;

import com.se.personal_budget_tracker.Repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
