/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanStowageDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DischargingPlanStowageDetailsRepository
    extends CommonCrudRepository<DischargingPlanStowageDetails, Long> {

  List<DischargingPlanStowageDetails> findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      DischargingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Query(
      "from DischargingPlanStowageDetails dr where dr.dischargingPlanPortWiseDetails.id in ?1 and dr.isActive = true order by dr.id")
  List<DischargingPlanStowageDetails>
      findByDischargingPlanPortWiseDetailsInAndIsActiveTrueOrderById(List<Long> ids);

  @Modifying
  @Transactional
  @Query(
      "UPDATE DischargingPlanStowageDetails SET isActive = false WHERE dischargingPlanPortWiseDetails = ?1")
  public void deleteByDischargingPlanPortWiseDetails(
      DischargingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Query(
      "SELECT LPSD FROM DischargingPlanStowageDetails LPSD WHERE LPSD.dischargingPlanPortWiseDetails.id IN ?1 AND LPSD.isActive = ?2")
  public List<DischargingPlanStowageDetails> findByPortWiseDetailIdsAndIsActive(
      List<Long> portWiseDetailIds, Boolean isActive);
}
