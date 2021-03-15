/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.cloud;

import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationFailureHandler;
import org.keycloak.adapters.springsecurity.authentication.KeycloakCookieBasedRedirect;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

/**
 * Overrides the Keycloak default authentication failure handler to return customized error response
 *
 * @author suhail.k
 */
@Log4j2
public class CloudAuthenticationFailureHandler extends KeycloakAuthenticationFailureHandler {

  private static final String CORRELATION_ID_HEADER = "correlationId";

  /**
   * On authentication failure, {@link CommonErrorResponse} with appropriate error code will be
   * returned
   */
  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    // Check that the response was not committed yet (this may happen when another
    // part of the Keycloak adapter sends a challenge or a redirect).
    if (!response.isCommitted()) {
      if (KeycloakCookieBasedRedirect.getRedirectUrlFromCookie(request) != null) {
        response.addCookie(KeycloakCookieBasedRedirect.createCookieFromRedirectUrl(null));
      }
      final CommonErrorResponse errorResponse =
          new CommonErrorResponse(
              String.valueOf(HttpStatus.UNAUTHORIZED.value()),
              CommonErrorCodes.E_HTTP_INVALID_TOKEN,
              request.getHeader(CORRELATION_ID_HEADER));
      log.error("Error validating the JWT token : {}", errorResponse, exception);
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      try {
        final ObjectMapper objMapper = new ObjectMapper();
        final String jsonStr = objMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonStr);
        log.debug("HttpServletResponse status is : {}", response.getStatus());
      } catch (final IOException ioe) {
        log.fatal("IO exception on writing the error to response", ioe);
      }
    } else {
      if (200 <= response.getStatus() && response.getStatus() < 300) {
        throw new RuntimeException(
            "Success response was committed while authentication failed!", exception);
      }
    }
  }
}
