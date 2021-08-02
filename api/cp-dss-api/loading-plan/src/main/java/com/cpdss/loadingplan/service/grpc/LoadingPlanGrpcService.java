/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.grpc;

import com.cpdss.common.generated.Common.BillOfLadding;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc.LoadingPlanServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.repository.BillOfLaddingRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.service.LoadingPlanService;
import com.cpdss.loadingplan.service.LoadingSequenceService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.impl.LoadingPlanRuleServiceImpl;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
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
    log.info("Inside saveLoadingPlan");
    LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder =
        LoadingPlanModels.UpdateUllageDetailsResponse.newBuilder();
    try {
      builder.setMessage("Working");
      loadingPlanService.getBillOfLaddingDetails(request, builder);
      loadingPlanService.getPortWiseStowageDetails(request, builder);
      loadingPlanService.getPortWiseBallastDetails(request, builder);
      loadingPlanService.getPortWiseRobDetails(request, builder);
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
        "Inside getBillOfLaddingDetails - getting bill of ladding details agianst cargonomination Id {}",request.getCargoNominationId());
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
}
