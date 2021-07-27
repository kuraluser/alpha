/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

/** @Author lincy.g */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoadablePlanStowageTempDetails {

  private Boolean isBallast;
  private Boolean isCommingle;
  private BigDecimal correctionFactor;
  private BigDecimal correctedUllage;
  private BigDecimal quantity;
  private BigDecimal rdgUllage;
  private BigDecimal fillingRatio;

  private Long stowageDetailsId;
  private Long ballastDetailsId;
  private Long loadablePatternId;
  private Long commingleDetailId;


}
