/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.rules;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleResponse {
  private List<RulePlans> plan;
}
