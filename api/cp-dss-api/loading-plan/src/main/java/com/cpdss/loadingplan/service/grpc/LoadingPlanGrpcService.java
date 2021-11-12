/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.grpc;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.BillOfLadding;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails;
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
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.entity.PortLoadingPlanCommingleDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageDetails;
import com.cpdss.loadingplan.repository.BillOfLaddingRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanCommingleDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.service.LoadingCargoHistoryService;
import com.cpdss.loadingplan.service.LoadingPlanService;
import com.cpdss.loadingplan.service.LoadingSequenceService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.impl.LoadingPlanRuleServiceImpl;
import com.cpdss.loadingplan.service.loadicator.LoadicatorService;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

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

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Override
  public void loadingPlanSynchronization(
      LoadingPlanSyncDetails request, StreamObserver<LoadingPlanSyncReply> responseObserver) {
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
      StreamObserver<LoadingInformationSynopticalReply> responseObserver) {
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
                          .mapToDouble(billOfLadding -> billOfLadding.getApi().doubleValue())
                          .average()
                          .orElse(0)));
              maxQuantity.setTemp(
                  String.valueOf(
                      quantity.stream()
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
      List<PortLoadingPlanStowageDetails> stowageDetails =
          portLoadingPlanStowageDetailsRepository.findByPortRotationXIdInAndIsActive(
              portRotationIds, true);
      Map<Long, List<PortLoadingPlanStowageDetails>> portWiseStowages =
          stowageDetails.stream()
              .collect(Collectors.groupingBy(PortLoadingPlanStowageDetails::getPortRotationXId));
      List<com.cpdss.loadingplan.entity.BillOfLadding> blList =
          billOfLaddingRepository.findByPortIdInAndIsActive(portIds, true);
      Map<Long, List<com.cpdss.loadingplan.entity.BillOfLadding>> portWiseBL =
          blList.stream()
              .collect(
                  Collectors.groupingBy(com.cpdss.loadingplan.entity.BillOfLadding::getPortId));
      Map<Long, List<PortLoadingPlanStowageDetails>> portWiseStowagesDepActuals =
          stowageDetails.stream()
              .filter(
                  stowage ->
                      stowage.getConditionType().equals(2) && stowage.getValueType().equals(1))
              .collect(Collectors.groupingBy(PortLoadingPlanStowageDetails::getPortRotationXId));
      if (portRotationIds.stream()
          .anyMatch(
              port ->
                  portWiseStowagesDepActuals.get(port) == null
                      || portWiseStowagesDepActuals.get(port).isEmpty())) {
        throw new GenericServiceException(
            "LS actuals or BL values are missing", "", HttpStatusCode.SERVICE_UNAVAILABLE);
      }
      if (!portWiseStowages.keySet().containsAll(portRotationIds)
          || !portWiseBL.keySet().containsAll(portIds)) {
        builder.setStatus(LoadingPlanConstants.FAILED);
        return;
      } else {
        portWiseCargosList.stream()
            .forEach(
                port -> {
                  try {
                    List<PortLoadingPlanStowageDetails> stowages =
                        portWiseStowages.get(port.getPortRotationId());
                    List<com.cpdss.loadingplan.entity.BillOfLadding> bLValues =
                        portWiseBL.get(port.getPortId());
                    List<Long> dbCargos =
                        stowages.stream()
                            .map(PortLoadingPlanStowageDetails::getCargoNominationXId)
                            .collect(Collectors.toList());
                    List<Long> dbBLCargos =
                        bLValues.stream()
                            .map(com.cpdss.loadingplan.entity.BillOfLadding::getCargoNominationId)
                            .collect(Collectors.toList());
                    // Checking if stowage details and bill of lading entries exists and quantity
                    // parameters are greater than zero
                    // Bug fix DSS 4458
                    if (!dbCargos.containsAll(port.getCargoIdsList())
                        || !dbBLCargos.containsAll(port.getCargoIdsList())
                        || (stowages.stream()
                            .anyMatch(
                                st ->
                                    st.getQuantity() == null
                                        || st.getQuantity().compareTo(BigDecimal.ZERO) <= 0
                                        || st.getQuantityM3() == null
                                        || st.getQuantityM3().compareTo(BigDecimal.ZERO) <= 0))
                        || (bLValues.stream()
                            .anyMatch(
                                bl ->
                                    bl.getQuantityMt() == null
                                        || bl.getQuantityMt().compareTo(BigDecimal.ZERO) <= 0
                                        || bl.getQuantityKl() == null
                                        || bl.getQuantityKl().compareTo(BigDecimal.ZERO) <= 0
                                        || bl.getQuantityBbls() == null
                                        || bl.getQuantityBbls().compareTo(BigDecimal.ZERO) <= 0
                                        || bl.getQuantityLT() == null
                                        || bl.getQuantityLT().compareTo(BigDecimal.ZERO) <= 0
                                        || bl.getApi() == null
                                        || bl.getApi().compareTo(BigDecimal.ZERO) <= 0
                                        || bl.getTemperature() == null
                                        || bl.getTemperature().compareTo(BigDecimal.ZERO) <= 0))) {
                      builder.setStatus(LoadingPlanConstants.FAILED);
                      throw new GenericServiceException(
                          "LS actuals or BL values are missing",
                          "",
                          HttpStatusCode.SERVICE_UNAVAILABLE);
                    }
                    // Add check for Zero and null values
                  } catch (Exception e) {
                    builder.setStatus(LoadingPlanConstants.FAILED);
                  }
                });
      }

      builder.setStatus(LoadingPlanConstants.SUCCESS);
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
    portLoadingPlanCommingleEntityList.forEach(
        item -> {
          LoadablePlanCommingleCargoDetails.Builder builder =
              LoadablePlanCommingleCargoDetails.newBuilder();
          builder.setId(item.getId());
          builder.setGrade(item.getGrade());
          builder.setTankName(item.getTankName());
          builder.setQuantity(item.getQuantity());
          builder.setApi(item.getApi());
          builder.setTemp(item.getTemperature());
          builder.setCargo1Abbreviation(item.getCargo1Abbreviation());
          builder.setCargo2Abbreviation(item.getCargo2Abbreviation());
          builder.setCargo1Percentage(item.getCargo1Percentage());
          builder.setCargo2Percentage(item.getCargo2Percentage());
          builder.setCargo1Bblsdbs(item.getCargo1BblsDbs());
          builder.setCargo2Bblsdbs(item.getCargo2BblsDbs());
          builder.setCargo1Bbls60F(item.getCargo1Bbls60f());
          builder.setCargo2Bbls60F(item.getCargo2Bbls60f());
          builder.setCargo1LT(item.getCargo1Lt());
          builder.setCargo2LT(item.getCargo2Lt());
          builder.setCargo1MT(item.getCargo1Mt());
          builder.setCargo2MT(item.getCargo2Mt());
          builder.setCargo1KL(item.getCargo1Kl());
          builder.setCargo1KL(item.getCargo2Kl());
          builder.setOrderedMT(item.getOrderQuantity());
          builder.setPriority(item.getPriority());
          builder.setLoadingOrder(item.getLoadingOrder());
          builder.setTankId(item.getTankId());
          builder.setFillingRatio(item.getFillingRatio());
          Optional.ofNullable(item.getCorrectedUllage())
              .ifPresent(i -> builder.setCorrectedUllage(i.toString()));
          Optional.ofNullable(item.getRdgUllage())
              .ifPresent(i -> builder.setRdgUllage(i.toString()));
          builder.setCorrectionFactor(item.getCorrectionFactor());
          builder.setSlopQuantity(item.getSlopQuantity());
          builder.setTimeRequiredForLoading(item.getTimeRequiredForLoading());
          builder.setTimeRequiredForLoading(item.getTimeRequiredForLoading());
          builder.setTankShortName(item.getShortName());

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
}
