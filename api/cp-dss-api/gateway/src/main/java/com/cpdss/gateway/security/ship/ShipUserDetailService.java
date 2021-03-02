/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.ship;

import com.cpdss.common.exception.PasswordExpireException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.UsersRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Ship user detail service
 *
 * @author suhail.k
 */
@Component
public class ShipUserDetailService implements UserDetailsService {

  @Autowired private UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Users user = this.usersRepository.findByUsernameAndIsActive(username, true);
    if (null != user) {
      if (null == user.getUserPassword()) {
        throw new AuthenticationCredentialsNotFoundException("Password not set for the user");
      } else if (null != user.getLoginSuspended() && user.getLoginSuspended()) {
        throw new AuthenticationServiceException("Login was suspended for the user");
      }
      validatePasswordExpiry(user);
      return new User(username, user.getUserPassword(), new ArrayList<>());
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }

  private void validatePasswordExpiry(Users users) throws PasswordExpireException {
    if (users.getPasswordExpiryDate() != null
        && LocalDateTime.now().isAfter(users.getPasswordExpiryDate())) {
      throw new PasswordExpireException(
          "Password expired on " + users.getPasswordExpiryDate(),
          CommonErrorCodes.E_CPDSS_PASSWORD_EXPIRED,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }
}
