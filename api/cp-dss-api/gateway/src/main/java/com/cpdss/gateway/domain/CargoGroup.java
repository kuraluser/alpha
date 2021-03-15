/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CargoGroup {

  private Long id;
  private Long cargo1Id;
  private Long cargoNomination1Id;
  private BigDecimal cargo1pct;
  private Long cargoNomination2Id;
  private Long cargo2Id;
  private BigDecimal cargo2pct;
  private BigDecimal quantity;
}
