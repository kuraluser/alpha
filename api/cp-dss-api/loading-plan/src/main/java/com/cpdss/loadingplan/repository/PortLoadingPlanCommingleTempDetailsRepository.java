/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.PortLoadingPlanCommingleTempDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** @author pranav.k */
@Repository
public interface PortLoadingPlanCommingleTempDetailsRepository
    extends CommonCrudRepository<PortLoadingPlanCommingleTempDetails, Long> {
  public List<PortLoadingPlanCommingleTempDetails> findByLoadingInformationAndIsActive(
      Long loadingInfoId, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortLoadingPlanCommingleTempDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortLoadingPlanCommingleTempDetails SET isActive = false WHERE loadingInformation = ?1")
  public void deleteByLoadingInformationId(Long loadingInfoId);

  @Transactional
  @Modifying
  @Query(
      "Update PortLoadingPlanCommingleTempDetails set api = ?1, fillingRatio = ?2, quantity = ?3, quantityM3 = ?4, temperature = ?5,"
          + " ullage = ?6 where tankXId = ?7 and loading_information_xid =?8 and arrival_departutre =?9")
  public void updateLoadingPlanCommingleDetails(
      String api,
      String fillingRatio,
      String quantity,
      String quantityM3,
      String temperature,
      String ullage,
      Long tankXId,
      Long loadingId,
      Integer arrivalDepartutre);

  public List<PortLoadingPlanCommingleTempDetails>
      findByLoadingInformationAndConditionTypeAndIsActive(
          Long loadingInfoId, Integer conditionType, boolean b);

  @Transactional
  @Modifying
  @Query(
      "Update PortLoadingPlanCommingleTempDetails set isActive = false WHERE loadingInformation = ?1 and conditionType = ?2 and isActive = true")
  public void deleteExistingByLoadingInfoAndConditionType(
      Long loadingInfoId, Integer conditionType);
}
