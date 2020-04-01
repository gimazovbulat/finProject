package ru.itis.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthManager implements AuthenticationManager {
    private final AuthenticationProvider authenticationProvider;

    public AuthManager(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication auth = authenticationProvider.authenticate(authentication);
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(auth);
            return auth;
        } else return null;
    }
}
