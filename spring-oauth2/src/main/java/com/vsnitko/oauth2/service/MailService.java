package com.vsnitko.oauth2.service;

import com.vsnitko.oauth2.model.entity.User;

/**
 * @author v.snitko
 * @since 2024.12.20
 */
public interface MailService {

    void sendVerificationEmail(User recipient);

    boolean verify(final String token);
}
