/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.jsonbuilder.CPDSSJsonParser;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.loadingplan.CargoMachineryInUse;
import com.cpdss.gateway.domain.loadingplan.CargoVesselTankDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingBerthDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingRates;
import com.cpdss.gateway.domain.loadingplan.LoadingSequences;
import com.cpdss.gateway.domain.loadingplan.LoadingStages;
import com.cpdss.gateway.domain.loadingplan.ToppingOffSequence;
import java.util.List;
import lombok.Data;

/** @author arun.j */
@Data
@CPDSSJsonParser
public class DischargingInformation {

  private CommonSuccessResponse responseStatus;
  private Long dischargingInfoId;
  private String loadableStudyName;
  private Long loadableStudyId;
  private Long synopticTableId;
  private Boolean isDischargingInfoComplete;
  private LoadingDetails dischargingDetails;
  private LoadingRates dischargingRates;
  private LoadingBerthDetails berthDetails;
  private CargoMachineryInUse machineryInUses;
  private LoadingStages dischargingStages;
  private LoadingSequences dischargingSequences;
  private List<ToppingOffSequence> toppingOffSequence;
  private CargoVesselTankDetails cargoVesselTankDetails;
  private Long dischargingInfoStatusId;
  private Long dischargingPlanArrStatusId;
  private Long dischargingPlanDepStatusId;
  private Long loadablePatternId;
  private Boolean isLoadingInstructionsComplete;
  private Boolean isLoadingSequenceGenerated;
  private Boolean isLoadingPlanGenerated;
}
