/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingDriveTank;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DischargingDriveTankRepository
    extends CommonCrudRepository<DischargingDriveTank, Long> {

  List<DischargingDriveTank> findByDischargingInformationAndIsActiveTrue(
      DischargeInformation dischargeInformation);

  @Modifying
  @Query("UPDATE DischargingDriveTank SET isActive = false WHERE dischargingInformation.id = ?1")
  void deleteByDischargingInformationId(Long dischargeInformationId);
}
