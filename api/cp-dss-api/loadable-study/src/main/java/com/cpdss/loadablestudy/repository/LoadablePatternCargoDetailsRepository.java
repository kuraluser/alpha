/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LoadablePatternCargoDetailsRepository
    extends CommonCrudRepository<LoadablePatternCargoDetails, Long> {

  public List<LoadablePatternCargoDetails> findByLoadablePatternIdAndIsActive(
      Long loadablePatternId, boolean isActive);

  public List<LoadablePatternCargoDetails>
      findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
          Long loadablePatternId, Long portRotationId, String operationType, boolean isActive);

  public Optional<LoadablePatternCargoDetails> findById(Long loadablePatternId);

  public List<LoadablePatternCargoDetails> findByLoadablePatternIdInAndIsActive(
      List<Long> loadablePatternId, boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadablePatternCargoDetails SET isActive = ?1 WHERE loadablePatternId = ?2")
  public void deleteLoadablePatternCargoDetails(Boolean isActive, Long loadablePatternId);

  @Query(
      "SELECT DISTINCT lp.portRotationId FROM LoadablePatternCargoDetails lp WHERE lp.cargoNominationId =?1 AND lp.isActive = true")
  List<Long> findAllPortRotationIdByCargoNomination(Long var1);
}
