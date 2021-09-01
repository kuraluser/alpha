/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingStagesDuration;
import java.util.List;

public interface DischargeStageDurationRepository
    extends CommonCrudRepository<DischargingStagesDuration, Long> {

  List<DischargingStagesDuration> findAllByIsActiveTrue();
}
