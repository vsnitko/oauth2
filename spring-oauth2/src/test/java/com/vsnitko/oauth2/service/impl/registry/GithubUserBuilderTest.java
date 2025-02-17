package com.vsnitko.oauth2.service.impl.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.entity.UserDetailsImpl;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class GithubUserBuilderTest {

    private static final OAuth2AuthorizedClient AUTHORIZED_CLIENT = new OAuth2AuthorizedClient(
        ClientRegistration.withRegistrationId("any")
            .authorizationGrantType(AuthorizationGrantType.JWT_BEARER).build(),
        "any",
        new OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            "tokenValue",
            Instant.now(),
            Instant.now().plusMillis(1)
        )
    );

    @Mock(answer = RETURNS_DEEP_STUBS)
    RestClient restClient;

    @Mock
    @SuppressWarnings("rawtypes")
    RestClient.RequestHeadersSpec headersSpec;

    @Mock
    RestClient.ResponseSpec responseSpec;

    @Mock(answer = RETURNS_DEEP_STUBS)
    JsonNode jsonNodeMock;

    @InjectMocks
    GithubUserBuilder githubUserBuilder;

    @Test
    void testGetProviderName() {
        assertEquals("github", githubUserBuilder.getProviderName());
    }

    @Test
    void testBuildUser() {
        final String expectedEmail = "expectedEmail";
        final String expectedLogin = "expectedLogin";
        final String expectedAvatar = "expectedAvatar";

        // 'Cannot resolve method' IDE bug
        when(restClient.get().uri(any(String.class)).header(any(), any())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(JsonNode.class)).thenReturn(jsonNodeMock);
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
