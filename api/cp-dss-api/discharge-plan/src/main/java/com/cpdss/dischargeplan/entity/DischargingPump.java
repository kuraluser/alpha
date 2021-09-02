/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the discharging_pumps database table. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discharging_pumps")
public class DischargingPump extends EntityDoc {

  private BigDecimal capacity;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_using")
  private Boolean isUsing;

  @Column(name = "pump_type_xid")
  private Long pumpTypeXid;

  @Column(name = "pump_xid")
  private Long pumpXid;

  // bi-directional many-to-one association to DischargingInformation
  @ManyToOne
  @JoinColumn(name = "discharging_xid")
  private DischargeInformation dischargingInformation;
}
