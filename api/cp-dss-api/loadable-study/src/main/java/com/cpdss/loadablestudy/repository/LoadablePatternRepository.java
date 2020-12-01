/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import java.util.List;

/** @Author jerin.g */
public interface LoadablePatternRepository extends CommonCrudRepository<LoadablePattern, Long> {
  public List<LoadablePattern> findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
      LoadableStudy loadableStudy, Boolean isActive);
}
