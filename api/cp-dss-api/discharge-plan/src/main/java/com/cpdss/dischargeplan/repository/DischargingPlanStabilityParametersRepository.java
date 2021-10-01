/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanStabilityParameters;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DischargingPlanStabilityParametersRepository
    extends CommonCrudRepository<DischargingPlanStabilityParameters, Long> {

  Optional<DischargingPlanStabilityParameters> findByDischargingPlanPortWiseDetailsAndIsActiveTrue(
      DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails);

  @Modifying
  @Transactional
  @Query(
      "UPDATE DischargingPlanStabilityParameters SET isActive = false WHERE dischargingPlanPortWiseDetails = ?1")
  public void deleteByDischargingPlanPortWiseDetails(
      DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails);
}
