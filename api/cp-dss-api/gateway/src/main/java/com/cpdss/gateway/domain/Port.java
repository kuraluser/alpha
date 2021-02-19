/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Port {
  private Long id;
  private String name;
  private String code;
  private BigDecimal waterDensity;
  private BigDecimal maxDraft;
  private BigDecimal maxAirDraft;
}
