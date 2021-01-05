/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "loadable_plan_ballast_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePlanBallastDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "tank_xid")
  private BigInteger tankXid;

  @Column(name = "rdg_level")
  private BigDecimal rdgLevel;

  @Column(name = "correction_factor")
  private BigDecimal correctionFactor;

  @Column(name = "corrected_level")
  private BigDecimal correctedLevel;

  @Column(name = "metric_ton")
  private BigDecimal metricTon;

  @Column(name = "cubic_meter")
  private BigDecimal cubicMeter;

  @Column(name = "percentage")
  private BigDecimal percentage;

  @Column(name = "sg")
  private BigDecimal sg;

  @Column(name = "lcg")
  private BigDecimal lcg;

  @Column(name = "tcg")
  private BigDecimal tcg;

  @Column(name = "vcg")
  private BigDecimal vcg;

  @Column(name = "inertia")
  private BigDecimal inertia;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "loadable_pattern_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadablePattern loadablePattern;
}
