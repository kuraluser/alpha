/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortDischargingPlanBallastDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanBallastDetails, Long> {

  public List<PortDischargingPlanBallastDetails> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);

  @Query(
      "FROM PortDischargingPlanBallastDetails PL INNER JOIN DischargeInformation DI ON PL.dischargingInformation.id = DI.id AND DI.dischargingPatternXid = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortDischargingPlanBallastDetails> findByPatternIdAndPortRotationIdAndIsActive(
      long patternId, long portRotationId, boolean b);

  public List<PortDischargingPlanBallastDetails>
      findByDischargingInformationAndConditionTypeAndIsActive(
          Long id, int conditionType, boolean b);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanBallastDetails set isActive = false WHERE dischargingInformation.id = ?1 and conditionType = ?2 and valueType = ?3 and isActive = true")
  public void deleteExistingByDischargingInfoAndConditionTypeAndValueType(
      Long loadingInfoId, Integer conditionType, Integer valueType);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortDischargingPlanBallastDetails SET isActive = false WHERE dischargingInformation.id = ?1")
  public void deleteByDischargingInformationId(Long loadingInfoId);

  List<PortDischargingPlanBallastDetails>
      findByPortRotationXIdAndConditionTypeAndValueTypeAndIsActive(
          Long portRotationId, Integer arrivalDeparture, Integer actualPlanned, Boolean isActive);
}
