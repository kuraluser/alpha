/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the discharging_delay database table. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discharging_delay")
public class DischargingDelay extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationXid;

  @Column(name = "cargo_xid")
  private Long cargoXid;

  private BigDecimal duration;

  @Column(name = "is_active")
  private Boolean isActive;

  private BigDecimal quantity;

  @Column(name = "reason_xid")
  private Long reasonXid;

  // bi-directional many-to-one association to DischargingInformation
  @ManyToOne
  @JoinColumn(name = "discharging_xid")
  private DischargeInformation dischargingInformation;

  // bi-directional many-to-one association to DischargingDelayReason
  @OneToMany(mappedBy = "dischargingDelay")
  private List<DischargingDelayReason> dischargingDelayReasons;
}
