/* Licensed under Apache-2.0 */
package com.cpdss.loadableplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadableplan.entity.LoadableQuantity;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

/** @Author jerin.g */
public interface LoadableQuantityRepository extends CommonCrudRepository<LoadableQuantity, Long> {
  @Query("FROM LoadableQuantity LQ WHERE LQ.loadableStudyXId.id = ?1")
  public Optional<LoadableQuantity> findByLoadableStudyXId(Long loadableStudyXId);
}
