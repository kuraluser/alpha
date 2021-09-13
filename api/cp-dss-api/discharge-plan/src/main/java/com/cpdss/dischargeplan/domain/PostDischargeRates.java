/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PostDischargeRates {
  private BigDecimal timeForDryCheck;
  private BigDecimal timeForFinalStripping;
  private BigDecimal timeForSlopDischarging;
  private BigDecimal freshOilWashing;
}
