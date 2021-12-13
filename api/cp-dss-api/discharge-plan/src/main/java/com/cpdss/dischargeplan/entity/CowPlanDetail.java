/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the cow_plan_details database table. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cow_plan_details")
public class CowPlanDetail extends EntityDoc {

  @Column(name = "cow_end_time")
  private BigDecimal cowEndTime;

  @Column(name = "cow_max_trim")
  private BigDecimal cowMaxTrim;

  @Column(name = "cow_min_trim")
  private BigDecimal cowMinTrim;

  @Column(name = "cow_operation_type")
  private Integer cowOperationType; // manual/auto

  @Column(name = "cow_percentage")
  private BigDecimal cowPercentage;

  @Column(name = "cow_start_time")
  private BigDecimal cowStartTime;

  @Column(name = "estimated_cow_duration")
  private BigDecimal estimatedCowDuration;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "need_flushing_oil")
  private Boolean needFlushingOil;

  @Column(name = "need_fresh_crude_storage")
  private Boolean needFreshCrudeStorage;

  @Column(name = "wash_tank_with_different_cargo")
  private Boolean washTankWithDifferentCargo; // radio button for enable/disable CWC section

  // bi-directional many-to-one association to CowTankDetail
  @OneToMany(mappedBy = "cowPlanDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<CowTankDetail> cowTankDetails;

  // bi-directional many-to-one association to CowWithDifferentCargo
  @OneToMany(mappedBy = "cowPlanDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<CowWithDifferentCargo> cowWithDifferentCargos;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "discharging_xid", referencedColumnName = "id")
  private DischargeInformation dischargeInformation;

  @Transient private Long communicationRelatedEntityId;
}
