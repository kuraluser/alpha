/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.ship;

import java.util.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * An {@link org.springframework.security.core.Authentication} implementation for representation of
 * JwtToken.
 *
 * @author suhail.k
 */
public class ShipAuthenticationToken extends AbstractAuthenticationToken {

  private static final Logger jwtAuthLogger = LogManager.getLogger(ShipAuthenticationToken.class);

  private static final long serialVersionUID = -6255033892894604535L;
  private String jwtToken;
  private ShipUserContext customerContext;

  public ShipAuthenticationToken(String unsafeToken) {
    super(null);
    this.jwtToken = unsafeToken;
    this.setAuthenticated(false);
  }

  public ShipAuthenticationToken(
      ShipUserContext customerContext, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.eraseCredentials();
    this.customerContext = customerContext;
    super.setAuthenticated(true);
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    if (authenticated) {
      jwtAuthLogger.warn(
          "Cannot set this token to be trusted - use constructor which takes a GrantedAuthority list instead");
      throw new IllegalArgumentException(
          "Cannot set this token to be trusted - use constructor which takes a GrantedAuthority list instead");
    }
    super.setAuthenticated(false);
  }

  @Override
  public Object getCredentials() {
    return jwtToken;
  }

  @Override
  public Object getPrincipal() {
    return this.customerContext;
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
    this.jwtToken = null;
  }

  public ShipUserContext getCustomerContext() {
    return customerContext;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((customerContext == null) ? 0 : customerContext.hashCode());
    result = prime * result + ((jwtToken == null) ? 0 : jwtToken.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (getClass() != obj.getClass()) return false;
    ShipAuthenticationToken other = (ShipAuthenticationToken) obj;
    if (customerContext == null) {
      if (other.customerContext != null) return false;
    } else if (!customerContext.equals(other.customerContext)) return false;
    if (jwtToken == null) {
      if (other.jwtToken != null) return false;
    } else if (!jwtToken.equals(other.jwtToken)) return false;
    return true;
  }
}
