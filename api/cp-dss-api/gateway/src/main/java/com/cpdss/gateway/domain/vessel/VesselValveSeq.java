/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

public class VesselValveSeq {
  private String valveCategory;
  private String valveTypeName;
  private String valveNumber;
  private Boolean isCommonValve;
  private String tankShortName;
  private String pipelineColor;
  private String pipelineName;

  public VesselValveSeq() {}

  public VesselValveSeq getInstance(VesselValveSequence v) {
    VesselValveSeq vq = new VesselValveSeq();
    vq.valveCategory = v.getValveCategory();
    vq.valveTypeName = v.getValveTypeName();
    vq.valveNumber = v.getValveNumber();
    vq.isCommonValve = v.getIsCommonValve();
    vq.tankShortName = v.getTankShortName();
    vq.pipelineColor = v.getPipelineColor();
    vq.pipelineName = v.getPipelineName();
    return vq;
  }
}
