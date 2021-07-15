/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.grpc;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc.LoadingPlanServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.service.LoadingPlanService;
import com.cpdss.loadingplan.service.impl.LoadingPlanRuleServiceImpl;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@GrpcService
public class LoadingPlanGrpcService extends LoadingPlanServiceImplBase {

  @Autowired LoadingPlanService loadingPlanService;

  @Autowired LoadingPlanRuleServiceImpl loadingPlanRuleService;

  @Override
  public void loadingPlanSynchronization(
      LoadingPlanSyncDetails request, StreamObserver<LoadingPlanSyncReply> responseObserver) {
    log.info("Inside loadablePlanSynchronization in Loading Plan micro service");
    LoadingPlanSyncReply.Builder builder = LoadingPlanSyncReply.newBuilder();
    try {

      loadingPlanService.loadingPlanSynchronization(request, builder);

      builder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());

    } catch (Exception e) {
      log.error("Exception when getLoadableStudy in Envoy Writer micro service", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(
                  "Exception when loadingPlanSyncronization in Loading Plan micro service is called")
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
}
