/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class LoadablePatternCargoDetailsDto {
  private Long id;

  private Long loadablePatternId;

  private Long portId;

  private Long tankId;

  private BigDecimal plannedQuantity;

  private BigDecimal actualQuantity;

  private Boolean isActive;

  private String operationType;

  private String abbreviation;

  private BigDecimal correctedUllage;

  private String colorCode;

  private Long cargoId;

  private BigDecimal api;

  private BigDecimal temperature;

  private Long portRotationId;

  private String correctionFactor;

  private Long cargoNominationId;

  private String fillingRatio;

  private BigDecimal cargoNominationTemperature;

  private BigDecimal onBoard;

  private BigDecimal maxTankVolume;
}
