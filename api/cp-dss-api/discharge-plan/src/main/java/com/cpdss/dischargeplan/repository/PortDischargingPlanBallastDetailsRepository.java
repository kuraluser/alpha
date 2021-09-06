/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortDischargingPlanBallastDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanBallastDetails, Long> {

  public List<PortDischargingPlanBallastDetails> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);

  @Query(
      "FROM PortDischargingPlanBallastDetails PL INNER JOIN DischargingInformation LI ON PL.dischargingInformation.id = LI.id AND LI.loadablePatternXId = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortDischargingPlanBallastDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortDischargingPlanBallastDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortDischargingPlanBallastDetails SET isActive = false WHERE dischargingInformation.id = ?1")
  public void deleteByDischargingInformationId(Long dischargingInfoId);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanBallastDetails set quantity = ?1, sounding = ?2, quantityM3 = ?3"
          + " where tankXId = ?4 and isActive = ?5 and isActive = ?6 and discharging_information_xid =?7 and arrival_departutre =?8")
  public void updateDischargingPlanBallastDetailsRepository(
      @Param("quantity") BigDecimal quantity,
      @Param("sounding") BigDecimal sounding,
      @Param("quantity_m3") BigDecimal quantityM3,
      @Param("tank_xid") Long tankXId,
      @Param("is_active") Boolean isActive,
      @Param("port_xid") Long portXId,
      @Param("discharging_information_xid") Long dischargingId,
      @Param("arrival_departutre") Long arrivalDepartutre);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanBallastDetails set isActive = false WHERE dischargingInformation.id = ?1 and conditionType = ?2 and isActive = true")
  public void deleteExistingByDischargingInfoAndConditionType(
      Long dischargingInfoId, Integer conditionType);
}
