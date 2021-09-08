/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import java.util.List;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingDelay;
import com.cpdss.dischargeplan.entity.DischargingDelayReason;

public interface DischargingDelayReasonRepository
    extends CommonCrudRepository<DischargingDelayReason, Long> {

  List<DischargingDelayReason> findAllByDischargingDelayAndIsActive(DischargingDelay dischargingDelay,boolean isActive);
}
