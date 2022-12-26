package com.vsnitko.oauth2.service;

import com.vsnitko.oauth2.model.payload.SignInRequest;
import com.vsnitko.oauth2.model.payload.SignInResponse;
import com.vsnitko.oauth2.model.payload.SignUpRequest;

/**
 * Service which is used for basic auth (using email and password)
 *
 * @author v.snitko
 * @since 2022.12.26
 */
public interface BasicAuthService {

  SignInResponse basicSignIn(SignInRequest signInRequest);

  SignInResponse basicSignUp(SignUpRequest signUpRequest);
}
