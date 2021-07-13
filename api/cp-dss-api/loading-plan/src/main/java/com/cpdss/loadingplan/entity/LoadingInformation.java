/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @ManyToOne
  @JoinColumn(name = "loading_status_xid")
  private LoadingInformationStatus loadingInformationStatus;

  @OneToMany(fetch = FetchType.LAZY)
  private Set<LoadingSequence> loadingSequences;
}
