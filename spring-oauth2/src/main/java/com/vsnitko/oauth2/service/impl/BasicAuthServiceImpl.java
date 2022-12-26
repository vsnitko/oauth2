package com.vsnitko.oauth2.service.impl;

import static org.springframework.util.StringUtils.hasText;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.entity.UserDetailsImpl;
import com.vsnitko.oauth2.model.payload.SignInRequest;
import com.vsnitko.oauth2.model.payload.SignInResponse;
import com.vsnitko.oauth2.model.payload.SignUpRequest;
import com.vsnitko.oauth2.service.BasicAuthService;
import com.vsnitko.oauth2.service.TokenManager;
import com.vsnitko.oauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author v.snitko
 * @since 2022.10.15
 */
@Service
@RequiredArgsConstructor
public class BasicAuthServiceImpl implements BasicAuthService {

  public static final String DEFAULT_USERNAME = "Default Username";

  private final AuthenticationManager authenticationManager;
  private final TokenManager tokenManager;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public SignInResponse basicSignIn(final SignInRequest signInRequest) {
    final var emailPassword =
        new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());
    Authentication authentication = authenticationManager.authenticate(emailPassword);

    final UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
    final String token = tokenManager.createToken(principal.getUser());
    return new SignInResponse(token);
  }

  @Override
  public SignInResponse basicSignUp(final SignUpRequest signUpRequest) {
    final User user = User.builder()
        .name(hasText(signUpRequest.getName()) ? signUpRequest.getName() : DEFAULT_USERNAME)
        .email(signUpRequest.getEmail())
        .password(passwordEncoder.encode(signUpRequest.getPassword()))
        .build();
    userService.save(user);

    final String token = tokenManager.createToken(user);
    return new SignInResponse(token);
  }
}
