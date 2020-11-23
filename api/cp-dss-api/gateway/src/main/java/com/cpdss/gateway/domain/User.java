/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** User object with user information */
@Data
public class User {
  private Long id;
  private RolePermissions rolePermissions;
}
