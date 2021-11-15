/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import com.cpdss.gateway.domain.VesselTank;
import lombok.Data;

@Data
public class VesselPumpTankMapping {

  private Integer vesselXid;
  private VesselPump vesselPump;
}
