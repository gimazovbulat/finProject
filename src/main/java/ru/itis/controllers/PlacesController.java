package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${size}")
    private int size;

    @GetMapping("/places/{id}")
    public String getPlace(@PathVariable("id") Integer id, Model model) {
        PlaceDto place = placesService.getById(id);
        model.addAttribute("place", place);
        return "place";
    }

    @GetMapping("/places")
    public String getPage(@RequestParam("page") Integer page, Model model) {
        List<PlaceDto> places = placesService.getAll(page, size);
        model.addAttribute("places", places);
        int count = placesService.getCount() / size;
        count = count == 0 ? 1 : count;
        model.addAttribute("count", count);
        return "places";
    }
}
