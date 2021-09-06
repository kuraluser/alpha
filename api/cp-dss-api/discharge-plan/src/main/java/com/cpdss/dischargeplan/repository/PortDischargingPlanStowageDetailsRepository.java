/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortDischargingPlanStowageDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanStowageDetails, Long> {

  public List<PortDischargingPlanStowageDetails> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);

  @Query(
      "FROM PortDischargingPlanStowageDetails PL INNER JOIN DischargingInformation LI ON PL.dischargingInformation.id = LI.id AND LI.loadablePatternXId = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortDischargingPlanStowageDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortDischargingPlanStowageDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortDischargingPlanStowageDetails SET isActive = false WHERE dischargingInformation.id = ?1")
  public void deleteByDischargingInformationId(Long dischargingInfoId);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanStowageDetails set quantity = ?1, ullage = ?2, quantityM3 = ?3, api = ?4, temperature = ?5"
          + " where tankXId = ?4 and isActive = ?5 and portXId= ?6 and discharging_information_xid =?7 and arrival_departutre =?8")
  public void updatePortDischargingPlanStowageDetailsRepository(
      @Param("quantity") BigDecimal quantity,
      @Param("ullage") BigDecimal ullage,
      @Param("quantity_m3") BigDecimal quantity_m3,
      @Param("api") BigDecimal api,
      @Param("temperature") BigDecimal temperature,
      @Param("tank_xid") Long tankXId,
      @Param("is_active") Boolean isActive,
      @Param("port_xid") Long portXId,
      @Param("discharging_information_xid") Long dischargingId,
      @Param("arrival_departutre") Long arrivalDepartutre);

  public List<PortDischargingPlanStowageDetails> findByPortRotationXIdInAndIsActive(
      List<Long> portRotationId, Boolean isActive);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanStowageDetails set isActive = false WHERE dischargingInformation.id = ?1 and conditionType = ?2 and isActive = true")
  public void deleteExistingByDischargingInfoAndConditionType(
      Long dischargingInfoId, Integer conditionType);
}
