/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Entity
@Table(name = "vw_stripping_sequence_cargo_valve")
public class VesselValveStrippingSequenceCargoValve {

  private static final long serialVersionUID = 1L;

  @Id private Long id;

  @Column(name = "vessel_id")
  private Long vesselId;

  @Column(name = "vessel_name")
  private String vesselName;

  @Column(name = "pipe_line_id")
  private Integer pipeLineId;

  @Column(name = "pipe_line_name")
  private String pipeLineName;

  @Column(name = "colour")
  private String colour;

  @Column(name = "valve_id")
  private Integer valveId;

  @Column(name = "valve")
  private String valve;

  @Column(name = "sequence_number")
  private Integer sequenceNumber;

  @Column(name = "manifold_name")
  private String manifoldName;

  @Column(name = "manifold_side")
  private String manifoldSide;

  @Column(name = "valve_side")
  private String valveSide;
}
