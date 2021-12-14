/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "port_discharge_plan_commingle_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortDischargingPlanCommingleDetails extends PortDischargingPlanCommingleEntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "discharging_xid", referencedColumnName = "id")
  private DischargeInformation dischargingInformation;

  @Transient private Long communicationRelatedEntityId;
}
