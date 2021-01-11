/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanDetailsResponse {
  private CommonSuccessResponse responseStatus;
  private List<LoadableQuantityCargoDetails> loadableQuantityCargoDetails;
  private List<LoadableQuantityCommingleCargoDetails> loadableQuantityCommingleCargoDetails;
  private List<List<VesselTank>> tankLists;
  private List<LoadablePlanStowageDetails> loadablePlanStowageDetails;
  private List<List<VesselTank>> frontBallastTanks;
  private List<List<VesselTank>> centerBallastTanks;
  private List<List<VesselTank>> rearBallastTanks;
  private List<LoadablePlanBallastDetails> loadablePlanBallastDetails;
  private List<LoadablePlanSynopticalRecord> loadablePlanSynopticalRecords;
}
