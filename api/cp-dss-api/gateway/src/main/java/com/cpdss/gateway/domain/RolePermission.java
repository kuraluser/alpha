/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RolePermission {
  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long screenId;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long roleId;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long userId;

  private Boolean canAdd;
  private Boolean canEdit;
  private Boolean canDelete;
  private Boolean canView;

  private String roleName;
  private String roleDescription;
}
