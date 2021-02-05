/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudyAlgoStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
public interface LoadableStudyAlgoStatusRepository
    extends CommonCrudRepository<LoadableStudyAlgoStatus, Long> {

  public Optional<LoadableStudyAlgoStatus> findByProcessIdAndIsActive(
      String processId, Boolean isActive);

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadableStudyAlgoStatus SET loadableStudyStatus.id = ?1 WHERE processId = ?2 and isActive = ?3")
  public void updateLoadableStudyAlgoStatus(
      Long loadableStudyStatusId, String processId, Boolean isActive);

  public Optional<LoadableStudyAlgoStatus> findByLoadableStudyIdAndProcessIdAndIsActive(
      Long loadableStudyId, String processId, Boolean isActive);
}
