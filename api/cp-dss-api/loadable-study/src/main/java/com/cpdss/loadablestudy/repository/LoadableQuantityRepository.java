/* Licensed at AlphaOri Technologies */
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

  @Query("FROM LoadableQuantity LQ WHERE LQ.id = ?1 and isActive = ?2")
  public LoadableQuantity findByIdAndIsActive(Long id, Boolean isActive);
}
