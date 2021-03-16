/* Licensed at AlphaOri Technologies */
package com.cpdss.loadableplan.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author jerin.g */
@Data
public class CargoNomination {

  private Long id;

  private Long loadableStudyId;

  private Long priority;

  private String color;

  private Long cargoId;

  private String abbreviation;

  private BigDecimal quantity;

  private BigDecimal maxTolerance;

  private BigDecimal minTolerance;

  private BigDecimal api;

  private BigDecimal temperature;

  private Long segregationId;
}
