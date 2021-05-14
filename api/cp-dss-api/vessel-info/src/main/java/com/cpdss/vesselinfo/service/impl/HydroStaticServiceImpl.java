/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service.impl;

import com.cpdss.vesselinfo.domain.HydrostaticSpecification;
import com.cpdss.vesselinfo.domain.SearchCriteria;
import com.cpdss.vesselinfo.entity.HydrostaticTable;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.repository.HydrostaticTableRepository;
import com.cpdss.vesselinfo.service.HydrostaticService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HydroStaticServiceImpl implements HydrostaticService {

  @Autowired HydrostaticTableRepository hydrostaticTableRepository;

  @Override
  public List<HydrostaticTable> fetchAllDataByDraftAndVessel(Vessel var1, BigDecimal var2) {
    Specification specification =
        Specification.where(
                new HydrostaticSpecification(
                    new SearchCriteria("vessel.id", "EQUAL", var1.getId())))
            .and(new HydrostaticSpecification(new SearchCriteria("draft", "EQUAL", var2)))
            .and(new HydrostaticSpecification(new SearchCriteria("isActive", "EQUAL", true)));
    List<HydrostaticTable> resp = hydrostaticTableRepository.findAll(specification);
    log.info(
        "Hydrostatic Table Search by Vessel {} and Draft {}, List Size - {}",
        var1.getId(),
        var2,
        resp.size());

    if (resp.isEmpty()) {
      Optional<HydrostaticTable> hT = inverseCaseForDWT(var1, var2);
      log.info("Hydrostatic Table Inverse Case For Draft {}", var2);
      return Arrays.asList(hT.get());
    }
    return resp;
  }

  /**
   * In case of draft vale is grater than table value, Then It must fetch the greatest draft value.
   * In case of draft vale is less than table value, Then It must fetch the lowest draft value
   *
   * @return
   */
  Optional<HydrostaticTable> inverseCaseForDWT(Vessel var1, BigDecimal var2) {
    try {
      Optional<HydrostaticTable> maxDraftData =
          hydrostaticTableRepository.findFirstByVesselOrderByDraftDesc(var1);
      Optional<HydrostaticTable> minDraftData =
          hydrostaticTableRepository.findFirstByVesselOrderByDraftAsc(var1);
      if (var2.compareTo(minDraftData.get().getDraft()) > 0) {
        return maxDraftData;
      } else {
        return minDraftData;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
}
