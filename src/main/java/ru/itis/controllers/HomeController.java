package ru.itis.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Profile("mvc")
public class HomeController {

    @GetMapping("/")
    public String getPage() {
        return "home";
    }
}
