/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.util.List;
import lombok.Data;

@Data
public class LoadingInformationAlgoRequest {

  private Long portId;
  private List<LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails;
  private LoadingInformation loadingInformation;
  private List<OnHandQuantity> onHandQuantity;
  private List<OnBoardQuantity> onBoardQuantity;
  private List<LoadingRule> loadingRules;
}
