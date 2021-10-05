/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class LoadablePatternCargoToppingOffSequenceDto {

  private Long id;

  private LoadablePatternDto loadablePattern;

  private Integer orderNumber;

  private Long tankXId;

  private Long cargoXId;

  private BigDecimal ullage;

  private BigDecimal volume;

  private BigDecimal weight;

  private BigDecimal fillingRatio;

  private String remarks;

  private Integer displayOrder;

  private Long portRotationXId;

  private Boolean isActive;
}
