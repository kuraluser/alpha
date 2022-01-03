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

  @Query(
      "FROM LoadablePatternCargoDetails lp WHERE lp.loadablePatternId = ?1 AND lp.portId = ?2 and lp.isActive = true")
  List<LoadablePatternCargoDetails> findAllByPatternIdAndPortId(Long var1, Long var2);

  Optional<LoadablePatternCargoDetails>
      findFirstByLoadablePatternIdAndCargoNominationIdAndTankIdAndIsActiveTrue(
          long loadablePatternId, long cargoNominationId, long tankId);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_pattern_cargo_details u where loadable_pattern_xid = ?1",
      nativeQuery = true)
  String getLoadablePatternCargoDetailsWithPatternId(long id);
}
