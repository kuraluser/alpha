/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.security.ship;

import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV;
import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV_SHIP;
import static com.cpdss.gateway.security.ship.ShipJwtService.USER_ID_CLAIM;

import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Class implementing Jwt Authentication Provider
 *
 * @author suhail.k
 */
@Component
@ConditionalOnProperty(name = CPDSS_BUILD_ENV, havingValue = CPDSS_BUILD_ENV_SHIP)
public class ShipAuthenticationProvider implements AuthenticationProvider {

  private static final Logger logger = LogManager.getLogger(ShipAuthenticationProvider.class);

  @Autowired private ShipJwtService jwtUtil;

  @Autowired private UsersRepository usersRepository;

  /** Verify the token and user */
  @Override
  public Authentication authenticate(final Authentication authentication) {
    logger.debug("Inside JwtAuthenticationProvider");
    final String token = (String) authentication.getCredentials();
    logger.debug("Authentication token: {}", token);
    try {
      Jws<Claims> claims = this.jwtUtil.parseClaims(token);
      Long userId = Long.parseLong(claims.getBody().get(USER_ID_CLAIM).toString());
      Users user = this.usersRepository.findByIdAndIsActive(userId, true);
      if (null == user) {
        throw new AuthenticationServiceException("User does not exist");
      }
      if (null != user.getLoginSuspended() && user.getLoginSuspended()) {
        throw new AuthenticationServiceException("Login was suspended for the user");
      }
      final ShipUserContext context = ShipUserContext.create(userId, new ArrayList<>());
      return new ShipAuthenticationToken(context, context.getAuthorities());
    } catch (AuthenticationServiceException ase) {
      throw new AuthenticationServiceException("Caught AuthenticationServiceException", ase);
    } catch (final Exception e) {
      throw new AuthenticationServiceException("Authentication failed", e);
    }
  }

  /** {@link ShipAuthenticationProvider} will only support {@link ShipAuthenticationToken} */
  @Override
  public boolean supports(final Class<?> authentication) {
    return (ShipAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
