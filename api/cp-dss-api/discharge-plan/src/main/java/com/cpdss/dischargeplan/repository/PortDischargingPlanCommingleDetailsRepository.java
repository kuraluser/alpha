/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanCommingleDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortDischargingPlanCommingleDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanCommingleDetails, Long> {

  List<PortDischargingPlanCommingleDetails> findByLoadablePatternIdAndIsActiveTrue(
      Long loadablePatternId);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortDischargingPlanCommingleDetails SET isActive = false WHERE dischargingInformation.id = ?1")
  public void deleteByDischargingInformationId(Long dischargingInfoId);

  public List<PortDischargingPlanCommingleDetails> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanCommingleDetails set isActive = false "
          + "WHERE dischargingInformation.id = ?1 and conditionType = ?2 and valueType = ?3 and isActive = true")
  public void deleteExistingByDischargingInformationAndConditionTypeAndValueType(
      Long id, Integer conditionType, int actualTypeValue);
}
