/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class LoadablePlanComminglePortwiseDetailsDto {
  private Long id;

  private Long portId;

  private String operationType;

  private Long loadablePlanId;

  private String grade;

  private String tankName;

  private String quantity;

  private String api;

  private String temperature;

  private String cargo1Abbreviation;

  private String cargo2Abbreviation;

  private String cargo1Percentage;

  private String cargo2Percentage;

  private String cargo1BblsDbs;

  private String cargo2BblsDbs;

  private String cargo1Bbls60f;

  private String cargo2Bbls60f;

  private String cargo1Lt;

  private String cargo2Lt;

  private String cargo1Mt;

  private String cargo2Mt;

  private String cargo1Kl;

  private String cargo2Kl;

  private Boolean isActive;

  private LoadablePatternDto loadablePattern;

  private Integer priority;

  private String orderQuantity;

  private Integer loadingOrder;

  private Long tankId;

  private String fillingRatio;

  private String correctedUllage;

  private String correctionFactor;

  private String rdgUllage;

  private Long portRotationXid;

  private BigDecimal actualQuantity;

  private Long cargo1NominationId;

  private Long cargo2NominationId;

  private String tankShortName;
}
