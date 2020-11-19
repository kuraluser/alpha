/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadablePlan.AlgoReply;
import com.cpdss.common.generated.LoadablePlanServiceGrpc.LoadablePlanServiceBlockingStub;
import com.cpdss.common.utils.HttpStatusCode;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/** @Author jerin.g */
@Service
@Log4j2
public class LoadablePlanService {

  @GrpcClient("loadablePlanService")
  private LoadablePlanServiceBlockingStub loadablePlanServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  /**
   * @param loadableStudiesId
   * @param first
   * @return Object
   */
  public void callAlgo(Long loadableStudyId, String correlationId) throws GenericServiceException {
    log.info("Inside callAlgo gateway service with correlationId : " + correlationId);
    com.cpdss.common.generated.LoadablePlan.AlgoRequest request =
        com.cpdss.common.generated.LoadablePlan.AlgoRequest.newBuilder()
            .setLoadableStudyId(loadableStudyId)
            .build();
    AlgoReply reply = this.callAlgo(request);
    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to fetch loadable studies",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
  }

  public AlgoReply callAlgo(com.cpdss.common.generated.LoadablePlan.AlgoRequest request) {
    return this.loadablePlanServiceBlockingStub.callAlgo(request);
  }
}
