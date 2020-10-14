/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.Voyage;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** @Author jerin.g */
@Service
public class GatewayService {

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";

  /**
   * @param voyage
   * @param companyId
   * @param vesselId
   * @param headers
   * @return response to contoller
   * @throws GenericServiceException CommonSuccessResponse
   */
  public CommonSuccessResponse saveVoyage(
      Voyage voyage, long companyId, long vesselId, HttpHeaders headers)
      throws GenericServiceException {
    VoyageRequest voyageRequest =
        VoyageRequest.newBuilder()
            .setCaptainId(voyage.getCaptainId())
            .setChiefOfficerId(voyage.getChiefOfficerId())
            .setCompanyId(companyId)
            .setVesselId(vesselId)
            .setVoyageNo(voyage.getVoyageNo())
            .build();

    VoyageReply voyageReply = loadableStudyServiceBlockingStub.saveVoyage(voyageRequest);
    if (SUCCESS.equalsIgnoreCase(voyageReply.getStatus())) {
      return new CommonSuccessResponse(voyageReply.getMessage(), "correlationId");
    } else {
      throw new GenericServiceException(
          "Error in calling voyage service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
