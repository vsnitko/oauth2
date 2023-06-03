package com.vsnitko.oauth2.model.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author v.snitko
 * @since 2022.12.18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    @NotBlank(message = "Email is not specified")
    @Email(message = "Not an email")
    private String email;

    @NotBlank(message = "Password is not specified")
    private String password;
}
