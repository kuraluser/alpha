/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CargoStage {
  private String name;
  private String color;
  private String abbreviation;
  private Long cargoNominationId;
  private BigDecimal quantity;
  private Long start;
  private Long end;
}
