/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageDetails;

@Repository
public interface PortDischargingPlanStowageDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanStowageDetails, Long> {

  public List<PortDischargingPlanStowageDetails> findByDischargingInformationAndIsActive(
      DischargeInformation dischargingInformation, Boolean isActive);

}
