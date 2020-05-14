package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInForm {
    @NotBlank(message = "Please enter email")
    @Email(message = "Email is not correct")
    private String email;
    @NotBlank(message = "Please enter email")
    private String password;

}
