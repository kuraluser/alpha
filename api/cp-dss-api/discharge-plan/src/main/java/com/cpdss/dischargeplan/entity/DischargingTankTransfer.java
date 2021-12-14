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
@Table(name = "discharging_tank_transfer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargingTankTransfer extends EntityDoc {

  @Column(name = "from_tank_ids")
  private String fromTankIds;

  @Column(name = "to_tank_xid")
  private Long toTankId;

  @Column(name = "time_start")
  private Integer timeStart;

  @Column(name = "time_end")
  private Integer timeEnd;

  @Column(name = "cargo_nomination_id")
  private Long cargoNominationId;

  @Column(name = "start_quantity")
  private BigDecimal startQuantity;

  @Column(name = "end_quantity")
  private BigDecimal endQuantity;

  @Column(name = "start_ullage")
  private BigDecimal startUllage;

  @Column(name = "end_ullage")
  private BigDecimal endUllage;

  @Column(name = "purpose")
  private String purpose;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "discharging_sequence_xid", referencedColumnName = "id", nullable = true)
  private DischargingSequence dischargingSequence;
}
