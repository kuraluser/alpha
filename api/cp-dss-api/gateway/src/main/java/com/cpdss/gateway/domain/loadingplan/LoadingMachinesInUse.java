/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class LoadingMachinesInUse {
  private Long id;
  private Long loadingInfoId;
  private Long dischargeInfoId;
  private Long machineId;
  private BigDecimal capacity;
  private Boolean isUsing;
  private Integer machineTypeId;
  private String machineName;
  private String machineTypeName;
  private String tankTypeName;
}
