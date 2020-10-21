/* Licensed under Apache-2.0 */
package com.cpdss.common.springdata;

import com.cpdss.common.utils.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

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
