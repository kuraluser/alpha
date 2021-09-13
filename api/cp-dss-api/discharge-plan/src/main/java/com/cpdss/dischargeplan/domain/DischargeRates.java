/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DischargeRates {
  private Long initialDischargingRate;
  private BigDecimal maxDischargingRate;
  private BigDecimal minBallastRate;
  private BigDecimal maxBallastRate;
}
