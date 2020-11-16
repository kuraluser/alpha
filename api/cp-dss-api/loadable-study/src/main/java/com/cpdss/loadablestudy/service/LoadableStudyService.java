/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.service;

import static java.lang.String.valueOf;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachment;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.Builder;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.LoadingPortDetail;
import com.cpdss.common.generated.LoadableStudy.Operation;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetail;
import com.cpdss.common.generated.LoadableStudy.PortRotationReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.LoadableStudy.ValveSegregation;
import com.cpdss.common.generated.LoadableStudy.ValveSegregationReply;
import com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest;
import com.cpdss.common.generated.LoadableStudy.VoyageDetail;
import com.cpdss.common.generated.LoadableStudy.VoyageListReply;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceImplBase;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.CargoNominationPortDetails;
import com.cpdss.loadablestudy.entity.CargoNominationValveSegregation;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyAttachments;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.CargoNominationOperationDetailsRepository;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import com.cpdss.loadablestudy.repository.CargoNominationValveSegregationRepository;
import com.cpdss.loadablestudy.repository.CargoOperationRepository;
import com.cpdss.loadablestudy.repository.LoadableQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyStatusRepository;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import io.grpc.stub.StreamObserver;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/** @Author jerin.g */
@Log4j2
@GrpcService
@Service
@Transactional
public class LoadableStudyService extends LoadableStudyServiceImplBase {

  @Value("${loadablestudy.attachement.rooFolder}")
  private String rootFolder;

  @Autowired private VoyageRepository voyageRepository;
  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRoationRepository;
  @Autowired private CargoOperationRepository cargoOperationRepository;

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private LoadableQuantityRepository loadableQuantityRepository;

  @Autowired private CargoNominationRepository cargoNominationRepository;

  @Autowired private CargoNominationValveSegregationRepository valveSegregationRepository;
  @Autowired private LoadableStudyStatusRepository loadableStudyStatusRepository;

  @Autowired
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String VOYAGEEXISTS = "VOYAGE_EXISTS";
  private static final String CREATED_DATE_FORMAT = "dd-MM-yyyy";
  private static final String INVALID_LOADABLE_QUANTITY = "INVALID_LOADABLE_QUANTITY";
  private static final String ETA_ETD_FROMAT = "yyyy-MM-dd HH:mm";
  private static final String LAY_CAN_FROMAT = "yyyy-MM-dd";
  private static final Long LOADING_OPERATION_ID = 1L;
  private static final Long DISCHARGING_OPERATION_ID = 2L;
  private static final Long LOADABLE_STUDY_INITIAL_STATUS_ID = 1L;
  private static final Long LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID = 3L;
  private static final String INVALID_LOADABLE_STUDY_ID = "INVALID_LOADABLE_STUDY_ID";

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  /**
   * method for save voyage
   *
   * @param request - voyage request details
   * @param responseObserver - grpc class
   * @return
   */
  @Override
  public void saveVoyage(VoyageRequest request, StreamObserver<VoyageReply> responseObserver) {
    VoyageReply reply = null;
    try {
      // validation for duplicate voyages
      if (!voyageRepository
          .findByCompanyXIdAndVesselXIdAndVoyageNo(
              request.getCompanyId(), request.getVesselId(), request.getVoyageNo())
          .isEmpty()) {
        reply =
            VoyageReply.newBuilder()
                .setResponseStatus(
                    StatusReply.newBuilder()
                        .setStatus(FAILED)
                        .setMessage(VOYAGEEXISTS)
                        .setCode(CommonErrorCodes.E_CPDSS_VOYAGE_EXISTS))
                .build();
      } else {

        Voyage voyage = new Voyage();
        voyage.setIsActive(true);
        voyage.setCompanyXId(request.getCompanyId());
        voyage.setVesselXId(request.getVesselId());
        voyage.setVoyageNo(request.getVoyageNo());
        voyage.setCaptainXId(request.getCaptainId());
        voyage.setChiefOfficerXId(request.getChiefOfficerId());
        voyage = voyageRepository.save(voyage);
        // when Db save is complete we return to client a success message
        reply =
            VoyageReply.newBuilder()
                .setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
                .setVoyageId(voyage.getId())
                .build();
      }
    } catch (Exception e) {

      log.error("Error in saving Voyage ", e);
      reply =
          VoyageReply.newBuilder()
              .setResponseStatus(
                  StatusReply.newBuilder()
                      .setStatus(FAILED)
                      .setMessage(FAILED)
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR))
              .build();

    } finally {
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }
  }

  /**
   * method to save loadable quantity
   *
   * @param loadableQuantityRequest
   * @param responseObserver
   * @throws Exception
   * @return void
   */
  @Override
  public void saveLoadableQuantity(
      LoadableQuantityRequest loadableQuantityRequest,
      StreamObserver<LoadableQuantityReply> responseObserver) {
    LoadableQuantityReply loadableQuantityReply = null;
    try {
      Optional<LoadableStudy> loadableStudy =
          loadableStudyRepository.findById((Long) loadableQuantityRequest.getLoadableStudyId());
      if (loadableStudy.isPresent()) {
        LoadableQuantity loadableQuantity = new LoadableQuantity();
        loadableQuantity.setConstant(new BigDecimal(loadableQuantityRequest.getConstant()));
        loadableQuantity.setDeadWeight(new BigDecimal(loadableQuantityRequest.getDwt()));
        loadableQuantity.setDisplacementAtDraftRestriction(
            StringUtils.isEmpty(loadableQuantityRequest.getDisplacmentDraftRestriction())
                ? null
                : new BigDecimal(loadableQuantityRequest.getDisplacmentDraftRestriction()));
        loadableQuantity.setDistanceFromLastPort(
            new BigDecimal(loadableQuantityRequest.getDistanceFromLastPort()));
        loadableQuantity.setEstimatedDOOnBoard(
            new BigDecimal(loadableQuantityRequest.getEstDOOnBoard()));
        loadableQuantity.setEstimatedFOOnBoard(
            new BigDecimal(loadableQuantityRequest.getEstFOOnBoard()));
        loadableQuantity.setEstimatedFWOnBoard(
            new BigDecimal(loadableQuantityRequest.getEstFreshWaterOnBoard()));
        loadableQuantity.setEstimatedSagging(
            new BigDecimal(loadableQuantityRequest.getEstSagging()));
        loadableQuantity.setDraftRestriction(
            new BigDecimal(loadableQuantityRequest.getLimitingDraft()));

        loadableQuantity.setEstimatedSeaDensity(
            StringUtils.isEmpty(loadableQuantityRequest.getEstSeaDensity())
                ? null
                : new BigDecimal(loadableQuantityRequest.getEstSeaDensity()));

        loadableQuantity.setFoConsumptionPerDay(
            new BigDecimal(loadableQuantityRequest.getFoConsumptionPerDay()));

        loadableQuantity.setLightWeight(
            StringUtils.isEmpty(loadableQuantityRequest.getVesselLightWeight())
                ? null
                : new BigDecimal(loadableQuantityRequest.getVesselLightWeight()));

        loadableQuantity.setLoadableStudyXId(loadableStudy.get());
        loadableQuantity.setOtherIfAny(new BigDecimal(loadableQuantityRequest.getOtherIfAny()));
        loadableQuantity.setSaggingDeduction(
            new BigDecimal(loadableQuantityRequest.getSaggingDeduction()));

        loadableQuantity.setSgCorrection(
            StringUtils.isEmpty(loadableQuantityRequest.getSgCorrection())
                ? null
                : new BigDecimal(loadableQuantityRequest.getSgCorrection()));

        loadableQuantity.setTotalFoConsumption(
            new BigDecimal(loadableQuantityRequest.getEstTotalFOConsumption()));
        loadableQuantity.setTotalQuantity(
            new BigDecimal(loadableQuantityRequest.getTotalQuantity()));
        loadableQuantity.setTpcatDraft(new BigDecimal(loadableQuantityRequest.getTpc()));
        loadableQuantity.setVesselAverageSpeed(
            new BigDecimal(loadableQuantityRequest.getVesselAverageSpeed()));

        loadableQuantity.setPortId(
            StringUtils.isEmpty(loadableQuantityRequest.getPortId())
                ? null
                : new BigDecimal(loadableQuantityRequest.getPortId()));
        loadableQuantity.setPortId(
            StringUtils.isEmpty(loadableQuantityRequest.getBoilerWaterOnBoard())
                ? null
                : new BigDecimal(loadableQuantityRequest.getBoilerWaterOnBoard()));
        loadableQuantity.setPortId(
            StringUtils.isEmpty(loadableQuantityRequest.getBallast())
                ? null
                : new BigDecimal(loadableQuantityRequest.getBallast()));
        loadableQuantity.setPortId(
            StringUtils.isEmpty(loadableQuantityRequest.getRunningHours())
                ? null
                : new BigDecimal(loadableQuantityRequest.getRunningHours()));
        loadableQuantity.setPortId(
            StringUtils.isEmpty(loadableQuantityRequest.getRunningDays())
                ? null
                : new BigDecimal(loadableQuantityRequest.getRunningDays()));
        loadableQuantity.setPortId(
            StringUtils.isEmpty(loadableQuantityRequest.getFoConInSZ())
                ? null
                : new BigDecimal(loadableQuantityRequest.getFoConInSZ()));
        loadableQuantity.setPortId(
            StringUtils.isEmpty(loadableQuantityRequest.getDraftRestriction())
                ? null
                : new BigDecimal(loadableQuantityRequest.getDraftRestriction()));

        loadableQuantity = loadableQuantityRepository.save(loadableQuantity);

        // when Db save is complete we return to client a success message
        loadableQuantityReply =
            LoadableQuantityReply.newBuilder()
                .setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
                .setLoadableQuantityId(loadableQuantity.getId())
                .build();
      } else {
        log.info("INVALID_LOADABLE_STUDY {} - ", loadableQuantityRequest.getLoadableStudyId());
        loadableQuantityReply =
            LoadableQuantityReply.newBuilder()
                .setResponseStatus(
                    StatusReply.newBuilder()
                        .setStatus(FAILED)
                        .setMessage(INVALID_LOADABLE_QUANTITY)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
                .build();
      }
    } catch (Exception e) {
      log.error("Error in saving loadable quantity ", e);
      loadableQuantityReply =
          LoadableQuantityReply.newBuilder()
              .setResponseStatus(
                  StatusReply.newBuilder()
                      .setStatus(FAILED)
                      .setMessage(FAILED)
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR))
              .build();
    } finally {
      responseObserver.onNext(loadableQuantityReply);
      responseObserver.onCompleted();
    }
  }

  /**
   * Method to find list of loadable studies based on vessel and voyage
   *
   * @param {@link LoadableStudyRequest} - The grpc generated message
   * @param {@link StreamObserver}
   */
  @Override
  @Transactional
  public void findLoadableStudiesByVesselAndVoyage(
      LoadableStudyRequest request, StreamObserver<LoadableStudyReply> responseObserver) {
    Builder replyBuilder = LoadableStudyReply.newBuilder();
    try {
      log.info("inside loadable study service - findLoadableStudiesByVesselAndVoyage");
      Optional<Voyage> voyageOpt = this.voyageRepository.findById(request.getVoyageId());
      if (!voyageOpt.isPresent()) {
        throw new GenericServiceException(
            "Voyage does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      List<LoadableStudy> loadableStudyEntityList =
          this.loadableStudyRepository.findByVesselXIdAndVoyageAndIsActive(
              request.getVesselId(), voyageOpt.get(), true);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT);
      for (LoadableStudy entity : loadableStudyEntityList) {
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.Builder builder =
            LoadableStudyDetail.newBuilder();
        builder.setId(entity.getId());
        builder.setName(entity.getName());
        builder.setCreatedDate(dateTimeFormatter.format(entity.getCreatedDate()));
        Optional.ofNullable(entity.getLoadableStudyStatus())
            .ifPresent(
                status -> {
                  builder.setStatusId(status.getId());
                  builder.setStatus(status.getName());
                });
        Optional.ofNullable(entity.getDetails()).ifPresent(builder::setDetail);
        Optional.ofNullable(entity.getCharterer()).ifPresent(builder::setCharterer);
        Optional.ofNullable(entity.getSubCharterer()).ifPresent(builder::setSubCharterer);
        Optional.ofNullable(entity.getLoadLineXId()).ifPresent(builder::setLoadLineXId);
        Optional.ofNullable(entity.getDraftMark())
            .ifPresent(dratMark -> builder.setDraftMark(valueOf(dratMark)));
        Optional.ofNullable(entity.getDraftRestriction())
            .ifPresent(draftRestriction -> builder.setDraftRestriction(valueOf(draftRestriction)));
        Optional.ofNullable(entity.getMaxAirTemperature())
            .ifPresent(maxTemp -> builder.setMaxAirTemperature(valueOf(maxTemp)));
        Optional.ofNullable(entity.getMaxWaterTemperature())
            .ifPresent(maxTemp -> builder.setMaxWaterTemperature(valueOf(maxTemp)));
        Set<LoadableStudyPortRotation> portRotations = entity.getPortRotations();
        if (null != portRotations && !portRotations.isEmpty()) {
          portRotations.forEach(
              port -> {
                if (port.isActive()
                    && null != port.getOperation()
                    && DISCHARGING_OPERATION_ID.equals(port.getOperation().getId())) {
                  builder.addDischargingPortIds(port.getPortXId());
                }
              });
        }
        replyBuilder.addLoadableStudies(builder.build());
      }

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study", e);
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(
                  ResponseStatus.newBuilder()
                      .setCode(e.getCode())
                      .setMessage(e.getMessage())
                      .setStatus(FAILED)
                      .build());
    } catch (Exception e) {
      log.error("Error fetching loadable studies", e);
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(
                  ResponseStatus.newBuilder()
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                      .setMessage("Error fetching loadable studies")
                      .setStatus(FAILED)
                      .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Save loadable study
   *
   * @param {@link LoadableStudyDetail}
   * @param {@link StreamObserver}
   */
  @Override
  public void saveLoadableStudy(
      LoadableStudyDetail request, StreamObserver<LoadableStudyReply> responseObserver) {
    Builder replyBuilder = LoadableStudyReply.newBuilder();
    LoadableStudy entity = null;
    try {
      entity = new LoadableStudy();
      this.checkVoyageAndCreatedFrom(request, entity);
      entity.setName(request.getName());
      entity.setDetails(StringUtils.isEmpty(request.getDetail()) ? null : request.getDetail());
      entity.setCharterer(
          StringUtils.isEmpty(request.getCharterer()) ? null : request.getCharterer());
      entity.setSubCharterer(
          StringUtils.isEmpty(request.getSubCharterer()) ? null : request.getSubCharterer());
      entity.setVesselXId(request.getVesselId());
      entity.setDraftMark(
          StringUtils.isEmpty(request.getDraftMark())
              ? null
              : new BigDecimal(request.getDraftMark()));
      entity.setLoadLineXId(request.getLoadLineXId());
      entity.setDraftRestriction(
          StringUtils.isEmpty(request.getDraftRestriction())
              ? null
              : new BigDecimal(request.getDraftRestriction()));
      entity.setMaxAirTemperature(
          StringUtils.isEmpty(request.getMaxAirTemperature())
              ? null
              : new BigDecimal(request.getMaxAirTemperature()));
      entity.setMaxWaterTemperature(
          StringUtils.isEmpty(request.getMaxWaterTemperature())
              ? null
              : new BigDecimal(request.getMaxWaterTemperature()));
      if (!request.getAttachmentsList().isEmpty()) {
        String folderLocation = this.constructFolderPath(entity);
        Files.createDirectories(Paths.get(this.rootFolder + folderLocation));
        Set<LoadableStudyAttachments> attachmentCollection = new HashSet<>();
        for (LoadableStudyAttachment attachment : request.getAttachmentsList()) {
          Path path = Paths.get(this.rootFolder + folderLocation + attachment.getFileName());
          Files.createFile(path);
          Files.write(path, attachment.getByteString().toByteArray());
          LoadableStudyAttachments attachmentEntity = new LoadableStudyAttachments();
          attachmentEntity.setUploadedFileName(attachment.getFileName());
          attachmentEntity.setFilePath(folderLocation);
          attachmentEntity.setLoadableStudy(entity);
          attachmentCollection.add(attachmentEntity);
        }
        entity.setAttachments(attachmentCollection);
      }
      entity.setLoadableStudyStatus(
          this.loadableStudyStatusRepository.getOne(LOADABLE_STUDY_INITIAL_STATUS_ID));
      entity = this.loadableStudyRepository.save(entity);
      replyBuilder
          .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
          .setId(entity.getId());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
      this.deleteFiles(entity);
    } catch (Exception e) {
      log.error("Error saving loadable study", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Error saving loadable study")
              .setStatus(FAILED)
              .build());
      this.deleteFiles(entity);
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  private void checkVoyageAndCreatedFrom(LoadableStudyDetail request, LoadableStudy entity)
      throws GenericServiceException {
    Optional<Voyage> voyageOpt = this.voyageRepository.findById(request.getVoyageId());
    if (!voyageOpt.isPresent()) {
      throw new GenericServiceException(
          "Voyage does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
    }
    entity.setVoyage(voyageOpt.get());
    if (0 != request.getDuplicatedFromId()) {
      Optional<LoadableStudy> createdFromOpt =
          this.loadableStudyRepository.findById(request.getDuplicatedFromId());
      if (!createdFromOpt.isPresent()) {
        throw new GenericServiceException(
            "Created from loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            null);
      }
      entity.setDuplicatedFrom(createdFromOpt.get());
    }
  }

  /**
   * Construct folder path for loadable study attachments
   *
   * @param loadableStudy - loadable study entity
   * @param voyage - voyage entity
   * @return - the folder path
   */
  private String constructFolderPath(LoadableStudy loadableStudy) {
    String separator = File.separator;
    StringBuilder pathBuilder = new StringBuilder(separator);
    pathBuilder
        .append("company_")
        .append(loadableStudy.getVoyage().getCompanyXId())
        .append(separator)
        .append("vessel_")
        .append(loadableStudy.getVesselXId())
        .append(separator)
        .append(loadableStudy.getVoyage().getVoyageNo().replace(" ", "_"))
        .append("_")
        .append(loadableStudy.getVoyage().getId())
        .append(separator)
        .append(loadableStudy.getName().replace(" ", "_"))
        .append(separator);
    return valueOf(pathBuilder);
  }

  /**
   * Method to delete file if there is any exception when saving loadable study
   *
   * @param entity - the set of entities for attachments
   */
  private void deleteFiles(LoadableStudy entity) {
    if (null == entity || null == entity.getAttachments()) {
      return;
    }
    for (LoadableStudyAttachments attachment : entity.getAttachments()) {
      Path path =
          Paths.get(this.rootFolder + attachment.getFilePath() + attachment.getUploadedFileName());
      try {
        Files.deleteIfExists(path);
      } catch (IOException e) {
        log.error(
            "unable to delete file : {}",
            this.rootFolder + attachment.getFilePath() + attachment.getUploadedFileName(),
            e);
      }
    }
  }

  @Override
  public void saveCargoNomination(
      CargoNominationRequest request, StreamObserver<CargoNominationReply> responseObserver) {
    CargoNominationReply.Builder cargoNominationReplyBuilder = CargoNominationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudy =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudy.isPresent()) {
        throw new GenericServiceException(
            "Loadable Study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      CargoNomination cargoNomination = null;
      if (request.getCargoNominationDetail() != null
          && request.getCargoNominationDetail().getId() != 0) {
        Optional<CargoNomination> existingCargoNomination =
            this.cargoNominationRepository.findByIdAndIsActive(
                request.getCargoNominationDetail().getId(), true);
        if (!existingCargoNomination.isPresent()) {
          throw new GenericServiceException(
              "Cargo Nomination does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
        cargoNomination = existingCargoNomination.get();
        cargoNomination = buildCargoNomination(cargoNomination, request);
      } else if (request.getCargoNominationDetail() != null
          && request.getCargoNominationDetail().getId() == 0) {
        cargoNomination = new CargoNomination();
        cargoNomination = buildCargoNomination(cargoNomination, request);
      }
      this.cargoNominationRepository.save(cargoNomination);
      cargoNominationReplyBuilder
          .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS))
          .setCargoNominationId(
              (cargoNomination != null && cargoNomination.getId() != null)
                  ? cargoNomination.getId()
                  : 0);
    } catch (Exception e) {
      log.error("Error saving cargo nomination", e);
      cargoNominationReplyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(cargoNominationReplyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  private CargoNomination buildCargoNomination(
      CargoNomination cargoNomination, CargoNominationRequest request) {
    cargoNomination.setLoadableStudyXId(request.getCargoNominationDetail().getLoadableStudyId());
    cargoNomination.setPriority(request.getCargoNominationDetail().getPriority());
    cargoNomination.setCargoXId(request.getCargoNominationDetail().getCargoId());
    cargoNomination.setAbbreviation(request.getCargoNominationDetail().getAbbreviation());
    cargoNomination.setColor(request.getCargoNominationDetail().getColor());
    cargoNomination.setQuantity(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getQuantity())
            ? new BigDecimal(request.getCargoNominationDetail().getQuantity())
            : null);
    cargoNomination.setMaxTolerance(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getMaxTolerance())
            ? new BigDecimal(request.getCargoNominationDetail().getMaxTolerance())
            : null);
    cargoNomination.setMinTolerance(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getMinTolerance())
            ? new BigDecimal(request.getCargoNominationDetail().getMinTolerance())
            : null);
    cargoNomination.setApi(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getApiEst())
            ? new BigDecimal(request.getCargoNominationDetail().getApiEst())
            : null);
    cargoNomination.setTemperature(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getTempEst())
            ? new BigDecimal(request.getCargoNominationDetail().getTempEst())
            : null);
    cargoNomination.setSegregationXId(request.getCargoNominationDetail().getSegregationId());
    // activate the records to be saved
    cargoNomination.setIsActive(true);
    if (!request.getCargoNominationDetail().getLoadingPortDetailsList().isEmpty()) {
      // clear any existing CargoNominationPortDetails otherwise create new
      if (cargoNomination.getCargoNominationPortDetails() != null) {
        cargoNomination.getCargoNominationPortDetails().clear();
      }
      Set<CargoNominationPortDetails> cargoNominationPortDetailsList =
          request.getCargoNominationDetail().getLoadingPortDetailsList().stream()
              .map(
                  loadingPortDetail -> {
                    CargoNominationPortDetails cargoNominationPortDetails =
                        new CargoNominationPortDetails();
                    cargoNominationPortDetails.setCargoNomination(cargoNomination);
                    cargoNominationPortDetails.setPortId(loadingPortDetail.getPortId());
                    cargoNominationPortDetails.setQuantity(
                        !loadingPortDetail.getQuantity().isEmpty()
                            ? new BigDecimal(loadingPortDetail.getQuantity())
                            : null);
                    cargoNominationPortDetails.setIsActive(true);
                    return cargoNominationPortDetails;
                  })
              .collect(Collectors.toSet());
      // clear any existing CargoNominationPortDetails otherwise create new
      if (cargoNomination.getCargoNominationPortDetails() != null) {
        cargoNomination.getCargoNominationPortDetails().addAll(cargoNominationPortDetailsList);
      } else {
        cargoNomination.setCargoNominationPortDetails(cargoNominationPortDetailsList);
      }
    }
    return cargoNomination;
  }

  @Override
  public void getLoadableStudyPortRotation(
      PortRotationRequest request, StreamObserver<PortRotationReply> responseObserver) {
    PortRotationReply.Builder replyBuilder = PortRotationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist in database", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      CargoOperation dischargingOperation =
          this.cargoOperationRepository.getOne(DISCHARGING_OPERATION_ID);
      List<LoadableStudyPortRotation> entityList =
          this.loadableStudyPortRoationRepository
              .findByLoadableStudyAndOperationNotAndIsActiveOrderByPortOrder(
                  loadableStudyOpt.get(), dischargingOperation, true);
      for (LoadableStudyPortRotation entity : entityList) {
        replyBuilder.addPorts(
            this.createPortDetail(
                entity,
                DateTimeFormatter.ofPattern(ETA_ETD_FROMAT),
                DateTimeFormatter.ofPattern(LAY_CAN_FROMAT)));
      }
      List<CargoOperation> operationEntityList = this.cargoOperationRepository.findAll();
      for (CargoOperation entity : operationEntityList) {
        replyBuilder.addOperations(this.createOperationDetail(entity));
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study - port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when fetching port rotation data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when fetching loadable study - port data")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Create {@link Operation} from {@link CargoOperation}
   *
   * @param entity - {@link CargoOperation}
   * @return
   */
  private Operation createOperationDetail(CargoOperation entity) {
    return Operation.newBuilder().setId(entity.getId()).setOperationName(entity.getName()).build();
  }

  /**
   * Create {@link PortDetail} from {@link LoadableStudyPortRotation}
   *
   * @param entity {@link LoadableStudyPortRotation}
   * @return {@link PortDetail}
   */
  private PortRotationDetail createPortDetail(
      LoadableStudyPortRotation entity,
      DateTimeFormatter etaEtdFormatter,
      DateTimeFormatter layCanFormatter) {
    PortRotationDetail.Builder builder = PortRotationDetail.newBuilder();
    builder.setId(entity.getId());
    builder.setLoadableStudyId(entity.getLoadableStudy().getId());
    Optional.ofNullable(entity.getPortXId()).ifPresent(builder::setPortId);
    Optional.ofNullable(entity.getOperation()).ifPresent(op -> builder.setOperationId(op.getId()));
    Optional.ofNullable(entity.getBerthXId()).ifPresent(builder::setBerthId);
    Optional.ofNullable(entity.getSeaWaterDensity())
        .ifPresent(density -> builder.setSeaWaterDensity(valueOf(density)));
    Optional.ofNullable(entity.getDistanceBetweenPorts())
        .ifPresent(distance -> builder.setDistanceBetweenPorts(valueOf(distance)));
    Optional.ofNullable(entity.getTimeOfStay())
        .ifPresent(timeOfStay -> builder.setTimeOfStay(valueOf(timeOfStay)));
    Optional.ofNullable(entity.getMaxDraft())
        .ifPresent(maxDraft -> builder.setMaxDraft(valueOf(maxDraft)));
    Optional.ofNullable(entity.getAirDraftRestriction())
        .ifPresent(airDraft -> builder.setMaxAirDraft(valueOf(airDraft)));
    Optional.ofNullable(entity.getEta())
        .ifPresent(eta -> builder.setEta(etaEtdFormatter.format(eta)));
    Optional.ofNullable(entity.getEtd())
        .ifPresent(etd -> builder.setEtd(etaEtdFormatter.format(etd)));
    Optional.ofNullable(entity.getLayCanFrom())
        .ifPresent(layCanFrom -> builder.setLayCanFrom(layCanFormatter.format(layCanFrom)));
    Optional.ofNullable(entity.getLayCanTo())
        .ifPresent(layCanTo -> builder.setLayCanTo(layCanFormatter.format(layCanTo)));
    Optional.ofNullable(entity.getPortOrder()).ifPresent(builder::setPortOrder);
    return builder.build();
  }

  /**
   * Retrieves list of cargoNominations by LoadableStudyId can be extended to other Ids like
   * vesselId or voyageId
   */
  @Override
  public void getCargoNominationById(
      CargoNominationRequest request, StreamObserver<CargoNominationReply> responseObserver) {
    com.cpdss.common.generated.LoadableStudy.CargoNominationReply.Builder replyBuilder =
        CargoNominationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      List<CargoNomination> cargoNominationList =
          this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
              request.getLoadableStudyId(), true);
      buildCargoNominationReply(cargoNominationList, replyBuilder);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study - port data", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } catch (Exception e) {
      log.error("Exception when fetching loadable study - port data", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * method for getting loadable quantity
   *
   * @param request - has the loadable quantity id
   * @param responseObserver
   */
  @Override
  public void getLoadableQuantity(
      LoadableQuantityReply request, StreamObserver<LoadableQuantityResponse> responseObserver) {
    LoadableQuantityResponse.Builder builder = LoadableQuantityResponse.newBuilder();
    try {
      List<LoadableQuantity> loadableQuantity =
          loadableQuantityRepository.findByLoadableStudyXId(request.getLoadableStudyId());
      Optional<LoadableStudy> loadableStudy =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudy.isPresent()) {
        log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadableStudyId());
        builder.setResponseStatus(
            StatusReply.newBuilder()
                .setStatus(FAILED)
                .setMessage(INVALID_LOADABLE_QUANTITY)
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
      } else if (loadableQuantity.isEmpty()) {
        String draftRestictoin = "";
        String draftExtreme = "";
        if (Optional.ofNullable(loadableStudy.get().getDraftMark()).isPresent()) {
          draftExtreme = loadableStudy.get().getDraftMark().toString();
        }
        VesselRequest replyBuilder =
            VesselRequest.newBuilder()
                .setVesselId(loadableStudy.get().getVesselXId())
                .setVesselDraftConditionId(loadableStudy.get().getLoadLineXId())
                .setDraftExtreme(draftExtreme)
                .build();
        VesselReply vesselReply = this.getVesselDetailsById(replyBuilder);
        if (Optional.ofNullable(loadableStudy.get().getDraftRestriction()).isPresent()) {
          draftRestictoin = loadableStudy.get().getDraftRestriction().toString();
        } else if (Optional.ofNullable(loadableStudy.get().getDraftMark()).isPresent()) {
          draftRestictoin = loadableStudy.get().getDraftMark().toString();
        }
        LoadableQuantityRequest loadableQuantityRequest =
            LoadableQuantityRequest.newBuilder()
                .setDisplacmentDraftRestriction(
                    vesselReply.getVesselLoadableQuantityDetails().getDisplacmentDraftRestriction())
                .setVesselLightWeight(
                    vesselReply.getVesselLoadableQuantityDetails().getVesselLightWeight())
                .setConstant(vesselReply.getVesselLoadableQuantityDetails().getConstant())
                .setDraftRestriction(draftRestictoin)
                .build();
        builder.setLoadableQuantityRequest(loadableQuantityRequest);
        builder.setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS));
      } else {

        LoadableQuantityRequest.Builder loadableQuantityRequest =
            LoadableQuantityRequest.newBuilder();
        Optional.ofNullable(loadableQuantity.get(0).getConstant())
            .ifPresent(cons -> loadableQuantityRequest.setConstant(cons.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getDraftRestriction())
            .ifPresent(
                draftRestriction ->
                    loadableQuantityRequest.setDraftRestriction(draftRestriction.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getDistanceFromLastPort())
            .ifPresent(dist -> loadableQuantityRequest.setDistanceFromLastPort(dist.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getDeadWeight())
            .ifPresent(deadWeight -> loadableQuantityRequest.setDwt(deadWeight.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getEstimatedDOOnBoard())
            .ifPresent(
                estDOOnBoard -> loadableQuantityRequest.setEstDOOnBoard(estDOOnBoard.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getEstimatedFOOnBoard())
            .ifPresent(
                estFOOnBoard -> loadableQuantityRequest.setEstFOOnBoard(estFOOnBoard.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getEstimatedFWOnBoard())
            .ifPresent(
                estFreshWaterOnBoard ->
                    loadableQuantityRequest.setEstFreshWaterOnBoard(
                        estFreshWaterOnBoard.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getEstimatedSagging())
            .ifPresent(estSagging -> loadableQuantityRequest.setEstSagging(estSagging.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getEstimatedSeaDensity())
            .ifPresent(
                estSeaDensity ->
                    loadableQuantityRequest.setEstSeaDensity(estSeaDensity.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getTotalFoConsumption())
            .ifPresent(
                estTotalFOConsumption ->
                    loadableQuantityRequest.setEstTotalFOConsumption(
                        estTotalFOConsumption.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getFoConsumptionPerDay())
            .ifPresent(
                foConsumptionPerDay ->
                    loadableQuantityRequest.setFoConsumptionPerDay(foConsumptionPerDay.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getDraftRestriction())
            .ifPresent(
                limitingDraft ->
                    loadableQuantityRequest.setLimitingDraft(limitingDraft.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getOtherIfAny())
            .ifPresent(otherIfAny -> loadableQuantityRequest.setOtherIfAny(otherIfAny.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getSaggingDeduction())
            .ifPresent(
                saggingDeduction ->
                    loadableQuantityRequest.setSaggingDeduction(saggingDeduction.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getSgCorrection())
            .ifPresent(
                sgCorrection -> loadableQuantityRequest.setSgCorrection(sgCorrection.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getTotalQuantity())
            .ifPresent(
                totalQuantity ->
                    loadableQuantityRequest.setTotalQuantity(totalQuantity.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getTpcatDraft())
            .ifPresent(tpc -> loadableQuantityRequest.setTpc(tpc.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getVesselAverageSpeed())
            .ifPresent(
                vesselAverageSpeed ->
                    loadableQuantityRequest.setVesselAverageSpeed(vesselAverageSpeed.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getLightWeight())
            .ifPresent(
                vesselLightWeight ->
                    loadableQuantityRequest.setVesselLightWeight(vesselLightWeight.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getLastModifiedDateTime())
            .ifPresent(
                updateDateAndTime ->
                    loadableQuantityRequest.setUpdateDateAndTime(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            .format(updateDateAndTime)));
        Optional.ofNullable(loadableQuantity.get(0).getPortId())
            .ifPresent(portId -> loadableQuantityRequest.setPortId(portId.longValue()));
        Optional.ofNullable(loadableQuantity.get(0).getBoilerWaterOnBoard())
            .ifPresent(
                boilerWaterOnBoard ->
                    loadableQuantityRequest.setBoilerWaterOnBoard(boilerWaterOnBoard.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getBallast())
            .ifPresent(ballast -> loadableQuantityRequest.setBallast(ballast.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getRunningHours())
            .ifPresent(
                runningHours -> loadableQuantityRequest.setRunningHours(runningHours.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getRunningDays())
            .ifPresent(
                runningDays -> loadableQuantityRequest.setRunningDays(runningDays.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getFoConsumptionInSZ())
            .ifPresent(
                foConsumptionInSZ ->
                    loadableQuantityRequest.setFoConInSZ(foConsumptionInSZ.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getDraftRestriction())
            .ifPresent(
                draftRestriction ->
                    loadableQuantityRequest.setDraftRestriction(draftRestriction.toString()));
        builder.setIsSummerZone(true);
        Optional.ofNullable(loadableStudy.get().getDraftRestriction())
            .ifPresent(sz -> builder.setIsSummerZone(false));
        builder.setLoadableQuantityRequest(loadableQuantityRequest);
        builder.setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS));
      }
    } catch (Exception e) {
      log.error("Error getting loadable quantity ", e);
      builder.setResponseStatus(
          StatusReply.newBuilder()
              .setStatus(FAILED)
              .setMessage(FAILED)
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR));
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  public VesselReply getVesselDetailsById(VesselRequest replyBuilder) {
    return this.vesselInfoGrpcService.getVesselDetailsById(replyBuilder);
  }

  private void buildCargoNominationReply(
      List<CargoNomination> cargoNominationList,
      com.cpdss.common.generated.LoadableStudy.CargoNominationReply.Builder
          cargoNominationReplyBuilder) {
    if (!CollectionUtils.isEmpty(cargoNominationList)) {
      cargoNominationList.forEach(
          cargoNomination -> {
            CargoNominationDetail.Builder builder = CargoNominationDetail.newBuilder();
            Optional.ofNullable(cargoNomination.getId()).ifPresent(builder::setId);
            Optional.ofNullable(cargoNomination.getLoadableStudyXId())
                .ifPresent(builder::setLoadableStudyId);
            Optional.ofNullable(cargoNomination.getPriority()).ifPresent(builder::setPriority);
            Optional.ofNullable(cargoNomination.getColor()).ifPresent(builder::setColor);
            Optional.ofNullable(cargoNomination.getCargoXId()).ifPresent(builder::setCargoId);
            Optional.ofNullable(cargoNomination.getAbbreviation())
                .ifPresent(builder::setAbbreviation);
            // build inner loadingPort details object
            if (!CollectionUtils.isEmpty(cargoNomination.getCargoNominationPortDetails())) {
              cargoNomination
                  .getCargoNominationPortDetails()
                  .forEach(
                      loadingPort -> {
                        LoadingPortDetail.Builder loadingPortDetailBuilder =
                            LoadingPortDetail.newBuilder();
                        Optional.ofNullable(loadingPort.getPortId())
                            .ifPresent(loadingPortDetailBuilder::setPortId);
                        Optional.ofNullable(loadingPort.getQuantity())
                            .ifPresent(
                                quantity ->
                                    loadingPortDetailBuilder.setQuantity(String.valueOf(quantity)));
                        builder.addLoadingPortDetails(loadingPortDetailBuilder);
                      });
            }
            Optional.ofNullable(cargoNomination.getMaxTolerance())
                .ifPresent(maxTolerance -> builder.setMaxTolerance(String.valueOf(maxTolerance)));
            Optional.ofNullable(cargoNomination.getMinTolerance())
                .ifPresent(minTolerance -> builder.setMinTolerance(String.valueOf(minTolerance)));
            Optional.ofNullable(cargoNomination.getApi())
                .ifPresent(api -> builder.setApiEst(String.valueOf(api)));
            Optional.ofNullable(cargoNomination.getTemperature())
                .ifPresent(temperature -> builder.setTempEst(String.valueOf(temperature)));
            Optional.ofNullable(cargoNomination.getSegregationXId())
                .ifPresent(builder::setSegregationId);
            cargoNominationReplyBuilder.addCargoNominations(builder);
          });
    }
  }

  /** Retrieves all valve segregation available */
  @Override
  public void getValveSegregation(
      ValveSegregationRequest request, StreamObserver<ValveSegregationReply> responseObserver) {
    ValveSegregationReply.Builder reply = ValveSegregationReply.newBuilder();
    try {
      Iterable<CargoNominationValveSegregation> segregationsList =
          valveSegregationRepository.findAll();
      segregationsList.forEach(
          segregation -> {
            ValveSegregation.Builder segregationDetail = ValveSegregation.newBuilder();
            if (segregation.getId() != null) {
              segregationDetail.setId(segregation.getId());
            }
            if (!StringUtils.isEmpty(segregation.getName())) {
              segregationDetail.setName(segregation.getName());
            }
            reply.addValveSegregation(segregationDetail);
          });
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      reply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getValveSegregation method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      reply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(reply.build());
      responseObserver.onCompleted();
    }
  }

  /** Get voyages by vessel */
  @Override
  public void getVoyagesByVessel(
      VoyageRequest request, StreamObserver<VoyageListReply> responseObserver) {
    VoyageListReply.Builder builder = VoyageListReply.newBuilder();
    try {
      List<Voyage> entityList =
          this.voyageRepository.findByVesselXIdAndIsActive(request.getVesselId(), true);
      for (Voyage entity : entityList) {
        VoyageDetail.Builder detailbuilder = VoyageDetail.newBuilder();
        detailbuilder.setId(entity.getId());
        detailbuilder.setVoyageNumber(entity.getVoyageNo());
        builder.addVoyages(detailbuilder.build());
      }
      builder.setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).build());
    } catch (Exception e) {
      builder.setResponseStatus(
          StatusReply.newBuilder()
              .setStatus(FAILED)
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /** Save port rotation info for loadable study */
  @Override
  public void saveLoadableStudyPortRotation(
      PortRotationDetail request, StreamObserver<PortRotationReply> responseObserver) {
    PortRotationReply.Builder replyBuilder = PortRotationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      LoadableStudyPortRotation entity = null;
      if (request.getId() == 0) {
        entity = new LoadableStudyPortRotation();
        entity.setLoadableStudy(loadableStudyOpt.get());
      } else {
        Optional<LoadableStudyPortRotation> portRoationOpt =
            this.loadableStudyPortRoationRepository.findById(request.getId());
        if (!portRoationOpt.isPresent()) {
          throw new GenericServiceException(
              "Port rotation does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
        entity = portRoationOpt.get();
      }
      entity =
          this.loadableStudyPortRoationRepository.save(
              this.createPortRotationEntity(entity, request));
      replyBuilder.setPortRotationId(entity.getId());
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study - port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when saving loadable study port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving port data")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  @Transactional
  public void saveDischargingPorts(
      PortRotationRequest request, StreamObserver<PortRotationReply> responseObserver) {
    PortRotationReply.Builder replyBuilder = PortRotationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      CargoOperation discharging = this.cargoOperationRepository.getOne(DISCHARGING_OPERATION_ID);
      List<LoadableStudyPortRotation> dischargingPorts =
          this.loadableStudyPortRoationRepository.findByLoadableStudyAndOperationAndIsActive(
              loadableStudyOpt.get(), discharging, true);
      List<Long> portIds = new ArrayList<>(request.getDischargingPortIdsList());
      for (LoadableStudyPortRotation portRoation : dischargingPorts) {
        if (!request.getDischargingPortIdsList().contains(portRoation.getPortXId())) {
          portRoation.setActive(false);
        } else {
          portIds.remove(portRoation.getPortXId());
        }
      }
      if (!portIds.isEmpty()) {
        portIds.forEach(
            id -> {
              LoadableStudyPortRotation port = new LoadableStudyPortRotation();
              port.setPortXId(id);
              port.setLoadableStudy(loadableStudyOpt.get());
              port.setOperation(discharging);
              dischargingPorts.add(port);
            });
      }
      this.loadableStudyPortRoationRepository.saveAll(dischargingPorts);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving discharging ports", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when saving discharging ports data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving discharging ports")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Create entity class from request
   *
   * @param entity
   * @param request
   * @return
   */
  private LoadableStudyPortRotation createPortRotationEntity(
      LoadableStudyPortRotation entity, PortRotationDetail request) {
    entity.setAirDraftRestriction(
        isEmpty(request.getMaxAirDraft()) ? null : new BigDecimal(request.getMaxAirDraft()));
    entity.setBerthXId(0 == request.getBerthId() ? null : request.getBerthId());
    entity.setDistanceBetweenPorts(
        isEmpty(request.getDistanceBetweenPorts())
            ? null
            : new BigDecimal(request.getDistanceBetweenPorts()));
    entity.setMaxDraft(
        isEmpty(request.getMaxDraft()) ? null : new BigDecimal(request.getMaxDraft()));
    entity.setSeaWaterDensity(
        isEmpty(request.getSeaWaterDensity())
            ? null
            : new BigDecimal(request.getSeaWaterDensity()));
    entity.setTimeOfStay(
        isEmpty(request.getTimeOfStay()) ? null : new BigDecimal(request.getTimeOfStay()));
    entity.setEta(
        isEmpty(request.getEta())
            ? null
            : LocalDateTime.from(
                DateTimeFormatter.ofPattern(ETA_ETD_FROMAT).parse(request.getEta())));
    entity.setEtd(
        isEmpty(request.getEtd())
            ? null
            : LocalDateTime.from(
                DateTimeFormatter.ofPattern(ETA_ETD_FROMAT).parse(request.getEtd())));
    entity.setLayCanFrom(
        isEmpty(request.getLayCanFrom())
            ? null
            : LocalDate.from(
                DateTimeFormatter.ofPattern(LAY_CAN_FROMAT).parse(request.getLayCanFrom())));
    entity.setLayCanTo(
        isEmpty(request.getLayCanTo())
            ? null
            : LocalDate.from(
                DateTimeFormatter.ofPattern(LAY_CAN_FROMAT).parse(request.getLayCanTo())));
    entity.setOperation(this.cargoOperationRepository.getOne(request.getOperationId()));
    entity.setPortOrder(0 == request.getPortOrder() ? null : request.getPortOrder());
    return entity;
  }

  /** Delete specific cargo nomination */
  @Override
  public void deleteCargoNomination(
      CargoNominationRequest request, StreamObserver<CargoNominationReply> responseObserver) {
    CargoNominationReply.Builder cargoNominationReplyBuilder = CargoNominationReply.newBuilder();
    try {
      Optional<CargoNomination> existingCargoNomination =
          this.cargoNominationRepository.findById(request.getCargoNominationId());
      if (!existingCargoNomination.isPresent()) {
        throw new GenericServiceException(
            "Cargo Nomination does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      this.cargoNominationOperationDetailsRepository.deleteCargoNominationPortDetails(
          request.getCargoNominationId());
      this.cargoNominationRepository.deleteCargoNomination(request.getCargoNominationId());
      cargoNominationReplyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (Exception e) {
      log.error("Error deleting cargo nomination", e);
      cargoNominationReplyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(cargoNominationReplyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void deleteLoadableStudy(
      LoadableStudyRequest request, StreamObserver<LoadableStudyReply> responseObserver) {
    LoadableStudyReply.Builder replyBuilder = LoadableStudyReply.newBuilder();
    try {
      Optional<LoadableStudy> entityOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!entityOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      LoadableStudy entity = entityOpt.get();
      if (null != entity.getLoadableStudyStatus()
          && LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID.equals(
              entity.getLoadableStudyStatus().getId())) {
        throw new GenericServiceException(
            "Loadable study with status plan generated cannot be deleted",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      entity.setActive(false);
      this.loadableStudyRepository.save(entity);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study - port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when saving loadable study - port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving loadable study - port data")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void getPortRotationByLoadableStudyId(
      PortRotationRequest request, StreamObserver<PortRotationReply> responseObserver) {
    log.info("Inside getPortRotation loadable study micro service");
    PortRotationReply.Builder portRotationReplyBuilder = PortRotationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudy =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());

      if (!loadableStudy.isPresent()) {
        log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadableStudyId());
        portRotationReplyBuilder.setResponseStatus(
            ResponseStatus.newBuilder()
                .setStatus(FAILED)
                .setMessage(INVALID_LOADABLE_STUDY_ID)
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
      } else {

        List<LoadableStudyPortRotation> loadableStudyPortRotations =
            this.loadableStudyPortRoationRepository.findByLoadableStudyAndOperationAndIsActive(
                loadableStudy.get(), cargoOperationRepository.getOne(LOADING_OPERATION_ID), true);
        if (loadableStudyPortRotations.isEmpty()) {
          log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadableStudyId());
          portRotationReplyBuilder.setResponseStatus(
              ResponseStatus.newBuilder()
                  .setStatus(FAILED)
                  .setMessage(INVALID_LOADABLE_STUDY_ID)
                  .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
        } else {
          List<LoadableStudyPortRotation> loadingPorts =
              this.loadableStudyPortRoationRepository
                  .findByLoadableStudyAndOperationAndIsActiveOrderByPortOrder(
                      loadableStudy.get(),
                      cargoOperationRepository.getOne(LOADING_OPERATION_ID),
                      true);
          loadingPorts.forEach(
              loadingPort -> {
                PortRotationDetail.Builder builder = PortRotationDetail.newBuilder();
                builder.setPortId(loadingPort.getPortXId());
                portRotationReplyBuilder.addPorts(builder);
              });

          portRotationReplyBuilder
              .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
              .build();
        }
      }
    } catch (Exception e) {
      log.error("Error deleting cargo nomination", e);
      portRotationReplyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(FAILED)
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR));
    } finally {
      responseObserver.onNext(portRotationReplyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /** Delete port rotation by id */
  @Override
  public void deletePortRotation(
      PortRotationRequest request, StreamObserver<PortRotationReply> responseObserver) {
    PortRotationReply.Builder replyBuilder = PortRotationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent() || !loadableStudyOpt.get().isActive()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      LoadableStudy loadableStudy = loadableStudyOpt.get();
      if (null != loadableStudy.getLoadableStudyStatus()
          && LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID.equals(
              loadableStudy.getLoadableStudyStatus().getId())) {
        throw new GenericServiceException(
            "Cannot delete ports for loadable study with status - plan generated",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      Optional<LoadableStudyPortRotation> entityOpt =
          this.loadableStudyPortRoationRepository.findById(request.getId());
      if (!entityOpt.isPresent()) {
        throw new GenericServiceException(
            "port rotation does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      LoadableStudyPortRotation entity = entityOpt.get();
      if (null != entity.getOperation()
          && (LOADING_OPERATION_ID.equals(entity.getOperation().getId())
              || DISCHARGING_OPERATION_ID.equals(entity.getOperation().getId()))) {
        throw new GenericServiceException(
            "Cannot delete loading/discharging ports",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      entity.setActive(false);
      this.loadableStudyPortRoationRepository.save(entity);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when deleting port rotation", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when deleting port rotation", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when deleting port rotation")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }
}
