/* Licensed under Apache-2.0 */
package com.cpdss.loadableplan.entity;

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
@Table(name = "loadablestudy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadableStudy extends EntityDoc {

  @Column(name = "vesselxid")
  private Long vesselXId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "voyagexid")
  private Voyage voyage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "duplicatedfrom")
  private LoadableStudy duplicatedFrom;

  @ManyToOne
  @JoinColumn(name = "loadablestudystatusxid")
  private LoadableStudyStatus loadableStudyStatus;

  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "details", columnDefinition = "TEXT")
  private String details;

  @Column(name = "charterer", length = 100)
  private String charterer;

  @Column(name = "subcharterer", length = 100)
  private String subCharterer;

  @Column(name = "draftmark")
  private BigDecimal draftMark;

  @Column(name = "loadlinexid")
  private Long loadLineXId;

  @Column(name = "draftrestriction")
  private BigDecimal draftRestriction;

  @Column(name = "estimatedmaxsag")
  private BigDecimal estimatedMaxSag;

  @Column(name = "maxairtemperature")
  private BigDecimal maxAirTemperature;

  @Column(name = "maxwatertemperature")
  private BigDecimal maxWaterTemperature;

  @Column(name = "isactive")
  private boolean isActive;

  @OneToMany(mappedBy = "loadableStudy", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private Set<LoadableStudyAttachments> attachments;

  @OneToMany(mappedBy = "loadableStudy", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private Set<LoadableStudyPortRotation> portRotations;

  @PrePersist
  void prePersist() {
    this.isActive = true;
  }
}
