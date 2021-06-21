/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.dischargestudy.DischargeStudyServiceGrpc.DischargeStudyServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.DischargeStudyResponse;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/** @Author jerin.g */
@Log4j2
@Service
public class DischargeStudyService {

  @GrpcClient("dischargeStudyService")
  private DischargeStudyServiceBlockingStub dischargeStudyGrpcService;

  private static final String SUCCESS = "SUCCESS";

  /**
   * @param vesselId
   * @param voyageId
   * @param dischargeStudyId
   * @param synopticalId
   * @param correlationId
   * @return LoadableStudyResponse
   */
  public DischargeStudyResponse getDischargeStudyByVoyage(
      Long vesselId, Long voyageId, Long dischargeStudyId, Long synopticalId, String correlationId)
      throws GenericServiceException {
    log.info(
        "Inside getDischargeStudyByVoyage gateway service with correlationId : " + correlationId);
    LoadingInformationSynopticalRequest.Builder requestBuilder =
        LoadingInformationSynopticalRequest.newBuilder();
    requestBuilder.setSynopticalId(synopticalId);
    LoadingInformationSynopticalReply grpcReply =
        this.getDischargeStudyByVoyage(requestBuilder.build());
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch getDischargeStudyByVoyage",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    return null;
  }

  public LoadingInformationSynopticalReply getDischargeStudyByVoyage(
      LoadingInformationSynopticalRequest request) {
    return this.dischargeStudyGrpcService.getDischargeStudyByVoyage(request);
  }
}
