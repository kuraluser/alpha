/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

  @ManyToOne
  @JoinColumn(name = "voyagexid")
  private Voyage voyage;

  @ManyToOne
  @JoinColumn(name = "duplicatedfrom")
  private LoadableStudy duplicatedFrom;

  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "details", columnDefinition = "TEXT")
  private String details;

  @Column(name = "charterer", length = 100)
  private String charterer;

  @Column(name = "loadablestudystatus")
  private String loadableStudyStatus;

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

  @Column(name = "maxtempexpected")
  private BigDecimal maxTempExpected;

  @Column(name = "isactive")
  private boolean isActive;

  @OneToMany(mappedBy = "loadableStudy", cascade = CascadeType.PERSIST)
  private Set<LoadableStudyAttachments> attachments;
}
