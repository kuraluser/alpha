/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.ReasonForDelay;
import java.util.List;

public interface ReasonForDelayRepository extends CommonCrudRepository<ReasonForDelay, Long> {

  List<ReasonForDelay> findAllByIsActiveTrue();
}
