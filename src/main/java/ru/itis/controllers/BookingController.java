package ru.itis.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.BookingDto;
import ru.itis.dto.BookingForm;
import ru.itis.dto.PlaceDto;
import ru.itis.dto.UserDto;
import ru.itis.models.Booking;
import ru.itis.security.CurrentUser;
import ru.itis.services.interfaces.BookingService;
import ru.itis.services.interfaces.PlacesService;
import ru.itis.services.interfaces.UsersService;

import java.util.List;

@Controller
public class BookingController {
    private final UsersService usersService;
    private final BookingService bookingService;
    private final PlacesService placesService;

    public BookingController(UsersService usersService,
                             BookingService bookingService,
                             PlacesService placesService) {
        this.usersService = usersService;
        this.bookingService = bookingService;
        this.placesService = placesService;
    }

    @GetMapping("users/{id}/bookings")
    public String getBookings(Model model, @CurrentUser UserDetails userDetails, @PathVariable("id") Long userId){
        String email = userDetails.getUsername();
        UserDto user = usersService.findUser(email);
        List<BookingDto> bookings = user.getBookings();
        model.addAttribute("bookings", bookings);
        return "bookings";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/booking/place/{id}")
    public String bookSeat(@PathVariable("id") Integer placeId,
                           BookingForm bookingForm,
                           @CurrentUser UserDetails userDetails,
                           Model model) {
        String email = userDetails.getUsername();
        UserDto user = usersService.findUser(email);

        bookingForm.setUserDto(user);
        bookingForm.setPlaceId(placeId);

        BookingDto bookingDto = bookingService.bookRooms(bookingForm);
        model.addAttribute("booking", bookingDto);

        PlaceDto place = placesService.getById(placeId);

        model.addAttribute("place", place);
        return "place";
    }
}
