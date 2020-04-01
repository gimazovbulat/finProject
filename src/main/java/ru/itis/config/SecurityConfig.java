package ru.itis.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import ru.itis.dao.interfaces.UsersRepository;
import ru.itis.security.AuthManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final UsersRepository usersRepository;

    private final UserDetailsService userDetailsService;
    private final AuthenticationProvider jwtAuthenticationProvider;
    @Qualifier(value = "jwtAuthenticationFilter")
    private final GenericFilterBean jwtAuthenticationFilter;


    public SecurityConfig(UsersRepository usersRepository,
                          UserDetailsService userDetailsService,
                          @Qualifier("jwtAuthenticationProvider") AuthenticationProvider jwtAuthenticationProvider, GenericFilterBean jwtAuthenticationFilter) {
        this.usersRepository = usersRepository;
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/signIn", "/signUp", "/confirm/*", "/")
                .permitAll()
                .antMatchers("/admin")
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new AuthManager(jwtAuthenticationProvider);
    }
}
