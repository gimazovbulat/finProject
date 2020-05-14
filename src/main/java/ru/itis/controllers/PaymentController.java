package ru.itis.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.BookingDto;
import ru.itis.services.interfaces.BookingService;

@RestController
@Profile("mvc")
public class PaymentController {
    private final BookingService bookingService;

    public PaymentController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/payment/booking/{id}")
    public ResponseEntity<Boolean> pay(@PathVariable("id") Long bookingId) {
        bookingService.pay(bookingId);
        return ResponseEntity.ok(true);
    }
}
