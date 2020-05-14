package ru.itis.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.dto.UserDto;
import ru.itis.restSecurity.CurrentUser;
import ru.itis.services.interfaces.UsersService;

@Controller
@Profile("mvc")
public class ProfileController {
    private final UsersService usersService;

    public ProfileController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showUser(Model model, @CurrentUser UserDetails userDetails) {
        String email = userDetails.getUsername();
        UserDto user = usersService.findUser(email);
        model.addAttribute("user", user);
        return "profile";
    }
}
