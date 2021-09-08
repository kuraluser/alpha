/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.PortDischargingPlanRobDetails;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PortDischargingPlanRobDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanRobDetails, Long> {

  public List<PortDischargingPlanRobDetails> findByDischargingInformationAndIsActive(
      Long fkId, Boolean isActive);
}
