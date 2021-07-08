/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.jsonbuilder.CPDSSJsonParser;
import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Loadable study entity
 *
 * @author suhail.k
 */
@Entity
@Table(name = "loadable_study")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@CPDSSJsonParser
public class LoadableStudy extends EntityDoc {

  @Column(name = "vessel_xid")
  private Long vesselXId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "voyage_xid")
  private Voyage voyage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "duplicated_from")
  private LoadableStudy duplicatedFrom;

  @ManyToOne
  @JoinColumn(name = "loadable_study_status_xid")
  private LoadableStudyStatus loadableStudyStatus;

  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "details", columnDefinition = "TEXT")
  private String details;

  @Column(name = "charterer", length = 100)
  private String charterer;

  @Column(name = "sub_charterer", length = 100)
  private String subCharterer;

  @Column(name = "draft_mark")
  private BigDecimal draftMark;

  @Column(name = "loadline_xid")
  private Long loadLineXId;

  @Column(name = "draft_restriction")
  private BigDecimal draftRestriction;

  @Column(name = "estimated_max_sag")
  private BigDecimal estimatedMaxSag;

  @Column(name = "max_air_temperature")
  private BigDecimal maxAirTemperature;

  @Column(name = "max_water_temperature")
  private BigDecimal maxWaterTemperature;

  @Column(name = "is_active")
  private boolean isActive;

  @OneToMany(mappedBy = "loadableStudy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<LoadableStudyAttachments> attachments;

  @OneToMany(mappedBy = "loadableStudy", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private Set<LoadableStudyPortRotation> portRotations;

  @Column(name = "case_no")
  private Integer caseNo;

  @Column(name = "first_cargo_to_be_discharged")
  private Long dischargeCargoNominationId;

  @Column(name = "load_on_top")
  private Boolean loadOnTop;

  @PrePersist
  void prePersist() {
    this.isActive = true;
  }

  @Column(name = "is_cargonomination_complete")
  private Boolean isCargoNominationComplete;

  @Column(name = "is_ports_complete")
  private Boolean isPortsComplete;

  @Column(name = "is_ohq_complete")
  private Boolean isOhqComplete;

  @Column(name = "is_obq_complete")
  private Boolean isObqComplete;

  @Column(name = "is_discharge_ports_complete")
  private Boolean isDischargePortsComplete;

  @Column(name = "feedback_loop")
  private Boolean feedbackLoop;

  @Column(name = "feedback_loop_count")
  private Integer feedbackLoopCount;

  @Column(name = "planning_type_xid")
  private Integer planningTypeXId = 1;

  @Column(name = "message_uuid")
  private String messageUUID;
}
