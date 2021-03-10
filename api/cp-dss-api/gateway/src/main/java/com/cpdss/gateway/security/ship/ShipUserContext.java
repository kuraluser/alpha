/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.ship;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * User context class
 *
 * @author suhail.k
 */
@Getter
@Setter
@AllArgsConstructor
public class ShipUserContext implements Serializable {

  private static final long serialVersionUID = 7838425285125292587L;
  private final Long userId;
  private final List<GrantedAuthority> authorities;

  public static ShipUserContext create(
      final Long userId, final List<GrantedAuthority> authorities) {
    return new ShipUserContext(userId, authorities);
  }
}
