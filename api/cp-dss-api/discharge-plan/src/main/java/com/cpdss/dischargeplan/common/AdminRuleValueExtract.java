/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.common;

import com.cpdss.dischargeplan.domain.rules.RulePlans;
import com.cpdss.dischargeplan.domain.rules.Rules;
import java.util.List;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class AdminRuleValueExtract {

  private List<RulePlans> plan;

  public String getDefaultValueForKey(AdminRuleTemplate template, Boolean secondInput) {
    return this.adminRuleValueCollector(template, secondInput);
  }

  public String getDefaultValueForKey(AdminRuleTemplate template) {
    return this.adminRuleValueCollector(template, false);
  }

  public String adminRuleValueCollector(AdminRuleTemplate template, Boolean secondInput) {
    for (RulePlans plan : plan) {
      for (Rules rule : plan.getRules()) {
        if (rule.getRuleTemplateId().equals(template.getRuleTemplateId().toString())) {
          String val;
          if (!secondInput) {
            val = rule.getInputs().stream().findFirst().get().getDefaultValue();
          } else {
            val = rule.getInputs().stream().skip(1).findFirst().get().getDefaultValue();
          }
          return val != null ? val : "";
        }
      }
    }
    return "";
  }
}
