/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudyAlgoStatus;
import java.util.List;
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

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadableStudyAlgoStatus SET loadableStudyStatus.id = ?1 WHERE messageId = ?2 and isActive = ?3")
  public void updateLoadableStudyAlgoStatusByMessageId(
      Long loadableStudyStatusId, String messageId, Boolean isActive);

  public Optional<LoadableStudyAlgoStatus> findByLoadableStudyIdAndProcessIdAndIsActive(
      Long loadableStudyId, String processId, Boolean isActive);

  Optional<LoadableStudyAlgoStatus> findByLoadableStudyIdAndMessageIdAndIsActive(
      Long loadableStudyId, String messageId, Boolean isActive);

  public List<LoadableStudyAlgoStatus> findByLoadableStudyIdAndIsActive(
      Long loadableStudyId, Boolean isActive);

  @Query(
      value =
          "select * from loadable_study_algo_status where loadabale_studyxid= ?1 and last_modified_date_time is not null order by last_modified_date_time desc limit 1",
      nativeQuery = true)
  Optional<LoadableStudyAlgoStatus> findByLoadableStudyId(Long loadableStudyId);
}
