/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LDIntactStability {
  private Long id;
  private String bigintialGomValue;
  private String bigIntialGomJudgement;
  private String maximumRightingLeverValue;
  private String maximumRightingLeverJudgement;
  private String angleatmaxrleverValue;
  private String angleatmaxrleverJudgement;
  private String areaofStability030Value;
  private String areaofStability030Judgement;
  private String areaofStability040Value;
  private String areaofStability040Judgement;
  private String areaofStability3040Value;
  private String areaofStability3040Judgement;
  private String heelBySteadyWindValue;
  private String heelBySteadyWindJudgement;
  private String stabilityAreaBaValue;
  private String stabilityAreaBaJudgement;
  private String gmAllowableCurveCheckValue;
  private String gmAllowableCurveCheckJudgement;
  private Boolean errorStatus;
  private String errorDetails;
  private String messageText;
  private Long portId;
  private Long synopticalId;
  private Long portRotationId;
}
