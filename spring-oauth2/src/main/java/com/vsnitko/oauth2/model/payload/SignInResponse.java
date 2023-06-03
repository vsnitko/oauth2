package com.vsnitko.oauth2.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author v.snitko
 * @since 2022.12.17
 */
@Data
@AllArgsConstructor
public class SignInResponse {

    private String token;
}
