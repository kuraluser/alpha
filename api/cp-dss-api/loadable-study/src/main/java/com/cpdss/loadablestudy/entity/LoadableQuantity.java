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
@Table(name = "loadable_quantity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadableQuantity extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "draft_restriction")
  private BigDecimal draftRestriction;

  @Column(name = "estimated_sea_density")
  private BigDecimal estimatedSeaDensity;

  @Column(name = "tpc_at_draft")
  private BigDecimal tpcatDraft;

  @Column(name = "estimated_sagging")
  private BigDecimal estimatedSagging;

  @Column(name = "displacement_at_draft_restriction")
  private BigDecimal displacementAtDraftRestriction;

  @Column(name = "lightweight")
  private BigDecimal lightWeight;

  @Column(name = "deadweight")
  private BigDecimal deadWeight;

  @Column(name = "sg_correction")
  private BigDecimal sgCorrection;

  @Column(name = "sagging_deduction")
  private BigDecimal saggingDeduction;

  @Column(name = "estimated_fo_onboard")
  private BigDecimal estimatedFOOnBoard;

  @Column(name = "estimated_do_onboard")
  private BigDecimal estimatedDOOnBoard;

  @Column(name = "estimated_fw_onboard")
  private BigDecimal estimatedFWOnBoard;

  @Column(name = "constant")
  private BigDecimal constant;

  @Column(name = "other_if_any")
  private BigDecimal otherIfAny;

  @Column(name = "total_quantity")
  private BigDecimal totalQuantity;

  @Column(name = "distance_from_last_port")
  private BigDecimal distanceFromLastPort;

  @Column(name = "vessel_average_speed")
  private BigDecimal vesselAverageSpeed;

  @Column(name = "fo_consumption_per_day")
  private BigDecimal foConsumptionPerDay;

  @Column(name = "total_fo_consumption")
  private BigDecimal totalFoConsumption;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "port_xid")
  private BigDecimal portId;

  @Column(name = "boiler_water_on_board")
  private BigDecimal boilerWaterOnBoard;

  @Column(name = "ballast")
  private BigDecimal ballast;

  @Column(name = "running_hours")
  private BigDecimal runningHours;

  @Column(name = "running_days")
  private BigDecimal runningDays;

  @Column(name = "fo_consumption_in_sz")
  private BigDecimal foConsumptionInSZ;

  @Column(name = "subtotal")
  private BigDecimal subTotal;

  @JoinColumn(name = "loadable_study_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadableStudy loadableStudyXId;
}
