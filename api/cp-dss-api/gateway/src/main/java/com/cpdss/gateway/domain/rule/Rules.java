/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.rule;

import java.util.List;
import lombok.Data;

@Data
public class Rules {

  private String id;

  private Boolean enable;

  private Boolean disable;

  private Boolean disableInSettings;

  private String ruleType;

  List<RulesInputs> inputs;
}
