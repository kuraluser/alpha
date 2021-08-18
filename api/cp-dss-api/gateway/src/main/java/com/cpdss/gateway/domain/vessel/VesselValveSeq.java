/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VesselValveSeq {
  private String valveCategory;
  private String valveTypeName;
  private String valveNumber;
  private Boolean isCommonValve;
  private String tankShortName;
  private String pipelineColor;
  private String pipelineName;
  private String stageNumber;
  private Integer vesselValveId;
  private boolean isShut;

  public VesselValveSeq getInstance(VesselValveSequence v) {
    VesselValveSeq vq = new VesselValveSeq();
    vq.valveCategory = v.getValveCategory();
    vq.valveTypeName = v.getValveTypeName();
    vq.valveNumber = v.getValveNumber();
    vq.isCommonValve = v.getIsCommonValve();
    vq.tankShortName = v.getTankShortName();
    vq.pipelineColor = v.getPipelineColor();
    vq.pipelineName = v.getPipelineName();
    vq.stageNumber = v.getStageNumber();
    vq.vesselValveId = v.getValveId();
    vq.isShut = v.getIsShut();
    return vq;
  }
}
