/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingDelay;
import com.cpdss.dischargeplan.entity.DischargingDelayReason;
import java.util.List;

public interface DischargingDelayReasonRepository
    extends CommonCrudRepository<DischargingDelayReason, Long> {

  List<DischargingDelayReason> findAllByDischargingDelayAndIsActive(
      DischargingDelay dischargingDelay, boolean isActive);
}
