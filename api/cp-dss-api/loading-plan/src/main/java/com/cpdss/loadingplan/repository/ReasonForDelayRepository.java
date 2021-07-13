/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.ReasonForDelay;
import java.util.List;
import java.util.Optional;

public interface ReasonForDelayRepository extends CommonCrudRepository<ReasonForDelay, Long> {

  List<ReasonForDelay> findAllByIsActiveTrue();

  public Optional<ReasonForDelay> findByIdAndIsActiveTrue(Long id);
}
