/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePlan;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/** @Author jerin.g */
public interface LoadablePlanRepository extends CommonCrudRepository<LoadablePlan, Long> {

  @Query("SELECT LP.id FROM LoadablePlan LP WHERE LP.loadableStudyXId = ?1")
  List<Long> getLoadablePlanIdsByLSId(Long id);
}
