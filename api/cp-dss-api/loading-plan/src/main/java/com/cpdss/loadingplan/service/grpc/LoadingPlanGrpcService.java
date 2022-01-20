/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.grpc;

import com.cpdss.common.communication.CommunicationConstants;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.BillOfLadding;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.PortWiseCargo;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc.LoadingPlanServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.entity.PortLoadingPlanCommingleDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageDetails;
import com.cpdss.loadingplan.entity.PyUser;
import com.cpdss.loadingplan.repository.BillOfLaddingRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanCommingleDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.repository.PyUserRepository;
import com.cpdss.loadingplan.service.*;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.impl.LoadingPlanRuleServiceImpl;
import com.cpdss.loadingplan.service.loadicator.LoadicatorService;
import com.google.common.base.Strings;
import com.google.gson.*;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;

/** @author pranav.k */
@Slf4j
@GrpcService
public class LoadingPlanGrpcService extends LoadingPlanServiceImplBase {

  @Autowired LoadingPlanService loadingPlanService;
  @Autowired LoadingPlanAlgoService loadingPlanAlgoService;
  @Autowired LoadingSequenceService loadingSequenceService;
  @Autowired LoadicatorService loadicatorService;

  @Autowired LoadingPlanRuleServiceImpl loadingPlanRuleService;

  @Autowired PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;

  @Autowired BillOfLaddingRepository billOfLaddingRepository;

  @Autowired PortLoadingPlanCommingleDetailsRepository portLoadingPlanCommingleDetailsRepository;

  @Autowired LoadingCargoHistoryService loadingCargoHistoryService;
  @Autowired LoadingInformationBuilderService informationBuilderService;
  @Autowired LoadingPlanStagingService loadingPlanStagingService;
  @Autowired PyUserRepository pyUserRepository;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";
  public static final Integer CONDITION_TYPE_DEP = 2;
  public static final Integer VALUE_TYPE_ACTUALS = 1;
  public static final Long ULLAGE_UPDATE_VALIDATED_TRUE = 13L;

  @Override
  public void loadingPlanSynchronization(
      LoadingPlanModels.LoadingPlanSyncRequest request,
      StreamObserver<LoadingPlanSyncReply> responseObserver) {
    log.info("Inside loadablePlanSynchronization");
    LoadingPlanSyncReply.Builder builder = LoadingPlanSyncReply.newBuilder();
    try {
      loadingPlanService.loadingPlanSynchronization(request, builder);
      builder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());

    } catch (Exception e) {
      log.error("Exception when loadingPlanSynchonization is called", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(LoadingPlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getOrSaveRulesForLoadingPlan(
      LoadingPlanModels.LoadingPlanRuleRequest request,
      StreamObserver<LoadingPlanModels.LoadingPlanRuleReply> responseObserver) {
    LoadingPlanModels.LoadingPlanRuleReply.Builder builder =
        LoadingPlanModels.LoadingPlanRuleReply.newBuilder();
    try {
      loadingPlanRuleService.getOrSaveRulesForLoadingPlan(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(LoadingPlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void saveLoadingPlan(
      LoadingPlanSaveRequest request, StreamObserver<LoadingPlanSaveResponse> responseObserver) {
    log.info("Inside saveLoadingPlan");
    LoadingPlanSaveResponse.Builder builder = LoadingPlanSaveResponse.newBuilder();
    try {
      loadingPlanAlgoService.saveLoadingSequenceAndPlan(builder, request);
      builder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when saveLoadingPlan microservice is called", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(LoadingPlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getLoadingSequences(
      LoadingSequenceRequest request, StreamObserver<LoadingSequenceReply> responseObserver) {
    log.info("Inside getLoadingSequences");
    LoadingSequenceReply.Builder builder = LoadingSequenceReply.newBuilder();
    try {
      loadingSequenceService.getLoadingSequences(request, builder);
      builder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when getLoadingSequence is called", e);
      e.printStackTrace();
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(LoadingPlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getLoadingPlan(
      LoadingPlanModels.LoadingInformationRequest request,
      StreamObserver<LoadingPlanModels.LoadingPlanReply> responseObserver) {
    LoadingPlanModels.LoadingPlanReply.Builder builder =
        LoadingPlanModels.LoadingPlanReply.newBuilder();
    try {
      loadingPlanService.getLoadingPlan(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(LoadingPlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getUpdateUllageDetails(
      LoadingPlanModels.UpdateUllageDetailsRequest request,
      io.grpc.stub.StreamObserver<LoadingPlanModels.UpdateUllageDetailsResponse> responseObserver) {
    log.info("Inside get Update Ullage details");
    LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder =
        LoadingPlanModels.UpdateUllageDetailsResponse.newBuilder();
    try {
      loadingPlanService.getBillOfLaddingDetails(request, builder);
      loadingPlanService.getPortWiseStowageDetails(request, builder);
      loadingPlanService.getPortWiseBallastDetails(request, builder);
      loadingPlanService.getPortWiseRobDetails(request, builder);
      loadingPlanService.getPortWiseStowageTempDetails(request, builder);
      loadingPlanService.getPortWiseBallastTempDetails(request, builder);
      loadingPlanService.getPortWiseCommingleDetails(request, builder);
      loadingPlanService.getPortWiseCommingleTempDetails(request, builder);
      // builder.setResponseStatus(
      // ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when saveLoadingPlan microservice is called", e);
      // builder.setResponseStatus(
      // ResponseStatus.newBuilder()
      // .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
      // .setMessage(e.getMessage())
      // .setStatus(LoadingPlanConstants.FAILED)
      // .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getBillOfLaddingDetails(
      BillOfLaddingRequest request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadingInformationSynopticalReply>
          responseObserver) {
    log.info(
        "Inside getBillOfLaddingDetails - getting bill of ladding details agianst cargonomination Id {}",
        request.getCargoNominationId());
    LoadingInformationSynopticalReply.Builder reply =
        LoadingInformationSynopticalReply.newBuilder();
    try {
      List<com.cpdss.loadingplan.entity.BillOfLadding> billOfLaddingList =
          billOfLaddingRepository.findByCargoNominationIdAndIsActive(
              request.getCargoNominationId(), true);
      if (!CollectionUtils.isEmpty(billOfLaddingList)) {
        billOfLaddingList.forEach(
            item -> {
              BillOfLadding.Builder billOfLadding = BillOfLadding.newBuilder();
              Optional.ofNullable(item.getId()).ifPresent(billOfLadding::setId);
              Optional.ofNullable(item.getPortId()).ifPresent(billOfLadding::setPortId);
              Optional.ofNullable(item.getCargoNominationId())
                  .ifPresent(billOfLadding::setCargoNominationId);
              Optional.ofNullable(item.getQuantityBbls())
                  .ifPresent(value -> billOfLadding.setQuantityBbls(String.valueOf(value)));
              Optional.ofNullable(item.getQuantityMt())
                  .ifPresent(value -> billOfLadding.setQuantityMt(String.valueOf(value)));
              Optional.ofNullable(item.getQuantityKl())
                  .ifPresent(value -> billOfLadding.setQuantityKl(String.valueOf(value)));
              Optional.ofNullable(item.getApi())
                  .ifPresent(value -> billOfLadding.setApi(String.valueOf(value)));
              Optional.ofNullable(item.getTemperature())
                  .ifPresent(value -> billOfLadding.setTemperature(String.valueOf(value)));
              reply.addBillOfLadding(billOfLadding);
            });
      }
      reply.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when getting bill of ladding details agianst cargonomination Id", e);
      reply.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(LoadingPlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(reply.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getCargoNominationMaxQuantity(
      MaxQuantityRequest request, StreamObserver<MaxQuantityResponse> responseObserver) {

    log.info(
        "Inside getCargoNominationMaxQuantity - getting max quantity agianst each cargonomination Id {}",
        request.getCargoNominationIdList());
    MaxQuantityResponse.Builder reply = MaxQuantityResponse.newBuilder();
    try {
      List<com.cpdss.loadingplan.entity.BillOfLadding> billOfLaddingList =
          billOfLaddingRepository.findByCargoNominationIdInAndIsActive(
              request.getCargoNominationIdList(), true);
      if (!CollectionUtils.isEmpty(billOfLaddingList)) {
        Map<Long, List<com.cpdss.loadingplan.entity.BillOfLadding>> cargoWiseQuantity =
            billOfLaddingList.stream()
                .collect(
                    Collectors.groupingBy(
                        com.cpdss.loadingplan.entity.BillOfLadding::getCargoNominationId));
        cargoWiseQuantity.forEach(
            (key, quantity) -> {
              MaxQuantityDetails.Builder maxQuantity = MaxQuantityDetails.newBuilder();
              maxQuantity.setCargoNominationId(key);
              maxQuantity.setMaxQuantity(
                  String.valueOf(
                      quantity.stream()
                          .mapToDouble(billOfLadding -> billOfLadding.getQuantityMt().doubleValue())
                          .sum()));
              maxQuantity.setApi(
                  String.valueOf(
                      quantity.stream()
                          .filter(bl -> bl.getApi() != null && bl.getApi().signum() > 0)
                          .mapToDouble(billOfLadding -> billOfLadding.getApi().doubleValue())
                          .average()
                          .orElse(0)));
              maxQuantity.setTemp(
                  String.valueOf(
                      quantity.stream()
                          .filter(
                              bl -> bl.getTemperature() != null && bl.getTemperature().signum() > 0)
                          .mapToDouble(
                              billOfLadding -> billOfLadding.getTemperature().doubleValue())
                          .average()
                          .orElse(0)));
              reply.addCargoMaxQuantity(maxQuantity);
            });
      }
      reply.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when getting bill of ladding details agianst cargonomination Id", e);
      reply.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(LoadingPlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(reply.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getCargoQuantityLoadingRatio(
      MaxQuantityRequest request, StreamObserver<MaxQuantityResponse> responseObserver) {
    log.info(
        "Inside getCargoLoadingRatio - calculation ratio agianst each cargonomination Id {}",
        request.getCargoNominationIdList());
    MaxQuantityResponse.Builder reply = MaxQuantityResponse.newBuilder();
    try {
      List<com.cpdss.loadingplan.entity.BillOfLadding> billOfLaddingList =
          billOfLaddingRepository.findByCargoNominationIdInAndIsActive(
              request.getCargoNominationIdList(), true);
      if (!CollectionUtils.isEmpty(billOfLaddingList)) {
        Map<Long, List<com.cpdss.loadingplan.entity.BillOfLadding>> cargoWiseQuantity =
            billOfLaddingList.stream()
                .collect(
                    Collectors.groupingBy(
                        com.cpdss.loadingplan.entity.BillOfLadding::getCargoNominationId));
        cargoWiseQuantity.forEach(
            (key, quantity) -> {
              MaxQuantityDetails.Builder maxQuantity = MaxQuantityDetails.newBuilder();
              maxQuantity.setCargoNominationId(key);
              Double blFig =
                  quantity.stream()
                      .mapToDouble(billOfLadding -> billOfLadding.getQuantityMt().doubleValue())
                      .sum();
              maxQuantity.setMaxQuantity(String.valueOf(blFig));
              // Finding quantity ratio Actual/BL
              List<PortLoadingPlanStowageDetails> actualQuantityList =
                  portLoadingPlanStowageDetailsRepository
                      .findByCargoNominationXIdAndPortRotationXIdAndValueTypeAndConditionTypeAndIsActiveTrue(
                          key,
                          request.getLastLoadingPortId(),
                          VALUE_TYPE_ACTUALS,
                          CONDITION_TYPE_DEP);
              Double cargoActual =
                  actualQuantityList.stream()
                      .mapToDouble(item -> item.getQuantity().doubleValue())
                      .sum();
              if (blFig > 0) {
                maxQuantity.setRatio(String.valueOf(cargoActual / blFig));
              }
              reply.addCargoMaxQuantity(maxQuantity);
            });
      }
      reply.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when getting ratio agianst cargonomination Id", e);
      reply.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(LoadingPlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(reply.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getLoadicatorData(
      LoadingInfoLoadicatorDataRequest request,
      StreamObserver<LoadingInfoLoadicatorDataReply> responseObserver) {
    LoadingInfoLoadicatorDataReply.Builder reply = LoadingInfoLoadicatorDataReply.newBuilder();
    try {
      loadicatorService.getLoadicatorData(request, reply);
      reply.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when getting data from Loadicator", e);
      reply.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(LoadingPlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(reply.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getLoadableStudyShoreTwo(
      LoadingPlanModels.UllageBillRequest request,
      StreamObserver<LoadingPlanModels.UllageBillReply> responseObserver) {
    LoadingPlanModels.UllageBillReply.Builder builder =
        LoadingPlanModels.UllageBillReply.newBuilder();
    try {
      loadingPlanService.getLoadableStudyShoreTwo(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(LoadingPlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Transactional
  @SuppressWarnings("unlikely-arg-type")
  @Override
  public void validateStowageAndBillOfLadding(
      StowageAndBillOfLaddingValidationRequest request,
      StreamObserver<ResponseStatus> responseObserver) {

    ResponseStatus.Builder builder = ResponseStatus.newBuilder();
    try {
      List<PortWiseCargo> portWiseCargosList = request.getPortWiseCargosList();
      List<Long> portRotationIds =
          portWiseCargosList.stream()
              .map(PortWiseCargo::getPortRotationId)
              .collect(Collectors.toList());
      List<Long> portIds =
          portWiseCargosList.stream().map(PortWiseCargo::getPortId).collect(Collectors.toList());
      List<Long> cargoIds =
          portWiseCargosList.stream()
              .flatMap(port -> port.getCargoIdsList().stream())
              .distinct()
              .collect(Collectors.toList());
      List<PortLoadingPlanStowageDetails> stowageDetails =
          portLoadingPlanStowageDetailsRepository
              .findByPortRotationXIdAndIsActiveAndConditionTypeAndValueType(
                  portRotationIds.get(portRotationIds.size() - 1),
                  true,
                  CONDITION_TYPE_DEP,
                  VALUE_TYPE_ACTUALS);
      List<com.cpdss.loadingplan.entity.BillOfLadding> blList =
          billOfLaddingRepository.findByCargoNominationIdInAndIsActive(cargoIds, true);
      Map<Long, List<com.cpdss.loadingplan.entity.BillOfLadding>> portWiseBL =
          blList.stream()
              .collect(
                  Collectors.groupingBy(com.cpdss.loadingplan.entity.BillOfLadding::getPortId));
      // Bug fix 4564 : in case once validated ullage update is edited and not
      // validated true again
      // DS should not be created
      // Adding if condition to check deparcher condition ullage update status 13 or
      // not.13 : Ullage
      // Update Validation Successful"
      if (!blList.isEmpty()
          && blList.stream()
              .allMatch(
                  bl ->
                      bl.getLoadingInformation()
                          .getDepartureStatus()
                          .getId()
                          .equals(ULLAGE_UPDATE_VALIDATED_TRUE))) {
        if (stowageDetails == null
            || stowageDetails.isEmpty()
            || !portWiseBL.keySet().containsAll(portIds)) {
          builder.setStatus(LoadingPlanConstants.FAILED);
          return;
        }
        Map<Long, List<PortLoadingPlanStowageDetails>> cargoWiseStowage =
            stowageDetails.stream()
                .filter(v -> v.getCargoNominationXId() != 0)
                .collect(
                    Collectors.groupingBy(PortLoadingPlanStowageDetails::getCargoNominationXId));
        cargoWiseStowage.forEach(
            (key, values) -> {
              if (values.stream()
                  .noneMatch(
                      v ->
                          v.getQuantity() != null
                              || v.getQuantity().compareTo(BigDecimal.ZERO) > 0)) {
                builder.setStatus(LoadingPlanConstants.FAILED);
                return;
              }
            });

        portWiseCargosList.stream()
            .forEach(
                port -> {
                  try {
                    List<com.cpdss.loadingplan.entity.BillOfLadding> bLValues =
                        portWiseBL.get(port.getPortId());
                    List<Long> dbCargos =
                        stowageDetails.stream()
                            .map(PortLoadingPlanStowageDetails::getCargoNominationXId)
                            .distinct()
                            .collect(Collectors.toList());
                    List<Long> dbBLCargos =
                        bLValues.stream()
                            .map(com.cpdss.loadingplan.entity.BillOfLadding::getCargoNominationId)
                            .distinct()
                            .collect(Collectors.toList());
                    // Checking if stowage details and bill of lading entries exists and quantity
                    // parameters are greater than zero
                    // Bug fix DSS 4458
                    // Issue fix : for commingle cargos - cargo nomination id will not be present in
                    // port loadable stowage details
                    // so removing !dbCargos.containsAll(port.getCargoIdsList()) check since this
                    // will
                    // always fail.
                    if (!dbBLCargos.containsAll(port.getCargoIdsList())
                        || (bLValues.stream()
                            .anyMatch(
                                bl ->
                                    bl.getQuantityMt() == null
                                        || bl.getQuantityMt().compareTo(BigDecimal.ZERO) < 0
                                        || bl.getQuantityKl() == null
                                        || bl.getQuantityKl().compareTo(BigDecimal.ZERO) < 0
                                        || bl.getQuantityBbls() == null
                                        || bl.getQuantityBbls().compareTo(BigDecimal.ZERO) < 0
                                        || bl.getQuantityLT() == null
                                        || bl.getQuantityLT().compareTo(BigDecimal.ZERO) < 0
                                        || bl.getApi() == null
                                        || bl.getApi().compareTo(BigDecimal.ZERO) < 0
                                        || bl.getTemperature() == null
                                        || bl.getTemperature().compareTo(BigDecimal.ZERO) < 0))) {
                      builder.setStatus(LoadingPlanConstants.FAILED);
                      throw new GenericServiceException(
                          "LS actuals or BL values are missing",
                          "",
                          HttpStatusCode.SERVICE_UNAVAILABLE);
                    } else {
                      builder.setStatus(LoadingPlanConstants.SUCCESS);
                    }
                    // Add check for Zero and null values
                  } catch (Exception e) {
                    builder.setStatus(LoadingPlanConstants.FAILED);
                  }
                });
      } else {
        // One or more ports have its ullage update validation not successful
        builder.setStatus(LoadingPlanConstants.FAILED);
        throw new GenericServiceException(
            "Ullage updated validation Pending", "", HttpStatusCode.SERVICE_UNAVAILABLE);
      }

    } catch (Exception e) {
      e.printStackTrace();
      builder.setStatus(LoadingPlanConstants.FAILED);
      builder.setHttpStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR.value());
      builder.setMessage(e.getMessage());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getLoadingPlanCommingleDetails(
      LoadingInformationSynopticalRequest request,
      StreamObserver<LoadablePlanCommingleCargoDetailsReply> responseObserver) {
    LoadablePlanCommingleCargoDetailsReply.Builder reply =
        LoadablePlanCommingleCargoDetailsReply.newBuilder();
    List<PortLoadingPlanCommingleDetails> portLoadingPlanCommingleEntityList =
        portLoadingPlanCommingleDetailsRepository.findByLoadablePatternIdAndIsActiveTrue(
            request.getLoadablePatternId());
    portLoadingPlanCommingleEntityList.stream()
        .filter(
            item ->
                item.getLoadingInformation().getPortRotationXId().equals(request.getSynopticalId()))
        .forEach(
            item -> {
              LoadablePlanCommingleCargoDetails.Builder builder =
                  LoadablePlanCommingleCargoDetails.newBuilder();
              Optional.ofNullable(item.getId()).ifPresent(builder::setId);
              Optional.ofNullable(item.getGrade()).ifPresent(builder::setGrade);
              Optional.ofNullable(item.getColorCode()).ifPresent(builder::setColorCode);
              Optional.ofNullable(item.getTankName()).ifPresent(builder::setTankName);
              Optional.ofNullable(item.getQuantity()).ifPresent(builder::setQuantity);
              Optional.ofNullable(item.getApi()).ifPresent(builder::setApi);
              Optional.ofNullable(item.getTemperature()).ifPresent(builder::setTemp);
              Optional.ofNullable(item.getCargo1Abbreviation())
                  .ifPresent(builder::setCargo1Abbreviation);
              Optional.ofNullable(item.getCargo2Abbreviation())
                  .ifPresent(builder::setCargo2Abbreviation);
              Optional.ofNullable(item.getCargo1Percentage())
                  .ifPresent(builder::setCargo1Percentage);
              Optional.ofNullable(item.getCargo2Percentage())
                  .ifPresent(builder::setCargo2Percentage);
              Optional.ofNullable(item.getCargo1BblsDbs()).ifPresent(builder::setCargo1Bblsdbs);
              Optional.ofNullable(item.getCargo2BblsDbs()).ifPresent(builder::setCargo2Bblsdbs);
              Optional.ofNullable(item.getCargo1Bbls60f()).ifPresent(builder::setCargo1Bbls60F);
              Optional.ofNullable(item.getCargo2Bbls60f()).ifPresent(builder::setCargo2Bbls60F);
              Optional.ofNullable(item.getCargo1Lt()).ifPresent(builder::setCargo1LT);
              Optional.ofNullable(item.getCargo2Lt()).ifPresent(builder::setCargo2LT);
              Optional.ofNullable(item.getCargo1Mt()).ifPresent(builder::setCargo1MT);
              Optional.ofNullable(item.getCargo2Mt()).ifPresent(builder::setCargo2MT);
              Optional.ofNullable(item.getCargo1Kl()).ifPresent(builder::setCargo1KL);
              Optional.ofNullable(item.getCargo2Kl()).ifPresent(builder::setCargo2KL);
              Optional.ofNullable(item.getOrderQuantity()).ifPresent(builder::setOrderedMT);
              Optional.ofNullable(item.getPriority()).ifPresent(builder::setPriority);
              Optional.ofNullable(item.getLoadingOrder()).ifPresent(builder::setLoadingOrder);
              Optional.ofNullable(item.getTankId()).ifPresent(builder::setTankId);
              Optional.ofNullable(item.getFillingRatio()).ifPresent(builder::setFillingRatio);
              Optional.ofNullable(item.getCorrectedUllage())
                  .ifPresent(i -> builder.setCorrectedUllage(i.toString()));
              Optional.ofNullable(item.getRdgUllage())
                  .ifPresent(i -> builder.setRdgUllage(i.toString()));
              Optional.ofNullable(item.getCorrectionFactor())
                  .ifPresent(builder::setCorrectionFactor);
              Optional.ofNullable(item.getSlopQuantity()).ifPresent(builder::setSlopQuantity);
              Optional.ofNullable(item.getTimeRequiredForLoading())
                  .ifPresent(builder::setTimeRequiredForLoading);
              Optional.ofNullable(item.getShortName()).ifPresent(builder::setTankShortName);
              Optional.ofNullable(item.getCargo1XId()).ifPresent(builder::setCargo1Id);
              Optional.ofNullable(item.getCargo2XId()).ifPresent(builder::setCargo2Id);
              Optional.ofNullable(item.getQuantity1MT()).ifPresent(builder::setQuantity1MT);
              Optional.ofNullable(item.getQuantity2MT()).ifPresent(builder::setQuantity2MT);
              Optional.ofNullable(item.getQuantity1M3()).ifPresent(builder::setQuantity1M3);
              Optional.ofNullable(item.getQuantity2M3()).ifPresent(builder::setQuantity2M3);
              Optional.ofNullable(item.getCargoNomination1XId())
                  .ifPresent(builder::setCargo1NominationId);
              Optional.ofNullable(item.getCargoNomination2XId())
                  .ifPresent(builder::setCargo2NominationId);
              // Added loading info to builder object for showing commingle as separate grade change
              Optional.ofNullable(item.getLoadingInformation())
                  .ifPresent(
                      loadingInfo ->
                          builder.setLoadingInformation(
                              LoadingInformationDetail.newBuilder()
                                  .setPortId(loadingInfo.getPortXId())
                                  .setPortRotationId(loadingInfo.getPortRotationXId())));
              Optional.ofNullable(item.getConditionType()).ifPresent(builder::setArrivalDeparcher);
              Optional.ofNullable(item.getValueType()).ifPresent(builder::setActualPlanned);
              reply.addLoadablePlanCommingleCargoList(builder.build());
            });
    reply.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    responseObserver.onNext(reply.build());
    responseObserver.onCompleted();
  }

  @Override
  public void getLoadingPlanCargoHistory(
      Common.CargoHistoryOpsRequest request,
      StreamObserver<Common.CargoHistoryResponse> responseObserver) {
    Common.CargoHistoryResponse.Builder builder = Common.CargoHistoryResponse.newBuilder();
    try {
      log.info("Get cargo history for voyage id - {}", request.getVoyageId());
      loadingCargoHistoryService.buildCargoDetailsFromStowageData(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(LoadingPlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void checkDependentProcess(
      Common.DependentProcessCheckRequestComm request,
      StreamObserver<Common.CommunicationCheckResponse> responseObserver) {

    log.debug("checkDependentProcess Request: {}", request);

    Common.CommunicationCheckResponse.Builder responseBuilder =
        Common.CommunicationCheckResponse.newBuilder();
    try {
      final boolean isDependentProcessCompleted =
          loadingPlanStagingService.dependantProcessIsCompleted(
              request.getDependantProcessId(),
              CommunicationConstants.CommunicationModule.LOADABLE_STUDY.getModuleName());
      log.info(
          "checkDependentProcess Completed ::: Dependent Process Id: {}, Completed Status: {}",
          request.getDependantProcessId(),
          isDependentProcessCompleted);

      // Set response status
      responseBuilder
          .setIsCompleted(isDependentProcessCompleted)
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setStatus(SUCCESS)
                  .setMessage(SUCCESS)
                  .setCode(String.valueOf(HttpStatusCode.OK.value()))
                  .setHttpStatusCode(HttpStatusCode.OK.value())
                  .build())
          .build();
    } catch (Exception e) {
      log.error(
          "checkDependentProcess failed. Dependent Process Id: {}",
          request.getDependantProcessId(),
          e);
      // Set response status
      responseBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(Strings.nullToEmpty(e.getMessage()))
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setHttpStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR.value())
              .build());
    } finally {
      // Build response
      responseObserver.onNext(responseBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void checkCommunicated(
      Common.CommunicationStatusCheckRequest request,
      StreamObserver<Common.CommunicationCheckResponse> responseObserver) {

    log.debug("checkCommunicated Request: {}", request);

    Common.CommunicationCheckResponse.Builder responseBuilder =
        Common.CommunicationCheckResponse.newBuilder();
    try {
      final boolean isCommunicated =
          loadingPlanStagingService.isCommunicated(
              MessageTypes.LOADABLESTUDY.getMessageType(),
              request.getReferenceId(),
              CommunicationConstants.CommunicationModule.LOADABLE_STUDY.getModuleName());
      log.info(
          "checkCommunicated Completed ::: Reference Id: {}, isCommunicated: {}",
          request.getReferenceId(),
          isCommunicated);

      // Set response status
      responseBuilder
          .setIsCompleted(isCommunicated)
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setStatus(SUCCESS)
                  .setMessage(SUCCESS)
                  .setCode(String.valueOf(HttpStatusCode.OK.value()))
                  .setHttpStatusCode(HttpStatusCode.OK.value())
                  .build())
          .build();
    } catch (Exception e) {
      log.error("checkCommunicated failed. Reference Id: {}", request.getReferenceId(), e);
      // Set response status
      responseBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(Strings.nullToEmpty(e.getMessage()))
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setHttpStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR.value())
              .build());
    } finally {
      // Build response
      responseObserver.onNext(responseBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * get pyuser Data for dischargeplan communication
   *
   * @param request
   * @param responseObserver
   */
  public void getPyUserForCommunication(
      LoadingPlanModels.LoadingPlanCommunicationRequest request,
      StreamObserver<LoadingPlanModels.LoadingPlanCommunicationReply> responseObserver) {
    LoadingPlanModels.LoadingPlanCommunicationReply.Builder replyBuilder =
        LoadingPlanModels.LoadingPlanCommunicationReply.newBuilder();
    try {
      log.info("PyUser request:{}", request.getId());
      String pyUser = pyUserRepository.getPyUserWithId(request.getId());
      if (pyUser != null) {
        log.info("PyUser get:{}", pyUser.length());
        replyBuilder.setDataJson(pyUser);
        replyBuilder.setResponseStatus(
            Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      } else {
        replyBuilder.setResponseStatus(
            Common.ResponseStatus.newBuilder().setMessage("No PyUser Found").build());
      }
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    } catch (Exception e) {
      log.error("Error occurred when get PyUser", e);
    }
  }

  /**
   * save PyUser Data for dischargeplan communication
   *
   * @param request
   * @param responseObserver
   */
  public void savePyUserForCommunication(
      LoadingPlanModels.LoadingPlanCommunicationRequest request,
      StreamObserver<LoadingPlanModels.LoadingPlanCommunicationReply> responseObserver) {
    LoadingPlanModels.LoadingPlanCommunicationReply.Builder replyBuilder =
        LoadingPlanModels.LoadingPlanCommunicationReply.newBuilder();
    try {
      this.savePyUser(request.getDataJson());
      replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (ResourceAccessException e) {
      e.printStackTrace();
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(
                  com.cpdss.loadingplan.utility.LoadingPlanConstants.FAILED_WITH_RESOURCE_EXC)
              .build());
    } catch (Exception e) {
      e.printStackTrace();
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(com.cpdss.loadingplan.utility.LoadingPlanConstants.FAILED_WITH_EXC)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  private void savePyUser(String dataJson) {
    try {
      log.info("Inside savePyUser in LoadingPlan");
      HashMap<String, String> map = loadingPlanStagingService.getAttributeMapping(new PyUser());
      JsonArray jsonArray =
          loadingPlanStagingService.getAsEntityJson(
              map, JsonParser.parseString(dataJson).getAsJsonArray());
      log.info("PyUser json array:{}", jsonArray);
      JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
      PyUser pyUser = new PyUser();
      pyUser.setId(jsonObject.get("id").getAsString());
      pyUser.setLogFile(jsonObject.get("logFile").getAsString());
      pyUser.setMessage(jsonObject.get("message").getAsJsonObject().toString());
      pyUser.setStatus(jsonObject.get("status").getAsString());
      pyUser.setTimeStamp(jsonObject.get("timeStamp").getAsString());
      if (pyUser != null) {
        pyUser = pyUserRepository.save(pyUser);
        log.info("Saved PyUser:{}", pyUser);
      }
    } catch (Exception e) {
      log.error("Error occurred when saving PyUser data part of dischargeplan communication", e);
    }
  }
}
