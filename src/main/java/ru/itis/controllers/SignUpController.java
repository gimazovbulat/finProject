package ru.itis.controllers;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.SignUpForm;
import ru.itis.security.CurrentUser;
import ru.itis.services.interfaces.SignUpService;

@Controller
public class SignUpController {
    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @GetMapping("/signUp")
    public String getPage(@CurrentUser UserDetails userDetails) {
        System.out.println(userDetails);
        return "signUp";
    }

    @PostMapping("/signUp")
    public void handleRequest(SignUpForm signUpForm, Model model) {
        signUpService.signUp(signUpForm);
    }
}
