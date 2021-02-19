/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
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
}
