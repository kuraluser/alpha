/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.gateway.domain.vessel.PumpType;
import com.cpdss.gateway.domain.vessel.VesselPump;
import java.util.List;
import lombok.Data;

@Data
public class CargoMachineryInUse {

  private List<PumpType> pumpTypes;
  private List<VesselPump> vesselPumps;
  private List<LoadingMachinesInUse> loadingMachinesInUses;
}
