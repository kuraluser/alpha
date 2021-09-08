/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingDelay;
import java.util.List;

public interface DischargingDelayRepository extends CommonCrudRepository<DischargingDelay, Long> {

  List<DischargingDelay> findAllByDischargingInformation_IdAndIsActive(Long var1, boolean isActive);
}
