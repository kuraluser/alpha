/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoadablePlanDetailsResponse {
  private Long id;
  private String caseNumber;
  private String date;
  private String voyageNumber;
  private List<LoadableQuantityCargoDetails> loadableQuantityCargoDetails;
  private List<LoadableQuantityCommingleCargoDetails> loadableQuantityCommingleCargoDetails;
  private List<List<VesselTank>> tankLists;
  private List<LoadablePlanStowageDetails> loadablePlanStowageDetails;
  private List<List<VesselTank>> frontBallastTanks;

  private List<List<VesselTank>> centerBallastTanks;

  private List<List<VesselTank>> rearBallastTanks;

  private List<LoadablePlanBallastDetails> loadablePlanBallastDetails;

  private List<LoadablePlanSynopticalRecord> loadablePlanSynopticalRecords;

  private List<LoadablePlanComments> loadablePlanComments;

  private CommonSuccessResponse responseStatus;

  private Long loadablePatternStatusId;

  private Long voyageStatusId;

  private Boolean validated;

  private StabilityParameter stabilityParameters;

  private Long loadableStudyStatusId;

  private BigDecimal loadableQuantity;

  private Long lastModifiedPort;

  private boolean confirmPlanEligibility;

  // DS fields
  private List<DischargePlanRoBDetails> dischargePlanRoBDetails;

  private List<LoadableQuantityCargoDetails> dischargeQuantityCargoDetails;

  private List<LoadableQuantityCommingleCargoDetails> dischargeQuantityCommingleCargoDetails;

  private List<LoadablePlanBallastDetails> dischargePlanBallastDetails;

  private List<LoadablePlanStowageDetails> dischargePlanStowageDetails;
}
