/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.AlgoError;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlgoErrorService {

  private static final String SUCCESS = "SUCCESS";

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  public List<AlgoError> saveAllAlgoErrors(List<AlgoError> algoError)
      throws GenericServiceException {
    List<AlgoError> responseList = new ArrayList<>();
    for (AlgoError algEr : algoError) {
      responseList.add(this.saveSingleAlogError(algEr));
      log.info(
          "Save algo error for heading and errors {}, {}",
          algEr.getErrorHeading(),
          algEr.getErrorDetails());
    }
    return responseList;
  }

  public AlgoError saveSingleAlogError(AlgoError algoError) throws GenericServiceException {
    LoadableStudy.AlgoErrors.Builder reqBuilder = LoadableStudy.AlgoErrors.newBuilder();
    reqBuilder.setErrorHeading(algoError.getErrorHeading());
    reqBuilder.addAllErrorMessages(algoError.getErrorDetails());
    LoadableStudy.AlgoErrors algoErrorsResp =
        loadableStudyServiceBlockingStub.saveAlgoErrors(reqBuilder.build());
    if (!SUCCESS.equals(algoErrorsResp.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Save alog error failed - " + algoErrorsResp.getResponseStatus().getMessage(),
          algoErrorsResp.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(algoErrorsResp.getResponseStatus().getHttpStatusCode())));
    }
    return new AlgoError(algoErrorsResp.getErrorHeading(), algoErrorsResp.getErrorMessagesList());
  }
}
