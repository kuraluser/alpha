/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

@Data
public class RulePlans {

  private String header;

  private List<Rules> rules;
}
