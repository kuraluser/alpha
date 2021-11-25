/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.gateway.domain.loadingplan.LoadingPlanExcelLoadingInstructionDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanExcelLoadingPlanDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DischargingPlanExcelDetails {

  private LoadingPlanExcelLoadingPlanDetails sheetOne;
  private LoadingPlanExcelLoadingInstructionDetails sheetTwo;
  private DischargingPlanExcelDischargingSequenceDetails sheetThree;
}
