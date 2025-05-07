package com.se.expense.utils;

import com.se.expense.Repository.UserRepository;
import com.se.expense.model.UserModel;

public class UserUtils {

  public static UserModel getUserByID(UserRepository userRepository, Long userId) throws RuntimeException {
    UserModel user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return user;
  }
}
