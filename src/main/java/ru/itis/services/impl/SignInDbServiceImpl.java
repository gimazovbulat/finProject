package ru.itis.services.impl;

import org.springframework.stereotype.Service;
import ru.itis.dao.interfaces.UsersRepository;
import ru.itis.dto.SignInForm;
import ru.itis.dto.TokenDto;
import ru.itis.models.User;
import ru.itis.services.interfaces.SignInDbService;
import ru.itis.services.interfaces.SignInService;

import java.util.Optional;

@Service
public class SignInDbServiceImpl implements SignInDbService {
    private final UsersRepository usersRepository;

    public SignInDbServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean signIn(SignInForm signInForm) {
        Optional<User> optionalUser = usersRepository.findByEmail(signInForm.getEmail());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
        }
        return true;
    }
}