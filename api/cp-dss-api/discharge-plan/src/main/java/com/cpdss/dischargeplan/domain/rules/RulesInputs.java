/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.rules;

import java.util.List;
import lombok.Data;

@Data
public class RulesInputs {

  private String prefix;

  private String defaultValue;

  private String type;

  private String max;

  private String min;

  private String value;

  private String suffix;

  private String id;

  private List<RuleDropDownMaster> ruleDropDownMaster;

  private Boolean isMandatory;
}
