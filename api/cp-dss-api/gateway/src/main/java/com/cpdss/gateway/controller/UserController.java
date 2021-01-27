/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.PermissionResponse;
import com.cpdss.gateway.domain.Role;
import com.cpdss.gateway.domain.RolePermission;
import com.cpdss.gateway.domain.RoleResponse;
import com.cpdss.gateway.domain.ScreenResponse;
import com.cpdss.gateway.domain.UserAuthorizationsResponse;
import com.cpdss.gateway.domain.UserResponse;
import com.cpdss.gateway.service.UserService;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/screens/role/{roleId}")
  public ScreenResponse getScreens(@PathVariable Long roleId, @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    ScreenResponse response = null;
    try {
      log.info("getScreens: {}");
      Long companyId = 1L;
      response = userService.getScreens(companyId, roleId, CORRELATION_ID_HEADER);

    } catch (Exception e) {
      log.error("Error in getRoles ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
    return response;
  }

  @GetMapping("/users")
  public UserResponse getUsers(@RequestHeader HttpHeaders headers) throws CommonRestException {
    UserResponse response = null;
    try {
      log.info("getScreens: {}");
      Long companyId = 1L;
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

	@PostMapping("/user/role/permission")
	public PermissionResponse savePermission(@RequestBody @Valid RolePermission permission,
			@RequestHeader HttpHeaders headers) throws CommonRestException {
		PermissionResponse permissionResponse = new PermissionResponse();

		try {
			log.info("save permission API. correlationId: {}", headers.getFirst(CORRELATION_ID_HEADER));
			Long companyId = 1L;
			permissionResponse = userService.savePermission(permission, companyId,
					headers.getFirst(CORRELATION_ID_HEADER));
		} catch (GenericServiceException e) {
			log.error("GenericServiceException in save voyage", e);
			throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
		} catch (Exception e) {
			log.error("Error in save permission ", e);
			throw new CommonRestException(CommonErrorCodes.E_GEN_INTERNAL_ERR, headers,
					HttpStatusCode.SERVICE_UNAVAILABLE, e.getMessage(), e);
		}
		return permissionResponse;
	}

  @PostMapping("/user/role")
  public RoleResponse saveRole(@RequestBody @Valid Role role, @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    RoleResponse roleResponse = new RoleResponse();

    try {
      log.info("save permission API. correlationId: {}", headers.getFirst(CORRELATION_ID_HEADER));
      Long companyId = 1L;
      roleResponse = userService.saveRole(role, companyId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (Exception e) {
      log.error("Error in save permission ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
    return roleResponse;
  }

  @DeleteMapping("/user/role/{roleId}")
  public RoleResponse deleteRole(@PathVariable Long roleId, @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    RoleResponse roleResponse = new RoleResponse();

    try {
      log.info("save permission API. correlationId: {}", headers.getFirst(CORRELATION_ID_HEADER));
      Long companyId = 1L;
      roleResponse =
          userService.deleteRole(roleId, companyId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (Exception e) {
      log.error("Error in save permission ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
    return roleResponse;
  }
}
