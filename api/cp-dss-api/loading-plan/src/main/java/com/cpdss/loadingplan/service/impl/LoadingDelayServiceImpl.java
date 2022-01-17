/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.LoadableStudy;
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
              .map(delay -> delay.getId())
              .collect(Collectors.toList());
      existingDelays.stream()
          .filter(existingDelay -> !requestedDelays.contains(existingDelay.getId()))
          .forEach(
              existingDelay -> {
                this.deleteDelayAndReasons(existingDelay);
                // loadingDelayRepository.deleteById(existingDelay.getId());
              });
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

  /**
   * Method to save default managing sequence cargos in loading information delay
   *
   * @param loadableQuantityCargoDetailsList LoadableQuantityCargoDetails list
   * @param savedLoadingInformation LoadingInformation entity input
   */
  @Override
  public void saveDefaultManagingSequence(
      List<LoadableStudy.LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList,
      LoadingInformation savedLoadingInformation) {

    log.info("Inside saveDefaultManagingSequence method!");

    List<com.cpdss.loadingplan.entity.LoadingDelay> loadingDelays = new ArrayList<>();
    loadableQuantityCargoDetailsList.forEach(
        loadableQuantityCargoDetails -> {
          com.cpdss.loadingplan.entity.LoadingDelay loadingDelay =
              new com.cpdss.loadingplan.entity.LoadingDelay();

          // Set fields
          Optional.of(loadableQuantityCargoDetails.getCargoId())
              .ifPresent(loadingDelay::setCargoXId);
          Optional.of(loadableQuantityCargoDetails.getCargoNominationId())
              .ifPresent(loadingDelay::setCargoNominationId);
          loadingDelay.setQuantity(
              StringUtils.isEmpty(loadableQuantityCargoDetails.getOrderedMT())
                  ? null
                  : new BigDecimal(loadableQuantityCargoDetails.getOrderedMT()));

          loadingDelay.setLoadingInformation(savedLoadingInformation);
          loadingDelay.setIsActive(true);
          loadingDelays.add(loadingDelay);
        });
    loadingDelayRepository.saveAll(loadingDelays);
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
    for (Long id : delay.getReasonForDelayIdsList()) {
      // Master Reason data
      Optional<ReasonForDelay> var1 = reasonForDelayRepository.findByIdAndIsActiveTrue(id);
      if (!var1.isEmpty()) {
        delayReasons.add(new LoadingDelayReason(loadingDelay, var1.get(), true));
      } else {
        throw new Exception("Delay Reason master data not found for id - " + id);
      }
    }
    loadingDelay.setLoadingDelayReasons(delayReasons);
    loadingDelay.setCargoNominationId(delay.getCargoNominationId());
    loadingDelay.setDuration(
        StringUtils.isEmpty(delay.getDuration()) ? null : new BigDecimal(delay.getDuration()));
    Optional.ofNullable(delay.getCargoId()).ifPresent(loadingDelay::setCargoXId);
    loadingDelay.setQuantity(
        StringUtils.isEmpty(delay.getQuantity()) ? null : new BigDecimal(delay.getQuantity()));
    loadingDelay.setIsActive(true);
  }

  private void deleteDelayReasons(List<LoadingDelayReason> reasonList) {
    reasonList.stream()
        .map(LoadingDelayReason::getId)
        .forEach(loadingDelayReasonRepository::deleteLoadingDelayReasonById);
  }

  private void deleteDelayAndReasons(com.cpdss.loadingplan.entity.LoadingDelay delay) {
    delay.setIsActive(false);
    delay.getLoadingDelayReasons().forEach(v -> v.setIsActive(false));
    loadingDelayRepository.save(delay);
    log.info("Deleted old delay Id {}", delay.getId());
  }
}
