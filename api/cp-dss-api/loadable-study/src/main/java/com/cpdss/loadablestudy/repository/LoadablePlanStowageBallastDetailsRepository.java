/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageBallastDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/** @author suhail.k */
public interface LoadablePlanStowageBallastDetailsRepository
    extends CommonCrudRepository<LoadablePlanStowageBallastDetails, Long> {

  @Query(
      "select b from LoadablePlanStowageBallastDetails b where b.loadablePlan.id="
          + "(select p.id from LoadablePlan p "
          + "where p.loadableStudyXId=?1 and p.isActive=true and p.loadablePlanStatus=?2)")
  public List<LoadablePlanStowageBallastDetails> findBallastDetailsForLoadableStudy(
      Long loadableStudyId, Long loadablePlanStatusId);
}
