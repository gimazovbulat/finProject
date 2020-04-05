package ru.itis.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.BookingDto;
import ru.itis.dto.BookingForm;
import ru.itis.dto.PlaceDto;
import ru.itis.dto.UserDto;
import ru.itis.security.CurrentUser;
import ru.itis.services.interfaces.BookingService;
import ru.itis.services.interfaces.PlaceService;
import ru.itis.services.interfaces.UsersService;

import java.util.Optional;

@Controller
public class BookingController {
    private final UsersService usersService;
    private final BookingService bookingService;
    private final PlaceService placeService;

    public BookingController(UsersService usersService,
                             BookingService bookingService,
                             PlaceService placeService) {
        this.usersService = usersService;
        this.bookingService = bookingService;
        this.placeService = placeService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/booking/place/{id}")
    public String bookSeat(@PathVariable("id") Integer placeId,
                           BookingForm bookingForm,
                           @CurrentUser UserDetails userDetails,
                           Model model) {
        String email = userDetails.getUsername();
        Optional<UserDto> optionalUser = usersService.findUser(email);

        if (optionalUser.isPresent()) {
            UserDto user = optionalUser.get();

            bookingForm.setUserDto(user);
            bookingForm.setPlaceId(placeId);

            BookingDto bookingDto = bookingService.bookSeats(bookingForm);
            model.addAttribute("booking", bookingDto);
            placeService.getById(placeId).ifPresent(dto -> model.addAttribute("place", dto));
            return "place";
        }
        return "place";
    }
}
