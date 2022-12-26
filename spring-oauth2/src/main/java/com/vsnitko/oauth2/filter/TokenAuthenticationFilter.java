package com.vsnitko.oauth2.filter;

import com.vsnitko.oauth2.service.TokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Class with filter that is executed on each HTTP request.
 * Checks weather request contains JWT token in "Authorization" header.
 * If header is not empty, token is validated.
 * If it's valid, sets authenticated used to Security Context.
 */
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private final TokenManager tokenManager;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(jwt) && tokenManager.validateToken(jwt)) {
      final Authentication authentication = tokenManager.getAuthentication(jwt);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }
}
