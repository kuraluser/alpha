/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import java.math.BigDecimal;
import lombok.Data;

/** @author pranav.k */
@Data
public class AlgoDischargingRates {

  private Long Id;
  private BigDecimal maxDischargingRate;
  private BigDecimal reducedDischargingRate;
  private BigDecimal minDeBallastingRate;
  private BigDecimal maxDeBallastingRate;
  private BigDecimal noticeTimeRateReduction;
  private BigDecimal noticeTimeStopDischarging;
  private BigDecimal lineContentRemaining; // BA Clarification Pending
  private BigDecimal minDischargingRate; // Newly added
  private BigDecimal shoreDischargingRate;
}
