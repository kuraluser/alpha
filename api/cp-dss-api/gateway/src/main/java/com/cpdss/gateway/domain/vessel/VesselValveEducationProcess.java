/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VesselValveEducationProcess {
  private static final long serialVersionUID = 1L;
  private Long id;
  private Integer eductionProcessMasterId;
  private Integer eductorId;
  private String eductorName;
  private Integer sequenceNumber;
  private String stepName;
  private String valveNumber;
  private Integer valveId;
  private String stageName;
  private Integer stageNumber;

  private Integer valveCategoryId;
  private String valveCategory;
  private Integer valveTypeId;
  private String valveTypeName;
  private Integer valveSide;
  private Long vesselTankId;
  private String tankShortName;

  private String manifoldName;
  private String manifoldSide;
}
