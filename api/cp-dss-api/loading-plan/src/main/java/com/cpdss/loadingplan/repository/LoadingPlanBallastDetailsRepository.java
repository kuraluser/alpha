/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingPlanBallastDetailsRepository
    extends CommonCrudRepository<LoadingPlanBallastDetails, Long> {

  List<LoadingPlanBallastDetails> findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Modifying
  @Transactional
  @Query(
      "UPDATE LoadingPlanBallastDetails SET isActive = false WHERE loadingPlanPortWiseDetails = ?1")
  public void deleteByLoadingPlanPortWiseDetails(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Query(
      "SELECT LPBD FROM LoadingPlanBallastDetails LPBD WHERE LPBD.loadingPlanPortWiseDetails.id IN ?1 AND LPBD.isActive = ?2")
  public List<LoadingPlanBallastDetails> findByLoadingPlanPortWiseDetailIdsAndIsActive(
      List<Long> portWiseDetailIds, Boolean isActive);

  @Transactional
  @Modifying
  @Query(
      "Update LoadingPlanBallastDetails set quantity = ?1, sounding = ?2, quantityM3 = ?3"
          + " where tankXId = ?4 and isActive = ?5")
  public void updateLoadingPlanBallastDetailsRepository(
      @Param("quantity") BigDecimal quantity,
      @Param("sounding") BigDecimal sounding,
      @Param("quantity_m3") BigDecimal quantityM3,
      @Param("tank_xid") Long tankXId,
      @Param("is_active") Boolean isActive);
}
