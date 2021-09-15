/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PortDischargingPlanBallastDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanBallastDetails, Long> {

  public List<PortDischargingPlanBallastDetails> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);

  @Query(
      "FROM PortDischargingPlanBallastDetails PL INNER JOIN DischargeInformation DI ON PL.dischargingInformation.id = DI.id AND DI.dischargingPatternXid = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortDischargingPlanBallastDetails> findByPatternIdAndPortRotationIdAndIsActive(
      long patternId, long portRotationId, boolean b);
}
