package com.vsnitko.oauth2.service;

import com.vsnitko.oauth2.model.entity.User;
import org.springframework.security.core.Authentication;

/**
 * @author v.snitko
 * @since 2022.12.26
 */
public interface TokenProvider {

    String createToken(User user);

    boolean validateToken(String authToken);

    Authentication getAuthentication(String token);
}
