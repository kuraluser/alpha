/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.PortDischargingPlanRobDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortDischargingPlanRobDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanRobDetails, Long> {

  public List<PortDischargingPlanRobDetails> findByDischargingInformationAndIsActive(
      Long fkId, Boolean isActive);

  @Query(
      "FROM PortDischargingPlanRobDetails PL INNER JOIN DischargeInformation LI ON PL.dischargingInformation = LI.id AND LI.dischargingPatternXid = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortDischargingPlanRobDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);

  public List<PortDischargingPlanRobDetails>
      findByDischargingInformationAndConditionTypeAndIsActive(
          long loadingInformationId, int arrivalDepartutre, boolean b);

  public List<PortDischargingPlanRobDetails>
      findByDischargingInformationAndConditionTypeAndValueTypeAndIsActive(
          Long fkId, Integer conditionType, Integer valueType, Boolean isActive);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortDischargingPlanRobDetails SET isActive = false WHERE dischargingInformation = ?1")
  public void deleteByDischargingInformationId(Long dischargingInformation);

  /**
   * Fetching rob details
   *
   * @param portXId
   * @param portRotationXId
   * @param conditionType
   * @param isActive
   * @return List<PortDischargingPlanRobDetails>
   */
  public List<PortDischargingPlanRobDetails>
      findByPortXIdAndPortRotationXIdAndConditionTypeAndIsActive(
          Long portXId, Long portRotationXId, Integer conditionType, Boolean isActive);

  /**
   * Fetching rob details
   *
   * @param portXId
   * @param portRotationXId
   * @param conditionType
   * @param valueType
   * @param isActive
   * @return List<PortDischargingPlanRobDetails>
   */
  public List<PortDischargingPlanRobDetails>
      findByPortXIdAndPortRotationXIdAndConditionTypeAndValueTypeAndIsActive(
          Long portXId,
          Long portRotationXId,
          Integer conditionType,
          Integer valueType,
          Boolean isActive);
}
