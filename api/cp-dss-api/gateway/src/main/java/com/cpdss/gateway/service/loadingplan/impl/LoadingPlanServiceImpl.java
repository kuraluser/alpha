/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadingPlanServiceImpl implements LoadingPlanService {

  @Autowired LoadingInformationService loadingInformationService;

  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;

  /**
   * Port Rotation From Loading Plan DB
   *
   * @param vesselId Numeric Long
   * @param portRId Numeric Long
   * @return Not decided
   * @throws GenericServiceException
   */
  @Override
  public Object getLoadingPortRotationDetails(Long vesselId, Long portRId)
      throws GenericServiceException {
    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    log.info("Active Voyage {} For Vessel Id {}", activeVoyage.getVoyageNumber(), vesselId);
    // TO DO
    return activeVoyage.getPortRotations();
  }

  /**
   * 1. Loading Details from LS 2. loading rate
   *
   * @param vesselId
   * @param planId
   * @param portRId
   * @return
   */
  @Override
  public LoadingInformation getLoadingInformationByPortRotation(
      Long vesselId, Long planId, Long portRId) throws GenericServiceException {
    LoadingInformation var1 = new LoadingInformation();

    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    log.info(
        "Get Loading Info, Active Voyage Number and Id {} ",
        activeVoyage.getVoyageNumber(),
        activeVoyage.getId());
    Optional<PortRotation> portRotation =
        activeVoyage.getPortRotations().stream().filter(v -> v.getId().equals(portRId)).findFirst();

    LoadingPlanModels.LoadingInformation loadingInfo =
        this.loadingPlanGrpcService.fetchLoadingInformation(
            vesselId, activeVoyage.getId(), planId, null);

    if (!portRotation.isPresent() || portRotation.get().getPortId() == null) {
      log.error("Port Rotation Id cannot be empty");
      throw new GenericServiceException(
          "Port Rotation Id Cannot be empty",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info(
        "Get Loading Info, Port rotation id is available in Active Voyage, Port Id is {}",
        portRotation.get().getPortId());

    // call to synoptic/port master table for sunrise/sunset data
    LoadingDetails loadingDetails =
        this.loadingInformationService.getLoadingDetailsByPortRotationId(
            loadingInfo.getLoadingDetail(),
            vesselId,
            activeVoyage.getId(),
            portRId,
            portRotation.get().getPortId());

    // from loading info table, loading plan service
    LoadingRates loadingRates = this.loadingInformationService.getLoadingRateForVessel(vesselId);

    // all done, call to port Info service
    List<BerthDetails> berthDetails =
        this.loadingInformationService.getBerthDetailsByPortId(portRotation.get().getPortId());

    // all done, call to vessel service
    CargoMachineryInUse machineryInUse =
        this.loadingInformationService.getCargoMachinesInUserFromVessel(vesselId);

    var1.setLoadingDetails(loadingDetails);
    var1.setBerthDetails(new LoadingBerthDetails(berthDetails));
    var1.setMachineryInUses(machineryInUse);
    var1.setResponseStatus(new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), null));
    return var1;
  }
}
