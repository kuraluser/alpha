/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/** @Author vinothkumar m */
@Data
@JsonInclude(Include.NON_EMPTY)
public class VesselRuleRequest {

  private List<RulePlans> plan;
}
