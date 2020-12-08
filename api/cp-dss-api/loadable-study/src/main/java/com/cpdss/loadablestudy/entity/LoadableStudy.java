/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

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

/**
 * Loadable study entity
 *
 * @author suhail.k
 */
@Entity
@Table(name = "loadable_study")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

  @OneToMany(mappedBy = "loadableStudy", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private Set<LoadableStudyAttachments> attachments;

  @OneToMany(mappedBy = "loadableStudy", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private Set<LoadableStudyPortRotation> portRotations;

  @Column(name = "case_no")
  private Integer caseNo;

  @PrePersist
  void prePersist() {
    this.isActive = true;
  }
}
