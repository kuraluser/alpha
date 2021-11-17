/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import static com.cpdss.gateway.common.GatewayConstants.DISCHARGING_RULE_MASTER_ID;
import static com.cpdss.gateway.common.GatewayConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.AlgoErrorReply;
import com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationReply;
import com.cpdss.common.generated.LoadableStudy.JsonRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.generated.discharge_plan.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.common.GatewayConstants;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.dischargeplan.CowPlan;
import com.cpdss.gateway.domain.dischargeplan.DischargeInformation;
import com.cpdss.gateway.domain.dischargeplan.DischargePlanResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargeRates;
import com.cpdss.gateway.domain.dischargeplan.DischargeUpdateUllageResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInfoAlgoStatus;
import com.cpdss.gateway.domain.dischargeplan.DischargingInformationRequest;
import com.cpdss.gateway.domain.dischargeplan.DischargingInformationResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlanAlgoRequest;
import com.cpdss.gateway.domain.dischargeplan.PostDischargeStage;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.LoadableStudyService;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanBuilderService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import com.cpdss.gateway.utility.AdminRuleValueExtract;
import com.cpdss.gateway.utility.RuleUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Slf4j
@Service
public class DischargeInformationService {

  @Autowired DischargeInformationGrpcService dischargeInformationGrpcService;

  @Autowired DischargeInformationBuilderService infoBuilderService;

  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;

  @Autowired LoadingInformationService loadingInformationService;

  @Autowired LoadingPlanBuilderService dischargingPlanBuilderService;

  @Autowired LoadingPlanService loadingPlanService;
  @Autowired DischargingSequenceService dischargingSequenceService;
  @Autowired VesselInfoService vesselInfoService;
  @Autowired LoadableStudyService loadableStudyService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("dischargeInformationService")
  private DischargeInformationServiceGrpc.DischargeInformationServiceBlockingStub
      dischargeInfoServiceStub;

  @GrpcClient("dischargeInformationService")
  DischargePlanServiceGrpc.DischargePlanServiceBlockingStub dischargePlanServiceBlockingStub;

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;
  /**
   * Get Discharge Information from discharge-plan and master tables
   *
   * @return
   */
  public DischargeInformation getDischargeInformation(Long vesselId, Long voyageId, Long portRoId)
      throws GenericServiceException {

    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    log.info(
        "Get Discharging Info, Active Voyage Number and Id {} ",
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

    // Common Fields
    if (activeVoyage.getActiveDs() != null) {
      dischargeInformation.setDischargeInfoId(disRpcReplay.getDischargeInfoId());
      dischargeInformation.setSynopticTableId(disRpcReplay.getSynopticTableId());
      dischargeInformation.setDischargeStudyId(activeVoyage.getActiveDs().getId());
      dischargeInformation.setDischargeStudyName(activeVoyage.getActiveDs().getName());
    }
    dischargeInformation.setDischargeSlopTanksFirst(disRpcReplay.getDischargeSlopTanksFirst());
    dischargeInformation.setDischargeCommingledCargoSeparately(
        disRpcReplay.getDischargeCommingledCargoSeparately());
    dischargeInformation.setIsDischargeInfoComplete(disRpcReplay.getIsDischargeInfoComplete());
    dischargeInformation.setIsDischargePlanGenerated(disRpcReplay.getIsDischargingPlanGenerated());
    dischargeInformation.setIsDischargeSequenceGenerated(
        disRpcReplay.getIsDischargingSequenceGenerated());
    dischargeInformation.setDischargeInfoStatusId(disRpcReplay.getDischargingInfoStatusId());
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

    // Post discharge rate
    this.infoBuilderService.buildPostDischargeRates(
        disRpcReplay.getPostDischargeStageTime(), extract, dischargeInformation);

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
            GatewayConstants.OPERATION_TYPE_DEP, // Discharge Info needed Arrival Conditions
            portRotation.get().getId(),
            portRotation.get().getPortId()));

    // setting discharge cargo nomination id
    this.setDischargeCargoNominationId(vesselTankDetails);

    dischargeInformation.setDischargeDetails(dischargeDetails);
    dischargeInformation.setDischargeRates(dischargeRates);
    dischargeInformation.setBerthDetails(new LoadingBerthDetails(availableBerths, selectedBerths));
    dischargeInformation.setMachineryInUses(machineryInUse);

    dischargeInformation.setDischargeStages(dischargeStages);
    dischargeInformation.setDischargeSequences(dischargeSequences);
    dischargeInformation.setCowPlan(cowPlan);

    dischargeInformation.setCargoVesselTankDetails(vesselTankDetails);
    return dischargeInformation;
  }

  public void setDischargeCargoNominationId(CargoVesselTankDetails vesselTankDetails) {
    try {
      for (var cQnt : vesselTankDetails.getCargoQuantities()) {
        Optional<Long> id =
            vesselTankDetails.getDischargeQuantityCargoDetails().stream()
                .filter(v -> v.getCargoNominationId().equals(cQnt.getCargoNominationId()))
                .findFirst()
                .map(DischargeQuantityCargoDetails::getDischargeCargoNominationId);
        if (id.isPresent()) {
          cQnt.setDischargeCargoNominationId(id.get());
        }
      }
    } catch (Exception e) {
      log.error("Failed to set discharge cargo nomination id - {}", e.getMessage());
    }
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
        activeVoyage.getDischargePortRotations().stream()
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
            activeVoyage.getDischargePatternId(),
            GatewayConstants.OPERATION_TYPE_DEP,
            portRotation.get().getId(),
            portRotation.get().getPortId()));
    // setting discharge cargo nomination id
    this.setDischargeCargoNominationId(vesselTankDetails);
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
    com.cpdss.common.generated.discharge_plan.DischargeInformation dischargingInformationReply =
        planReply.getDischargingInformation();
    dischargeInformation.setDischargeInfoStatusId(
        dischargingInformationReply.getDischargingInfoStatusId());
    dischargeInformation.setDischargePlanArrStatusId(
        dischargingInformationReply.getDischargingPlanArrStatusId());
    dischargeInformation.setDischargePlanDepStatusId(
        dischargingInformationReply.getDischargingPlanDepStatusId());
    dischargeInformation.setIsDischargeInfoComplete(
        dischargingInformationReply.getIsDischargeInfoComplete());
    dischargeInformation.setIsDischargeInstructionsComplete(
        dischargingInformationReply.getIsDischargeInfoComplete());
    dischargeInformation.setIsDischargePlanGenerated(
        dischargingInformationReply.getIsDischargingPlanGenerated());
    dischargeInformation.setIsDischargeSequenceGenerated(
        dischargingInformationReply.getIsDischargingSequenceGenerated());
    dischargeInformation.setDischargeSlopTanksFirst(
        dischargingInformationReply.getDischargeSlopTanksFirst());
    dischargeInformation.setDischargeCommingledCargoSeparately(
        dischargingInformationReply.getDischargeCommingledCargoSeparately());
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

  public DischargingInformationResponse saveDischargingInformation(
      DischargingInformationRequest request, String correlationId) throws GenericServiceException {
    try {
      log.info("Calling saveDischargingInformation in discharging-plan microservice via GRPC");
      DischargingInfoSaveResponse response = infoBuilderService.saveDataAsync(request);
      if (request.getDischargeDetails() != null) {
        // Updating synoptic table (time)
        log.info(
            "Saving Loading info Times details at Synoptic Table - id {}",
            request.getSynopticTableId());
        this.loadableStudyService.saveLoadingInfoToSynopticalTable(
            request.getSynopticTableId(),
            request.getDischargeDetails().getTimeOfSunrise(),
            request.getDischargeDetails().getTimeOfSunset());
      }
      // Discharging Info Case 10 - if protested, is commingled
      if (request.getCargoToBeDischarged() != null
          && request.getCargoToBeDischarged().getDischargeQuantityCargoDetails() != null
          && !request.getCargoToBeDischarged().getDischargeQuantityCargoDetails().isEmpty()) {
        this.loadingPlanGrpcService.updateDischargeQuantityCargoDetails(
            request.getCargoToBeDischarged().getDischargeQuantityCargoDetails());
      }
      if (response == null) {
        throw new GenericServiceException(
            "Failed to save Discharging Information",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      DischargingInformationResponse dischargingInformationResponse =
          buildDischargingInformationResponse(response, correlationId);
      dischargingInformationResponse.setDischargingInformation(
          this.getDischargeInformation(
              dischargingInformationResponse.getVesseld(),
              dischargingInformationResponse.getVoyageId(),
              response.getPortRotationId()));
      return dischargingInformationResponse;
    } catch (Exception e) {
      log.error("Failed to save DischargingInformation {}", request.getDischargeInfoId());
      e.printStackTrace();
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      throw new GenericServiceException(
          "Failed to save Loading Information",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  DischargingInformationResponse buildDischargingInformationResponse(
      DischargingInfoSaveResponse response2, String correlationId) {
    DischargingInformationResponse response = new DischargingInformationResponse();
    CommonSuccessResponse successResponse =
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId);
    response.setResponseStatus(successResponse);
    response.setDischargingInfoId(response2.getDischargingInfoId());
    response.setPortRotationId(response2.getPortRotationId());
    response.setSynopticalTableId(response2.getSynopticalTableId());
    response.setVesseld(response2.getVesselId());
    response.setVoyageId(response2.getVoyageId());
    return response;
  }

  public LoadingInfoAlgoResponse generateDischargingPlan(Long vesselId, Long voyageId, Long infoId)
      throws GenericServiceException {
    log.info("Calling generateDischargingPlan of discharging-plan microservice via GRPC");
    LoadingInfoAlgoResponse algoResponse = new LoadingInfoAlgoResponse();
    try {
      DischargeInformationRequest.Builder builder = DischargeInformationRequest.newBuilder();
      builder.setDischargeInfoId(infoId);
      builder.setVesselId(vesselId);
      builder.setVoyageId(voyageId);
      DischargePlanAlgoRequest response =
          dischargePlanServiceBlockingStub.generateDischargePlan(builder.build());
      if (response.getResponseStatus().getStatus().equalsIgnoreCase(SUCCESS)) {
        CommonSuccessResponse successResponse = new CommonSuccessResponse("200", "");
        algoResponse.setProcessId(response.getProcessId());
        algoResponse.setResponseStatus(successResponse);
        return algoResponse;
      } else {
        log.error("Failed to generate Discharging Plan for Discharging Information {}", infoId);
        throw new GenericServiceException(
            "Failed to generate Discharging Plan",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error("Failed to generate Discharging Plan for Discharging Information {}", infoId);
      e.printStackTrace();
      throw new GenericServiceException(
          "Failed to generate Discharging Plan",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public LoadingPlanAlgoResponse saveDischargingPlan(
      Long vesselId,
      Long voyageId,
      Long infoId,
      DischargingPlanAlgoRequest dischargingPlanAlgoRequest,
      String requestJsonString)
      throws GenericServiceException {

    LoadingPlanAlgoResponse algoResponse = new LoadingPlanAlgoResponse();
    DischargingPlanSaveRequest.Builder builder = DischargingPlanSaveRequest.newBuilder();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      objectMapper.writeValue(
          new File(this.rootFolder + "/json/dischargingInformationResult_" + infoId + ".json"),
          dischargingPlanAlgoRequest);
    } catch (IOException e) {
      log.error("Exception encountered when saving Loading Information Response JSON");
    }
    try {
      log.info("Saving Discharging Information Response JSON");
      StatusReply reply =
          this.saveJson(
              infoId, GatewayConstants.DISCHARGING_INFORMATION_RESPONSE_JSON_ID, requestJsonString);
      if (!GatewayConstants.SUCCESS.equals(reply.getStatus())) {
        log.error("Error occured  in gateway while writing JSON to database.");
      }
    } catch (Exception e) {
      log.error("Exception encountered when processing Discharging Information Response JSON");
    }
    dischargingSequenceService.buildDischargingPlanSaveRequest(
        dischargingPlanAlgoRequest, vesselId, infoId, builder);
    DischargingPlanSaveResponse response =
        dischargePlanServiceBlockingStub.saveDischargingPlan(builder.build());
    if (!response.getResponseStatus().getStatus().equals(SUCCESS)) {
      log.error("Exception occured when saving discharging plan");
      throw new GenericServiceException(
          "Unable to save loading plan for discharging information " + infoId,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    algoResponse.setProcessId(dischargingPlanAlgoRequest.getProcessId());
    algoResponse.setResponseStatus(new CommonSuccessResponse(SUCCESS, ""));
    return algoResponse;
  }
  /**
   * @param referenceId
   * @return StatusReply
   */
  public StatusReply saveJson(Long referenceId, Long jsonTypeId, String json) {
    JsonRequest jsonRequest =
        JsonRequest.newBuilder()
            .setReferenceId(referenceId)
            .setJsonTypeId(jsonTypeId)
            .setJson(json)
            .build();
    return this.loadingPlanGrpcService.saveJson(jsonRequest);
  }

  public DischargingInfoAlgoStatus dischargeInfoStatusCheck(
      Long vesselId, Long voyageId, Long infoId, String processId, Integer conditionType)
      throws GenericServiceException {
    DischargeInfoStatusRequest.Builder builder = DischargeInfoStatusRequest.newBuilder();
    builder.setDischargeInfoId(infoId);
    builder.setProcessId(processId);
    builder.setConditionType(conditionType);

    DischargingInfoAlgoStatus algoStatus = new DischargingInfoAlgoStatus();
    var rpcRepay = dischargePlanServiceBlockingStub.dischargeInfoStatusCheck(builder.build());
    if (!SUCCESS.equals(rpcRepay.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Could not find discharge info status in discharge information",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    algoStatus.setDischargingInfoStatusId(rpcRepay.getDischargeInfoStatusId());
    algoStatus.setDischargingInfoStatusLastModifiedTime(
        rpcRepay.getDischargeInfoStatusLastModifiedTime());
    algoStatus.setResponseStatus(new CommonSuccessResponse("200", ""));
    return algoStatus;
  }

  /**
   * Updates Discharging Information status
   *
   * @param request
   * @param first
   * @return LoadingInfoAlgoResponse
   * @throws GenericServiceException
   * @throws NumberFormatException
   */
  public LoadingInfoAlgoResponse saveDischargingInfoStatus(
      AlgoStatusRequest request, String correlationId)
      throws NumberFormatException, GenericServiceException {
    log.info("Inside update discharging info status api");
    LoadingInfoAlgoResponse loadingInfoAlgoResponse = new LoadingInfoAlgoResponse();
    com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest.Builder requestBuilder =
        com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest.newBuilder();
    requestBuilder.setLoadableStudystatusId(request.getDischargingInfoStatusId());
    requestBuilder.setProcesssId(request.getProcessId());
    AlgoStatusReply reply =
        dischargePlanServiceBlockingStub.saveDischargingPlanAlgoStatus(requestBuilder.build());
    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to save DischargingInfoStatus",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
    loadingInfoAlgoResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return loadingInfoAlgoResponse;
  }

  /**
   * Discharging sequence
   *
   * @param vesselId
   * @param voyageId
   * @param infoId
   * @return
   * @throws GenericServiceException
   */
  public LoadingSequenceResponse getDischargingSequence(Long vesselId, Long voyageId, Long infoId)
      throws GenericServiceException {
    LoadingSequenceRequest.Builder request = LoadingSequenceRequest.newBuilder();
    request.setLoadingInfoId(infoId);
    DischargeSequenceReply reply =
        this.dischargePlanServiceBlockingStub.getDischargingSequences(request.build());
    if (!reply.getResponseStatus().getStatus().equals(SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get dischargeSequence",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
    LoadingSequenceResponse response = new LoadingSequenceResponse();
    dischargingSequenceService.buildDischargingSequence(vesselId, reply, response);
    return response;
  }

  /**
   * Fetches Discharge Information ALGO Errors
   *
   * @param vesselId
   * @param voyageId
   * @param infoId
   * @param conditionType
   * @return
   * @throws GenericServiceException
   */
  public AlgoErrorResponse getDischargingInfoAlgoErrors(
      Long vesselId, Long voyageId, Long infoId, Integer conditionType)
      throws GenericServiceException {
    log.info("Fetching ALGO errors of Discharging Information {} from Discharge-Plan MS", infoId);
    AlgoErrorRequest.Builder requestBuilder = AlgoErrorRequest.newBuilder();
    requestBuilder.setLoadingInformationId(infoId);
    Optional.ofNullable(conditionType).ifPresent(requestBuilder::setConditionType);
    AlgoErrorResponse algoResponse = new AlgoErrorResponse();
    AlgoErrorReply reply =
        this.dischargePlanServiceBlockingStub.getDischargingInfoAlgoErrors(requestBuilder.build());
    if (!reply.getResponseStatus().getStatus().equals(SUCCESS)) {
      throw new GenericServiceException(
          "Failed to fetch Discharging Information ALGO status",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    this.buildAlgoErrors(reply, algoResponse);
    return algoResponse;
  }

  /**
   * Builds ALGO error response
   *
   * @param reply
   * @param algoResponse
   */
  private void buildAlgoErrors(AlgoErrorReply reply, AlgoErrorResponse algoResponse) {
    List<AlgoError> algoErrors = new ArrayList<AlgoError>();
    reply
        .getAlgoErrorsList()
        .forEach(
            error -> {
              AlgoError algoError = new AlgoError();
              algoError.setErrorHeading(error.getErrorHeading());
              algoError.setErrorDetails(error.getErrorMessagesList());
              algoErrors.add(algoError);
            });
    algoResponse.setAlgoErrors(algoErrors);
    algoResponse.setResponseStatus(new CommonSuccessResponse("200", ""));
  }

  /**
   * Get Discharging rules fot an informationId
   *
   * @param vesselId
   * @param voyageId
   * @param dischargeInfoId
   */
  public RuleResponse getDischargingPlanRules(Long vesselId, Long voyageId, Long dischargeInfoId)
      throws GenericServiceException {
    DischargeRuleRequest.Builder builder = DischargeRuleRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.setSectionId(DISCHARGING_RULE_MASTER_ID);
    builder.setDischargeInfoId(dischargeInfoId);
    RuleResponse ruleResponse =
        this.dischargeInformationGrpcService.saveOrGetDischargingPlanRules(builder);
    log.info("Discharging Info Rule Fetch for Vessel Id {}, info Id {}", vesselId, dischargeInfoId);
    return ruleResponse;
  }

  /**
   * Save Discharging rules fot an informationId
   *
   * @param vesselId
   * @param voyageId
   * @param infoId
   * @param ruleRequest
   */
  public RuleResponse saveDischargingPlanRules(
      Long vesselId, Long voyageId, Long infoId, RuleRequest ruleRequest)
      throws GenericServiceException {
    DischargeRuleRequest.Builder builder = DischargeRuleRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.setSectionId(DISCHARGING_RULE_MASTER_ID);
    builder.setDischargeInfoId(infoId);
    RuleUtility.buildRuleListForSave(ruleRequest, null, null, null, builder, false, false, true);
    RuleResponse ruleResponse =
        this.dischargeInformationGrpcService.saveOrGetDischargingPlanRules(builder);
    return ruleResponse;
  }
}
