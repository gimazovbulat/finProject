package ru.itis.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.itis.dto.BookingDto;
import ru.itis.dto.MessageDto;
import ru.itis.dto.SeatDto;
import ru.itis.services.interfaces.MailService;

import java.util.stream.Collectors;

@Component
@Aspect
public class MyAspect {
    private final MailService mailService;
    private final String TEMPLATE = "Hi %s! Here's your booking rooms: %s. From: %s to %s";


    public MyAspect(MailService mailService) {
        this.mailService = mailService;
    }

    @AfterReturning(value = "@annotation(SendMailAnno)", returning = "bookingDto")
    public void sendLinkToMail(BookingDto bookingDto) {
        String formattedText = String.format(TEMPLATE,
                bookingDto.getEmail(),
                bookingDto.getSeats().stream().map(SeatDto::getNumber).collect(Collectors.toList()),
                bookingDto.getStartTime(),
                bookingDto.getEndTime()
        );
        mailService.sendText(bookingDto.getEmail(), new MessageDto(formattedText, "booked seats"));
    }
}

