package com.bestprofi.controllers;

import com.bestprofi.models.User;
import com.bestprofi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AcountController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginForm() {
        return "loginForm";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/createUser")
    public String createUser(@Valid User user, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "registerForm";
        }

        if (!user.getPassword().equals(user.getRepassword())) {
            model.addAttribute("message", "Passwords not equal");
            return "registerForm";
        }

        userRepository.save(user);
        return "redirect:/login";
    }
}
