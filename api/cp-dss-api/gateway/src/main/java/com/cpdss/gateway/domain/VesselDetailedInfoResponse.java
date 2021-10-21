/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

@Data
public class VesselDetailedInfoResponse {

  private CommonSuccessResponse responseStatus;

  private Long vesselId;

  private String vesselName;

  private String vesselImageUrl;

  private String countryFlagUrl;

  private String imoNumber;

  private VesselGeneralInfoResponse generalInfo;

  private VesselDimensionResponse vesselDimesnsions;

  private VesselDraftDisplacementResponse draftDisplacementDeadweight;

  private List<List<VesselTankInformation>> cargoTanks;

  private List<List<VesselTankInformation>> bunkerTanks;

  private List<List<VesselTankInformation>> bunkerRearTanks;

  private List<List<VesselTankInformation>> ballastFrontTanks;

  private List<List<VesselTankInformation>> ballastCenterTanks;

  private List<List<VesselTankInformation>> ballastRearTanks;
}
