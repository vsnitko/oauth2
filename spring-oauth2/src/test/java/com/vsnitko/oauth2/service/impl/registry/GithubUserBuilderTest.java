package com.vsnitko.oauth2.service.impl.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.entity.UserDetailsImpl;
import java.io.IOException;
import java.time.Instant;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.client.HttpClientErrorException;

@ExtendWith(MockitoExtension.class)
class GithubUserBuilderTest {

  @Mock
  ObjectMapper objectMapper;

  @InjectMocks
  GithubUserBuilder githubUserBuilder;

  @Mock(answer = RETURNS_DEEP_STUBS)
  Request requestMock;

  @Mock
  Response responseMock;

  @Mock(answer = RETURNS_DEEP_STUBS)
  JsonNode jsonNodeMock;

  @Test
  void testGetProviderName() {
    assertEquals("github", githubUserBuilder.getProviderName());
  }

  private static final OAuth2AuthorizedClient AUTHORIZED_CLIENT = new OAuth2AuthorizedClient(
      ClientRegistration.withRegistrationId("any").authorizationGrantType(AuthorizationGrantType.JWT_BEARER).build(),
      "any",
      new OAuth2AccessToken(
          OAuth2AccessToken.TokenType.BEARER,
          "tokenValue",
          Instant.now(),
          Instant.now().plusMillis(1)
      )
  );

  @Test
  void testBuildUser() throws IOException {
    final String expectedEmail = "expectedEmail";
    final String expectedLogin = "expectedLogin";
    final String expectedAvatar = "expectedAvatar";
    try (MockedStatic<Request> mockedRequest = mockStatic(Request.class)) {
      mockedRequest.when(() -> Request.get(any(String.class))).thenReturn(requestMock);
      when(requestMock.addHeader(any(), any()).execute()).thenReturn(responseMock);
      final String response = "response";
      when(responseMock.handleResponse(any())).thenReturn(response);
      when(objectMapper.readValue(response, JsonNode.class)).thenReturn(jsonNodeMock);
      when(jsonNodeMock.get(0).get("email").textValue()).thenReturn(expectedEmail);

      final UserDetailsImpl principal = new UserDetailsImpl(new User());
      principal.getAttributes().put("login", expectedLogin);
      principal.getAttributes().put("avatar_url", expectedAvatar);
      final OAuth2AuthenticationToken oAuth2Token = new OAuth2AuthenticationToken(principal, null, "any");

      final User actualUser = githubUserBuilder.buildUser(AUTHORIZED_CLIENT, oAuth2Token);

      assertEquals(expectedEmail, actualUser.getEmail());
      assertEquals(expectedLogin, actualUser.getName());
      assertEquals(expectedAvatar, actualUser.getAvatar());
    }
  }

  @Test
  void testBuildUser_exceptionally() throws IOException {
    try (MockedStatic<Request> mockedRequest = mockStatic(Request.class)) {
      mockedRequest.when(() -> Request.get(any(String.class))).thenReturn(requestMock);
      when(requestMock.addHeader(any(), any()).execute()).thenReturn(responseMock);
      final String response = "response";
      when(responseMock.handleResponse(any())).thenReturn(response);
      when(objectMapper.readValue(response, JsonNode.class)).thenAnswer(a -> {
        throw new IOException();
      });

      final OAuth2AuthenticationToken oAuth2Token =
          new OAuth2AuthenticationToken(new UserDetailsImpl(new User()), null, "any");
      assertThrows(HttpClientErrorException.class, () -> githubUserBuilder.buildUser(AUTHORIZED_CLIENT, oAuth2Token));
    }
  }

  @Test
  void testBuildUser_emailNotNull() {
    final String email = "anyEmail";
    final UserDetailsImpl principal = new UserDetailsImpl(new User());
    principal.getAttributes().put("email", email);
    final OAuth2AuthenticationToken oAuth2Token = new OAuth2AuthenticationToken(principal, null, "any");

    final User actualUser = githubUserBuilder.buildUser(AUTHORIZED_CLIENT, oAuth2Token);

    assertEquals(email, actualUser.getEmail());
    }
}
