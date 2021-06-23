/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VesselRule {

  private String header;
  private Long id;
  private Long templateId;
  private Boolean templateIsEnable;
  private Boolean templateDisplayInSettings;
  private String templateRuleType;
  private Long templateInputId;
  private Long templateFId;
  private String templateInputPrefix;
  private String templateInputSuffix;
  private String templateInputDefaultValue;
  private String templateInputMaxValue;
  private String templateInputMinValue;
  private String templateInputTypeValue;
  private Boolean isHardRule;
}
