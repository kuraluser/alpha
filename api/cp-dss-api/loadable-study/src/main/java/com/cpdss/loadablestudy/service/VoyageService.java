/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.util.Optional.ofNullable;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfoServiceGrpc;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.CargoHistory;
import com.cpdss.loadablestudy.domain.VoyageDto;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.ApiTempHistoryRepository;
import com.cpdss.loadablestudy.repository.CargoHistoryRepository;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternCargoDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternCargoToppingOffSequenceRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyAlgoStatusRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.VoyageHistoryRepository;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import com.cpdss.loadablestudy.repository.VoyageStatusRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Master Service for Voyage Related Operations
 *
 * @author Johnsooraj.x
 */
@Slf4j
@Service
public class VoyageService {

  @Autowired private VoyageRepository voyageRepository;

  @Autowired private LoadablePatternRepository loadablePatternRepository;

  @Autowired private VoyageStatusRepository voyageStatusRepository;

  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired private VoyageHistoryRepository voyageHistoryRepository;

  @Autowired private CargoHistoryRepository cargoHistoryRepository;

  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private CargoNominationRepository cargoNominationRepository;

  @Autowired private ApiTempHistoryRepository apiTempHistoryRepository;

  @Autowired private LoadablePatternCargoToppingOffSequenceRepository toppingOffSequenceRepository;

  @Autowired private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;

  @Autowired private SynopticService synopticService;

  @Autowired private CargoNominationService cargoNominationService;

  @Value("${loadablestudy.voyage.day.difference}")
  private String dayDifference;

  @GrpcClient("loadingPlanService")
  private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @GrpcClient("cargoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

  /**
   * Find the voyage which have a status 3 Also, collect the LS details and Port Rotation Details
   *
   * @param builder GRPC Response
   * @param vesselId Long id
   * @param activeStatus Long Number - 3
   */
  public void fetchActiveVoyageByVesselId(
      LoadableStudy.ActiveVoyage.Builder builder, Long vesselId, Long activeStatus) {
    List<Voyage> activeVoyage =
        this.voyageRepository.findActiveVoyagesByVesselId(activeStatus, vesselId);
    if (activeVoyage != null && !activeVoyage.isEmpty()) {
      Voyage voyage = activeVoyage.stream().findFirst().get();

      // Set Voyage related properties
      Optional.ofNullable(voyage.getId()).ifPresent(builder::setId);
      Optional.ofNullable(voyage.getVoyageNo()).ifPresent(builder::setVoyageNumber);
      Optional.ofNullable(voyage.getActualEndDate())
          .ifPresent(v -> builder.setActualEndDate(formatter.format(v)));
      Optional.ofNullable(voyage.getActualStartDate())
          .ifPresent(v -> builder.setActualStartDate(formatter.format(v)));
      Optional.ofNullable(voyage.getVoyageStartDate())
          .ifPresent(startDate -> builder.setStartDate(formatter.format(startDate)));
      Optional.ofNullable(voyage.getVoyageEndDate())
          .ifPresent(endDate -> builder.setEndDate(formatter.format(endDate)));
      builder.setStatus(voyage.getVoyageStatus() != null ? voyage.getVoyageStatus().getName() : "");
      Optional.ofNullable(voyage.getVoyageStatus())
          .ifPresent(status -> builder.setStatusId(status.getId()));

      log.info(
          "Get Active voyage, for vessel {}, Voyage No {}, Id {}",
          vesselId,
          voyage.getVoyageNo(),
          voyage.getId());
      // set confirmed loadable study
      if (voyage.getVoyageStatus() != null
          && (STATUS_ACTIVE.equalsIgnoreCase(voyage.getVoyageStatus().getName())
              || STATUS_CLOSE.equalsIgnoreCase(voyage.getVoyageStatus().getName()))) {

        Optional<com.cpdss.loadablestudy.entity.LoadableStudy> confirmedLs =
            voyage.getLoadableStudies().stream()
                .filter(
                    ls ->
                        (ls.getLoadableStudyStatus() != null
                            && STATUS_CONFIRMED.equalsIgnoreCase(
                                ls.getLoadableStudyStatus().getName())
                            && ls.getPlanningTypeXId().equals(PLANNING_TYPE_LOADING)))
                .findFirst();

        Optional<com.cpdss.loadablestudy.entity.LoadableStudy> confirmedDs =
            voyage.getLoadableStudies().stream()
                .filter(
                    ds ->
                        (ds.getLoadableStudyStatus() != null
                            && STATUS_CONFIRMED.equalsIgnoreCase(
                                ds.getLoadableStudyStatus().getName())
                            && ds.getPlanningTypeXId().equals(PLANNING_TYPE_DISCHARGE)))
                .findFirst();

        if (confirmedLs.isPresent()) {
          LoadableStudy.LoadableStudyDetail.Builder lsBuilder =
              LoadableStudy.LoadableStudyDetail.newBuilder();
          this.buildLoadableStudyForVoyage(lsBuilder, confirmedLs.get());
          builder.setConfirmedLoadableStudy(lsBuilder);
          if (!confirmedLs.get().getPortRotations().isEmpty()) {
            for (LoadableStudyPortRotation lsPr : confirmedLs.get().getPortRotations()) {
              LoadableStudy.PortRotationDetail.Builder grpcPRBuilder =
                  LoadableStudy.PortRotationDetail.newBuilder();
              this.buildPortRotationForLs(grpcPRBuilder, lsPr);
              builder.addPortRotation(grpcPRBuilder.build());
              log.info(
                  "Get Active voyage, Loadable Study Name {}, Id {}",
                  confirmedLs.get().getName(),
                  confirmedLs.get().getId());
              List<LoadablePattern> patterns =
                  loadablePatternRepository.findConfirmedPatternByLoadableStudyId(
                      confirmedLs.get().getId(), LS_STATUS_CONFIRMED);
              if (!patterns.isEmpty()) {
                Long id = patterns.stream().findFirst().get().getId();
                builder.setPatternId(patterns.stream().findFirst().get().getId());
                builder.setPatternCaseNo(patterns.stream().findFirst().get().getCaseNumber());
                builder.setPatternStatus(
                    patterns.stream().findFirst().get().getLoadableStudyStatus());
                log.info(
                    "Get Active voyage, Confirmed Pattern Id {}, Case No {}, Status",
                    patterns.stream().findFirst().get().getId(),
                    patterns.stream().findFirst().get().getCaseNumber(),
                    patterns.stream().findFirst().get().getLoadableStudyStatus());
              } else {
                log.info("Confirmed pattern not found for LS Id - {}", confirmedLs.get().getId());
              }
            }
          }
        }

        if (confirmedDs.isPresent()) {
          LoadableStudy.LoadableStudyDetail.Builder dsBuilder =
              LoadableStudy.LoadableStudyDetail.newBuilder();
          this.buildLoadableStudyForVoyage(dsBuilder, confirmedDs.get());
          builder.setConfirmedDischargeStudy(dsBuilder);
          List<CargoNomination> cargoNominations =
              cargoNominationService.getCargoNominations(confirmedDs.get().getId());
          if (!cargoNominations.isEmpty()) {
            cargoNominations.stream()
                .forEach(
                    nomination -> {
                      CargoNominationDetail.Builder dsCargo = CargoNominationDetail.newBuilder();
                      dsCargo.setId(nomination.getId());
                      dsCargo.setApi(nomination.getApi().toString());
                      dsCargo.setColor(nomination.getColor());
                      dsCargo.setQuantity(nomination.getQuantity().toString());
                      dsCargo.setTemperature(nomination.getTemperature().toString());
                      builder.addDischargeCargoNomination(dsCargo);
                    });
          }

          if (!confirmedDs.get().getPortRotations().isEmpty()) {
            for (LoadableStudyPortRotation dsPr : confirmedDs.get().getPortRotations()) {
              LoadableStudy.PortRotationDetail.Builder grpcPRBuilder =
                  LoadableStudy.PortRotationDetail.newBuilder();
              this.buildPortRotationForLs(grpcPRBuilder, dsPr);
              builder.addDischargePortRotation(grpcPRBuilder.build());
              log.info(
                  "Get Active voyage, Discharge Study Name {}, Id {}",
                  confirmedDs.get().getName(),
                  confirmedDs.get().getId());
              List<LoadablePattern> patterns =
                  loadablePatternRepository.findConfirmedPatternByLoadableStudyId(
                      confirmedDs.get().getId(), LS_STATUS_CONFIRMED);
              if (!patterns.isEmpty()) {
                Long id = patterns.stream().findFirst().get().getId();
                builder.setDischargePatternId(patterns.stream().findFirst().get().getId());
                builder.setDischargePatternCaseNo(
                    patterns.stream().findFirst().get().getCaseNumber());
                builder.setDischargePatternStatus(
                    patterns.stream().findFirst().get().getLoadableStudyStatus());
                log.info(
                    "Get Active voyage, Confirmed Pattern Id {}, Case No {}, Status",
                    patterns.stream().findFirst().get().getId(),
                    patterns.stream().findFirst().get().getCaseNumber(),
                    patterns.stream().findFirst().get().getLoadableStudyStatus());
              } else {
                log.info("Confirmed pattern not found for DS Id - {}", confirmedDs.get().getId());
              }
            }
          }
        }
      }
      log.info(
          "Active voyage for vessel {}, voyage no {}, status {}",
          vesselId,
          voyage.getVoyageNo(),
          voyage.getVoyageStatus().getId());
    }
  }

  // Few data added for now
  private void buildLoadableStudyForVoyage(
      LoadableStudy.LoadableStudyDetail.Builder builder,
      com.cpdss.loadablestudy.entity.LoadableStudy entity) {
    Optional.ofNullable(entity.getId()).ifPresent(builder::setId);
    Optional.ofNullable(entity.getName()).ifPresent(builder::setName);
    Optional.ofNullable(entity.getLoadableStudyStatus().getId()).ifPresent(builder::setStatusId);
    Optional.ofNullable(entity.getDraftMark())
        .ifPresent(v -> builder.setDraftMark(String.valueOf(v)));
    Optional.ofNullable(entity.getCreatedDateTime())
        .ifPresent(v -> builder.setCreatedDate(formatter.format(v)));
  }

  // Few data added for now
  private void buildPortRotationForLs(
      LoadableStudy.PortRotationDetail.Builder builder, LoadableStudyPortRotation entity) {
    Optional.ofNullable(entity.getId()).ifPresent(builder::setId);
    Optional.ofNullable(entity.getPortXId()).ifPresent(builder::setPortId);
    Optional.ofNullable(entity.getPortOrder()).ifPresent(builder::setPortOrder);
    Optional.ofNullable(entity.getBerthXId()).ifPresent(builder::setBerthId);
    Optional.ofNullable(entity.getOperation().getId()).ifPresent(builder::setOperationId);
    Optional.ofNullable(entity.getSeaWaterDensity())
        .ifPresent(v -> builder.setSeaWaterDensity(String.valueOf(v)));
  }

  public void checkIfVoyageClosed(Long voyageId) throws GenericServiceException {
    Voyage voyage = this.voyageRepository.findByIdAndIsActive(voyageId, true);
    if (null != voyage
        && null != voyage.getVoyageStatus()
        && voyage.getVoyageStatus().getId().equals(CLOSE_VOYAGE_STATUS)) {
      throw new GenericServiceException(
          "Save /Edit /Duplicate/Delete operations  not allowed for closed voyage",
          CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * Save Voyage
   *
   * @param request
   * @param builder
   * @return
   */
  LoadableStudy.VoyageReply.Builder saveVoyage(
      LoadableStudy.VoyageRequest request, LoadableStudy.VoyageReply.Builder builder) {
    // validation for duplicate voyages
    if (!voyageRepository
        .findByCompanyXIdAndVesselXIdAndVoyageNoIgnoreCase(
            request.getCompanyId(), request.getVesselId(), request.getVoyageNo())
        .isEmpty()) {
      builder
          .setResponseStatus(
              LoadableStudy.StatusReply.newBuilder()
                  .setStatus(FAILED)
                  .setMessage(VOYAGE_EXISTS)
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
      voyage.setVoyageStartDate(
          !StringUtils.isEmpty(request.getStartDate())
              ? LocalDateTime.from(
                  DateTimeFormatter.ofPattern(DATE_FORMAT).parse(request.getStartDate()))
              : null);
      voyage.setVoyageEndDate(
          !StringUtils.isEmpty(request.getEndDate())
              ? LocalDateTime.from(
                  DateTimeFormatter.ofPattern(DATE_FORMAT).parse(request.getEndDate()))
              : null);
      voyage.setStartTimezoneId((long) request.getStartTimezoneId());
      voyage.setEndTimezoneId((long) request.getEndTimezoneId());
      voyage.setVoyageStatus(this.voyageStatusRepository.getOne(OPEN_VOYAGE_STATUS));
      voyage = voyageRepository.save(voyage);
      // when Db save is complete we return to client a success message
      builder
          .setResponseStatus(
              LoadableStudy.StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
          .setVoyageId(voyage.getId())
          .build();
    }
    return builder;
  }

  public LoadableStudy.VoyageListReply.Builder getVoyagesByVessel(
      LoadableStudy.VoyageRequest request, LoadableStudy.VoyageListReply.Builder builder) {

    List<Voyage> entityList =
        this.voyageRepository.findByVesselXIdAndIsActiveOrderByIdDesc(request.getVesselId(), true);
    for (Voyage entity : entityList) {
      LoadableStudy.VoyageDetail.Builder detailbuilder = LoadableStudy.VoyageDetail.newBuilder();
      detailbuilder.setId(entity.getId());
      detailbuilder.setVoyageNumber(entity.getVoyageNo());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      Optional.ofNullable(entity.getActualEndDate())
          .ifPresent(
              actualEndDate -> detailbuilder.setActualEndDate(formatter.format(actualEndDate)));
      Optional.ofNullable(entity.getActualStartDate())
          .ifPresent(
              actualStartDate ->
                  detailbuilder.setActualStartDate(formatter.format(actualStartDate)));
      Optional.ofNullable(entity.getVoyageStartDate())
          .ifPresent(startDate -> detailbuilder.setStartDate(formatter.format(startDate)));
      ofNullable(entity.getVoyageEndDate())
          .ifPresent(endDate -> detailbuilder.setEndDate(formatter.format(endDate)));
      detailbuilder.setStatus(
          entity.getVoyageStatus() != null ? entity.getVoyageStatus().getName() : "");
      Optional.ofNullable(entity.getVoyageStatus())
          .ifPresent(status -> detailbuilder.setStatusId(status.getId()));

      // fetch the confirmed loadable/discharge study for active voyages
      if (entity.getVoyageStatus() != null
          && (STATUS_ACTIVE.equalsIgnoreCase(entity.getVoyageStatus().getName())
              || STATUS_CLOSE.equalsIgnoreCase(entity.getVoyageStatus().getName()))) {
        Optional<com.cpdss.loadablestudy.entity.LoadableStudy> confirmedStudy = Optional.empty();
        // Checking if Discharge started or not
        Optional<List<com.cpdss.loadablestudy.entity.LoadableStudy>> dischargeStudyEntries =
            synopticService.checkDischargeStarted(entity.getVesselXId(), entity.getId());
        if (dischargeStudyEntries.isPresent()) {
          detailbuilder.setIsDischargeStarted(true);
          confirmedStudy = getConfirmedStudy(entity, PLANNING_TYPE_DISCHARGE);
          if (confirmedStudy.isPresent()) {
            detailbuilder.setConfirmedDischargeStudyId(confirmedStudy.get().getId());
            //            Long noOfDays = this.getNumberOfDays(confirmedStudy.get());
            //            Optional.ofNullable(noOfDays).ifPresent(item ->
            // detailbuilder.setNoOfDays(item));
          }
        } else {
          detailbuilder.setIsDischargeStarted(false);
        }
        confirmedStudy = getConfirmedStudy(entity, PLANNING_TYPE_LOADING);
        if (confirmedStudy.isPresent()) {
          detailbuilder.setConfirmedLoadableStudyId(confirmedStudy.get().getId());
          Long noOfDays = this.getNumberOfDays(confirmedStudy.get());
          Optional.ofNullable(noOfDays).ifPresent(item -> detailbuilder.setNoOfDays(item));
        }
      }
      builder.addVoyages(detailbuilder.build());
    }
    builder.setResponseStatus(LoadableStudy.StatusReply.newBuilder().setStatus(SUCCESS).build());
    return builder;
  }

  private Optional<com.cpdss.loadablestudy.entity.LoadableStudy> getConfirmedStudy(
      Voyage entity, Integer planningType) {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> confirmedStudy = Optional.empty();
    Stream<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyStream =
        ofNullable(entity.getLoadableStudies()).map(Collection::stream).orElseGet(Stream::empty);

    confirmedStudy =
        loadableStudyStream
            .filter(
                loadableStudyElement ->
                    loadableStudyElement.getPlanningTypeXId() != null
                        && loadableStudyElement.getPlanningTypeXId().equals(planningType))
            .filter(
                loadableStudyElement ->
                    (loadableStudyElement.getLoadableStudyStatus() != null
                        && STATUS_CONFIRMED.equalsIgnoreCase(
                            loadableStudyElement.getLoadableStudyStatus().getName())))
            .findFirst();
    return confirmedStudy;
  }

  private Long getNumberOfDays(com.cpdss.loadablestudy.entity.LoadableStudy entity) {
    LoadableStudyPortRotation lastPort =
        this.loadableStudyPortRotationRepository
            .findFirstByLoadableStudyAndIsActiveOrderByPortOrderDesc(entity, true);
    Long daysBetween = null;

    if (null != lastPort && null != lastPort.getEtd()) {

      daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), lastPort.getEtd().toLocalDate());
    }
    if (null != daysBetween && daysBetween <= Long.parseLong(dayDifference)) {
      return daysBetween;
    }
    return null;
  }

  /**
   * find voyage history for previous voyage
   *
   * @return
   */
  public List<CargoHistory> findCargoHistoryForPrvsVoyage(Voyage voyage) {
    if (voyage.getVoyageStartDate() != null && voyage.getVoyageEndDate() != null) {

      VoyageStatus voyageStatus = this.voyageStatusRepository.getOne(CLOSE_VOYAGE_STATUS);

      Voyage previousVoyage =
          this.voyageRepository
              .findFirstByVesselXIdAndIsActiveAndVoyageStatusOrderByLastModifiedDateDesc(
                  voyage.getVesselXId(), true, voyageStatus);
      if (null == previousVoyage) {
        log.error("Could not find previous voyage of {}", voyage.getVoyageNo());
      } else {
        VoyageHistory voyageHistory =
            this.voyageHistoryRepository.findFirstByVoyageOrderByPortOrderDesc(previousVoyage);
        if (null == voyageHistory) {
          log.error("Could not find voyage history for voyage: {}", previousVoyage.getVoyageNo());
        } else {
          return this.cargoHistoryRepository.findCargoHistory(
              previousVoyage.getId(), voyageHistory.getLoadingPortId());
        }
      }
    } else {
      log.error(
          "Voyage start/end date for voyage {} not set and hence, cargo history cannot be fetched",
          voyage.getVoyageNo());
    }
    return new ArrayList<>();
  }

  void checkIfVoyageActive(Long voyageId) throws GenericServiceException {
    Voyage voyage = this.voyageRepository.findByIdAndIsActive(voyageId, true);
    if (null != voyage
        && null != voyage.getVoyageStatus()
        && voyage.getVoyageStatus().getId().equals(ACTIVE_VOYAGE_STATUS)) {
      throw new GenericServiceException(
          "Save not allowed for active voyage",
          CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public LoadableStudy.SaveVoyageStatusReply.Builder saveVoyageStatus(
      LoadableStudy.SaveVoyageStatusRequest request,
      LoadableStudy.SaveVoyageStatusReply.Builder replyBuilder)
      throws GenericServiceException {
    Voyage voyageEntity = this.voyageRepository.findByIdAndIsActive(request.getVoyageId(), true);

    if (null == voyageEntity) {
      throw new GenericServiceException(
          " Voyage does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    Stream<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyStream =
        Optional.ofNullable(voyageEntity.getLoadableStudies())
            .map(Collection::stream)
            .orElseGet(Stream::empty);
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudy =
        loadableStudyStream
            .filter(
                loadableStudyElement ->
                    (loadableStudyElement.getLoadableStudyStatus() != null
                        && STATUS_CONFIRMED.equalsIgnoreCase(
                            loadableStudyElement.getLoadableStudyStatus().getName())))
            .findFirst();

    if (request.getStatus().equalsIgnoreCase(START_VOYAGE)) {
      List<Voyage> activeVoyage =
          this.voyageRepository.findByVoyageStatusAndIsActive(ACTIVE_VOYAGE_STATUS, true);
      if (!activeVoyage.isEmpty()) {
        throw new GenericServiceException(
            "Active Voyage already exist",
            CommonErrorCodes.E_CPDSS_ACTIVE_VOYAGE_EXISTS,
            HttpStatusCode.BAD_REQUEST);
      }
      if (!loadableStudy.isPresent()) {
        throw new GenericServiceException(
            "Confirmed Loadable study does not exist",
            CommonErrorCodes.E_CPDSS_CONFIRMED_LS_DOES_NOT_EXIST,
            HttpStatusCode.BAD_REQUEST);
      }
      Optional<VoyageStatus> status =
          this.voyageStatusRepository.findByIdAndIsActive(ACTIVE_VOYAGE_STATUS, true);
      if (!status.isPresent()) {
        throw new GenericServiceException(
            "Voyage status does not  exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      voyageEntity.setVoyageStatus(status.get());

      if (request.getActualStartDate().isEmpty()) {
        LoadableStudyPortRotation minPortOrderEntity =
            this.loadableStudyPortRotationRepository
                .findFirstByLoadableStudyAndIsActiveOrderByPortOrderAsc(loadableStudy.get(), true);

        if (null != minPortOrderEntity) {
          List<SynopticalTable> synopticalData = minPortOrderEntity.getSynopticalTable();
          if (!synopticalData.isEmpty()) {
            Optional<SynopticalTable> synoptical =
                synopticalData.stream()
                    .filter(
                        data ->
                            data.getLoadableStudyPortRotation()
                                    .getId()
                                    .equals(minPortOrderEntity.getId())
                                && SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(data.getOperationType()))
                    .findAny();
            if (synoptical.isPresent()) {
              voyageEntity.setActualStartDate(synoptical.get().getEtaActual());
            }
          }
        }
      } else {
        voyageEntity.setActualStartDate(
            !StringUtils.isEmpty(request.getActualStartDate())
                ? LocalDateTime.from(
                    DateTimeFormatter.ofPattern(DATE_FORMAT).parse(request.getActualStartDate()))
                : null);
      }
      // Synchronizing with Loading Plan Microservice
      loadableStudy.get().getPortRotations().stream()
          .filter(portRotation -> portRotation.isActive())
          .forEach(
              portRotation -> {
                LoadingPlanModels.LoadingPlanSyncDetails.Builder builder =
                    LoadingPlanModels.LoadingPlanSyncDetails.newBuilder();
                Optional<LoadablePattern> confirmedLoadablePatternOpt =
                    this.loadablePatternRepository
                        .findByLoadableStudyAndLoadableStudyStatusAndIsActive(
                            loadableStudy.get(), CONFIRMED_STATUS_ID, true);
                if (confirmedLoadablePatternOpt.isPresent()) {
                  buildLoadingPlanSyncDetails(
                      builder,
                      confirmedLoadablePatternOpt.get(),
                      portRotation,
                      request.getVoyageId());
                  // LoadablePlanDetailsReply.Builder planDetailsReplyBuilder =
                  // LoadablePlanDetailsReply.newBuilder();
                  // try {
                  // buildLoadablePlanDetails(
                  // confirmedLoadablePatternOpt,
                  // planDetailsReplyBuilder);
                  //
                  // builder.setLoadablePlanDetailsReply(planDetailsReplyBuilder);
                  // } catch (GenericServiceException e) {
                  // log.error(
                  // "Could not build loadable plan details for loading
                  // pattern "
                  // + confirmedLoadablePatternOpt.get().getId());
                  // e.printStackTrace();
                  // }
                }
                LoadingPlanModels.LoadingPlanSyncReply loadablePlanSyncReply =
                    this.loadingPlanSynchronization(builder.build());
                if (loadablePlanSyncReply.getResponseStatus().getStatus().equals(SUCCESS)) {
                  log.info(
                      "Loading plan synchronization successful for loadable pattern "
                          + confirmedLoadablePatternOpt.get().getId());
                } else {
                  log.error(
                      "Loading plan synchronization failed for loadable pattern "
                          + confirmedLoadablePatternOpt.get().getId());
                }
              });
    } else {
      Optional<VoyageStatus> status =
          this.voyageStatusRepository.findByIdAndIsActive(CLOSE_VOYAGE_STATUS, true);
      if (!status.isPresent()) {
        throw new GenericServiceException(
            "Voyage status does not  exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      voyageEntity.setVoyageStatus(status.get());

      if (request.getActualEndDate().isEmpty()) {
        LoadableStudyPortRotation maxPortOrderEntity =
            this.loadableStudyPortRotationRepository
                .findFirstByLoadableStudyAndIsActiveOrderByPortOrderDesc(loadableStudy.get(), true);
        if (maxPortOrderEntity != null) {
          List<SynopticalTable> synopticalData = maxPortOrderEntity.getSynopticalTable();
          if (!synopticalData.isEmpty()) {
            Optional<SynopticalTable> synoptical =
                synopticalData.stream()
                    .filter(
                        data ->
                            data.getLoadableStudyPortRotation()
                                    .getId()
                                    .equals(maxPortOrderEntity.getId())
                                && SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE.equals(
                                    data.getOperationType()))
                    .findAny();

            if (synoptical.isPresent()) {
              voyageEntity.setActualEndDate(synoptical.get().getEtdActual());
            }
          }
        }
      } else {
        voyageEntity.setActualEndDate(
            !StringUtils.isEmpty(request.getActualEndDate())
                ? LocalDateTime.from(
                    DateTimeFormatter.ofPattern(DATE_FORMAT).parse(request.getActualEndDate()))
                : null);
      }
    }
    this.voyageRepository.save(voyageEntity);
    try {
      this.updateApiTempWithCargoNominations(voyageEntity);
    } catch (Exception e) {
      log.info("Voyage Close, update api-temp - Failed {} - {}", e.getMessage(), e);
    }
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  private void buildLoadingPlanSyncDetails(
      LoadingPlanModels.LoadingPlanSyncDetails.Builder builder,
      LoadablePattern loadablePattern,
      LoadableStudyPortRotation portRotation,
      Long voyageId) {
    buildLoadingInformationDetails(builder, loadablePattern, portRotation, voyageId);
    buildCargoToppingOffSequence(builder, loadablePattern, portRotation);
  }

  private LoadingPlanModels.LoadingPlanSyncReply loadingPlanSynchronization(
      LoadingPlanModels.LoadingPlanSyncDetails request) {
    return this.loadingPlanService.loadingPlanSynchronization(request);
  }

  /**
   * This operation can work async, Data is adding to Api-History Table
   *
   * <p>Data means, the active loadable-pattern (cargo, port, api, temp etc)
   *
   * @param voyage -
   */
  public void updateApiTempWithCargoNominations(Voyage voyage) {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudy =
        loadableStudyRepository.findByVoyageAndLoadableStudyStatusAndIsActiveAndPlanningTypeXId(
            voyage, CONFIRMED_STATUS_ID, true, Common.PLANNING_TYPE.LOADABLE_STUDY_VALUE);
    if (loadableStudy.isPresent()) {
      log.info("Voyage Close, update api-temp - ls id {}", loadableStudy.get().getId());
      Optional<LoadablePattern> pattern =
          loadablePatternRepository.findByLoadableStudyAndLoadableStudyStatusAndIsActive(
              loadableStudy.get(), CONFIRMED_STATUS_ID, true);
      if (pattern.isPresent()) {
        log.info("Voyage Close, update api-temp - lp id {}", pattern.get().getId());
        List<CargoNomination> nominations =
            cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                loadableStudy.get().getId(), true);
        log.info("Voyage Close, cargo nomination size - {}", nominations.size());
        for (CargoNomination nomi : nominations) {
          for (CargoNominationPortDetails cnPd : nomi.getCargoNominationPortDetails()) {
            ApiTempHistory apiHis =
                this.buildApiTempHistoryByPortId(voyage.getVesselXId(), nomi, cnPd.getPortId());
            this.saveApiTempWithPortDetails(apiHis);
          }
        }
      }
    }
  }

  private void buildLoadingInformationDetails(
      LoadingPlanModels.LoadingPlanSyncDetails.Builder builder,
      LoadablePattern loadablePattern,
      LoadableStudyPortRotation portRotation,
      Long voyageId) {
    builder.getLoadingInformationDetailBuilder().setLoadablePatternId(loadablePattern.getId());
    builder.getLoadingInformationDetailBuilder().setPortId(portRotation.getPortXId());
    builder.getLoadingInformationDetailBuilder().setVoyageId(voyageId);
    List<LoadableStudyAlgoStatus> algoStatuses =
        loadableStudyAlgoStatusRepository.findByLoadableStudyIdAndIsActive(
            loadablePattern.getLoadableStudy().getId(), true);
    Optional<LoadableStudyAlgoStatus> latestStatusOpt =
        algoStatuses.stream()
            .sorted(Comparator.comparing(LoadableStudyAlgoStatus::getCreatedDateTime).reversed())
            .findFirst();
    latestStatusOpt.ifPresent(
        status ->
            builder
                .getLoadingInformationDetailBuilder()
                .setLoadableStudyProcessId(status.getProcessId()));
    Optional<SynopticalTable> synopticalTableOpt =
        portRotation.getSynopticalTable().stream()
            .filter(
                synopticalTable ->
                    synopticalTable.getIsActive()
                        && synopticalTable
                            .getOperationType()
                            .equalsIgnoreCase(SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL))
            .findFirst();
    if (synopticalTableOpt.isPresent()) {
      builder
          .getLoadingInformationDetailBuilder()
          .setSynopticalTableId(synopticalTableOpt.get().getId());
      builder.getLoadingInformationDetailBuilder().setPortRotationId(portRotation.getId());
    }
    builder
        .getLoadingInformationDetailBuilder()
        .setVesselId(loadablePattern.getLoadableStudy().getVesselXId());
  }

  /** Fetch the api and temp history for cargo and port ids if available */
  private void buildCargoToppingOffSequence(
      LoadingPlanModels.LoadingPlanSyncDetails.Builder builder,
      LoadablePattern loadablePattern,
      LoadableStudyPortRotation portRotation) {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> loadablePatternCargoDetails =
        this.loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
            loadablePattern.getId(), true);
    this.toppingOffSequenceRepository.findByLoadablePatternAndIsActive(loadablePattern, true)
        .stream()
        .forEach(
            toppingSequence -> {
              LoadingPlanModels.CargoToppingOffSequence.Builder sequenceBuilder =
                  LoadingPlanModels.CargoToppingOffSequence.newBuilder();
              Optional.ofNullable(toppingSequence.getCargoXId())
                  .ifPresent(sequenceBuilder::setCargoXId);
              Optional.ofNullable(toppingSequence.getLoadablePattern().getId())
                  .ifPresent(sequenceBuilder::setLoadablePatternId);
              Optional.ofNullable(toppingSequence.getOrderNumber())
                  .ifPresent(sequenceBuilder::setOrderNumber);
              Optional.ofNullable(toppingSequence.getTankXId())
                  .ifPresent(sequenceBuilder::setTankXId);
              Optional<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> cargoDetailOpt =
                  loadablePatternCargoDetails.stream()
                      .filter(
                          details ->
                              details.getPortRotationId().equals(portRotation.getId())
                                  && details.getTankId().equals(sequenceBuilder.getTankXId()))
                      .findAny();
              if (cargoDetailOpt.isPresent()) {
                Optional.ofNullable(cargoDetailOpt.get().getApi())
                    .ifPresent(api -> sequenceBuilder.setApi(String.valueOf(api)));
                Optional.ofNullable(cargoDetailOpt.get().getTemperature())
                    .ifPresent(
                        temperature -> sequenceBuilder.setTemperature(String.valueOf(temperature)));
                Optional.ofNullable(cargoDetailOpt.get().getCorrectedUllage())
                    .ifPresent(ullage -> sequenceBuilder.setUllage(String.valueOf(ullage)));
                Optional.ofNullable(cargoDetailOpt.get().getPlannedQuantity())
                    .ifPresent(weight -> sequenceBuilder.setWeight(String.valueOf(weight)));
                Optional.ofNullable(cargoDetailOpt.get().getFillingRatio())
                    .ifPresent(
                        fillingRatio ->
                            sequenceBuilder.setFillingRatio(String.valueOf(fillingRatio)));
              }
              Optional.ofNullable(toppingSequence.getDisplayOrder())
                  .ifPresent(sequenceBuilder::setDisplayOrder);
              Optional.ofNullable(toppingSequence.getPortRotationXId())
                  .ifPresent(sequenceBuilder::setPortRotationId);
              builder.addCargoToppingOffSequences(sequenceBuilder.build());
            });
  }

  public void saveApiTempWithPortDetails(ApiTempHistory apiTempHistory) {
    apiTempHistoryRepository.save(apiTempHistory);
    log.info("Voyage Close, new api-history - {}", apiTempHistory.getId());
  }

  private ApiTempHistory buildApiTempHistoryByPortId(
      Long vesselId, CargoNomination cargoNomination, Long portId) {
    return ApiTempHistory.builder()
        .vesselId(vesselId)
        .cargoId(cargoNomination.getCargoXId())
        .loadingPortId(portId)
        .loadedDate(cargoNomination.getLastModifiedDateTime())
        .year(cargoNomination.getLastModifiedDateTime().getYear())
        .month(cargoNomination.getLastModifiedDateTime().getMonth().getValue())
        .date(cargoNomination.getLastModifiedDateTime().getDayOfMonth())
        .api(cargoNomination.getApi())
        .isActive(true)
        .temp(cargoNomination.getTemperature())
        .build();
  }

  public LoadableStudy.VoyageListReply.Builder getVoyages(
      LoadableStudy.VoyageRequest request, LoadableStudy.VoyageListReply.Builder builder)
      throws GenericServiceException {
    PortInfo.PortRequest.Builder portReqBuilder = PortInfo.PortRequest.newBuilder();
    PortInfo.PortReply portReply = this.GetPortInfo(portReqBuilder.build());

    if (portReply != null
        && portReply.getResponseStatus() != null
        && !SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling port service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }

    CargoInfo.CargoRequest cargoRequest = CargoInfo.CargoRequest.newBuilder().build();
    CargoInfo.CargoReply cargoReply = this.getCargoInfo(cargoRequest);
    if (!SUCCESS.equalsIgnoreCase(cargoReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    List<Voyage> entityList = null;

    // apply date filter for actual start date
    if (!request.getFromStartDate().isEmpty() && !request.getToStartDate().isEmpty()) {
      LocalDate from =
          LocalDate.from(
              DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT).parse(request.getFromStartDate()));
      LocalDate to =
          LocalDate.from(
              DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT).parse(request.getToStartDate()));
      entityList =
          voyageRepository.findByIsActiveAndVesselXIdAndActualStartDateBetween(
              true, request.getVesselId(), from, to);

    } else {
      entityList =
          voyageRepository
              .findByIsActiveAndVesselXIdOrderByVoyageStatusDescAndLastModifiedDateTimeDesc(
                  true, request.getVesselId());
      entityList = entityList.stream().distinct().collect(Collectors.toList());
    }
    for (Voyage entity : entityList) {
      LoadableStudy.VoyageDetail.Builder detailbuilder = LoadableStudy.VoyageDetail.newBuilder();
      detailbuilder.setId(entity.getId());
      detailbuilder.setVoyageNumber(entity.getVoyageNo());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      ofNullable(entity.getVoyageStartDate())
          .ifPresent(startDate -> detailbuilder.setStartDate(formatter.format(startDate)));
      ofNullable(entity.getVoyageEndDate())
          .ifPresent(endDate -> detailbuilder.setEndDate(formatter.format(endDate)));
      ofNullable(entity.getActualStartDate())
          .ifPresent(startDate -> detailbuilder.setActualStartDate(formatter.format(startDate)));
      ofNullable(entity.getActualEndDate())
          .ifPresent(endDate -> detailbuilder.setActualEndDate(formatter.format(endDate)));
      detailbuilder.setStatus(
          entity.getVoyageStatus() != null ? entity.getVoyageStatus().getName() : "");

      // fetch the confirmed loadable study for active voyages

      Stream<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyStream =
          Optional.ofNullable(entity.getLoadableStudies())
              .map(Collection::stream)
              .orElseGet(Stream::empty);
      Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudy =
          loadableStudyStream
              .filter(
                  loadableStudyElement ->
                      (loadableStudyElement.getLoadableStudyStatus() != null
                          && STATUS_CONFIRMED.equalsIgnoreCase(
                              loadableStudyElement.getLoadableStudyStatus().getName())))
              .findFirst();
      if (loadableStudy.isPresent()) {

        detailbuilder.setConfirmedLoadableStudyId(loadableStudy.get().getId());
        List<Long> loadingPorts =
            this.loadableStudyPortRotationRepository.getLoadingPorts(loadableStudy.get()).stream()
                .distinct()
                .collect(Collectors.toList());

        portReply.getPortsList().stream()
            .filter(port -> loadingPorts.contains(port.getId()))
            .forEach(
                loadingPort -> {
                  LoadableStudy.LoadingPortDetail.Builder loadingPortDetail =
                      LoadableStudy.LoadingPortDetail.newBuilder();
                  loadingPortDetail.setName(loadingPort.getName());
                  loadingPortDetail.setPortId(loadingPort.getId());
                  detailbuilder.addLoadingPorts(loadingPortDetail);
                });

        List<Long> dischargingPorts =
            this.loadableStudyPortRotationRepository.getDischarigingPorts(loadableStudy.get())
                .stream()
                .distinct()
                .collect(Collectors.toList());

        portReply.getPortsList().stream()
            .filter(port -> dischargingPorts.contains(port.getId()))
            .forEach(
                dischargingPort -> {
                  LoadableStudy.DischargingPortDetail.Builder dischargingPortDetail =
                      LoadableStudy.DischargingPortDetail.newBuilder();
                  dischargingPortDetail.setName(dischargingPort.getName());
                  dischargingPortDetail.setPortId(dischargingPort.getId());
                  detailbuilder.addDischargingPorts(dischargingPortDetail);
                });

        List<CargoNomination> cargoList =
            this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                loadableStudy.get().getId(), true);

        List<Long> cargos =
            cargoList.stream()
                .map(CargoNomination::getCargoXId)
                .distinct()
                .collect(Collectors.toList());

        List<CargoInfo.CargoDetail> cargoes =
            cargoReply.getCargosList().stream()
                .filter(cargo -> cargos.contains(cargo.getId()))
                .collect(Collectors.toList());
        cargoes.forEach(
            cargo -> {
              LoadableStudy.CargoDetails.Builder cargoDetails =
                  LoadableStudy.CargoDetails.newBuilder();
              cargoDetails.setName(cargo.getCrudeType());
              cargoDetails.setCargoId(cargo.getId());
              detailbuilder.addCargos(cargoDetails);
            });

        detailbuilder.setCharterer(loadableStudy.get().getCharterer());
      }

      builder.addVoyages(detailbuilder.build());
    }
    builder.setResponseStatus(LoadableStudy.StatusReply.newBuilder().setStatus(SUCCESS).build());
    return builder;
  }

  public PortInfo.PortReply GetPortInfo(PortInfo.PortRequest build) {
    return portInfoGrpcService.getPortInfo(build);
  }

  public CargoInfo.CargoReply getCargoInfo(CargoInfo.CargoRequest build) {
    return cargoInfoGrpcService.getCargoInfo(build);
  }

  public void buildVoyageDetails(
      ModelMapper modelMapper, com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy) {
    loadableStudy.setVoyage(new VoyageDto());
    Voyage voyage = this.voyageRepository.findByIdAndIsActive(loadableStudy.getVoyageId(), true);
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    VoyageDto voyageDDto = new VoyageDto();
    voyageDDto = modelMapper.map(voyage, VoyageDto.class);
    loadableStudy.setVoyage(voyageDDto);
  }
}
