/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingStagesMinAmount;
import java.util.Optional;

public interface DischargingStagesMinAmountRepository
    extends CommonCrudRepository<DischargingStagesMinAmount, Long> {

  Optional<DischargingStagesMinAmount> findByMinAmountAndIsActiveTrue(Integer minAmount);
}
