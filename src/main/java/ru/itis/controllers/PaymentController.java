package ru.itis.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.BookingDto;
import ru.itis.dto.PaymentDto;
import ru.itis.security.CurrentUser;
import ru.itis.services.interfaces.BookingService;
import ru.itis.services.interfaces.PaymentService;

@RestController
public class PaymentController {
    private final PaymentService paymentService;
    private final BookingService bookingService;

    public PaymentController(PaymentService paymentService, BookingService bookingService) {
        this.paymentService = paymentService;
        this.bookingService = bookingService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/payment/booking/{id}")
    public ResponseEntity<PaymentDto> pay(@PathVariable("id") Long bookingId, @CurrentUser UserDetails userDetails) {
        System.out.println(userDetails);
        BookingDto booking = bookingService.findBooking(bookingId);
        System.out.println("booking " + booking);
        PaymentDto paymentDto = booking.getPaymentDto();
        System.out.println(paymentDto);
        PaymentDto result = paymentService.pay(paymentDto);
        return ResponseEntity.ok(result);
    }
}
