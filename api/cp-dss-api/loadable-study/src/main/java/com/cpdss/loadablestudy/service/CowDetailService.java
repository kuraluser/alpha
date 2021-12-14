/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.loadablestudy.entity.DischargeStudyCowDetail;
import com.cpdss.loadablestudy.repository.DischargeStudyCowDetailRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CowDetailService {
  @Autowired DischargeStudyCowDetailRepository dischargeStudyCowDetailRepository;

  public Map<Long, DischargeStudyCowDetail> getCowDetailForThePort(
      long dischargestudyId, List<Long> portIds) {

    List<DischargeStudyCowDetail> cowDetails =
        dischargeStudyCowDetailRepository.findByDischargeStudyStudyIdAndPortIdInAndIsActive(
            dischargestudyId, portIds, true);
    return cowDetails.stream()
        .collect(
            Collectors.toMap(
                DischargeStudyCowDetail::getPortId, (DischargeStudyCowDetail cow) -> cow));
  }

  public DischargeStudyCowDetail getCowDetailForOnePort(long dischargestudyId, Long portId) {

    return dischargeStudyCowDetailRepository.findByDischargeStudyStudyIdAndPortIdAndIsActive(
        dischargestudyId, portId, true);
  }

  public void saveAll(List<DischargeStudyCowDetail> cowDetailsToSave) {
    dischargeStudyCowDetailRepository.saveAll(cowDetailsToSave);
  }

  public DischargeStudyCowDetail getCowDetailForDS(long dischargestudyId) {

    //    return
    DischargeStudyCowDetail cow =
        dischargeStudyCowDetailRepository.findFirstByDischargeStudyStudyIdAndIsActive(
            dischargestudyId, true);
    return cow;
  }
}
