/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.DeballastingRate;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingSequence;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DeballastingRateRepository extends CommonCrudRepository<DeballastingRate, Long> {

  List<DeballastingRate> findByLoadingSequenceAndIsActiveTrueOrderById(
      LoadingSequence loadingSequence);

  List<DeballastingRate> findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Modifying
  @Transactional
  @Query("UPDATE DeballastingRate SET isActive = false WHERE loadingSequence = ?1")
  public void deleteByLoadingSequence(LoadingSequence loadingSequence);

  @Modifying
  @Transactional
  @Query("UPDATE DeballastingRate SET isActive = false WHERE loadingPlanPortWiseDetails = ?1")
  public void deleteByLoadingPlanPortWiseDetails(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);
}
