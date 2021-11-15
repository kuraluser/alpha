/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "loading_information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingInformation extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "vessel_xid")
  private Long vesselXId;

  @Column(name = "loadable_pattern_xid")
  private Long loadablePatternXId;

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "synoptical_table_xid")
  private Long synopticalTableXId;

  @Column(name = "sunrise_time")
  private LocalTime sunriseTime;

  @Column(name = "sunset_time")
  private LocalTime sunsetTime;

  @Column(name = "start_time")
  private LocalTime startTime;

  @Column(name = "initial_trim")
  private BigDecimal initialTrim;

  @Column(name = "maximum_trim")
  private BigDecimal maximumTrim;

  @Column(name = "final_trim")
  private BigDecimal finalTrim;

  @Column(name = "max_loading_rate")
  private BigDecimal maxLoadingRate;

  @Column(name = "reduced_loading_rate")
  private BigDecimal reducedLoadingRate;

  @Column(name = "min_loading_rate")
  private BigDecimal minLoadingRate;

  @Column(name = "min_deballast_rate")
  private BigDecimal minDeBallastRate;

  @Column(name = "max_deballast_rate")
  private BigDecimal maxDeBallastRate;

  @Column(name = "notice_time_for_rate_reduction")
  private Integer noticeTimeForRateReduction;

  @Column(name = "notice_time_for_stop_loading")
  private Integer noticeTimeForStopLoading;

  @Column(name = "line_content_remaining")
  private BigDecimal lineContentRemaining;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "stages_min_amount_xid")
  private StageOffset stageOffset;

  @ManyToOne
  @JoinColumn(name = "stages_duration_xid")
  private StageDuration stageDuration;

  @Column(name = "is_track_start_end_stage")
  private Boolean trackStartEndStage = true;

  @Column(name = "is_track_grade_switching")
  private Boolean trackGradeSwitch = true;

  @Column(name = "voyage_xid")
  private Long voyageId;

  @Column(name = "initial_loading_rate")
  private BigDecimal initialLoadingRate;

  @OneToMany(mappedBy = "loadingInformation")
  private Set<LoadingBerthDetail> loadingBerthDetails;

  @OneToMany(mappedBy = "loadingInformation")
  private Set<CargoToppingOffSequence> cargoToppingOfSequences;

  @OneToMany(mappedBy = "loadingInformation")
  private Set<LoadingDelay> loadingDelays;

  @OneToMany(mappedBy = "loadingInformation")
  private Set<LoadingMachineryInUse> loadingMachineriesInUse;

  @Column(name = "port_rotation_xid")
  private Long portRotationXId;

  @Column(name = "is_loading_information_complete")
  private Boolean isLoadingInfoComplete = false;

  @Column(name = "shore_loading_rate")
  private BigDecimal shoreLoadingRate;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "loading_status_xid", nullable = true)
  private LoadingInformationStatus loadingInformationStatus;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "arrival_status_xid", nullable = true)
  private LoadingInformationStatus arrivalStatus;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "departure_status_xid", nullable = true)
  private LoadingInformationStatus departureStatus;

  @Column(name = "is_loading_instructions_complete")
  private Boolean isLoadingInstructionsComplete;

  @Column(name = "is_loading_sequence_generated")
  private Boolean isLoadingSequenceGenerated;

  @Column(name = "is_loading_plan_generated")
  private Boolean isLoadingPlanGenerated;

  @Column(name = "loadable_study_process_xid")
  private String loadableStudyProcessId;

  @Column(name = "loading_plan_details_from_algo")
  private String loadingPlanDetailsFromAlgo;
}
