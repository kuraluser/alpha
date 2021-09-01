/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.ReasonForDelay;
import java.util.List;

public interface ReasonForDelayRepository extends CommonCrudRepository<ReasonForDelay, Long> {

  List<ReasonForDelay> findAllByIsActiveTrue();
}
