/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import lombok.ToString;

/** The persistent class for the vw_vessel_valve_sequence database table. */
@ToString
@Data
@Entity
@Table(name = "vw_vessel_valve_sequence")
public class VesselValveSequence implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id private Long id;

  @Column(name = "is_common_valve")
  private Boolean isCommonValve;

  @Column(name = "is_shut")
  private Boolean isShut;

  @Column(name = "pipeline_id")
  private Integer pipelineId;

  private String pipelineColor;

  private String pipelineName;

  private String pipelineType;

  @Column(name = "pump_code")
  private String pumpCode;

  @Column(name = "pump_name")
  private String pumpName;

  @Column(name = "pump_type")
  private String pumpType;

  @Column(name = "sequence_number")
  private Integer sequenceNumber;

  @Column(name = "sequence_operation_id")
  private Integer sequenceOperationId;

  @Column(name = "sequence_operation_name")
  private String sequenceOperationName;

  @Column(name = "sequence_type_id")
  private Integer sequenceTypeId;

  @Column(name = "sequence_type_name")
  private String sequenceTypeName;

  @Column(name = "sequence_vessel_mapping_id")
  private Integer sequenceVesselMappingId;

  @Column(name = "stage_number")
  private String stageNumber;

  @Column(name = "tank_short_name")
  private String tankShortName;

  @Column(name = "valve_category")
  private String valveCategory;

  @Column(name = "valve_category_id")
  private Integer valveCategoryId;

  @Column(name = "valve_id")
  private Integer valveId;

  @Column(name = "valve_number")
  private String valveNumber;

  @Column(name = "valve_side")
  private Integer valveSide;

  @Column(name = "valve_type_id")
  private Integer valveTypeId;

  @Column(name = "valve_type_name")
  private String valveTypeName;

  @Column(name = "vessel_name")
  private String vesselName;

  @Column(name = "vessel_tank_xid")
  private Integer vesselTankXid;

  @Column(name = "vessel_xid")
  private Long vesselXid;

  @Column(name = "manifold_name")
  private String manifoldName;

  @Column(name = "manifold_side")
  private String manifoldSide;
}
