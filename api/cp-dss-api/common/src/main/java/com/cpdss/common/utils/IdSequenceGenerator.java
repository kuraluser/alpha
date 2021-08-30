/* Licensed at AlphaOri Technologies */
package com.cpdss.common.utils;

import java.io.Serializable;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * Primary key generator
 *
 * @author mathewgillroy-ao
 * @since 27-08-2021
 */
public class IdSequenceGenerator implements IdentifierGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object)
      throws HibernateException {

    // Two digit random number appended to avoid collision at the same instant
    String randomNumber = String.format("%02d", ThreadLocalRandom.current().nextInt(100));
    return Long.parseLong(
        System.getenv("SHIP_PRIMARY_KEY_PREFIX") + Instant.now().toEpochMilli() + randomNumber);
  }
}
