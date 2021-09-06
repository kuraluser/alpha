/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.PortDischargingPlanRobDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortDischargingPlanRobDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanRobDetails, Long> {

  public List<PortDischargingPlanRobDetails> findByDischargingInformationAndIsActive(
      Long fkId, Boolean isActive);

  @Query(
      "FROM PortDischargingPlanRobDetails PL INNER JOIN DischargingInformation LI ON PL.dischargingInformation = LI.id AND LI.loadablePatternXId = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortDischargingPlanRobDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortDischargingPlanRobDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortDischargingPlanRobDetails SET isActive = false WHERE dischargingInformation = ?1")
  public void deleteByDischargingInformationId(Long dischargingInfoId);

  @Transactional
  @Modifying
  @Query(
      "Update PortDischargingPlanRobDetails set quantity = ?1, quantityM3 = ?2"
          + " where tankXId = ?3")
  public void updatePortDischargingPlanRobDetailsRepository(
      @Param("quantity") BigDecimal quantity,
      @Param("quantity_m3") BigDecimal quantityM3,
      @Param("tank_xid") Long tankXId);

  @Modifying
  @Query(
      value =
          "insert into PortDischargingPlanRobDetails (discharging_information_xid, tank_xid, quantity_mt, density,"
              + "colour_code, actual_planned, arrival_departutre) values"
              + " (:discharging_information_xid, :tank_xid, :quantity_mt, :density, :colour_code, :actual_planned, :arrival_departutre)",
      nativeQuery = true)
  void insertPortDischargingPlanRobDetails(
      @Param("discharging_information_xid") Long dischargingInfoId,
      @Param("tank_xid") Long tankXid,
      @Param("quantity_mt") Long quantity_mt,
      @Param("density") Long density,
      @Param("colour_code") String colourCode,
      @Param("actual_planned") Long actual_planned,
      @Param("arrival_departutre") Long arrival_departutre);
}
