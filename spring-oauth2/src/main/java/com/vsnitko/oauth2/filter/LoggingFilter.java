package com.vsnitko.oauth2.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * This class includes two filters:
 * <ul>
 *     <li>Bean filter for HTTP requests</li>
 *     <li>Aspect filter for each call of service method</li>
 * </ul>
 *
 * @author v.snitko
 * @since 2023.01.31
 */
@Slf4j
@Aspect
@Component
public class LoggingFilter {

  /**
   * Logging filter for HTTP requests
   */
  @Bean
  public CommonsRequestLoggingFilter logFilter() {
    final CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter() {
      @Override
      public void afterRequest(HttpServletRequest request, String message) {
        // As CommonsRequestLoggingFilter handles 2 logs ('before' and 'after' request),
        // I need only 'before' request log, that's why I leave this body empty
      }
    };
    filter.setBeforeMessagePrefix("Received request [");
    return filter;

  }

  /**
   * Pointcut for each class located at com/vsnitko/oauth2/service/impl
   */
  @Pointcut("within(com.vsnitko.oauth2.service.impl..*)")
  public void pointcut() {
  }

  /**
   * This method will be executed before each public method for each class located at com/vsnitko/oauth2/service/impl
   * Note that this pointcut won't work for methods that are not declared in interface
   */
  @Before("pointcut()")
  public void logMethod(JoinPoint joinPoint) {
    final String methodSignature = joinPoint.getSignature().toShortString();
    log.debug("Started processing '{}'", methodSignature);
  }

  /**
   * This method will be executed after each public method for each class located at com/vsnitko/oauth2/service/impl
   * Note that this pointcut won't work for methods that are not declared in interface
   */
  @AfterReturning(pointcut = "pointcut()")
  public void logMethodAfter(JoinPoint joinPoint) {
    final String methodSignature = joinPoint.getSignature().toShortString();
    log.debug("Finished processing '{}'", methodSignature);
  }
}
