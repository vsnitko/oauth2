package com.vsnitko.oauth2.web;

import com.vsnitko.oauth2.model.payload.SignInRequest;
import com.vsnitko.oauth2.model.payload.SignInResponse;
import com.vsnitko.oauth2.model.payload.SignUpRequest;
import com.vsnitko.oauth2.service.BasicAuthService;
import jakarta.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v.snitko
 * @since 2022.12.26
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class BasicAuthController {

  private final BasicAuthService basicAuthService;

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
}
