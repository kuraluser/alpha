/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.loadingplan.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Discharge Plan Module, Parent Object
 *
 * @author Johnsooraj.x
 * @since 24-08-2021
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DischargeInformation {

  private CommonSuccessResponse responseStatus;
  private Long dischargeInfoId;
  private String dischargeStudyName;
  private Long dischargeStudyId;
  private Long synopticTableId;
  private Boolean isDischargeInfoComplete;

  // reusing common class from loading
  private LoadingDetails dischargeDetails;
  private DischargeRates dischargeRates;
  private LoadingBerthDetails berthDetails;
  private CargoMachineryInUse machineryInUses;
  private LoadingStages dischargeStages;
  private LoadingSequences dischargeSequences;
  private CowPlan cowPlan;
}
