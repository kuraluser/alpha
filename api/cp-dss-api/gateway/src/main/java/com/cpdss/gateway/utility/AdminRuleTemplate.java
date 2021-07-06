/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.utility;

public enum AdminRuleTemplate {
  INITIAL_TRIM(3L),
  FINAL_TRIM(2L),
  MAXIMUM_TRIM(1L),

  REDUCED_LOADING_RATE(54L),
  MIN_DE_BALLAST_RATE(55L),
  MAX_DE_BALLAST_RATE(56L),
  NOTICE_TIME_FOR_RATE_REDUCTION(57L),
  NOTICE_TIME_FOR_STOPPING_LOADING(58L),
  LINE_CONTENT_REMAINING(59L);
  private Long ruleTemplateId;

  AdminRuleTemplate(Long ruleTemplateId) {
    this.ruleTemplateId = ruleTemplateId;
  }

  public Long getRuleTemplateId() {
    return ruleTemplateId;
  }
}
