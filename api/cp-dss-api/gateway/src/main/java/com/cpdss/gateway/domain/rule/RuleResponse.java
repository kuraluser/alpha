/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.rule;

import com.cpdss.common.jsonbuilder.CPDSSJsonParser;
import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

@Data
@CPDSSJsonParser
public class RuleResponse {

  private CommonSuccessResponse responseStatus;

  private List<RulePlans> plan;
}
