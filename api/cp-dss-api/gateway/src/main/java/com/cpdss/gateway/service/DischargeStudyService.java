/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static java.lang.String.valueOf;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.DischargeStudyDetail;
import com.cpdss.common.generated.LoadableStudy.DischargeStudyReply;
import com.cpdss.common.generated.LoadableStudy.UpdateDischargeStudyReply;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.generated.dischargestudy.DischargeStudyServiceGrpc.DischargeStudyServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.BillOfLadding;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyRequest;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyResponse;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyUpdateResponse;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyValue;
import com.cpdss.gateway.domain.LoadableQuantityCommingleCargoDetails;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.PortRotationResponse;
import java.util.ArrayList;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** @Author jerin.g */
@Log4j2
@Service
public class DischargeStudyService {

  @GrpcClient("dischargeStudyService")
  private DischargeStudyServiceBlockingStub dischargeStudyGrpcService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  @Autowired LoadableStudyService loadableStudyService;

  private static final String SUCCESS = "SUCCESS";

  /**
   * @param vesselId
   * @param voyageId
   * @param dischargeStudyId
   * @param correlationId
   * @return LoadableStudyResponse
   */
  public DischargeStudyResponse getDischargeStudyByVoyage(
      Long vesselId, Long voyageId, Long dischargeStudyId, String correlationId)
      throws GenericServiceException {
    log.info(
        "Inside getDischargeStudyByVoyage gateway service with correlationId : " + correlationId);
    LoadableStudy.LoadableStudyRequest.Builder loadableStudyRequest =
        LoadableStudy.LoadableStudyRequest.newBuilder();
    loadableStudyRequest.setVoyageId(voyageId);
    LoadableStudy.LoadablePatternConfirmedReply patternReply =
        loadableStudyServiceBlockingStub.getLoadablePatternByVoyageAndStatus(
            loadableStudyRequest.build());
    if (patternReply != null
        && patternReply.getResponseStatus() != null
        && SUCCESS.equalsIgnoreCase(patternReply.getResponseStatus().getStatus())) {
      LoadingInformationSynopticalRequest.Builder requestBuilder =
          LoadingInformationSynopticalRequest.newBuilder();
      requestBuilder.setLoadablePatternId(patternReply.getPattern().getLoadablePatternId());
      LoadingInformationSynopticalReply grpcReply =
          this.getDischargeStudyByVoyage(requestBuilder.build());
      if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
            "Failed to fetch getDischargeStudyByVoyage",
            grpcReply.getResponseStatus().getCode(),
            HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
      }
      LoadableStudy.LoadablePlanDetailsRequest.Builder loadablePlanRequest =
          LoadableStudy.LoadablePlanDetailsRequest.newBuilder();
      loadablePlanRequest.setLoadablePatternId(patternReply.getPattern().getLoadablePatternId());
      LoadableStudy.LoadableCommingleDetailsReply loadableCommingleDetailsReply =
          loadableStudyServiceBlockingStub.getLoadableCommingleByPatternId(
              loadablePlanRequest.build());

      DischargeStudyResponse dischargeStudyResponse = new DischargeStudyResponse();
      if (!SUCCESS.equals(loadableCommingleDetailsReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
            "Failed to fetch getDischargeStudyByVoyage",
            grpcReply.getResponseStatus().getCode(),
            HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
      }
      dischargeStudyResponse.setLoadableQuantityCommingleCargoDetails(
          new ArrayList<LoadableQuantityCommingleCargoDetails>());
      loadableCommingleDetailsReply
          .getLoadableQuantityCommingleCargoDetailsList()
          .forEach(
              lqcd -> {
                LoadableQuantityCommingleCargoDetails details =
                    loadableStudyService.getLoadableQuantityCommingleCargoDetails(lqcd);
                dischargeStudyResponse.getLoadableQuantityCommingleCargoDetails().add(details);
              });
      dischargeStudyResponse.setResponseStatus(
          new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
      return buildDischargeStudyResponse(grpcReply, dischargeStudyResponse);

    } else {
      throw new GenericServiceException(
          "Failed to fetch Confirmed lS",
          patternReply.getResponseStatus().getCode(),
          HttpStatusCode.BAD_REQUEST);
    }
  }

  private DischargeStudyResponse buildDischargeStudyResponse(
      LoadingInformationSynopticalReply grpcReply, DischargeStudyResponse dischargeStudyResponse) {
    ModelMapper modelMapper = new ModelMapper();

    dischargeStudyResponse.setBillOfLaddings(new ArrayList<>());
    grpcReply
        .getBillOfLaddingList()
        .forEach(
            billOfLadding -> {
              BillOfLadding blfigure = new BillOfLadding();
              blfigure =
                  modelMapper.map(billOfLadding, com.cpdss.gateway.domain.BillOfLadding.class);
              dischargeStudyResponse.getBillOfLaddings().add(blfigure);
            });
    return dischargeStudyResponse;
  }

  public LoadingInformationSynopticalReply getDischargeStudyByVoyage(
      LoadingInformationSynopticalRequest request) {
    return this.dischargeStudyGrpcService.getDischargeStudyByVoyage(request);
  }

  public PortRotationResponse getDischargeStudyPortDataByVoyage(
      Long vesselId, Long voyageId, Long dischargeStudyId, String correlationId)
      throws GenericServiceException {
    PortRotationResponse response = new PortRotationResponse();

    response =
        loadableStudyService.getLoadableStudyPortRotationList(
            vesselId, voyageId, dischargeStudyId, correlationId);
    return response;
  }

  /**
   * @param request
   * @param correlationId
   * @param headers
   * @return
   * @throws GenericServiceException
   */
  public PortRotationResponse savePortRotation(
      PortRotation request, String correlationId, HttpHeaders headers)
      throws GenericServiceException {
    log.info("Inside DischargeStudy savePortRotation");
    LoadableStudy.PortRotationDetail portRotationDetail =
        loadableStudyService.createPortRotationDetail(request, headers);
    LoadableStudy.PortRotationReply grpcReply =
        loadableStudyService.savePortRotation(portRotationDetail);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to save loadable study - ports",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(grpcReply.getResponseStatus().getHttpStatusCode())));
    }
    PortRotationResponse response = new PortRotationResponse();
    response.setId(grpcReply.getPortRotationId());
    response.setResponseStatus(
        new CommonSuccessResponse(valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * @param loadableStudyId
   * @param id
   * @param correlationId
   * @return
   * @throws GenericServiceException
   */
  public PortRotationResponse deletePortRotation(
      Long loadableStudyId, Long id, String correlationId) throws GenericServiceException {
    LoadableStudy.PortRotationRequest request =
        LoadableStudy.PortRotationRequest.newBuilder()
            .setId(id)
            .setLoadableStudyId(loadableStudyId)
            .build();
    LoadableStudy.PortRotationReply grpcReply = loadableStudyService.deletePortRotation(request);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to delete port rotation",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(grpcReply.getResponseStatus().getHttpStatusCode())));
    }
    PortRotationResponse response = new PortRotationResponse();
    response.setResponseStatus(
        new CommonSuccessResponse(valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  public LoadableStudyResponse saveDischargeStudy(
      DischargeStudyRequest request, String correlationId) throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.DischargeStudyDetail.Builder builder =
        DischargeStudyDetail.newBuilder();

    Optional.ofNullable(request.getName()).ifPresent(builder::setName);
    Optional.ofNullable(request.getEnquiryDetails()).ifPresent(builder::setEnquiryDetails);
    Optional.ofNullable(request.getVesselId()).ifPresent(builder::setVesselId);
    Optional.ofNullable(request.getVoyageId()).ifPresent(builder::setVoyageId);
    DischargeStudyReply reply = this.saveDischargeStudy(builder.build());
    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to save loadable studies",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getHttpStatusCode())));
    }
    LoadableStudyResponse response = new LoadableStudyResponse();
    response.setLoadableStudyId(reply.getId());
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  private DischargeStudyReply saveDischargeStudy(DischargeStudyDetail dischargeStudyDetail) {
    return this.loadableStudyServiceBlockingStub.saveDischargeStudy(dischargeStudyDetail);
  }

  private UpdateDischargeStudyReply updateDischargeStudy(
      DischargeStudyDetail dischargeStudyDetail) {
    return this.loadableStudyServiceBlockingStub.updateDischargeStudy(dischargeStudyDetail);
  }

  public DischargeStudyUpdateResponse updateDischargeStudy(
      @Valid DischargeStudyRequest request, String correlationId, Long dischargeStudyId)
      throws GenericServiceException {

    com.cpdss.common.generated.LoadableStudy.DischargeStudyDetail.Builder builder =
        DischargeStudyDetail.newBuilder();

    Optional.ofNullable(request.getName()).ifPresent(builder::setName);
    Optional.ofNullable(request.getEnquiryDetails()).ifPresent(builder::setEnquiryDetails);
    Optional.ofNullable(dischargeStudyId).ifPresent(builder::setDischargeStudyId);

    UpdateDischargeStudyReply reply = this.updateDischargeStudy(builder.build());
    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to save loadable studies",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getHttpStatusCode())));
    }
    DischargeStudyUpdateResponse response = new DischargeStudyUpdateResponse();
    response.setDischargeStudy(
        new DischargeStudyValue(
            reply.getDischargeStudy().getId(),
            reply.getDischargeStudy().getName(),
            reply.getDischargeStudy().getEnquiryDetails()));
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  public OnHandQuantityResponse getOnHandQuantity(
      Long vesselId, Long dischargeStudyId, Long portRotationId, String correlationId)
      throws GenericServiceException {
    LoadableStudy.OnHandQuantityRequest request =
        LoadableStudy.OnHandQuantityRequest.newBuilder()
            .setVesselId(vesselId)
            .setLoadableStudyId(dischargeStudyId)
            .setPortRotationId(portRotationId)
            .build();
    LoadableStudy.OnHandQuantityReply grpcReply = loadableStudyService.getOnHandQuantity(request);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch on hand quantities",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    return loadableStudyService.buildOnHandQuantityResponse(grpcReply, correlationId);
  }

  public OnHandQuantityResponse saveOnHandQuantity(OnHandQuantity request, String correlationId)
      throws GenericServiceException {
    return this.loadableStudyService.saveOnHandQuantity(request, correlationId);
  }
}
