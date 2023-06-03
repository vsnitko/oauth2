package com.vsnitko.oauth2.handler;

import com.vsnitko.oauth2.config.AppProperties;
import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.service.TokenManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Class with onAuthenticationSuccess method that is executed when user was successfully logged-in with OAuth2.
 * Redirects user to path depending on {@link #appProperties}. Full path generated at
 * {@link #determineTargetUrl(HttpServletRequest, HttpServletResponse, Authentication)}
 */
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenManager tokenManager;
    private final AppProperties appProperties;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        final String targetUrl = determineTargetUrl(request, response, authentication);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * Generates redirect URL path. Sets JWT token to URL query param
     */
    @Override
    protected String determineTargetUrl(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) {
        final String token = tokenManager.createToken((User) authentication.getDetails());
        final String uri = appProperties.getClientPath() + appProperties.getClientOauth2RedirectEndpoint();
        return UriComponentsBuilder.fromUriString(uri)
            .queryParam("token", token)
            .build().toUriString();
    }
}
