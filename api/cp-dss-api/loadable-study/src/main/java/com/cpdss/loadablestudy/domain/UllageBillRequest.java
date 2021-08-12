/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.cpdss.loadablestudy.entity.BillOfLanding;
import java.util.List;
import lombok.Data;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;

@Data
public class UllageBillRequest {
  List<LoadingPlanModels.BillOfLanding> billOfLandingList;
  List<UpdateUllage> ullageUpdList;
}
