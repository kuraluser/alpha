/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.rules;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleResponse {
  public RuleResponse(List<RulePlans> plan) {
    this.plan = plan;
  }

  private CommonSuccessResponse responseStatus;

  private List<RulePlans> plan;
}
