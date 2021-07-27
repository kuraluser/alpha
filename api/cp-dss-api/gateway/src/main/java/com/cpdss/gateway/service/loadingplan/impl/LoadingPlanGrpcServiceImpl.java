/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import static com.cpdss.gateway.common.GatewayConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfoServiceGrpc;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusReply;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoAlgoRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoSaveResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest.Builder;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.domain.VoyageStatusRequest;
import com.cpdss.gateway.domain.VoyageStatusResponse;
import com.cpdss.gateway.domain.loadingplan.CargoVesselTankDetails;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.LoadableStudyService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.utility.Utility;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    List<PortRotation> rotationDomain = new ArrayList<>();
    if (activeVoyage.getPortRotationCount() > 0) {
      for (LoadableStudy.PortRotationDetail pr : activeVoyage.getPortRotationList()) {
        PortRotation prObj = new PortRotation();
        BeanUtils.copyProperties(pr, prObj);
        rotationDomain.add(prObj);
      }
    }
    voyageResponse.setPortRotations(rotationDomain);
    if (activeVoyage.getConfirmedLoadableStudy() != null
        && activeVoyage.getConfirmedLoadableStudy().getId() > 0) {
      com.cpdss.gateway.domain.LoadableStudy loadableStudy =
          new com.cpdss.gateway.domain.LoadableStudy();
      BeanUtils.copyProperties(activeVoyage.getConfirmedLoadableStudy(), loadableStudy);
      voyageResponse.setActiveLs(loadableStudy);
    }

    return voyageResponse;
  }

  @Override
  public Object getPortRotationDetailsForActiveVoyage(Long vesselId) {
    return null;
  }

  @Override
  public LoadableStudy.LoadingSynopticResponse fetchSynopticRecordForPortRotationArrivalCondition(
      Long portRId) throws GenericServiceException {
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
          .filter(v -> v.getOperationType().equalsIgnoreCase("ARR"))
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
      Long patternId, String operationType, Long portRotationId, Long portId) {
    LoadableStudy.LoadingPlanCommonResponse response =
        this.loadableStudyServiceBlockingStub.getSynopticDataForLoadingPlan(
            LoadableStudy.LoadingPlanIdRequest.newBuilder()
                .setPatternId(patternId)
                .setOperationType(operationType)
                .setPortRotationId(portRotationId)
                .setPortId(portId)
                .build());

    if (response.getResponseStatus().getStatus().equals(SUCCESS)) {
      return response.getLoadableQuantityCargoDetailsList();
    }
    return new ArrayList<>();
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
  public ResponseStatus generateLoadingPlan(Long loadingInfoId) {
    LoadingInfoAlgoRequest.Builder builder = LoadingInfoAlgoRequest.newBuilder();
    builder.setLoadingInfoId(loadingInfoId);
    return this.loadingInfoServiceBlockingStub.generateLoadingPlan(builder.build());
  }

  /*  @Override
  public RuleResponse getLoadingPlanRules(Long vesselId, Long loadingInfoId)
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
    ruleResponse.setPlan(Utility.buildLoadingPlanRule(loadingRuleReply));
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
}
