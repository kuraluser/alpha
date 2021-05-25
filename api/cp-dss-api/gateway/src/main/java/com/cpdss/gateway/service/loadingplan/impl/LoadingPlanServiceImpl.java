/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadingPlanServiceImpl implements LoadingPlanService {

  @Autowired LoadingInformationService loadingInformationService;

  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;

  @Override
  public Object getLoadingPortRotationDetails(Long vesselId, Long portRId) {

    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    log.info("Active voyage for the vessel {}", vesselId);

    return null;
  }

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

    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    Optional<PortRotation> portRotation =
        activeVoyage.getPortRotations().stream().filter(v -> v.getId().equals(portRId)).findFirst();
    if (!portRotation.isPresent()) {
      // throw error, we cannot go forward
    }

    // call to synoptic
    LoadingDetails loadingDetails =
        this.loadingInformationService.getLoadingDetailsByPortRotationId(
            vesselId, voyageId, portRId);

    // from loading info table, loading plan service
    LoadingRates loadingRates = this.loadingInformationService.getLoadingRateForVessel(vesselId);

    // done
    List<BerthDetails> berthDetails =
        this.loadingInformationService.getBerthDetailsByPortId(portRotation.get().getPortId());

    // all done
    CargoMachineryInUse machineryInUse =
        this.loadingInformationService.getCargoMachinesInUserFromVessel(vesselId);

    return null;
  }
}
