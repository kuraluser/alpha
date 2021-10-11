/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingPlanStabilityParameters;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingPlanStabilityParametersRepository
    extends CommonCrudRepository<LoadingPlanStabilityParameters, Long> {

  Optional<LoadingPlanStabilityParameters> findByLoadingPlanPortWiseDetailsAndIsActiveTrue(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Modifying
  @Transactional
  @Query(
      "UPDATE LoadingPlanStabilityParameters SET isActive = false WHERE loadingPlanPortWiseDetails = ?1")
  public void deleteByLoadingPlanPortWiseDetails(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Query("SELECT LPSP FROM LoadingPlanStabilityParameters LPSP WHERE LPSP.loadingPlanPortWiseDetails.id IN ?1")
  public List<LoadingPlanStabilityParameters> findByLoadingPlanPortWiseDetailIds(
          List<Long> portWiseDetailIds);
}
