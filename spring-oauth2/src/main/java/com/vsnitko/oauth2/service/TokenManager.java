package com.vsnitko.oauth2.service;

import com.vsnitko.oauth2.model.entity.User;
import org.springframework.security.core.Authentication;

/**
 * Service to manipulate JWT tokens
 *
 * @author v.snitko
 * @since 2022.12.26
 */
public interface TokenManager {

  String createToken(User user);

  boolean validateToken(String authToken);

  Authentication getAuthentication(String token);
}
