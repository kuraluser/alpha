/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import static java.lang.String.valueOf;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequest;
import com.cpdss.common.generated.CargoInfoServiceGrpc.CargoInfoServiceBlockingStub;
import com.cpdss.common.generated.LoadableStudy.AlgoReply;
import com.cpdss.common.generated.LoadableStudy.AlgoRequest;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.CommingleCargoReply;
import com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest;
import com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply;
import com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachment;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.Builder;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest;
import com.cpdss.common.generated.LoadableStudy.LoadingPortDetail;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.Operation;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetail;
import com.cpdss.common.generated.LoadableStudy.PortRotationReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply;
import com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest;
import com.cpdss.common.generated.LoadableStudy.SynopticalDataReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalDataRequest;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest;
import com.cpdss.common.generated.LoadableStudy.TankDetail;
import com.cpdss.common.generated.LoadableStudy.TankList;
import com.cpdss.common.generated.LoadableStudy.ValveSegregationReply;
import com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest;
import com.cpdss.common.generated.LoadableStudy.VoyageDetail;
import com.cpdss.common.generated.LoadableStudy.VoyageListReply;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequest;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceBlockingStub;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.AlgoPatternResponse;
import com.cpdss.gateway.domain.AlgoStatusRequest;
import com.cpdss.gateway.domain.AlgoStatusResponse;
import com.cpdss.gateway.domain.Cargo;
import com.cpdss.gateway.domain.CargoGroup;
import com.cpdss.gateway.domain.CargoNomination;
import com.cpdss.gateway.domain.CargoNominationResponse;
import com.cpdss.gateway.domain.CommingleCargo;
import com.cpdss.gateway.domain.CommingleCargoResponse;
import com.cpdss.gateway.domain.CommonResponse;
import com.cpdss.gateway.domain.DischargingPortRequest;
import com.cpdss.gateway.domain.LoadablePattern;
import com.cpdss.gateway.domain.LoadablePatternCargoDetails;
import com.cpdss.gateway.domain.LoadablePatternDetailsResponse;
import com.cpdss.gateway.domain.LoadablePatternResponse;
import com.cpdss.gateway.domain.LoadablePlanBallastDetails;
import com.cpdss.gateway.domain.LoadablePlanDetailsResponse;
import com.cpdss.gateway.domain.LoadablePlanStowageDetails;
import com.cpdss.gateway.domain.LoadablePlanSynopticalRecord;
import com.cpdss.gateway.domain.LoadableQuantity;
import com.cpdss.gateway.domain.LoadableQuantityCommingleCargoDetails;
import com.cpdss.gateway.domain.LoadableQuantityResponse;
import com.cpdss.gateway.domain.LoadableStudy;
import com.cpdss.gateway.domain.LoadableStudyAttachmentData;
import com.cpdss.gateway.domain.LoadableStudyAttachmentResponse;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.LoadableStudyStatusResponse;
import com.cpdss.gateway.domain.LoadingPort;
import com.cpdss.gateway.domain.OnBoardQuantity;
import com.cpdss.gateway.domain.OnBoardQuantityResponse;
import com.cpdss.gateway.domain.OnHandQuantity;
import com.cpdss.gateway.domain.OnHandQuantityResponse;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.PortRotationResponse;
import com.cpdss.gateway.domain.Purpose;
import com.cpdss.gateway.domain.SynopticalCargoRecord;
import com.cpdss.gateway.domain.SynopticalOhqRecord;
import com.cpdss.gateway.domain.SynopticalRecord;
import com.cpdss.gateway.domain.SynopticalTableResponse;
import com.cpdss.gateway.domain.ValveSegregation;
import com.cpdss.gateway.domain.VesselTank;
import com.cpdss.gateway.domain.Voyage;
import com.cpdss.gateway.domain.VoyageResponse;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/** GatewayLoadableStudyService - service class for loadable study related operations */
@Service
@Log4j2
public class LoadableStudyService {

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyServiceBlockingStub;

  @GrpcClient("cargoInfoService")
  private CargoInfoServiceBlockingStub cargoInfoServiceBlockingStub;

  @GrpcClient("portInfoService")
  private PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  private static final String SUCCESS = "SUCCESS";
  private static final int LOADABLE_STUDY_ATTACHEMENT_MAX_SIZE = 1 * 1024 * 1024;
  private static final List<String> ATTACHMENT_ALLOWED_EXTENSIONS =
      Arrays.asList("docx", "pdf", "txt", "jpg", "png", "msg", "eml");
  private static final String ARR = "ARR";
  private static final String DEP = "DEP";

  private static final Long FRESH_WATER_TANK_CATEGORY_ID = 3L;
  private static final Long FUEL_OIL_TANK_CATEGORY_ID = 5L;
  private static final Long DIESEL_OIL_TANK_CATEGORY_ID = 6L;
  private static final Long LUBRICATING_OIL_TANK_CATEGORY_ID = 14L;
  private static final Long LUBRICANT_OIL_TANK_CATEGORY_ID = 19L;

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
      Voyage voyage, long companyId, long vesselId, String correlationId)
      throws GenericServiceException {
    VoyageResponse voyageResponse = new VoyageResponse();
    VoyageRequest voyageRequest =
        VoyageRequest.newBuilder()
            .setCaptainId(voyage.getCaptainId())
            .setChiefOfficerId(voyage.getChiefOfficerId())
            .setCompanyId(companyId)
            .setVesselId(vesselId)
            .setVoyageNo(voyage.getVoyageNo())
            .setStartDate(!StringUtils.isEmpty(voyage.getStartDate()) ? voyage.getStartDate() : "")
            .setEndDate(!StringUtils.isEmpty(voyage.getEndDate()) ? voyage.getEndDate() : "")
            .build();

    VoyageReply voyageReply = this.saveVoyage(voyageRequest);
    if (!SUCCESS.equalsIgnoreCase(voyageReply.getResponseStatus().getStatus())) {
      if (CommonErrorCodes.E_CPDSS_VOYAGE_EXISTS.equalsIgnoreCase(
          voyageReply.getResponseStatus().getCode())) {
        throw new GenericServiceException(
            voyageReply.getResponseStatus().getMessage(),
            voyageReply.getResponseStatus().getCode(),
            HttpStatusCode.valueOf(Integer.valueOf(CommonErrorCodes.E_HTTP_BAD_REQUEST)));
      } else {
        throw new GenericServiceException(
            voyageReply.getResponseStatus().getMessage(),
            voyageReply.getResponseStatus().getCode(),
            HttpStatusCode.valueOf(Integer.valueOf(voyageReply.getResponseStatus().getCode())));
      }
    } else {
      voyageResponse.setResponseStatus(
          new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
      voyageResponse.setVoyageId(voyageReply.getVoyageId());
    }
    return voyageResponse;
  }

  /**
   * @param voyageRequest
   * @return VoyageReply
   */
  public VoyageReply saveVoyage(VoyageRequest voyageRequest) {
    return loadableStudyServiceBlockingStub.saveVoyage(voyageRequest);
  }

  /**
   * @param loadableQuantity
   * @param companyId
   * @param vesselId
   * @param loadableStudiesId
   * @param headers
   * @return
   * @throws GenericServiceException CommonSuccessResponse
   */
  public LoadableQuantityResponse saveLoadableQuantity(
      LoadableQuantity loadableQuantity, long loadableStudiesId, String correlationId)
      throws GenericServiceException {
    LoadableQuantityResponse loadableQuantityResponse = new LoadableQuantityResponse();
    LoadableQuantityRequest.Builder builder = LoadableQuantityRequest.newBuilder();
    builder.setConstant(loadableQuantity.getConstant());
    Optional.ofNullable(loadableQuantity.getDisplacmentDraftRestriction())
        .ifPresent(builder::setDisplacmentDraftRestriction);
    Optional.ofNullable(loadableQuantity.getDistanceFromLastPort())
        .ifPresent(builder::setDistanceFromLastPort);

    builder.setDwt(loadableQuantity.getDwt());
    builder.setEstDOOnBoard(loadableQuantity.getEstDOOnBoard());
    builder.setEstFOOnBoard(loadableQuantity.getEstFOOnBoard());
    builder.setEstFreshWaterOnBoard(loadableQuantity.getEstFreshWaterOnBoard());
    builder.setEstSagging(loadableQuantity.getEstSagging());
    Optional.ofNullable(loadableQuantity.getEstSeaDensity()).ifPresent(builder::setEstSeaDensity);
    Optional.ofNullable(loadableQuantity.getFoConsumptionPerDay())
        .ifPresent(builder::setFoConsumptionPerDay);
    builder.setOtherIfAny(loadableQuantity.getOtherIfAny());
    builder.setSaggingDeduction(loadableQuantity.getSaggingDeduction());
    Optional.ofNullable(loadableQuantity.getSgCorrection()).ifPresent(builder::setSgCorrection);
    builder.setTotalQuantity(loadableQuantity.getTotalQuantity());
    builder.setTpc(loadableQuantity.getTpc());
    Optional.ofNullable(loadableQuantity.getVesselAverageSpeed())
        .ifPresent(builder::setVesselAverageSpeed);
    Optional.ofNullable(loadableQuantity.getVesselLightWeight())
        .ifPresent(builder::setVesselLightWeight);
    Optional.ofNullable(loadableQuantity.getPortId()).ifPresent(builder::setPortId);
    Optional.ofNullable(loadableQuantity.getBoilerWaterOnBoard())
        .ifPresent(builder::setBoilerWaterOnBoard);
    Optional.ofNullable(loadableQuantity.getBallast()).ifPresent(builder::setBallast);
    Optional.ofNullable(loadableQuantity.getRunningHours()).ifPresent(builder::setRunningHours);
    Optional.ofNullable(loadableQuantity.getRunningDays()).ifPresent(builder::setRunningDays);
    Optional.ofNullable(loadableQuantity.getFoConInSZ()).ifPresent(builder::setFoConInSZ);
    Optional.ofNullable(loadableQuantity.getDraftRestriction())
        .ifPresent(builder::setDraftRestriction);
    Optional.ofNullable(loadableQuantity.getSubTotal()).ifPresent(builder::setSubTotal);
    Optional.ofNullable(loadableQuantity.getFoConsumptionPerDay())
        .ifPresent(builder::setFoConsumptionPerDay);
    builder.setLoadableStudyId(loadableStudiesId).build();

    LoadableQuantityReply loadableQuantityReply = this.saveLoadableQuantity(builder.build());
    if (!SUCCESS.equalsIgnoreCase(loadableQuantityReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          loadableQuantityReply.getResponseStatus().getMessage(),
          loadableQuantityReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(loadableQuantityReply.getResponseStatus().getCode())));
    } else {

      loadableQuantityResponse.setResponseStatus(
          new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
      loadableQuantityResponse.setLoadableQuantityId(loadableQuantityReply.getLoadableQuantityId());
    }
    return loadableQuantityResponse;
  }

  public LoadableQuantityReply saveLoadableQuantity(
      LoadableQuantityRequest loadableQuantityRequest) {
    return loadableStudyServiceBlockingStub.saveLoadableQuantity(loadableQuantityRequest);
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
    log.info("fetching loadable studies. correlationId: {}", correlationdId);
    LoadableStudyRequest request =
        LoadableStudyRequest.newBuilder().setVesselId(vesselId).setVoyageId(voyageId).build();
    LoadableStudyReply reply = this.getloadableStudyList(request);
    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to fetch loadable studies",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
    List<LoadableStudy> list = new ArrayList<>();
    for (LoadableStudyDetail grpcReply : reply.getLoadableStudiesList()) {
      LoadableStudy dto = new LoadableStudy();
      dto.setId(grpcReply.getId());
      dto.setName(grpcReply.getName());
      dto.setDetail(grpcReply.getDetail());
      dto.setCreatedDate(grpcReply.getCreatedDate());
      dto.setCharterer(grpcReply.getCharterer());
      dto.setSubCharterer(grpcReply.getSubCharterer());
      dto.setDraftMark(
          StringUtils.isEmpty(grpcReply.getDraftMark())
              ? null
              : new BigDecimal(grpcReply.getDraftMark()));
      dto.setLoadLineXId(grpcReply.getLoadLineXId());
      dto.setDraftRestriction(
          StringUtils.isEmpty(grpcReply.getDraftRestriction())
              ? null
              : new BigDecimal(grpcReply.getDraftRestriction()));
      dto.setMaxAirTemperature(
          StringUtils.isEmpty(grpcReply.getMaxAirTemperature())
              ? null
              : new BigDecimal(grpcReply.getMaxAirTemperature()));
      dto.setMaxWaterTemperature(
          StringUtils.isEmpty(grpcReply.getMaxWaterTemperature())
              ? null
              : new BigDecimal(grpcReply.getMaxWaterTemperature()));
      dto.setDischargingPortIds(grpcReply.getDischargingPortIdsList());
      dto.setStatus(grpcReply.getStatus());
      dto.setStatusId(grpcReply.getStatusId());

      List<LoadableStudyAttachmentData> attachmentList = new ArrayList();

      grpcReply
          .getAttachmentsList()
          .forEach(
              attachments -> {
                LoadableStudyAttachmentData loadableStudyAttachment =
                    new LoadableStudyAttachmentData();
                loadableStudyAttachment.setFileName(attachments.getFileName());
                loadableStudyAttachment.setId(attachments.getId());
                attachmentList.add(loadableStudyAttachment);
              });
      dto.setLoadableStudyAttachment(attachmentList);
      dto.setDischargingCargoid(grpcReply.getDischargingCargoId());

      list.add(dto);
    }
    LoadableStudyResponse response = new LoadableStudyResponse();
    response.setLoadableStudies(list);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationdId));
    return response;
  }

  /**
   * Call loadable study microservice through grpc
   *
   * @param request {@link LoadableStudyRequest}
   * @return {@link LoadableStudyReply}
   */
  public LoadableStudyReply getloadableStudyList(LoadableStudyRequest request) {
    return this.loadableStudyServiceBlockingStub.findLoadableStudiesByVesselAndVoyage(request);
  }

  public LoadableStudyResponse saveLoadableStudy(
      final LoadableStudy request, String correlationId, MultipartFile[] files)
      throws GenericServiceException, IOException {
    this.validateLoadableStudyFiles(files);
    Builder builder = LoadableStudyDetail.newBuilder();
    Optional.ofNullable(request.getName()).ifPresent(builder::setName);
    Optional.ofNullable(request.getDetail()).ifPresent(builder::setDetail);
    Optional.ofNullable(request.getCharterer()).ifPresent(builder::setCharterer);
    Optional.ofNullable(request.getSubCharterer()).ifPresent(builder::setSubCharterer);
    Optional.ofNullable(request.getDraftMark())
        .ifPresent(draftMark -> builder.setDraftMark(String.valueOf(draftMark)));
    Optional.ofNullable(request.getLoadLineXId()).ifPresent(builder::setLoadLineXId);
    Optional.ofNullable(request.getDraftRestriction())
        .ifPresent(
            draftRestriction -> builder.setDraftRestriction(String.valueOf(draftRestriction)));
    Optional.ofNullable(request.getMaxAirTemperature())
        .ifPresent(maxTemp -> builder.setMaxAirTemperature(String.valueOf(maxTemp)));
    Optional.ofNullable(request.getMaxWaterTemperature())
        .ifPresent(maxTemp -> builder.setMaxWaterTemperature(String.valueOf(maxTemp)));
    Optional.ofNullable(request.getVesselId()).ifPresent(builder::setVesselId);
    Optional.ofNullable(request.getVoyageId()).ifPresent(builder::setVoyageId);
    Optional.ofNullable(request.getId()).ifPresent(builder::setId);
    Optional.ofNullable(request.getCreatedFromId()).ifPresent(builder::setDuplicatedFromId);
    for (MultipartFile file : files) {
      String orginalFileName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
      String fileName = orginalFileName.substring(0, orginalFileName.lastIndexOf("."));
      String extension = orginalFileName.substring(orginalFileName.lastIndexOf(".")).toLowerCase();
      builder.addAttachments(
          LoadableStudyAttachment.newBuilder()
              .setFileName(fileName + System.currentTimeMillis() + extension)
              .setByteString(ByteString.copyFrom(file.getBytes()))
              .build());
    }
    LoadableStudyReply reply = this.saveLoadableStudy(builder.build());
    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to save loadable studies",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
    LoadableStudyResponse response = new LoadableStudyResponse();
    response.setLoadableStudyId(reply.getId());
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  private void validateLoadableStudyFiles(MultipartFile[] files)
      throws GenericServiceException, IOException {
    log.info("validating attachment files");
    for (MultipartFile file : files) {
      if (file.getSize() > LOADABLE_STUDY_ATTACHEMENT_MAX_SIZE) {
        throw new GenericServiceException(
            "loadable study attachment size exceeds maximum allowed size",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      String originalFileName =
          file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
      if (!ATTACHMENT_ALLOWED_EXTENSIONS.contains(
          originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase())) {
        throw new GenericServiceException(
            "unsupported file type",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    }
  }

  public LoadableStudyReply saveLoadableStudy(LoadableStudyDetail grpcRequest) {
    return this.loadableStudyServiceBlockingStub.saveLoadableStudy(grpcRequest);
  }

  /**
   * Retrieves the cargo information from cargo master, port information from port master and cargo
   * nomination details from loadable-study service
   *
   * @param loadableStudyId
   * @param headers
   * @return
   * @throws GenericServiceException
   */
  public CargoNominationResponse getCargoNomination(Long loadableStudyId, HttpHeaders headers)
      throws GenericServiceException {
    CargoNominationResponse cargoNominationResponse = new CargoNominationResponse();
    // Build response with response status
    buildCargoNominationResponseWithResponseStatus(cargoNominationResponse);
    // Retrieve cargo information from cargo master
    CargoRequest cargoRequest =
        CargoRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
    CargoReply cargoReply = cargoInfoServiceBlockingStub.getCargoInfo(cargoRequest);
    if (cargoReply != null
        && cargoReply.getResponseStatus() != null
        && SUCCESS.equalsIgnoreCase(cargoReply.getResponseStatus().getStatus())) {
      buildCargoNominationResponseWithCargo(cargoNominationResponse, cargoReply);
    } else {
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    // Retrieve cargo Nominations from cargo nomination table
    CargoNominationRequest cargoNominationRequest =
        CargoNominationRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
    CargoNominationReply cargoNominationReply =
        loadableStudyServiceBlockingStub.getCargoNominationById(cargoNominationRequest);
    if (SUCCESS.equalsIgnoreCase(cargoNominationReply.getResponseStatus().getStatus())) {
      buildCargoNominationResponse(cargoNominationResponse, cargoNominationReply);
    } else {
      throw new GenericServiceException(
          "Error calling getCargoNominationById service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    // Retrieve segregation List
    ValveSegregationRequest valveSegregationRequest =
        ValveSegregationRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
    ValveSegregationReply valveSegregationReply =
        loadableStudyServiceBlockingStub.getValveSegregation(valveSegregationRequest);
    if (SUCCESS.equalsIgnoreCase(valveSegregationReply.getResponseStatus().getStatus())) {
      buildCargoNominationResponseWithValveSegregation(
          cargoNominationResponse, valveSegregationReply);
    } else {
      throw new GenericServiceException(
          "Error calling getValveSegregation service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return cargoNominationResponse;
  }

  private CargoNominationResponse buildCargoNominationResponseWithResponseStatus(
      CargoNominationResponse cargoNominationResponse) {
    // set response status irrespective of whether cargo details are available
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    cargoNominationResponse.setResponseStatus(commonSuccessResponse);
    return cargoNominationResponse;
  }

  /**
   * Builds the cargoNomination saved list from database
   *
   * @param cargoNominationResponse
   * @param reply
   * @return
   */
  private CargoNominationResponse buildCargoNominationResponse(
      CargoNominationResponse cargoNominationResponse, CargoNominationReply reply) {
    if (reply != null && !reply.getCargoNominationsList().isEmpty()) {
      List<CargoNomination> cargoNominationList = new ArrayList<>();
      reply
          .getCargoNominationsList()
          .forEach(
              cargoNominationDetail -> {
                CargoNomination cargoNomination = new CargoNomination();
                cargoNomination.setId(cargoNominationDetail.getId());
                cargoNomination.setLoadableStudyId(cargoNominationDetail.getLoadableStudyId());
                cargoNomination.setPriority(cargoNominationDetail.getPriority());
                cargoNomination.setColor(cargoNominationDetail.getColor());
                cargoNomination.setCargoId(cargoNominationDetail.getCargoId());
                cargoNomination.setAbbreviation(cargoNominationDetail.getAbbreviation());
                if (!CollectionUtils.isEmpty(cargoNominationDetail.getLoadingPortDetailsList())) {
                  List<LoadingPort> loadingPortList = new ArrayList<>();
                  cargoNominationDetail
                      .getLoadingPortDetailsList()
                      .forEach(
                          port -> {
                            LoadingPort loadingPort = new LoadingPort();
                            loadingPort.setId(port.getPortId());
                            loadingPort.setQuantity(
                                port.getQuantity() != null
                                    ? new BigDecimal(port.getQuantity())
                                    : new BigDecimal("0"));
                            loadingPortList.add(loadingPort);
                          });
                  cargoNomination.setLoadingPorts(loadingPortList);
                }
                // TODO: add quantity column whena added in database
                // cargoNomination.setQuantity(cargoNominationDetail.getQuantity);
                cargoNomination.setMaxTolerance(
                    !StringUtils.isEmpty(cargoNominationDetail.getMaxTolerance())
                        ? new BigDecimal(cargoNominationDetail.getMaxTolerance())
                        : new BigDecimal("0"));
                cargoNomination.setMinTolerance(
                    !StringUtils.isEmpty(cargoNominationDetail.getMinTolerance())
                        ? new BigDecimal(cargoNominationDetail.getMinTolerance())
                        : new BigDecimal("0"));
                cargoNomination.setApi(
                    !StringUtils.isEmpty(cargoNominationDetail.getApiEst())
                        ? new BigDecimal(cargoNominationDetail.getApiEst())
                        : new BigDecimal("0"));
                cargoNomination.setTemperature(
                    !StringUtils.isEmpty(cargoNominationDetail.getTempEst())
                        ? new BigDecimal(cargoNominationDetail.getTempEst())
                        : new BigDecimal("0"));
                cargoNomination.setSegregationId(cargoNominationDetail.getSegregationId());
                cargoNominationList.add(cargoNomination);
              });
      cargoNominationResponse.setCargoNominations(cargoNominationList);
    }
    return cargoNominationResponse;
  }

  /**
   * @param cargoNominationResponse
   * @param cargoReply
   * @return
   */
  private CargoNominationResponse buildCargoNominationResponseWithValveSegregation(
      CargoNominationResponse cargoNominationResponse, ValveSegregationReply reply) {
    if (reply != null && !reply.getValveSegregationList().isEmpty()) {
      List<ValveSegregation> segregationList = new ArrayList<>();
      reply
          .getValveSegregationList()
          .forEach(
              segregationDetail -> {
                ValveSegregation valveSegregation = new ValveSegregation();
                valveSegregation.setId(segregationDetail.getId());
                valveSegregation.setName(segregationDetail.getName());
                segregationList.add(valveSegregation);
              });
      cargoNominationResponse.setSegregations(segregationList);
    }
    return cargoNominationResponse;
  }

  /**
   * @param cargoNominationResponse
   * @param cargoReply
   * @return
   */
  private CargoNominationResponse buildCargoNominationResponseWithCargo(
      CargoNominationResponse cargoNominationResponse, CargoReply cargoReply) {
    if (cargoReply != null && !cargoReply.getCargosList().isEmpty()) {
      List<Cargo> cargoList = new ArrayList<>();
      cargoReply
          .getCargosList()
          .forEach(
              cargoDetail -> {
                Cargo cargo = new Cargo();
                cargo.setId(cargoDetail.getId());
                cargo.setApi(cargoDetail.getApi());
                cargo.setAbbreviation(cargoDetail.getAbbreviation());
                cargo.setName(cargoDetail.getCrudeType());
                cargoList.add(cargo);
              });
      cargoNominationResponse.setCargos(cargoList);
    }
    return cargoNominationResponse;
  }

  /**
   * Save cargo nomination details using loadable-study service
   *
   * @param loadableStudyId
   * @param headers
   * @return
   * @throws GenericServiceException
   */
  public CargoNominationResponse saveCargoNomination(
      Long vesselId,
      Long voyageId,
      Long loadableStudyId,
      CargoNomination request,
      HttpHeaders headers)
      throws GenericServiceException {
    CargoNominationResponse cargoNominationResponse = new CargoNominationResponse();
    // Build response with response status
    buildCargoNominationResponseWithResponseStatus(cargoNominationResponse);
    // Build cargoNomination payload for grpc call
    com.cpdss.common.generated.LoadableStudy.CargoNominationRequest.Builder builder =
        CargoNominationRequest.newBuilder();
    Optional.ofNullable(vesselId).ifPresent(builder::setVesselId);
    Optional.ofNullable(voyageId).ifPresent(builder::setVoyageId);
    Optional.ofNullable(loadableStudyId).ifPresent(builder::setLoadableStudyId);
    // build inner cargoNomination detail object
    CargoNominationDetail.Builder cargoNominationDetailbuilder = CargoNominationDetail.newBuilder();
    Optional.ofNullable(request.getId()).ifPresent(cargoNominationDetailbuilder::setId);
    Optional.ofNullable(request.getLoadableStudyId())
        .ifPresent(cargoNominationDetailbuilder::setLoadableStudyId);
    Optional.ofNullable(request.getPriority()).ifPresent(cargoNominationDetailbuilder::setPriority);
    Optional.ofNullable(request.getColor()).ifPresent(cargoNominationDetailbuilder::setColor);
    Optional.ofNullable(request.getCargoId()).ifPresent(cargoNominationDetailbuilder::setCargoId);
    Optional.ofNullable(request.getAbbreviation())
        .ifPresent(cargoNominationDetailbuilder::setAbbreviation);
    // build inner loadingPort details object
    if (!CollectionUtils.isEmpty(request.getLoadingPorts())) {
      request
          .getLoadingPorts()
          .forEach(
              loadingPort -> {
                LoadingPortDetail.Builder loadingPortDetailBuilder = LoadingPortDetail.newBuilder();
                Optional.ofNullable(loadingPort.getId())
                    .ifPresent(loadingPortDetailBuilder::setPortId);
                Optional.ofNullable(loadingPort.getQuantity())
                    .ifPresent(
                        quantity -> loadingPortDetailBuilder.setQuantity(String.valueOf(quantity)));
                cargoNominationDetailbuilder.addLoadingPortDetails(loadingPortDetailBuilder);
              });
    }
    Optional.ofNullable(request.getQuantity())
        .ifPresent(quantity -> cargoNominationDetailbuilder.setQuantity(String.valueOf(quantity)));
    Optional.ofNullable(request.getMaxTolerance())
        .ifPresent(
            maxTolerance ->
                cargoNominationDetailbuilder.setMaxTolerance(String.valueOf(maxTolerance)));
    Optional.ofNullable(request.getMinTolerance())
        .ifPresent(
            minTolerance ->
                cargoNominationDetailbuilder.setMinTolerance(String.valueOf(minTolerance)));
    Optional.ofNullable(request.getApi())
        .ifPresent(api -> cargoNominationDetailbuilder.setApiEst(String.valueOf(api)));
    Optional.ofNullable(request.getTemperature())
        .ifPresent(
            temperature -> cargoNominationDetailbuilder.setTempEst(String.valueOf(temperature)));
    Optional.ofNullable(request.getSegregationId())
        .ifPresent(cargoNominationDetailbuilder::setSegregationId);
    builder.setCargoNominationDetail(cargoNominationDetailbuilder);
    CargoNominationRequest cargoNominationRequest = builder.build();
    CargoNominationReply cargoNominationReply =
        loadableStudyServiceBlockingStub.saveCargoNomination(cargoNominationRequest);
    if (cargoNominationReply != null
        && cargoNominationReply.getResponseStatus() != null
        && SUCCESS.equalsIgnoreCase(cargoNominationReply.getResponseStatus().getStatus())) {
      cargoNominationResponse.setCargoNominationId(cargoNominationReply.getCargoNominationId());
    } else {
      throw new GenericServiceException(
          "Error in saving cargo nomination",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return cargoNominationResponse;
  }

  /**
   * Get lodable study port rotation data
   *
   * @param vesselId - the vessle id
   * @param voyageId - the voyage id
   * @param loadableStudyId - the loadable study id
   * @return {@link PortResponse}
   * @throws GenericServiceException
   */
  public PortRotationResponse getLoadableStudyPortRotationList(
      Long vesselId, Long voyageId, Long loadableStudyId, String correlationId)
      throws GenericServiceException {
    PortRotationResponse response = new PortRotationResponse();
    PortRotationReply grpcReply =
        this.getLoadableStudyPortRotationList(
            PortRotationRequest.newBuilder()
                .setLoadableStudyId(loadableStudyId)
                .setVesselId(vesselId)
                .setVoyageId(voyageId)
                .build());
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to fetch loadable study - ports",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    response.setPortList(new ArrayList<>());
    for (PortRotationDetail portDetail : grpcReply.getPortsList()) {
      PortRotation port = new PortRotation();
      port.setId(portDetail.getId());
      port.setPortId(0 == portDetail.getPortId() ? null : portDetail.getPortId());
      port.setBerthId(0 == portDetail.getBerthId() ? null : portDetail.getBerthId());
      port.setPortOrder(0 == portDetail.getPortOrder() ? null : portDetail.getPortOrder());
      port.setLoadableStudyId(loadableStudyId);
      port.setOperationId(0 == portDetail.getOperationId() ? null : portDetail.getOperationId());
      port.setSeaWaterDensity(
          isEmpty(portDetail.getSeaWaterDensity())
              ? null
              : new BigDecimal(portDetail.getSeaWaterDensity()));
      port.setDistanceBetweenPorts(
          isEmpty(portDetail.getDistanceBetweenPorts())
              ? null
              : new BigDecimal(portDetail.getDistanceBetweenPorts()));
      port.setTimeOfStay(
          isEmpty(portDetail.getTimeOfStay()) ? null : new BigDecimal(portDetail.getTimeOfStay()));
      port.setMaxDraft(
          isEmpty(portDetail.getMaxDraft()) ? null : new BigDecimal(portDetail.getMaxDraft()));
      port.setMaxAirDraft(
          isEmpty(portDetail.getMaxAirDraft())
              ? null
              : new BigDecimal(portDetail.getMaxAirDraft()));
      port.setEta(portDetail.getEta());
      port.setEtd(portDetail.getEtd());
      port.setLayCanFrom(portDetail.getLayCanFrom());
      port.setLayCanTo(portDetail.getLayCanTo());
      // Fetch distance, eta/etd actual values from synoptical table
      SynopticalDataRequest synopticalRequest =
          SynopticalDataRequest.newBuilder()
              .setLoadableStudyId(loadableStudyId)
              .setPortId(portDetail.getPortId())
              .build();
      SynopticalDataReply synopticalDataReply =
          loadableStudyServiceBlockingStub.getSynopticalDataByPortId(synopticalRequest);
      if (synopticalDataReply != null
          && synopticalDataReply.getResponseStatus() != null
          && !SUCCESS.equalsIgnoreCase(synopticalDataReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
            "Error in getLoadableStudyPortRotationList - getSynopticalDataByPortId",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      // add distance, eta/etd actual values from synoptical table
      if (synopticalDataReply != null
          && !CollectionUtils.isEmpty(synopticalDataReply.getSynopticalRecordsList())) {
        synopticalDataReply
            .getSynopticalRecordsList()
            .forEach(
                record -> {
                  port.setDistanceBetweenPorts(
                      !StringUtils.isEmpty(record.getDistance())
                          ? new BigDecimal(record.getDistance())
                          : BigDecimal.ZERO);
                  if (ARR.equalsIgnoreCase(record.getOperationType())) {
                    port.setEtaActual(record.getEtaEtdActual());
                  } else {
                    port.setEtdActual(record.getEtaEtdActual());
                  }
                });
      }
      response.getPortList().add(port);
      response.setResponseStatus(
          new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    }
    response.setOperations(new ArrayList<>());
    for (Operation operation : grpcReply.getOperationsList()) {
      com.cpdss.gateway.domain.Operation op = new com.cpdss.gateway.domain.Operation();
      op.setId(operation.getId());
      op.setOperationName(operation.getOperationName());
      response.getOperations().add(op);
    }
    return response;
  }

  /**
   * Call grpc server to get list of ports against loadable study
   *
   * @param request {@link PortRequest}
   * @return {@link PortReply}
   */
  public PortRotationReply getLoadableStudyPortRotationList(PortRotationRequest request) {
    return this.loadableStudyServiceBlockingStub.getLoadableStudyPortRotation(request);
  }

  /**
   * Save port rotation for a loadable study
   *
   * @param request
   * @param first
   * @return
   * @throws GenericServiceException
   */
  public PortRotationResponse savePortRotation(PortRotation request, String correlationId)
      throws GenericServiceException {
    log.info("Inside savePortRotation");
    PortRotationReply grpcReply = this.savePortRotation(this.createPortRotationDetail(request));
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to save loadable study - ports",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    PortRotationResponse response = new PortRotationResponse();
    response.setId(grpcReply.getPortRotationId());
    response.setResponseStatus(
        new CommonSuccessResponse(valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Save all port rotations for a loadable study
   *
   * @param request
   * @param first
   * @return
   * @throws GenericServiceException
   */
  public PortRotationResponse savePortRotationList(
      com.cpdss.gateway.domain.PortRotationRequest request, String correlationId)
      throws GenericServiceException {
    log.info("Inside savePortRotationList");
    PortRotationRequest savePortRotationListRequest = buildSavePortRotationList(request);
    PortRotationReply grpcReply =
        loadableStudyServiceBlockingStub.saveLoadableStudyPortRotationList(
            savePortRotationListRequest);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to save loadable study - ports",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    PortRotationResponse response = new PortRotationResponse();
    response.setResponseStatus(
        new CommonSuccessResponse(valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  private PortRotationRequest buildSavePortRotationList(
      com.cpdss.gateway.domain.PortRotationRequest request) {
    PortRotationRequest.Builder portListBuilder = PortRotationRequest.newBuilder();
    portListBuilder.setLoadableStudyId(request.getLoadableStudyId());
    if (!CollectionUtils.isEmpty(request.getPortList())) {
      request
          .getPortList()
          .forEach(
              requestPort -> {
                PortRotationDetail.Builder builder = PortRotationDetail.newBuilder();
                builder.setId(requestPort.getId());
                builder.setLoadableStudyId(request.getLoadableStudyId());
                Optional.ofNullable(requestPort.getOperationId())
                    .ifPresent(builder::setOperationId);
                Optional.ofNullable(requestPort.getBerthId()).ifPresent(builder::setBerthId);
                Optional.ofNullable(requestPort.getDistanceBetweenPorts())
                    .ifPresent(
                        item ->
                            builder.setDistanceBetweenPorts(
                                valueOf(requestPort.getDistanceBetweenPorts())));
                Optional.ofNullable(requestPort.getEta())
                    .ifPresent(item -> builder.setEta(valueOf(requestPort.getEta())));
                Optional.ofNullable(requestPort.getEtd())
                    .ifPresent(item -> builder.setEtd(valueOf(requestPort.getEtd())));
                Optional.ofNullable(requestPort.getLayCanFrom())
                    .ifPresent(item -> builder.setLayCanFrom(valueOf(requestPort.getLayCanFrom())));
                Optional.ofNullable(requestPort.getLayCanTo())
                    .ifPresent(item -> builder.setLayCanTo(valueOf(requestPort.getLayCanTo())));
                Optional.ofNullable(requestPort.getMaxAirDraft())
                    .ifPresent(
                        item -> builder.setMaxAirDraft(valueOf(requestPort.getMaxAirDraft())));
                Optional.ofNullable(requestPort.getMaxDraft())
                    .ifPresent(item -> builder.setMaxDraft(valueOf(requestPort.getMaxDraft())));
                Optional.ofNullable(requestPort.getPortId())
                    .ifPresent(item -> builder.setPortId(requestPort.getPortId()));
                Optional.ofNullable(requestPort.getSeaWaterDensity())
                    .ifPresent(
                        item ->
                            builder.setSeaWaterDensity(valueOf(requestPort.getSeaWaterDensity())));
                Optional.ofNullable(requestPort.getTimeOfStay())
                    .ifPresent(item -> builder.setTimeOfStay(valueOf(requestPort.getTimeOfStay())));
                Optional.ofNullable(requestPort.getPortOrder()).ifPresent(builder::setPortOrder);
                Optional.ofNullable(requestPort.getEtaActual())
                    .ifPresent(item -> builder.setEtaActual(valueOf(requestPort.getEtaActual())));
                Optional.ofNullable(requestPort.getEtdActual())
                    .ifPresent(item -> builder.setEtdActual(valueOf(requestPort.getEtdActual())));
                portListBuilder.addPortRotationDetails(builder);
              });
    }
    return portListBuilder.build();
  }

  /**
   * Call loadable study service to save port rotation
   *
   * @param request {@link PortRotationDetail}
   * @return
   */
  public PortRotationReply savePortRotation(PortRotationDetail request) {
    return this.loadableStudyServiceBlockingStub.saveLoadableStudyPortRotation(request);
  }

  /**
   * Create Port rotation grpc request object
   *
   * @param request
   * @return
   */
  private PortRotationDetail createPortRotationDetail(PortRotation request) {
    PortRotationDetail.Builder builder = PortRotationDetail.newBuilder();
    builder.setId(request.getId());
    builder.setLoadableStudyId(request.getLoadableStudyId());
    Optional.ofNullable(request.getOperationId()).ifPresent(builder::setOperationId);
    Optional.ofNullable(request.getBerthId()).ifPresent(builder::setBerthId);
    Optional.ofNullable(request.getDistanceBetweenPorts())
        .ifPresent(
            item -> builder.setDistanceBetweenPorts(valueOf(request.getDistanceBetweenPorts())));
    Optional.ofNullable(request.getEta())
        .ifPresent(item -> builder.setEta(valueOf(request.getEta())));
    Optional.ofNullable(request.getEtd())
        .ifPresent(item -> builder.setEtd(valueOf(request.getEtd())));
    Optional.ofNullable(request.getLayCanFrom())
        .ifPresent(item -> builder.setLayCanFrom(valueOf(request.getLayCanFrom())));
    Optional.ofNullable(request.getLayCanTo())
        .ifPresent(item -> builder.setLayCanTo(valueOf(request.getLayCanTo())));
    Optional.ofNullable(request.getMaxAirDraft())
        .ifPresent(item -> builder.setMaxAirDraft(valueOf(request.getMaxAirDraft())));
    Optional.ofNullable(request.getMaxDraft())
        .ifPresent(item -> builder.setMaxDraft(valueOf(request.getMaxDraft())));
    Optional.ofNullable(request.getPortId())
        .ifPresent(item -> builder.setPortId(request.getPortId()));
    Optional.ofNullable(request.getSeaWaterDensity())
        .ifPresent(item -> builder.setSeaWaterDensity(valueOf(request.getSeaWaterDensity())));
    Optional.ofNullable(request.getTimeOfStay())
        .ifPresent(item -> builder.setTimeOfStay(valueOf(request.getTimeOfStay())));
    Optional.ofNullable(request.getPortOrder()).ifPresent(builder::setPortOrder);
    Optional.ofNullable(request.getEtaActual())
        .ifPresent(item -> builder.setEtaActual(valueOf(request.getEtaActual())));
    Optional.ofNullable(request.getEtdActual())
        .ifPresent(item -> builder.setEtdActual(valueOf(request.getEtdActual())));
    return builder.build();
  }

  /**
   * @param loadableQuantityId
   * @param correlationId
   * @return
   * @throws GenericServiceException LoadableQuantityResponse
   */
  public LoadableQuantityResponse getLoadableQuantity(long loadableStudyId, String correlationId)
      throws GenericServiceException {
    LoadableQuantityResponse loadableQuantityResponseDto = new LoadableQuantityResponse();
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    LoadableQuantityReply loadableQuantityRequest =
        LoadableQuantityReply.newBuilder().setLoadableStudyId(loadableStudyId).build();
    com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse loadableQuantityResponse =
        this.getLoadableQuantityResponse(loadableQuantityRequest);
    if (!SUCCESS.equalsIgnoreCase(loadableQuantityResponse.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          loadableQuantityResponse.getResponseStatus().getMessage(),
          loadableQuantityResponse.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(loadableQuantityResponse.getResponseStatus().getCode())));
    }

    loadableQuantity.setConstant(
        loadableQuantityResponse.getLoadableQuantityRequest().getConstant());
    loadableQuantity.setDwt(loadableQuantityResponse.getLoadableQuantityRequest().getDwt());
    loadableQuantity.setDisplacmentDraftRestriction(
        loadableQuantityResponse.getLoadableQuantityRequest().getDisplacmentDraftRestriction());
    loadableQuantity.setDistanceFromLastPort(
        loadableQuantityResponse.getLoadableQuantityRequest().getDistanceFromLastPort());
    loadableQuantity.setEstDOOnBoard(
        loadableQuantityResponse.getLoadableQuantityRequest().getEstDOOnBoard());
    loadableQuantity.setEstFOOnBoard(
        loadableQuantityResponse.getLoadableQuantityRequest().getEstFOOnBoard());
    loadableQuantity.setEstFreshWaterOnBoard(
        loadableQuantityResponse.getLoadableQuantityRequest().getEstFreshWaterOnBoard());
    loadableQuantity.setEstSagging(
        loadableQuantityResponse.getLoadableQuantityRequest().getEstSagging());
    loadableQuantity.setEstSeaDensity(
        loadableQuantityResponse.getLoadableQuantityRequest().getEstSeaDensity());

    loadableQuantity.setFoConsumptionPerDay(
        loadableQuantityResponse.getLoadableQuantityRequest().getFoConsumptionPerDay());

    loadableQuantity.setVesselLightWeight(
        loadableQuantityResponse.getLoadableQuantityRequest().getVesselLightWeight());
    loadableQuantity.setOtherIfAny(
        loadableQuantityResponse.getLoadableQuantityRequest().getOtherIfAny());
    loadableQuantity.setSaggingDeduction(
        loadableQuantityResponse.getLoadableQuantityRequest().getSaggingDeduction());
    loadableQuantity.setSgCorrection(
        loadableQuantityResponse.getLoadableQuantityRequest().getSgCorrection());
    loadableQuantity.setTotalQuantity(
        loadableQuantityResponse.getLoadableQuantityRequest().getTotalQuantity());
    loadableQuantity.setTpc(loadableQuantityResponse.getLoadableQuantityRequest().getTpc());
    loadableQuantity.setVesselAverageSpeed(
        loadableQuantityResponse.getLoadableQuantityRequest().getVesselAverageSpeed());
    loadableQuantity.setUpdateDateAndTime(
        loadableQuantityResponse.getLoadableQuantityRequest().getUpdateDateAndTime());
    loadableQuantity.setPortId(
        Integer.parseInt(
            String.valueOf(loadableQuantityResponse.getLoadableQuantityRequest().getPortId())));
    loadableQuantity.setBoilerWaterOnBoard(
        loadableQuantityResponse.getLoadableQuantityRequest().getBoilerWaterOnBoard());
    loadableQuantity.setBallast(loadableQuantityResponse.getLoadableQuantityRequest().getBallast());
    loadableQuantity.setRunningHours(
        loadableQuantityResponse.getLoadableQuantityRequest().getRunningHours());
    loadableQuantity.setRunningDays(
        loadableQuantityResponse.getLoadableQuantityRequest().getRunningDays());
    loadableQuantity.setFoConInSZ(
        loadableQuantityResponse.getLoadableQuantityRequest().getFoConInSZ());
    loadableQuantity.setDraftRestriction(
        loadableQuantityResponse.getLoadableQuantityRequest().getDraftRestriction());
    loadableQuantity.setSubTotal(
        loadableQuantityResponse.getLoadableQuantityRequest().getSubTotal());
    loadableQuantity.setDwt(loadableQuantityResponse.getLoadableQuantityRequest().getDwt());
    loadableQuantityResponseDto.setLoadableQuantity(loadableQuantity);
    loadableQuantityResponseDto.setSelectedZone(loadableQuantityResponse.getSelectedZone());
    loadableQuantityResponseDto.setCaseNo(loadableQuantityResponse.getCaseNo());
    loadableQuantityResponseDto.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));

    return loadableQuantityResponseDto;
  }

  /**
   * @param loadableQuantityId
   * @return LoadableQuantityReply
   */
  public com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse
      getLoadableQuantityResponse(LoadableQuantityReply loadableQuantityRequest) {
    return loadableStudyServiceBlockingStub.getLoadableQuantity(loadableQuantityRequest);
  }

  /**
   * Get voyage list by vessel
   *
   * @param vesselId
   * @param first
   * @return
   * @throws GenericServiceException
   */
  public VoyageResponse getVoyageListByVessel(Long vesselId, String correlationId)
      throws GenericServiceException {
    VoyageRequest request = VoyageRequest.newBuilder().setVesselId(vesselId).build();
    VoyageListReply grpcReply = this.getVoyageListByVessel(request);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to fetch voyage list",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    VoyageResponse response = new VoyageResponse();
    response.setVoyages(new ArrayList<>());
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    for (VoyageDetail detail : grpcReply.getVoyagesList()) {
      Voyage voyage = new Voyage();
      voyage.setId(detail.getId());
      voyage.setVoyageNo(detail.getVoyageNumber());
      voyage.setStartDate(detail.getStartDate());
      voyage.setEndDate(detail.getEndDate());
      voyage.setStatus(detail.getStatus());
      voyage.setConfirmedLoadableStudyId(
          detail.getConfirmedLoadableStudyId() != 0 ? detail.getConfirmedLoadableStudyId() : null);
      response.getVoyages().add(voyage);
    }
    return response;
  }

  /**
   * Call grpc service to fetch list of voyages by vessel
   *
   * @param request
   * @return
   */
  public VoyageListReply getVoyageListByVessel(VoyageRequest request) {
    return this.loadableStudyServiceBlockingStub.getVoyagesByVessel(request);
  }

  /**
   * Delete cargo nomination details using loadable-study service
   *
   * @param loadableStudyId
   * @param headers
   * @return
   * @throws GenericServiceException
   */
  public CargoNominationResponse deleteCargoNomination(Long cargoNominationId, HttpHeaders headers)
      throws GenericServiceException {
    CargoNominationResponse cargoNominationResponse = new CargoNominationResponse();
    // Build response with response status
    buildCargoNominationResponseWithResponseStatus(cargoNominationResponse);
    // Build cargoNomination payload for grpc call
    com.cpdss.common.generated.LoadableStudy.CargoNominationRequest.Builder builder =
        CargoNominationRequest.newBuilder();
    Optional.ofNullable(cargoNominationId).ifPresent(builder::setCargoNominationId);
    CargoNominationRequest cargoNominationRequest = builder.build();
    CargoNominationReply cargoNominationReply =
        loadableStudyServiceBlockingStub.deleteCargoNomination(cargoNominationRequest);
    if (cargoNominationReply != null
        && cargoNominationReply.getResponseStatus() != null
        && !SUCCESS.equalsIgnoreCase(cargoNominationReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling deleteCargoNomination",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return cargoNominationResponse;
  }

  /**
   * Save discharging ports for a loadable study
   *
   * @param request {@link DischargingPortRequest}
   * @param correlationId
   * @return {@link PortRotationResponse}
   * @throws GenericServiceException
   */
  public PortRotationResponse saveDischargingPorts(
      DischargingPortRequest request, String correlationId) throws GenericServiceException {
    log.info("Inside savePortRotation");
    PortRotationRequest grpcRequest =
        PortRotationRequest.newBuilder()
            .addAllDischargingPortIds(request.getPortIds())
            .setLoadableStudyId(request.getLoadableStudyId())
            .setDischargingCargoId(request.getDischargingCargoId())
            .build();
    PortRotationReply grpcReply = this.saveDischargingPorts(grpcRequest);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to save discharging ports",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    PortRotationResponse response = new PortRotationResponse();
    response.setResponseStatus(
        new CommonSuccessResponse(valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Call loadble study micro service to save discharging ports
   *
   * @param grpcRequest {@link PortRotationRequest}
   * @return
   */
  public PortRotationReply saveDischargingPorts(PortRotationRequest grpcRequest) {
    return this.loadableStudyServiceBlockingStub.saveDischargingPorts(grpcRequest);
  }

  /**
   * Delete loadable study by id
   *
   * @param loadableStudyId - The primary key for loadable study
   * @param correlationId
   * @return
   * @throws NumberFormatException
   * @throws GenericServiceException
   */
  public LoadableStudyResponse deleteLoadableStudy(
      final Long loadableStudyId, final String correlationId) throws GenericServiceException {
    LoadableStudyRequest request =
        LoadableStudyRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
    LoadableStudyReply grpcReply = this.deleteLoadableStudy(request);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to delete loadable study",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    LoadableStudyResponse response = new LoadableStudyResponse();
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatusCode.OK.value()), correlationId));
    return response;
  }

  /**
   * Call grpc service
   *
   * @param request
   * @return
   */
  public LoadableStudyReply deleteLoadableStudy(LoadableStudyRequest request) {
    return this.loadableStudyServiceBlockingStub.deleteLoadableStudy(request);
  }

  /**
   * @param loadableStudyId
   * @param first
   * @return PortRotationResponse
   */
  public PortRotationResponse getPortRotation(
      @Min(value = 1, message = "400") Long loadableStudyId, String correlationId)
      throws GenericServiceException {
    log.info("Inside getPortRotation gateway service with correlationId : " + correlationId);
    PortRotationRequest portRotationRequest =
        PortRotationRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
    PortRotationReply portRotationReply = this.getPortRotation(portRotationRequest);
    if (!SUCCESS.equals(portRotationReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get Port Rotation",
          portRotationReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(portRotationReply.getResponseStatus().getCode())));
    }
    PortRotationResponse response = new PortRotationResponse();
    response.setPortList(new ArrayList<>());
    portRotationReply
        .getPortsList()
        .forEach(
            portList -> {
              PortRotation portRotation = new PortRotation();
              portRotation.setPortId(portList.getPortId());
              response.getPortList().add(portRotation);
            });
    response.setResponseStatus(
        new CommonSuccessResponse(valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * @param portRotationRequest
   * @return PortRotationReply
   */
  public PortRotationReply getPortRotation(PortRotationRequest portRotationRequest) {
    return this.loadableStudyServiceBlockingStub.getPortRotationByLoadableStudyId(
        portRotationRequest);
  }

  /**
   * Delete port rotation by id
   *
   * @param id
   * @param id
   * @param first
   * @return
   * @throws GenericServiceException
   */
  public PortRotationResponse deletePortRotation(
      Long loadableStudyId, Long id, String correlationId) throws GenericServiceException {
    PortRotationRequest request =
        PortRotationRequest.newBuilder().setId(id).setLoadableStudyId(loadableStudyId).build();
    PortRotationReply grpcReply = this.deletePortRotation(request);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to delete port rotation",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    PortRotationResponse response = new PortRotationResponse();
    response.setResponseStatus(
        new CommonSuccessResponse(valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Call grpc service to delete port rotation
   *
   * @param request
   * @return
   */
  public PortRotationReply deletePortRotation(PortRotationRequest request) {
    return this.loadableStudyServiceBlockingStub.deletePortRotation(request);
  }

  /**
   * @param loadableStudiesId
   * @param first
   * @return LoadablePatternResponse
   */
  public LoadablePatternResponse getLoadablePatterns(Long loadableStudiesId, String correlationId)
      throws GenericServiceException {
    LoadablePatternRequest loadablePatternRequest =
        LoadablePatternRequest.newBuilder().setLoadableStudyId(loadableStudiesId).build();
    LoadablePatternReply loadablePatternReply = this.getLoadablePattern(loadablePatternRequest);
    if (!SUCCESS.equals(loadablePatternReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get LoadablePattern ",
          loadablePatternReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(loadablePatternReply.getResponseStatus().getCode())));
    }
    return this.buildLoadablePatternResponse(loadablePatternReply, correlationId);
  }

  /**
   * @param loadablePatternReply
   * @param correlationId
   * @return LoadablePatternResponse
   */
  private LoadablePatternResponse buildLoadablePatternResponse(
      LoadablePatternReply loadablePatternReply, String correlationId) {
    LoadablePatternResponse loadablePatternResponse = new LoadablePatternResponse();
    loadablePatternResponse.setLoadableStudyName(loadablePatternReply.getLoadableStudyName());
    loadablePatternResponse.setLoadablePatternCreatedDate(
        loadablePatternReply.getLoadablePatternCreatedDate());
    loadablePatternResponse.setLoadablePatterns(new ArrayList<LoadablePattern>());
    loadablePatternResponse.setTankLists(
        createGroupWiseTankList(loadablePatternReply.getTanksList()));
    loadablePatternReply
        .getLoadablePatternList()
        .forEach(
            loadablePattern -> {
              LoadablePattern loadablePatternDto = new LoadablePattern();
              loadablePatternDto.setLoadablePatternId(loadablePattern.getLoadablePatternId());
              loadablePatternDto.setConstraints(loadablePattern.getConstraints());
              loadablePatternDto.setTotalDifferenceColor(loadablePattern.getTotalDifferenceColor());
              loadablePatternDto.setLoadableStudyStatusId(
                  loadablePattern.getLoadableStudyStatusId());
              loadablePatternDto.setLoadablePatternCargoDetails(
                  new ArrayList<LoadablePatternCargoDetails>());
              loadablePattern
                  .getLoadablePatternCargoDetailsList()
                  .forEach(
                      loadablePatternCargoDetail -> {
                        LoadablePatternCargoDetails loadablePatternCargoDetails =
                            new LoadablePatternCargoDetails();
                        Optional.ofNullable(loadablePatternCargoDetail.getPriority())
                            .ifPresent(
                                priority -> loadablePatternCargoDetails.setPriority(priority));
                        Optional.ofNullable(loadablePatternCargoDetail.getQuantity())
                            .ifPresent(
                                quantity ->
                                    loadablePatternCargoDetails.setQuantity(
                                        String.valueOf(quantity)));
                        Optional.ofNullable(loadablePatternCargoDetail.getTankId())
                            .ifPresent(tankId -> loadablePatternCargoDetails.setTankId(tankId));
                        Optional.ofNullable(loadablePatternCargoDetail.getCargoAbbreviation())
                            .ifPresent(
                                cargoAbbreviation ->
                                    loadablePatternCargoDetails.setCargoAbbreviation(
                                        cargoAbbreviation));
                        Optional.ofNullable(loadablePatternCargoDetail.getCargoColor())
                            .ifPresent(
                                cargoColor ->
                                    loadablePatternCargoDetails.setCargoColor(cargoColor));
                        Optional.ofNullable(loadablePatternCargoDetail.getDifference())
                            .ifPresent(
                                difference ->
                                    loadablePatternCargoDetails.setDifference(
                                        String.valueOf(difference)));

                        Optional.ofNullable(loadablePatternCargoDetail.getDifferenceColor())
                            .ifPresent(
                                differenceColor ->
                                    loadablePatternCargoDetails.setDifferenceColor(
                                        differenceColor));
                        Optional.ofNullable(
                                loadablePatternCargoDetail.getLoadablePatternDetailsId())
                            .ifPresent(
                                loadablePatternDetailsId ->
                                    loadablePatternCargoDetails.setLoadablePatternDetailsId(
                                        loadablePatternDetailsId));
                        Optional.ofNullable(loadablePatternCargoDetail.getIsCommingle())
                            .ifPresent(
                                commingle -> loadablePatternCargoDetails.setIsCommingle(commingle));
                        Optional.ofNullable(
                                loadablePatternCargoDetail.getLoadablePatternCommingleDetailsId())
                            .ifPresent(
                                id ->
                                    loadablePatternCargoDetails
                                        .setLoadablePatternCommingleDetailsId(id));
                        loadablePatternDto
                            .getLoadablePatternCargoDetails()
                            .add(loadablePatternCargoDetails);
                      });
              loadablePatternResponse.getLoadablePatterns().add(loadablePatternDto);
            });
    loadablePatternResponse.setResponseStatus(
        new CommonSuccessResponse(valueOf(HttpStatus.OK.value()), correlationId));
    return loadablePatternResponse;
  }

  /**
   * @param loadablePatternRequest
   * @return LoadablePatternReply
   */
  public LoadablePatternReply getLoadablePattern(LoadablePatternRequest loadablePatternRequest) {
    return this.loadableStudyServiceBlockingStub.getLoadablePatternDetails(loadablePatternRequest);
  }

  /**
   * Get on hand quantity details
   *
   * @param companyId
   * @param vesselId
   * @param loadableStudyId
   * @param portId
   * @return
   * @throws GenericServiceException
   */
  public OnHandQuantityResponse getOnHandQuantity(
      final Long companyId,
      final Long vesselId,
      final Long loadableStudyId,
      final Long portId,
      String correlationId)
      throws GenericServiceException {
    OnHandQuantityRequest request =
        OnHandQuantityRequest.newBuilder()
            .setCompanyId(companyId)
            .setVesselId(vesselId)
            .setLoadableStudyId(loadableStudyId)
            .setPortId(portId)
            .build();
    OnHandQuantityReply grpcReply = this.getOnHandQuantity(request);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch on hand quantities",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    return this.buildOnHandQuantityResponse(grpcReply, correlationId);
  }

  /**
   * Build on hand quantity response
   *
   * @param grpcReply
   * @return
   */
  private OnHandQuantityResponse buildOnHandQuantityResponse(
      OnHandQuantityReply grpcReply, String correlationId) {
    OnHandQuantityResponse response = new OnHandQuantityResponse();
    response.setOnHandQuantities(new ArrayList<>());
    for (OnHandQuantityDetail detail : grpcReply.getOnHandQuantityList()) {
      OnHandQuantity onHandQuantity = new OnHandQuantity();
      onHandQuantity.setId(detail.getId());
      onHandQuantity.setTankId(detail.getTankId());
      onHandQuantity.setTankName(detail.getTankName());
      onHandQuantity.setFuelTypeId(detail.getFuelTypeId());
      onHandQuantity.setFuelTypeName(detail.getFuelType());
      onHandQuantity.setFuelTypeShortName(detail.getFuelTypeShortName());
      onHandQuantity.setArrivalQuantity(
          isEmpty(detail.getArrivalQuantity())
              ? BigDecimal.ZERO
              : new BigDecimal(detail.getArrivalQuantity()));
      onHandQuantity.setArrivalVolume(
          isEmpty(detail.getArrivalVolume())
              ? BigDecimal.ZERO
              : new BigDecimal(detail.getArrivalVolume()));
      onHandQuantity.setDepartureQuantity(
          isEmpty(detail.getDepartureQuantity())
              ? BigDecimal.ZERO
              : new BigDecimal(detail.getDepartureQuantity()));
      onHandQuantity.setDepartureVolume(
          isEmpty(detail.getDepartureVolume())
              ? BigDecimal.ZERO
              : new BigDecimal(detail.getDepartureVolume()));
      onHandQuantity.setColorCode(isEmpty(detail.getColorCode()) ? null : detail.getColorCode());
      response.getOnHandQuantities().add(onHandQuantity);
    }
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return this.buildOhqTankLayoutArray(response, grpcReply);
  }

  /**
   * Build tank layout array
   *
   * @param response
   * @param grpcReply
   * @return
   */
  private OnHandQuantityResponse buildOhqTankLayoutArray(
      OnHandQuantityResponse response, OnHandQuantityReply grpcReply) {
    response.setTanks(this.createGroupWiseTankList(grpcReply.getTanksList()));
    response.setRearTanks(this.createGroupWiseTankList(grpcReply.getRearTanksList()));
    return response;
  }

  /**
   * Group tanks based on tank group parameter
   *
   * @param tankList
   * @return
   */
  private List<List<VesselTank>> createGroupWiseTankList(List<TankList> tankList) {
    List<List<VesselTank>> tanks = new ArrayList<>();
    for (TankList list : tankList) {
      List<VesselTank> tankGroup = new ArrayList<>();
      for (TankDetail detail : list.getVesselTankList()) {
        VesselTank tank = new VesselTank();
        tank.setId(detail.getTankId());
        tank.setCategoryId(detail.getTankCategoryId());
        tank.setFrameNumberFrom(detail.getFrameNumberFrom());
        tank.setFrameNumberTo(detail.getFrameNumberTo());
        tank.setShortName(detail.getShortName());
        tank.setCategoryName(detail.getTankCategoryName());
        tank.setName(detail.getTankName());
        tank.setDensity(isEmpty(detail.getDensity()) ? null : new BigDecimal(detail.getDensity()));
        tank.setFillCapcityCubm(
            isEmpty(detail.getFillCapacityCubm())
                ? null
                : new BigDecimal(detail.getFillCapacityCubm()));
        tank.setFullCapacityCubm(
            isEmpty(detail.getFullCapacityCubm()) ? null : detail.getFullCapacityCubm());
        tank.setSlopTank(detail.getIsSlopTank());
        tank.setGroup(detail.getTankGroup());
        tank.setOrder(detail.getTankOrder());
        tank.setHeightFrom(detail.getHeightFrom());
        tank.setHeightTo(detail.getHeightTo());
        tankGroup.add(tank);
      }
      tanks.add(tankGroup);
    }
    return tanks;
  }

  /**
   * Call micro service over grpc
   *
   * @param request
   * @return
   */
  public OnHandQuantityReply getOnHandQuantity(OnHandQuantityRequest request) {
    return this.loadableStudyServiceBlockingStub.getOnHandQuantity(request);
  }

  /**
   * Save on hand quantity
   *
   * @param request
   * @param correlationId
   * @return
   * @throws GenericServiceException
   * @throws NumberFormatException
   */
  public OnHandQuantityResponse saveOnHandQuantity(OnHandQuantity request, String correlationId)
      throws GenericServiceException {
    OnHandQuantityResponse response = new OnHandQuantityResponse();
    OnHandQuantityDetail.Builder builder = OnHandQuantityDetail.newBuilder();
    builder.setId(request.getId());
    builder.setLoadableStudyId(request.getLoadableStudyId());
    builder.setPortId(request.getPortId());
    builder.setTankId(request.getTankId());
    builder.setFuelTypeId(request.getFuelTypeId());
    Optional.ofNullable(request.getArrivalQuantity())
        .ifPresent(item -> builder.setArrivalQuantity(valueOf(item)));
    Optional.ofNullable(request.getArrivalVolume())
        .ifPresent(item -> builder.setArrivalVolume(valueOf(item)));
    Optional.ofNullable(request.getDepartureQuantity())
        .ifPresent(item -> builder.setDepartureQuantity(valueOf(item)));
    Optional.ofNullable(request.getDepartureVolume())
        .ifPresent(item -> builder.setDepartureVolume(valueOf(item)));
    OnHandQuantityReply grpcReply = this.saveOnHandQuantity(builder.build());
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to save on hand quantities",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    response.setId(grpcReply.getId());
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Call grpc service to save on hand quantity
   *
   * @param request
   * @return
   */
  public OnHandQuantityReply saveOnHandQuantity(OnHandQuantityDetail request) {
    return this.loadableStudyServiceBlockingStub.saveOnHandQuantity(request);
  }

  /**
   * Retrieves the commingle cargo information along with vessel cargo tanks lookup array
   *
   * @param loadableStudyId
   * @param headers
   * @return
   * @throws GenericServiceException
   */
  public CommingleCargoResponse getCommingleCargo(Long loadableStudyId, Long vesselId)
      throws GenericServiceException {
    CommingleCargoResponse commingleCargoResponse = new CommingleCargoResponse();
    // Build response with response status
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    commingleCargoResponse.setResponseStatus(commonSuccessResponse);
    // Retrieve cargos nominated for the loadable study
    CargoNominationRequest cargoNominationRequest =
        CargoNominationRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
    CargoNominationReply cargoNominationReply =
        loadableStudyServiceBlockingStub.getCargoNominationById(cargoNominationRequest);
    if (SUCCESS.equalsIgnoreCase(cargoNominationReply.getResponseStatus().getStatus())) {
      buildCommingleCargoResponseWithCargos(commingleCargoResponse, cargoNominationReply);
    } else {
      throw new GenericServiceException(
          "Error calling getCargoNominationById service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    // Retrieve commingle cargo information from commingle cargo table
    CommingleCargoRequest commingleCargoRequest =
        CommingleCargoRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
    CommingleCargoReply commingleCargoReply =
        loadableStudyServiceBlockingStub.getCommingleCargo(commingleCargoRequest);
    if (SUCCESS.equalsIgnoreCase(commingleCargoReply.getResponseStatus().getStatus())) {
      buildCommingleCargoResponse(commingleCargoResponse, commingleCargoReply);
    } else {
      throw new GenericServiceException(
          "Error calling getCommingleCargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    // Retrieve vessel info preferred tanks lookup
    VesselRequest vesselRequest = VesselRequest.newBuilder().setVesselId(vesselId).build();
    VesselReply vesselReply = vesselInfoGrpcService.getVesselCargoTanks(vesselRequest);
    if (SUCCESS.equalsIgnoreCase(vesselReply.getResponseStatus().getStatus())) {
      buildCommingleCargoResponseWithVesselTanks(commingleCargoResponse, vesselReply);
    } else {
      throw new GenericServiceException(
          "Error calling getVesselCargoTanks service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    // Retrieve purpose of commingle
    PurposeOfCommingleRequest purposeOfCommingleRequest =
        PurposeOfCommingleRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
    PurposeOfCommingleReply purposeOfCommingleReply =
        loadableStudyServiceBlockingStub.getPurposeOfCommingle(purposeOfCommingleRequest);
    if (SUCCESS.equalsIgnoreCase(purposeOfCommingleReply.getResponseStatus().getStatus())) {
      buildCommingleCargoResponseWithPurposeOfCommingle(
          commingleCargoResponse, purposeOfCommingleReply);
    } else {
      throw new GenericServiceException(
          "Error calling getPurposeOfCommingle service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return commingleCargoResponse;
  }

  private void buildCommingleCargoResponse(
      CommingleCargoResponse commingleCargoResponse, CommingleCargoReply commingleCargoReply) {
    if (commingleCargoReply != null && !commingleCargoReply.getCommingleCargoList().isEmpty()) {
      CommingleCargo commingleCargo = new CommingleCargo();
      List<CargoGroup> cargoGroups = new ArrayList<>();
      commingleCargoReply
          .getCommingleCargoList()
          .forEach(
              commingleCargoGen -> {
                commingleCargo.setPurposeId(commingleCargoGen.getPurposeId());
                commingleCargo.setSlopOnly(commingleCargoGen.getSlopOnly());
                commingleCargo.setPreferredTanks(commingleCargoGen.getPreferredTanksList());
                CargoGroup cargoGroup = new CargoGroup();
                cargoGroup.setId(commingleCargoGen.getId());
                cargoGroup.setCargo1Id(commingleCargoGen.getCargo1Id());
                cargoGroup.setCargo1pct(
                    !StringUtils.isEmpty(commingleCargoGen.getCargo1Pct())
                        ? new BigDecimal(commingleCargoGen.getCargo1Pct())
                        : new BigDecimal("0"));
                cargoGroup.setCargo2Id(commingleCargoGen.getCargo2Id());
                cargoGroup.setCargo2pct(
                    !StringUtils.isEmpty(commingleCargoGen.getCargo2Pct())
                        ? new BigDecimal(commingleCargoGen.getCargo2Pct())
                        : new BigDecimal("0"));
                cargoGroup.setQuantity(
                    !StringUtils.isEmpty(commingleCargoGen.getQuantity())
                        ? new BigDecimal(commingleCargoGen.getQuantity())
                        : new BigDecimal("0"));
                cargoGroups.add(cargoGroup);
              });
      commingleCargo.setCargoGroups(cargoGroups);
      commingleCargoResponse.setCommingleCargo(commingleCargo);
    }
  }

  /**
   * builds commingleCargoResponse from the cargo nomination data for the specific loadable study
   *
   * @param commingleCargoResponse
   * @param reply
   * @return
   */
  private CommingleCargoResponse buildCommingleCargoResponseWithCargos(
      CommingleCargoResponse commingleCargoResponse, CargoNominationReply reply) {
    if (reply != null && !reply.getCargoNominationsList().isEmpty()) {
      List<CargoNomination> cargoNominationList = new ArrayList<>();
      List<CargoNominationDetail> cargoNominationDetailsFiltered =
          reply.getCargoNominationsList().stream()
              .filter(distinctByKey(cargoNominationDetail -> cargoNominationDetail.getCargoId()))
              .collect(Collectors.toList());
      cargoNominationDetailsFiltered.forEach(
          cargoNominationDetail -> {
            CargoNomination cargoNomination = new CargoNomination();
            cargoNomination.setId(cargoNominationDetail.getId());
            cargoNomination.setColor(cargoNominationDetail.getColor());
            cargoNomination.setCargoId(cargoNominationDetail.getCargoId());
            if (!CollectionUtils.isEmpty(cargoNominationDetail.getLoadingPortDetailsList())) {
              List<LoadingPort> loadingPortList = new ArrayList<>();
              cargoNominationDetail
                  .getLoadingPortDetailsList()
                  .forEach(
                      port -> {
                        LoadingPort loadingPort = new LoadingPort();
                        loadingPort.setId(port.getPortId());
                        loadingPort.setQuantity(
                            port.getQuantity() != null
                                ? new BigDecimal(port.getQuantity())
                                : new BigDecimal("0"));
                        loadingPortList.add(loadingPort);
                      });
              cargoNomination.setLoadingPorts(loadingPortList);
            }
            cargoNominationList.add(cargoNomination);
          });
      commingleCargoResponse.setCargoNominations(cargoNominationList);
    }
    return commingleCargoResponse;
  }

  /**
   * Function to retrieve distinct objects by an attribute of the object
   *
   * @param <T>
   * @param keyExtractor
   * @return
   */
  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  /**
   * Builds commingle response with vessel tanks
   *
   * @param commingleCargoResponse
   * @param vesselReply
   */
  private void buildCommingleCargoResponseWithVesselTanks(
      CommingleCargoResponse commingleCargoResponse, VesselReply vesselReply) {
    if (vesselReply != null && !vesselReply.getVesselTanksList().isEmpty()) {
      List<VesselTank> vesselTankList = new ArrayList<>();
      vesselReply
          .getVesselTanksList()
          .forEach(
              vesselTankDetail -> {
                VesselTank vesselTank = new VesselTank();
                vesselTank.setId(vesselTankDetail.getTankId());
                vesselTank.setName(vesselTankDetail.getTankName());
                vesselTank.setShortName(vesselTankDetail.getShortName());
                vesselTankList.add(vesselTank);
              });
      commingleCargoResponse.setVesselTanks(vesselTankList);
    }
  }

  /**
   * building commingle response with purpose of commingle
   *
   * @param commingleCargoResponse
   * @param reply
   */
  private void buildCommingleCargoResponseWithPurposeOfCommingle(
      CommingleCargoResponse commingleCargoResponse, PurposeOfCommingleReply reply) {
    if (reply != null && !reply.getPurposeOfCommingleList().isEmpty()) {
      List<Purpose> purposeList = new ArrayList<>();
      reply
          .getPurposeOfCommingleList()
          .forEach(
              purposeDetail -> {
                Purpose purpose = new Purpose();
                purpose.setId(purposeDetail.getId());
                purpose.setName(purposeDetail.getName());
                purposeList.add(purpose);
              });
      commingleCargoResponse.setPurposes(purposeList);
    }
  }

  public CommingleCargoResponse saveCommingleCargo(
      Long loadableStudyId, com.cpdss.gateway.domain.CommingleCargo commingleCargo)
      throws GenericServiceException {
    CommingleCargoResponse commingleCargoResponse = new CommingleCargoResponse();
    // Build response with response status
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    commingleCargoResponse.setResponseStatus(commonSuccessResponse);
    // Build commingle payload for grpc call
    if (commingleCargo != null) {
      com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest.Builder builder =
          buildSaveCommingleCargoRequest(loadableStudyId, commingleCargo);
      CommingleCargoRequest commingleCargoRequest = builder.build();
      CommingleCargoReply commingleCargoReply =
          loadableStudyServiceBlockingStub.saveCommingleCargo(commingleCargoRequest);
      if (commingleCargoReply != null
          && commingleCargoReply.getResponseStatus() != null
          && !SUCCESS.equalsIgnoreCase(commingleCargoReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
            "Error in saving commingle cargo",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
    }
    return commingleCargoResponse;
  }

  /**
   * build save commingle cargo request for grpc
   *
   * @param loadableStudyId
   * @param commingleCargo
   * @return
   */
  private com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest.Builder
      buildSaveCommingleCargoRequest(Long loadableStudyId, CommingleCargo commingleCargoRequest) {
    com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest.Builder builder =
        CommingleCargoRequest.newBuilder();
    if (commingleCargoRequest != null) {
      Optional.ofNullable(loadableStudyId).ifPresent(builder::setLoadableStudyId);
      // build inner commingleCargo object
      Long purposeId =
          commingleCargoRequest.getPurposeId() != null ? commingleCargoRequest.getPurposeId() : 0;
      boolean slopOnly = commingleCargoRequest.isSlopOnly();
      List<Long> preferredTanks =
          !CollectionUtils.isEmpty(commingleCargoRequest.getPreferredTanks())
              ? commingleCargoRequest.getPreferredTanks()
              : Collections.emptyList();
      if (!CollectionUtils.isEmpty(commingleCargoRequest.getCargoGroups())) {
        commingleCargoRequest
            .getCargoGroups()
            .forEach(
                cargoGroup -> {
                  com.cpdss.common.generated.LoadableStudy.CommingleCargo.Builder
                      commingleCargoBuilder =
                          com.cpdss.common.generated.LoadableStudy.CommingleCargo.newBuilder();
                  Optional.ofNullable(cargoGroup.getId()).ifPresent(commingleCargoBuilder::setId);
                  Optional.ofNullable(purposeId).ifPresent(commingleCargoBuilder::setPurposeId);
                  Optional.ofNullable(slopOnly).ifPresent(commingleCargoBuilder::setSlopOnly);
                  Optional.ofNullable(preferredTanks)
                      .ifPresent(commingleCargoBuilder::addAllPreferredTanks);
                  Optional.ofNullable(cargoGroup.getCargo1Id())
                      .ifPresent(commingleCargoBuilder::setCargo1Id);
                  Optional.ofNullable(cargoGroup.getCargo1pct())
                      .ifPresent(
                          cargo1pct ->
                              commingleCargoBuilder.setCargo1Pct(String.valueOf(cargo1pct)));
                  Optional.ofNullable(cargoGroup.getCargo2Id())
                      .ifPresent(commingleCargoBuilder::setCargo2Id);
                  Optional.ofNullable(cargoGroup.getCargo2pct())
                      .ifPresent(
                          cargo2pct ->
                              commingleCargoBuilder.setCargo2Pct(String.valueOf(cargo2pct)));
                  Optional.ofNullable(cargoGroup.getQuantity())
                      .ifPresent(
                          quantity -> commingleCargoBuilder.setQuantity(String.valueOf(quantity)));
                  builder.addCommingleCargo(commingleCargoBuilder);
                });
      }
    }
    return builder;
  }

  /**
   * @param loadablePatternDetailsId
   * @param first
   * @return LoadablePatternDetailsResponse
   */
  public LoadablePatternDetailsResponse getLoadablePatternCommingleDetails(
      Long loadablePatternCommingleDetailsId, String correlationId) throws GenericServiceException {
    LoadablePatternCommingleDetailsRequest loadablePatternCommingleDetailsRequest =
        LoadablePatternCommingleDetailsRequest.newBuilder()
            .setLoadablePatternCommingleDetailsId(loadablePatternCommingleDetailsId)
            .build();
    LoadablePatternCommingleDetailsReply loadablePatternCommingleDetailsReply =
        this.getLoadablePatternCommingleDetails(loadablePatternCommingleDetailsRequest);
    if (!SUCCESS.equals(loadablePatternCommingleDetailsReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get Loadable Pattern Commingle Details ",
          loadablePatternCommingleDetailsReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(loadablePatternCommingleDetailsReply.getResponseStatus().getCode())));
    }
    return this.buildLoadablePatternCommingleDetailsResponse(
        loadablePatternCommingleDetailsReply, correlationId);
  }

  /**
   * @param loadablePatternCommingleDetailsReply
   * @param correlationId
   * @return LoadablePatternDetailsResponse
   */
  private LoadablePatternDetailsResponse buildLoadablePatternCommingleDetailsResponse(
      LoadablePatternCommingleDetailsReply loadablePatternCommingleDetailsReply,
      String correlationId) {
    LoadablePatternDetailsResponse loadablePatternDetailsResponse =
        new LoadablePatternDetailsResponse();
    loadablePatternDetailsResponse.setId(loadablePatternCommingleDetailsReply.getId());
    loadablePatternDetailsResponse.setApi(loadablePatternCommingleDetailsReply.getApi());
    loadablePatternDetailsResponse.setCargo1Abbrivation(
        loadablePatternCommingleDetailsReply.getCargo1Abbrivation());
    loadablePatternDetailsResponse.setCargo2Abbrivation(
        loadablePatternCommingleDetailsReply.getCargo2Abbrivation());
    loadablePatternDetailsResponse.setCargo1Percentage(
        loadablePatternCommingleDetailsReply.getCargo1Percentage());
    loadablePatternDetailsResponse.setCargo2Percentage(
        loadablePatternCommingleDetailsReply.getCargo2Percentage());
    loadablePatternDetailsResponse.setCargo1Quantity(
        loadablePatternCommingleDetailsReply.getCargo1Quantity());
    loadablePatternDetailsResponse.setCargo2Quantity(
        loadablePatternCommingleDetailsReply.getCargo2Quantity());
    loadablePatternDetailsResponse.setGrade(loadablePatternCommingleDetailsReply.getGrade());
    loadablePatternDetailsResponse.setQuantity(loadablePatternCommingleDetailsReply.getQuantity());
    loadablePatternDetailsResponse.setTankShortName(
        loadablePatternCommingleDetailsReply.getTankShortName());
    loadablePatternDetailsResponse.setTemperature(
        loadablePatternCommingleDetailsReply.getTemperature());
    loadablePatternDetailsResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return loadablePatternDetailsResponse;
  }

  /**
   * @param loadablePatternCommingleDetailsRequest
   * @return LoadablePatternCommingleDetailsReply
   */
  public LoadablePatternCommingleDetailsReply getLoadablePatternCommingleDetails(
      LoadablePatternCommingleDetailsRequest loadablePatternCommingleDetailsRequest) {

    return this.loadableStudyServiceBlockingStub.getLoadablePatternCommingleDetails(
        loadablePatternCommingleDetailsRequest);
  }

  /**
   * @param loadableStudiesId
   * @param first
   * @return Object
   */
  public AlgoPatternResponse generateLoadablePatterns(Long loadableStudyId, String correlationId)
      throws GenericServiceException {
    log.info(
        "Inside generateLoadablePatterns gateway service with correlationId : " + correlationId);
    AlgoRequest request = AlgoRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
    AlgoPatternResponse algoPatternResponse = new AlgoPatternResponse();
    AlgoReply reply = this.generateLoadablePatterns(request);
    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to call algo",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
    algoPatternResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return algoPatternResponse;
  }

  public AlgoReply generateLoadablePatterns(AlgoRequest request) {
    return this.loadableStudyServiceBlockingStub.generateLoadablePatterns(request);
  }

  /**
   * Get on board quantities
   *
   * @param vesselId
   * @param loadableStudyId
   * @param portId
   * @param portId2
   * @param first
   * @return
   * @throws GenericServiceException
   */
  public OnBoardQuantityResponse getOnBoardQuantites(
      Long vesselId, Long voyageId, Long loadableStudyId, Long portId, String correlationId)
      throws GenericServiceException {
    log.info("LoadableStudyService - getOnBoardQuantites, correlationId:{}", correlationId);
    log.debug(
        "getOnBoardQuantites, vesselId:{}, loadableStudyId:{}, portId:{}",
        vesselId,
        loadableStudyId,
        portId);
    OnBoardQuantityRequest request =
        OnBoardQuantityRequest.newBuilder()
            .setVoyageId(voyageId)
            .setLoadableStudyId(loadableStudyId)
            .setVesselId(vesselId)
            .setPortId(portId)
            .build();
    OnBoardQuantityReply grpcReply = this.getOnBoardQuantites(request);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error calling getOnBoardQuantites grpc service",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    return this.buildOnBoardQuantityResponse(grpcReply);
  }

  /**
   * Build on board quantity response
   *
   * @param grpcReply
   * @return
   */
  private OnBoardQuantityResponse buildOnBoardQuantityResponse(OnBoardQuantityReply grpcReply) {
    OnBoardQuantityResponse response = new OnBoardQuantityResponse();
    response.setOnBoardQuantities(new ArrayList<>());
    for (OnBoardQuantityDetail detail : grpcReply.getOnBoardQuantityList()) {
      OnBoardQuantity dto = new OnBoardQuantity();
      dto.setId(detail.getId());
      dto.setCargoId(0 == detail.getCargoId() ? null : detail.getCargoId());
      dto.setColorCode(isEmpty(detail.getColorCode()) ? null : detail.getColorCode());
      dto.setAbbreviation(isEmpty(detail.getAbbreviation()) ? null : detail.getAbbreviation());
      dto.setSounding(
          isEmpty(detail.getSounding()) ? BigDecimal.ZERO : new BigDecimal(detail.getSounding()));
      dto.setWeight(
          isEmpty(detail.getWeight()) ? BigDecimal.ZERO : new BigDecimal(detail.getWeight()));
      dto.setVolume(
          isEmpty(detail.getVolume()) ? BigDecimal.ZERO : new BigDecimal(detail.getVolume()));
      dto.setTankId(detail.getTankId());
      dto.setTankName(detail.getTankName());
      response.getOnBoardQuantities().add(dto);
    }
    response.setTanks(this.createGroupWiseTankList(grpcReply.getTanksList()));
    return response;
  }

  /**
   * Grpc call for fetching on board quantities
   *
   * @param request
   * @return
   */
  public OnBoardQuantityReply getOnBoardQuantites(OnBoardQuantityRequest request) {
    return this.loadableStudyServiceBlockingStub.getOnBoardQuantity(request);
  }

  /**
   * Save on board quantity
   *
   * @param request
   * @param first
   * @return
   * @throws GenericServiceException
   */
  public OnBoardQuantityResponse saveOnBoardQuantites(OnBoardQuantity request, String correlationId)
      throws GenericServiceException {
    log.info("saveOnBoardQuantites, correlationId: {}", correlationId);
    log.debug("saveOnBoardQuantites, request: {}", request);
    OnBoardQuantityResponse response = new OnBoardQuantityResponse();
    OnBoardQuantityReply grpcReply =
        this.saveOnBoardQuantites(this.buildObqRequest(request, correlationId), correlationId);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to save on board quantities",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    response.setId(grpcReply.getId());
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Build on board quantity save grpc call request
   *
   * @param request
   * @param correlationId
   * @return
   */
  private OnBoardQuantityDetail buildObqRequest(OnBoardQuantity request, String correlationId) {
    log.info("buildObqRequest, correlationId: {}", correlationId);
    OnBoardQuantityDetail.Builder builder = OnBoardQuantityDetail.newBuilder();
    builder.setId(request.getId());
    Optional.ofNullable(request.getCargoId()).ifPresent(builder::setCargoId);
    builder.setPortId(request.getPortId());
    builder.setLoadableStudyId(request.getLoadableStudyId());
    builder.setTankId(request.getTankId());
    builder.setWeight(valueOf(request.getWeight()));
    builder.setVolume(valueOf(request.getVolume()));
    Optional.ofNullable(request.getSounding())
        .ifPresent(sounding -> builder.setSounding(valueOf(request.getSounding())));
    Optional.ofNullable(request.getColorCode()).ifPresent(builder::setColorCode);
    Optional.ofNullable(request.getAbbreviation()).ifPresent(builder::setAbbreviation);
    return builder.build();
  }

  /**
   * Save on board quantity grpc service call
   *
   * @param request
   * @return
   */
  public OnBoardQuantityReply saveOnBoardQuantites(
      OnBoardQuantityDetail request, String correlationId) {
    log.info("saveOnBoardQuantites grpc call, correlationId: {}", correlationId);
    return this.loadableStudyServiceBlockingStub.saveOnBoardQuantity(request);
  }

  /**
   * @param request
   * @param correlationId
   * @return AlgoStatusResponse
   */
  public AlgoStatusResponse saveAlgoLoadableStudyStatus(
      AlgoStatusRequest request, String correlationId) throws GenericServiceException {
    log.info(
        "Inside updateLoadableStudyStatus gateway service with correlationId : " + correlationId);
    AlgoStatusResponse response = new AlgoStatusResponse();
    AlgoStatusReply grpcReply =
        this.saveAlgoLoadableStudyStatus(
            this.buildAlgoLoadableStudyStatusRequest(request, correlationId));
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to update Loadable Study Status",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * @param request
   * @param correlationId
   * @return AlgoStatusRequest
   */
  private com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest
      buildAlgoLoadableStudyStatusRequest(AlgoStatusRequest request, String correlationId) {
    com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest.Builder builder =
        com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest.newBuilder();
    builder.setProcesssId(request.getProcessId());
    builder.setLoadableStudystatusId(request.getLoadableStudyStatusId());
    return builder.build();
  }

  /**
   * @param request
   * @param correlationId
   * @return AlgoStatusRequest
   */
  public com.cpdss.common.generated.LoadableStudy.AlgoStatusReply saveAlgoLoadableStudyStatus(
      com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest request) {
    return this.loadableStudyServiceBlockingStub.saveAlgoLoadableStudyStatus(request);
  }

  /**
   * Get synoptical table information
   *
   * @param loadableStudyId
   * @param vesselId
   * @return
   * @throws GenericServiceException
   */
  public SynopticalTableResponse getSynopticalTable(Long vesselId, Long loadableStudyId)
      throws GenericServiceException {
    SynopticalTableResponse synopticalTableResponse = new SynopticalTableResponse();
    // Build response with response status
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    synopticalTableResponse.setResponseStatus(commonSuccessResponse);
    // Retrieve synoptical table for the loadable study
    SynopticalTableRequest synopticalTableRequest =
        SynopticalTableRequest.newBuilder()
            .setLoadableStudyId(loadableStudyId)
            .setVesselId(vesselId)
            .build();
    SynopticalTableReply synopticalTableReply =
        loadableStudyServiceBlockingStub.getSynopticalTable(synopticalTableRequest);
    if (SUCCESS.equalsIgnoreCase(synopticalTableReply.getResponseStatus().getStatus())) {
      buildSynopticalTableResponse(synopticalTableResponse, synopticalTableReply);
    } else {
      throw new GenericServiceException(
          "Error calling getSynopticalTable service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return synopticalTableResponse;
  }

  private void buildSynopticalTableResponse(
      SynopticalTableResponse synopticalTableResponse, SynopticalTableReply reply) {
    if (!CollectionUtils.isEmpty(reply.getSynopticalRecordsList())) {
      List<SynopticalRecord> synopticalTableList = new ArrayList<>();
      reply
          .getSynopticalRecordsList()
          .forEach(
              synopticalProtoRecord -> {
                SynopticalRecord synopticalRecord = new SynopticalRecord();
                this.buildSynopticalRecord(synopticalRecord, synopticalProtoRecord);
                this.buildSynopticalTableCargos(synopticalRecord, synopticalProtoRecord);
                this.buildOhqDataForSynopticalTable(synopticalRecord, synopticalProtoRecord);
                this.buildVesselDataForSynopticalTable(synopticalRecord, synopticalProtoRecord);
                this.buildSynopticalLoadicatorRecord(synopticalRecord, synopticalProtoRecord);
                synopticalTableList.add(synopticalRecord);
              });
      synopticalTableResponse.setSynopticalRecords(synopticalTableList);
    }
  }

  /**
   * Set loadicator data in synoptical table
   *
   * @param synopticalRecord
   * @param synopticalProtoRecord
   */
  private void buildSynopticalLoadicatorRecord(
      SynopticalRecord synopticalRecord,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord synopticalProtoRecord) {
    SynopticalTableLoadicatorData proto = synopticalProtoRecord.getLoadicatorData();
    synopticalRecord.setHogSag(
        isEmpty(proto.getHogSag()) ? null : new BigDecimal(proto.getHogSag()));
    synopticalRecord.setFinalDraftFwd(
        isEmpty(proto.getFinalDraftFwd())
            ? BigDecimal.ZERO
            : new BigDecimal(proto.getFinalDraftFwd()));
    synopticalRecord.setFinalDraftAft(
        isEmpty(proto.getFinalDraftAft())
            ? BigDecimal.ZERO
            : new BigDecimal(proto.getFinalDraftAft()));
    synopticalRecord.setFinalDraftMid(
        isEmpty(proto.getFinalDraftMid())
            ? BigDecimal.ZERO
            : new BigDecimal(proto.getFinalDraftMid()));
    synopticalRecord.setCalculatedDraftFwdActual(
        isEmpty(proto.getCalculatedDraftFwdActual())
            ? BigDecimal.ZERO
            : new BigDecimal(proto.getCalculatedDraftFwdActual()));
    synopticalRecord.setCalculatedDraftFwdPlanned(
        isEmpty(proto.getCalculatedDraftFwdPlanned())
            ? BigDecimal.ZERO
            : new BigDecimal(proto.getCalculatedDraftFwdPlanned()));

    synopticalRecord.setCalculatedDraftAftActual(
        isEmpty(proto.getCalculatedDraftAftActual())
            ? BigDecimal.ZERO
            : new BigDecimal(proto.getCalculatedDraftAftActual()));
    synopticalRecord.setCalculatedDraftAftPlanned(
        isEmpty(proto.getCalculatedDraftAftPlanned())
            ? BigDecimal.ZERO
            : new BigDecimal(proto.getCalculatedDraftAftPlanned()));

    synopticalRecord.setCalculatedDraftMidActual(
        isEmpty(proto.getCalculatedDraftMidActual())
            ? BigDecimal.ZERO
            : new BigDecimal(proto.getCalculatedDraftMidActual()));
    synopticalRecord.setCalculatedDraftMidPlanned(
        isEmpty(proto.getCalculatedDraftMidPlanned())
            ? BigDecimal.ZERO
            : new BigDecimal(proto.getCalculatedDraftMidPlanned()));

    synopticalRecord.setCalculatedTrimActual(
        isEmpty(proto.getCalculatedTrimActual())
            ? BigDecimal.ZERO
            : new BigDecimal(proto.getCalculatedTrimActual()));
    synopticalRecord.setCalculatedTrimPlanned(
        isEmpty(proto.getCalculatedTrimPlanned())
            ? BigDecimal.ZERO
            : new BigDecimal(proto.getCalculatedTrimPlanned()));

    synopticalRecord.setBlindSector(
        isEmpty(proto.getBlindSector()) ? BigDecimal.ZERO : new BigDecimal(proto.getBlindSector()));
  }

  /**
   * Build synoptical record
   *
   * @param synopticalRecord
   * @param synopticalProtoRecord
   */
  private void buildSynopticalRecord(
      SynopticalRecord synopticalRecord,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord synopticalProtoRecord) {
    synopticalRecord.setId(synopticalProtoRecord.getId());
    synopticalRecord.setPortId(synopticalProtoRecord.getPortId());
    synopticalRecord.setPortName(synopticalProtoRecord.getPortName());
    synopticalRecord.setSpecificGravity(
        !isEmpty(synopticalProtoRecord.getSpecificGravity())
            ? new BigDecimal(synopticalProtoRecord.getSpecificGravity())
            : BigDecimal.ZERO);
    synopticalRecord.setOperationType(synopticalProtoRecord.getOperationType());
    synopticalRecord.setDistance(
        !isEmpty(synopticalProtoRecord.getDistance())
            ? new BigDecimal(synopticalProtoRecord.getDistance())
            : BigDecimal.ZERO);
    synopticalRecord.setSpeed(
        !isEmpty(synopticalProtoRecord.getSpeed())
            ? new BigDecimal(synopticalProtoRecord.getSpeed())
            : BigDecimal.ZERO);
    synopticalRecord.setRunningHours(
        !isEmpty(synopticalProtoRecord.getRunningHours())
            ? new BigDecimal(synopticalProtoRecord.getRunningHours())
            : BigDecimal.ZERO);
    synopticalRecord.setInPortHours(
        !isEmpty(synopticalProtoRecord.getInPortHours())
            ? new BigDecimal(synopticalProtoRecord.getInPortHours())
            : BigDecimal.ZERO);
    synopticalRecord.setTimeOfSunrise(synopticalProtoRecord.getTimeOfSunrise());
    synopticalRecord.setTimeOfSunset(synopticalProtoRecord.getTimeOfSunset());
    synopticalRecord.setHwTideFrom(
        !isEmpty(synopticalProtoRecord.getHwTideFrom())
            ? new BigDecimal(synopticalProtoRecord.getHwTideFrom())
            : BigDecimal.ZERO);
    synopticalRecord.setHwTideTimeFrom(synopticalProtoRecord.getHwTideTimeFrom());
    synopticalRecord.setHwTideTo(
        !isEmpty(synopticalProtoRecord.getHwTideTo())
            ? new BigDecimal(synopticalProtoRecord.getHwTideTo())
            : BigDecimal.ZERO);
    synopticalRecord.setHwTideTimeTo(synopticalProtoRecord.getHwTideTimeTo());
    synopticalRecord.setLwTideFrom(
        !isEmpty(synopticalProtoRecord.getLwTideFrom())
            ? new BigDecimal(synopticalProtoRecord.getLwTideFrom())
            : BigDecimal.ZERO);
    synopticalRecord.setLwTideTimeFrom(synopticalProtoRecord.getLwTideTimeFrom());
    synopticalRecord.setLwTideTo(
        !isEmpty(synopticalProtoRecord.getLwTideTo())
            ? new BigDecimal(synopticalProtoRecord.getLwTideTo())
            : BigDecimal.ZERO);
    synopticalRecord.setLwTideTimeTo(synopticalProtoRecord.getLwTideTimeTo());
    synopticalRecord.setEtaEtdActual(synopticalProtoRecord.getEtaEtdActual());
    synopticalRecord.setEtaEtdPlanned(synopticalProtoRecord.getEtaEtdEstimated());
    synopticalRecord.setBallastPlanned(
        isEmpty(synopticalProtoRecord.getBallastPlanned())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getBallastPlanned()));
    synopticalRecord.setBallastActual(
        isEmpty(synopticalProtoRecord.getBallastActual())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getBallastActual()));
  }

  /**
   * Set vessel data in synoptical table
   *
   * @param synopticalRecord
   * @param synopticalProtoRecord
   */
  private void buildVesselDataForSynopticalTable(
      SynopticalRecord synopticalRecord,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord synopticalProtoRecord) {
    synopticalRecord.setOthersActual(
        isEmpty(synopticalProtoRecord.getOthersActual())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getOthersActual()));
    synopticalRecord.setOthersPlanned(
        isEmpty(synopticalProtoRecord.getOthersPlanned())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getOthersPlanned()));
    synopticalRecord.setConstantActual(
        isEmpty(synopticalProtoRecord.getConstantActual())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getConstantActual()));
    synopticalRecord.setConstantPlanned(
        isEmpty(synopticalProtoRecord.getConstantPlanned())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getConstantPlanned()));
    synopticalRecord.setTotalDwtActual(
        isEmpty(synopticalProtoRecord.getTotalDwtActual())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getTotalDwtActual()));
    synopticalRecord.setTotalDwtPlanned(
        isEmpty(synopticalProtoRecord.getTotalDwtPlanned())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getTotalDwtPlanned()));
    synopticalRecord.setDisplacementActual(
        isEmpty(synopticalProtoRecord.getDisplacementActual())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getDisplacementActual()));
    synopticalRecord.setDisplacementPlanned(
        isEmpty(synopticalProtoRecord.getDisplacementPlanned())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getDisplacementPlanned()));
  }

  /**
   * Set ohq data for synoptical table
   *
   * @param synopticalRecord
   * @param synopticalProtoRecord
   */
  private void buildOhqDataForSynopticalTable(
      SynopticalRecord synopticalRecord,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord synopticalProtoRecord) {
    List<SynopticalOhqRecord> foList = new ArrayList<>();
    List<SynopticalOhqRecord> doList = new ArrayList<>();
    List<SynopticalOhqRecord> fwList = new ArrayList<>();
    List<SynopticalOhqRecord> lubeList = new ArrayList<>();

    for (com.cpdss.common.generated.LoadableStudy.SynopticalOhqRecord protoRec :
        synopticalProtoRecord.getOhqList()) {
      SynopticalOhqRecord rec = new SynopticalOhqRecord();
      rec.setTankId(protoRec.getTankId());
      rec.setTankName(protoRec.getTankName());
      rec.setFuelType(protoRec.getFuelType());
      rec.setFuelTypeId(protoRec.getFuelTypeId());
      rec.setActualWeight(
          isEmpty(protoRec.getActualWeight())
              ? BigDecimal.ZERO
              : new BigDecimal(protoRec.getActualWeight()));
      rec.setPlannedWeight(
          isEmpty(protoRec.getPlannedWeight())
              ? BigDecimal.ZERO
              : new BigDecimal(protoRec.getPlannedWeight()));
      if (FUEL_OIL_TANK_CATEGORY_ID.equals(protoRec.getFuelTypeId())) {
        foList.add(rec);
      } else if (DIESEL_OIL_TANK_CATEGORY_ID.equals(protoRec.getFuelTypeId())) {
        doList.add(rec);
      } else if (FRESH_WATER_TANK_CATEGORY_ID.equals(protoRec.getFuelTypeId())) {
        fwList.add(rec);
      } else {
        lubeList.add(rec);
      }
    }
    synopticalRecord.setFoList(foList);
    synopticalRecord.setDoList(doList);
    synopticalRecord.setFwList(fwList);
    synopticalRecord.setLubeList(lubeList);

    synopticalRecord.setActualFOTotal(
        foList.stream().map(fo -> fo.getActualWeight()).reduce(BigDecimal.ZERO, BigDecimal::add));
    synopticalRecord.setActualDOTotal(
        doList.stream()
            .map(doTank -> doTank.getActualWeight())
            .reduce(BigDecimal.ZERO, BigDecimal::add));
    synopticalRecord.setActualFWTotal(
        fwList.stream().map(fw -> fw.getActualWeight()).reduce(BigDecimal.ZERO, BigDecimal::add));
    synopticalRecord.setActualLubeTotal(
        lubeList.stream()
            .map(lube -> lube.getActualWeight())
            .reduce(BigDecimal.ZERO, BigDecimal::add));

    synopticalRecord.setPlannedFOTotal(
        foList.stream().map(fo -> fo.getPlannedWeight()).reduce(BigDecimal.ZERO, BigDecimal::add));
    synopticalRecord.setPlannedDOTotal(
        doList.stream()
            .map(doTank -> doTank.getPlannedWeight())
            .reduce(BigDecimal.ZERO, BigDecimal::add));
    synopticalRecord.setPlannedFWTotal(
        fwList.stream().map(fw -> fw.getPlannedWeight()).reduce(BigDecimal.ZERO, BigDecimal::add));
    synopticalRecord.setPlannedLubeTotal(
        lubeList.stream()
            .map(lube -> lube.getPlannedWeight())
            .reduce(BigDecimal.ZERO, BigDecimal::add));
  }

  /**
   * Build cargo details
   *
   * @param synopticalRecord
   * @param synopticalProtoRecord
   * @return
   */
  private void buildSynopticalTableCargos(
      SynopticalRecord synopticalRecord,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord synopticalProtoRecord) {
    List<SynopticalCargoRecord> list = new ArrayList<>();
    for (com.cpdss.common.generated.LoadableStudy.SynopticalCargoRecord protoRec :
        synopticalProtoRecord.getCargoList()) {
      SynopticalCargoRecord rec = new SynopticalCargoRecord();
      rec.setTankId(protoRec.getTankId());
      rec.setTankName(protoRec.getTankName());
      rec.setActualWeight(
          isEmpty(protoRec.getActualWeight())
              ? BigDecimal.ZERO
              : new BigDecimal(protoRec.getActualWeight()));
      rec.setPlannedWeight(
          isEmpty(protoRec.getPlannedWeight())
              ? BigDecimal.ZERO
              : new BigDecimal(protoRec.getPlannedWeight()));
      list.add(rec);
    }
    synopticalRecord.setCargos(list);
    synopticalRecord.setCargoPlannedTotal(
        isEmpty(synopticalProtoRecord.getCargoPlannedTotal())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getCargoPlannedTotal()));
    synopticalRecord.setCargoActualTotal(
        isEmpty(synopticalProtoRecord.getCargoActualTotal())
            ? BigDecimal.ZERO
            : new BigDecimal(synopticalProtoRecord.getCargoActualTotal()));
  }

  /**
   * @param loadablePatternId
   * @param first
   * @return CommonResponse
   */
  public CommonResponse confirmPlan(Long loadablePatternId, String correlationId)
      throws GenericServiceException {
    log.info("Inside confirmPlan gateway service with correlationId : " + correlationId);
    CommonResponse response = new CommonResponse();
    ConfirmPlanRequest.Builder request = ConfirmPlanRequest.newBuilder();
    request.setLoadablePatternId(loadablePatternId);
    ConfirmPlanReply grpcReply = this.confirmPlan(request);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to confirm plan",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * @param request
   * @return ConfirmPlanReply
   */
  public ConfirmPlanReply confirmPlan(
      com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.Builder request) {
    return this.loadableStudyServiceBlockingStub.confirmPlan(request.build());
  }

  /**
   * @param loadablePatternId
   * @param loadableStudyId
   * @param first
   * @return LoadablePlanDetailsResponse
   */
  public LoadablePlanDetailsResponse getLoadablePatternDetails(
      Long loadablePatternId, Long loadableStudyId, Long vesselId, String correlationId)
      throws GenericServiceException {
    log.info(
        "Inside getLoadablePatternDetails gateway service with correlationId : " + correlationId);
    LoadablePlanDetailsResponse response = new LoadablePlanDetailsResponse();
    LoadablePlanDetailsRequest.Builder request = LoadablePlanDetailsRequest.newBuilder();
    request.setLoadablePatternId(loadablePatternId);
    LoadablePlanDetailsReply grpcReply = this.getLoadablePatternDetails(request);
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to getLoadablePatternDetails",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    buildLoadableStudyQuantity(response, grpcReply);
    buildloadableStudyCommingleCargoDetails(response, grpcReply);
    buildLoadableStudyStowageDetails(response, grpcReply);
    response.setTankLists(createGroupWiseTankList(grpcReply.getTanksList()));
    response.setFrontBallastTanks(createGroupWiseTankList(grpcReply.getBallastFrontTanksList()));
    response.setCenterBallastTanks(createGroupWiseTankList(grpcReply.getBallastCenterTanksList()));
    response.setRearBallastTanks(createGroupWiseTankList(grpcReply.getBallastRearTanksList()));
    buildLoadableStudyBallastDetails(response, grpcReply);
    buildSynopticalTableDetails(
        response, 716L, vesselId); // ToDo change loadable study to actual one
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * @param response
   * @param loadableStudyId void
   * @throws GenericServiceException
   */
  private void buildSynopticalTableDetails(
      LoadablePlanDetailsResponse response, Long loadableStudyId, Long vesselId)
      throws GenericServiceException {

    SynopticalTableResponse synopticalTableResponse = getSynopticalTable(vesselId, loadableStudyId);
    if (!synopticalTableResponse
        .getResponseStatus()
        .getStatus()
        .equals(String.valueOf(HttpStatus.OK.value()))) {
      throw new GenericServiceException(
          "Failed to get synoptical table data",
          String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
          HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
    response.setLoadablePlanSynopticalRecords(new ArrayList<LoadablePlanSynopticalRecord>());
    synopticalTableResponse
        .getSynopticalRecords()
        .forEach(
            str -> {
              LoadablePlanSynopticalRecord synopticalRecord = new LoadablePlanSynopticalRecord();
              synopticalRecord.setId(str.getId());
              synopticalRecord.setDisplacementPlanned(str.getDisplacementPlanned());
              synopticalRecord.setEtaEtdPlanned(str.getEtaEtdPlanned());
              synopticalRecord.setOperationType(str.getOperationType());
              synopticalRecord.setOthersPlanned(str.getOthersPlanned());
              synopticalRecord.setPlannedDOTotal(str.getPlannedDOTotal());
              synopticalRecord.setPlannedFOTotal(str.getPlannedFOTotal());
              synopticalRecord.setPlannedFWTotal(str.getPlannedFWTotal());
              synopticalRecord.setPortId(str.getPortId());
              synopticalRecord.setPortName(str.getPortName());
              synopticalRecord.setSpecificGravity(str.getSpecificGravity());
              synopticalRecord.setTotalDwtPlanned(str.getTotalDwtPlanned());
              synopticalRecord.setFinalDraftAft(str.getFinalDraftAft());
              synopticalRecord.setFinalDraftFwd(str.getFinalDraftFwd());
              synopticalRecord.setFinalDraftMid(str.getFinalDraftMid());
              synopticalRecord.setCalculatedTrimPlanned(str.getCalculatedTrimPlanned());
              synopticalRecord.setCargoPlannedTotal(str.getCargoPlannedTotal());
              synopticalRecord.setBallastPlanned(str.getBallastPlanned());
              response.getLoadablePlanSynopticalRecords().add(synopticalRecord);
            });
  }

  /**
   * @param response
   * @param grpcReply void
   */
  private void buildLoadableStudyBallastDetails(
      LoadablePlanDetailsResponse response, LoadablePlanDetailsReply grpcReply) {
    response.setLoadablePlanBallastDetails(new ArrayList<LoadablePlanBallastDetails>());
    grpcReply
        .getLoadablePlanBallastDetailsList()
        .forEach(
            lpbd -> {
              LoadablePlanBallastDetails details = new LoadablePlanBallastDetails();
              details.setId(lpbd.getId());
              details.setCorrectedLevel(lpbd.getCorrectedLevel());
              details.setCorrectionFactor(lpbd.getCorrectionFactor());
              details.setCubicMeter(lpbd.getCubicMeter());
              details.setInertia(lpbd.getInertia());
              details.setLcg(lpbd.getLcg());
              details.setMetricTon(lpbd.getMetricTon());
              details.setPercentage(lpbd.getPercentage());
              details.setRdgLevel(lpbd.getRdgLevel());
              details.setSg(lpbd.getSg());
              details.setTankId(lpbd.getTankId());
              details.setTcg(lpbd.getTcg());
              details.setVcg(lpbd.getVcg());
              response.getLoadablePlanBallastDetails().add(details);
            });
  }

  /**
   * @param response
   * @param grpcReply void
   */
  private void buildloadableStudyCommingleCargoDetails(
      LoadablePlanDetailsResponse response, LoadablePlanDetailsReply grpcReply) {
    response.setLoadableQuantityCommingleCargoDetails(
        new ArrayList<LoadableQuantityCommingleCargoDetails>());
    grpcReply
        .getLoadableQuantityCommingleCargoDetailsList()
        .forEach(
            lqccd -> {
              LoadableQuantityCommingleCargoDetails details =
                  new LoadableQuantityCommingleCargoDetails();
              details.setId(lqccd.getId());
              details.setApi(lqccd.getApi());
              details.setCargo1Abbreviation(lqccd.getCargo1Abbreviation());
              details.setCargo1Bbls60f(lqccd.getCargo1Bbls60F());
              details.setCargo1Bblsdbs(lqccd.getCargo1Bblsdbs());
              details.setCargo1KL(lqccd.getCargo1KL());
              details.setCargo1LT(lqccd.getCargo1LT());
              details.setCargo1MT(lqccd.getCargo1MT());
              details.setCargo1Percentage(lqccd.getCargo1Percentage());
              details.setCargo2Abbreviation(lqccd.getCargo2Abbreviation());
              details.setCargo2Bbls60f(lqccd.getCargo2Bbls60F());
              details.setCargo2Bblsdbs(lqccd.getCargo2Bblsdbs());
              details.setCargo2KL(lqccd.getCargo2KL());
              details.setCargo2LT(lqccd.getCargo2LT());
              details.setCargo2MT(lqccd.getCargo2MT());
              details.setCargo2Percentage(lqccd.getCargo2Percentage());
              details.setGrade(lqccd.getGrade());
              details.setQuantity(lqccd.getQuantity());
              details.setTankName(lqccd.getTankName());
              details.setTemp(lqccd.getTemp());
              response.getLoadableQuantityCommingleCargoDetails().add(details);
            });
  }

  /**
   * @param response
   * @param grpcReply void
   */
  private void buildLoadableStudyStowageDetails(
      LoadablePlanDetailsResponse response, LoadablePlanDetailsReply grpcReply) {
    response.setLoadablePlanStowageDetails(new ArrayList<LoadablePlanStowageDetails>());
    grpcReply
        .getLoadablePlanStowageDetailsList()
        .forEach(
            lpsd -> {
              LoadablePlanStowageDetails loadablePlanStowageDetails =
                  new LoadablePlanStowageDetails();
              loadablePlanStowageDetails.setApi(lpsd.getApi());
              loadablePlanStowageDetails.setCargoAbbreviation(lpsd.getCargoAbbreviation());
              loadablePlanStowageDetails.setCorrectedUllage(lpsd.getCorrectedUllage());
              loadablePlanStowageDetails.setCorrectionFactor(lpsd.getCorrectionFactor());
              loadablePlanStowageDetails.setFillingRatio(lpsd.getFillingRatio());
              loadablePlanStowageDetails.setObservedBarrels(lpsd.getObservedBarrels());
              loadablePlanStowageDetails.setObservedBarrelsAt60(lpsd.getObservedBarrelsAt60());
              loadablePlanStowageDetails.setObservedM3(lpsd.getObservedM3());
              loadablePlanStowageDetails.setRdgUllage(lpsd.getRdgUllage());
              loadablePlanStowageDetails.setTankId(lpsd.getTankId());
              loadablePlanStowageDetails.setTankName(lpsd.getTankName());
              loadablePlanStowageDetails.setTemperature(lpsd.getTemperature());
              loadablePlanStowageDetails.setWeight(lpsd.getWeight());
              loadablePlanStowageDetails.setId(lpsd.getId());
              response.getLoadablePlanStowageDetails().add(loadablePlanStowageDetails);
            });
  }

  /**
   * @param response
   * @param grpcReply void
   */
  private void buildLoadableStudyQuantity(
      LoadablePlanDetailsResponse response, LoadablePlanDetailsReply grpcReply) {
    response.setLoadableQuantityCargoDetails(
        new ArrayList<com.cpdss.gateway.domain.LoadableQuantityCargoDetails>());
    grpcReply
        .getLoadableQuantityCargoDetailsList()
        .forEach(
            lqcd -> {
              com.cpdss.gateway.domain.LoadableQuantityCargoDetails cargoDetails =
                  new com.cpdss.gateway.domain.LoadableQuantityCargoDetails();
              cargoDetails.setDifferenceColor(lqcd.getDifferenceColor());
              cargoDetails.setDifferencePercentage(lqcd.getDifferencePercentage());
              cargoDetails.setEstimatedAPI(lqcd.getEstimatedAPI());
              cargoDetails.setEstimatedTemp(lqcd.getEstimatedTemp());
              cargoDetails.setGrade(lqcd.getGrade());
              cargoDetails.setId(lqcd.getId());
              cargoDetails.setLoadableBbls60f(lqcd.getLoadableBbls60F());
              cargoDetails.setLoadableBblsdbs(lqcd.getLoadableBblsdbs());
              cargoDetails.setLoadableKL(lqcd.getLoadableKL());
              cargoDetails.setLoadableLT(lqcd.getLoadableLT());
              cargoDetails.setLoadableMT(lqcd.getLoadableMT());
              cargoDetails.setMaxTolerence(lqcd.getMaxTolerence());
              cargoDetails.setMinTolerence(lqcd.getMinTolerence());
              cargoDetails.setOrderBbls60f(lqcd.getOrderBbls60F());
              cargoDetails.setOrderBblsdbs(lqcd.getOrderBblsdbs());
              response.getLoadableQuantityCargoDetails().add(cargoDetails);
            });
  }

  /**
   * @param request
   * @return LoadablePlanDetailsReply
   */
  public LoadablePlanDetailsReply getLoadablePatternDetails(
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.Builder request) {
    return this.loadableStudyServiceBlockingStub.getLoadablePlanDetails(request.build());
  }

  /**
   * @param loadableStudyId
   * @param first
   * @return LoadableStudyStatusResponse
   */
  public LoadableStudyStatusResponse getLoadableStudyStatus(
      Long loadableStudyId, String correlationId) throws GenericServiceException {
    log.info("Inside getLoadableStudyStatus gateway service with correlationId : " + correlationId);
    LoadableStudyStatusResponse response = new LoadableStudyStatusResponse();
    LoadableStudyStatusReply grpcReply =
        this.getLoadableStudyStatus(
            this.buildLoadableStudyStatusRequest(loadableStudyId, correlationId));
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to get Loadable Study Status",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    response.setLoadableStudyStatusId(grpcReply.getLoadableStudystatusId());
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * @param buildLoadableStudyStatusRequest
   * @return LoadableStudyStatusReply
   */
  LoadableStudyStatusReply getLoadableStudyStatus(
      LoadableStudyStatusRequest loadableStudyStatusRequest) {
    return this.loadableStudyServiceBlockingStub.getLoadableStudyStatus(loadableStudyStatusRequest);
  }

  /**
   * @param loadableStudyId
   * @param correlationId
   * @return Long
   */
  private LoadableStudyStatusRequest buildLoadableStudyStatusRequest(
      Long loadableStudyId, String correlationId) {
    LoadableStudyStatusRequest.Builder builder = LoadableStudyStatusRequest.newBuilder();
    builder.setLoadableStudyId(loadableStudyId);
    return builder.build();
  }

  /**
   * Save synoptical records
   *
   * @param request
   * @param voyageId
   * @param loadableStudyId
   * @param first
   * @return
   * @throws InterruptedException
   */
  public SynopticalTableResponse saveSynopticalTable(
      com.cpdss.gateway.domain.SynopticalTableRequest request,
      Long voyageId,
      Long loadableStudyId,
      String correlationId)
      throws GenericServiceException, InterruptedException {
    log.info("LoadableStudyService: saveSynopticalTable, correlationId:{}", correlationId);
    log.debug(
        "LoadableStudyService: saveSynopticalTable, request: {}, voyageId: {}, loadbleStudyId:{}",
        request,
        voyageId,
        loadableStudyId);
    SynopticalTableResponse response = new SynopticalTableResponse();
    List<Long> failedRecords = new ArrayList<>();
    List<Thread> workers = new ArrayList<>();
    Set<Long> portIds =
        request.getSynopticalRecords().stream()
            .map(SynopticalRecord::getPortId)
            .collect(Collectors.toSet());
    CountDownLatch latch = new CountDownLatch(portIds.size());
    for (Long portId : portIds) {
      SynopticalTableRequest grpcRequest =
          this.buildSynopticalTableRequest(portId, request, loadableStudyId, correlationId);
      workers.add(
          new Thread(
              () -> this.saveSynopticalTable(grpcRequest, correlationId, failedRecords, latch)));
    }
    workers.forEach(Thread::start);
    latch.await();
    if (!failedRecords.isEmpty()) {
      response.setFailedRecords(failedRecords);
      response.setResponseStatus(
          new CommonSuccessResponse(
              String.valueOf(HttpStatus.MULTI_STATUS.value()), correlationId));
    } else {
      response.setResponseStatus(
          new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    }
    return response;
  }

  /**
   * Call grpc service for saving synoptical table
   *
   * @param grpcRequest
   * @return
   */
  private void saveSynopticalTable(
      SynopticalTableRequest grpcRequest,
      String correlationId,
      List<Long> failedRecords,
      CountDownLatch latch) {
    try {
      log.debug("calling grpc serice: saveSynopticalTable, correationId: {}", correlationId);
      SynopticalTableReply grpcReply =
          this.loadableStudyServiceBlockingStub.saveSynopticalTable(grpcRequest);
      if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
        grpcRequest.getSynopticalRecordList().forEach(rec -> failedRecords.add(rec.getId()));
      }
    } catch (Exception e) {
      log.error("Error calling synoptical table save grpc service", e);
    } finally {
      latch.countDown();
    }
  }

  /**
   * Build synoptical record grpc object
   *
   * @param request
   * @param loadableStudyId
   * @return
   * @throws GenericServiceException
   */
  private SynopticalTableRequest buildSynopticalTableRequest(
      Long portId,
      com.cpdss.gateway.domain.SynopticalTableRequest request,
      Long loadableStudyId,
      String correlationId)
      throws GenericServiceException {
    log.debug("building grpc request, correlationId:{}", correlationId);
    SynopticalTableRequest.Builder builder = SynopticalTableRequest.newBuilder();
    builder.setLoadableStudyId(loadableStudyId);
    List<SynopticalRecord> records =
        request.getSynopticalRecords().stream()
            .filter(rec -> rec.getPortId().equals(portId))
            .collect(Collectors.toList());
    if (records.size() != 2) {
      throw new GenericServiceException(
          "Invalid size of port records",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    for (SynopticalRecord rec : records) {
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder recordBuilder =
          com.cpdss.common.generated.LoadableStudy.SynopticalRecord.newBuilder();
      this.buildSynopticalRequestRecord(recordBuilder, rec);
      this.buildSynopticalRequestLoadicatorData(recordBuilder, rec);
      this.buildSynopticalRequestCargoData(recordBuilder, rec);
      builder.addSynopticalRecord(recordBuilder.build());
    }
    return builder.build();
  }

  /**
   * Cargo data for save request of synoptical table
   *
   * @param recordBuilder
   * @param request
   */
  private void buildSynopticalRequestCargoData(
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder recordBuilder,
      SynopticalRecord request) {
    if (null != request.getCargos()) {
      for (SynopticalCargoRecord cargo : request.getCargos()) {
        com.cpdss.common.generated.LoadableStudy.SynopticalCargoRecord.Builder builder =
            com.cpdss.common.generated.LoadableStudy.SynopticalCargoRecord.newBuilder();
        Optional.ofNullable(cargo.getTankId()).ifPresent(builder::setTankId);
        Optional.ofNullable(cargo.getPlannedWeight())
            .ifPresent(item -> builder.setPlannedWeight(valueOf(item)));
        Optional.ofNullable(cargo.getActualWeight())
            .ifPresent(item -> builder.setActualWeight(valueOf(item)));
        recordBuilder.addCargo(builder.build());
      }
    }
  }

  /**
   * Loadicator data for save request of synoptical table
   *
   * @param recordBuilder
   * @param request
   */
  private void buildSynopticalRequestLoadicatorData(
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder recordBuilder,
      SynopticalRecord request) {
    SynopticalTableLoadicatorData.Builder loadicatorBuilder =
        SynopticalTableLoadicatorData.newBuilder();
    Optional.ofNullable(request.getHogSag())
        .ifPresent(item -> loadicatorBuilder.setHogSag(valueOf(item)));
    Optional.ofNullable(request.getCalculatedDraftFwdActual())
        .ifPresent(item -> loadicatorBuilder.setCalculatedDraftFwdActual(valueOf(item)));
    Optional.ofNullable(request.getCalculatedDraftAftActual())
        .ifPresent(item -> loadicatorBuilder.setCalculatedDraftAftActual(valueOf(item)));
    Optional.ofNullable(request.getCalculatedDraftMidActual())
        .ifPresent(item -> loadicatorBuilder.setCalculatedDraftMidActual(valueOf(item)));
    Optional.ofNullable(request.getCalculatedTrimActual())
        .ifPresent(item -> loadicatorBuilder.setCalculatedTrimActual(valueOf(item)));
    Optional.ofNullable(request.getBlindSector())
        .ifPresent(item -> loadicatorBuilder.setBlindSector(valueOf(item)));
    recordBuilder.setLoadicatorData(loadicatorBuilder.build());
  }

  /**
   * Build synoptical table save request
   *
   * @param recordBuilder
   * @param request
   */
  private void buildSynopticalRequestRecord(
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder recordBuilder,
      SynopticalRecord request) {
    recordBuilder.setId(request.getId());
    recordBuilder.setPortId(request.getPortId());
    recordBuilder.setOperationType(request.getOperationType());
    Optional.ofNullable(request.getDistance())
        .ifPresent(item -> recordBuilder.setDistance(valueOf(request.getDistance())));
    Optional.ofNullable(request.getSpeed())
        .ifPresent(item -> recordBuilder.setSpeed(valueOf(request.getSpeed())));
    Optional.ofNullable(request.getRunningHours())
        .ifPresent(item -> recordBuilder.setRunningHours(valueOf(request.getRunningHours())));
    Optional.ofNullable(request.getInPortHours())
        .ifPresent(item -> recordBuilder.setInPortHours(valueOf(request.getInPortHours())));
    Optional.ofNullable(request.getInPortHours())
        .ifPresent(item -> recordBuilder.setInPortHours(valueOf(request.getInPortHours())));
    Optional.ofNullable(request.getTimeOfSunrise()).ifPresent(recordBuilder::setTimeOfSunrise);
    Optional.ofNullable(request.getTimeOfSunset()).ifPresent(recordBuilder::setTimeOfSunset);

    Optional.ofNullable(request.getHwTideFrom())
        .ifPresent(item -> recordBuilder.setHwTideFrom(valueOf(request.getHwTideFrom())));
    Optional.ofNullable(request.getHwTideTo())
        .ifPresent(item -> recordBuilder.setHwTideTo(valueOf(request.getHwTideTo())));
    Optional.ofNullable(request.getHwTideTimeFrom())
        .ifPresent(item -> recordBuilder.setHwTideTimeFrom(valueOf(request.getHwTideTimeFrom())));
    Optional.ofNullable(request.getHwTideTimeTo())
        .ifPresent(item -> recordBuilder.setHwTideTimeTo(valueOf(request.getHwTideTimeTo())));

    Optional.ofNullable(request.getLwTideFrom())
        .ifPresent(item -> recordBuilder.setLwTideFrom(valueOf(request.getLwTideFrom())));
    Optional.ofNullable(request.getLwTideTo())
        .ifPresent(item -> recordBuilder.setLwTideTo(valueOf(request.getLwTideTo())));
    Optional.ofNullable(request.getLwTideTimeFrom())
        .ifPresent(item -> recordBuilder.setLwTideTimeFrom(valueOf(request.getLwTideTimeFrom())));
    Optional.ofNullable(request.getLwTideTimeTo())
        .ifPresent(item -> recordBuilder.setLwTideTimeTo(valueOf(request.getLwTideTimeTo())));

    Optional.ofNullable(request.getSpecificGravity())
        .ifPresent(item -> recordBuilder.setSpecificGravity(valueOf(request.getSpecificGravity())));
    Optional.ofNullable(request.getEtaEtdActual()).ifPresent(recordBuilder::setEtaEtdActual);
    Optional.ofNullable(request.getEtaEtdPlanned()).ifPresent(recordBuilder::setEtaEtdEstimated);
  }

  public LoadableStudyAttachmentResponse downloadLoadableStudyAttachment(
      Long attachmentId, Long loadableStudyId, String correlationId)
      throws GenericServiceException {
    log.info(
        "Inside downloadLoadableStudyAttachment gateway service with correlationId : "
            + correlationId);
    LoadableStudyAttachmentResponse response = new LoadableStudyAttachmentResponse();

    LoadableStudyAttachmentReply grpcReply =
        this.downloadLoadableStudyAttachment(
            this.builddownloadLoadableStudyAttachmentRequest(
                attachmentId, loadableStudyId, correlationId));
    if (!SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to get download LoadableStudy Attachment",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    response.setFilePath(grpcReply.getFilePath());
    return response;
  }

  public LoadableStudyAttachmentReply downloadLoadableStudyAttachment(
      LoadableStudyAttachmentRequest grpcRequest) {
    return this.loadableStudyServiceBlockingStub.downloadLoadableStudyAttachment(grpcRequest);
  }

  private LoadableStudyAttachmentRequest builddownloadLoadableStudyAttachmentRequest(
      Long attachmentId, Long loadableStudyId, String correlationId) {
    LoadableStudyAttachmentRequest.Builder builder = LoadableStudyAttachmentRequest.newBuilder();
    builder.setFileId(attachmentId);
    builder.setLoadableStudyId(loadableStudyId);
    return builder.build();
  }
}
