/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDischargeStage {
  private BigDecimal dryCheckTime;
  private BigDecimal slopDischargingTime;
  private BigDecimal finalStrippingTime;
  private BigDecimal freshOilWashingTime;
}
