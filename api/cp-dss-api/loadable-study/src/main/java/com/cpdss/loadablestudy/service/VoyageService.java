/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoyageService {

  @Autowired private VoyageRepository voyageRepository;

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

  public LoadableStudy.ActiveVoyage fetchActiveVoyageByVesselId(
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

      // set confirmed loadable study
      if (voyage.getVoyageStatus() != null
          && (STATUS_ACTIVE.equalsIgnoreCase(voyage.getVoyageStatus().getName())
              || STATUS_CLOSE.equalsIgnoreCase(voyage.getVoyageStatus().getName()))) {

        Optional<com.cpdss.loadablestudy.entity.LoadableStudy> lsOb =
            voyage.getLoadableStudies().stream()
                .filter(
                    ls ->
                        (ls.getLoadableStudyStatus() != null
                            && STATUS_CONFIRMED.equalsIgnoreCase(
                                ls.getLoadableStudyStatus().getName())))
                .findFirst();

        if (lsOb.isPresent()) {
          LoadableStudy.LoadableStudyDetail.Builder lsBuilder =
              LoadableStudy.LoadableStudyDetail.newBuilder();
          this.buildLoadableStudyForVoyage(lsBuilder, lsOb.get());
          builder.setConfirmedLoadableStudy(lsBuilder);
          if (!lsOb.get().getPortRotations().isEmpty()) {
            for (LoadableStudyPortRotation lsPr : lsOb.get().getPortRotations()) {
              LoadableStudy.PortRotationDetail.Builder grpcPRBuilder =
                  LoadableStudy.PortRotationDetail.newBuilder();
              this.buildPortRotationForLs(grpcPRBuilder, lsPr);
              builder.addPortRotation(grpcPRBuilder.build());
            }
          }
        }
      }
    }
    return null;
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
    Optional.ofNullable(entity.getBerthXId()).ifPresent(builder::setBerthId);
    Optional.ofNullable(entity.getOperation().getId()).ifPresent(builder::setOperationId);
    Optional.ofNullable(entity.getSeaWaterDensity())
        .ifPresent(v -> builder.setSeaWaterDensity(String.valueOf(v)));
  }
}
