/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.gateway.domain.loadingplan.LoadablePlanCommingleDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanRobDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanStowageDetails;
import java.util.List;
import lombok.Data;

@Data
public class LoadingUpdateUllageResponse extends UllageResponse {

  private List<PortLoadablePlanStowageDetails> portLoadablePlanStowageDetails;
  private List<PortLoadablePlanBallastDetails> portLoadablePlanBallastDetails;
  private List<PortLoadablePlanRobDetails> portLoadablePlanRobDetails;
  private List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails;
}
