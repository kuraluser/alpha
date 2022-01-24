/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.AlgoErrorHeading;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AlgoErrorHeadingRepository
    extends CommonCrudRepository<AlgoErrorHeading, Long>,
        JpaSpecificationExecutor<AlgoErrorHeading> {

  Optional<AlgoErrorHeading> findByErrorHeading(String errorHeading);

  Optional<List<AlgoErrorHeading>> findAllByErrorHeading(String errorHeading);

  Optional<List<AlgoErrorHeading>> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);

  Optional<List<AlgoErrorHeading>> findByLoadableStudyAndIsActive(
      LoadableStudy loadablePattern, Boolean isActive);

  @Query(
      value =
          "SELECT aeh.id ,aeh.error_heading, ae.error_message from public.algo_error_heading aeh left join "
              + "public.algo_errors ae on aeh.id = ae.error_heading_xid "
              + "where aeh.loadable_study_xid = ?1 and aeh.is_active = ?2",
      nativeQuery = true)
  List<Object[]> findByLoadableStudyIdAndIsActive(Long id, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE AlgoErrorHeading SET isActive = ?1 WHERE loadablePattern.id = ?2")
  public void deleteAlgoErrorHeading(Boolean isActive, Long loadablePatternId);

  @Transactional
  @Modifying
  @Query("UPDATE AlgoErrorHeading SET isActive = ?1 WHERE loadableStudy.id = ?2")
  public void deleteAlgoErrorHeadingByLSId(Boolean isActive, Long loadableStudyId);

  @Query("SELECT ALE.id FROM AlgoErrorHeading ALE WHERE ALE.loadableStudy.id = ?1")
  List<Long> getAlgoErrorIdWithLoadableStudyId(Long id);
}
