/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingInformationStatus;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
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

  List<DischargeInformation> findAllByVesselXidAndVoyageXidAndIsActiveTrue(Long id1, Long id2);

  @Transactional
  @Modifying
  @Query("UPDATE DischargeInformation SET arrivalStatusId = ?1 WHERE id = ?2")
  public void updateDischargeInformationArrivalStatus(Long arrivalStatus, Long id);

  @Transactional
  @Modifying
  @Query("UPDATE DischargeInformation SET departureStatusId = ?1 WHERE id = ?2")
  public void updateDischargeInformationDepartureStatus(Long depStatus, Long id);

  @Transactional
  @Modifying
  @Query("UPDATE DischargeInformation li SET li.isDischargeInformationComplete = ?2 WHERE id = ?1")
  void updateDischargeInformationCompleteStatus(Long id, boolean status);

  @Transactional
  @Modifying
  @Query("UPDATE DischargeInformation SET dischargingPlanDetailsFromAlgo = ?2 WHERE id = ?1")
  public void updateDischargingPlanDetailsFromAlgo(Long id, String dischargingPlanDetailsFromAlgo);

  @Transactional
  @Modifying
  @Query("UPDATE DischargeInformation SET dischargingInformationStatus = ?1 WHERE id = ?2")
  public void updateDischargingInformationStatus(
      DischargingInformationStatus dischargingInformationStatus, Long id);

  @Transactional
  @Modifying
  @Query(
      "UPDATE DischargeInformation SET dischargingInformationStatus = ?1, arrivalStatusId = ?2, departureStatusId = ?3 WHERE id = ?4")
  public void updateDischargingInformationStatuses(
      DischargingInformationStatus dischargingInformationStatus,
      Long arrivalStatus,
      Long departureStatus,
      Long id);

  @Transactional
  @Modifying
  @Query("UPDATE DischargeInformation li SET li.isDischargingSequenceGenerated = ?2 WHERE id = ?1")
  void updateIsDischargingSequenceGeneratedStatus(Long id, boolean status);

  @Transactional
  @Modifying
  @Query("UPDATE DischargeInformation li SET li.isDischargingPlanGenerated = ?2 WHERE id = ?1")
  void updateIsDischargingPlanGeneratedStatus(Long id, boolean status);

  @Transactional
  @Modifying
  @Query("UPDATE DischargeInformation SET startTime = ?1, finalTrim=?2 WHERE id = ?3")
  void updateStartTimeAndFinalTrim(LocalTime startTime, BigDecimal finalTrim, Long id);

  @Transactional
  @Modifying
  @Query("UPDATE DischargeInformation SET initialTrim = ?1, maximumTrim=?2 WHERE id = ?3")
  void updateInitialTrimAndMaximumTrim(BigDecimal initialTrim, BigDecimal maximumTrim, Long id);

  @Transactional
  @Modifying
  @Query(
      "UPDATE DischargeInformation SET dischargeCommingleCargoSeparately = ?1, dischargeSlopTankFirst=?2 WHERE id = ?3")
  void updateCommingledCargoAndSlopTankFirst(
      Boolean dischargeCommingleCargoSeparately, Boolean dischargeSlopTankFirst, Long id);

  @Transactional
  @Modifying
  @Query(
      "UPDATE DischargeInformation SET isTrackStartEndStage = ?1, isTrackGradeSwitching=?2 WHERE id = ?3")
  void updateIsTrackStartEndAndTrackGradeSwitching(
      boolean trackStartEndStage, boolean trackGradeSwitch, Long id);

  @Transactional
  @Modifying
  @Query(
      "UPDATE DischargeInformation SET timeForFinalStripping = ?1, freshOilWashing=?2,timeForSlopDischarging = ?3, timeForDryCheck=?4 WHERE id = ?5")
  void updateFinalStrippingAndFreshOilWashAndSlopDischargingAndTimeForDryCheck(
      BigDecimal timeForFinalStripping,
      BigDecimal freshOilWashing,
      BigDecimal timeForSlopDischarging,
      BigDecimal timeForDryCheck,
      Long id);

  @Query("Update DischargeInformation SET isDischargingInstructionsComplete = ?1 WHERE id = ?2")
  @Modifying
  @Transactional
  void updateDischargeInstructionStatus(boolean status, Long id);
}
