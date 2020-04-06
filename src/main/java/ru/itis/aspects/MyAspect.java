package ru.itis.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.itis.dto.BookingDto;
import ru.itis.dto.MessageDto;
import ru.itis.dto.RoomDto;
import ru.itis.services.interfaces.MailService;

import java.text.SimpleDateFormat;
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
    public void sendBookingToMail(BookingDto bookingDto) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedText = String.format(TEMPLATE,
                bookingDto.getEmail(),
                bookingDto.getRooms().stream().map(RoomDto::getNumber).collect(Collectors.toList()),
                simpleDateFormat.format(bookingDto.getStartTime()),
                simpleDateFormat.format(bookingDto.getEndTime())
        );
        mailService.sendText(bookingDto.getEmail(), new MessageDto(formattedText, "booked rooms"));
    }
}

