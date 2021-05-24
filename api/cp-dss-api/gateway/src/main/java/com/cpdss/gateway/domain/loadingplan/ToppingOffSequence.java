/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ToppingOffSequence {

  private Integer sequenceOrder;
  private Long tankId;
  private String shortName;
  private String cargoType;
  private BigDecimal ullage;
  private BigDecimal volume;
  private BigDecimal weight;
  private BigDecimal fillingPercent;
  private String remarks;
}
