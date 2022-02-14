/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortDischargingPlanStowageDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanStowageDetails, Long> {

  public List<PortDischargingPlanStowageDetails> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);

  @Query(
      "FROM PortDischargingPlanStowageDetails PL INNER JOIN DischargeInformation DI ON PL.dischargingInformation.id = DI.id AND DI.dischargingPatternXid = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortDischargingPlanStowageDetails> findByPatternIdAndPortRotationIdAndIsActive(
      long patternId, long portRotationId, boolean b);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanStowageDetails set isActive = false WHERE dischargingInformation.id = ?1 and conditionType = ?2 and valueType = ?3 and isActive = true")
  public void deleteExistingByDischargingInfoAndConditionTypeAndValueType(
      Long loadingInfoId, Integer conditionType, Integer valueType);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortDischargingPlanStowageDetails SET isActive = false WHERE dischargingInformation.id = ?1")
  public void deleteByDischargingInformationId(Long loadingInfoId);

  List<PortDischargingPlanStowageDetails>
      findByPortRotationXIdAndConditionTypeAndValueTypeAndIsActive(
          Long portRotationId, Integer conditionType, Integer valueType, Boolean isActive);

  @Query(
      "FROM PortDischargingPlanStowageDetails pdpsd WHERE pdpsd.dischargingInformation.id = ?1 AND pdpsd.conditionType = 2 AND pdpsd.valueType = 1 AND pdpsd.isActive = true")
  List<PortDischargingPlanStowageDetails> findCargoHistoryData(Long infoId);

  /**
   * Fetches list of PortDischargingPlanStowageDetails entities
   *
   * @param dischargeInformation discharge information entity
   * @param conditionType arrival or departure condition
   * @param valueType actual or planned value
   * @return list of PortDischargingPlanStowageDetails entities
   */
  List<PortDischargingPlanStowageDetails>
      findByDischargingInformationAndConditionTypeAndValueTypeAndIsActiveTrue(
          DischargeInformation dischargeInformation, int conditionType, Integer valueType);
}
