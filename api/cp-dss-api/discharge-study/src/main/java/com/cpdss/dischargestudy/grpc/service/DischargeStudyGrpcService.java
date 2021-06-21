/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargestudy.grpc.service;

import com.cpdss.common.generated.dischargestudy.DischargeStudyServiceGrpc.DischargeStudyServiceImplBase;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest;
import com.cpdss.dischargestudy.service.DischargeStudyService;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
@Log4j2
@GrpcService
@Transactional
public class DischargeStudyGrpcService extends DischargeStudyServiceImplBase {

  @Autowired private DischargeStudyService dischargeStudyService;

  @Override
  public void getDischargeStudyByVoyage(
      LoadingInformationSynopticalRequest request,
      StreamObserver<LoadingInformationSynopticalReply> responseObserver) {
    log.info("Inside getLoadableStudy in Envoy Writer micro service");
    LoadingInformationSynopticalReply builder = null;
    try {

      builder = dischargeStudyService.getDischargeStudyByVoyage(request);

    } catch (Exception e) {
      log.error("Exception when getLoadableStudy in Envoy Writer micro service", e);

    } finally {
      responseObserver.onNext(builder);
      responseObserver.onCompleted();
    }
  }
}
