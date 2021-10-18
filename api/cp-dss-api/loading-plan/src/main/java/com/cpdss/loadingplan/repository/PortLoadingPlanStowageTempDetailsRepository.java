/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageTempDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortLoadingPlanStowageTempDetailsRepository
    extends CommonCrudRepository<PortLoadingPlanStowageTempDetails, Long> {

  public List<PortLoadingPlanStowageTempDetails> findByLoadingInformationAndIsActive(
      Long loadingInfoId, Boolean isActive);

  @Query(
      "FROM PortLoadingPlanStowageTempDetails PL INNER JOIN LoadingInformation LI ON PL.loadingInformation = LI.id AND LI.loadablePatternXId = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3 AND LI.isActive = ?3 ORDER BY PL.id")
  public List<PortLoadingPlanStowageTempDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortLoadingPlanStowageTempDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortLoadingPlanStowageTempDetails SET isActive = false WHERE loadingInformation = ?1")
  public void deleteByLoadingInformationId(Long loadingInfoId);

  @Transactional
  @Modifying
  @Query(
      "Update PortLoadingPlanStowageTempDetails set quantity = ?1, ullage = ?2, quantityM3 = ?3, api = ?4, temperature = ?5, correctedUllage = ?6, fillingPercentage = ?7, correctionFactor = ?8"
          + " where tankXId = ?9 and loading_information_xid =?10 and arrival_departutre =?11")
  public void updatePortLoadingPlanStowageDetailsRepository(
      @Param("quantity") BigDecimal quantity,
      @Param("ullage") BigDecimal ullage,
      @Param("quantity_m3") BigDecimal quantity_m3,
      @Param("api") BigDecimal api,
      @Param("temperature") BigDecimal temperature,
      @Param("correctedUllage") BigDecimal correctedUllage,
      @Param("fillingPercentage") BigDecimal fillingPercentage,
      @Param("correctionFactor") BigDecimal correctionFactor,
      @Param("tank_xid") Long tankXId,
      @Param("loading_information_xid") Long loadingId,
      @Param("arrival_departutre") Long arrivalDepartutre);

  public List<PortLoadingPlanStowageTempDetails>
      findByLoadingInformationAndConditionTypeAndIsActive(
          Long loadingInfoId, Integer conditionType, boolean b);

  @Transactional
  @Modifying
  @Query(
      "Update PortLoadingPlanStowageTempDetails set isActive = false WHERE loadingInformation = ?1 and conditionType = ?2 and isActive = true")
  public void deleteExistingByLoadingInfoAndConditionType(
      Long loadingInfoId, Integer conditionType);

  @Modifying
  @Query(
      value =
          "insert into PortLoadingPlanStowageTempDetails (loading_information_xid, tank_xid, "
              + "temperature, corrected_ullage,  quantity_mt, filling_percentage, api, cargo_nomination_xid, port_xid,"
              + "port_rotation_xid, actual_planned, arrival_departutre, correction_factor) values"
              + " (:loading_information_xid, :tank_xid,  "
              + "  :temperature, :corrected_ullage,  :quantity_mt, :filling_percentage, :api, :cargo_nomination_xid, :port_xid,"
              + "  :port_rotation_xid, :actual_planned, :arrival_departutre, :correction_factor)",
      nativeQuery = true)
  void insertPortLoadingPlanStowageTempDetails(
      @Param("loading_information_xid") Long loadingInfoId,
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
