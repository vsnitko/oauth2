package com.vsnitko.oauth2.web;

import static com.vsnitko.oauth2.web.Constants.BASIC_AUTH_PATH;
import static com.vsnitko.oauth2.web.Constants.TOKEN_VERIFIED_QUERY_PARAM;
import static com.vsnitko.oauth2.web.Constants.VERIFICATION_REDIRECT_PATH;
import static com.vsnitko.oauth2.web.Constants.VERIFICATION_TOKEN_QUERY_PARAM;
import static com.vsnitko.oauth2.web.Constants.VERIFY_EMAIL_PATH;

import com.vsnitko.oauth2.config.AppProperties;
import com.vsnitko.oauth2.model.payload.SignInRequest;
import com.vsnitko.oauth2.model.payload.SignInResponse;
import com.vsnitko.oauth2.model.payload.SignUpRequest;
import com.vsnitko.oauth2.service.BasicAuthService;
import com.vsnitko.oauth2.service.MailService;
import jakarta.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author v.snitko
 * @since 2022.12.26
 */
@RestController
@RequestMapping(BASIC_AUTH_PATH)
@RequiredArgsConstructor
public class BasicAuthController {

    private final AppProperties appProperties;
    private final BasicAuthService basicAuthService;
    private final MailService mailService;

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest userDto) {
        return ResponseEntity.ok(basicAuthService.basicSignIn(userDto));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(basicAuthService.basicSignUp(signUpRequest));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException() {
        return ResponseEntity.badRequest().body("Bad credentials");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleEmailAlreadyExists() {
        return ResponseEntity.badRequest().body("User with this email already exists");
    }

    @GetMapping(VERIFY_EMAIL_PATH)
    public RedirectView verifyEmail(@RequestParam(VERIFICATION_TOKEN_QUERY_PARAM) String token) {
        final boolean verified = mailService.verify(token);
        final String redirectUrl = UriComponentsBuilder
            .fromUriString(appProperties.getClientPath())
            .pathSegment(VERIFICATION_REDIRECT_PATH)
            .queryParam(TOKEN_VERIFIED_QUERY_PARAM, verified)
            .build()
            .toUriString();
        return new RedirectView(redirectUrl);
    }
}
