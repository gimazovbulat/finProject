package ru.itis.services.interfaces;

import ru.itis.dto.SignUpForm;
import ru.itis.dto.StatusCode;

public interface SignUpService {
    StatusCode signUp(SignUpForm signUpForm);
}
