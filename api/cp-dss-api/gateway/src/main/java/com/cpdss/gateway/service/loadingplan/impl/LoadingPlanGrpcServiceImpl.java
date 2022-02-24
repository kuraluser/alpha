/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import static com.cpdss.gateway.common.GatewayConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.*;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest.Builder;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.DischargeQuantityCargoDetails;
import com.cpdss.gateway.domain.UllageBillReply;
import com.cpdss.gateway.domain.loadingplan.CargoVesselTankDetails;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.LoadableStudyService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.utility.RuleUtility;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** Calls to Grpc Service Parser here and give back to caller. */
@Slf4j
@Service
public class LoadingPlanGrpcServiceImpl implements LoadingPlanGrpcService {

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  @GrpcClient("cargoInfoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoServiceBlockingStub;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("loadingInformationService")
  private LoadingInformationServiceGrpc.LoadingInformationServiceBlockingStub
      loadingInfoServiceBlockingStub;

  @GrpcClient("loadingPlanService")
  private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanServiceBlockingStub;

  @Autowired private LoadableStudyService loadableStudyService;

  @Override
  public VoyageResponse getActiveVoyageDetails(Long vesselId) throws GenericServiceException {
    LoadableStudy.ActiveVoyage activeVoyage =
        loadableStudyServiceBlockingStub.getActiveVoyagesByVessel(
            this.buildVoyageRequest(vesselId));
    if (!activeVoyage.getResponseStatus().getStatus().equalsIgnoreCase(SUCCESS)) {
      log.error("Failed to collect Active Voyage Data, Vessel Id {}", vesselId);
      throw new GenericServiceException(
          "Failed to get Active Voyage",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    if (activeVoyage.getId() <= 0) {
      log.error("Failed to collect Active Voyage Data, Vessel Id {}", vesselId);
      throw new GenericServiceException(
          "No Active Voyage Exist",
          CommonErrorCodes.E_CPDSS_NO_ACTIVE_VOYAGE_FOUND,
          HttpStatusCode.BAD_REQUEST);
    }
    VoyageResponse voyageResponse = new VoyageResponse();
    BeanUtils.copyProperties(activeVoyage, voyageResponse);
    voyageResponse.setCaptainId(activeVoyage.getCaptainId());
    voyageResponse.setChiefOfficerId(activeVoyage.getChiefOfficerId());
    // Printing object to check if we loosing any data by BeanUtils.copyProperties
    log.info("After copy properties " + voyageResponse.toString());
    if (activeVoyage.getConfirmedLoadableStudy() != null
        && activeVoyage.getConfirmedLoadableStudy().getId() > 0) {
      com.cpdss.gateway.domain.LoadableStudy loadableStudy =
          new com.cpdss.gateway.domain.LoadableStudy();
      BeanUtils.copyProperties(activeVoyage.getConfirmedLoadableStudy(), loadableStudy);
      voyageResponse.setActiveLs(loadableStudy);

      List<PortRotation> rotationDomain = new ArrayList<>();
      if (activeVoyage.getPortRotationCount() > 0) {
        for (LoadableStudy.PortRotationDetail pr : activeVoyage.getPortRotationList()) {
          PortRotation prObj = new PortRotation();
          BeanUtils.copyProperties(pr, prObj);
          rotationDomain.add(prObj);
        }
      }
      voyageResponse.setPortRotations(rotationDomain);
    }
    if (activeVoyage.getConfirmedDischargeStudy() != null
        && activeVoyage.getConfirmedDischargeStudy().getId() > 0) {
      com.cpdss.gateway.domain.LoadableStudy loadableStudy =
          new com.cpdss.gateway.domain.LoadableStudy();
      BeanUtils.copyProperties(activeVoyage.getConfirmedDischargeStudy(), loadableStudy);
      voyageResponse.setActiveDs(loadableStudy);

      List<PortRotation> rotationDomain = new ArrayList<>();
      if (!activeVoyage.getDischargePortRotationList().isEmpty()) {
        for (LoadableStudy.PortRotationDetail pr : activeVoyage.getDischargePortRotationList()) {
          PortRotation prObj = new PortRotation();
          BeanUtils.copyProperties(pr, prObj);
          rotationDomain.add(prObj);
        }
      }
      voyageResponse.setDischargePortRotations(rotationDomain);
    }
    log.info(
        "Active Voyage found Id {}, LS Id {}, DS Id {}",
        voyageResponse.getId(),
        voyageResponse.getActiveLs(),
        voyageResponse.getActiveDs());
    return voyageResponse;
  }

  @Override
  public Object getPortRotationDetailsForActiveVoyage(Long vesselId) {
    return null;
  }

  @Override
  public LoadableStudy.LoadingSynopticResponse fetchSynopticRecordForPortRotation(
      Long portRId, String operationType) throws GenericServiceException {
    LoadableStudy.LoadingPlanCommonResponse response =
        this.loadableStudyServiceBlockingStub.getSynopticDataForLoadingPlan(
            LoadableStudy.LoadingPlanIdRequest.newBuilder()
                .setIdType("PORT_ROTATION")
                .setId(portRId)
                .build());

    if (!response.getResponseStatus().getStatus().equals(SUCCESS)) {
      log.error("Failed to get Synoptic data from LS ", response.getResponseStatus().getMessage());
      throw new GenericServiceException(
          "Failed to get Synoptic Data for Port",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    if (response.getSynopticDataList().isEmpty()) {
      log.info("No data found for Port Rotation {} in Synoptic table", portRId);
      return null;
    } else {
      return response.getSynopticDataList().stream()
          .filter(v -> v.getOperationType().equalsIgnoreCase(operationType))
          .findFirst()
          .get();
    }
  }

  @Override
  public PortInfo.PortDetail fetchPortDetailByPortId(Long portId) throws GenericServiceException {
    PortInfo.PortReply response =
        this.portInfoServiceBlockingStub.getPortInfoByPortIds(
            PortInfo.GetPortInfoByPortIdsRequest.newBuilder()
                .addAllId(Arrays.asList(portId))
                .build());

    if (!response.getResponseStatus().getStatus().equals(SUCCESS)) {
      log.error(
          "Failed to get Port Details from Port Info ", response.getResponseStatus().getMessage());
      throw new GenericServiceException(
          "Failed to get Port Details from Port Info",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    if (response.getPortsList().isEmpty()) {
      log.info("No data found for port, Id {}", portId);
      return null;
    } else {
      return response.getPortsList().stream().filter(v -> (v.getId() == portId)).findFirst().get();
    }
  }

  @Override
  public LoadingPlanModels.LoadingInformation fetchLoadingInformation(
      Long vesselId, Long voyageId, Long loadingInfoId, Long patternId, Long portRotationId)
      throws GenericServiceException {
    LoadingPlanModels.LoadingInformationRequest.Builder builder =
        LoadingPlanModels.LoadingInformationRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.setVoyageId(voyageId);
    builder.setLoadingPlanId(loadingInfoId);
    if (patternId != null) builder.setLoadingPatternId(patternId);
    if (portRotationId != null) builder.setPortRotationId(portRotationId);
    LoadingPlanModels.LoadingInformation replay =
        loadingInfoServiceBlockingStub.getLoadingInformation(builder.build());
    if (!replay.getResponseStatus().getStatus().equals(SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get Loading Information",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    return replay;
  }

  @Override
  public CargoVesselTankDetails fetchPortWiseCargoDetails(
      Long vesselId,
      Long voyageId,
      Long loadableStudyId,
      Long portId,
      Long portOrder,
      Long portRotationId,
      String operationType) {
    CargoVesselTankDetails cvt = new CargoVesselTankDetails();
    VoyageStatusRequest request = new VoyageStatusRequest();
    request.setPortOrder(portOrder);
    request.setOperationType(operationType);
    request.setPortRotationId(portRotationId);
    try {
      VoyageStatusResponse rpcResponse =
          this.loadableStudyService.getVoyageStatus(
              request, vesselId, voyageId, loadableStudyId, portId, null);
      cvt.setCargoTanks(rpcResponse.getCargoTanks());
      cvt.setCargoQuantities(rpcResponse.getCargoQuantities());
      cvt.setCargoConditions(rpcResponse.getCargoConditions());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    return cvt;
  }

  @Override
  public List<LoadableStudy.LoadableQuantityCargoDetails> fetchLoadablePlanCargoDetails(
      Long patternId,
      String operationType,
      Long portRotationId,
      Long portId,
      Boolean isFilterOn,
      Common.PLANNING_TYPE planning_type,
      Long dischargeInfoId) {
    LoadableStudy.LoadingPlanCommonResponse response =
        this.loadableStudyServiceBlockingStub.getSynopticDataForLoadingPlan(
            LoadableStudy.LoadingPlanIdRequest.newBuilder()
                .setPatternId(patternId)
                .setOperationType(operationType)
                .setPortRotationId(portRotationId)
                .setPortId(portId)
                .setCargoNominationFilter(isFilterOn)
                .setPlanningType(planning_type)
                .setDischargingInfoId(dischargeInfoId)
                .build());

    if (response.getResponseStatus().getStatus().equals(SUCCESS)) {
      return response.getLoadableQuantityCargoDetailsList();
    }
    return new ArrayList<>();
  }

  @Override
  public LoadableStudy.LoadingPlanCommonResponse fetchLoadablePlanCargoDetailsReplay(
      Long patternId,
      String operationType,
      Long portRotationId,
      Long portId,
      Boolean isFilterOn,
      Common.PLANNING_TYPE planning_type,
      Long loadingInfoId) {
    LoadableStudy.LoadingPlanCommonResponse response =
        this.loadableStudyServiceBlockingStub.getSynopticDataForLoadingPlan(
            LoadableStudy.LoadingPlanIdRequest.newBuilder()
                .setPatternId(patternId)
                .setOperationType(operationType)
                .setPortRotationId(portRotationId)
                .setPortId(portId)
                .setCargoNominationFilter(isFilterOn)
                .setPlanningType(planning_type)
                .setLoadingInfoId(loadingInfoId)
                .build());

    if (response.getResponseStatus().getStatus().equals(SUCCESS)) {
      return response;
    }
    return null;
  }

  public LoadableStudy.VoyageRequest buildVoyageRequest(Long vesselId) {
    LoadableStudy.VoyageRequest.Builder builder = LoadableStudy.VoyageRequest.newBuilder();
    builder.setVesselId(vesselId);
    return builder.build();
  }

  @Override
  public LoadingInfoSaveResponse saveLoadingInformation(LoadingInformation loadingInformation) {
    return this.loadingInfoServiceBlockingStub.saveLoadingInformation(loadingInformation);
  }

  @Override
  public Boolean updateUllageAtLoadingPlan(LoadingPlanModels.UpdateUllageLoadingRequest request)
      throws GenericServiceException {
    LoadingPlanModels.UpdateUllageLoadingReplay replay =
        this.loadingInfoServiceBlockingStub.updateUllage(request);
    if (!replay.getResponseStatus().getStatus().equals(SUCCESS)) {
      log.error(
          "Update Ullage, Failed to update at Loading Information: {}",
          replay.getResponseStatus().getMessage());
      throw new GenericServiceException(
          "Failed to update ullage in Loading Information DB",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info(
        "Update ullage at Loading Plan Service Success!, Port Rotation Id {}, Voyage Id {}, Tank Id {}",
        request.getPortRotationId(),
        request.getVoyageId(),
        request.getTankId());
    return true;
  }

  public AlgoStatusReply saveLoadingInfoStatus(AlgoStatusRequest request) {
    return this.loadingInfoServiceBlockingStub.saveAlgoLoadingPlanStatus(request);
  }

  @Override
  public LoadingInfoAlgoReply generateLoadingPlan(Long loadingInfoId) {
    LoadingInfoAlgoRequest.Builder builder = LoadingInfoAlgoRequest.newBuilder();
    builder.setLoadingInfoId(loadingInfoId);
    return this.loadingInfoServiceBlockingStub.generateLoadingPlan(builder.build());
  }

  /*  @Override
  public RuleResponse getDischargingPlanRules(Long vesselId, Long loadingInfoId)
      throws GenericServiceException {
    RuleResponse ruleResponse = new RuleResponse();
    LoadingPlanModels.LoadingPlanRuleRequest.Builder builder =
        LoadingPlanModels.LoadingPlanRuleRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.setSectionId(LOADING_RULE_MASTER_ID);
    builder.setLoadingInfoId(loadingInfoId);
    LoadingPlanModels.LoadingPlanRuleReply loadingRuleReply =
        this.loadingPlanServiceBlockingStub.getOrSaveRulesForLoadingPlan(builder.build());
    if (!loadingRuleReply.getResponseStatus().getStatus().equals(SUCCESS)) {
      throw new GenericServiceException(
          "failed to get Vessel Details ",
          loadingRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(loadingRuleReply.getResponseStatus().getCode())));
    }
    ruleResponse.setPlan(Utility.buildLoadingPlanRule(loadingRuleReply));
    ruleResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), null));
    return ruleResponse;
  }*/

  @Override
  public RuleResponse saveOrGetLoadingPlanRules(
      LoadingPlanModels.LoadingPlanRuleRequest.Builder builder) throws GenericServiceException {
    RuleResponse ruleResponse = new RuleResponse();
    LoadingPlanModels.LoadingPlanRuleReply loadingRuleReply =
        this.loadingPlanServiceBlockingStub.getOrSaveRulesForLoadingPlan(builder.build());
    if (!loadingRuleReply.getResponseStatus().getStatus().equals(SUCCESS)) {
      throw new GenericServiceException(
          "Failed to save loading plan rules",
          loadingRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(loadingRuleReply.getResponseStatus().getCode())));
    }
    ruleResponse.setPlan(RuleUtility.buildLoadingPlanRule(loadingRuleReply));
    ruleResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), null));
    return ruleResponse;
  }

  @Override
  public LoadingSequenceReply getLoadingSequence(Builder builder) throws GenericServiceException {
    LoadingSequenceReply reply =
        this.loadingPlanServiceBlockingStub.getLoadingSequences(builder.build());
    if (!reply.getResponseStatus().getStatus().equals(SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get loading sequences for loading information " + builder.getLoadingInfoId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    return reply;
  }

  @Override
  public LoadingPlanSaveResponse saveLoadingPlan(LoadingPlanSaveRequest request) {
    return this.loadingPlanServiceBlockingStub.saveLoadingPlan(request);
  }

  @Override
  public LoadingPlanModels.LoadingPlanReply getLoadingPlan(
      Long vesselId, Long voyageId, Long loadingInfoId, Long patternId, Long portRotationId)
      throws GenericServiceException {

    LoadingPlanModels.LoadingInformationRequest.Builder builder =
        LoadingPlanModels.LoadingInformationRequest.newBuilder();
    builder.setLoadingPlanId(loadingInfoId);
    builder.setLoadingPatternId(patternId);
    builder.setPortRotationId(portRotationId);
    builder.setVoyageId(voyageId);
    builder.setVesselId(vesselId);

    LoadingPlanModels.LoadingPlanReply reply =
        this.loadingPlanServiceBlockingStub.getLoadingPlan(builder.build());

    if (!reply.getResponseStatus().getStatus().equals(SUCCESS)) {
      log.error("Failed to fetch Loading plan, Message {}", reply.getResponseStatus().getMessage());
      throw new GenericServiceException(
          "Failed to get loading plan from loading info",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    return reply;
  }

  @Override
  public LoadingPlanModels.UpdateUllageDetailsResponse getUpdateUllageDetails(
      LoadingPlanModels.UpdateUllageDetailsRequest.Builder requestBuilder)
      throws GenericServiceException {
    log.info("Calling getLoadingInstructions in loading-plan microservice via GRPC");

    LoadingPlanModels.UpdateUllageDetailsResponse grpcResponse =
        loadingPlanServiceBlockingStub.getUpdateUllageDetails(requestBuilder.build());

    //      if (grpcResponse.getResponseStatus().getStatus().equalsIgnoreCase(SUCCESS)) {
    //        log.info("GRPC call successfull");
    //        return this.buildResponseModel(grpcResponse);
    //      } else {
    //        log.error(
    //                "Failed to retrieve update ullage details of  vesselID: {} on port: {}",
    //                vesselId,
    //                portId);
    //        throw new GenericServiceException(
    //                "Failed to retrieve update ullage details",
    //                CommonErrorCodes.E_HTTP_BAD_REQUEST,
    //                HttpStatusCode.BAD_REQUEST);
    //      }
    System.out.println(grpcResponse.getMessage());
    return grpcResponse;
  }

  @Override
  public StatusReply saveJson(JsonRequest jsonRequest) {
    return this.loadableStudyServiceBlockingStub.saveJson(jsonRequest);
  }

  @Override
  public LoadingInfoStatusReply getLoadingInfoAlgoStatus(LoadingInfoStatusRequest request) {
    return this.loadingInfoServiceBlockingStub.getLoadingInfoAlgoStatus(request);
  }

  @Override
  public AlgoErrorReply getLoadingInfoAlgoErrors(AlgoErrorRequest request) {
    return this.loadingInfoServiceBlockingStub.getLoadingInfoAlgoErrors(request);
  }

  @Override
  public UllageBillReply getLoadableStudyShoreTwo(
      String first, LoadingPlanModels.UllageBillRequest.Builder inputData)
      throws GenericServiceException {
    LoadingPlanModels.UllageBillReply reply =
        loadingPlanServiceBlockingStub.getLoadableStudyShoreTwo(inputData.build());
    UllageBillReply replyData = new UllageBillReply();
    replyData.setProcessId(reply.getProcessId());
    replyData.setResponseStatus(
        new CommonSuccessResponse(
            reply.getResponseStatus().getStatus(), reply.getResponseStatus().getCode()));
    return replyData;
  }

  @Override
  public List<LoadablePlanBallastDetails> fetchLoadablePlanBallastDetails(
      Long patternId, Long portRotationId) {

    LoadableStudy.LoadingPlanCommonResponse response =
        this.loadableStudyServiceBlockingStub.getSynopticDataForLoadingPlan(
            LoadableStudy.LoadingPlanIdRequest.newBuilder()
                .setPatternId(patternId)
                .setId(portRotationId)
                .build());

    if (response.getResponseStatus().getStatus().equals(SUCCESS)) {
      return response.getLoadablePlanBallastDetailsList();
    }
    return new ArrayList<>();
  }

  @Override
  public ResponseStatus updateDischargeQuantityCargoDetails(
      List<DischargeQuantityCargoDetails> dischargeQuantityCargoDetails) {
    DischargeQuantityCargoDetailsRequest.Builder builder =
        DischargeQuantityCargoDetailsRequest.newBuilder();
    dischargeQuantityCargoDetails.stream()
        .forEach(
            cargo -> {
              com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails.Builder
                  detailBuilder =
                      com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails
                          .newBuilder();
              detailBuilder.setId(cargo.getId());
              detailBuilder.setIfProtested(cargo.getProtested());
              detailBuilder.setIsCommingled(cargo.getIsCommingledDischarge());
              Optional.ofNullable(cargo.getSlopQuantity())
                  .ifPresent(detailBuilder::setSlopQuantity);
              builder.addCargoDetails(detailBuilder);
            });

    return loadableStudyServiceBlockingStub.updateDischargeQuantityCargoDetails(builder.build());
  }
}
