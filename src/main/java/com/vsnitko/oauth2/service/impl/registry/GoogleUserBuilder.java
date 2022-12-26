package com.vsnitko.oauth2.service.impl.registry;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.service.UserBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * @author v.snitko
 * @since 2022.12.24
 */
@Service
public class GoogleUserBuilder implements UserBuilder {

    @Override
    public String getRegistrationId() {
        return "google";
    }

    @Override
    public User buildUser(final OAuth2AuthorizedClient authorizedClient, final OAuth2AuthenticationToken oAuth2Token) {
        final OAuth2User oAuth2User = oAuth2Token.getPrincipal();
        return User.builder()
                .name(oAuth2User.getAttribute("name"))
                .email(oAuth2User.getAttribute("email"))
                .avatar(oAuth2User.getAttribute("picture"))
                .emailVerified(oAuth2User.getAttribute("email_verified"))
                .build();
    }
}
