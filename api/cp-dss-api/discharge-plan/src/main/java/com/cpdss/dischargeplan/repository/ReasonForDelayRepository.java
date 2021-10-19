/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.ReasonForDelay;
import java.util.List;
import java.util.Optional;

public interface ReasonForDelayRepository extends CommonCrudRepository<ReasonForDelay, Long> {

  List<ReasonForDelay> findAllByIsActiveTrue();

  Optional<ReasonForDelay> findByIdAndIsActiveTrue(Long id);
}
