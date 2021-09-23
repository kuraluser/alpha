/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.gateway.domain.UllageResponse;
import com.cpdss.gateway.domain.loadingplan.LoadablePlanCommingleDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanRobDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanStowageDetails;
import java.util.List;
import lombok.Data;

@Data
public class DischargeUpdateUllageResponse extends UllageResponse {

  private List<PortLoadablePlanStowageDetails> portDischargePlanStowageDetails;
  private List<PortLoadablePlanBallastDetails> portDischargePlanBallastDetails;
  private List<PortLoadablePlanRobDetails> portDischargePlanRobDetails;
  private List<LoadablePlanCommingleDetails> dischargePlanCommingleDetails;
}
