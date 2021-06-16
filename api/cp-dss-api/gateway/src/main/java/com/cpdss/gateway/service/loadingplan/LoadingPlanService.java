/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.domain.loadingplan.LoadingInformation;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationRequest;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationResponse;

public interface LoadingPlanService {

  Object getLoadingPortRotationDetails(Long vesselId, Long portRId) throws GenericServiceException;

  LoadingInformation getLoadingInformationByPortRotation(Long vesselId, Long planId, Long portRId)
      throws GenericServiceException;

  public LoadingInformationResponse saveLoadingInformation(LoadingInformationRequest request)
      throws GenericServiceException;
}
