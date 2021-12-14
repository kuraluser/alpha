/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the cow_with_different_cargo database table. */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cow_with_different_cargo")
public class CowWithDifferentCargo extends EntityDoc {

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationXid;

  @Column(name = "cargo_xid")
  private Long cargoXid;

  @Column(name = "discharging_xid")
  private Long dischargingXid;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "tank_xid")
  private Long tankXid;

  @Column(name = "washing_cargo_nomination_xid")
  private Long washingCargoNominationXid;

  @Column(name = "washing_cargo_xid")
  private Long washingCargoXid;

  // bi-directional many-to-one association to CowPlanDetail
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cow_plan_details_xid")
  private CowPlanDetail cowPlanDetail;

  @Transient private Long communicationRelatedEntityId;
}
