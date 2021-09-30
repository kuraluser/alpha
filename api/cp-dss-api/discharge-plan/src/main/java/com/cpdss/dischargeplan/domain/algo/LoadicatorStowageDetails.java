/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class LoadicatorStowageDetails {

  private Long tankXId;
  private Long cargoNominationXId;
  private String abbreviation;
  private BigDecimal correctedUllage;
  private BigDecimal weight;
  private BigDecimal rdgUllage;
  private BigDecimal fillingPercentage;
  private BigDecimal correctionFactor;
  private BigDecimal observedM3;
  private BigDecimal observedBarrels;
  private BigDecimal observedBarrelsAt60;
  private BigDecimal api;
  private BigDecimal temperature;
  private String colorCode;
  private Long cargoXId;
  private BigDecimal cargoNominationTemperature;
  private Boolean isActive;
  private Long portXId;
  private Long portRotationXId;
  private BigDecimal quantity;
  private BigDecimal quantityM3;
  private BigDecimal ullage;
  private Integer conditionType;
  private Integer valueType;
}
