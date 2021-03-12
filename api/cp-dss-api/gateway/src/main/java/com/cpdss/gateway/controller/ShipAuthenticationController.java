/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV;
import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV_SHIP;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.ShipLoginRequest;
import com.cpdss.gateway.domain.ShipLoginResponse;
import com.cpdss.gateway.service.UserService;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for Ship side authentication
 *
 * @author suhail.k
 */
@RestController
@Validated
@Log4j2
@ConditionalOnProperty(name = CPDSS_BUILD_ENV, havingValue = CPDSS_BUILD_ENV_SHIP)
public class ShipAuthenticationController {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserService userService;

  private static final String CORRELATION_ID_HEADER = "correlationId";

  /**
   * Ship side authentication handler
   *
   * @param request
   * @return
   * @throws Exception
   */
  @PostMapping(value = "/api/ship/authenticate")
  public ShipLoginResponse authenticate(
      @RequestHeader HttpHeaders headers, @RequestBody @Valid ShipLoginRequest request)
      throws CommonRestException {
    try {
      this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
      return this.userService.generateShipUserToken(
          request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (AuthenticationException ae) {
      log.error("Authentication exception", ae);
      log.info("Updating the last login attemt date");
      this.userService.updateLastLoginAttemptedDateForShipUser(request);
      throw new CommonRestException(
          CommonErrorCodes.E_HTTP_UNAUTHORIZED_RQST,
          headers,
          HttpStatusCode.UNAUTHORIZED,
          "Authentication exception occurred",
          ae);
    } catch (GenericServiceException e) {
      this.userService.updateLastLoginAttemptedDateForShipUser(request);
      log.info("Password expire user - {}, message - {}", request.getUsername(), e.getMessage());
      throw new CommonRestException(
          CommonErrorCodes.E_CPDSS_PASSWORD_EXPIRED,
          headers,
          HttpStatusCode.UNAUTHORIZED,
          "Authentication exception occurred",
          e);
    } catch (Exception e) {
      log.error("Exception occurred", e);
      throw new CommonRestException(
          CommonErrorCodes.E_HTTP_UNAUTHORIZED_RQST,
          headers,
          HttpStatusCode.UNAUTHORIZED,
          "Exception occurred",
          e);
    }
  }
}
