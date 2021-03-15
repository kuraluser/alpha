/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanQuantity;
import java.util.List;

/** @Author jerin.g */
public interface LoadablePlanQuantityRepository
    extends CommonCrudRepository<LoadablePlanQuantity, Long> {

  public List<LoadablePlanQuantity> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);
}
