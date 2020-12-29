/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class VesselDetailsResponse {

  private CommonSuccessResponse responseStatus;

  private Vessel vessel;

  private List<VesselDraftCondition> vesselDraftCondition;

  private List<VesselTank> vesselTanks;

  private List<HydrostaticData> hydrostaticDatas;

  private List<VesselTankTCG> vesselTankTCGs;

  private BMAndSF bMAndSF;
}