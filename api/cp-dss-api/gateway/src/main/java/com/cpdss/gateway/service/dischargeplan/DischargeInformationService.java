/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.common.generated.discharge_plan.DischargeInformationServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargingPlanReply;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.dischargeplan.CowPlan;
import com.cpdss.gateway.domain.dischargeplan.DischargeInformation;
import com.cpdss.gateway.domain.dischargeplan.DischargeRates;
import com.cpdss.gateway.domain.dischargeplan.DischargingInformation;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanBuilderService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeInformationService {

  private final String OPERATION_TYPE_ARR = "ARR";

  @Autowired DischargeInformationGrpcService dischargeInformationGrpcService;

  @Autowired DischargeInformationBuilderService infoBuilderService;

  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;

  @Autowired LoadingInformationService loadingInformationService;

  @Autowired LoadingPlanBuilderService loadingPlanBuilderService;

  @GrpcClient("dischargeInformationService")
  private DischargeInformationServiceGrpc.DischargeInformationServiceBlockingStub
      dischargeInfoServiceStub;

  /**
   * Get Discharge Information from discharge-plan and master tables
   *
   * @return
   */
  public DischargeInformation getDischargeInformation(Long vesselId, Long voyageId, Long portRoId)
      throws GenericServiceException {

    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    log.info(
        "Get Loading Info, Active Voyage Number and Id {} ",
        activeVoyage.getVoyageNumber(),
        activeVoyage.getId());
    Optional<PortRotation> portRotation =
        activeVoyage.getDischargePortRotations().stream()
            .filter(v -> v.getId().equals(portRoId))
            .findFirst();

    if (!portRotation.isPresent() || portRotation.get().getPortId() == null) {
      log.error("Port Rotation Id cannot be empty");
      throw new GenericServiceException(
          "Port Rotation Id Cannot be empty",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    com.cpdss.common.generated.discharge_plan.DischargeInformation disRpcReplay =
        this.dischargeInformationGrpcService.getDischargeInfoRpc(vesselId, voyageId, portRoId);

    DischargeInformation dischargeInformation = new DischargeInformation();
    if (activeVoyage.getActiveDs() != null) {
      dischargeInformation.setDischargeInfoId(disRpcReplay.getDischargeInfoId());
      dischargeInformation.setSynopticTableId(disRpcReplay.getSynopticTableId());
      dischargeInformation.setDischargeStudyId(activeVoyage.getActiveDs().getId());
      dischargeInformation.setDischargeStudyName(activeVoyage.getActiveDs().getName());
    }

    // discharge details
    LoadingDetails dischargeDetails =
        this.infoBuilderService.buildDischargeDetailFromMessage(
            disRpcReplay.getDischargeDetails(),
            portRotation.get().getPortId(),
            portRotation.get().getId());

    // discharge rates
    DischargeRates dischargeRates =
        this.infoBuilderService.buildDischargeRatesFromMessage(disRpcReplay.getDischargeRate());

    // discharge berth (master data)
    List<BerthDetails> availableBerths =
        this.loadingInformationService.getMasterBerthDetailsByPortId(
            portRotation.get().getPortId());

    // discharge berth (selected data)
    List<BerthDetails> selectedBerths =
        this.infoBuilderService.buildDischargeBerthsFromMessage(disRpcReplay.getBerthDetailsList());

    // discharge machines (manifold, bottom line, pumps)
    CargoMachineryInUse machineryInUse =
        this.infoBuilderService.buildDischargeMachinesFromMessage(
            disRpcReplay.getMachineInUseList(), vesselId);

    // discharge stages ()
    LoadingStages dischargeStages =
        this.loadingInformationService.getLoadingStagesAndMasters(disRpcReplay.getDischargeStage());

    // discharge sequence (reason/delay)
    LoadingSequences dischargeSequences =
        this.infoBuilderService.buildDischargeSequencesAndDelayFromMessage(
            disRpcReplay.getDischargeDelay());

    // cow plan
    CowPlan cowPlan = this.infoBuilderService.buildDischargeCowPlan(disRpcReplay.getCowPlan());

    // Call 1 to DS for cargo details
    CargoVesselTankDetails vesselTankDetails =
        this.loadingPlanGrpcService.fetchPortWiseCargoDetails(
            vesselId,
            activeVoyage.getId(),
            activeVoyage.getActiveDs().getId(),
            portRotation.get().getPortId(),
            portRotation.get().getPortOrder(),
            portRotation.get().getId(),
            OPERATION_TYPE_ARR); // Discharge Info needed Arrival Conditions

    // Call No. 2 To synoptic data for loading (same as port rotation in above code)
    vesselTankDetails.setLoadableQuantityCargoDetails(
        this.loadingInformationService.getLoadablePlanCargoDetailsByPort(
            vesselId,
            activeVoyage.getDischargePatternId(),
            OPERATION_TYPE_ARR, // Discharge Info needed Arrival Conditions
            portRotation.get().getId(),
            portRotation.get().getPortId()));

    dischargeInformation.setCargoVesselTankDetails(vesselTankDetails);

    dischargeInformation.setDischargeDetails(dischargeDetails);
    dischargeInformation.setDischargeRates(dischargeRates);
    dischargeInformation.setBerthDetails(new LoadingBerthDetails(availableBerths, selectedBerths));
    dischargeInformation.setMachineryInUses(machineryInUse);

    dischargeInformation.setDischargeStages(dischargeStages);
    dischargeInformation.setDischargeSequences(dischargeSequences);
    dischargeInformation.setCowPlan(cowPlan);
    return dischargeInformation;
  }

  public LoadingPlanResponse getDischargingPlan(
      Long vesselId, Long voyageId, Long infoId, Long portRotationId)
      throws GenericServiceException {

    final String OPERATION_TYPE = "DEP";
    LoadingPlanResponse loadingPlanResponse = new LoadingPlanResponse();

    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    log.info(
        "Get dischargin Plan, Active Voyage Number and Id {} ",
        activeVoyage.getVoyageNumber(),
        activeVoyage.getId());
    Optional<PortRotation> portRotation =
        activeVoyage.getPortRotations().stream()
            .filter(v -> v.getId().equals(portRotationId))
            .findFirst();

    DischargeInformationRequest.Builder builder = DischargeInformationRequest.newBuilder();
    builder.setDischargeInfoId(infoId);
    builder.setDischargePatternId(activeVoyage.getPatternId());
    builder.setPortRotationId(portRotation.get().getId());
    builder.setVoyageId(voyageId);
    builder.setVesselId(vesselId);
    DischargingPlanReply planReply =
        this.dischargeInfoServiceStub.getDischargingPlan(builder.build());

    DischargingInformation dischargingInformation = new DischargingInformation();
    // from loading info table, loading plan service
    LoadingRates loadingRates =
        this.loadingInformationService.getLoadingRateForVessel(
            planReply.getDischargingInformation().getLoadingRate(), vesselId);

    // Topping Off Sequence
    List<ToppingOffSequence> toppingSequence =
        this.loadingInformationService.getToppingOffSequence(
            planReply.getDischargingInformation().getToppingOffSequenceList());
    // buildTankLayout(vesselId, loadingPlanResponse);
    dischargingInformation.setDischargingRates(loadingRates);
    dischargingInformation.setToppingOffSequence(toppingSequence);

    LoadingDetails loadingDetails =
        this.loadingInformationService.getLoadingDetailsByPortRotationId(
            planReply.getDischargingInformation().getLoadingDetail(),
            vesselId,
            activeVoyage.getId(),
            portRotationId,
            portRotation.get().getPortId());
    dischargingInformation.setDischargingDetails(loadingDetails);

    // Berth data from master, call to port Info service
    List<BerthDetails> masterBerthDetails =
        this.loadingInformationService.getMasterBerthDetailsByPortId(
            portRotation.get().getPortId());
    List<BerthDetails> loadingBerthDetails =
        this.loadingInformationService.buildLoadingPlanBerthDetails(
            planReply.getDischargingInformation().getLoadingBerthsList());

    LoadingBerthDetails berthDetails = new LoadingBerthDetails();
    berthDetails.setAvailableBerths(masterBerthDetails);
    berthDetails.setSelectedBerths(loadingBerthDetails);
    dischargingInformation.setBerthDetails(berthDetails);

    CargoMachineryInUse machineryInUse =
        this.loadingInformationService.getCargoMachinesInUserFromVessel(
            planReply.getDischargingInformation().getLoadingMachinesList(), vesselId);
    dischargingInformation.setMachineryInUses(machineryInUse);

    LoadingStages loadingStages =
        this.loadingInformationService.getLoadingStagesAndMasters(
            planReply.getDischargingInformation().getLoadingStage());
    dischargingInformation.setDischargingStages(loadingStages);

    dischargingInformation.setDischargingInfoStatusId(
        planReply.getDischargingInformation().getLoadingInfoStatusId());
    dischargingInformation.setDischargingPlanArrStatusId(
        planReply.getDischargingInformation().getLoadingPlanArrStatusId());
    dischargingInformation.setDischargingPlanDepStatusId(
        planReply.getDischargingInformation().getLoadingPlanDepStatusId());
    dischargingInformation.setLoadablePatternId(
        planReply.getDischargingInformation().getLoadablePatternId());

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
    dischargingInformation.setCargoVesselTankDetails(vesselTankDetails);

    LoadingSequences loadingSequences =
        this.loadingInformationService.getLoadingSequence(
            planReply.getDischargingInformation().getLoadingDelays());
    dischargingInformation.setDischargingSequences(loadingSequences);

    loadingPlanResponse.setDischargingInformation(dischargingInformation);

    List<LoadablePlanBallastDetails> loadablePlanBallastDetails =
        loadingPlanGrpcService.fetchLoadablePlanBallastDetails(
            activeVoyage.getPatternId(), portRotation.get().getId());
    loadingPlanResponse.setPlanBallastDetails(
        loadingPlanBuilderService.buildLoadingPlanBallastFromRpc(
            planReply.getPortDischargingPlanBallastDetailsList(), loadablePlanBallastDetails));
    loadingPlanResponse.setPlanStowageDetails(
        loadingPlanBuilderService.buildLoadingPlanStowageFromRpc(
            planReply.getPortDischargingPlanStowageDetailsList()));
    loadingPlanResponse.setPlanRobDetails(
        loadingPlanBuilderService.buildLoadingPlanRobFromRpc(
            planReply.getPortDischargingPlanRobDetailsList()));
    loadingPlanResponse.setPlanStabilityParams(
        loadingPlanBuilderService.buildLoadingPlanStabilityParamFromRpc(
            planReply.getPortDischargingPlanStabilityParametersList()));

    return loadingPlanResponse;
  }
}
