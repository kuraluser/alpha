/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Set;
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
  private Long initialDischargingRate;

  @Column(name = "initial_trim")
  private BigDecimal initialTrim;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_loading_information_complete")
  private Boolean isLoadingInformationComplete = false;

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
  private Time startTime;

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

  // bi-directional many-to-one association to DischargingInformationStatus
  @ManyToOne
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

  // bi-directional many-to-one association to DischargingDelay
  @OneToMany(mappedBy = "dischargingInformation", fetch = FetchType.EAGER)
  private Set<DischargingDelay> dischargingDelays;

  // bi-directional many-to-one association to DischargingMachineryInUse
  @OneToMany(mappedBy = "dischargingInformation", fetch = FetchType.EAGER)
  private Set<DischargingMachineryInUse> dischargingMachineryInUses;

  /*   // bi-directional many-to-one association to DischargingBerthDetail
  @OneToMany(mappedBy = "dischargingInformation", cascade = CascadeType.ALL)
  private List<DischargingBerthDetail> dischargingBerthDetails;

  // bi-directional many-to-one association to DischargingPump
  @OneToMany(mappedBy = "dischargingInformation")
  private List<DischargingPump> dischargingPumps;*/

  public DischargeInformation(Long pk) {
    this.setId(pk);
  }
}
