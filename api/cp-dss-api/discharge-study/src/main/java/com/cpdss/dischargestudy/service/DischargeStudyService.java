/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargestudy.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
@Log4j2
@Transactional
@Service
public class DischargeStudyService {

  @GrpcClient("loadingInformationService")
  private LoadingInformationServiceGrpc.LoadingInformationServiceBlockingStub
      loadingInfoServiceBlockingStub;

  public LoadingInformationSynopticalReply getDischargeStudyByVoyage(
      LoadingInformationSynopticalRequest request) throws GenericServiceException {

    return loadingInfoServiceBlockingStub.getLoadigInformationBySynoptical(request);
  }
}
