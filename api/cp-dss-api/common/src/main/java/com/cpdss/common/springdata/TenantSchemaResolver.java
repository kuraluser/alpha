package com.cpdss.common.springdata;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import com.cpdss.common.utils.TenantContext;

/**
 * Class to configure the tenant schema name
 *
 * @author krishna
 */
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

  private String defaultTenant = "public";

  @Override
  public String resolveCurrentTenantIdentifier() {
    String t = TenantContext.getCurrentTenant();
    if (t != null) {
      return t;
    } else {
      return defaultTenant;
    }
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
