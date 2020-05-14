package ru.itis.services.interfaces;

import ru.itis.dto.SignInForm;

public interface SignInDbService {
    boolean signIn(SignInForm signInForm);
}
