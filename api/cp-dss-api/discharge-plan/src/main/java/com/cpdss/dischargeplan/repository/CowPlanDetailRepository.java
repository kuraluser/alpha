/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.CowPlanDetail;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface CowPlanDetailRepository extends CommonCrudRepository<CowPlanDetail, Long> {

  @Query("FROM CowPlanDetail cpd WHERE cpd.dischargeInformation.id = ?1 and cpd.isActive = true")
  Optional<CowPlanDetail> findByDischargingId(Long var1);
}
