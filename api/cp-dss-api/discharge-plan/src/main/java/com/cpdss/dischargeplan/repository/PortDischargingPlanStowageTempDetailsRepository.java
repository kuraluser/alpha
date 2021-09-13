/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageTempDetails;

@Repository
public interface PortDischargingPlanStowageTempDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanStowageTempDetails, Long> {

  public List<PortDischargingPlanStowageTempDetails> findByDischargingInformationAndIsActive(
      Long dischargingInfoId, Boolean isActive);

  @Query(
      "FROM PortDischargingPlanStowageTempDetails PL INNER JOIN DischargeInformation LI ON PL.dischargingInformation = LI.id AND LI.dischargingPatternXid = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortDischargingPlanStowageTempDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortDischargingPlanStowageTempDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortDischargingPlanStowageTempDetails SET isActive = false WHERE dischargingInformation = ?1")
  public void deleteByDischargingInformationId(Long dischargingInfoId);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanStowageTempDetails set quantity = ?1, ullage = ?2, quantityM3 = ?3, api = ?4, temperature = ?5"
          + " where tankXId = ?6 and discharging_information_xid =?7 and arrival_departutre =?8")
  public void updatePortDischargingPlanStowageDetailsRepository(
      @Param("quantity") BigDecimal quantity,
      @Param("ullage") BigDecimal ullage,
      @Param("quantity_m3") BigDecimal quantity_m3,
      @Param("api") BigDecimal api,
      @Param("temperature") BigDecimal temperature,
      @Param("tank_xid") Long tankXId,
      @Param("discharging_information_xid") Long dischargingId,
      @Param("arrival_departutre") Long arrivalDepartutre);

  public List<PortDischargingPlanStowageTempDetails>
      findByDischargingInformationAndConditionTypeAndIsActive(
          Long dischargingInfoId, Integer conditionType, boolean b);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanStowageTempDetails set isActive = false WHERE dischargingInformation = ?1 and conditionType = ?2 and isActive = true")
  public void deleteExistingByDischargingInfoAndConditionType(
      Long dischargingInfoId, Integer conditionType);

  @Modifying
  @Query(
      value =
          "insert into PortDischargingPlanStowageTempDetails (discharging_information_xid, tank_xid, "
              + "temperature, corrected_ullage,  quantity_mt, filling_percentage, api, cargo_nomination_xid, port_xid,"
              + "port_rotation_xid, actual_planned, arrival_departutre, correction_factor) values"
              + " (:discharging_information_xid, :tank_xid,  "
              + "  :temperature, :corrected_ullage,  :quantity_mt, :filling_percentage, :api, :cargo_nomination_xid, :port_xid,"
              + "  :port_rotation_xid, :actual_planned, :arrival_departutre, :correction_factor)",
      nativeQuery = true)
  void insertPortDischargingPlanStowageTempDetails(
      @Param("discharging_information_xid") Long dischargingInfoId,
      @Param("tank_xid") Long tankXid,
      @Param("temperature") Long temperature,
      @Param("corrected_ullage") Long corrected_ullage,
      @Param("quantity_mt") Long quantity_mt,
      @Param("filling_percentage") Long filling_percentage,
      @Param("api") Long api,
      @Param("cargo_nomination_xid") Long cargo_nomination_xid,
      @Param("port_xid") Long port_xid,
      @Param("port_rotation_xid") Long port_rotation_xid,
      @Param("actual_planned") Long actual_planned,
      @Param("arrival_departutre") Long arrival_departutre,
      @Param("correction_factor") Long correction_factor);
}
