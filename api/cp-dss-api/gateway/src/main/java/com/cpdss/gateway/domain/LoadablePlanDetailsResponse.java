/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanDetailsResponse {
  @JsonInclude(Include.NON_NULL)
  private Long id;

  @JsonInclude(Include.NON_NULL)
  private String caseNumber;

  @JsonInclude(Include.NON_NULL)
  private String date;

  @JsonInclude(Include.NON_NULL)
  private String voyageNumber;

  private List<LoadableQuantityCargoDetails> loadableQuantityCargoDetails;

  private List<LoadableQuantityCommingleCargoDetails> loadableQuantityCommingleCargoDetails;

  @JsonInclude(Include.NON_NULL)
  private List<List<VesselTank>> tankLists;

  private List<LoadablePlanStowageDetails> loadablePlanStowageDetails;

  @JsonInclude(Include.NON_NULL)
  private List<List<VesselTank>> frontBallastTanks;

  @JsonInclude(Include.NON_NULL)
  private List<List<VesselTank>> centerBallastTanks;

  @JsonInclude(Include.NON_NULL)
  private List<List<VesselTank>> rearBallastTanks;

  private List<LoadablePlanBallastDetails> loadablePlanBallastDetails;

  @JsonInclude(Include.NON_NULL)
  private List<LoadablePlanSynopticalRecord> loadablePlanSynopticalRecords;

  @JsonInclude(Include.NON_NULL)
  private List<LoadablePlanComments> loadablePlanComments;

  @JsonInclude(Include.NON_NULL)
  private CommonSuccessResponse responseStatus;

  @JsonInclude(Include.NON_NULL)
  private Long loadablePatternStatusId;

  @JsonInclude(Include.NON_NULL)
  private Long voyageStatusId;

  @JsonInclude(Include.NON_NULL)
  private Boolean validated;

  @JsonInclude(Include.NON_NULL)
  private StabilityParameter stabilityParameters;

  @JsonInclude(Include.NON_NULL)
  private Long loadableStudyStatusId;
}
