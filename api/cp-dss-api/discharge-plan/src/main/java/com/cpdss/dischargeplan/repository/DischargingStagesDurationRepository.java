/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingStagesDuration;
import java.util.Optional;

public interface DischargingStagesDurationRepository
    extends CommonCrudRepository<DischargingStagesDuration, Long> {

  Optional<DischargingStagesDuration> findByDurationAndIsActiveTrue(Integer duration);
}
