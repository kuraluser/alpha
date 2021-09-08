/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastDetails;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PortDischargingPlanBallastDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanBallastDetails, Long> {

  public List<PortDischargingPlanBallastDetails> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);
}
