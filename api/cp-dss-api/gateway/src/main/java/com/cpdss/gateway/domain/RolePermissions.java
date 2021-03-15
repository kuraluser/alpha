/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

@Data
public class RolePermissions {
  private Long id;

  private String role;

  private List<Resource> resources;
}
