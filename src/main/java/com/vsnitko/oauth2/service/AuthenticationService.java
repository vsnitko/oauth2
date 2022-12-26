package com.vsnitko.oauth2.service;

import com.vsnitko.oauth2.model.payload.SignInRequest;
import com.vsnitko.oauth2.model.payload.SignInResponse;
import com.vsnitko.oauth2.model.payload.SignUpRequest;

/**
 * @author v.snitko
 * @since 2022.12.26
 */
public interface AuthenticationService {

    SignInResponse basicSignIn(final SignInRequest signInRequest);

    SignInResponse basicSignUp(final SignUpRequest signUpRequest);
}
