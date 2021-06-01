/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class LoadingMachinesInUse {
  private Long id;
  private Long loadingInfoId;
  private Long pumpId;
  private BigDecimal capacity;
}
