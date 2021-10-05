/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class LoadablePlanStowageDetailsDto {
  private Long id;

  private Long tankId;

  private Long cargoNominationId;

  private String abbreviation;

  private String correctedUllage;

  private String weight;

  private String rdgUllage;

  private String fillingPercentage;

  private Boolean isActive;

  private String tankname;

  private String correctionFactor;

  private String observedM3;

  private String observedBarrels;

  private String observedBarrelsAt60;

  private String api;

  private String temperature;

  private String colorCode;

  private LoadablePlanDto loadablePlan;

  private LoadablePatternDto loadablePattern;

  private BigDecimal cargoNominationTemperature;
}
