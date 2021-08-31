/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.grpc;

import com.cpdss.common.generated.Common.BillOfLadding;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply;
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
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageDetails;
import com.cpdss.loadingplan.repository.BillOfLaddingRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.service.LoadingPlanService;
import com.cpdss.loadingplan.service.LoadingSequenceService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.impl.LoadingPlanRuleServiceImpl;
import com.cpdss.loadingplan.service.loadicator.LoadicatorService;
import io.grpc.stub.StreamObserver;
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
      loadingPlanAlgoService.saveLoadingSequenceAndPlan(request);
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
      //      builder.setResponseStatus(
      //              ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when saveLoadingPlan microservice is called", e);
      //      builder.setResponseStatus(
      //              ResponseStatus.newBuilder()
      //                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
      //                      .setMessage(e.getMessage())
      //                      .setStatus(LoadingPlanConstants.FAILED)
      //                      .build());
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
        Map<Long, Double> cargoWiseQuantity =
            billOfLaddingList.stream()
                .collect(
                    Collectors.groupingBy(
                        com.cpdss.loadingplan.entity.BillOfLadding::getCargoNominationId,
                        Collectors.summingDouble(
                            a -> Double.parseDouble(String.valueOf(a.getQuantityMt())))));
        cargoWiseQuantity.forEach(
            (key, quantity) -> {
              MaxQuantityDetails.Builder maxQuantity = MaxQuantityDetails.newBuilder();
              maxQuantity.setCargoNominationId(key);
              maxQuantity.setMaxQuantity(quantity.toString());
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
  public void getLoadableStudyShoreTwo(
      LoadingPlanModels.UllageBillRequest request,
      StreamObserver<LoadingPlanModels.UllageBillReply> responseObserver) {
    LoadingPlanModels.UllageBillReply.Builder builder =
        LoadingPlanModels.UllageBillReply.newBuilder();
    try {
      loadingPlanService.getLoadableStudyShoreTwo(request, responseObserver);
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
      List<PortLoadingPlanStowageDetails> stowageDetails =
          portLoadingPlanStowageDetailsRepository.findByPortRotationXIdInAndIsActive(
              portRotationIds, true);
      Map<Long, List<PortLoadingPlanStowageDetails>> portWiseStowages =
          stowageDetails.stream()
              .collect(Collectors.groupingBy(PortLoadingPlanStowageDetails::getPortRotationXId));
      List<com.cpdss.loadingplan.entity.BillOfLadding> blList =
          billOfLaddingRepository.findByPortIdInAndIsActive(portRotationIds, true);
      Map<Long, List<com.cpdss.loadingplan.entity.BillOfLadding>> portWiseBL =
          blList.stream()
              .collect(
                  Collectors.groupingBy(com.cpdss.loadingplan.entity.BillOfLadding::getPortId));
      portWiseCargosList.stream()
          .forEach(
              port -> {
                List<PortLoadingPlanStowageDetails> stowages =
                    portWiseStowages.get(port.getPortRotationId());
                if (stowages==null) {
                  builder.setStatus(LoadingPlanConstants.FAILED);
                  return;
                }
                List<Long> dbCargos =
                    stowages.stream()
                        .map(PortLoadingPlanStowageDetails::getCargoNominationXId)
                        .collect(Collectors.toList());
                if (!port.getCargoIdsList().equals(dbCargos)) {
                  builder.setStatus(LoadingPlanConstants.FAILED);
                  return;
                }
                List<com.cpdss.loadingplan.entity.BillOfLadding> bLValues =
                    portWiseBL.get(port.getPortId());
                if (bLValues==null) {
                  builder.setStatus(LoadingPlanConstants.FAILED);
                  return;
                }
                List<Long> dbBLCargos =
                    bLValues.stream()
                        .map(com.cpdss.loadingplan.entity.BillOfLadding::getCargoNominationId)
                        .collect(Collectors.toList());
                if (!port.getCargoIdsList().equals(dbBLCargos)) {
                  builder.setStatus(LoadingPlanConstants.FAILED);
                  return;
                }
              });
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
}
