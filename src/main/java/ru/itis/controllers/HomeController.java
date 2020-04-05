package ru.itis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.dto.PlaceDto;
import ru.itis.services.interfaces.PlaceService;

import java.util.List;

@Controller
public class HomeController {
    private final PlaceService placeService;

    public HomeController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/")
    public String getPage(Model model) {
        List<PlaceDto> places = placeService.getAll(1, 10);//todo
        model.addAttribute("places", places);
        return "home";
    }
}
