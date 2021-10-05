/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class LoadablePlanStowageBallastDetailsDto {

  private Long id;

  private LoadablePlanDto loadablePlan;

  private Long tankXId;

  private BigDecimal quantity;

  private BigDecimal actualQuantity;

  private Long portXId;

  private String operationType;

  private Long loadablePatternId;

  private Boolean isActive;

  private String correctedUllage;

  private String sg;

  private String colorCode;

  private Long portRotationId;

  private String rdgUllage;

  private String correctionFactor;

  private String fillingPercentage;

  private BigDecimal volume;

  private BigDecimal maxTankVolume;
}
