/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TrimAllowed {
  private BigDecimal initialTrim;
  private BigDecimal maximumTrim;
  private BigDecimal finalTrim;
}
