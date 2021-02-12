/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
  private List<Role> roles;
  private List<Role> users;
  private CommonSuccessResponse responseStatus;
  private Long roleId;
  private String message;
  private long totalElements;
}
