/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SynopticalOhqRecord {

  private Long tankId;

  private String tankName;

  private Long fuelTypeId;

  private String fuelType;

  private BigDecimal plannedWeight;

  private BigDecimal actualWeight;

  private BigDecimal density;

  private BigDecimal capacity;
}
