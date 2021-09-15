/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.PortDischargingPlanRobDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PortDischargingPlanRobDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanRobDetails, Long> {

  public List<PortDischargingPlanRobDetails> findByDischargingInformationAndIsActive(
      Long fkId, Boolean isActive);

  @Query(
      "FROM PortDischargingPlanRobDetails PL INNER JOIN DischargeInformation LI ON PL.dischargingInformation = LI.id AND LI.dischargingPatternXid = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortDischargingPlanRobDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);
}
