/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import java.util.List;
import java.util.Optional;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingStagesMinAmount;

public interface DischargeStageMinAmountRepository
    extends CommonCrudRepository<DischargingStagesMinAmount, Long> {

  List<DischargingStagesMinAmount> findAllByIsActiveTrue();

Optional<DischargingStagesMinAmount> findByIdAndIsActiveTrue(long id);
}
