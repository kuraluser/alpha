/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingSequence;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DischargingSequenceRepository
    extends CommonCrudRepository<DischargingSequence, Long> {

  public List<DischargingSequence> findByDischargeInformationAndIsActive(
      DischargeInformation dischargeInformation, Boolean isActive);

  public List<DischargingSequence> findByDischargeInformationAndIsActiveOrderBySequenceNumber(
      DischargeInformation dischargeInformation, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE DischargingSequence SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query("UPDATE DischargingSequence SET isActive = false WHERE dischargeInformation.id = ?1")
  public void deleteByDischargeInformationId(Long dischargingInfoId);

  @Query(
      "SELECT DISTINCT cargoNominationXId FROM DischargingSequence WHERE dischargeInformation = ?1 AND isActive = ?2")
  public List<Long> findToBeDischargedCargoNominationIdBydischargeInformationAndIsActive(
      DischargeInformation dischargeInformation, Boolean isActive);

  List<DischargingSequence> findByDischargeInformationId(Long dischargeInformationId);
}
