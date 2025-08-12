package com.vsnitko.oauth2.service.impl;

import static org.springframework.util.StringUtils.hasText;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.entity.UserDetailsImpl;
import com.vsnitko.oauth2.model.payload.SignInRequest;
import com.vsnitko.oauth2.model.payload.AuthResponse;
import com.vsnitko.oauth2.model.payload.SignUpRequest;
import com.vsnitko.oauth2.model.payload.UserResponse;
import com.vsnitko.oauth2.service.MailService;
import com.vsnitko.oauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
public class AuthService {

    public static final String DEFAULT_USERNAME = "Default Username";

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

//    public AuthResponse basicSignIn(SignInRequest signInRequest) {
//        final var emailPassword =
//            new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());
//        final Authentication authentication = authenticationManager.authenticate(emailPassword);
//
//        final UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
//        final String accessToken = tokenManager.createAccessToken(principal.getUser());
//        final String refreshToken = tokenManager.createRefreshToken(principal.getUser());
//        final UserResponse userResponse = UserResponse.builder()
//                .id(principal.getUser().getId())
//                .email(principal.getUser().getEmail())
//                .username(principal.getUser().getName())
//                .avatar(principal.getUser().getAvatar())
//                .build();
//        return new AuthResponse(accessToken, refreshToken, userResponse);
//    }
//
//    public AuthResponse basicSignUp(SignUpRequest signUpRequest) {
//        final User user = User.builder()
//            .name(hasText(signUpRequest.getUsername()) ? signUpRequest.getUsername() : DEFAULT_USERNAME)
//            .email(signUpRequest.getEmail())
//            .password(passwordEncoder.encode(signUpRequest.getPassword()))
//            .build();
//        userService.save(user);
//        mailService.sendVerificationEmail(user);
//
//        final String accessToken = tokenManager.createAccessToken(user);
//        final String refreshToken = tokenManager.createRefreshToken(user);
//        final UserResponse userResponse = UserResponse.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .username(user.getName())
//                .avatar(user.getAvatar())
//                .build();
//        return new AuthResponse(accessToken, refreshToken, userResponse);
//    }
    
    public HttpHeaders addRefreshTokenCookie(String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refresh-token", refreshToken)
                .httpOnly(true)
//                .secure(true)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return headers;
    }
}
