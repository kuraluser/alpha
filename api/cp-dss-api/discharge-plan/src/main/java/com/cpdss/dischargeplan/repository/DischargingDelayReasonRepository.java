/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingDelayReason;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface DischargingDelayReasonRepository
    extends CommonCrudRepository<DischargingDelayReason, Long> {

  @Query(
      "FROM DischargingDelayReason ddr WHERE ddr.dischargingDelay.id = ?1 and ddr.isActive = true")
  List<DischargingDelayReason> findAllByDischargingDelayId(Long id);
}
