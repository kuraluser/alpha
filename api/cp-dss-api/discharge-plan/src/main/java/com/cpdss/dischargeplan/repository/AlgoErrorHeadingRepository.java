/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.AlgoErrorHeading;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AlgoErrorHeadingRepository extends CommonCrudRepository<AlgoErrorHeading, Long> {

  public List<AlgoErrorHeading> findByDischargingInformationIdAndIsActiveTrue(
      Long dischargingInfoId);

  @Modifying
  @Transactional
  @Query("UPDATE AlgoErrorHeading SET isActive = false WHERE dischargingInformation = ?1")
  public void deleteByDischargingInformation(DischargeInformation dischargingInformation);

  public List<AlgoErrorHeading> findByDischargingInformationIdAndConditionTypeAndIsActiveTrue(
      Long dischargingInfoId, Integer conditionType);

  @Modifying
  @Transactional
  @Query(
      "UPDATE AlgoErrorHeading SET isActive = false WHERE dischargingInformation = ?1 AND conditionType = ?2")
  public void deleteByDischargingInformationAndConditionType(
      DischargeInformation dischargingInformation, Integer conditionType);

  List<AlgoErrorHeading> findByDischargingInformationId(Long id);
}
