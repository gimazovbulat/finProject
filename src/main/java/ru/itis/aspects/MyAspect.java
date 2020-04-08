package ru.itis.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.itis.dto.*;
import ru.itis.services.interfaces.MailService;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Component
@Aspect
public class MyAspect {
    private final MailService mailService;
    private final String BOOKING_TEMPLATE = "Hi %s! Here's your booking rooms: %s. From: %s to %s";

    public MyAspect(MailService mailService) {
        this.mailService = mailService;
    }

    @AfterReturning(value = "@annotation(SendBookingEmail)", returning = "bookingDto")
    public void formBookingMail(BookingDto bookingDto) {
        String formattedText = String.format(BOOKING_TEMPLATE,
                bookingDto.getEmail(),
                bookingDto.getRooms().stream().map(RoomDto::getNumber).collect(Collectors.toList()),
                bookingDto.getStartDate(),
                bookingDto.getEndDate()
        );
        mailService.sendEmailText(bookingDto.getEmail(), "booking", formattedText);
    }

    @AfterReturning(value = "@annotation(SendSignUpEmail)", returning = "userDto")
    public void formWelcomingMail(UserDto userDto){
        mailService.sendEmailWelcoming(userDto);
    }
}

