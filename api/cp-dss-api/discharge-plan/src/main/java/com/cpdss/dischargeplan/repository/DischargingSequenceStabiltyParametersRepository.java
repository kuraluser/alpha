/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingSequenceStabilityParameters;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DischargingSequenceStabiltyParametersRepository
    extends CommonCrudRepository<DischargingSequenceStabilityParameters, Long> {

  public List<DischargingSequenceStabilityParameters> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE DischargingSequenceStabilityParameters SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  public List<DischargingSequenceStabilityParameters>
      findByDischargingInformationAndIsActiveOrderByTime(
          DischargeInformation dischargingInformation, Boolean isActive);

  @Modifying
  @Transactional
  @Query(
      "UPDATE DischargingSequenceStabilityParameters SET isActive = false WHERE dischargingInformation.id = ?1")
  public void deleteByDischargingInformationId(Long dischargingInfoId);
}
