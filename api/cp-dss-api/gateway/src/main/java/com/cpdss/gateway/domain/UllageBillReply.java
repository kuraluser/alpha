/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.generated.Common;
import java.util.List;
import lombok.Data;

@Data
public class UllageBillReply {
  Common.ResponseStatus responseStatus;
  List<RulePlans> rulePlan;
}
