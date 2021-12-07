/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VesselValveSequence implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Boolean isCommonValve;

  private Integer pipelineId;

  private String pipelineColor;

  private String pipelineName;

  private String pipelineType;

  private BigDecimal sequenceNumber;

  private Integer sequenceOperationId;

  private String sequenceOperationName;

  private Integer sequenceTypeId;

  private String sequenceTypeName;

  private Integer sequenceVesselMappingId;

  private String tankShortName;

  private String valveCategory;

  private Integer valveCategoryId;

  private String valveNumber;

  private Integer valveSide;

  private Integer valveTypeId;

  private String valveTypeName;

  private String vesselName;

  private Integer vesselTankXid;

  private Integer vesselValveMappingId;

  private Long vesselXid;

  private String stageNumber;
  private Integer valveId;
  private Boolean isShut;

  private String pumpCode;
  private String pumpName;
  private String pumpType;

  private String manifoldName;
  private String manifoldSide;
}
