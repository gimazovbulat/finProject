package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignUpForm {
    @Email(message = "{Email.signUpForm.email}")
    @NotEmpty(message = "{NotEmpty.signUpForm.email}")
    private String email;
    @NotEmpty(message = "{NotEmpty.signUpForm.password}")
    private String password;
    @Transient
    @NotEmpty(message = "{NotEmpty.signUpForm.confirm_password}")
    private String confirmPassword;
    private MultipartFile file;
}
