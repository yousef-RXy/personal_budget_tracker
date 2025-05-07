package com.se.income.utils;

import com.se.income.Repository.UserRepository;
import com.se.income.model.UserModel;

public class UserUtils {

  public static UserModel getUserByID(UserRepository userRepository, Long userId) throws RuntimeException {
    UserModel user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return user;
  }
}
