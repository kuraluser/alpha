/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Rules {

  private String id;

  private Boolean enable;

  private Boolean disable;

  private Boolean displayInSettings;

  private String ruleType;

  private List<RulesInputs> inputs;

  private String ruleTemplateId;

  private String vesselRuleXId;

  private Boolean isHardRule;

  private Long numericPrecision;

  private Long numericScale;
}
