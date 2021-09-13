/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class LoadingRates {

  private Long Id;
  private BigDecimal maxLoadingRate;
  private BigDecimal maxDischaringRate;
  private BigDecimal reducedDischaringRate;
  private BigDecimal reducedLoadingRate;
  private BigDecimal minDeBallastingRate;
  private BigDecimal maxDeBallastingRate;
  private BigDecimal noticeTimeRateReduction;
  private BigDecimal noticeTimeStopLoading;
  private BigDecimal noticeTimeStopDischarging;
  private BigDecimal lineContentRemaining;
  private BigDecimal minDischargingRate;
  private BigDecimal minLoadingRate; // or initial loading rate
  private BigDecimal shoreLoadingRate;
  private BigDecimal shoreDischargingRate;
}
