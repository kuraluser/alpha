/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDischargeStage {
  private BigDecimal timeForDryCheck;
  private BigDecimal slopDischarging;
  private BigDecimal finalStripping;
  private BigDecimal freshOilWashing;
}
