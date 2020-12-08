/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/** @Author jerin.g */
public interface LoadableQuantityRepository extends CommonCrudRepository<LoadableQuantity, Long> {
  @Query("FROM LoadableQuantity LQ WHERE LQ.loadableStudyXId.id = ?1 and isActive = ?2")
  public List<LoadableQuantity> findByLoadableStudyXIdAndIsActive(
      Long loadableStudyXId, Boolean isActive);
}
