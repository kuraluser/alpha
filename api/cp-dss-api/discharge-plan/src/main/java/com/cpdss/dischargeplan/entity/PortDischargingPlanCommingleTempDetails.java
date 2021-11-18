/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "port_discharge_plan_commingle_details_temp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortDischargingPlanCommingleTempDetails extends PortDischargingPlanCommingleEntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "discharging_xid")
  private Long dischargingInformation;

  
}
