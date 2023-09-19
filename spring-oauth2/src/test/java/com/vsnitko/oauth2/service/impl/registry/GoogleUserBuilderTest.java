package com.vsnitko.oauth2.service.impl.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.vsnitko.oauth2.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

@ExtendWith(MockitoExtension.class)
class GoogleUserBuilderTest {

    @Mock
    private OAuth2AuthorizedClient authorizedClient;

    @Mock
    private OAuth2AuthenticationToken oAuth2Token;

    @Mock
    private OAuth2User oAuth2User;

    @InjectMocks
    private GoogleUserBuilder googleUserBuilder;

    @Test
    void getProviderName() {
        assertEquals("google", googleUserBuilder.getProviderName());
    }

    @Test
    public void buildUser() {
        final String name = "name";
        final String email = "email";
        final String avatar = "avatar";
        when(oAuth2Token.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttribute("name")).thenReturn(name);
        when(oAuth2User.getAttribute("email")).thenReturn(email);
        when(oAuth2User.getAttribute("picture")).thenReturn(avatar);
        when(oAuth2User.getAttribute("email_verified")).thenReturn(true);

        User user = googleUserBuilder.buildUser(authorizedClient, oAuth2Token);

        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(avatar, user.getAvatar());
        assertTrue(user.getEmailVerified());
    }
}
