/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.grpc;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadingplan.service.LoadingInformationService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Grpc Service Implementation from common
 *
 * @author Johnsooraj.X
 * @since 20-05-2021
 */
@Slf4j
@GrpcService
public class LoadingInformationGrpcService
    extends LoadingInformationServiceGrpc.LoadingInformationServiceImplBase {

  @Autowired LoadingInformationService loadingInformationService;

  /**
   * Loading Information Is the First page in Loading module (UI).
   *
   * <p>Some data are parse at gateway service, some from LS Service.
   *
   * <p>A large JSON response needed based on the UI layout. Total 8 object counted Till - 20/05/21
   * Discussion (IT CAN CHANGE BASED ON REQUIREMENT)
   *
   * @param request - GRPC Request From Proto
   * @param responseObserver - Builder Response for GRPC Request
   */
  @Override
  public void getLoadingInformation(
      LoadingPlanModels.LoadingInformationRequest request,
      StreamObserver<LoadingPlanModels.LoadingInformation> responseObserver) {
    LoadingPlanModels.LoadingInformation.Builder builder =
        LoadingPlanModels.LoadingInformation.newBuilder();
    try {
      this.loadingInformationService.getLoadingInformation(request, builder);
    } catch (GenericServiceException e) {
      e.printStackTrace();
      builder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void saveLoadingInformation(
      LoadingInformation request, StreamObserver<ResponseStatus> responseObserver) {
    ResponseStatus.Builder builder = ResponseStatus.newBuilder();
    try {
      this.loadingInformationService.saveLoadingInformation(request);
      builder.setMessage("Successfully saved Loading information").setStatus(SUCCESS).build();
    } catch (Exception e) {
      e.printStackTrace();
      builder
          .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
          .setMessage(e.getMessage())
          .setStatus(FAILED)
          .build();
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }
}
