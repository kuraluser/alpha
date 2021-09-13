/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.common.generated.discharge_plan.DischargeInformationServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargingPlanReply;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.common.GatewayConstants;
import com.cpdss.gateway.domain.LoadingUpdateUllageResponse;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.dischargeplan.CowPlan;
import com.cpdss.gateway.domain.dischargeplan.DischargeInformation;
import com.cpdss.gateway.domain.dischargeplan.DischargeRates;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlanResponse;
import com.cpdss.gateway.domain.loadingplan.BerthDetails;
import com.cpdss.gateway.domain.loadingplan.CargoMachineryInUse;
import com.cpdss.gateway.domain.loadingplan.CargoVesselTankDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingBerthDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingSequences;
import com.cpdss.gateway.domain.loadingplan.LoadingStages;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanBuilderService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired LoadingPlanService loadingPlanService;

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

  public DischargingPlanResponse getDischargingPlan(
      Long vesselId, Long voyageId, Long infoId, Long portRotationId, String correlationId)
      throws GenericServiceException {
    DischargingPlanResponse dischargingPlanResponse = new DischargingPlanResponse();

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
    if (!GatewayConstants.SUCCESS.equals(planReply.getResponseStatus().getStatus())) {
      log.error("Port Rotation Id cannot be empty");
      throw new GenericServiceException(
          "Port Rotation Id Cannot be empty",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    DischargeInformation dischargeInformation = new DischargeInformation();
    if (activeVoyage.getActiveDs() != null) {
      dischargeInformation.setDischargeInfoId(
          planReply.getDischargingInformation().getDischargeInfoId());
      dischargeInformation.setSynopticTableId(
          planReply.getDischargingInformation().getSynopticTableId());
      dischargeInformation.setDischargeStudyId(activeVoyage.getActiveDs().getId());
      dischargeInformation.setDischargeStudyName(activeVoyage.getActiveDs().getName());
    }

    // discharge rates
    DischargeRates dischargeRates =
        this.infoBuilderService.buildDischargeRatesFromMessage(
            planReply.getDischargingInformation().getDischargeRate());
    dischargeInformation.setDischargeRates(dischargeRates);

    // discharge berth (master data)
    List<BerthDetails> availableBerths =
        this.loadingInformationService.getMasterBerthDetailsByPortId(
            portRotation.get().getPortId());
    availableBerths.stream()
        .forEach(
            berth -> {
              berth.setDischargingBerthId(berth.getLoadingBerthId());
              berth.setDischargingInfoId(berth.getLoadingInfoId());
            });
    // discharge berth (selected data)
    List<BerthDetails> selectedBerths =
        this.infoBuilderService.buildDischargeBerthsFromMessage(
            planReply.getDischargingInformation().getBerthDetailsList());
    selectedBerths.stream()
        .forEach(
            berth -> {
              berth.setDischargingBerthId(berth.getLoadingBerthId());
              berth.setDischargingInfoId(berth.getLoadingInfoId());
            });
    LoadingBerthDetails berthDetails = new LoadingBerthDetails();
    berthDetails.setAvailableBerths(availableBerths);
    berthDetails.setSelectedBerths(selectedBerths);
    dischargeInformation.setBerthDetails(berthDetails);

    // discharge machines (manifold, bottom line, pumps)
    CargoMachineryInUse machineryInUse =
        this.infoBuilderService.buildDischargeMachinesFromMessage(
            planReply.getDischargingInformation().getMachineInUseList(), vesselId);
    dischargeInformation.setMachineryInUses(machineryInUse);

    // discharge stages ()
    LoadingStages dischargeStages =
        this.loadingInformationService.getLoadingStagesAndMasters(
            planReply.getDischargingInformation().getDischargeStage());
    dischargeInformation.setDischargeStages(dischargeStages);

    CargoVesselTankDetails vesselTankDetails =
        this.loadingPlanGrpcService.fetchPortWiseCargoDetails(
            vesselId,
            activeVoyage.getId(),
            activeVoyage.getActiveDs().getId(),
            portRotation.get().getPortId(),
            portRotation.get().getPortOrder(),
            portRotation.get().getId(),
            OPERATION_TYPE_ARR);
    // Call No. 2 To synoptic data for loading (same as port rotation in above code)
    vesselTankDetails.setDischargeQuantityCargoDetails(
        this.loadingInformationService.getDischargePlanCargoDetailsByPort(
            vesselId,
            activeVoyage.getPatternId(),
            OPERATION_TYPE_ARR,
            portRotation.get().getId(),
            portRotation.get().getPortId()));
    dischargeInformation.setCargoVesselTankDetails(vesselTankDetails);

    // discharge sequence (reason/delay)
    LoadingSequences dischargeSequences =
        this.infoBuilderService.buildDischargeSequencesAndDelayFromMessage(
            planReply.getDischargingInformation().getDischargeDelay());
    dischargeInformation.setDischargeSequences(dischargeSequences);

    dischargingPlanResponse.setDischargingInformation(dischargeInformation);

    List<LoadablePlanBallastDetails> loadablePlanBallastDetails =
        loadingPlanGrpcService.fetchLoadablePlanBallastDetails(
            activeVoyage.getPatternId(), portRotation.get().getId());
    dischargingPlanResponse.setPlanBallastDetails(
        loadingPlanBuilderService.buildLoadingPlanBallastFromRpc(
            planReply.getPortDischargingPlanBallastDetailsList(), loadablePlanBallastDetails));
    dischargingPlanResponse.setPlanStowageDetails(
        loadingPlanBuilderService.buildLoadingPlanStowageFromRpc(
            planReply.getPortDischargingPlanStowageDetailsList()));
    dischargingPlanResponse.setPlanRobDetails(
        loadingPlanBuilderService.buildLoadingPlanRobFromRpc(
            planReply.getPortDischargingPlanRobDetailsList()));
    dischargingPlanResponse.setPlanStabilityParams(
        loadingPlanBuilderService.buildLoadingPlanStabilityParamFromRpc(
            planReply.getPortDischargingPlanStabilityParametersList()));
    dischargingPlanResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return dischargingPlanResponse;
  }

  public LoadingUpdateUllageResponse getUpdateUllageDetails(
      Long vesselId, Long patternId, Long portRotationId, String operationType)
      throws GenericServiceException {

    return loadingPlanService.getUpdateUllageDetails(
        vesselId, patternId, portRotationId, operationType, true);
  }
}
