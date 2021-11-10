/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingDelay;
import java.util.List;
import java.util.Optional;

public interface DischargingDelayRepository extends CommonCrudRepository<DischargingDelay, Long> {

  List<DischargingDelay> findAllByDischargingInformation_IdAndIsActiveOrderById(
      Long var1, boolean isActive);

  List<DischargingDelay> findByDischargingInformationIdAndIsActive(Long id, boolean b);

  Optional<DischargingDelay> findByIdAndIsActive(long id, boolean b);
}
