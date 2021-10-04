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
@Table(name = "ballast_operation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BallastOperation extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "discharging_sequences_xid", referencedColumnName = "id", nullable = true)
  private DischargingSequence dischargingSequence;

  @Column(name = "time_start")
  private Integer startTime;

  @Column(name = "time_end")
  private Integer endTime;

  @Column(name = "pump_xid")
  private Long pumpXId;

  @Column(name = "pump_name")
  private String pumpName;

  @Column(name = "rate_m3_hr")
  private BigDecimal rate;

  @Column(name = "quantity_m3")
  private BigDecimal quantityM3;

  @Column(name = "is_active")
  private Boolean isActive;
}
