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
@Table(name = "vw_stripping_sequence")
public class VesselValveStrippingSequence implements Serializable {

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
}
