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

  public String getDefaultValueForKey(AdminRuleTemplate template) {
    for (RulePlans plan : plan) {
      for (Rules rule : plan.getRules()) {
        log.info(
            "Find Rule for Id {}, template id {}",
            template.getRuleTemplateId(),
            rule.getRuleTemplateId());
        if (rule.getRuleTemplateId().equals(template.getRuleTemplateId().toString())) {
          log.info("Rule default value {}", rule.getInputs().size());
          String val = rule.getInputs().stream().findFirst().get().getDefaultValue();
          return val != null ? val : "";
        }
      }
    }
    return "";
  }
}
