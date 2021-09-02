/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingStagesMinAmount;
import java.util.List;

public interface DischargeStageMinAmountRepository
    extends CommonCrudRepository<DischargingStagesMinAmount, Long> {

  List<DischargingStagesMinAmount> findAllByIsActiveTrue();
}
