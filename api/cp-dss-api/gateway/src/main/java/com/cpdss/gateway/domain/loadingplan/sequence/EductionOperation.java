/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.List;
import lombok.Data;

/** @author pranav.k */
@Data
public class EductionOperation {

  private Long timeStart;
  private Long timeEnd;
  private List<Long> tanks;
  private List<Long> pumpSelected;
  private List<Long> ballastPumpSelected;
}
