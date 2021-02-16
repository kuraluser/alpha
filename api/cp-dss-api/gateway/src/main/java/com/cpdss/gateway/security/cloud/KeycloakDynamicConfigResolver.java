/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.cloud;

import lombok.extern.log4j.Log4j2;
import org.keycloak.TokenVerifier;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;

/**
 * Dynamic configuration resolver for keycloak
 *
 * @author suhail.k
 */
@Configuration
@Primary
@Log4j2
public class KeycloakDynamicConfigResolver implements KeycloakConfigResolver {

  private KeycloakDeployment keycloakDeployment;

  private static final String DUMMY_REALM = "DUMMY_REALM";
  private static final String DUMMY_CLIENT = "DUMMY_REALM";

  private static final String CPDSS_KEYCLOAK_CLIENT = "cpdss_api";
  public static final String SHORE_API_PREFIX = "/api/cloud/";
  public static final String SHIP_API_PREFIX = "/api/ship/";
  public static final String AUTHORIZATION_HEADER = "authorization";

  @Autowired private AdapterConfig adapterConfig;

  /**
   * Extract realm information from keycloak token. Keycloak token has url of token issuing realm in
   * the format: https://keycloak-base_url/auth/realms/realm_name. So "realm_name" will be extracted
   * from here and will be used for keycloak configuration
   */
  @Override
  public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
    // dummy data for preflight requests
    if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
      log.info("Preflight request arrived");
      this.adapterConfig.setRealm(DUMMY_REALM);
      this.adapterConfig.setResource(DUMMY_CLIENT);
    }
    if (null == request.getHeader(AUTHORIZATION_HEADER)) {
      log.error("KeycloakDynamicConfigResolver: invalid authorization header");
      // dummy data for carrying out the error processing to keycloak library filters
      this.adapterConfig.setRealm(DUMMY_REALM);
      this.adapterConfig.setResource(DUMMY_CLIENT);
    } else {
      try {
        AccessToken token = parseKeycloakToken(request.getHeader(AUTHORIZATION_HEADER));
        if (null != token) {
          this.adapterConfig.setRealm(extractRealm(token));
          this.adapterConfig.setResource(CPDSS_KEYCLOAK_CLIENT);
        } else {
          this.adapterConfig.setRealm(DUMMY_REALM);
          this.adapterConfig.setResource(CPDSS_KEYCLOAK_CLIENT);
        }
      } catch (VerificationException e) {
        log.error("KeycloakDynamicConfigResolver: Token verification exception", e);
        return null;
      }
    }
    keycloakDeployment = KeycloakDeploymentBuilder.build(adapterConfig);
    return keycloakDeployment;
  }

  void setAdapterConfig(AdapterConfig adapterConfig) {
    this.adapterConfig = adapterConfig;
  }

  /**
   * Extract realm information from keycloak token. Keycloak token has url of token issuing realm in
   * the format: https://keycloak-base_url/auth/realms/realm_name. So "realm_name" will be extracted
   * from here and will be used for keycloak configuration
   *
   * @param token {@link AccessToken} - The keycloak token instance
   * @return String - the realm name
   */
  public String extractRealm(AccessToken token) {
    if (token.getIssuer() != null) {
      String[] segments = token.getIssuer().split("/");
      return segments[segments.length - 1];
    }
    return DUMMY_REALM;
  }

  /**
   * Parse keycloak token from authorization header
   *
   * @param token - The token in string format
   * @return {@link AccessToken} - The keycloak access token instance
   * @throws VerificationException
   */
  public AccessToken parseKeycloakToken(String token) throws VerificationException {
    return TokenVerifier.create(token.replace("Bearer ", ""), AccessToken.class).getToken();
  }
}
