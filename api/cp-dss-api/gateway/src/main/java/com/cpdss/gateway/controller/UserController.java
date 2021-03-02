/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.service.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    } catch (GenericServiceException e) {
      log.error("GenericServiceException in save voyage", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
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
    try {
      log.info("getUsers: {}");
      return this.userService.getUsers(headers.getFirst(CORRELATION_ID_HEADER));
    } catch (Exception e) {
      log.error("Error in getScreens ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Get roles API
   *
   * @param headers header values
   * @param pageSize pageSize value. Defaults to 10
   * @param page page number value. Defaults to 0
   * @param sortBy Sort column name. Default column - id
   * @param orderBy Sort order. Default to ascending order
   * @param params All request params (filter params)
   * @return RoleResponse response object
   * @throws CommonRestException Exception object
   */
  @PostMapping("/users/{userId}")
  public UserResponse saveUser(
      @PathVariable Long userId,
      @RequestBody @Valid User request,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("getUsers: {}");
      request.setId(userId);
      return this.userService.saveUser(request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("Error in saveUser ", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in saveUser ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @GetMapping("/roles")
  public RoleResponse getRoles(
      @RequestHeader HttpHeaders headers,
      @RequestParam(required = false, defaultValue = "10") int pageSize,
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "id") String sortBy,
      @RequestParam(required = false, defaultValue = "desc") String orderBy,
      @RequestParam Map<String, String> params)
      throws CommonRestException {
    RoleResponse response = null;
    try {
      log.info("getScreens: {}");
      Long companyId = 1L;

      //      Get filters
      List<String> filterKeys = Arrays.asList("id", "name", "description", "companyXId");
      Map<String, String> filterParams =
          params.entrySet().stream()
              .filter(e -> filterKeys.contains(e.getKey()))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

      response =
          userService.getRoles(
              companyId, CORRELATION_ID_HEADER, filterParams, page, pageSize, sortBy, orderBy);

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
  public PermissionResponse savePermission(
      @RequestBody @Valid RolePermission permission, @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    PermissionResponse permissionResponse = new PermissionResponse();

    try {
      log.info("save permission API. CorrelationId: {}", headers.getFirst(CORRELATION_ID_HEADER));
      Long companyId = 1L;
      permissionResponse =
          userService.savePermission(
              permission, companyId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in save voyage", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in save permission api", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
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
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in save voyage", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
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

  /**
   * Admin Role required to access this API
   *
   * @param request ResetPasswordRequest {userid, password}
   * @return ResetPasswordResponse
   */
  @PostMapping("/users/reset-password")
  public ResponseEntity<ResetPasswordResponse> resetPassword(
          @RequestBody ResetPasswordRequest request,
          @RequestHeader HttpHeaders headers
  ) throws CommonRestException {
    ResetPasswordResponse response = new ResetPasswordResponse();
    try {
      boolean var1 = userService.resetPassword(request.getPassword(), request.getUserId());
      response.setResponseStatus(new CommonSuccessResponse(
              "200",
              headers.getFirst(CORRELATION_ID_HEADER)
      ));
    } catch (GenericServiceException e) {
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      throw new CommonRestException(
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              headers,
              HttpStatusCode.INTERNAL_SERVER_ERROR,
              e.getMessage(),
              e);
    }
    return ResponseEntity.ok(response);
  }

}
