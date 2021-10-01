/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingInformationAlgoStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DischargingInformationAlgoStatusRepository
    extends CommonCrudRepository<DischargingInformationAlgoStatus, Long> {

  Optional<DischargingInformationAlgoStatus> findByProcessIdAndIsActiveTrue(String processId);

  @Transactional
  @Modifying
  @Query(
      "UPDATE DischargingInformationAlgoStatus SET dischargingInformationStatus.id = ?1 WHERE processId = ?2")
  public void updateDischargingInformationAlgoStatus(
      Long dischargingInformationStatusId, String processId);

  Optional<DischargingInformationAlgoStatus>
      findByProcessIdAndDischargingInformationIdAndIsActiveTrue(
          String processId, Long dischargingInfoId);

  @Transactional
  @Modifying
  @Query(
      "UPDATE DischargingInformationAlgoStatus SET isActive = false WHERE dischargingInformation.id = ?1 AND processId = ?2")
  public void deleteDischargingInformationAlgoStatus(Long dischargingInfoId, String processId);

  Optional<DischargingInformationAlgoStatus>
      findByProcessIdAndDischargingInformationIdAndConditionTypeAndIsActiveTrue(
          String processId, Long dischargingInfoId, Integer conditionType);

  @Transactional
  @Modifying
  @Query(
      "UPDATE DischargingInformationAlgoStatus SET dischargingInformationStatus.id = ?1 WHERE dischargeInformation.id = ?2 AND processId = ?3")
  public void updateDischargingInformationAlgoStatus(
      Long dischargingInformationStatusId, Long dischargingInfoId, String processId);
}
