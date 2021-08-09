/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Sequence {

  private String stage;
  private String timeStart;
  private String timeEnd;
  private Boolean toLoadicator;

  @JsonProperty("cargoLoadingRatePerTankM3_Hr")
  private Map<String, String> tankWiseCargoLoadingRates;

  @JsonProperty("deballastingRateM3_Hr")
  private Map<String, String> deballastingRates;

  @JsonProperty("cargoLoadingRateM3_Hr")
  private Map<String, String> stageWiseCargoLoadingRates;

  private List<LoadingPlanPortWiseDetails> loadablePlanPortWiseDetails;

  Map<String, List<Pump>> ballast;
}