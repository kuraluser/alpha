/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingStagesDuration;
import java.util.List;
import java.util.Optional;

public interface DischargeStageDurationRepository
    extends CommonCrudRepository<DischargingStagesDuration, Long> {

  List<DischargingStagesDuration> findAllByIsActiveTrue();

  Optional<DischargingStagesDuration> findByIdAndIsActiveTrue(long id);
}
