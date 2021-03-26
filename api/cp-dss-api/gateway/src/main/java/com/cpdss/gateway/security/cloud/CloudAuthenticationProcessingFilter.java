/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.security.cloud;

import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * Authentication processing filter for cloud
 *
 * @author suhail.k
 */
public class CloudAuthenticationProcessingFilter extends KeycloakAuthenticationProcessingFilter {

  /**
   * This method sets {@link CloudAuthenticationFailureHandler} as the auth failure handler for
   * cloud
   *
   * @param authenticationManager
   */
  public CloudAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
    setAuthenticationFailureHandler(new CloudAuthenticationFailureHandler());
  }
}
