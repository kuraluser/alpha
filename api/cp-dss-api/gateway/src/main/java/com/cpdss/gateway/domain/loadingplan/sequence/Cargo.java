/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Cargo {

  private Long tankId;
  private String tankName;
  private BigDecimal quantity;
  private BigDecimal ullage;
  private Long start;
  private Long end;
  private String name;
  private String color;
  private String abbreviation;
  private Long cargoNominationId;
}
