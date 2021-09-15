/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.utility;

import com.cpdss.gateway.domain.RulePlans;
import com.cpdss.gateway.domain.Rules;
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
        log.info(
            "Find Rule for Id {}, template id {}",
            template.getRuleTemplateId(),
            rule.getRuleTemplateId());
        if (rule.getRuleTemplateId().equals(template.getRuleTemplateId().toString())) {
          String val;
          if (!secondInput) {
            log.info("Rule default value, 1st Input {}", rule.getInputs().size());
            val = rule.getInputs().stream().findFirst().get().getDefaultValue();
          } else {
            log.info("Rule default value, 2nd Input {}", rule.getInputs().size());
            val = rule.getInputs().stream().skip(1).findFirst().get().getDefaultValue();
          }
          return val != null ? val : "";
        }
      }
    }
    return "";
  }
}
