/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/** User object with user information */
@Data
@JsonInclude(Include.NON_EMPTY)
public class User {

  private Long id;

  private RolePermissions rolePermissions;

  @NotEmpty(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String username;

  private String firstName;

  private String lastName;

  private String designation;

  private String role;

  private Long roleId;

  private Boolean isLoginSuspended;

  private Boolean defaultUser;

  private long statusCode;

  private String statusValue;

  private Integer rejectionCount;

  private String keycloakId;

  private Integer vesselStatusCode;

  private String vesselStatus;
}
