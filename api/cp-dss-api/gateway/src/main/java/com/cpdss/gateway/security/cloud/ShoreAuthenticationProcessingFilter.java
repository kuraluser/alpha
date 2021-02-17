/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.cloud;

import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.springframework.security.authentication.AuthenticationManager;

public class ShoreAuthenticationProcessingFilter extends KeycloakAuthenticationProcessingFilter {

  public ShoreAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
    setAuthenticationFailureHandler(new ShoreAuthenticationFailureHandler());
  }
}
