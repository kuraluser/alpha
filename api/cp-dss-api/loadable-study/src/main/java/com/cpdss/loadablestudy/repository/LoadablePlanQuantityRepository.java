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

  // Can use for loading plan, and discharge plan
  default List<LoadablePlanQuantity> PORT_WISE_CARGO_DETAILS(
      Long patternId, String opType, Long portRotation, Long port) {
    return findAllLoadablePlanQuantity(patternId, opType, portRotation, port);
  }

  public List<LoadablePlanQuantity> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadablePlanQuantity SET isActive = ?1 WHERE loadablePattern.id = ?2")
  public void deleteLoadablePlanQuantity(Boolean isActive, Long loadablePatternId);

  String CARGO_TO_BE_LOADED_QUERY_1 =
      "FROM LoadablePlanQuantity lpq WHERE lpq.loadablePattern.id = ?1 AND lpq.cargoNominationId IN (SELECT DISTINCT lpcd.cargoNominationId FROM LoadablePatternCargoDetails lpcd WHERE lpcd.loadablePatternId = ?1 AND lpcd.operationType = ?2 AND lpcd.portRotationId = ?3 AND lpcd.portId = ?4 AND lpcd.isActive = true) AND lpq.isActive = true";

  @Query(CARGO_TO_BE_LOADED_QUERY_1)
  List<LoadablePlanQuantity> findAllLoadablePlanQuantity(
      Long patternId, String opType, Long portRotation, Long port);
}
