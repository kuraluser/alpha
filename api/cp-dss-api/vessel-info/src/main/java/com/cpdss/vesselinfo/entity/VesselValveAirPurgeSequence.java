/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "vw_airpurge_sequence")
public class VesselValveAirPurgeSequence implements Serializable {

  @Id private Long id;

  @Column(name = "vessel_id")
  private Long vesselId;

  @Column(name = "vessel_name")
  private String vesselName;

  @Column(name = "shortname")
  private String shortname;

  @Column(name = "tank_id")
  private Long tankId;

  @Column(name = "pump_id")
  private Long pumpId;

  @Column(name = "pump_code")
  private String pumpCode;

  @Column(name = "sequence_number")
  private Integer sequenceNumber;

  @Column(name = "valve_number")
  private String valveNumber;

  @Column(name = "valve_id")
  private Integer valveId;

  @Column(name = "is_shut")
  private Boolean isShut;

  @Column(name = "is_cop_warm_up")
  private Boolean isCopWarmup;

  @Column(name = "manifold_name")
  private String manifoldName;

  @Column(name = "manifold_side")
  private String manifoldSide;
}
