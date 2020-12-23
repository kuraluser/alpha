/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SynopticalCargoRecord {

  private Long tankId;

  private String tankName;

  private BigDecimal actualWeight;

  private BigDecimal plannedWeight;
}
