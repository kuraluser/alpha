/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.discharge_plan.DischargeDelay;
import com.cpdss.common.generated.discharge_plan.DischargeDelays;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingDelay;
import com.cpdss.dischargeplan.entity.DischargingDelayReason;
import com.cpdss.dischargeplan.entity.ReasonForDelay;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargingDelayReasonRepository;
import com.cpdss.dischargeplan.repository.DischargingDelayRepository;
import com.cpdss.dischargeplan.repository.ReasonForDelayRepository;
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
public class DischargingDelayService {

  @Autowired DischargingDelayRepository dischargingDelayRepository;
  @Autowired DischargeInformationRepository dischargingInformationRepository;
  @Autowired ReasonForDelayRepository reasonForDelayRepository;
  @Autowired DischargingDelayReasonRepository dischargingDelayReasonRepository;

  public void saveDischargingDelayList(
      DischargeDelay dischargeDelay, DischargeInformation dischargingInformation) throws Exception {
    if (dischargeDelay.getDelaysCount() > 0) {
      List<DischargingDelay> existingDelays =
          dischargingDelayRepository.findByDischargingInformationIdAndIsActive(
              dischargingInformation.getId(), true);
      List<Long> requestedDelays =
          dischargeDelay.getDelaysList().stream()
              .map(DischargeDelays::getId)
              .collect(Collectors.toList());
      existingDelays.stream()
          .filter(existingDelay -> !requestedDelays.contains(existingDelay.getId()))
          .forEach(this::deleteDelayAndReasons);
    }
    for (DischargeDelays delay : dischargeDelay.getDelaysList()) {
      log.info(
          "Saving delay {} for DischargingInformation {}",
          delay.getId(),
          delay.getDischargeInfoId());
      DischargingDelay dischargingDelay = null;
      if (delay.getId() == 0) {
        dischargingDelay = new DischargingDelay();
      } else {
        Optional<DischargingDelay> loadingDelayOpt =
            dischargingDelayRepository.findByIdAndIsActive(delay.getId(), true);
        if (loadingDelayOpt.isPresent()) {
          dischargingDelay = loadingDelayOpt.get();
        } else {
          log.error("Exception occured while saving delay {}", delay.getId());
          throw new Exception("Cannot find the DISCHARGING delay with id " + delay.getId());
        }
      }

      buildDischargingDelay(delay, dischargingDelay);
      dischargingDelayRepository.save(dischargingDelay);
      log.info(
          "Discharging Delay Saved Id {}, Reasons {}",
          dischargingDelay.getId(),
          dischargingDelay.getDischargingDelayReasons());
    }
  }

  private void buildDischargingDelay(DischargeDelays delay, DischargingDelay dischargingDelay)
      throws Exception {
    Optional<DischargeInformation> dischargingInformationOpt =
        dischargingInformationRepository.findByIdAndIsActiveTrue(delay.getDischargeInfoId());

    if (dischargingInformationOpt.isPresent()) {
      dischargingDelay.setDischargingInformation(dischargingInformationOpt.get());
    } else {
      throw new Exception(
          "Cannot find the loading information for the delay with id "
              + delay.getDischargeInfoId());
    }

    if (delay.getId() > 0) {
      // Remove All And Add Again
      List<DischargingDelayReason> reasonList =
          dischargingDelayReasonRepository.findAllByDischargingDelayAndIsActive(
              dischargingDelay, true);
      if (!reasonList.isEmpty()) {
        this.deleteDelayReasons(reasonList);
      }
    }

    // Incoming Delays
    List<DischargingDelayReason> delayReasons = new ArrayList<>();
    for (Long id : delay.getReasonForDelayIdsList()) {
      // Master Reason data
      Optional<ReasonForDelay> var1 = reasonForDelayRepository.findByIdAndIsActiveTrue(id);
      if (!var1.isEmpty()) {
        delayReasons.add(new DischargingDelayReason(true, dischargingDelay, var1.get()));
      } else {
        throw new Exception("Delay Reason master data not found for id - " + id);
      }
    }
    dischargingDelay.setDischargingDelayReasons(delayReasons);
    dischargingDelay.setCargoNominationXid(delay.getCargoNominationId());
    dischargingDelay.setSequenceNo(delay.getSequenceNo());
    dischargingDelay.setDuration(
        StringUtils.isEmpty(delay.getDuration()) ? null : new BigDecimal(delay.getDuration()));
    Optional.ofNullable(delay.getCargoId()).ifPresent(dischargingDelay::setCargoXid);
    dischargingDelay.setQuantity(
        StringUtils.isEmpty(delay.getQuantity()) ? null : new BigDecimal(delay.getQuantity()));
    dischargingDelay.setIsActive(true);
  }

  private void deleteDelayReasons(List<DischargingDelayReason> reasonList) {
    reasonList.stream()
        .map(DischargingDelayReason::getId)
        .forEach(dischargingDelayReasonRepository::deleteDischargingDelayReasonById);
  }

  private void deleteDelayAndReasons(DischargingDelay delay) {
    delay.setIsActive(false);
    delay.getDischargingDelayReasons().forEach(v -> v.setIsActive(false));
    dischargingDelayRepository.save(delay);
    log.info("Deleted old delay Id {}", delay.getId());
  }

  /**
   * Method to save default managing sequence cargos in discharge information delay
   *
   * @param dischargeInformationService DischargeInformation input object
   * @param managingSequenceRequestList List<LoadingPlanModels.ManagingSequenceRequest> input list
   */
  public void saveDefaultManagingSequence(
      DischargeInformation dischargeInformationService,
      List<LoadingPlanModels.ManagingSequenceRequest> managingSequenceRequestList) {

    log.info("Inside saveDefaultManagingSequence method!");

    List<DischargingDelay> dischargingDelays = new ArrayList<>();
    managingSequenceRequestList.forEach(
        managingSequenceRequest -> {
          DischargingDelay dischargingDelay = new DischargingDelay();

          // Set fields
          dischargingDelay.setDischargingInformation(dischargeInformationService);
          dischargingDelay.setCargoXid(managingSequenceRequest.getCargoId());
          dischargingDelay.setQuantity(new BigDecimal(managingSequenceRequest.getQuantity()));

          dischargingDelays.add(dischargingDelay);
        });

    dischargingDelayRepository.saveAll(dischargingDelays);
  }
}
