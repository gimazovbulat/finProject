package ru.itis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
@Profile("mvc")
@EnableJdbcHttpSession
public class SessionConfig extends AbstractHttpSessionApplicationInitializer {
}
