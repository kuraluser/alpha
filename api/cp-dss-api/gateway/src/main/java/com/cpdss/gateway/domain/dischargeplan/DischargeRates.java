/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DischargeRates {
  private BigDecimal initialDischargingRate;
  private BigDecimal maxDischargingRate;
  private BigDecimal minBallastRate;
  private BigDecimal maxBallastRate;
  private BigDecimal lineContentRemaining;
  private BigDecimal minDeBallastingRate;
  private BigDecimal maxDeBallastingRate;
  private Integer noticeTimeForRateReduction;
  private Integer noticeTimeForStopDischarging;
  private BigDecimal minDischargingRate;
  private BigDecimal reducedDischargingRate;
  private BigDecimal shoreDischargingRate;
  private Long id;
}
