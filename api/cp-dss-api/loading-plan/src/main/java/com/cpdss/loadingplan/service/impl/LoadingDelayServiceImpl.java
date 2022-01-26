/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelay;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelays;
import com.cpdss.loadingplan.entity.LoadingDelayReason;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.ReasonForDelay;
import com.cpdss.loadingplan.repository.LoadingDelayReasonRepository;
import com.cpdss.loadingplan.repository.LoadingDelayRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.ReasonForDelayRepository;
import com.cpdss.loadingplan.service.LoadingDelayService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Transactional
public class LoadingDelayServiceImpl implements LoadingDelayService {

  @Autowired LoadingDelayRepository loadingDelayRepository;
  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired ReasonForDelayRepository reasonForDelayRepository;
  @Autowired LoadingDelayReasonRepository loadingDelayReasonRepository;

  @Override
  public void saveLoadingDelayList(
      LoadingDelay loadingDelays, LoadingInformation loadingInformation) throws Exception {
    if (loadingDelays.getDelaysCount() > 0) {
      List<com.cpdss.loadingplan.entity.LoadingDelay> existingDelays =
          loadingDelayRepository.findByLoadingInformationIdAndIsActive(
              loadingInformation.getId(), true);
      List<Long> requestedDelays =
          loadingDelays.getDelaysList().stream()
              .map(LoadingDelays::getId)
              .collect(Collectors.toList());

      List<com.cpdss.loadingplan.entity.LoadingDelay> loadingDelayList =
          existingDelays.stream()
              .filter(existingDelay -> !requestedDelays.contains(existingDelay.getId()))
              .collect(Collectors.toList());
      deleteDelayAndReasons(loadingDelayList);
    }
    for (LoadingDelays delay : loadingDelays.getDelaysList()) {
      log.info(
          "Saving delay {} for LoadingInformation {}", delay.getId(), delay.getLoadingInfoId());
      com.cpdss.loadingplan.entity.LoadingDelay loadingDelay = null;
      if (delay.getId() == 0) {
        loadingDelay = new com.cpdss.loadingplan.entity.LoadingDelay();
      } else {
        Optional<com.cpdss.loadingplan.entity.LoadingDelay> loadingDelayOpt =
            loadingDelayRepository.findByIdAndIsActive(delay.getId(), true);
        if (loadingDelayOpt.isPresent()) {
          loadingDelay = loadingDelayOpt.get();
        } else {
          log.error("Exception occured while saving delay {}", delay.getId());
          throw new Exception("Cannot find the loading delay with id " + delay.getId());
        }
      }

      buildLoadingDelay(delay, loadingDelay);
      loadingDelayRepository.save(loadingDelay);
      log.info(
          "Loading Delay Saved Id {}, Reasons {}",
          loadingDelay.getId(),
          loadingDelay.getLoadingDelayReasons());
    }
  }

  private void buildLoadingDelay(
      LoadingDelays delay, com.cpdss.loadingplan.entity.LoadingDelay loadingDelay)
      throws Exception {
    Optional<LoadingInformation> loadingInformationOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(delay.getLoadingInfoId());

    if (loadingInformationOpt.isPresent()) {
      loadingDelay.setLoadingInformation(loadingInformationOpt.get());
    } else {
      throw new Exception(
          "Cannot find the loading information for the delay with id " + delay.getLoadingInfoId());
    }

    if (delay.getId() > 0) {
      // Remove All And Add Again
      List<LoadingDelayReason> reasonList =
          loadingDelayReasonRepository.findAllByLoadingDelayAndIsActiveTrue(loadingDelay);
      if (!reasonList.isEmpty()) {
        this.deleteDelayReasons(reasonList);
      }
    }

    // Incoming Delays
    List<LoadingDelayReason> delayReasons = new ArrayList<>();
    for (Long reasonForDelayId : delay.getReasonForDelayIdsList()) {
      // Master Reason data
      Optional<ReasonForDelay> reasonForDelayWrapper =
          reasonForDelayRepository.findByIdAndIsActiveTrue(reasonForDelayId);
      if (reasonForDelayWrapper.isPresent()) {
        delayReasons.add(new LoadingDelayReason(loadingDelay, reasonForDelayWrapper.get(), true));
      } else {
        log.error("Delay Reason master data not found for reasonForDelayId: {}", reasonForDelayId);
        throw new Exception(
            "Delay Reason master data not found for reasonForDelayId - " + reasonForDelayId);
      }
    }
    loadingDelay.setLoadingDelayReasons(delayReasons);
    loadingDelay.setCargoNominationId(delay.getCargoNominationId());
    loadingDelay.setDuration(
        StringUtils.isEmpty(delay.getDuration()) ? null : new BigDecimal(delay.getDuration()));
    Optional.of(delay.getCargoId()).ifPresent(loadingDelay::setCargoXId);
    loadingDelay.setQuantity(
        StringUtils.isEmpty(delay.getQuantity()) ? null : new BigDecimal(delay.getQuantity()));
    loadingDelay.setLoadingRate(
        StringUtils.hasLength(delay.getLoadingRate())
            ? new BigDecimal(delay.getLoadingRate())
            : null);
    loadingDelay.setIsActive(true);
  }

  private void deleteDelayReasons(List<LoadingDelayReason> reasonList) {
    reasonList.stream()
        .map(LoadingDelayReason::getId)
        .forEach(loadingDelayReasonRepository::deleteLoadingDelayReasonById);
  }

  private void deleteDelayAndReasons(
      List<com.cpdss.loadingplan.entity.LoadingDelay> loadingDelayList) {

    loadingDelayList.forEach(
        loadingDelay -> {
          log.info("Deleted old delay Id {}", loadingDelay.getId());
          loadingDelay.setIsActive(false);
          loadingDelay.getLoadingDelayReasons().forEach(v -> v.setIsActive(false));
        });
    loadingDelayRepository.saveAll(loadingDelayList);
  }
}
