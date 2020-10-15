/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.LoadableStudy;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.Voyage;
import com.cpdss.gateway.domain.VoyageResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** GatewayLoadableStudyService - service class for loadable study related operations */
@Service
public class LoadableStudyService {

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";

  private static final String STRING_NULL = "null";

  /**
   * method for voyage save
   *
   * @param voyage
   * @param companyId
   * @param vesselId
   * @param headers
   * @return response to controller
   * @throws GenericServiceException CommonSuccessResponse
   */
  public VoyageResponse saveVoyage(
      Voyage voyage, long companyId, long vesselId, HttpHeaders headers)
      throws GenericServiceException {
    VoyageResponse voyageResponse = new VoyageResponse();
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
      voyageResponse.setResponseStatus(
          new CommonSuccessResponse(voyageReply.getMessage(), "correlationId"));
      voyageResponse.setVoyageId(voyageReply.getVoyageId());
      return voyageResponse;
    } else {
      throw new GenericServiceException(
          "Error in calling voyage service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * This method calls loadable study microservice to get a list of loadable studies by vessel and
   * voyage
   *
   * @param vesselId - the vessel id
   * @param voyageId - the voyage id
   * @param voyageId2
   * @param correlationdId - the correlation id
   * @return LoadableStudyResponse
   * @throws GenericServiceException
   */
  public LoadableStudyResponse getLoadableStudies(
      Long companyId, Long vesselId, Long voyageId, String correlationdId)
      throws GenericServiceException {
    LoadableStudyRequest request =
        LoadableStudyRequest.newBuilder().setVesselId(vesselId).setVoyageId(voyageId).build();
    LoadableStudyReply reply = this.getloadableStudyList(request);
    ;
    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to fetch loadable studies",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    List<LoadableStudy> list = new ArrayList<LoadableStudy>();
    reply
        .getLoadableStudiesList()
        .forEach(
            study -> {
              LoadableStudy loadableStudy = new LoadableStudy();
              loadableStudy.setId(study.getId());
              loadableStudy.setName(study.getName());
              loadableStudy.setDetail(study.getDetail());
              loadableStudy.setCreatedDate(study.getCreatedDate());
              loadableStudy.setCharterer(study.getCharterer());
              loadableStudy.setSubCharterer(study.getSubCharterer());
              loadableStudy.setDraftMark(
                  !STRING_NULL.equals(study.getDraftMark())
                      ? new BigDecimal(study.getDraftMark())
                      : null);
              loadableStudy.setLoadLineXId(study.getLoadLineXId());
              loadableStudy.setDraftRestriction(
                  !STRING_NULL.equals(study.getDraftRestriction())
                      ? new BigDecimal(study.getDraftRestriction())
                      : null);
              loadableStudy.setMaxTempExpected(
                  !STRING_NULL.equals(study.getMaxTempExpected())
                      ? new BigDecimal(study.getMaxTempExpected())
                      : null);
              list.add(loadableStudy);
            });
    LoadableStudyResponse response = new LoadableStudyResponse();
    response.setLoadableStudies(list);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationdId));
    return response;
  }

  /**
   * Call loadable study micro service through grpc
   *
   * @param request {@link LoadableStudyRequest}
   * @return {@link LoadableStudyReply}
   */
  public LoadableStudyReply getloadableStudyList(LoadableStudyRequest request) {
    return this.loadableStudyServiceBlockingStub.findLoadableStudiesByVesselAndVoyage(request);
  }
}
