package com.vsnitko.oauth2.service.impl.registry;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.service.UserBuilder;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author v.snitko
 * @since 2022.12.24
 */
@Service
@RequiredArgsConstructor
public class GithubUserBuilder implements UserBuilder {

    private final ObjectMapper objectMapper;

    @Override
    public String getProviderName() {
        return "github";
    }

    @Override
    public User buildUser(OAuth2AuthorizedClient authorizedClient, OAuth2AuthenticationToken oAuth2Token) {
        final OAuth2User oAuth2User = oAuth2Token.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            email = getPrivateGithubEmail(authorizedClient);
        }

        return User.builder()
            .name(oAuth2User.getAttribute("login"))
            .email(email)
            .avatar(oAuth2User.getAttribute("avatar_url"))
            .emailVerified(true).build();
    }

    /**
     * GitHub hides user email by default, so we should make a call to GitHub API to get private email
     */
    private String getPrivateGithubEmail(OAuth2AuthorizedClient authorizedClient) {
        final OAuth2AccessToken githubAccessToken = authorizedClient.getAccessToken();
        final String userUri = authorizedClient
            .getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUri();
        final String githubToken = String.format("%s %s",
                                                 githubAccessToken.getTokenType().getValue(),
                                                 githubAccessToken.getTokenValue());

        try {
            final String response = Request
                .get(String.format("%s/emails", userUri))
                .addHeader(AUTHORIZATION, githubToken)
                .execute()
                .handleResponse(new BasicHttpClientResponseHandler());
            return objectMapper.readValue(response, JsonNode.class).get(0).get("email").textValue();
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());
        }
    }
}
