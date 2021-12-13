/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cargo_valves")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoValve extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "discharging_sequence_xid", referencedColumnName = "id")
  private DischargingSequence dischargingSequence;

  @Column(name = "time")
  private Integer time;

  @Column(name = "operation")
  private String operation;

  @Column(name = "valve_code")
  private String valveCode;

  @Column(name = "valve_xid")
  private Long valveXId;

  @Column(name = "valve_type")
  private String valveType;

  @Column(name = "is_active")
  private Boolean isActive;

  @Transient private Long communicationRelatedEntityId;
}
