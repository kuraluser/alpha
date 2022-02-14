/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.domain.rules.RuleResponse;
import com.cpdss.loadingplan.repository.projections.PortTideAlgo;
import java.util.List;
import lombok.Data;

@Data
public class LoadingInformationAlgoRequest {

  private String module = LoadingPlanConstants.LOADING_INFORMATION_REQUEST_JSON_MODULE_NAME;
  private Long vesselId;
  private Long voyageId;
  private Long portId;
  private String portCode;
  private Long portRotationId;
  private List<LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails;
  private LoadingInformation loadingInformation;
  private List<OnHandQuantity> onHandQuantity;
  private List<OnBoardQuantity> onBoardQuantity;
  // private List<LoadingRule> loadingRules; Adding new rule structure for loading specific
  private List<PortTideAlgo> portTideDetails;
  private RuleResponse loadingRules;
}
