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

    final String OPERATION_TYPE = "DEP";

    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    log.info(
        "Get Loading Info, Active Voyage Number and Id {} ",
        activeVoyage.getVoyageNumber(),
        activeVoyage.getId());
    Optional<PortRotation> portRotation =
        activeVoyage.getPortRotations().stream().filter(v -> v.getId().equals(portRId)).findFirst();

    if (!portRotation.isPresent() || portRotation.get().getPortId() == null) {
      log.error("Port Rotation Id cannot be empty");
      throw new GenericServiceException(
          "Port Rotation Id Cannot be empty",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    LoadingPlanModels.LoadingInformation loadingInfo =
        this.loadingPlanGrpcService.fetchLoadingInformation(
            vesselId,
            activeVoyage.getId(),
            planId,
            activeVoyage.getPatternId(),
            portRotation.get().getId());

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
    LoadingRates loadingRates =
        this.loadingInformationService.getLoadingRateForVessel(
            loadingInfo.getLoadingRate(), vesselId);

    // Berth data from master, call to port Info service
    List<BerthDetails> masterBerthDetails =
        this.loadingInformationService.getMasterBerthDetailsByPortId(
            portRotation.get().getPortId());
    List<BerthDetails> loadingBerthDetails =
        this.loadingInformationService.buildLoadingPlanBerthDetails(
            loadingInfo.getLoadingBerthsList());

    // Call to vessel and set value from loading plan
    CargoMachineryInUse machineryInUse =
        this.loadingInformationService.getCargoMachinesInUserFromVessel(
            loadingInfo.getLoadingMachinesList(), vesselId);

    // from loading plan, user data + master data
    LoadingStages loadingStages =
        this.loadingInformationService.getLoadingStagesAndMasters(loadingInfo.getLoadingStage());

    // Topping Off Sequence
    List<ToppingOffSequence> toppingSequence =
        this.loadingInformationService.getToppingOffSequence(
            loadingInfo.getToppingOffSequenceList());

    // Call No. 1 To synoptic table api (voyage-status)
    CargoVesselTankDetails vesselTankDetails =
        this.loadingPlanGrpcService.fetchPortWiseCargoDetails(
            vesselId,
            activeVoyage.getId(),
            activeVoyage.getActiveLs().getId(),
            portRotation.get().getPortId(),
            portRotation.get().getPortOrder(),
            portRotation.get().getId(),
            OPERATION_TYPE);
    // Call No. 2 To synoptic data for loading (same as port rotation in above code)
    vesselTankDetails.setLoadableQuantityCargoDetails(
        this.loadingInformationService.getLoadablePlanCargoDetailsByPort(
            vesselId,
            activeVoyage.getPatternId(),
            OPERATION_TYPE,
            portRotation.get().getId(),
            portRotation.get().getPortId()));

    LoadingSequences loadingSequences =
        this.loadingInformationService.getLoadingSequence(loadingInfo.getLoadingDelays());

    var1.setLoadingInfoId(loadingInfo.getLoadingInfoId());
    var1.setSynopticTableId(loadingInfo.getSynopticTableId());
    var1.setIsLoadingInfoComplete(loadingInfo.getIsLoadingInfoComplete());
    var1.setLoadingDetails(loadingDetails);
    var1.setLoadingRates(loadingRates);
    var1.setBerthDetails(new LoadingBerthDetails(masterBerthDetails, loadingBerthDetails));
    var1.setMachineryInUses(machineryInUse);
    var1.setLoadingStages(loadingStages);
    var1.setToppingOffSequence(toppingSequence);
    var1.setCargoVesselTankDetails(vesselTankDetails);
    var1.setLoadingSequences(loadingSequences);
    var1.setResponseStatus(new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), null));
    return var1;
  }

  @Override
  public LoadingInformationResponse saveLoadingInformation(
      LoadingInformationRequest request, String correlationId) throws GenericServiceException {
    return this.loadingInformationService.saveLoadingInformation(request, correlationId);
  }
}
