/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails;
import com.cpdss.dischargeplan.entity.DischargingSequence;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DischargingPlanPortWiseDetailsRepository
    extends CommonCrudRepository<DischargingPlanPortWiseDetails, Long> {

  List<DischargingPlanPortWiseDetails> findByDischargingSequenceAndIsActiveTrueOrderById(
      DischargingSequence dischargingSequence);

  @Modifying
  @Transactional
  @Query("UPDATE DischargingPlanPortWiseDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE DischargingPlanPortWiseDetails SET isActive = false WHERE dischargingSequence = ?1")
  public void deleteByDischargingSequence(DischargingSequence dischargingSequence);

  @Query(
      "SELECT LPPWD FROM DischargingPlanPortWiseDetails LPPWD WHERE LPPWD.dischargingSequence.id IN "
          + "(SELECT LS.id FROM DischargingSequence LS WHERE LS.dischargeInformation.id = ?1 AND LS.toLoadicator = ?2 AND LS.isActive = true) "
          + " AND LPPWD.isActive = ?3")
  public List<DischargingPlanPortWiseDetails>
      findByDischargeInformationIdAndToLoadicatorAndIsActive(
          Long dischargingInfoId, Boolean toLoadicator, Boolean isActive);
}
