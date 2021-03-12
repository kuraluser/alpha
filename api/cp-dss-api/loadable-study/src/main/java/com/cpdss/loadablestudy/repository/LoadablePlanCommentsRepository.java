/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanComments;
import java.util.List;

/** @Author jerin.g */
public interface LoadablePlanCommentsRepository
    extends CommonCrudRepository<LoadablePlanComments, Long> {
  public List<LoadablePlanComments> findByLoadablePatternAndIsActiveOrderByIdDesc(
      LoadablePattern loadablePattern, Boolean isActive);
}
