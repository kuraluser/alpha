/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.PortDischargingPlanCommingleTempDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortDischargingPlanCommingleTempDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanCommingleTempDetails, Long> {

  public List<PortDischargingPlanCommingleTempDetails>
      findByDischargingInformationAndConditionTypeAndIsActive(
          Long dischargingInformationId, Integer conditionType, Boolean isActive);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanCommingleTempDetails set isActive = false "
          + "WHERE dischargingInformation = ?1 and conditionType = ?2 and isActive = true")
  public void deleteExistingByDischargingInfoAndConditionType(
      Long dischargingInformationId, Integer conditionType);

  public List<PortDischargingPlanCommingleTempDetails> findByDischargingInformationAndIsActive(
      Long dsInfo, Boolean isActive);
}
