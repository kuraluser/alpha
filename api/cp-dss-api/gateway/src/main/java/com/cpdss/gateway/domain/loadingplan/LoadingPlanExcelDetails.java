/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;


import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanExcelLoadingSequenceDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadingPlanExcelDetails {

  private LoadingPlanExcelLoadingPlanDetails sheetOne;
  private Object sheetTwo;
  private LoadingPlanExcelLoadingSequenceDetails sheetThree;
}
