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
import org.springframework.util.StringUtils;

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
      DischargingBerthDetail dischargingBerthDetail = null;
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

  private void buildDischargingBerthDetail(
      DischargeBerths berth, DischargingBerthDetail dischargingBerthDetail) throws Exception {
    Optional<DischargeInformation> dischargingInformationOpt =
        dischargingInformationRepository.findByIdAndIsActiveTrue(berth.getDischargeInfoId());
    if (dischargingInformationOpt.isPresent()) {
      dischargingBerthDetail.setDischargingInformation(dischargingInformationOpt.get());
    } else {
      throw new Exception(
          "Cannot find the discharge study for berth detail with id " + berth.getDischargeInfoId());
    }
    dischargingBerthDetail.setAirDraftLimitation(
        StringUtils.isEmpty(berth.getAirDraftLimitation())
            ? null
            : new BigDecimal(berth.getAirDraftLimitation()));
    Optional.ofNullable(berth.getBerthId()).ifPresent(dischargingBerthDetail::setBerthXid);
    dischargingBerthDetail.setDepth(
        StringUtils.isEmpty(berth.getDepth()) ? null : new BigDecimal(berth.getDepth()));
    dischargingBerthDetail.setMaxManifoldHeight(
        StringUtils.isEmpty(berth.getMaxManifoldHeight())
            ? null
            : new BigDecimal(berth.getMaxManifoldHeight()));
    dischargingBerthDetail.setSeaDraftLimitation(
        StringUtils.isEmpty(berth.getSeaDraftLimitation())
            ? null
            : new BigDecimal(berth.getSeaDraftLimitation()));
    Optional.ofNullable(berth.getSpecialRegulationRestriction())
        .ifPresent(dischargingBerthDetail::setSpecialRegulationRestriction);
    Optional.ofNullable(berth.getHoseConnections())
        .ifPresent(dischargingBerthDetail::setHoseConnections);
    Optional.ofNullable(berth.getItemsToBeAgreedWith())
        .ifPresent(dischargingBerthDetail::setItemToBeAgreed);
    dischargingBerthDetail.setLineContentDisplacement(
        berth.getLineDisplacement().isEmpty()
            ? BigDecimal.ZERO
            : new BigDecimal(berth.getLineDisplacement()));
    dischargingBerthDetail.setIsActive(true);
  }
}
