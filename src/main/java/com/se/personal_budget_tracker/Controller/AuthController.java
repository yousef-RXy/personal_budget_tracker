package com.se.personal_budget_tracker.Controller;

// Ensure the correct package path for the User class
import com.se.personal_budget_tracker.model.UserModel;
import com.se.personal_budget_tracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserModel()); 
        return "register";  
    }

    @PostMapping("/register")
    public String registerUser(UserModel user) {
        userService.registerUser(user);  
        return "redirect:/login";  
    }
}
