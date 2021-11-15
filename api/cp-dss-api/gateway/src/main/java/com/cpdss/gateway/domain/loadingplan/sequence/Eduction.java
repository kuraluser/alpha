/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.Map;
import lombok.Data;

/** @author pranav.k */
@Data
public class Eduction {

  private String timeStart;
  private String timeEnd;
  private Map<String, String> tank;
  private Map<String, Object> pumpSelected;
  private Map<String, Object> ballastPumpSelected;
}
