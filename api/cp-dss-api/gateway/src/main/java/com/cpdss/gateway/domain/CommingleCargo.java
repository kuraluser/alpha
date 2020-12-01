/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CommingleCargo {

  private Long id;
  private Long purpose;
  private boolean slopOnly;
  private List<LoadingPort> loadingPorts;
  private BigDecimal quantity;
}
