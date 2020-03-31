package ru.itis.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.SignInForm;
import ru.itis.dto.TokenDto;
import ru.itis.services.interfaces.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignInController {
    private final SignInService signInService;

    public SignInController(SignInService signInService) {
        this.signInService = signInService;
    }

    @GetMapping("/signIn")
    public String getPage(Authentication authentication) {
        System.out.println(authentication);
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication1);
        if (authentication != null) {
            return "redirect:/profile";
        }
        return "signIn";
    }

    @PostMapping("/signIn")
    public String signIn(SignInForm signInForm, HttpServletResponse servletResponse){
        TokenDto tokenDto = signInService.signIn(signInForm);
        if (tokenDto != null){
            Cookie tokenCookie = new Cookie("Authorization", "Bearer_" + tokenDto.getToken());
            servletResponse.addCookie(tokenCookie);
            return "redirect:/";
        }
        else {
            return null; //todo
        }
    }
}
