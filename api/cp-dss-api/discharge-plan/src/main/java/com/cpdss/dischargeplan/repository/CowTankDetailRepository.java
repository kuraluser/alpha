/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.CowPlanDetail;
import com.cpdss.dischargeplan.entity.CowTankDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CowTankDetailRepository extends CommonCrudRepository<CowTankDetail, Long> {

  @Query("FROM CowPlanDetail cpd WHERE cpd.dischargeInformation.id = ?1 and cpd.isActive = true")
  Optional<CowPlanDetail> findByDischargingId(Long var1);

  @Modifying
  @Transactional
  @Query("UPDATE CowTankDetail SET isActive = false WHERE dischargingXid = ?1")
  void deleteByDischargingInformationId(Long dischargingInformationId);

  List<CowTankDetail> findByDischargingXidAndIsActiveTrue(Long dischargingId);
}
