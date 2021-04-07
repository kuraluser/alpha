/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.keycloak.KeycloakUser;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Admin Notifications Response object */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminNotificationsResponse {
  private CommonSuccessResponse responseStatus;
  private Set<KeycloakUser> notifications;
}
