/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DischargeInformationRepository
    extends CommonCrudRepository<DischargeInformation, Long> {

  Optional<DischargeInformation> findByIdAndIsActiveTrue(Long var1);

  Optional<DischargeInformation> findBySynopticTableXidAndIsActiveTrue(Long var1);

  Optional<DischargeInformation> findByVesselXidAndVoyageXidAndPortRotationXidAndIsActiveTrue(
      Long var1, Long var2, Long var3);

  Optional<DischargeInformation> findByIdAndIsActiveAndVesselXid(
      long dischargeStudyId, boolean isActive, long vesselId);

  @Transactional
  @Modifying
  @Query("UPDATE DischargeInformation SET arrivalStatusId = ?1 WHERE id = ?2")
  public void updateDischargeInformationArrivalStatus(Long arrivalStatus, Long id);

  @Transactional
  @Modifying
  @Query("UPDATE DischargeInformation SET departureStatusId = ?1 WHERE id = ?2")
  public void updateDischargeInformationDepartureStatus(Long depStatus, Long id);
}
