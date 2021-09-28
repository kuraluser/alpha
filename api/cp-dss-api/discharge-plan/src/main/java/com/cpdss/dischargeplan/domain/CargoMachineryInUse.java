/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import com.cpdss.dischargeplan.domain.vessel.PumpTypes;
import com.cpdss.dischargeplan.domain.vessel.VesselBottomLine;
import com.cpdss.dischargeplan.domain.vessel.VesselManifold;
import com.cpdss.dischargeplan.domain.vessel.VesselPump;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CargoMachineryInUse {
  private List<PumpTypes> pumpTypes;
  private List<VesselPump> vesselPumps;
  private List<VesselManifold> vesselManifolds;
  private List<VesselBottomLine> vesselBottomLines;
  private List<DischargeMachinesInUse> machinesInUses; // User selected data
}
