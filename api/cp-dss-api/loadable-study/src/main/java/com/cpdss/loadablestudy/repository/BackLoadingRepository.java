/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.BackLoading;
import java.util.List;

public interface BackLoadingRepository extends CommonCrudRepository<BackLoading, Long> {

  public List<BackLoading> findByDischargeStudyIdAndPortIdInAndIsActive(
      Long id, List<Long> portIds, boolean isActive);
}
