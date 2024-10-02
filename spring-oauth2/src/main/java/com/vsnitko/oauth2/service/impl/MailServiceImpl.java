package com.vsnitko.oauth2.service.impl;

import static com.vsnitko.oauth2.web.Constants.BASIC_AUTH_PATH;
import static com.vsnitko.oauth2.web.Constants.VERIFICATION_TOKEN_QUERY_PARAM;
import static com.vsnitko.oauth2.web.Constants.VERIFY_EMAIL_PATH;

import com.vsnitko.oauth2.config.AppProperties;
import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.entity.VerificationToken;
import com.vsnitko.oauth2.repository.VerificationTokenRepository;
import com.vsnitko.oauth2.service.MailService;
import com.vsnitko.oauth2.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.sql.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author v.snitko
 * @since 2024.12.20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    public static final String EMAIL_VERIFICATION_SUBJECT = "Email Verification";
    public static final String THYMELEAF_HTML_TEMPLATE_NAME = "email-verification-template";
    public static final String THYMELEAF_VERIFICATION_LINK_VARIABLE = "emailVerificationLink";

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final VerificationTokenRepository tokenRepository;
    private final UserService userService;
    private final AppProperties appProperties;
    private final MailProperties mailProperties;

    @Override
    public void sendVerificationEmail(final User recipient) {
        final VerificationToken verificationToken = createVerificationToken(recipient);
        tokenRepository.save(verificationToken);
        final String verificationUrl = UriComponentsBuilder
            .fromUriString(appProperties.getServerPath())
            .pathSegment(BASIC_AUTH_PATH, VERIFY_EMAIL_PATH)
            .queryParam(VERIFICATION_TOKEN_QUERY_PARAM, verificationToken.getToken())
            .build()
            .toUriString();

        final Context context = new Context();
        context.setVariable(THYMELEAF_VERIFICATION_LINK_VARIABLE, verificationUrl);
        final String htmlContent = templateEngine.process(THYMELEAF_HTML_TEMPLATE_NAME, context);
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(recipient.getEmail());
            helper.setSubject(EMAIL_VERIFICATION_SUBJECT);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MailAuthenticationException e) {
            log.warn("Email authentication failed, check that you specified admin username and password properly."
                     + "Message won't be sent", e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private VerificationToken createVerificationToken(User user) {
        final String token = UUID.randomUUID().toString();
        final VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        return verificationToken;

    }

    @Override
    public boolean verify(final String token) {
        final Optional<VerificationToken> verificationToken = tokenRepository.findByToken(token);
        if (verificationToken.isEmpty()
            || verificationToken.get().getExpiryDate().before(new Date(System.currentTimeMillis()))) {
            return false;
        }

        final User user = verificationToken.get().getUser();
        user.setEmailVerified(true);
        userService.save(user);
        return true;
    }
}
