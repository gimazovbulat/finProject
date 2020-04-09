package ru.itis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.dto.PlaceDto;
import ru.itis.services.interfaces.PlacesService;

import java.util.List;

@Controller
public class PlacesController {
    private final PlacesService placesService;

    public PlacesController(PlacesService placesService) {
        this.placesService = placesService;
    }

    @GetMapping("/places/{id}")
    public String getPlace(@PathVariable("id") Integer id, Model model) {
        PlaceDto place = placesService.getById(id);
        model.addAttribute("place", place);
        return "place";
    }

    @GetMapping("/places")
    public String getPage(@RequestParam("page") Integer page, Model model) {
        List<PlaceDto> places = placesService.getAll(page, 10);
        model.addAttribute("places", places);
        model.addAttribute("count", placesService.getCount());
        return "places";
    }
}
