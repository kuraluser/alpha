/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BunkerConditions {
  private BigDecimal dieselOilWeight;
  private BigDecimal fuelOilWeight;
  private BigDecimal ballastWeight;
  private BigDecimal freshWaterWeight;
  private BigDecimal othersWeight;
  private BigDecimal totalDwtWeight;
  private BigDecimal displacement;
  private BigDecimal specificGravity;
}
