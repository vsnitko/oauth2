package com.vsnitko.oauth2.handler;

import com.vsnitko.oauth2.config.AppProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Class with onAuthenticationSuccess method that is executed when user was successfully logged-in with OAuth2.
 * Redirects user to path depending on {@link #appProperties}. Full path generated at
 * {@link #determineTargetUrl(HttpServletRequest, HttpServletResponse, Authentication)}
 */
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AppProperties appProperties;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {;

        final String uri = appProperties.getClientPath() + appProperties.getClientOauth2RedirectEndpoint();
        final String targetUrl = UriComponentsBuilder.fromUriString(uri)
                .build()
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
