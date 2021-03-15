/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
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
  private Long tankId;

  @Column(name = "rdg_level")
  private String rdgLevel;

  @Column(name = "correction_factor")
  private String correctionFactor;

  @Column(name = "corrected_level")
  private String correctedLevel;

  @Column(name = "metric_ton")
  private String metricTon;

  @Column(name = "cubic_meter")
  private String cubicMeter;

  @Column(name = "percentage")
  private String percentage;

  @Column(name = "sg")
  private String sg;

  @Column(name = "lcg")
  private String lcg;

  @Column(name = "tcg")
  private String tcg;

  @Column(name = "vcg")
  private String vcg;

  @Column(name = "inertia")
  private String inertia;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "tank_name")
  private String tankName;

  @Column(name = "color_code")
  private String colorCode;

  @JoinColumn(name = "loadable_pattern_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadablePattern loadablePattern;
}
