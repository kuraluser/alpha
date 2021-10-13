/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.List;
import lombok.Data;

/** @author pranav.k */
@Data
public class Eduction {

  private String timeStart;
  private String timeEnd;
  private List<String> tank;
  private List<String> pumpSelected;
}
