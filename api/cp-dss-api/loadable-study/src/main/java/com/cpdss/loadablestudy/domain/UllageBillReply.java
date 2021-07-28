/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

@Data
public class UllageBillReply {
  ResponseStatus responseStatus;
  List<RulePlans> rulePlan;
}
