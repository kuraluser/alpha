/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.PortLoadingPlanBallastDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortLoadingPlanBallastDetailsRepository
    extends CommonCrudRepository<PortLoadingPlanBallastDetails, Long> {

  public List<PortLoadingPlanBallastDetails> findByLoadingInformationAndIsActive(
      LoadingInformation loadingInformation, Boolean isActive);

  @Query(
      "FROM PortLoadingPlanBallastDetails PL INNER JOIN LoadingInformation LI ON PL.loadingInformation.id = LI.id AND LI.loadablePatternXId = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3 AND LI.isActive = ?3 ORDER BY PL.id")
  public List<PortLoadingPlanBallastDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortLoadingPlanBallastDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortLoadingPlanBallastDetails SET isActive = false WHERE loadingInformation.id = ?1")
  public void deleteByLoadingInformationId(Long loadingInfoId);

  @Transactional
  @Modifying
  @Query(
      "Update PortLoadingPlanBallastDetails set quantity = ?1, sounding = ?2, quantityM3 = ?3"
          + " where tankXId = ?4 and isActive = ?5 and isActive = ?6 and loading_information_xid =?7 and arrival_departutre =?8")
  public void updateLoadingPlanBallastDetailsRepository(
      @Param("quantity") BigDecimal quantity,
      @Param("sounding") BigDecimal sounding,
      @Param("quantity_m3") BigDecimal quantityM3,
      @Param("tank_xid") Long tankXId,
      @Param("is_active") Boolean isActive,
      @Param("port_xid") Long portXId,
      @Param("loading_information_xid") Long loadingId,
      @Param("arrival_departutre") Long arrivalDepartutre);

  @Transactional
  @Modifying
  @Query(
      "Update PortLoadingPlanBallastDetails set isActive = false WHERE loadingInformation.id = ?1 and conditionType = ?2 and isActive = true")
  public void deleteExistingByLoadingInfoAndConditionType(
      Long loadingInfoId, Integer conditionType);

  @Transactional
  @Modifying
  @Query(
      "Update PortLoadingPlanBallastDetails set isActive = false WHERE loadingInformation.id = ?1 and conditionType = ?2 and valueType = ?3 and isActive = true")
  public void deleteExistingByLoadingInfoAndConditionTypeAndValueType(
      Long loadingInfoId, Integer conditionType, Integer valueType);

  public List<PortLoadingPlanBallastDetails> findByLoadingInformationId(Long Id);
}
