/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.gateway.domain.loadingplan.BerthDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingDetails;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import org.springframework.stereotype.Service;

/** Loading Information Tab Grid Data Populate here */
@Service
public class LoadingInformationServiceImpl implements LoadingInformationService {

  @Override
  public LoadingDetails getLoadingDetailsByPortRotationId(
      Long vesselId, Long voyageId, Long portRId) {
    return null;
  }

  @Override
  public BerthDetails getBerthDetailsByPortId(Long portId) {
    return null;
  }
}
