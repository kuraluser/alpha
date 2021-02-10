/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RolePermission {
  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private List<ScreenInfo> screens;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long roleId;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private List<Long> userId;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Role role;

  private List<Long> deselectedUserId;
}
