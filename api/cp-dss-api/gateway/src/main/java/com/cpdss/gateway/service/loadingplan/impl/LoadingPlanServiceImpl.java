/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.gateway.domain.loadingplan.BerthDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationResponse;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadingPlanServiceImpl implements LoadingPlanService {

  @Autowired LoadingInformationService loadingInformationService;

  /**
   * 1. Loading Details from LS 2. loading rate
   *
   * @param vesselId
   * @param voyageId
   * @param planId
   * @param portRId
   * @return
   */
  @Override
  public LoadingInformationResponse getLoadingInformationByPortRotation(
      Long vesselId, Long voyageId, Long planId, Long portRId) {

    LoadingDetails loadingDetails =
        this.loadingInformationService.getLoadingDetailsByPortRotationId(
            vesselId, voyageId, portRId);

    // currently we don't have port id, only have port rotation id
    BerthDetails berthDetails = this.loadingInformationService.getBerthDetailsByPortId(0l);
    return null;
  }
}
