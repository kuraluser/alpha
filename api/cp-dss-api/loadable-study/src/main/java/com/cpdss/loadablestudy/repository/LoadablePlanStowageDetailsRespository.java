/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
public interface LoadablePlanStowageDetailsRespository
    extends CommonCrudRepository<LoadablePlanStowageDetails, Long> {
  public List<LoadablePlanStowageDetails> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);

  public List<LoadablePlanStowageDetails> findByLoadablePatternIdInAndIsActive(
      List<Long> loadablePatternIds, boolean b);

  @Transactional
  @Modifying
  @Query("UPDATE LoadablePlanStowageDetails SET isActive = ?1 WHERE loadablePattern.id = ?2")
  public void deleteLoadablePlanStowageDetails(Boolean isActive, Long loadablePatternId);
}
