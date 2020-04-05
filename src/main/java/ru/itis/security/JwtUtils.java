package ru.itis.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.models.Role;
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
        claims.put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        claims.put("id", user.getId());
        claims.put("state", user.getUserState().getValue());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, encodedSecret)
                .compact();
    }


    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(encodedSecret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authentication")) {
                    cookie.setMaxAge(0);
                    cookie.setValue("");
                }
            }
            return false;
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String reqToken = req.getHeader("Authentication");
        String cookieToken = null;
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("Authentication")) {
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
