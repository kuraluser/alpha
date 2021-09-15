/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastTempDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanBallastTempDetails set sg =?1, corrected_ullage =?2, color_code =?3, quantity = ?4, sounding = ?5, quantityM3 = ?6"
          + " where tankXId = ?7 and discharging_information_xid =?8 and arrival_departutre =?9")
  public void updateDischargingPlanBallastDetailsRepository(
      @Param("sg") BigDecimal sg,
      @Param("corrected_ullage") BigDecimal corrected_ullage,
      @Param("color_code") String colorCode,
      @Param("quantity") BigDecimal quantity,
      @Param("sounding") BigDecimal sounding,
      @Param("quantity_m3") BigDecimal quantityM3,
      @Param("tank_xid") Long tankXId,
      @Param("discharging_information_xid") Long dischargingId,
      @Param("arrival_departutre") Long arrivalDepartutre);

  public List<PortDischargingPlanBallastTempDetails>
      findByDischargingInformationAndConditionTypeAndIsActive(
          Long dischargingInfoId, Integer conditionType, boolean b);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanBallastTempDetails set isActive = false WHERE dischargingInformation = ?1 and conditionType = ?2 and isActive = true")
  public void deleteExistingByDischargingInfoAndConditionType(
      Long dischargingInfoId, Integer conditionType);

  @Modifying
  @Query(
      value =
          "insert into PortDischargingPlanBallastTempDetails (discharging_information_xid, port_xid, port_rotation_xid, tank_xid, temperature, "
              + "corrected_ullage,  quantity_mt, observedM3, filling_percentage, sounding, actual_planned, arrival_departutre, colour_code, sg) values"
              + "(:discharging_information_xid, :port_xid, :port_rotation_xid, :tank_xid, :temperature,"
              + ":corrected_ullage,  :quantity_mt, :observedM3, :filling_percentage, :sounding, :actual_planned, :arrival_departutre, :colour_code, :sg)",
      nativeQuery = true)
  void insertPortDischargingPlanBallastTempDetails(
      @Param("discharging_information_xid") Long dischargingInfoId,
      @Param("port_rotation_xid") Long port_rotation_xid,
      @Param("port_xid") Long port_xid,
      @Param("tank_xid") Long tankXid,
      @Param("temperature") Long temperature,
      @Param("corrected_ullage") Long corrected_ullage,
      @Param("quantity_mt") Long quantity_mt,
      @Param("observedM3") Long observedM3,
      @Param("filling_percentage") Long filling_percentage,
      @Param("sounding") Long sounding,
      @Param("actual_planned") Long actual_planned,
      @Param("arrival_departutre") Long arrival_departutre,
      @Param("colour_code") String colour_code,
      @Param("sg") Long sg);
}
