/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanCommingleDetails;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** @author pranav.k */
@Repository
public interface LoadingPlanCommingleDetailsRepository
    extends CommonCrudRepository<LoadingPlanCommingleDetails, Long> {

  @Modifying
  @Transactional
  @Query(
      "UPDATE LoadingPlanCommingleDetails SET isActive = false WHERE loadingPlanPortWiseDetails = ?1")
  public void deleteByLoadingPlanPortWiseDetails(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Query(
      "SELECT LPCD FROM LoadingPlanCommingleDetails LPCD WHERE LPCD.loadingPlanPortWiseDetails.id IN ?1 AND LPCD.isActive = ?2")
  public List<LoadingPlanCommingleDetails> findByPortWiseDetailIdsAndIsActive(
      List<Long> portWiseDetailIds, Boolean isActive);
}
