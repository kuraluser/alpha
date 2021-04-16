/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** @Author Mohamed.thaiseer */
public interface LoadablePlanCommingleDetailsPortwiseRepository
    extends CommonCrudRepository<LoadablePlanComminglePortwiseDetails, Long> {

  @Query(
      "from LoadablePlanComminglePortwiseDetails LPCPD where LPCPD.loadablePattern.id=?1"
          + " and LPCPD.isActive=?2")
  public List<LoadablePlanComminglePortwiseDetails> findByLoadablePatternIdAndIsActive(
      Long loadablePatternId, boolean isActive);

  @Query(
      "from LoadablePlanComminglePortwiseDetails LPCPD where LPCPD.loadablePattern.id=?1"
          + " and LPCPD.portRotationXid=?2 and LPCPD.operationType=?3 and LPCPD.isActive=?4")
  public List<LoadablePlanComminglePortwiseDetails>
      findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
          long loadablePatternId, Long id, String operationType, boolean b);

  @Query(
      "select LPCPD from LoadablePlanComminglePortwiseDetails LPCPD where LPCPD.loadablePattern.id in (:loadablePatternIds)"
          + " and LPCPD.isActive = :isActive")
  public List<LoadablePlanComminglePortwiseDetails> findByLoadablePatternIdInAndIsActive(
      @Param("loadablePatternIds") List<Long> loadablePatternIds,
      @Param("isActive") Boolean isActive);
}