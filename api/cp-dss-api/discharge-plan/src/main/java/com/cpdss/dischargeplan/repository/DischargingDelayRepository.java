/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingDelay;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface DischargingDelayRepository extends CommonCrudRepository<DischargingDelay, Long> {

  @Query("FROM DischargingDelay dd WHERE dd.dischargingInformation.id = :var1 AND isActive = true")
  List<DischargingDelay> findAllByDischargeInfoId(Long var1);
}
