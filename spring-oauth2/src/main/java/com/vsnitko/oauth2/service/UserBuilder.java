package com.vsnitko.oauth2.service;

import com.vsnitko.oauth2.model.entity.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

/**
 * @author v.snitko
 * @since 2022.12.24
 */
public interface UserBuilder {

  String getProviderName();

  User buildUser(OAuth2AuthorizedClient authorizedClient, OAuth2AuthenticationToken oAuth2Token);
}
