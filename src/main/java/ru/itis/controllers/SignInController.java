package ru.itis.controllers;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.SignInForm;
import ru.itis.dto.TokenDto;
import ru.itis.security.CurrentUser;
import ru.itis.services.interfaces.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
//@RestController
public class SignInController {
    private final SignInService signInService;

    public SignInController(SignInService signInService) {
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
    public String signIn(SignInForm signInForm, HttpServletResponse servletResponse) {
        TokenDto tokenDto = signInService.signIn(signInForm);
        if (tokenDto != null) {
            Cookie tokenCookie = new Cookie("Authentication", "Bearer_" + tokenDto.getToken());
            servletResponse.addCookie(tokenCookie);
            return "redirect:/";
        } else {
            return null; //todo
        }
    }

   /* @PostMapping("/signIn")
    public void signIn(@RequestBody SignInForm signInForm, HttpServletResponse servletResponse) {
        System.out.println(signInForm);
        TokenDto tokenDto = signInService.signIn(signInForm);
        if (tokenDto != null) {
            Cookie tokenCookie = new Cookie("Authentication", "Bearer_" + tokenDto.getToken());
            servletResponse.addCookie(tokenCookie);
        }
    }*/
}
