/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the discharging_information database table. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discharging_information")
public class DischargeInformation extends EntityDoc {

  @Column(name = "departure_status_xid")
  private Long departureStatusId;

  @Column(name = "arrival_status_xid")
  private Long arrivalStatusId;

  @Column(name = "discharging_pattern_xid")
  private Long dischargingPatternXid;

  @Column(name = "synoptical_table_xid")
  private Long synopticTableXid;

  @Column(name = "port_rotation_xid")
  private Long portRotationXid;

  @Column(name = "port_xid")
  private Long portXid;

  @Column(name = "vessel_xid")
  private Long vesselXid;

  @Column(name = "voyage_xid")
  private Long voyageXid;

  @Column(name = "final_trim")
  private BigDecimal finalTrim;

  @Column(name = "fresh_oil_washing")
  private BigDecimal freshOilWashing;

  @Column(name = "initial_discharging_rate")
  private BigDecimal initialDischargingRate;

  @Column(name = "initial_trim")
  private BigDecimal initialTrim;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_discharging_information_complete")
  private Boolean isDischargeInformationComplete = false;

  @Column(name = "is_track_grade_switching")
  private Boolean isTrackGradeSwitching = true;

  @Column(name = "is_track_start_end_stage")
  private Boolean isTrackStartEndStage = true;

  @Column(name = "line_content_remaining")
  private BigDecimal lineContentRemaining;

  @Column(name = "max_ballast_rate")
  private BigDecimal maxBallastRate;

  @Column(name = "max_discharging_rate")
  private BigDecimal maxDischargingRate;

  @Column(name = "maximum_trim")
  private BigDecimal maximumTrim;

  @Column(name = "min_ballast_rate")
  private BigDecimal minBallastRate;

  @Column(name = "min_discharging_rate")
  private BigDecimal minDischargingRate;

  @Column(name = "notice_time_for_rate_reduction")
  private Integer noticeTimeForRateReduction;

  @Column(name = "notice_time_for_stop_discharging")
  private Integer noticeTimeForStopDischarging;

  @Column(name = "reduced_discharging_rate")
  private BigDecimal reducedDischargingRate;

  @Column(name = "start_time")
  private LocalTime startTime;

  @Column(name = "sunrise_time")
  private Timestamp sunriseTime;

  @Column(name = "sunset_time")
  private Timestamp sunsetTime;

  @Column(name = "time_for_dry_check")
  private BigDecimal timeForDryCheck;

  @Column(name = "time_for_final_stripping")
  private BigDecimal timeForFinalStripping;

  @Column(name = "time_for_slop_discharging")
  private BigDecimal timeForSlopDischarging;

  @Column(name = "discharge_slop_tank_first")
  private Boolean dischargeSlopTankFirst = false; // default unchecked

  @Column(name = "discharge_comingle_cargo_seperately")
  private Boolean dischargeCommingleCargoSeparately = false; // default unchecked

  // bi-directional many-to-one association to DischargingInformationStatus
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "discharging_status_xid")
  private DischargingInformationStatus dischargingInformationStatus;

  // bi-directional many-to-one association to DischargingStagesMinAmount
  @ManyToOne
  @JoinColumn(name = "stages_min_amount_xid")
  private DischargingStagesMinAmount dischargingStagesMinAmount;

  // bi-directional many-to-one association to DischargingStagesDuration
  @ManyToOne
  @JoinColumn(name = "stages_duration_xid")
  private DischargingStagesDuration dischargingStagesDuration;

  /*  // bi-directional many-to-one association to DischargingDelay
  @OneToMany(mappedBy = "dischargingInformation", fetch = FetchType.LAZY)
  private Set<DischargingDelay> dischargingDelays;

  // bi-directional many-to-one association to DischargingMachineryInUse
  @OneToMany(mappedBy = "dischargingInformation", fetch = FetchType.LAZY)
  private Set<DischargingMachineryInUse> dischargingMachineryInUses;*/
  /*   // bi-directional many-to-one association to DischargingBerthDetail
  @OneToMany(mappedBy = "dischargingInformation", cascade = CascadeType.ALL)
  private List<DischargingBerthDetail> dischargingBerthDetails;

  // bi-directional many-to-one association to DischargingPump
  @OneToMany(mappedBy = "dischargingInformation")
  private List<DischargingPump> dischargingPumps;*/

  @Column(name = "is_discharging_sequence_generated")
  private Boolean isDischargingSequenceGenerated;

  @Column(name = "is_discharging_plan_generated")
  private Boolean isDischargingPlanGenerated;

  @Column(name = "discharging_plan_details_from_algo")
  private String dischargingPlanDetailsFromAlgo;

  @Column(name = "discharge_study_process_xid")
  private String dischargeStudyProcessId;

  @Column(name = "is_discharging_instructions_complete")
  private Boolean isDischargingInstructionsComplete = false;

  @Column(name = "common_date")
  private LocalDate commonDate;

  @Column(name = "is_stage_duration_used")
  private Boolean isStageDurationUsed;

  @Column(name = "is_stage_offset_used")
  private Boolean isStageOffsetUsed;

  public DischargeInformation(Long pk) {
    this.setId(pk);
  }
}
