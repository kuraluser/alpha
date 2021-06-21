/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;

public interface LoadablePlanCommingleDetailsService {

  public void saveLoadablePlanCommingleDetailsList(
      List<LoadableQuantityCommingleCargoDetails> commingleDetailsList,
      LoadingInformation loadingInformation);
}
