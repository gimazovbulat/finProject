package ru.itis.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dao.interfaces.RolesRepository;
import ru.itis.dao.interfaces.UsersRepository;
import ru.itis.dto.SignUpForm;
import ru.itis.models.User;
import ru.itis.models.UserState;
import ru.itis.models.Role;
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
    public void signUp(SignUpForm form) {
        Set<Role> roles = new HashSet<>();
        Optional<Role> role = rolesRepository.findById(1);
        role.ifPresent(roles::add);
        String confirmLink = UUID.randomUUID().toString();
        User user = User.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .userState(UserState.NOT_CONFIRMED)
                .confirmLink(confirmLink)
                .avaPath("default")
                .roles(roles)
                .build();

        usersRepository.save(user);

        mailService.sendEmailConfirmationLink(user);
    }
}
