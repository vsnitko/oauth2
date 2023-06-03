package com.vsnitko.oauth2.service.impl.registry;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.service.UserBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Factory service which will choose implementation for building user. How it works: <br>
 * <ul>
 *      <li>{@link #UserBuilderFactory(List)} constructor will be called while initializing spring context.
 * As parameters, spring will pass all known services that implements {@link UserBuilder} (except this one)</li>
 *      <li>
 *          In constructor, HashMap of services will be filled where key is provider name and value is actual service.
 *          Provider name is valuated from {@link UserBuilder#getProviderName()} method
 *      </li>
 *      <li>
 *        When we need to choose required implementation, we pass OAuth2AuthenticationToken as parameter in
 *        {@link #buildUser(OAuth2AuthorizedClient, OAuth2AuthenticationToken)} method.
 *        This token contains providerName (or clientRegistrationId),
 *        which we can use to get required implementation from HashMap
 *      </li>
 * </ul>
 *
 * @author v.snitko
 * @since 2022.12.24
 */
@Primary
@Service
public class UserBuilderFactory implements UserBuilder {

    //key - providerName (or clientRegistrationId); value - service implementation
    private final Map<String, UserBuilder> services = new HashMap<>();

    public UserBuilderFactory(List<UserBuilder> services) {
        services.forEach(service -> this.services.put(service.getProviderName(), service));
    }

    @Override
    public String getProviderName() {
        throw new IllegalStateException(
            "This method should not be called directly in factory service. Please, use another implementation");
    }

    @Override
    public User buildUser(OAuth2AuthorizedClient authorizedClient, OAuth2AuthenticationToken oAuth2Token) {
        final String providerName = oAuth2Token.getAuthorizedClientRegistrationId();
        return this.services.get(providerName).buildUser(authorizedClient, oAuth2Token);
    }
}
