/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.keycloak;

import com.cpdss.common.utils.Doc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Keycloak User object
 *
 * @author sreekumar.k
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUser implements Doc {

  private String id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private Long userId;
}
