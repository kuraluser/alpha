/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadingPlanStabilityParam {
  private String foreDraft;
  private String meanDraft;
  private String aftDraft;
  private String trim;
  private String bm;
  private String sf;
  private Integer conditionType;
}
