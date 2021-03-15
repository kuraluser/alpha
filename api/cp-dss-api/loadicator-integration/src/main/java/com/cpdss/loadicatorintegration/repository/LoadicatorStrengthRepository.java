/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.LoadicatorStrength;
import java.util.List;

public interface LoadicatorStrengthRepository
    extends CommonCrudRepository<LoadicatorStrength, Long> {

  List<LoadicatorStrength> findByStowagePlanIdIn(List<Long> stowagePlanIds);
}
