/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.rules;

import java.util.List;
import lombok.Data;

@Data
public class RulePlans {

  private String header;

  private List<Rules> rules;
}
