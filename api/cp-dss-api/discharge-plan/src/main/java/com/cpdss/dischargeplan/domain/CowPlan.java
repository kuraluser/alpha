/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import java.util.List;
import lombok.Data;

@Data
public class CowPlan {

  private String cowOptionType;
  private Integer cowOptionTypeValue;

  private String cowTankPercent;
  private String cowStartTime;
  private String cowEndTime;
  private String estCowDuration;
  private String trimCowMin;
  private String trimCowMax;
  private Boolean needFreshCrudeStorage;
  private Boolean needFlushingOil;

  private List<Long> topCow;
  private List<Long> bottomCow;
  private List<Long> allCow;
  private List<CargoForCowDetails> cargoCow;
}
