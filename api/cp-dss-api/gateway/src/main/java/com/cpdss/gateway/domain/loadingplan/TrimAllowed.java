/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

/**
 * Make sure always Even Keel (trim = 0.0) Departure Condition - Even Keel Ballast Stripping
 * Condition - 1.5 meter by stern Topping off - Even Keel, If possible. Else 1 meter by stern.
 * Slowly become even keel on completion
 */
@Data
public class TrimAllowed {
  private BigDecimal initialTrim;
  private BigDecimal maximumTrim;
  private BigDecimal finalTrim; // topping off
}
