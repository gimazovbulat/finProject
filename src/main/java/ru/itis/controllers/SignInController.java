package ru.itis.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.SignInForm;
import ru.itis.dto.TokenDto;
import ru.itis.restSecurity.CurrentUser;
import ru.itis.services.interfaces.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@Profile("rest")
public class SignInController {
    private final SignInService signInService;

    public SignInController(SignInService signInService) {
        this.signInService = signInService;
    }

   /* @PostMapping("/signIn")
    public String signIn(SignInForm signInForm, HttpServletResponse servletResponse) {
        TokenDto tokenDto = signInService.signIn(signInForm);
        if (tokenDto != null) {
            Cookie tokenCookie = new Cookie("Authentication", "Bearer_" + tokenDto.getToken());
            servletResponse.addCookie(tokenCookie);
            return "redirect:/";
        } else {
            return null; //todo
        }
    }*/
}
