/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/** User object with user information */
@Data
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
}
