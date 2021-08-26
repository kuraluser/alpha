/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the discharging_machinery_in_use database table. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discharging_machinary_in_use")
public class DischargingMachineryInUse extends EntityDoc {
  private static final long serialVersionUID = 1L;

  private BigDecimal capacity;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_using")
  private Boolean isUsing;

  @Column(name = "machine_type_xid")
  private Integer machineTypeXid;

  @Column(name = "machine_xid")
  private Long machineXid;

  // bi-directional many-to-one association to DischargingInformation
  @ManyToOne
  @JoinColumn(name = "discharging_xid")
  private DischargeInformation dischargingInformation;
}
