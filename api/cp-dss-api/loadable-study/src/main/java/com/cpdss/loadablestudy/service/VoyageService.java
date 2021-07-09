/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.util.Optional.ofNullable;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.CargoHistory;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
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

  @Value("${loadablestudy.voyage.day.difference}")
  private String dayDifference;

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
                                ls.getLoadableStudyStatus().getName())))
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
      builder.setResponseStatus(
          LoadableStudy.StatusReply.newBuilder()
              .setStatus(SUCCESS)
              .setMessage(SUCCESS)
              .setCode(CommonErrorCodes.E_CPDSS_VOYAGE_EXISTS)
              .build());
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
      // fetch the confirmed loadable study for active voyages
      if (entity.getVoyageStatus() != null
          && (STATUS_ACTIVE.equalsIgnoreCase(entity.getVoyageStatus().getName())
              || STATUS_CLOSE.equalsIgnoreCase(entity.getVoyageStatus().getName()))) {
        Stream<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyStream =
            ofNullable(entity.getLoadableStudies())
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
          Long noOfDays = this.getNumberOfDays(loadableStudy.get());
          Optional.ofNullable(noOfDays).ifPresent(item -> detailbuilder.setNoOfDays(item));
        }
      }
      builder.addVoyages(detailbuilder.build());
    }
    builder.setResponseStatus(LoadableStudy.StatusReply.newBuilder().setStatus(SUCCESS).build());
    return builder;
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
}
