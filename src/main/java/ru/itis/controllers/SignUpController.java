package ru.itis.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.SignUpForm;
import ru.itis.dto.StatusCode;
import ru.itis.services.interfaces.SignUpService;

import javax.validation.Valid;

@Controller
@Profile("mvc")
public class SignUpController {
    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @GetMapping("/signUp")
    public String getPage(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signUp";
    }

    @PostMapping("/signUp")
    public String handleRequest(@Valid SignUpForm signUpForm, BindingResult bindingResult, Model model) {
        boolean bindingResErr = bindingResult.hasErrors();
        if (!bindingResErr) {
            StatusCode statusCode = signUpService.signUp(signUpForm);
            if (!statusCode.getStatus().equals(StatusCode.Status.OK)) {
                model.addAttribute("confirmPasswordError", statusCode.getDescr());
            }
        } else {
            model.addAttribute("signUpForm", signUpForm);
        }
        return "signUp";

    }
}
