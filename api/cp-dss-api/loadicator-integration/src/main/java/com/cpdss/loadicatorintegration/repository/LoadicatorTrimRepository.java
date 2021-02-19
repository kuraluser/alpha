/* Licensed under Apache-2.0 */
package com.cpdss.loadicatorintegration.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.LoadicatorTrim;
import java.util.List;

public interface LoadicatorTrimRepository extends CommonCrudRepository<LoadicatorTrim, Long> {

  List<LoadicatorTrim> findByStowagePlanIdIn(List<Long> stowagePlanIds);
}
