package com.vsnitko.oauth2.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author v.snitko
 * @since 2022.12.17
 */
@Data
@Builder
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;

    @JsonIgnore
    private String refreshToken;

    private UserResponse user;
}
