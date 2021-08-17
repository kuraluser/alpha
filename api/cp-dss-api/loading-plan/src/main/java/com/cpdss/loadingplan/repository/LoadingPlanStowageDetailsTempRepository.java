/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanStowageTempDetails;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingPlanStowageDetailsTempRepository
    extends CommonCrudRepository<LoadingPlanStowageTempDetails, Long> {

  @Modifying
  @Transactional
  @Query(
      "UPDATE LoadingPlanStowageDetails SET isActive = false WHERE loadingPlanPortWiseDetails = ?1")
  public void deleteByLoadingPlanStowageTempDetails(
      LoadingPlanStowageTempDetails loadingPlanPortWiseDetails);

  @Transactional
  @Modifying
  @Query(
      "Update LoadingPlanStowageTempDetails set quantity = ?1, ullage = ?2, quantityM3 = ?3, api = ?4, temperature = ?5"
          + " where tankXId = ?4 and isActive = ?5")
  public void updateLoadingPlanStowageTempDetailsRepository(
      @Param("quantity") BigDecimal quantity,
      @Param("ullage") BigDecimal ullage,
      @Param("quantity_m3") BigDecimal quantity_m3,
      @Param("api") BigDecimal api,
      @Param("temperature") BigDecimal temperature,
      @Param("tank_xid") Long tankXId,
      @Param("is_active") Boolean isActive);
}
