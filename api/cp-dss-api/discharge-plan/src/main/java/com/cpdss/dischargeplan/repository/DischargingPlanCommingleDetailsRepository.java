/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingPlanCommingleDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** @author pranav.k */
@Repository
public interface DischargingPlanCommingleDetailsRepository
    extends CommonCrudRepository<DischargingPlanCommingleDetails, Long> {

  @Modifying
  @Transactional
  @Query(
      "UPDATE DischargingPlanCommingleDetails SET isActive = false WHERE dischargingPlanPortWiseDetails = ?1")
  public void deleteByDischargingPlanPortWiseDetails(
      DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails);

  @Query(
      "SELECT LPCD FROM DischargingPlanCommingleDetails LPCD WHERE LPCD.dischargingPlanPortWiseDetails.id IN ?1 AND LPCD.isActive = ?2")
  public List<DischargingPlanCommingleDetails> findByPortWiseDetailIdsAndIsActive(
      List<Long> portWiseDetailIds, Boolean isActive);

  public List<DischargingPlanCommingleDetails>
      findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(
          DischargingPlanPortWiseDetails portWiseDetails);

  @Query(
      "from DischargingPlanCommingleDetails dr where dr.dischargingPlanPortWiseDetails.id in ?1 and dr.isActive = true order by dr.id")
  public List<DischargingPlanCommingleDetails>
      findByDischargingPlanPortWiseDetailsInAndIsActiveTrueOrderById(List<Long> ids);
}
