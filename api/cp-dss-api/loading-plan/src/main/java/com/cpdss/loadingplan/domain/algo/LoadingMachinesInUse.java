/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class LoadingMachinesInUse {
  private Long id;
  private Long loadingInfoId;
  private Long machineId;
  private String machineName;
  private BigDecimal capacity;
  private Integer machineTypeId;
  private String machineTypeName;
}
