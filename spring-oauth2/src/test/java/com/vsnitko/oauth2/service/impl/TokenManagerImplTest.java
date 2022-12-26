package com.vsnitko.oauth2.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.vsnitko.oauth2.config.JwtProperties;
import com.vsnitko.oauth2.model.entity.User;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class TokenManagerImplTest {

  @Mock
  UserServiceImpl userService;

  @Mock
  JwtProperties jwtProperties;

  @InjectMocks
  TokenManagerImpl tokenManager;

  @BeforeEach
  void setUp() {
    when(jwtProperties.getSecretKey()).thenReturn("secret");
  }

  @Test
  void createToken() {
    final String name = "name";
    final String email = "email";
    final String avatar = "avatar";
    final String expectedToken = "token";
    User user = new User()
        .setId(1L)
        .setName(name)
        .setEmail(email)
        .setAvatar(avatar);

    try (
        MockedStatic<JWT> mockedJwt = mockStatic(JWT.class);
        MockedStatic<Algorithm> mockedAlgorithm = mockStatic(Algorithm.class)
    ) {
      final JWTCreator.Builder mockBuilder = mock(JWTCreator.Builder.class);
      final Algorithm algorithm = mock(Algorithm.class);
      mockedJwt.when(JWT::create).thenReturn(mockBuilder);
      mockedAlgorithm.when(() -> Algorithm.HMAC256(any(String.class))).thenReturn(algorithm);
      when(JWT.create().withSubject(any(String.class))).thenReturn(mockBuilder);
      when(JWT.create().withClaim(any(String.class), any(String.class))).thenReturn(mockBuilder);
      when(JWT.create().withIssuedAt(any(Date.class))).thenReturn(mockBuilder);
      when(JWT.create().withExpiresAt(any(Date.class))).thenReturn(mockBuilder);
      when(JWT.create().sign(algorithm)).thenReturn(expectedToken);

      String token = tokenManager.createToken(user);

      verify(JWT.create(), times(1)).withSubject(user.getId().toString());
      verify(JWT.create(), times(1)).withClaim(email, user.getEmail());
      verify(JWT.create(), times(1)).withClaim(name, user.getName());
      verify(JWT.create(), times(1)).withClaim(avatar, user.getAvatar());
      verify(JWT.create(), times(1)).withIssuedAt(any(Date.class));
      verify(JWT.create(), times(1)).withExpiresAt(any(Date.class));
      verify(JWT.create(), times(1)).sign(algorithm);

      assertEquals(expectedToken, token);
    }
  }

  @Test
  public void validateToken_valid() {
    when(jwtProperties.getExpirationMinutes()).thenReturn(1L);
    final String generatedToken = tokenManager.createToken(new User().setId(1L));

    when(jwtProperties.getSecretKey()).thenReturn("secret");

    boolean isValid = tokenManager.validateToken(generatedToken);

    assertTrue(isValid);
  }

  @Test
  void validateToken_invalid_wrongSecret() {
    when(jwtProperties.getExpirationMinutes()).thenReturn(1L);
    final String generatedToken = tokenManager.createToken(new User().setId(1L));

    when(jwtProperties.getSecretKey()).thenReturn("wrongSecret");

    boolean isValid = tokenManager.validateToken(generatedToken);

    assertFalse(isValid);
  }

  @Test
  void getAuthentication_invalid_expired() {
    final String generatedToken = tokenManager.createToken(new User().setId(1L));

    when(jwtProperties.getSecretKey()).thenReturn("secret");

    boolean isValid = tokenManager.validateToken(generatedToken);

    assertFalse(isValid);
  }

  @Test
  void getAuthentication_returnsAuthenticationWithUser() {
    final long id = 1L;
    final String generatedToken = tokenManager.createToken(new User().setId(id));
    final User foundUser = new User();

    when(userService.getById(id)).thenReturn(foundUser);

    final Authentication actualPrincipal = tokenManager.getAuthentication(generatedToken);

    verify(userService, times(1)).getById(id);
    assertSame(foundUser, actualPrincipal.getPrincipal());
  }
}
