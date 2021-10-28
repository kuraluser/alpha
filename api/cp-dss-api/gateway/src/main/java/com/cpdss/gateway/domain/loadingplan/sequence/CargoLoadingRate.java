/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CargoLoadingRate {

  private Long startTime;
  private Long endTime;
  private List<BigDecimal> loadingRates;
  private List<BigDecimal> dischargingRates;
}
