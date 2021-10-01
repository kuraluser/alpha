/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.BallastingRate;
import com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails;
import com.cpdss.dischargeplan.entity.DischargingSequence;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BallastingRateRepository extends CommonCrudRepository<BallastingRate, Long> {

  List<BallastingRate> findByDischargingSequenceAndIsActiveTrueOrderById(
      DischargingSequence dischargingSequence);

  List<BallastingRate> findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails);

  @Modifying
  @Transactional
  @Query("UPDATE BallastingRate SET isActive = false WHERE dischargingSequence = ?1")
  public void deleteByDischargingSequence(DischargingSequence dischargingSequence);

  @Modifying
  @Transactional
  @Query("UPDATE BallastingRate SET isActive = false WHERE dischargingPlanPortWiseDetails = ?1")
  public void deleteByDischargingPlanPortWiseDetails(
      DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails);
}
