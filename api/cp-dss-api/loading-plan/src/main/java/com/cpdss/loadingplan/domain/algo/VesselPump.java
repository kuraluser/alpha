/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class VesselPump {

  private Long id;
  private Long vesselId;
  private Long pumpTypeId;
  private String pumpName;
  private String pumpCode;
  private BigDecimal pumpCapacity;
}
