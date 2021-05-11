/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.IntactStability;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface LoadicatorIntactStabilityRepository
    extends CommonCrudRepository<IntactStability, Long> {

  @Query("FROM IntactStability WHERE stowagePlanId in ?1 ORDER BY Id")
  List<IntactStability> findByStowagePlanIdIn(List<Long> stowagePlanIds);

  public IntactStability findByStowagePlanId(Long id);
}
