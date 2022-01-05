/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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

  private Boolean washTanksWithDifferentCargo;
  private List<Long> topCowTankIds;
  private List<Long> bottomCowTankIds;
  private List<Long> allCowTankIds;
  private List<CargoForCowDetails> cargoCowTankIds;
  private List<CowHistory> cowHistories;
  private Boolean enableDayLightRestriction;
}
