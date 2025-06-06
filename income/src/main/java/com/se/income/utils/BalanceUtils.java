package com.se.income.utils;

import com.se.income.Repository.UserRepository;
import com.se.income.model.UserModel;

public class BalanceUtils {
  public static boolean increaseBalance(UserRepository userRepository, long userID, long amount) {
    try {

      UserModel user = UserUtils.getUserByID(userRepository, userID);
      long balance = user.getBalance();

      user.setBalance(balance + amount);
      userRepository.save(user);

      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }

  public static boolean decreaseBalance(UserRepository userRepository, long userID, long amount) {
    try {
      UserModel user = UserUtils.getUserByID(userRepository, userID);
      long balance = user.getBalance();

      if (balance < amount)
        return false;

      user.setBalance(balance - amount);
      userRepository.save(user);

      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }

  public static boolean decreaseBalance(UserRepository userRepository, long userID, long newAmount, long oldAmount) {
    return decreaseBalance(userRepository, userID, newAmount - oldAmount);
  }

}
