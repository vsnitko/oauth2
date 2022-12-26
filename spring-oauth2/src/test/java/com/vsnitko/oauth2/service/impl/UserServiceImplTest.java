package com.vsnitko.oauth2.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.payload.EditRequest;
import com.vsnitko.oauth2.repository.UserRepository;
import com.vsnitko.oauth2.service.UserBuilder;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  UserRepository userRepository;

  @Mock
  UserBuilder userBuilder;

  @InjectMocks
  UserServiceImpl userService;

  @Test
  void getById_returnsUser_whenUserExists() {
    Long id = 1L;
    User expectedUser = new User();
    when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

    User result = userService.getById(id);

    assertEquals(expectedUser, result);
  }

  @Test
  void getById_returnsNull_whenUserDoesNotExist() {
    Long id = 1L;
    when(userRepository.findById(id)).thenReturn(Optional.empty());

    User result = userService.getById(id);

    assertNull(result);
  }

  @Test
  void save() {
    final User user = new User();

    userService.save(user);

    verify(userRepository, times(1)).save(user);
  }

  @Captor
  ArgumentCaptor<User> userCaptor;

  @Test
  void edit() {
    final String newName = "New Name";
    EditRequest editRequest = new EditRequest().setName(newName);
    User principal = new User().setId(1L);

    User userReference = new User();
    when(userRepository.getReferenceById(principal.getId())).thenReturn(userReference);
    when(userRepository.save(userReference)).thenReturn(userReference);

    User result = userService.edit(editRequest, principal);

    verify(userRepository, times(1)).getReferenceById(principal.getId());
    verify(userRepository, times(1)).save(userCaptor.capture());

    assertEquals(newName, userCaptor.getValue().getName());
    assertEquals(editRequest.getName(), result.getName());
  }

  @Test
  void loadUserByUsername_returnsUserDetails_whenUserExists() {
    String email = "test@example.com";
    User user = new User().setEmail(email);
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

    UserDetails result = userService.loadUserByUsername(email);

    assertEquals(user.getEmail(), result.getUsername());
    verify(userRepository, times(1)).findByEmail(email);
  }

  @Test
  void loadUserByUsername_throwsException_whenUserDoesNotExist() {
    String email = "nonexistent@example.com";
    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
    verify(userRepository, times(1)).findByEmail(email);
  }

  @Test
  void saveAuthorizedClient_savesNewUser_whenUserDoesNotExist() {
    var authorizedClient = mock(OAuth2AuthorizedClient.class);
    var principal = mock(OAuth2AuthenticationToken.class);

    User builtUser = new User();
    when(userBuilder.buildUser(authorizedClient, principal)).thenReturn(builtUser);
    when(userRepository.findByEmail(builtUser.getEmail())).thenReturn(Optional.empty());
    when(userRepository.save(builtUser)).thenReturn(builtUser);

    userService.saveAuthorizedClient(authorizedClient, principal);

    verify(userBuilder).buildUser(authorizedClient, principal);
    verify(userRepository).findByEmail(builtUser.getEmail());
    verify(userRepository).save(builtUser);
  }

  @Test
  void saveAuthorizedClient_doesNotSaveNewUser_whenUserExists() {
    var authorizedClient = mock(OAuth2AuthorizedClient.class);
    var principal = mock(OAuth2AuthenticationToken.class);

    User builtUser = new User();
    when(userBuilder.buildUser(authorizedClient, principal)).thenReturn(builtUser);
    when(userRepository.findByEmail(builtUser.getEmail())).thenReturn(Optional.of(builtUser));

    userService.saveAuthorizedClient(authorizedClient, principal);

    verify(userBuilder, times(1)).buildUser(authorizedClient, principal);
    verify(userRepository, times(0)).save(any());
  }

  @Test
  void loadAuthorizedClient() {
    assertNull(userService.loadAuthorizedClient("any", "any"));
  }

  @Test
  void removeAuthorizedClient() {
    userService.removeAuthorizedClient("any", "any");
  }
}
