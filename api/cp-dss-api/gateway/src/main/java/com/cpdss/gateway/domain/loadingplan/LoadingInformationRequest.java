/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;
import lombok.Data;

@Data
public class LoadingInformationRequest {

  private Long loadingInfoId;

  private Long synopticalTableId;

  private LoadingDetails loadingDetails;

  private LoadingRates loadingRates;

  private List<BerthDetails> loadingBerths;

  private List<LoadingMachinesInUse> loadingMachineries;

  private StageOffset stageOffset;

  private StageDuration stageDuration;

  private List<LoadingDelays> loadingDelays;

  private List<ToppingOffSequence> toppingOffSequence;

  private Boolean trackStartEndStage;

  private Boolean trackGradeSwitch;
}
