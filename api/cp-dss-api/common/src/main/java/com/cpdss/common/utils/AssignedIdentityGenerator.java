/* Licensed at AlphaOri Technologies */
package com.cpdss.common.utils;

import java.io.Serializable;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

/**
 * Primary key generator
 *
 * @author mathewgillroy-ao
 * @since 15-09-2021
 */
public class AssignedIdentityGenerator extends IdentityGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor s, Object obj) {

    // If the Id value is null, then using DB generation strategy.
    if (obj instanceof Identifiable) {
      Identifiable identifiable = (Identifiable) obj;
      Serializable id = identifiable.getId();
      if (id != null) {
        return id;
      }
    }
    return super.generate(s, obj);
  }
}
