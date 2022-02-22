/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.discharge_plan.DischargeBerths;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingBerthDetail;
import com.cpdss.dischargeplan.repository.DischargeBerthDetailRepository;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class DischargingBerthService {

  @Autowired DischargeBerthDetailRepository dischargingBerthDetailRepository;
  @Autowired DischargeInformationRepository dischargingInformationRepository;

  public void saveDischargingBerthList(
      List<DischargeBerths> dischargingBerthsList, DischargeInformation dischargingInformation)
      throws Exception {
    List<DischargingBerthDetail> existingBerths =
        dischargingBerthDetailRepository.findByDischargingInformationIdAndIsActive(
            dischargingInformation.getId(), true);
    List<Long> requestedBerths =
        dischargingBerthsList.stream().map(DischargeBerths::getId).collect(Collectors.toList());
    existingBerths.stream()
        .filter(berth -> !requestedBerths.contains(berth.getId()))
        .forEach(berth -> dischargingBerthDetailRepository.deleteById(berth.getId()));
    for (DischargeBerths berth : dischargingBerthsList) {
      log.info(
          "Saving berth {} for DischargingInformation {}",
          berth.getBerthId(),
          berth.getDischargeInfoId());
      DischargingBerthDetail dischargingBerthDetail;
      if (berth.getId() == 0) {
        dischargingBerthDetail = new DischargingBerthDetail();
      } else {
        Optional<DischargingBerthDetail> dischargingBerthDetailOpt =
            dischargingBerthDetailRepository.findByIdAndIsActiveTrue(berth.getId());
        if (dischargingBerthDetailOpt.isPresent()) {
          dischargingBerthDetail = dischargingBerthDetailOpt.get();
        } else {
          log.error("Cannot find the discharging berth with id {}", berth.getId());
          throw new Exception("Cannot find the discharging berth with id " + berth.getId());
        }
      }

      buildDischargingBerthDetail(berth, dischargingBerthDetail);
      dischargingBerthDetailRepository.save(dischargingBerthDetail);
    }
  }

  /**
   * Builds discharging berth entity from grpc builder request
   *
   * @param berth berth input grpc object
   * @param dischargingBerthDetail discharging berth entity
   * @throws Exception in case of failures
   */
  private void buildDischargingBerthDetail(
      DischargeBerths berth, DischargingBerthDetail dischargingBerthDetail) throws Exception {

    log.info("Inside buildDischargingBerthDetail method!");
    Optional<DischargeInformation> dischargingInformationOpt =
        dischargingInformationRepository.findByIdAndIsActiveTrue(berth.getDischargeInfoId());
    if (dischargingInformationOpt.isPresent()) {
      dischargingBerthDetail.setDischargingInformation(dischargingInformationOpt.get());
    } else {
      throw new Exception(
          "Cannot find the discharge study for berth detail with id " + berth.getDischargeInfoId());
    }

    // Set fields
    dischargingBerthDetail.setAirDraftLimitation(returnZeroIfBlank(berth.getAirDraftLimitation()));
    Optional.of(berth.getBerthId()).ifPresent(dischargingBerthDetail::setBerthXid);
    dischargingBerthDetail.setDepth(returnZeroIfBlank(berth.getDepth()));
    dischargingBerthDetail.setMaxManifoldHeight(returnZeroIfBlank(berth.getMaxManifoldHeight()));
    dischargingBerthDetail.setMaxManifoldPressure(
        returnZeroIfBlank(berth.getMaxManifoldPressure()));
    dischargingBerthDetail.setSeaDraftLimitation(returnZeroIfBlank(berth.getSeaDraftLimitation()));
    Optional.of(berth.getSpecialRegulationRestriction())
        .ifPresent(dischargingBerthDetail::setSpecialRegulationRestriction);
    Optional.of(berth.getHoseConnections()).ifPresent(dischargingBerthDetail::setHoseConnections);
    Optional.of(berth.getItemsToBeAgreedWith())
        .ifPresent(dischargingBerthDetail::setItemToBeAgreed);

    dischargingBerthDetail.setLineContentDisplacement(
        returnZeroIfBlank(berth.getLineDisplacement()));
    Optional.of(berth.getAirPurge()).ifPresent(dischargingBerthDetail::setIsAirPurge);
    Optional.of(berth.getCargoCirculation())
        .ifPresent(dischargingBerthDetail::setIsCargoCirculation);
    dischargingBerthDetail.setDisplacement(returnZeroIfBlank(berth.getDisplacement()));
    dischargingBerthDetail.setEnableDayLightRestriction(berth.getEnableDayLightRestriction());
    dischargingBerthDetail.setNeedFlushingOilAndCrudeStorage(
        berth.getNeedFlushingOilAndCrudeStorage());
    dischargingBerthDetail.setFreshCrudeOilQuantity(
        returnZeroIfBlank(berth.getFreshCrudeOilQuantity()));
    dischargingBerthDetail.setFreshCrudeOilTime(returnZeroIfBlank(berth.getFreshCrudeOilTime()));
    dischargingBerthDetail.setIsActive(true);
  }

  /**
   * Returns big decimal value of provided string else returns big decimal zero
   *
   * @param string input string
   * @return big decimal value
   */
  private BigDecimal returnZeroIfBlank(String string) {

    return io.micrometer.core.instrument.util.StringUtils.isBlank(string)
        ? BigDecimal.ZERO
        : new BigDecimal(string);
  }
}
