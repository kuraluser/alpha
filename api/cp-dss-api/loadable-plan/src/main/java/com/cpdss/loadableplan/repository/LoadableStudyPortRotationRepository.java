/* Licensed under Apache-2.0 */
package com.cpdss.loadableplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadableplan.entity.LoadableStudyPortRotation;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface LoadableStudyPortRotationRepository
    extends CommonCrudRepository<LoadableStudyPortRotation, Long> {

  /**
   * @param loadableStudyId
   * @param isActive
   * @return List<LoadableStudyPortRotation>
   */
  @Query(
      "FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy.id = ?1 AND LSPR.isActive = ?2")
  public List<LoadableStudyPortRotation> findByLoadableStudyAndIsActive(
      Long loadableStudyId, Boolean isActive);
}
