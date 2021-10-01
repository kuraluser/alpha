/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.algo;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LDTrim {
  private Long id;
  private String foreDraftValue;
  private String aftDraftValue;
  private String trimValue;
  private String heelValue;
  private String meanDraftValue;
  private String meanDraftJudgement;
  private String displacementValue;
  private String displacementJudgement;
  private String maximumDraftValue;
  private String maximumDraftJudgement;
  private String airDraftValue;
  private String airDraftJudgement;
  private String minimumForeDraftInRoughWeatherValue;
  private String minimumForeDraftInRoughWeatherJudgement;
  private String maximumAllowableVisibility;
  private String maximumAllowableJudement;
  private Boolean errorStatus;
  private String errorDetails;
  private String messageText;
  private String deflection;
}
