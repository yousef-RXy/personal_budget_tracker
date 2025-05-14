package com.se.auth.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.se.auth.Service.UserService;
import com.se.auth.aspects.LogExecutionTime;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/balance")
    @LogExecutionTime
    public Optional<Double> getBalance(@RequestHeader("User-Id") Long userId) {
        return userService.getBalance(userId);
    }

}