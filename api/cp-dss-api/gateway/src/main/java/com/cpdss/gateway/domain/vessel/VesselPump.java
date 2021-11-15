/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import com.cpdss.gateway.domain.VesselTank;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class VesselPump {

  private Long id;
  private Long vesselId;
  private Long pumpTypeId;
  private String pumpName;
  private String pumpCode;
  private BigDecimal pumpCapacity;
  private Integer machineType;
  private List<VesselTank> vesselTanks;
}
