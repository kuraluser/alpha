/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for Synoptical Table */
@Entity
@Table(name = "synoptical_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SynopticalTable extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "loadable_study_xid")
  private Long loadableStudyXId;

  @Column(name = "operation_type")
  private String operationType;

  @Column(name = "distance")
  private BigDecimal distance;

  @Column(name = "speed")
  private BigDecimal speed;

  @Column(name = "running_hours")
  private BigDecimal runningHours;

  @Column(name = "in_port_hours")
  private BigDecimal inPortHours;

  @Column(name = "eta_actual")
  private LocalDateTime etaActual;

  @Column(name = "etd_actual")
  private LocalDateTime etdActual;

  @Column(name = "time_of_sunrise")
  private String timeOfSunrise;

  @Column(name = "time_of_sunset")
  private String timeOfSunSet;

  @Column(name = "hw_tide_from")
  private BigDecimal hwTideFrom;

  @Column(name = "hw_tide_to")
  private BigDecimal hwTideTo;

  @Column(name = "hw_tide_time_from")
  private LocalDateTime hwTideTimeFrom;

  @Column(name = "hw_tide_time_to")
  private LocalDateTime hwTideTimeTo;

  @Column(name = "lw_tide_from")
  private BigDecimal lwTideFrom;

  @Column(name = "lw_tide_to")
  private BigDecimal lwTideTo;

  @Column(name = "lw_tide_time_from")
  private LocalDateTime lwTideTimeFrom;

  @Column(name = "lw_tide_time_to")
  private LocalDateTime lwTideTimeTo;

  @Column(name = "sea_water_sg")
  private BigDecimal seaWaterSg;

  @Column(name = "port_xid")
  private Long portXid;

  @Column(name = "time_of_sunset")
  private String timeOfSunset;

  @Column(name = "sea_water_sg")
  private BigDecimal specificGravity;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "port_rotation_xid")
  private LoadableStudyPortRotation loadableStudyPortRotation;

  @Column(name = "others_planned")
  private BigDecimal othersPlanned;

  @Column(name = "others_actual")
  private BigDecimal othersActual;

  @Column(name = "constant_actual")
  private BigDecimal constantActual;

  @Column(name = "dead_weight_actual")
  private BigDecimal deadWeightActual;

  @Column(name = "displacement_actual")
  private BigDecimal displacementActual;

  @Column(name = "ballast_actual")
  private BigDecimal ballastActual;
}
