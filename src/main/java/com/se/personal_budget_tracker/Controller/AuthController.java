package com.se.personal_budget_tracker.Controller;

// Ensure the correct package path for the User class
import com.se.personal_budget_tracker.model.UserModel;
import com.se.personal_budget_tracker.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public boolean login(UserModel user) {
    return true;
  }

  @PostMapping("/register")
  public boolean registerUser(UserModel user) {
    userService.registerUser(user);
    return true;
  }
}