/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class VesselPumpResponse {

  private Long pumpId;
  private Long vesselId;
  private Long pumpTypeId;
  private String pumpName;
  private String pumpCode;
  private BigDecimal pumpCapacity;
  private Integer machineType;
}
