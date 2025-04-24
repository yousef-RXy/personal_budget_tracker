package com.se.personal_budget_tracker.utils;

import com.se.personal_budget_tracker.Repository.UserRepository;
import com.se.personal_budget_tracker.model.UserModel;

public class UserUtils {

  public static UserModel getUserByID(UserRepository userRepository, Long userId) throws RuntimeException {
    UserModel user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return user;
  }
}
