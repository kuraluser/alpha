/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "loadable_pattern_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePatternDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "priority")
  private Integer priority;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationId;

  @Column(name = "tank_xid")
  private Long tankId;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "loadable_study_status")
  private Integer loadableStudyStatus;

  @Column(name = "cargo_abbreviation")
  private String cargoAbbreviation;

  @Column(name = "cargo_color")
  private String cargoColor;

  @Column(name = "difference")
  private BigDecimal difference;

  @Column(name = "difference_color")
  private String differenceColor;

  @Column(name = "constraints")
  private String constraints;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "loadable_patternxid")
  @ManyToOne
  private LoadablePattern loadablePattern;
}
