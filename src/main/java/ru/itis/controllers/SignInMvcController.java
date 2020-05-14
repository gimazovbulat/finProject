package ru.itis.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.SignInForm;
import ru.itis.restSecurity.CurrentUser;
import ru.itis.services.interfaces.SignInDbService;

@Controller
@Profile("mvc")
public class SignInMvcController {
    private final SignInDbService signInService;

    public SignInMvcController(SignInDbService signInService) {
        this.signInService = signInService;
    }

    @GetMapping("/signIn")
    public String getPage(@CurrentUser UserDetails userDetails) {
        if (userDetails != null) {
            return "redirect:/profile";
        }
        return "signIn";
    }

    @PostMapping("/signIn")
    public String signIn(SignInForm signInForm, Model model) {
        boolean isSignedIn = signInService.signIn(signInForm);
        if (isSignedIn) {
            return "redirect:/profile";
        } else {
            model.addAttribute("err", "wrong email or password");
            return "signIn";
        }
    }
}
