/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LDStrength {
  private Long id;
  private String shearingForcePersentValue;
  private String shearingForceJudgement;
  private String sfFrameNumber;
  private String sfSideShellValue;
  private String sfSideShellJudgement;
  private String sfSideShellFrameNumber;
  private String sfHopperValue;
  private String sfHopperJudgement;
  private String sfHopperFrameNumber;
  private String outerLongiBhdValue;
  private String outerLongiBhdJudgement;
  private String outerLongiBhdFrameNumber;
  private String innerLongiBhdValue;
  private String innerLongiBhdJudgement;
  private String innerLongiBhdFrameNumber;
  private String bendingMomentPersentValue;
  private String bendingMomentPersentJudgement;
  private String bendingMomentPersentFrameNumber;
  private Boolean errorStatus;
  private String errorDetails;
  private String messageText;
  private Long portId;
  private Long synopticalId;
  private Long portRotationId;
}
