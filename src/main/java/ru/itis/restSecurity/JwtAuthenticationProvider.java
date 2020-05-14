package ru.itis.restSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.itis.models.UserState;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component("jwtAuthenticationProvider")
@Profile("rest")
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Bad token");
        }

        List<String> roles = claims.get("roles", ArrayList.class);
        String email = claims.getSubject();
        UserState userState = UserState.valueOf(claims.get("state", String.class));
        Long id = claims.get("id", Long.class);

        UserDetails userDetails = new UserDetailsImpl(id, email, userState, roles);

        authentication.setAuthenticated(true);
        ((JwtAuthentication) authentication).setUserDetails(userDetails);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
