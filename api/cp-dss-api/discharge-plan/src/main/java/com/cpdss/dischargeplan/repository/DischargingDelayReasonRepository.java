/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingDelay;
import com.cpdss.dischargeplan.entity.DischargingDelayReason;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DischargingDelayReasonRepository
    extends CommonCrudRepository<DischargingDelayReason, Long> {

  List<DischargingDelayReason> findAllByDischargingDelayAndIsActive(
      DischargingDelay dischargingDelay, boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE DischargingDelayReason SET isActive = false WHERE id = ?1")
  int deleteDischargingDelayReasonById(Long id);
}
