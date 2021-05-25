/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.gateway.domain.voyage.VoyageResponse;

public interface LoadingPlanGrpcService {

  VoyageResponse getActiveVoyageDetails(Long vesselId);

  Object getPortRotationDetailsForActiveVoyage(Long vesselId);
}
