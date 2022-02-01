/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingPlanBallastDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DischargingPlanBallastDetailsRepository
    extends CommonCrudRepository<DischargingPlanBallastDetails, Long> {

  List<DischargingPlanBallastDetails> findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails);

  @Query(
      "from DischargingPlanBallastDetails dr where dr.dischargingPlanPortWiseDetails.id in ?1 and dr.isActive = true order by dr.id")
  List<DischargingPlanBallastDetails>
      findByDischargingPlanPortWiseDetailsInAndIsActiveTrueOrderById(List<Long> ids);

  @Modifying
  @Transactional
  @Query(
      "UPDATE DischargingPlanBallastDetails SET isActive = false WHERE dischargingPlanPortWiseDetails = ?1")
  public void deleteByDischargingPlanPortWiseDetails(
      DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails);

  @Query(
      "SELECT LPBD FROM DischargingPlanBallastDetails LPBD WHERE LPBD.dischargingPlanPortWiseDetails.id IN ?1 AND LPBD.isActive = ?2")
  public List<DischargingPlanBallastDetails> findByDischargingPlanPortWiseDetailIdsAndIsActive(
      List<Long> portWiseDetailIds, Boolean isActive);
}
