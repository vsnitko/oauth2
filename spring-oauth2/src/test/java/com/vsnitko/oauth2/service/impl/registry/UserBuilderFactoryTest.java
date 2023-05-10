package com.vsnitko.oauth2.service.impl.registry;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.service.UserBuilder;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@ExtendWith(MockitoExtension.class)
class UserBuilderFactoryTest {

  private static final String PROVIDER_NAME = "providerName";

  @Mock
  private OAuth2AuthorizedClient authorizedClient;
  @Mock
  private OAuth2AuthenticationToken oAuth2Token;
  @Mock
  private UserBuilder userBuilder;

  private UserBuilderFactory userBuilderFactory;

  @BeforeEach
  void setUp() {
    when(userBuilder.getProviderName()).thenReturn(PROVIDER_NAME);
    userBuilderFactory = new UserBuilderFactory(List.of(userBuilder));
  }

  @Test
  public void getProviderName() {
    assertThrows(IllegalStateException.class, () -> userBuilderFactory.getProviderName());
  }

  @Test
  public void buildUser() {
    final User expectedUser = new User();
    when(oAuth2Token.getAuthorizedClientRegistrationId()).thenReturn(PROVIDER_NAME);
    when(userBuilder.buildUser(authorizedClient, oAuth2Token)).thenReturn(expectedUser);

    User actualUser = userBuilderFactory.buildUser(authorizedClient, oAuth2Token);

    assertSame(expectedUser, actualUser);
  }
}
