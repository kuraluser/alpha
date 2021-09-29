/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastTempDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortDischargingPlanBallastTempDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanBallastTempDetails, Long> {

  public List<PortDischargingPlanBallastTempDetails> findByDischargingInformationAndIsActive(
      Long dischargingInfoId, Boolean isActive);

  @Query(
      "FROM PortDischargingPlanBallastTempDetails PL INNER JOIN DischargeInformation LI ON PL.dischargingInformation = LI.id AND LI.dischargingPatternXid = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortDischargingPlanBallastTempDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortDischargingPlanBallastTempDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortDischargingPlanBallastTempDetails SET isActive = false WHERE dischargingInformation = ?1")
  public void deleteByDischargingInformationId(Long dischargingInfoId);

  public List<PortDischargingPlanBallastTempDetails>
      findByDischargingInformationAndConditionTypeAndIsActive(
          Long dischargingInfoId, Integer conditionType, boolean b);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanBallastTempDetails set isActive = false WHERE dischargingInformation = ?1 and conditionType = ?2 and isActive = true")
  public void deleteExistingByDischargingInfoAndConditionType(
      Long dischargingInfoId, Integer conditionType);
}
