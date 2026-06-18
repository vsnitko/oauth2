package com.vsnitko.oauth2.service.impl;

import static com.vsnitko.oauth2.service.impl.MailServiceImpl.THYMELEAF_HTML_TEMPLATE_NAME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vsnitko.oauth2.config.AppProperties;
import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.entity.VerificationToken;
import com.vsnitko.oauth2.repository.VerificationTokenRepository;
import com.vsnitko.oauth2.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.sql.Date;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.mail.autoconfigure.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@ExtendWith(MockitoExtension.class)
class MailServiceImplTest {

    @Mock
    JavaMailSender mailSender;

    @Mock
    TemplateEngine templateEngine;

    @Mock
    VerificationTokenRepository tokenRepository;

    @Mock
    UserService userService;

    @Mock
    AppProperties appProperties;

    @Mock
    MailProperties mailProperties;

    @InjectMocks
    private MailServiceImpl mailService;

    @Test
    void sendVerificationEmail() {
        final User recipient = User.builder()
            .email("any")
            .build();
        when(appProperties.getServerPath()).thenReturn("http://localhost:8080/api");
        when(templateEngine.process(eq(THYMELEAF_HTML_TEMPLATE_NAME), any(Context.class)))
            .thenReturn("any");
        when(mailProperties.getUsername()).thenReturn("any");
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));

        mailService.sendVerificationEmail(recipient);

        verify(tokenRepository).save(any(VerificationToken.class));
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    void sendVerificationEmailExceptionally() throws MessagingException {
        final User recipient = User.builder()
            .email("any")
            .build();
        final MimeMessage mimeMessage = mock(MimeMessage.class);

        when(appProperties.getServerPath()).thenReturn("http://localhost:8080/api");
        when(templateEngine.process(eq(THYMELEAF_HTML_TEMPLATE_NAME), any(Context.class)))
            .thenReturn("any");
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(MessagingException.class).when(mimeMessage).setContent(any());

        assertThrows(RuntimeException.class, () -> mailService.sendVerificationEmail(recipient));
    }

    @Test
    void verifyEmailSuccess() {
        final String token = "accessToken";
        final User user = new User();
        final VerificationToken value = new VerificationToken()
            .setExpiryDate(new Date(Long.MAX_VALUE))
            .setUser(user);

        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(value));

        final boolean verified = mailService.verify(token);

        assertTrue(verified);
        assertTrue(user.getEmailVerified());
        verify(userService).save(user);
    }

    @Test
    void verifyEmailFailed_whenTokenIsEmpty() {
        final String token = "accessToken";

        when(tokenRepository.findByToken(token)).thenReturn(Optional.empty());

        final boolean verified = mailService.verify(token);

        assertFalse(verified);
        verify(userService, never()).save(any(User.class));
    }

    @Test
    void verifyEmailFailed_whenTokenExpired() {
        final String token = "accessToken";
        final VerificationToken value = new VerificationToken()
            .setExpiryDate(new Date(0));

        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(value));

        final boolean verified = mailService.verify(token);

        assertFalse(verified);
        verify(userService, never()).save(any(User.class));
    }
}
