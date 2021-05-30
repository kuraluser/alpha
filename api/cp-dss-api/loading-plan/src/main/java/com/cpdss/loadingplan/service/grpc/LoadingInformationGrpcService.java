/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.grpc;

import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
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
      StreamObserver<LoadingPlanModels.LoadingPlanInformation> responseObserver) {}
}
