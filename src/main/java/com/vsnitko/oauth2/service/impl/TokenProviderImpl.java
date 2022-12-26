package com.vsnitko.oauth2.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vsnitko.oauth2.config.JwtProperties;
import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.service.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author v.snitko
 * @since 2022.12.17
 */
@Service
@RequiredArgsConstructor
public class TokenProviderImpl implements TokenProvider {

    private final UserServiceImpl userService;
    private final JwtProperties jwtProperties;

    @Override
    public String createToken(User user) {
        final Date now = new Date();
        final Date expired = new Date(now.getTime() + jwtProperties.getExpirationMinutes() * 60 * 1000);
        final Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());

        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withClaim("name", user.getName())
                .withClaim("avatar", user.getAvatar())
                .withIssuedAt(now)
                .withExpiresAt(expired)
                .sign(algorithm);
    }

    @Override
    public boolean validateToken(String authToken) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());
            JWT.require(algorithm)
                    .build()
                    .verify(authToken);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        User user = userService.getById(Long.parseLong(JWT.decode(token).getSubject()));
        return new UsernamePasswordAuthenticationToken(user, null);
    }
}
