/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.ship;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Filter class for validating the JWT token
 *
 * @author suhail.k
 */
public class ShipAuthorizationFilter extends AbstractAuthenticationProcessingFilter {

  private static final Logger jwtAuthLogger = LogManager.getLogger(ShipAuthorizationFilter.class);

  private final AuthenticationFailureHandler failureHandler;
  private final ShipTokenExtractor tokenExtractor;

  public ShipAuthorizationFilter(
      AuthenticationFailureHandler failureHandler,
      ShipTokenExtractor tokenExtractor,
      RequestMatcher matcher) {
    super(matcher);
    jwtAuthLogger.trace("In JwtAuthorizationFilter constructor");
    this.failureHandler = failureHandler;
    this.tokenExtractor = tokenExtractor;
  }

  /** Attempt for authentication, validate the token */
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Optional<String> tokenStrOpt = tokenExtractor.extract(request);
      if (tokenStrOpt.isPresent()) {
        String token = tokenStrOpt.get();
        return getAuthenticationManager().authenticate(new ShipAuthenticationToken(token));
      } else {
        throw new AuthenticationServiceException("Invalid authorization header");
      }
    } catch (Exception e) {
      jwtAuthLogger.error(e.getMessage(), e);
      throw new AuthenticationServiceException(e.getMessage(), e);
    }
  }

  /** On successfull authentication */
  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);
    chain.doFilter(request, response);
  }

  /** On authentication failure */
  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    SecurityContextHolder.clearContext();
    failureHandler.onAuthenticationFailure(request, response, failed);
  }
}
