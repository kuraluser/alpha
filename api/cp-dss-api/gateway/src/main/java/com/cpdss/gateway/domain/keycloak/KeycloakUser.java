/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.keycloak;

import com.cpdss.common.utils.Doc;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for Keycloak User object
 *
 * @author sreekumar.k
 */
@Data
@AllArgsConstructor
public class KeycloakUser implements Doc, Serializable {
  private static final long serialVersionUID = -1038061200893090358L;

  private String id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
}
