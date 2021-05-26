/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.domain.voyage.VoyageResponse;

public interface LoadingPlanGrpcService {

  VoyageResponse getActiveVoyageDetails(Long vesselId) throws GenericServiceException;

  Object getPortRotationDetailsForActiveVoyage(Long vesselId);
}
