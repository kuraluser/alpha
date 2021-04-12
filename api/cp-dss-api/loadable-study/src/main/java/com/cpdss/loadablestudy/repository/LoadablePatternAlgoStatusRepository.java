/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePatternAlgoStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
public interface LoadablePatternAlgoStatusRepository
    extends CommonCrudRepository<LoadablePatternAlgoStatus, Long> {

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadablePatternAlgoStatus SET loadablePattern.id = ?1 WHERE processId = ?2 and isActive = ?3")
  public void updateLoadablePatternAlgoStatus(
      Long loadableStudyStatusId, String processId, Boolean isActive);

  public Optional<LoadablePatternAlgoStatus> findByLoadablePatternIdAndProcessIdAndIsActive(
      Long loadablePatternId, String processId, Boolean isActive);

  public List<LoadablePatternAlgoStatus> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);
}
