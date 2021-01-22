/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.ScreenResponse;
import com.cpdss.gateway.domain.UserAuthorizationsResponse;
import com.cpdss.gateway.domain.UserResponse;
import com.cpdss.gateway.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controller for user related operations
 */
@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class UserController {

  @Autowired private UserService userService;

  private static final String CORRELATION_ID_HEADER = "correlationId";

  /**
   * Retrieves user permissions information
   *
   * @param id
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping("/user-authorizations")
  public UserAuthorizationsResponse getUserAuthorizations(@RequestHeader HttpHeaders headers)
      throws CommonRestException {
    UserAuthorizationsResponse response = null;
    try {
      response = userService.getUserPermissions(headers);
    } catch (Exception e) {
      log.error("Error in getUserAuthorizations ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
    return response;
  }

  @GetMapping("/screens/company/{companyId}/role/{roleId}")
  public ScreenResponse getScreens(
      @PathVariable Long companyId, @PathVariable Long roleId, @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    ScreenResponse response = null;
    try {
      log.info("getScreens: {}");
      response = userService.getScreens(companyId, roleId, CORRELATION_ID_HEADER);

    } catch (Exception e) {
      log.error("Error in getScreens ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
    return response;
  }

  @GetMapping("/company/{companyId}/users")
  public UserResponse getUsers(@PathVariable Long companyId, @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    UserResponse response = null;
    try {
      log.info("getScreens: {}");
      response = userService.getUsers(companyId, CORRELATION_ID_HEADER);

    } catch (Exception e) {
      log.error("Error in getScreens ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
    return response;
  }
}
