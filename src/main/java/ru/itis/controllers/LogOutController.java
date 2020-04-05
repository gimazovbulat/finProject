package ru.itis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LogOutController {
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        System.out.println(request);
        return "redirect:/signIn";
     /*   Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authentication")) {
                cookie.setMaxAge(0);
                cookie.setValue("");
            }
        }
        return "redirect:/signIn";*/
    }

}
