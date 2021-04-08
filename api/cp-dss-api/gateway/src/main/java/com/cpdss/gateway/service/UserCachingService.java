/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.redis.CommonKeyValueStore;
import com.cpdss.gateway.domain.keycloak.KeycloakUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class for user caching functionalities
 *
 * @author sreekumar.k
 */
@Service
@Log4j2
public class UserCachingService extends CommonKeyValueStore<KeycloakUser> {
  @Autowired private KeycloakService keycloakService;

  /**
   * Method to get a specific user
   *
   * @param userId userId value
   * @return KeycloakUser User object
   */
  public KeycloakUser getUser(final String userId) throws GenericServiceException {
    //    Try to read from cache
    KeycloakUser user = this.getData(userId);
    log.debug("User data from cache: {}", user);

    if (null == user) {
      //    Get user data from Keycloak when user not found in cache
      user = keycloakService.getUser(userId);
      log.debug("User data from Keycloak: {}", user);
      this.storeData(user.getId(), user);
    }

    return user;
  }
}
