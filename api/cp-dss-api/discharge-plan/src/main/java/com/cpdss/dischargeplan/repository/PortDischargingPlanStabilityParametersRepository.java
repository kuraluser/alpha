/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStabilityParameters;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortDischargingPlanStabilityParametersRepository
    extends CommonCrudRepository<PortDischargingPlanStabilityParameters, Long> {

  public List<PortDischargingPlanStabilityParameters> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortDischargingPlanStabilityParameters SET isActive = false WHERE dischargingInformation.id = ?1")
  public void deleteByDischargingInformationId(Long dsInfoId);

  default Optional<PortDischargingPlanStabilityParameters> getDataByInfoAndConditionAndValueTypes(
      Long fk1, Integer val1, Integer val2) {
    return this.findByDischargingInformationIdAndConditionTypeAndValueTypeAndIsActiveTrue(fk1, val1, val2);
  }

  Optional<PortDischargingPlanStabilityParameters>
      findByDischargingInformationIdAndConditionTypeAndValueTypeAndIsActiveTrue(
          Long fk1, Integer val1, Integer val2);
}
