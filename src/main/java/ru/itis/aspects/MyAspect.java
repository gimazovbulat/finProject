package ru.itis.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.itis.dto.BookingDto;
import ru.itis.services.interfaces.MailService;

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
                bookingDto.getUserDto().getEmail(),
                bookingDto.getSeats(),
                bookingDto.getStartTime(),
                bookingDto.getEndTime()
        );
        mailService.sendText(bookingDto.getUserDto().getEmail(), formattedText);
    }
}

