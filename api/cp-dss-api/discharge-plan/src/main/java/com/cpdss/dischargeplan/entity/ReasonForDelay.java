/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** The persistent class for the reason_for_delay database table. */
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "reason_for_delay")
public class ReasonForDelay extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "is_active")
  private Boolean isActive;

  private String reason;

  // bi-directional many-to-one association to DischargingDelayReason
  @OneToMany(mappedBy = "reasonForDelay")
  private List<DischargingDelayReason> dischargingDelayReasons;
}
