package ru.itis.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.dao.interfaces.UsersRepository;
import ru.itis.models.Role;
import ru.itis.models.User;

import java.util.Optional;
import java.util.stream.Collectors;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsersRepository usersRepository;

    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = usersRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            return new UserDetailsImpl(
                    user.getId(),
                    user.getEmail(),
                    user.getUserState(),
                    user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        }
        throw new UsernameNotFoundException("user not found");
    }
}
