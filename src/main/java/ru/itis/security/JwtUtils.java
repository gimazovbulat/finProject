package ru.itis.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.models.User;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    @Value("${jwt.token.secret}")
    private String secret;

    private String encodedSecret;

    @PostConstruct
    public void init() {
        encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(User user) {

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", user.getRoles().stream().map(Role::getVal).collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, encodedSecret)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(encodedSecret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String reqToken = req.getHeader("Authorization");
        String cookieToken = null;
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("Authorization")) {
                    cookieToken = cookie.getValue();
                }
            }
        }
        if (reqToken != null && reqToken.startsWith("Bearer_")) {
            return reqToken.substring(7);
        } else if (cookieToken != null && cookieToken.startsWith("Bearer_")) {
            return cookieToken.substring(7);
        }
        return null;
    }
}
