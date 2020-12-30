/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanCommingleDetails;
import java.util.List;

/** @Author jerin.g */
public interface LoadablePlanCommingleDetailsRepository
    extends CommonCrudRepository<LoadablePlanCommingleDetails, Long> {

  public List<LoadablePlanCommingleDetails> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);
}
