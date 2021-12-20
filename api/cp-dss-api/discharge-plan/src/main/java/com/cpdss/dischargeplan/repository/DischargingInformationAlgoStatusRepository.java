/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
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

  Optional<DischargingInformationAlgoStatus> findByProcessIdAndDischargeInformationAndIsActiveTrue(
      String processId, Long dischargingInfoId);

  @Transactional
  @Modifying
  @Query(
      "UPDATE DischargingInformationAlgoStatus SET isActive = false WHERE dischargeInformation.id = ?1 AND processId = ?2")
  public void deleteDischargingInformationAlgoStatus(Long dischargingInfoId, String processId);

  Optional<DischargingInformationAlgoStatus>
      findByProcessIdAndDischargeInformationAndConditionTypeAndIsActiveTrue(
          String processId, DischargeInformation dischargingInformation, Integer conditionType);

  @Transactional
  @Modifying
  @Query(
      "UPDATE DischargingInformationAlgoStatus SET dischargingInformationStatus.id = ?1 WHERE dischargeInformation.id = ?2 AND processId = ?3")
  public void updateDischargingInformationAlgoStatus(
      Long dischargingInformationStatusId, Long dischargingInfoId, String processId);

  Optional<DischargingInformationAlgoStatus>
      findByProcessIdAndDischargeInformationIdAndIsActiveTrue(String pId, Long dId);

  Optional<DischargingInformationAlgoStatus>
      findByProcessIdAndDischargeInformationIdAndConditionTypeAndIsActiveTrue(
          String pId, Long dId, Integer tId);

  @Query(
      value =
          "select * from discharging_information_algo_status where discharging_information_xid= ?1 and last_modified_date_time is not null order by last_modified_date_time desc limit 1",
      nativeQuery = true)
  Optional<DischargingInformationAlgoStatus> findByDischargeInformationId(Long id);
}
