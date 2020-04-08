package ru.itis.services.interfaces;

import ru.itis.dto.UserDto;
import ru.itis.models.User;

public interface MailService {
    void sendEmailConfirmationLink(User user);

    void sendEmailText(String email, String subject, String text);

    void sendEmailWelcoming(UserDto userDto);
}
