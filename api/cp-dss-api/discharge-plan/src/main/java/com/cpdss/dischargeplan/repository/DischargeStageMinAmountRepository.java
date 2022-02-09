/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingStagesMinAmount;
import java.util.List;
import java.util.Optional;

public interface DischargeStageMinAmountRepository
    extends CommonCrudRepository<DischargingStagesMinAmount, Long> {

  List<DischargingStagesMinAmount> findAllByIsActiveTrueOrderByMinAmount();

  Optional<DischargingStagesMinAmount> findByIdAndIsActiveTrue(long id);
}
