/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.LoadicatorTrim;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface LoadicatorTrimRepository extends CommonCrudRepository<LoadicatorTrim, Long> {

  @Query("FROM LoadicatorTrim WHERE stowagePlanId in ?1 ORDER BY Id")
  List<LoadicatorTrim> findByStowagePlanIdIn(List<Long> stowagePlanIds);

  public LoadicatorTrim findByStowagePlanId(Long stowageId);
}
