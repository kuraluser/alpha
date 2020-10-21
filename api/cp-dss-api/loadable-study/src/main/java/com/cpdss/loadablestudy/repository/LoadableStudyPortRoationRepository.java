/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import java.util.List;

public interface LoadableStudyPortRoationRepository
    extends CommonCrudRepository<LoadableStudyPortRotation, Long> {

  public List<LoadableStudyPortRotation> findByLoadableStudy(final LoadableStudy loadableStudy);
}
