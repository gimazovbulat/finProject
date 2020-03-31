package ru.itis.controllers;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.security.CurrentUser;
import ru.itis.services.interfaces.UsersService;

@Controller
public class ProfileController {
    private final UsersService usersService;

    public ProfileController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/profile")
    public String showUser(Model model, @CurrentUser UserDetails userDetailsTest) {
        System.out.println(userDetailsTest);
      /*  System.out.println(SecurityContextHolder.getContext().getAuthentication());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Optional<UserDto> optionalUser = usersService.findUser(email);
        optionalUser.ifPresent(userDto -> model.addAttribute("user", userDto));*/
        return "profile";
    }
}
