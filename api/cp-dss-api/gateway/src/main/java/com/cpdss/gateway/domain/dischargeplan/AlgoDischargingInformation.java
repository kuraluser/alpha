/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.gateway.domain.loadingplan.AlgoBerthDetails;
import java.util.List;
import lombok.Data;

/** @author pranav.k */
@Data
public class AlgoDischargingInformation {

  private AlgoDischargingRates loadingRates;
  // re using the loading domain class
  private List<AlgoBerthDetails> berthDetails;
  private AlgoDischargingSequences loadingSequences;
}
