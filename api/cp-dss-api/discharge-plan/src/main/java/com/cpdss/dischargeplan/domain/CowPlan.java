/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CowPlan {

  private String cowOptionType;
  private Integer cowOptionTypeValue;

  private BigDecimal cowPercentage;
  private BigDecimal cowStartTime;
  private BigDecimal cowEndTime;
  private BigDecimal estimatedCowDuration;
  private BigDecimal cowMinTrim;
  private BigDecimal cowMaxTrim;
  private Boolean needFreshCrudeStorage;
  private Boolean needFlushingOil;

  private List<Long> topCowTankIds;
  private List<Long> bottomCowTankIds;
  private List<Long> allCowTankIds;
  private List<CargoForCowDetails> cargoCowTankIds;
}
