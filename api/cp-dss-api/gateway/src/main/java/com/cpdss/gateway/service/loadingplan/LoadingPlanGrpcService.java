/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.gateway.domain.voyage.VoyageResponse;

public interface LoadingPlanGrpcService {

  VoyageResponse getActiveVoyageDetails(Long vesselId) throws GenericServiceException;

  Object getPortRotationDetailsForActiveVoyage(Long vesselId);

  LoadableStudy.LoadingSynopticResponse fetchSynopticRecordForPortRotationArrivalCondition(
      Long portRId) throws GenericServiceException;

  PortInfo.PortDetail fetchPortDetailByPortId(Long portId) throws GenericServiceException;
}
