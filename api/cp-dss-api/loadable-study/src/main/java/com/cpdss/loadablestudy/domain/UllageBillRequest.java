/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import java.util.List;
import lombok.Data;

@Data
public class UllageBillRequest {
  List<LoadingPlanModels.BillOfLanding> billOfLandingList;
  List<UpdateUllage> ullageUpdList;
}
