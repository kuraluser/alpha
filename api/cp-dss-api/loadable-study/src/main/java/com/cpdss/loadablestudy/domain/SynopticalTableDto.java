/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

/** @Author vinothkumar */
@Data
public class SynopticalTableDto {

  private Long id;

  private Long loadableStudyXId;

  private String operationType;

  private BigDecimal distance;

  private BigDecimal speed;

  private BigDecimal runningHours;

  private BigDecimal inPortHours;

  private LocalDateTime etaActual;

  private LocalDateTime etdActual;

  private LocalTime timeOfSunrise;

  private LocalTime timeOfSunSet;

  private BigDecimal hwTideFrom;

  private BigDecimal hwTideTo;

  private LocalTime hwTideTimeFrom;

  private LocalTime hwTideTimeTo;

  private BigDecimal lwTideFrom;

  private BigDecimal lwTideTo;

  private LocalTime lwTideTimeFrom;

  private LocalTime lwTideTimeTo;

  private Long portXid;

  //  @Column(name = "sea_water_sg")
  //  private BigDecimal specificGravity;

  private Boolean isActive;

  private LoadableStudyPortRotationDto loadableStudyPortRotation;

  private BigDecimal othersPlanned;

  private BigDecimal othersActual;

  private BigDecimal constantPlanned;

  private BigDecimal constantActual;

  private BigDecimal deadWeightPlanned;

  private BigDecimal deadWeightActual;

  private BigDecimal displacementPlanned;

  private BigDecimal displacementActual;
}
