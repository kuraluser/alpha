/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @author ravi.r */
@Data
public class BallastDetail {

  private Long id;

  private BigDecimal loadingInformationId;

  private BigDecimal tankId;

  private BigDecimal temperature;

  private BigDecimal correctedUllage;

  private BigDecimal correctionFactor;

  private BigDecimal quantity;

  private BigDecimal observedM3;

  private BigDecimal fillingRatio;

  private BigDecimal sounding;

  private BigDecimal filling_percentage;

  private BigDecimal arrival_departutre;

  private BigDecimal actual_planned;

  private String color_code;

  private BigDecimal sg;

  private Boolean isUpdate;

  private Long portXId;

  private Long portRotationXId;
}
