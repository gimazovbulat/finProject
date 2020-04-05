package ru.itis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.dto.PlaceDto;
import ru.itis.services.interfaces.PlaceService;

import java.util.Optional;

@Controller
public class PlacesController {
    private final PlaceService placeService;

    public PlacesController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/places/{id}")
    public String getPlace(@PathVariable("id") Integer id, Model model) {
        Optional<PlaceDto> optionalPlace = placeService.getById(id);
        optionalPlace.ifPresent(placeDto -> model.addAttribute("place", placeDto));
        return "place";
    }
}
