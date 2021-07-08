/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;

public interface LoadablePlanQuantityService {

  public void saveLoadablePlanQuantyList(
      List<LoadableQuantityCargoDetails> cargoDetailsList, LoadingInformation loadingInformation);
}
