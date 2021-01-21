/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails;
import java.util.List;

/** @Author jerin.g */
public interface LoadablePlanBallastDetailsRepository
    extends CommonCrudRepository<LoadablePlanBallastDetails, Long> {
  public List<LoadablePlanBallastDetails> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);
}
