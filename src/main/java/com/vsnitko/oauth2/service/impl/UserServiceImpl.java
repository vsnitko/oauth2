package com.vsnitko.oauth2.service.impl;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.entity.UserDetailsImpl;
import com.vsnitko.oauth2.model.payload.EditRequest;
import com.vsnitko.oauth2.repository.UserRepository;
import com.vsnitko.oauth2.service.UserBuilder;
import com.vsnitko.oauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author v.snitko
 * @since 2022.10.15
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService, OAuth2AuthorizedClientService {

    private final UserRepository userRepository;
    private final UserBuilder userBuilder;

    @Override
    public User getById(final Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User edit(EditRequest editRequest, User principal) {
        final User userReference = userRepository.getReferenceById(principal.getId());
        userReference.setName(editRequest.getName());
        return userRepository.save(userReference);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new UserDetailsImpl(
                        user.getEmail(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority(user.getRole().name())),
                        user
                )).orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s email does not exist", email)));
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        OAuth2AuthenticationToken tokenPrincipal = (OAuth2AuthenticationToken) principal;
        User builtUser = userBuilder.buildUser(authorizedClient, tokenPrincipal);
        User user = userRepository.findByEmail(builtUser.getEmail())
                .orElseGet(() -> userRepository.save(builtUser));
        tokenPrincipal.setDetails(user);
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(final String clientRegistrationId, final String principalName) {
        //do nothing
        return null;
    }

    @Override
    public void removeAuthorizedClient(final String clientRegistrationId, final String principalName) {
        //do nothing
    }
}
