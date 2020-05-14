package ru.itis.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dao.interfaces.RolesRepository;
import ru.itis.dao.interfaces.UsersRepository;
import ru.itis.dto.SignUpForm;
import ru.itis.dto.StatusCode;
import ru.itis.models.Role;
import ru.itis.models.User;
import ru.itis.models.UserState;
import ru.itis.services.interfaces.MailService;
import ru.itis.services.interfaces.SignUpService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SignUpServiceImpl implements SignUpService {
    private final
    UsersRepository usersRepository;
    private final
    PasswordEncoder passwordEncoder;
    private final
    MailService mailService;
    private final RolesRepository rolesRepository;

    public SignUpServiceImpl(PasswordEncoder passwordEncoder, UsersRepository usersRepository, MailService mailService, RolesRepository rolesRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.mailService = mailService;
        this.rolesRepository = rolesRepository;
    }

    @Transactional
    @Override
    public StatusCode signUp(SignUpForm form) {
        Set<Role> roles = new HashSet<>();
        Optional<Role> role = rolesRepository.findById(1);
        role.ifPresent(roles::add);
        String confirmLink = UUID.randomUUID().toString();
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            return new StatusCode(StatusCode.Status.VALIDATION_ERROR, "password and confirmation don't match");
        }
        User user = User.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .userState(UserState.NOT_CONFIRMED)
                .confirmLink(confirmLink)
                .avaPath("/files/=c82217a0-7a6a-11ea-bc55-0242ac130003.png")
                .points(0)
                .roles(roles)
                .build();

        usersRepository.save(user);

        mailService.sendEmailConfirmationLink(user);

        return new StatusCode(StatusCode.Status.OK, "");
    }
}
