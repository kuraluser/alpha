/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.utility;

public enum AdminRuleTemplate {
  // Loading Rules - start
  INITIAL_TRIM(3L),
  FINAL_TRIM(2L),
  MAXIMUM_TRIM(1L),

  REDUCED_LOADING_RATE(54L),
  MIN_DE_BALLAST_RATE(55L),
  MAX_DE_BALLAST_RATE(56L),
  NOTICE_TIME_FOR_RATE_REDUCTION(57L),
  NOTICE_TIME_FOR_STOPPING_LOADING(58L),
  LINE_CONTENT_REMAINING(59L),
  // Loading Rules - end

  // Discharge Rules -start
  DISCHARGE_INITIAL_TRIM(3L), // need clarifications
  DISCHARGE_FINAL_TRIM(2L), // need clarifications
  DISCHARGE_MAXIMUM_TRIM(1L), // need clarifications

  DISCHARGE_INITIAL_RATE(921L),
  DISCHARGE_MAXIMUM_RATE(924L),
  DISCHARGE_MIN_DE_BALLAST_RATE(932L),
  DISCHARGE_MAX_DE_BALLAST_RATE(933L),

  DISCHARGE_TIME_FOR_DRY_CHECK(955L),
  DISCHARGE_SLOP_DISCHARGE(952L),
  DISCHARGE_FINAL_STRIPPING(954L),
  DISCHARGE_FRESH_OIL_WASHING(953L),

  DISCHARGE_COW_TRIM_MIN(906L),
  DISCHARGE_COW_TRIM_MAX(906L);

  // Discharge Rules -end

  private Long ruleTemplateId;

  AdminRuleTemplate(Long ruleTemplateId) {
    this.ruleTemplateId = ruleTemplateId;
  }

  public Long getRuleTemplateId() {
    return ruleTemplateId;
  }
}
