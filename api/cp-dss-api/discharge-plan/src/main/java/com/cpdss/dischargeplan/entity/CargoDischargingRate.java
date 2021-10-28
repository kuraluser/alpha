/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cargo_discharging_rate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoDischargingRate extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "discharging_sequences_xid", referencedColumnName = "id")
  private DischargingSequence dischargingSequence;

  @Column(name = "tank_xid")
  private Long tankXId;

  @Column(name = "loading_rate")
  private BigDecimal dischargingRate;

  @Column(name = "is_active")
  private Boolean isActive;
}
