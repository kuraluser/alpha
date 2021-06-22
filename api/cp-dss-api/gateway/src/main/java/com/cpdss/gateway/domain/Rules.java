/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

@Data
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
}
