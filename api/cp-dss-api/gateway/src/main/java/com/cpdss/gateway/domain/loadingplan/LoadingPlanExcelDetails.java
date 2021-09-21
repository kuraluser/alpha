/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadingPlanExcelDetails {

  private LoadingPlanExcelLoadingPlanDetails sheetOne;
  private Object sheetTwo;
  private Object sheetThree;
}
