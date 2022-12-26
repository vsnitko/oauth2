package com.vsnitko.oauth2.service.impl.registry;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.service.UserBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author v.snitko
 * @since 2022.12.24
 */
@Primary
@Service
public class UserBuilderPrimary implements UserBuilder {

    private final Map<String, UserBuilder> services = new HashMap<>();

    public UserBuilderPrimary(final List<UserBuilder> services) {
        services.forEach(service -> this.services.put(service.getRegistrationId(), service));
    }

    @Override
    public String getRegistrationId() {
        return null;
    }

    @Override
    public User buildUser(final OAuth2AuthorizedClient authorizedClient, final OAuth2AuthenticationToken oAuth2Token) {
        final String currentRegistrationId = oAuth2Token.getAuthorizedClientRegistrationId();
        return this.services.get(currentRegistrationId).buildUser(authorizedClient, oAuth2Token);
    }
}
