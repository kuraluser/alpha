/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.grpc;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc.LoadingPlanServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.service.LoadingPlanService;
import com.cpdss.loadingplan.service.LoadingSequenceService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.impl.LoadingPlanRuleServiceImpl;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

/** @author pranav.k */
@Slf4j
@GrpcService
public class LoadingPlanGrpcService extends LoadingPlanServiceImplBase {

  @Autowired LoadingPlanService loadingPlanService;
  @Autowired LoadingPlanAlgoService loadingPlanAlgoService;
  @Autowired LoadingSequenceService loadingSequenceService;

  @Autowired LoadingPlanRuleServiceImpl loadingPlanRuleService;

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
}
