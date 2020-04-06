package ru.itis.services.interfaces;

import ru.itis.dto.UserDto;

import java.util.Optional;

public interface UsersService {
    UserDto findUser(Long id);
    UserDto findUser(String email);
}
