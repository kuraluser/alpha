/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.IntactStability;
import java.util.List;

public interface LoadicatorIntactStabilityRepository
    extends CommonCrudRepository<IntactStability, Long> {

  List<IntactStability> findByStowagePlanIdIn(List<Long> stowagePlanIds);

  public IntactStability findByStowagePlanId(Long id);
}
