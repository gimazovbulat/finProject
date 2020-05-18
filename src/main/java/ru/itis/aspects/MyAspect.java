package ru.itis.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.dao.interfaces.UsersRepository;
import ru.itis.dto.BookingDto;
import ru.itis.dto.RoomDto;
import ru.itis.dto.UserDto;
import ru.itis.models.FileInfo;
import ru.itis.models.User;
import ru.itis.services.interfaces.MailService;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Aspect
public class MyAspect {
    private final MailService mailService;
    private final UsersRepository usersRepository;
    private final String BOOKING_TEMPLATE = "Hi %s! Here's your booking rooms: %s. From: %s to %s";

    @Value("${web.root}")
    private String webRoot;

    public MyAspect(MailService mailService, UsersRepository usersRepository) {
        this.mailService = mailService;
        this.usersRepository = usersRepository;
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
    public void formWelcomingMail(UserDto userDto) {
        mailService.sendEmailWelcoming(userDto);
    }

    @AfterReturning(value = "@annotation(SendMailAnno)", returning = "fileInfo")
    public void sendLinkToMail(FileInfo fileInfo){
        Optional<User> optionalUser = usersRepository.find(fileInfo.getUser().getId());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            mailService.sendEmailText(user.getEmail(), "file downloaded", webRoot + fileInfo.getUrl());
        }
    }
}

