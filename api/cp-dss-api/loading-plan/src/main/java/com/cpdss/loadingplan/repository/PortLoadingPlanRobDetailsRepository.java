/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.PortLoadingPlanRobDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortLoadingPlanRobDetailsRepository
    extends CommonCrudRepository<PortLoadingPlanRobDetails, Long> {

  public List<PortLoadingPlanRobDetails> findByLoadingInformationAndIsActive(
      Long fkId, Boolean isActive);
  
  public List<PortLoadingPlanRobDetails> findByLoadingInformationAndConditionTypeAndIsActive(
	      Long fkId, Integer conditionType, Boolean isActive);

  @Query(
      "FROM PortLoadingPlanRobDetails PL INNER JOIN LoadingInformation LI ON PL.loadingInformation = LI.id AND LI.loadablePatternXId = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3 ORDER BY PL.id")
  public List<PortLoadingPlanRobDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortLoadingPlanRobDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query("UPDATE PortLoadingPlanRobDetails SET isActive = false WHERE loadingInformation = ?1")
  public void deleteByLoadingInformationId(Long loadingInfoId);

  @Transactional
  @Modifying
  @Query(
      "Update PortLoadingPlanRobDetails set quantity = ?1, quantityM3 = ?2"
          + " where tankXId = ?3 and loadingInformation = ?4 and conditionType = ?5 and valueType = ?6 and isActive = true")
  public void updatePortLoadingPlanRobDetailsRepository(
      @Param("quantity") BigDecimal quantity,
      @Param("quantity_m3") BigDecimal quantityM3,
      @Param("tank_xid") Long tankXId,
      @Param("loading_info_id") Long loadingInfoId,
      @Param("condition_type") Integer conditionType,
      @Param("value_type") Integer valueType);

  @Modifying
  @Query(
      value =
          "insert into PortLoadingPlanRobDetails (loading_information_xid, tank_xid, quantity_mt, density,"
              + "colour_code, actual_planned, arrival_departutre) values"
              + " (:loading_information_xid, :tank_xid, :quantity_mt, :density, :colour_code, :actual_planned, :arrival_departutre)",
      nativeQuery = true)
  void insertPortLoadingPlanRobDetails(
      @Param("loading_information_xid") Long loadingInfoId,
      @Param("tank_xid") Long tankXid,
      @Param("quantity_mt") Long quantity_mt,
      @Param("density") Long density,
      @Param("colour_code") String colourCode,
      @Param("actual_planned") Long actual_planned,
      @Param("arrival_departutre") Long arrival_departutre);
}
