/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageDetails;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PortDischargingPlanStowageDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanStowageDetails, Long> {

  public List<PortDischargingPlanStowageDetails> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);
}
