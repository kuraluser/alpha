/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.ship;

import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * Failure handler for JWT authentication failure
 *
 * @author suhail.k
 */
@Component
@Log4j2
public class ShipAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final AuthenticationException exception)
      throws IOException, ServletException {
    final CommonErrorResponse errorResponse =
        new CommonErrorResponse(
            String.valueOf(HttpStatus.UNAUTHORIZED.value()),
            CommonErrorCodes.E_HTTP_UNAUTHORIZED_RQST,
            null);
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
  }
}
