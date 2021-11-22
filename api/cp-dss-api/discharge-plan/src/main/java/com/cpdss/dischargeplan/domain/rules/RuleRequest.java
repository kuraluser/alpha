/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/** @Author Sreemanikandan K K */
@Data
@JsonInclude(Include.NON_EMPTY)
public class RuleRequest {

  private List<RulePlans> plan;
}
