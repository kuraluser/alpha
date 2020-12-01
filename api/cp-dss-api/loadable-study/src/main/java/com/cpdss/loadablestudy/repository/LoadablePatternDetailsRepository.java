/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePatternDetails;
import java.util.List;

/** @Author jerin.g */
public interface LoadablePatternDetailsRepository
    extends CommonCrudRepository<LoadablePatternDetails, Long> {
  public List<LoadablePatternDetails> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);
}
