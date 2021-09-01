/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.List;
import lombok.Data;

@Data
public class FlowRate {

  private String tankName;
  private List<List> data;
}
