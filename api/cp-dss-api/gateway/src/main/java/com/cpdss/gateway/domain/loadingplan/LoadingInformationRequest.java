/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.common.jsonbuilder.CPDSSJsonParser;
import java.util.List;
import lombok.Data;

@Data
@CPDSSJsonParser
public class LoadingInformationRequest {

  private Long loadingInfoId;

  private Long synopticalTableId;

  private LoadingDetails loadingDetails;

  private LoadingRates loadingRates;

  private List<BerthDetails> loadingBerths;

  private List<LoadingMachinesInUse> loadingMachineries;

  private List<LoadingDelays> loadingDelays;

  private List<ToppingOffSequence> toppingOffSequence;

  private LoadingStagesRequest loadingStages;
  private Boolean isLoadingInfoComplete; // validation logic at Front-end
}
