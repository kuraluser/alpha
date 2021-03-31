/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanQuantity;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
public interface LoadablePlanQuantityRepository
    extends CommonCrudRepository<LoadablePlanQuantity, Long> {

  public List<LoadablePlanQuantity> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadablePlanQuantity SET isActive = ?1 WHERE loadablePattern.id = ?2")
  public void deleteLoadablePlanQuantity(Boolean isActive, Long loadablePatternId);
}
