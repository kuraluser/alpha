/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class LoadingMachinesInUse {
  private Long id;
  private Long loadingInfoId;
  private Long dischargingInfoId;
  private Long machineId;
  private BigDecimal capacity;
  private Boolean isUsing;
  private Integer machineTypeId;
}
