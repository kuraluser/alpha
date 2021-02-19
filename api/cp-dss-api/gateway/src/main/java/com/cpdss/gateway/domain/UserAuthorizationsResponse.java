/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/** Response class for User Authorizations */
@Data
@JsonInclude(Include.NON_EMPTY)
public class UserAuthorizationsResponse {

  private CommonSuccessResponse responseStatus;

  private User user;

  private RolePermissions rolePermissions;
}
