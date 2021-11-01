/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingPlanBallastDetailsRepository
    extends CommonCrudRepository<LoadingPlanBallastDetails, Long> {

  List<LoadingPlanBallastDetails> findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Modifying
  @Transactional
  @Query(
      "UPDATE LoadingPlanBallastDetails SET isActive = false WHERE loadingPlanPortWiseDetails = ?1")
  public void deleteByLoadingPlanPortWiseDetails(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Query(
      "SELECT LPBD FROM LoadingPlanBallastDetails LPBD WHERE LPBD.loadingPlanPortWiseDetails.id IN ?1 AND LPBD.isActive = ?2")
  public List<LoadingPlanBallastDetails> findByLoadingPlanPortWiseDetailIdsAndIsActive(
      List<Long> portWiseDetailIds, Boolean isActive);

  @Query(
      "SELECT LPBD FROM LoadingPlanBallastDetails LPBD WHERE LPBD.loadingPlanPortWiseDetails.id IN ?1")
  public List<LoadingPlanBallastDetails> findByLoadingPlanPortWiseDetailIds(
      List<Long> portWiseDetailIds);
}
