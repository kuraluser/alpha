/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class LoadingRates {

  private Long Id;
  private BigDecimal maxLoadingRate;
  private BigDecimal reducedLoadingRate;
  private BigDecimal minDeBallastingRate;
  private BigDecimal maxDeBallastingRate;
  private BigDecimal noticeTimeRateReduction;
  private BigDecimal noticeTimeStopLoading;
  private BigDecimal lineContentRemaining;
  private BigDecimal minLoadingRate; // or initial loading rate
  private BigDecimal shoreLoadingRate;
}