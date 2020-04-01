package ru.itis.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.SignInForm;
import ru.itis.dto.TokenDto;
import ru.itis.security.CurrentUser;
import ru.itis.security.JwtAuthentication;
import ru.itis.services.interfaces.SignInService;

@RestController
public class SignInController {
    private final SignInService signInService;
    private final AuthenticationManager authenticationManager;

    public SignInController(SignInService signInService, AuthenticationManager authenticationManager) {
        this.signInService = signInService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/signIn")
    public String getPage(@CurrentUser UserDetails userDetails) {
        if (userDetails != null) {
            return "redirect:/profile";
        }
        return "signIn";
    }

  /*  @PostMapping("/signIn")
    public String signIn(SignInForm signInForm, HttpServletResponse servletResponse) {
        TokenDto tokenDto = signInService.signIn(signInForm);
        if (tokenDto != null) {
            Cookie tokenCookie = new Cookie("Authorization", "Bearer_" + tokenDto.getToken());
            servletResponse.addCookie(tokenCookie);
            return "redirect:/";
        } else {
            return null; //todo
        }
    }*/

    @PostMapping("/signIn")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInForm signInData) {
        TokenDto tokenDto = signInService.signIn(signInData);
        authenticationManager.authenticate(new JwtAuthentication(tokenDto.getToken()));
       /* Authentication authentication = new JwtAuthentication(tokenDto.getToken());
        SecurityContextHolder.getContext().setAuthentication(authentication);*/
        return ResponseEntity.ok(tokenDto);
    }
}
