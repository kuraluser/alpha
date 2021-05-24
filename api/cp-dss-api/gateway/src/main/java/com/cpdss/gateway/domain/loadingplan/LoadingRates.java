/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class LoadingRates {

  private BigDecimal initialLoadingRate;
  private BigDecimal maxLoadingRate;
  private BigDecimal reducedLoadingRate;
  private BigDecimal minDeBallastingRate;
  private BigDecimal maxDeBallastingRate;
  private String noticeTimeRateReduction;
  private String noticeTimeStopLoading;
  private BigDecimal lineContentRemaining; // BA Clarification Pending
}
