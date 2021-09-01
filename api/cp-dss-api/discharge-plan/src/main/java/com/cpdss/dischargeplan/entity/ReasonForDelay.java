/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the reason_for_delay database table. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reason_for_delay")
public class ReasonForDelay extends EntityDoc {

  @Column(name = "is_active")
  private Boolean isActive;

  private String reason;

  // bi-directional many-to-one association to DischargingDelayReason
  @OneToMany(mappedBy = "reasonForDelay", cascade = CascadeType.ALL)
  private List<DischargingDelayReason> dischargingDelayReasons;
}
