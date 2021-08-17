/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @author ravi.r */
@Data
public class RobDetail {

  private Long id;

  private BigDecimal loadingInformationId;

  private BigDecimal tankId;

  private BigDecimal temperature;

  private BigDecimal correctedUllage;

  private BigDecimal correctionFactor;

  private BigDecimal quantity;

  private BigDecimal observedM3;

  private BigDecimal fillingRatio;
}
