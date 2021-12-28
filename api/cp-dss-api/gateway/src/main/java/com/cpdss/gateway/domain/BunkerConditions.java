/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BunkerConditions {
  private BigDecimal dieselOilPlannedWeight;
  private BigDecimal dieselOilActualWeight;
  private BigDecimal fuelOilPlannedWeight;
  private BigDecimal fuelOilActualWeight;
  private BigDecimal ballastPlannedWeight;
  private BigDecimal ballastActualWeight;
  private BigDecimal freshWaterPlannedWeight;
  private BigDecimal freshWaterActualWeight;
  private BigDecimal othersPlannedWeight;
  private BigDecimal othersActualWeight;
  private BigDecimal totalDwtWeightPlanned;
  private BigDecimal totalDwtWeightActual;
  private BigDecimal displacementPlanned;
  private BigDecimal displacementActual;
  private BigDecimal specificGravity;
  private BigDecimal constantActual;
  private BigDecimal constantPlanned;
}
