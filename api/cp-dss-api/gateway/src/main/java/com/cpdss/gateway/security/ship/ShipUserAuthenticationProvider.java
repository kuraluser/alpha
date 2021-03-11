/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.ship;

import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV;
import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV_SHIP;

import java.util.Collections;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Ship user authenticator
 *
 * @author suhail.k
 */
@Component
@ConditionalOnProperty(name = CPDSS_BUILD_ENV, havingValue = CPDSS_BUILD_ENV_SHIP)
public class ShipUserAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) {
    return new UsernamePasswordAuthenticationToken(
        authentication.getName(),
        authentication.getCredentials().toString(),
        Collections.emptyList());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isInstance(UsernamePasswordAuthenticationToken.class);
  }
}
