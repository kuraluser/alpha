/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "cow_machine_particulars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CowMachineParticulars extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "length_of_main_pipe")
  private BigDecimal lengthOfMainPipe;

  @Column(name = "capacity")
  private BigDecimal capacity;

  @Column(name = "working_pressure")
  private BigDecimal workingPressure;

  @Column(name = "vertical_working_angle")
  private BigDecimal verticalWorkingAngle;

  @Column(name = "horizontal_revolution")
  private BigDecimal horizontalRevolution;

  @Column(name = "nozzle_diameter")
  private BigDecimal nozzleDiameter;

  @Column(name = "effective_jet_distance")
  private BigDecimal effectiveJetDistance;

  @Column(name = "time_for_one_cycle_wash")
  private BigDecimal timeForOneCycleWash;

  @Column(name = "no_of_set")
  private BigDecimal noOfSet;

  @Column(name = "weight")
  private BigDecimal weight;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
