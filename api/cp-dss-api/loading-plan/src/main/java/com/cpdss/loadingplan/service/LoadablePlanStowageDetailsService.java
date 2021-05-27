/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;

public interface LoadablePlanStowageDetailsService {

  public void saveLoadablePlanStowageDetailsList(
      List<LoadableStudy.LoadablePlanStowageDetails> stowageDetailsList,
      LoadingInformation loadingInformation);
}
