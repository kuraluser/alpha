/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingPlanRobDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingPlanRobDetailsRepository
    extends CommonCrudRepository<LoadingPlanRobDetails, Long> {

  List<LoadingPlanRobDetails> findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Modifying
  @Transactional
  @Query("UPDATE LoadingPlanRobDetails SET isActive = false WHERE loadingPlanPortWiseDetails = ?1")
  public void deleteByLoadingPlanPortWiseDetails(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);

  @Query(
      "SELECT LPRD FROM LoadingPlanRobDetails LPRD WHERE LPRD.loadingPlanPortWiseDetails.id IN ?1 AND LPRD.isActive = ?2")
  public List<LoadingPlanRobDetails> findByPortWiseDetailIdsAndIsActive(
      List<Long> portWiseDetailIds, Boolean isActive);

  @Transactional
  @Modifying
  @Query(
      "Update LoadingPlanRobDetails set quantity = ?1, quantityM3 = ?2"
          + " where tankXId = ?3 and isActive = ?4")
  public void updateLoadingPlanRobDetailsRepository(
      @Param("quantity") BigDecimal quantity,
      @Param("quantity_m3") BigDecimal quantityM3,
      @Param("tank_xid") Long tankXId,
      @Param("is_active") Boolean isActive);
}
