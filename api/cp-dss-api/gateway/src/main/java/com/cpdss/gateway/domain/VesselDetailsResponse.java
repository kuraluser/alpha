/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.loadingplan.VesselComponent;
import com.cpdss.gateway.domain.vessel.PumpType;
import com.cpdss.gateway.domain.vessel.TankType;
import com.cpdss.gateway.domain.vessel.VesselPump;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

/** @Author jerin.g @Author arun.j */
@Data
public class VesselDetailsResponse {

  private CommonSuccessResponse responseStatus;

  private Vessel vessel;

  private List<VesselDraftCondition> vesselDraftCondition;

  private List<VesselTank> vesselTanks;

  private List<HydrostaticData> hydrostaticDatas;

  private List<VesselTankTCG> vesselTankTCGs;

  private BMAndSF bMAndSF;

  private List<UllageDetails> ullageDetails;

  private List<UllageTrimCorrection> ullageTrimCorrections;

  private List<SelectableParameter> selectableParameter;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Object vesselValveSequence;

  private List<PumpType> pumpTypes;
  private List<VesselPump> vesselPumps;
  private List<TankType> tankTypes;
  private List<VesselComponent> vesselManifold;
  private List<VesselComponent> vesselBottomLine;
}
