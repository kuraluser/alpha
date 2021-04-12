/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.AlgoErrorHeading;
import com.cpdss.loadablestudy.entity.AlgoErrors;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AlgoErrorsRepository
    extends CommonCrudRepository<AlgoErrors, Long>, JpaSpecificationExecutor<AlgoErrors> {

  Optional<List<AlgoErrors>> findByAlgoErrorHeadingAndIsActive(
      AlgoErrorHeading algoErrorHeading, Boolean isActive);

  @Transactional
  @Modifying
  @Query(
      "UPDATE AlgoErrors SET isActive = ?1 WHERE algoErrorHeading.id IN (SELECT AEH.id FROM AlgoErrorHeading AEH WHERE AEH.loadablePattern.id = ?2)")
  public void deleteAlgoError(Boolean isActive, Long loadablePatternId);
}
