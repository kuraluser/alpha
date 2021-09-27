/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

/** @author pranav.k */
@Data
public class AlgoLoadingRates {

  private Long Id;
  private BigDecimal maxLoadingRate;
  private BigDecimal reducedLoadingRate;
  private BigDecimal minDeBallastingRate;
  private BigDecimal maxDeBallastingRate;
  private BigDecimal noticeTimeRateReduction;
  private BigDecimal noticeTimeStopLoading;
  private BigDecimal lineContentRemaining; // BA Clarification Pending
  private BigDecimal minLoadingRate; // Newly added
  private BigDecimal shoreLoadingRate;
}
