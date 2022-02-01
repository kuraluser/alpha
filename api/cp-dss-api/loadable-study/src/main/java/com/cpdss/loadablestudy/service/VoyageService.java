/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.VoyageListReply.Builder;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.discharge_plan.DischargePlanServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.CargoHistory;
import com.cpdss.loadablestudy.domain.VoyageDto;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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

  @Autowired private SynopticalTableRepository synopticalTableRepository;

  @Value("${loadablestudy.voyage.day.difference}")
  private String dayDifference;

  @Value("${cpdss.voyage.validation.enable}")
  private boolean validationBeforeVoyageClosureEnabled;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @GrpcClient("cargoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;

  @GrpcClient("dischargeInformationService")
  private DischargePlanServiceGrpc.DischargePlanServiceBlockingStub
      dischargePlanServiceBlockingStub;

  private final Long ACTIVE_VOYAGE = 3L;

  @Autowired CargoService cargoService;

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
        List<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudies =
            loadableStudyRepository.findByListOfVoyage(List.of(voyage.getId()));
        Optional<com.cpdss.loadablestudy.entity.LoadableStudy> confirmedLs =
            emptyIfNull(loadableStudies).stream()
                .filter(
                    ls ->
                        (ls.getLoadableStudyStatus() != null
                            && STATUS_CONFIRMED.equalsIgnoreCase(
                                ls.getLoadableStudyStatus().getName())
                            && ls.getPlanningTypeXId().equals(PLANNING_TYPE_LOADING)))
                .findFirst();

        Optional<com.cpdss.loadablestudy.entity.LoadableStudy> confirmedDs =
            emptyIfNull(loadableStudies).stream()
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
            List<LoadablePattern> patterns =
                loadablePatternRepository.findConfirmedPatternByLoadableStudyId(
                    confirmedLs.get().getId(), LS_STATUS_CONFIRMED);
            for (LoadableStudyPortRotation lsPr : confirmedLs.get().getPortRotations()) {
              LoadableStudy.PortRotationDetail.Builder grpcPRBuilder =
                  LoadableStudy.PortRotationDetail.newBuilder();
              this.buildPortRotationForLs(grpcPRBuilder, lsPr);
              builder.addPortRotation(grpcPRBuilder.build());
              log.info(
                  "Get Active voyage, Loadable Study Name {}, Id {}",
                  confirmedLs.get().getName(),
                  confirmedLs.get().getId());

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
            List<LoadablePattern> patterns =
                loadablePatternRepository.findConfirmedPatternByLoadableStudyId(
                    confirmedDs.get().getId(), LS_STATUS_CONFIRMED);
            for (LoadableStudyPortRotation dsPr : confirmedDs.get().getPortRotations()) {
              LoadableStudy.PortRotationDetail.Builder grpcPRBuilder =
                  LoadableStudy.PortRotationDetail.newBuilder();
              this.buildPortRotationForLs(grpcPRBuilder, dsPr);
              builder.addDischargePortRotation(grpcPRBuilder.build());
              log.info(
                  "Get Active voyage, Discharge Study Name {}, Id {}",
                  confirmedDs.get().getName(),
                  confirmedDs.get().getId());
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
    Optional.ofNullable(entity.getLoadLineXId()).ifPresent(builder::setLoadLineXId);
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
    Optional.ofNullable(entity.getEta()).ifPresent(eta -> builder.setEta(eta.toString()));
    Optional.ofNullable(entity.getEtd()).ifPresent(etd -> builder.setEtd(etd.toString()));
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

  /**
   * Method to return only active voyages against a vessel
   *
   * @param request
   * @param builder
   * @return
   */
  public LoadableStudy.VoyageListReply.Builder getActiveVoyagesByVessel(
      LoadableStudy.VoyageRequest request, LoadableStudy.VoyageListReply.Builder builder) {
    log.info("Fetching list of ACTIVE voyages for vessel : {}", request.getVesselId());
    List<Voyage> entityList =
        this.voyageRepository
            .findByVesselXIdAndIsActiveAndVoyageStatus_IdOrderByCreatedDateTimeDesc(
                request.getVesselId(), true, ACTIVE_VOYAGE);
    return voyageResponseBuilder(entityList, builder);
  }

  /**
   * Builder method to return voyage list
   *
   * @param entityList
   * @param builder
   * @return
   */
  private Builder voyageResponseBuilder(List<Voyage> entityList, Builder builder) {
    // List of discharged voyages
    List<Long> voyageIds = new ArrayList<>();
    Map<Long, LocalDateTime> etdMap = new HashMap<>();
    if (!CollectionUtils.isEmpty(entityList)) {
      voyageIds =
          loadableStudyRepository.getActiveVoyageIdsByVesselIdAndPlanningType(
              entityList.get(0).getVesselXId(), PLANNING_TYPE_DISCHARGE);
      List<Object[]> vesselEtdList =
          loadableStudyRepository.getVesellEtd(entityList.get(0).getVesselXId());
      if (vesselEtdList != null) {
        vesselEtdList.forEach(
            item -> {
              if (item[0] != null) {
                LocalDateTime dateTime = null;
                if (item[1] != null) {
                  dateTime = ((Timestamp) item[1]).toLocalDateTime();
                }
                etdMap.put(Long.valueOf(item[0].toString()), dateTime);
              }
            });
      }
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    for (Voyage entity : entityList) {
      LoadableStudy.VoyageDetail.Builder detailbuilder = LoadableStudy.VoyageDetail.newBuilder();
      detailbuilder.setId(entity.getId());
      detailbuilder.setVoyageNumber(entity.getVoyageNo());
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
        if (voyageIds.contains(entity.getId())) {
          detailbuilder.setIsDischargeStarted(true);
          confirmedStudy = getConfirmedStudy(entity, PLANNING_TYPE_DISCHARGE);
          if (confirmedStudy.isPresent()) {
            detailbuilder.setConfirmedDischargeStudyId(confirmedStudy.get().getId());
          }
        } else {
          detailbuilder.setIsDischargeStarted(false);
        }
        confirmedStudy = getConfirmedStudy(entity, PLANNING_TYPE_LOADING);
        if (confirmedStudy.isPresent()) {
          detailbuilder.setConfirmedLoadableStudyId(confirmedStudy.get().getId());
          Long noOfDays = this.getNumberOfDays(confirmedStudy.get(), etdMap);
          Optional.ofNullable(noOfDays).ifPresent(item -> detailbuilder.setNoOfDays(item));
        }
      }
      builder.addVoyages(detailbuilder.build());
    }
    builder.setResponseStatus(LoadableStudy.StatusReply.newBuilder().setStatus(SUCCESS).build());
    return builder;
  }

  /**
   * Return all voyages on a vessel
   *
   * @param request
   * @param builder
   * @return
   */
  public LoadableStudy.VoyageListReply.Builder getVoyagesByVessel(
      LoadableStudy.VoyageRequest request, LoadableStudy.VoyageListReply.Builder builder) {
    List<Voyage> entityList =
        this.voyageRepository.findByVesselXIdAndIsActiveOrderByCreatedDateTimeDesc(
            request.getVesselId(), true);
    return voyageResponseBuilder(entityList, builder);
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
                        && loadableStudyElement.getPlanningTypeXId().equals(planningType)
                        && loadableStudyElement.getLoadableStudyStatus() != null
                        && STATUS_CONFIRMED.equalsIgnoreCase(
                            loadableStudyElement.getLoadableStudyStatus().getName()))
            .findFirst();

    return confirmedStudy;
  }

  private Long getNumberOfDays(
      com.cpdss.loadablestudy.entity.LoadableStudy entity, Map<Long, LocalDateTime> etdMap) {
    Long daysBetween = null;
    if (etdMap.get(entity.getId()) != null) {
      daysBetween =
          ChronoUnit.DAYS.between(LocalDate.now(), etdMap.get(entity.getId()).toLocalDate());
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
            if (synoptical.isPresent() && synoptical.get().getEtaActual() != null) {
              voyageEntity.setActualStartDate(synoptical.get().getEtaActual());
            } else {
              // Set today's date as the actual start date of the voyage.
              voyageEntity.setActualStartDate(LocalDateTime.now());
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
    } else {

      if (validationBeforeVoyageClosureEnabled) {

        Set<com.cpdss.loadablestudy.entity.LoadableStudy> allLoadableStudies =
            voyageEntity.getLoadableStudies();
        if (allLoadableStudies == null) {
          log.error("No loadable/discharge studies found!");
          throw new GenericServiceException(
              "No loadable/discharge studies found!",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }

        List<com.cpdss.loadablestudy.entity.LoadableStudy> dischargeStudies =
            allLoadableStudies.stream()
                .filter(
                    loadableStudyElement ->
                        (loadableStudyElement.getLoadableStudyStatus() != null
                            && STATUS_CONFIRMED.equalsIgnoreCase(
                                loadableStudyElement.getLoadableStudyStatus().getName())
                            && loadableStudyElement.getPlanningTypeXId()
                                == PLANNING_TYPE_DISCHARGE))
                .collect(Collectors.toList());
        if (dischargeStudies.isEmpty()) {
          log.error("No confirmed discharge study");
          throw new GenericServiceException(
              "No confirmed discharge study",
              CommonErrorCodes.E_CPDSS_NO_ACUTALS_OR_BL_VALUES_FOUND,
              HttpStatusCode.BAD_REQUEST);
        }

        com.cpdss.loadablestudy.entity.LoadableStudy dischargeStudy = dischargeStudies.get(0);

        // Validations before closing the voyage
        // 1. Update Ullage verified for last DS port
        // 2. Updated bl figure for all cargo
        validateActuals(dischargeStudy);

        // 3. ETA Actual and ETD Actual check for all port rotations
        List<LoadableStudyPortRotation> loadableStudyPortRotations =
            this.loadableStudyPortRotationRepository.findByLoadableStudyIdAndIsActive(
                dischargeStudy.getId(), true);
        for (LoadableStudyPortRotation loadableStudyPortRotation : loadableStudyPortRotations) {
          List<SynopticalTable> synopticalTables =
              this.synopticalTableRepository
                  .findByLoadableStudyXIdAndLoadableStudyPortRotation_idAndIsActive(
                      dischargeStudy.getId(), loadableStudyPortRotation.getId(), true);
          for (SynopticalTable synopticalTable : synopticalTables) {
            if (synopticalTable.getEtaActual() == null && synopticalTable.getEtdActual() == null) {
              log.error(
                  "No Actual ETA/ETD values found in Synoptical Table id: {}",
                  synopticalTable.getId());
              throw new GenericServiceException(
                  "No Actual ETA/ETD values found",
                  CommonErrorCodes.E_CPDSS_NO_ACTUAL_ETA_OR_ETD_FOUND,
                  HttpStatusCode.BAD_REQUEST);
            }
          }
        }
      }

      Optional<VoyageStatus> status =
          this.voyageStatusRepository.findByIdAndIsActive(CLOSE_VOYAGE_STATUS, true);
      if (status.isEmpty()) {
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

            if (synoptical.isPresent() && synoptical.get().getEtdActual() != null) {
              voyageEntity.setActualEndDate(synoptical.get().getEtdActual());
            } else {
              // Set today's date as the actual start date of the voyage.
              voyageEntity.setActualEndDate(LocalDateTime.now());
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

      try {
        // Get and Save Cargo History from Loading and Discharge Modules
        cargoService.saveCargoHistoryFromOperationsModule(
            voyageEntity.getVesselXId(), voyageEntity.getId());
      } catch (Exception e) {
        log.info("Failed to save Cargo History - Error {}", e.getMessage());
      }
    }
    this.voyageRepository.save(voyageEntity);
    // API History is now fetched from loading and discharging modules.
    //    try {
    //      this.updateApiTempWithCargoNominations(voyageEntity);
    //    } catch (Exception e) {
    //      log.info("Voyage Close, update api-temp - Failed {} - {}", e.getMessage(), e);
    //    }
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /**
   * Validation of ullage update and bill of ladding before closure of voyage
   *
   * @param loadableStudy
   * @throws GenericServiceException
   */
  private void validateActuals(com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy)
      throws GenericServiceException {

    List<CargoNomination> cargoNominations =
        cargoNominationService.getCargoNominationByLoadableStudyId(loadableStudy.getId());
    List<LoadableStudyPortRotation> ports =
        loadableStudyPortRotationRepository
            .findByLoadableStudyAndIsActive(loadableStudy.getId(), true).stream()
            .filter(p -> p.getOperation().getId().equals(LOADING_OPERATION_ID))
            .sorted(Comparator.comparing(LoadableStudyPortRotation::getPortOrder))
            .collect(Collectors.toList());
    LoadingPlanModels.StowageAndBillOfLaddingValidationRequest.Builder request =
        LoadingPlanModels.StowageAndBillOfLaddingValidationRequest.newBuilder();
    ports.forEach(
        port -> {
          LoadingPlanModels.PortWiseCargo.Builder portBuilder =
              LoadingPlanModels.PortWiseCargo.newBuilder();
          portBuilder.setPortRotationId(port.getId());
          portBuilder.setPortId(port.getPortXId());
          List<Long> cargoIds =
              cargoNominations.stream()
                  .filter(
                      cargo ->
                          cargo.getCargoNominationPortDetails().stream()
                              .anyMatch(portData -> portData.getPortId().equals(port.getPortXId())))
                  .map(CargoNomination::getId)
                  .collect(Collectors.toList());

          portBuilder.addAllCargoIds(cargoIds);
          request.addPortWiseCargos(portBuilder);
        });
    Common.ResponseStatus response =
        this.dischargePlanServiceBlockingStub.validateStowageAndBillOfLadding(request.build());
    if (!SUCCESS.equalsIgnoreCase(response.getStatus())) {
      throw new GenericServiceException(
          "No Atcuals",
          CommonErrorCodes.E_CPDSS_NO_ACUTALS_OR_BL_VALUES_FOUND,
          HttpStatusCode.BAD_REQUEST);
    }
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
    portReqBuilder.setBerthDataNotNeed(true);
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
    log.info("total voyages listed - {}", entityList.size());

    List<Long> voyageIds = entityList.stream().map(Voyage::getId).collect(Collectors.toList());

    List<Long> lsIdList = new ArrayList<>();
    List<Long> dsIdList = new ArrayList<>();

    Map<Long, com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyMap = new HashMap<>();
    Map<Long, com.cpdss.loadablestudy.entity.LoadableStudy> dischargeStudyMap = new HashMap<>();
    Map<Long, String> loadableStudyChartererMap = new HashMap<>();
    // To fetch all loadableStudy for the requested voyageIds
    List<com.cpdss.loadablestudy.entity.LoadableStudy> listOfLoadableStudy =
        loadableStudyRepository.findByListOfVoyage(voyageIds);
    listOfLoadableStudy.forEach(
        loadableStudy -> {
          if (loadableStudy.getLoadableStudyStatus() != null
              && STATUS_CONFIRMED.equalsIgnoreCase(
                  loadableStudy.getLoadableStudyStatus().getName())) {
            if (loadableStudy.getPlanningTypeXId() == Common.PLANNING_TYPE.LOADABLE_STUDY_VALUE) {
              lsIdList.add(loadableStudy.getId());
              loadableStudyMap.put(loadableStudy.getVoyage().getId(), loadableStudy);
            }
            if (loadableStudy.getPlanningTypeXId() == Common.PLANNING_TYPE.DISCHARGE_STUDY_VALUE) {
              dsIdList.add(loadableStudy.getId());
              dischargeStudyMap.put(loadableStudy.getVoyage().getId(), loadableStudy);
            }
            loadableStudyChartererMap.put(loadableStudy.getId(), loadableStudy.getCharterer());
          }
        });
    log.info("Confirmed LS Size - {}", loadableStudyMap.size());
    log.info("Confirmed DS Size - {}", dischargeStudyMap.size());
    Map<Long, List<Long>> loadingPortHashMap = new HashMap<>();
    Map<Long, List<Long>> dischargingPortHashMap = new HashMap<>();
    Map<Long, List<Long>> cargoHashMap = new HashMap<>();
    // To fetch distinct loading portIds for the requested loadableStudyIds
    List<Object[]> distinctLoadingPorts =
        this.loadableStudyPortRotationRepository.getPortIdListForLSAndDSForVoyages(
            lsIdList, LOADING_OPERATION_ID);
    setLoadingAndDischargingPortMap(distinctLoadingPorts, loadingPortHashMap);
    // To fetch distinct discharging portIds for the requested loadableStudyIds
    List<Object[]> distinctDischargingPorts =
        this.loadableStudyPortRotationRepository.getPortIdListForLSAndDSForVoyages(
            dsIdList, DISCHARGING_OPERATION_ID);
    setLoadingAndDischargingPortMap(distinctDischargingPorts, dischargingPortHashMap);
    // To fetch distinct cargoIds for the requested loadableStudyIds
    List<Object[]> distinctCargos =
        this.cargoNominationRepository.findByLoadableStudyIdIn(lsIdList);
    setLoadingAndDischargingPortMap(distinctCargos, cargoHashMap);
    List<PortDetail> portsList = portReply.getPortsList();

    for (Voyage entity : entityList) {
      LoadableStudy.VoyageDetail.Builder detailbuilder = LoadableStudy.VoyageDetail.newBuilder();
      detailbuilder.setId(entity.getId());
      detailbuilder.setVoyageNumber(entity.getVoyageNo());
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      ofNullable(entity.getVoyageStartDate())
          .ifPresent(startDate -> detailbuilder.setStartDate(dateFormatter.format(startDate)));
      ofNullable(entity.getVoyageEndDate())
          .ifPresent(endDate -> detailbuilder.setEndDate(dateFormatter.format(endDate)));
      ofNullable(entity.getActualStartDate())
          .ifPresent(
              startDate -> detailbuilder.setActualStartDate(dateFormatter.format(startDate)));
      ofNullable(entity.getActualEndDate())
          .ifPresent(endDate -> detailbuilder.setActualEndDate(dateFormatter.format(endDate)));
      detailbuilder.setStatus(
          entity.getVoyageStatus() != null ? entity.getVoyageStatus().getName() : "");

      // Check any loadableStudy for that voyage is in confirmed status for active
      // voyages
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
          loadableStudyMap.get(entity.getId());
      com.cpdss.loadablestudy.entity.LoadableStudy dischargeStudy =
          dischargeStudyMap.get(entity.getId());
      if (dischargeStudy != null) {
        detailbuilder.setConfirmedDischargeStudyId(dischargeStudy.getId());
      }
      if (loadableStudy != null) {

        detailbuilder.setConfirmedLoadableStudyId(loadableStudy.getId());
        // loading ports wil not be available for discharging. SO based on the planning
        // type id,
        // populating the discharging ports and loading ports
        addLoadingPorts(loadingPortHashMap, portsList, detailbuilder, loadableStudy);
        addDischargingPorts(dischargingPortHashMap, portsList, detailbuilder, dischargeStudy);
        List<Long> cargos = cargoHashMap.get(loadableStudy.getId());
        if (cargos != null && !cargos.isEmpty()) {
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
        }
        if (loadableStudyChartererMap.get(loadableStudy.getId()) != null) {
          detailbuilder.setCharterer(loadableStudyChartererMap.get(loadableStudy.getId()));
        }
      }

      builder.addVoyages(detailbuilder.build());
    }
    builder.setResponseStatus(LoadableStudy.StatusReply.newBuilder().setStatus(SUCCESS).build());
    return builder;
  }

  private void addDischargingPorts(
      Map<Long, List<Long>> dischargingPortHashMap,
      List<PortDetail> portsList,
      LoadableStudy.VoyageDetail.Builder detailbuilder,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy) {
    if (loadableStudy != null) {
      List<Long> dischargePorts = dischargingPortHashMap.get(loadableStudy.getId());
      if (dischargePorts != null && !dischargePorts.isEmpty()) {
        for (Long id : dischargePorts) {
          Optional<PortDetail> portDetail =
              portsList.stream().filter(item -> item.getId() == id).findAny();
          if (portDetail.isPresent()) {
            LoadableStudy.DischargingPortDetail.Builder dischargingPortDetail =
                LoadableStudy.DischargingPortDetail.newBuilder();
            dischargingPortDetail.setName(portDetail.get().getName());
            dischargingPortDetail.setPortId(portDetail.get().getId());
            detailbuilder.addDischargingPorts(dischargingPortDetail);
          }
        }
      }
    }
  }

  private void addLoadingPorts(
      Map<Long, List<Long>> loadingPortHashMap,
      List<PortDetail> portsList,
      LoadableStudy.VoyageDetail.Builder detailbuilder,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy) {
    List<Long> ports = loadingPortHashMap.get(loadableStudy.getId());
    if (ports != null && !ports.isEmpty()) {
      for (Long id : ports) {
        Optional<PortDetail> portDetail =
            portsList.stream().filter(item -> item.getId() == id).findAny();
        if (portDetail.isPresent()) {
          LoadableStudy.LoadingPortDetail.Builder loadingPortDetail =
              LoadableStudy.LoadingPortDetail.newBuilder();
          loadingPortDetail.setName(portDetail.get().getName());
          loadingPortDetail.setPortId(portDetail.get().getId());
          detailbuilder.addLoadingPorts(loadingPortDetail);
        }
      }
    }
  }

  /**
   * To create HasMap with loadbaleStudyId as key and its corresponding list of loading ports or
   * list of discharging ports or list of cargoIds as value
   *
   * @param portsObj
   * @param portHashMap
   */
  private void setLoadingAndDischargingPortMap(
      List<Object[]> portsObj, Map<Long, List<Long>> portHashMap) {
    for (Object[] obj : portsObj) {
      long portId = (long) obj[0];
      long loadingId = (long) obj[1];
      List<Long> ports = new ArrayList<>();
      if (portHashMap.containsKey(loadingId)) {
        List<Long> list = portHashMap.get(loadingId);
        list.add(portId);
        portHashMap.put(loadingId, list);
      } else {
        ports.add(portId);
        portHashMap.put(loadingId, ports);
      }
    }
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

  public void getVoyageByVoyageId(
      LoadableStudy.VoyageInfoRequest request, LoadableStudy.VoyageInfoReply.Builder replyBuilder) {
    Voyage voyage = this.voyageRepository.findByIdAndIsActive(request.getVoyageId(), true);
    replyBuilder.setVoyageDetail(
        LoadableStudy.VoyageDetail.newBuilder().setVoyageNumber(voyage.getVoyageNo()).build());
  }

  /**
   * Checks if Discharging has started for the given vessel and voyage.
   *
   * @param vesselId
   * @param voyageId
   * @throws GenericServiceException
   */
  public void checkIfDischargingStarted(Long vesselId, Long voyageId)
      throws GenericServiceException {
    List<Long> voyageIds =
        loadableStudyRepository.getActiveVoyageIdsByVesselIdAndPlanningType(
            vesselId, PLANNING_TYPE_DISCHARGE);
    if (voyageIds.contains(voyageId)) {
      log.error("Discharging has started for the given voyage {}", voyageId);
      throw new GenericServiceException(
          "Discharging has started for the given voyage " + voyageId,
          CommonErrorCodes.E_CPDSS_DISCHARGING_STARTED,
          HttpStatusCode.BAD_REQUEST);
    }
  }
}
