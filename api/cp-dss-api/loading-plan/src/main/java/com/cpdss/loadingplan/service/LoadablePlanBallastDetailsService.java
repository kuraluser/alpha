/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;

public interface LoadablePlanBallastDetailsService {

  public void saveLoadablePlanBallastDetailsList(
      List<LoadablePlanBallastDetails> ballastDetailsList, LoadingInformation loadingInformation);
}
