/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.LoadicatorTrim;
import java.util.List;

public interface LoadicatorTrimRepository extends CommonCrudRepository<LoadicatorTrim, Long> {

  List<LoadicatorTrim> findByStowagePlanIdIn(List<Long> stowagePlanIds);

  public LoadicatorTrim findByStowagePlanId(Long stowageId);
}
