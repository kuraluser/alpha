/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationReply;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.common.generated.discharge_plan.DischargeInformationServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargingPlanReply;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.common.GatewayConstants;
import com.cpdss.gateway.domain.DischargeQuantityCargoDetails;
import com.cpdss.gateway.domain.LoadingUpdateUllageResponse;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.domain.UllageBillReply;
import com.cpdss.gateway.domain.UllageBillRequest;
import com.cpdss.gateway.domain.dischargeplan.CowPlan;
import com.cpdss.gateway.domain.dischargeplan.DischargeInformation;
import com.cpdss.gateway.domain.dischargeplan.DischargePlanResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargeRates;
import com.cpdss.gateway.domain.dischargeplan.DischargeUpdateUllageResponse;
import com.cpdss.gateway.domain.dischargeplan.PostDischargeStage;
import com.cpdss.gateway.domain.loadingplan.BerthDetails;
import com.cpdss.gateway.domain.loadingplan.CargoMachineryInUse;
import com.cpdss.gateway.domain.loadingplan.CargoVesselTankDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingBerthDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingSequences;
import com.cpdss.gateway.domain.loadingplan.LoadingStages;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanBuilderService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import com.cpdss.gateway.utility.AdminRuleValueExtract;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeInformationService {

  @Autowired DischargeInformationGrpcService dischargeInformationGrpcService;

  @Autowired DischargeInformationBuilderService infoBuilderService;

  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;

  @Autowired LoadingInformationService loadingInformationService;

  @Autowired LoadingPlanBuilderService dischargingPlanBuilderService;

  @Autowired LoadingPlanService loadingPlanService;

  @Autowired VesselInfoService vesselInfoService;

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

    // RPC call to vessel info, Get Rules (default value for Discharge Info)
    RuleResponse ruleResponse =
        vesselInfoService.getRulesByVesselIdAndSectionId(
            vesselId, GatewayConstants.DISCHARGING_RULE_MASTER_ID, null, null);
    AdminRuleValueExtract extract =
        AdminRuleValueExtract.builder().plan(ruleResponse.getPlan()).build();

    // discharge details
    LoadingDetails dischargeDetails =
        this.infoBuilderService.buildDischargeDetailFromMessage(
            disRpcReplay.getDischargeDetails(),
            portRotation.get().getPortId(),
            portRotation.get().getId(),
            extract);

    // discharge rates
    DischargeRates dischargeRates =
        this.infoBuilderService.buildDischargeRatesFromMessage(
            disRpcReplay.getDischargeRate(), extract);

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
    CowPlan cowPlan =
        this.infoBuilderService.buildDischargeCowPlan(disRpcReplay.getCowPlan(), extract);

    // Call 1 to DS for cargo details
    CargoVesselTankDetails vesselTankDetails =
        this.loadingPlanGrpcService.fetchPortWiseCargoDetails(
            vesselId,
            activeVoyage.getId(),
            activeVoyage.getActiveDs().getId(),
            portRotation.get().getPortId(),
            portRotation.get().getPortOrder(),
            portRotation.get().getId(),
            GatewayConstants.OPERATION_TYPE_ARR); // Discharge Info needed Arrival Conditions

    // Call No. 2 To synoptic data for loading (same as port rotation in above code)
    vesselTankDetails.setDischargeQuantityCargoDetails(
        this.loadingInformationService.getDischargePlanCargoDetailsByPort(
            vesselId,
            activeVoyage.getDischargePatternId(),
            GatewayConstants.OPERATION_TYPE_ARR, // Discharge Info needed Arrival Conditions
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

  public DischargePlanResponse getDischargingPlan(
      Long vesselId, Long voyageId, Long infoId, Long portRotationId, String correlationId)
      throws GenericServiceException {
    DischargePlanResponse dischargingPlanResponse = new DischargePlanResponse();

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
      dischargeInformation.setDischargePatternId(activeVoyage.getDischargePatternId());
    }
    // dischargeInformation.setDischargePlanArrStatusId(planReply.getDischargingInformation());
    // discharge rates
    DischargeRates dischargeRates =
        this.infoBuilderService.buildDischargeRatesFromMessage(
            planReply.getDischargingInformation().getDischargeRate(), null); // rule not needed
    dischargeInformation.setDischargeRates(dischargeRates);

    // re using the already written call for loading. and copying the tank details to discharging
    LoadingPlanResponse lp = new LoadingPlanResponse();
    loadingPlanService.buildTankLayout(vesselId, lp);
    BeanUtils.copyProperties(lp, dischargingPlanResponse);
    // discharge berth (master data)
    List<BerthDetails> availableBerths =
        this.loadingInformationService.getMasterBerthDetailsByPortId(
            portRotation.get().getPortId());
    availableBerths.stream()
        .forEach(
            berth -> {
              berth.setDischargingBerthId(berth.getLoadingBerthId());
              berth.setDischargeInfoId(berth.getLoadingInfoId());
            });
    // discharge berth (selected data)
    List<BerthDetails> selectedBerths =
        this.infoBuilderService.buildDischargeBerthsFromMessage(
            planReply.getDischargingInformation().getBerthDetailsList());
    selectedBerths.stream()
        .forEach(
            berth -> {
              berth.setDischargingBerthId(berth.getLoadingBerthId());
              berth.setDischargeInfoId(berth.getLoadingInfoId());
            });
    LoadingBerthDetails berthDetails = new LoadingBerthDetails();
    berthDetails.setAvailableBerths(availableBerths);
    berthDetails.setSelectedBerths(selectedBerths);
    dischargeInformation.setBerthDetails(berthDetails);

    // discharge machines (manifold, bottom line, pumps)
    CargoMachineryInUse machineryInUse =
        this.infoBuilderService.buildDischargeMachinesFromMessage(
            planReply.getDischargingInformation().getMachineInUseList(), vesselId);
    machineryInUse.setLoadingMachinesInUses(null);
    dischargeInformation.setMachineryInUses(machineryInUse);

    // discharge stages ()
    LoadingStages dischargeStages =
        this.loadingInformationService.getLoadingStagesAndMasters(
            planReply.getDischargingInformation().getDischargeStage());
    dischargeInformation.setDischargeStages(dischargeStages);

    // post discharge stage
    PostDischargeStage postDischargeStage =
        this.loadingInformationService.getPostDischargeStage(
            planReply.getDischargingInformation().getPostDischargeStageTime());
    dischargeInformation.setPostDischargeStageTime(postDischargeStage);
    CargoNominationReply nominations =
        loadingPlanService.getCargoNominationsByStudyId(activeVoyage.getActiveDs().getId());
    CargoVesselTankDetails vesselTankDetails =
        this.loadingPlanGrpcService.fetchPortWiseCargoDetails(
            vesselId,
            activeVoyage.getId(),
            activeVoyage.getActiveDs().getId(),
            portRotation.get().getPortId(),
            portRotation.get().getPortOrder(),
            portRotation.get().getId(),
            GatewayConstants.OPERATION_TYPE_ARR);
    // Call No. 2 To synoptic data for loading (same as port rotation in above code)
    vesselTankDetails.setDischargeQuantityCargoDetails(
        this.loadingInformationService.getDischargePlanCargoDetailsByPort(
            vesselId,
            activeVoyage.getPatternId(),
            GatewayConstants.OPERATION_TYPE_ARR,
            portRotation.get().getId(),
            portRotation.get().getPortId()));
    vesselTankDetails
        .getDischargeQuantityCargoDetails()
        .forEach(
            dqcd -> {
              CargoNominationDetail cargoDetail =
                  nominations.getCargoNominationsList().stream()
                      .filter(detail -> dqcd.getCargoNominationId().equals(detail.getId()))
                      .findFirst()
                      .orElse(null);
              dqcd.setBlFigure(new BigDecimal(cargoDetail.getQuantity()));

              BigDecimal sum =
                  planReply.getPortDischargingPlanStowageDetailsList().stream()
                      .filter(
                          stowage ->
                              dqcd.getCargoNominationId().equals(stowage.getCargoNominationId())
                                  && stowage.getValueType() == 2
                                  && stowage.getConditionType() == 2)
                      .map(detail -> new BigDecimal(detail.getQuantity()))
                      .reduce(BigDecimal.ZERO, BigDecimal::add);
              dqcd.setShipFigure(sum);
            });
    dischargeInformation.setCargoVesselTankDetails(vesselTankDetails);

    // discharge sequence (reason/delay)
    LoadingSequences dischargeSequences =
        this.infoBuilderService.buildDischargeSequencesAndDelayFromMessage(
            planReply.getDischargingInformation().getDischargeDelay());
    dischargeInformation.setDischargeSequences(dischargeSequences);

    // discharge details
    LoadingDetails dischargeDetails =
        this.infoBuilderService.buildDischargeDetailFromMessage(
            planReply.getDischargingInformation().getDischargeDetails(),
            portRotation.get().getPortId(),
            portRotation.get().getId(),
            null);
    dischargeInformation.setDischargeDetails(dischargeDetails);

    // RPC call to vessel info, Get Rules (default value for Discharge Info)
    RuleResponse ruleResponse =
        vesselInfoService.getRulesByVesselIdAndSectionId(
            vesselId, GatewayConstants.DISCHARGING_RULE_MASTER_ID, null, null);
    AdminRuleValueExtract extract =
        AdminRuleValueExtract.builder().plan(ruleResponse.getPlan()).build();
    // cow plan
    CowPlan cowPlan =
        this.infoBuilderService.buildDischargeCowPlan(
            planReply.getDischargingInformation().getCowPlan(), extract);
    dischargeInformation.setCowPlan(cowPlan);

    dischargingPlanResponse.setDischargingInformation(dischargeInformation);
    List<LoadableStudy.LoadableQuantityCargoDetails> portCargos =
        this.loadingPlanGrpcService.fetchLoadablePlanCargoDetails(
            activeVoyage.getDischargePatternId(),
            GatewayConstants.OPERATION_TYPE_ARR,
            portRotationId,
            portRotation.get().getPortId(),
            false,
            Common.PLANNING_TYPE.DISCHARGE_STUDY);
    List<DischargeQuantityCargoDetails> currentPortCargos =
        loadingInformationService.buildDischargePlanQuantity(portCargos, vesselId);
    dischargingPlanResponse.setCurrentPortCargos(currentPortCargos);
    dischargingPlanResponse.setPlanBallastDetails(
        dischargingPlanBuilderService.buildLoadingPlanBallastFromRpc(
            planReply.getPortDischargingPlanBallastDetailsList()));
    dischargingPlanResponse.setPlanStowageDetails(
        dischargingPlanBuilderService.buildLoadingPlanStowageFromRpc(
            planReply.getPortDischargingPlanStowageDetailsList()));
    dischargingPlanResponse.setPlanRobDetails(
        dischargingPlanBuilderService.buildLoadingPlanRobFromRpc(
            planReply.getPortDischargingPlanRobDetailsList()));
    dischargingPlanResponse.setPlanStabilityParams(
        dischargingPlanBuilderService.buildLoadingPlanStabilityParamFromRpc(
            planReply.getPortDischargingPlanStabilityParametersList()));
    dischargingPlanResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return dischargingPlanResponse;
  }

  public DischargeUpdateUllageResponse getUpdateUllageDetails(
      Long vesselId, Long patternId, Long portRotationId, String operationType)
      throws GenericServiceException {
    DischargeUpdateUllageResponse response = new DischargeUpdateUllageResponse();
    LoadingUpdateUllageResponse dischargeUllageResponse =
        loadingPlanService.getUpdateUllageDetails(
            vesselId, patternId, portRotationId, operationType, true);
    BeanUtils.copyProperties(dischargeUllageResponse, response);
    response.setPortDischargePlanBallastDetails(
        dischargeUllageResponse.getPortLoadablePlanBallastDetails());
    response.setPortDischargePlanRobDetails(
        dischargeUllageResponse.getPortLoadablePlanRobDetails());
    response.setPortDischargePlanStowageDetails(
        dischargeUllageResponse.getPortLoadablePlanStowageDetails());
    response.setDischargePlanCommingleDetails(
        dischargeUllageResponse.getLoadablePlanCommingleDetails());

    return response;
  }

  public UllageBillReply updateUllage(UllageBillRequest request, String correlationId)
      throws GenericServiceException {

    return loadingPlanService.getLoadableStudyShoreTwo(correlationId, request, true);
  }
}
