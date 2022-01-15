/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author pranav.k */
@Entity
@Table(name = "discharging_tank_transfer_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargingTankTransferDetails extends EntityDoc {

  @Column(name = "tank_xid")
  private Long tankXId;

  @Column(name = "start_quantity")
  private BigDecimal startQuantity;

  @Column(name = "end_quantity")
  private BigDecimal endQuantity;

  @Column(name = "start_ullage")
  private BigDecimal startUllage;

  @Column(name = "end_ullage")
  private BigDecimal endUllage;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "tank_transfer_details_xid", referencedColumnName = "id", nullable = true)
  private DischargingTankTransfer dischargingTankTransfer;
}
