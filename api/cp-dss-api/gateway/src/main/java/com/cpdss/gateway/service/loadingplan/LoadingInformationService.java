/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.gateway.domain.loadingplan.BerthDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingDetails;

public interface LoadingInformationService {

  /**
   * Sunrise and Sunset Data must get and save at Synoptic Table in LS
   *
   * @param vesselId
   * @param voyageId
   * @param portRId
   * @return - LoadingDetails
   */
  LoadingDetails getLoadingDetailsByPortRotationId(Long vesselId, Long voyageId, Long portRId);

  /**
   * Berth Data is based on Port, So The Port Id must be pass to Port-Info Service
   *
   * @param portId - Long
   * @return - BerthDetails
   */
  BerthDetails getBerthDetailsByPortId(Long portId);
}
