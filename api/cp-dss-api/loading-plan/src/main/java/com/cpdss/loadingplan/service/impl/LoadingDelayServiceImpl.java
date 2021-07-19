/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelay;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelays;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.ReasonForDelay;
import com.cpdss.loadingplan.repository.LoadingDelayRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.ReasonForDelayRepository;
import com.cpdss.loadingplan.service.LoadingDelayService;
import java.math.BigDecimal;
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
                loadingDelayRepository.deleteById(existingDelay.getId());
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

      if (delay.getReasonForDelayId() != 0) {
        buildLoadingDelay(delay, loadingDelay);
        loadingDelayRepository.save(loadingDelay);
      }
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

    Optional<ReasonForDelay> reasonOpt =
        reasonForDelayRepository.findByIdAndIsActiveTrue(delay.getReasonForDelayId());

    if (reasonOpt.isPresent()) {
      loadingDelay.setReasonForDelay(reasonOpt.get());
    } else {
      throw new Exception(
          "Cannot find the reason with id " + delay.getReasonForDelayId() + "for the delay");
    }

    loadingDelay.setCargoNominationId(delay.getCargoNominationId());
    loadingDelay.setDuration(
        StringUtils.isEmpty(delay.getDuration()) ? null : new BigDecimal(delay.getDuration()));
    Optional.ofNullable(delay.getCargoId()).ifPresent(loadingDelay::setCargoXId);
    loadingDelay.setQuantity(
        StringUtils.isEmpty(delay.getQuantity()) ? null : new BigDecimal(delay.getQuantity()));
    loadingDelay.setIsActive(true);
  }
}
