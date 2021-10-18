/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.CowPlanDetail;
import com.cpdss.dischargeplan.entity.CowTankDetail;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface CowTankDetailRepository extends CommonCrudRepository<CowTankDetail, Long> {

  @Query("FROM CowPlanDetail cpd WHERE cpd.dischargeInformation.id = ?1 and cpd.isActive = true")
  Optional<CowPlanDetail> findByDischargingId(Long var1);
}
