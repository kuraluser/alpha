/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanRobDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DischargingPlanRobDetailsRepository
    extends CommonCrudRepository<DischargingPlanRobDetails, Long> {

  List<DischargingPlanRobDetails> findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails);

  @Query(
      "from DischargingPlanRobDetails dr where dr.dischargingPlanPortWiseDetails.id in ?1 and dr.isActive = true order by dr.id")
  List<DischargingPlanRobDetails> findByDischargingPlanPortWiseDetailsInAndIsActiveTrueOrderById(
      List<Long> ids);

  @Modifying
  @Transactional
  @Query(
      "UPDATE DischargingPlanRobDetails SET isActive = false WHERE dischargingPlanPortWiseDetails = ?1")
  public void deleteByDischargingPlanPortWiseDetails(
      DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails);

  @Query(
      "SELECT LPRD FROM DischargingPlanRobDetails LPRD WHERE LPRD.dischargingPlanPortWiseDetails.id IN ?1 AND LPRD.isActive = ?2")
  public List<DischargingPlanRobDetails> findByPortWiseDetailIdsAndIsActive(
      List<Long> portWiseDetailIds, Boolean isActive);
}
