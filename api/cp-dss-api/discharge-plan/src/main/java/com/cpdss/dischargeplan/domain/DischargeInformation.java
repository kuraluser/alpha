/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import com.cpdss.common.jsonbuilder.CPDSSJsonParser;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.dischargeplan.domain.cargo.DischargeQuantityCargoDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

/**
 * @author johnsooraj
 * @since 23-08-21
 */
@Data
@CPDSSJsonParser
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DischargeInformation {

  private CommonSuccessResponse responseStatus;
  private Long dischargeInfoId;
  private String dischargeStudyName;
  private Long dischargeStudyId;
  private Long synopticTableId;
  private Boolean isDischargeInfoComplete;

  private DischargeDetails dischargeDetails;
  private DischargeRates dischargeRates;
  private DischargeBerthDetails berthDetails;
  private CargoMachineryInUse machineryInUses;
  private DischargeStages dischargeStages;
  private DischargeSequences dischargeSequences;
  private CowPlan cowPlan;
  private PostDischargeRates postDischargeRates;
  private List<DischargeQuantityCargoDetails> dischargeQuantityCargoDetails;
}
