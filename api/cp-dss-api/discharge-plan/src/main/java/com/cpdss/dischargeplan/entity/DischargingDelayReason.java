/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the discharging_delay_reason database table. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discharging_delay_reason")
public class DischargingDelayReason extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "is_active")
  private Boolean isActive;

  // bi-directional many-to-one association to DischargingDelay
  @ManyToOne
  @JoinColumn(name = "discharging_delay_xid")
  private DischargingDelay dischargingDelay;

  // bi-directional many-to-one association to ReasonForDelay
  @ManyToOne
  @JoinColumn(name = "reason_xid")
  private ReasonForDelay reasonForDelay;
}
