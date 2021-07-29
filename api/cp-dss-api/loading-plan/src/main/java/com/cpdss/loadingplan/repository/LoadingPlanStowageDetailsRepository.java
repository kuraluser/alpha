/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingPlanStowageDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingPlanStowageDetailsRepository
    extends CommonCrudRepository<LoadingPlanStowageDetails, Long> {

  List<LoadingPlanStowageDetails> findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Modifying
  @Transactional
  @Query(
      "UPDATE LoadingPlanStowageDetails SET isActive = false WHERE loadingPlanPortWiseDetails = ?1")
  public void deleteByLoadingPlanPortWiseDetails(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);
}
