/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetailsTemp;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** @author suhail.k */
public interface LoadablePlanStowageDetailsTempRepository
    extends CommonCrudRepository<LoadablePlanStowageDetailsTemp, Long> {

  public LoadablePlanStowageDetailsTemp findByLoadablePlanStowageDetailsAndIsActive(
      LoadablePlanStowageDetails stowageDetail, boolean isActive);

  @Query(
      "SELECT LPSD.id, LPSD.cargoNominationId, LPSD.tankId, coalesce(CAST(LPSDT.quantity AS string ), LPSD.weight) FROM LoadablePlanStowageDetails LPSD LEFT JOIN LoadablePlanStowageDetailsTemp LPSDT ON LPSD.id = LPSDT.loadablePlanStowageDetails.id AND LPSDT.isActive = ?2 WHERE LPSD.loadablePattern.id = ?1 AND LPSD.isActive = ?2")
  public List<Object> findByLoadablePlanStowageTempDetailsAndIsActive(
      Long loadablePatternId, Boolean isActive);

  @Query(
      "SELECT LPSD.id, LPSD.tankId, coalesce(CAST(LPSDT.quantity AS string ), LPSD.metricTon) FROM LoadablePlanBallastDetails LPSD LEFT JOIN LoadablePlanStowageDetailsTemp LPSDT ON LPSD.id = LPSDT.loadablePlanBallastDetails.id AND LPSDT.isActive = ?2 WHERE LPSD.loadablePattern.id = ?1 AND LPSD.isActive = ?2")
  public List<Object> findByLoadablePlanBallastTempDetailsAndIsActive(
      Long loadablePatternId, Boolean isActive);

  @Query(
      "SELECT CASE WHEN count(*) > 0 then true else false end from LoadablePlanStowageDetailsTemp LPSDT WHERE LPSDT.loadablePlanBallastDetails.id in (SELECT id FROM LoadablePlanBallastDetails LPBD WHERE LPBD.loadablePattern.id = ?1 AND LPBD.isActive = ?2) AND LPSDT.isActive = ?2")
  public Boolean isBallastEdited(Long loadablePatternId, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadablePlanStowageDetailsTemp SET isActive = ?1 WHERE loadablePattern.id = ?2")
  public void deleteLoadablePlanStowageDetailsTemp(Boolean isActive, Long loadablePatternId);

  public List<LoadablePlanStowageDetailsTemp> findByLoadablePlanStowageDetailsInAndIsActive(
      List<LoadablePlanStowageDetails> stowageDetails, boolean isActive);

  public List<LoadablePlanStowageDetailsTemp> findByLoadablePlanBallastDetailsInAndIsActive(
      List<LoadablePlanBallastDetails> loadablePlanBallastDetails, boolean isActive);

  public LoadablePlanStowageDetailsTemp findByLoadablePlanBallastDetailsAndIsActive(
      LoadablePlanBallastDetails ballastDetails, boolean b);
}
