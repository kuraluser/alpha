/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "loadable_plan_stowage_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePlanStowageDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "tank_xid")
  private Long tankId;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationId;

  @Column(name = "abbreviation")
  private String abbreviation;

  @Column(name = "corrected_ullage")
  private String correctedUllage;

  @Column(name = "weight")
  private String weight;

  @Column(name = "rdg_ullage")
  private String rdgUllage;

  @Column(name = "filling_percentage")
  private String fillingPercentage;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "tankname")
  private String tankname;

  @Column(name = "correction_factor")
  private String correctionFactor;

  @Column(name = "observed_m3")
  private String observedM3;

  @Column(name = "observed_barrels")
  private String observedBarrels;

  @Column(name = "observed_barrels_at_60")
  private String observedBarrelsAt60;

  @Column(name = "api")
  private String api;

  @Column(name = "temperature")
  private String temperature;

  @Column(name = "color_code")
  private String colorCode;

  @JoinColumn(name = "loadable_plan_xid", referencedColumnName = "id")
  @ManyToOne
  @JsonIgnore
  private LoadablePlan loadablePlan;

  @JoinColumn(name = "loadable_pattern_xid")
  @ManyToOne
  @JsonIgnore
  private LoadablePattern loadablePattern;

  @Column(name = "cargo_nomination_temperature")
  private BigDecimal cargoNominationTemperature;

  @Transient private Long communicationRelatedEntityId;
}
