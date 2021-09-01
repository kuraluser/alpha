/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.PortLoadingPlanBallastTempDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortLoadingPlanBallastTempDetailsRepository
    extends CommonCrudRepository<PortLoadingPlanBallastTempDetails, Long> {

  public List<PortLoadingPlanBallastTempDetails> findByLoadingInformationAndIsActive(
      LoadingInformation loadingInformation, Boolean isActive);

  @Query(
      "FROM PortLoadingPlanBallastTempDetails PL INNER JOIN LoadingInformation LI ON PL.loadingInformation.id = LI.id AND LI.loadablePatternXId = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortLoadingPlanBallastTempDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortLoadingPlanBallastTempDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortLoadingPlanBallastTempDetails SET isActive = false WHERE loadingInformation.id = ?1")
  public void deleteByLoadingInformationId(Long loadingInfoId);

  @Transactional
  @Modifying
  @Query(
      "Update PortLoadingPlanBallastTempDetails set sg =?1, corrected_ullage =?2, color_code =?3, quantity = ?4, sounding = ?5, quantityM3 = ?6"
          + " where tankXId = ?7 and loading_information_xid =?8 and arrival_departutre =?9")
  public void updateLoadingPlanBallastDetailsRepository(
      @Param("sg") BigDecimal sg,
      @Param("corrected_ullage") BigDecimal corrected_ullage,
      @Param("color_code") String colorCode,
      @Param("quantity") BigDecimal quantity,
      @Param("sounding") BigDecimal sounding,
      @Param("quantity_m3") BigDecimal quantityM3,
      @Param("tank_xid") Long tankXId,
      @Param("loading_information_xid") Long loadingId,
      @Param("arrival_departutre") Long arrivalDepartutre);

  public List<PortLoadingPlanBallastTempDetails>
      findByLoadingInformationAndConditionTypeAndIsActive(
          LoadingInformation loadingInformation, Integer conditionType, boolean b);

  @Transactional
  @Modifying
  @Query(
      "Update PortLoadingPlanBallastTempDetails set isActive = false WHERE loadingInformation.id = ?1 and conditionType = ?2 and isActive = true")
  public void deleteExistingByLoadingInfoAndConditionType(
      Long loadingInfoId, Integer conditionType);
}
