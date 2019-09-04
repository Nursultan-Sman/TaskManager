package com.bestprofi.controllers;

import com.bestprofi.models.Role;
import com.bestprofi.models.User;
import com.bestprofi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class AcountController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginForm() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            return "redirect:/";
        }
        return "loginForm";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/register")
    public String createUser(@Valid User user, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "registerForm";
        }

        if (!user.getPassword().equals(user.getRepassword())) {
            model.addAttribute("message", "Passwords not equal");
            return "registerForm";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
