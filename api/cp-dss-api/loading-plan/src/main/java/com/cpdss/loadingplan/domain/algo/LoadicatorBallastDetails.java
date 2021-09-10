/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class LoadicatorBallastDetails {

  private Long tankXId;
  private BigDecimal correctedUllage;
  private BigDecimal weight;
  private BigDecimal rdgUllage;
  private BigDecimal fillingPercentage;
  private BigDecimal correctionFactor;
  private BigDecimal observedM3;
  private BigDecimal observedBarrels;
  private BigDecimal observedBarrelsAt60;
  private BigDecimal sg;
  private BigDecimal temperature;
  private Long portXId;
  private Long operationXId;
  private Long portConditionXId;
  private Long operationType;
  private BigDecimal quantity;
  private BigDecimal actualQuantity;
  private String colorCode;
  private Long portRotationXId;
  private Boolean isActive;
  private Integer conditionType;
  private Integer valueType;
  private BigDecimal quantityM3;
  private BigDecimal sounding;
}
