package com.vsnitko.oauth2.model.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author v.snitko
 * @since 2022.12.18
 */
@Data
@AllArgsConstructor
public class SignInRequest {

    @NotBlank(message = "No email")
    @Email(message = "Not an email")
    private String email;

    @NotBlank(message = "No password")
    private String password;
}
