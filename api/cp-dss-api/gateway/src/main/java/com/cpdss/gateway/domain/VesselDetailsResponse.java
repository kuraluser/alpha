/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
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
}
