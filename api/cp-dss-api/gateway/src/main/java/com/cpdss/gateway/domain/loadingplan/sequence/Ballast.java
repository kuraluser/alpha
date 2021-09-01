/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Ballast {

  private Long tankId;
  private String tankName;
  private BigDecimal quantity;
  private BigDecimal sounding;
  private Long start;
  private Long end;
  private String color;
}
