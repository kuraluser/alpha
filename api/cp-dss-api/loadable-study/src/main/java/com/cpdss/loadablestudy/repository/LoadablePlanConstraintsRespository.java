/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanConstraints;
import java.util.List;

/** @Author jerin.g */
public interface LoadablePlanConstraintsRespository
    extends CommonCrudRepository<LoadablePlanConstraints, Long> {
  public List<LoadablePlanConstraints> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);
}
