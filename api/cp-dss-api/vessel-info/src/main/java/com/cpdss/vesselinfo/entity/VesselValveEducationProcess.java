/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Entity
@Table(name = "vw_eduction_process")
public class VesselValveEducationProcess {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "eduction_process_master_id")
  private Integer eductionProcessMasterId;

  @Column(name = "eductor_id")
  private Integer eductorId;

  @Column(name = "eductor_name")
  private String eductorName;

  @Column(name = "sequence_number")
  private Integer sequenceNumber;

  @Column(name = "step_name")
  private String stepName;

  @Column(name = "valve_number")
  private String valveNumber;
}