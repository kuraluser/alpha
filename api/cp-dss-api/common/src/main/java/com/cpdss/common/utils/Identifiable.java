/* Licensed at AlphaOri Technologies */
package com.cpdss.common.utils;

import java.io.Serializable;

/**
 * Interface for primary key generator
 *
 * @author mathewgillroy-ao
 * @since 27-08-2021
 */
public interface Identifiable<T extends Serializable> {
  T getId();
}
