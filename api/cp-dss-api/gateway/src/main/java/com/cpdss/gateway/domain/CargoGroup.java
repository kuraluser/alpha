/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CargoGroup {

  private Long id;
  private Long cargo1Id;
  private BigDecimal cargo1pct;
  private Long cargo2Id;
  private BigDecimal cargo2pct;
  private BigDecimal quantity;
}
