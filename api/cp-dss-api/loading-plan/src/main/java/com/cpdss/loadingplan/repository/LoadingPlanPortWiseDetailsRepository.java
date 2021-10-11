/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingSequence;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingPlanPortWiseDetailsRepository
    extends CommonCrudRepository<LoadingPlanPortWiseDetails, Long> {

  List<LoadingPlanPortWiseDetails> findByLoadingSequenceAndIsActiveTrueOrderById(
      LoadingSequence loadingSequence);

  @Modifying
  @Transactional
  @Query("UPDATE LoadingPlanPortWiseDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query("UPDATE LoadingPlanPortWiseDetails SET isActive = false WHERE loadingSequence = ?1")
  public void deleteByLoadingSequence(LoadingSequence loadingSequence);

  @Query(
      "SELECT LPPWD FROM LoadingPlanPortWiseDetails LPPWD WHERE LPPWD.loadingSequence.id IN "
          + "(SELECT LS.id FROM LoadingSequence LS WHERE LS.loadingInformation.id = ?1 AND LS.toLoadicator = ?2 AND LS.isActive = true) "
          + " AND LPPWD.isActive = ?3")
  public List<LoadingPlanPortWiseDetails> findByLoadingInformationIdAndToLoadicatorAndIsActive(
      Long loadingInfoId, Boolean toLoadicator, Boolean isActive);

  @Query(
          "SELECT LPPWD FROM LoadingPlanPortWiseDetails LPPWD WHERE LPPWD.loadingSequence.id IN "
                  + "(SELECT LS.id FROM LoadingSequence LS WHERE LS.loadingInformation.id = ?1)")
  public List<LoadingPlanPortWiseDetails> findByLoadingInformationId(
          Long loadingInfoId);
}
