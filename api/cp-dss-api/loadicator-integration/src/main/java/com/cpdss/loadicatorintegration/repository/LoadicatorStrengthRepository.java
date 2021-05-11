/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.LoadicatorStrength;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface LoadicatorStrengthRepository
    extends CommonCrudRepository<LoadicatorStrength, Long> {
  
  @Query("FROM LoadicatorStrength WHERE stowagePlanId in ?1 ORDER BY Id")
  List<LoadicatorStrength> findByStowagePlanIdIn(List<Long> stowagePlanIds);

  public LoadicatorStrength findByStowagePlanId(Long id);
}
