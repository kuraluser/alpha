/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.cpdss.gateway.domain.dischargeplan.DischargingPlanPortWiseDetails;
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
  private List<Map<String, String>> tankWiseCargoLoadingRates;

  @JsonProperty("cargoDischargingRatePerTankM3_Hr")
  private List<Map<String, String>> tankWiseCargoDischargingRates;
  
  @JsonProperty("deballastingRateM3_Hr")
  private Map<String, String> deballastingRates;

  @JsonProperty("cargoLoadingRateM3_Hr")
  private Map<String, String> stageWiseCargoLoadingRates;

  @JsonProperty("cargoDischargingRateM3_Hr")
  private Map<String, String> stageWiseCargoDischargingRates;
  
  private List<LoadingPlanPortWiseDetails> loadablePlanPortWiseDetails;
  
  private List<DischargingPlanPortWiseDetails> dischargePlanPortWiseDetails;

  Map<String, List<Pump>> ballast;

  private Map<String, String> ballastingRateM3_Hr;
  private List simDeballastingRateM3_Hr;
  private List simBallastingRateM3_Hr;
  private List simCargoLoadingRatePerTankM3_Hr;
}
