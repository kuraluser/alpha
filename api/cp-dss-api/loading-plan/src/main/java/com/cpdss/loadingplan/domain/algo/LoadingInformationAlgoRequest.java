/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.util.List;
import lombok.Data;

@Data
public class LoadingInformationAlgoRequest {

  private LoadingInformation loadingInformation;
  private List<LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails;
  private List<OnHandQuantity> onHandQuantity;
  private List<OnBoardQuantity> onBoardQuantity;
  private List<LoadingRule> loadingRules;
}
