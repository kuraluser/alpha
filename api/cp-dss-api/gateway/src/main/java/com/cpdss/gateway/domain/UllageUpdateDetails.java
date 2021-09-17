/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @author ravi.r */
@Data
public class UllageUpdateDetails {

  private BigDecimal loadingInformationId;

  private BigDecimal tankId;

  private BigDecimal temperature;

  private BigDecimal correctedUllage;

  private BigDecimal correctionFactor;

  private BigDecimal quantity;

  private BigDecimal observedM3;

  private BigDecimal fillingRatio;

  private BigDecimal api;

  private BigDecimal ullage;

  private Long port_xid;

  private Long port_rotation_xid;

  private Long arrival_departutre;

  private Long actual_planned;

  private Long grade;

  private Long fillingPercentage;

  private Boolean isUpdate;

  private BigDecimal cargoNominationId;

  private String color_code;

  private String abbreviation;

  private Long cargoId;
}
