/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastDetails;

@Repository
public interface PortDischargingPlanBallastDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanBallastDetails, Long> {

  public List<PortDischargingPlanBallastDetails> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);
}
