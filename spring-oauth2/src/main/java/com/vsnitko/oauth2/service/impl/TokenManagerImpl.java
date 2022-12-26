package com.vsnitko.oauth2.service.impl;

import static java.lang.Long.parseLong;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vsnitko.oauth2.config.JwtProperties;
import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.service.TokenManager;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Class that is responsible for managing JWT tokens: generating, validating, extracting user data (decoding)
 *
 * @author v.snitko
 * @since 2022.12.17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenManagerImpl implements TokenManager {

  private final UserServiceImpl userService;
  private final JwtProperties jwtProperties;

  /**
   * Generates JWT token based on user data and {@link #jwtProperties}
   */
  @Override
  public String createToken(User user) {
    final Date now = new Date();
    final Date expired = new Date(now.getTime() + jwtProperties.getExpirationMinutes() * 60 * 1000);
    final Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());

    return JWT.create()
        .withSubject(user.getId().toString())
        .withClaim("email", user.getEmail())
        .withClaim("name", user.getName())
        .withClaim("avatar", user.getAvatar())
        .withIssuedAt(now)
        .withExpiresAt(expired)
        .sign(algorithm);
  }

  /**
   * Validates JWT token. Checks secret key and expiration date
   */
  @Override
  public boolean validateToken(String authToken) {
    try {
      final Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());
      final DecodedJWT decodedJwt = JWT.require(algorithm)
          .build()
          .verify(authToken);
      return !decodedJwt.getExpiresAt().before(new Date());
    } catch (JWTVerificationException e) {
      return false;
    }
  }

  /**
   * Extracts user id from token (decoding) and searches for user with this id in db
   */
  @Override
  public Authentication getAuthentication(String token) {
    final String userId = JWT.decode(token).getSubject();
    User user = userService.getById(parseLong(userId));
    return new UsernamePasswordAuthenticationToken(user, null);
  }
}
