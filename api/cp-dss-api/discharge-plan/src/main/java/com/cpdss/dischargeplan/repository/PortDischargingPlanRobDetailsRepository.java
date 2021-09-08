/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.PortDischargingPlanRobDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortDischargingPlanRobDetailsRepository
    extends CommonCrudRepository<PortDischargingPlanRobDetails, Long> {

  public List<PortDischargingPlanRobDetails> findByDischargingInformationAndIsActive(
      Long fkId, Boolean isActive);

  
}
