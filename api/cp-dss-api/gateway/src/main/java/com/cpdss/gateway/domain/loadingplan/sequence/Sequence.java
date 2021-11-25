/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.cpdss.gateway.domain.Cleaning;
import com.cpdss.gateway.domain.CleaningTankDetails;
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

  @JsonProperty("deballastingRateM3_Hr")
  private Map<String, String> deballastingRates;

  @JsonProperty("cargoLoadingRateM3_Hr")
  private Map<String, String> stageWiseCargoLoadingRates;

  private List<LoadingPlanPortWiseDetails> loadablePlanPortWiseDetails;

  Map<String, List<Pump>> ballast;

  @JsonProperty("ballastingRateM3_Hr")
  private Map<String, String> ballastingRates;

  private Eduction eduction;

  // for discharging
  private List cargoValves;
  private List ballastValves;
  private Map<String, List<Cargo>> cargo;

  @JsonProperty("cargoDischargingRateM3_Hr")
  private Map<String, String> stageWiseCargoDischargingRates;

  @JsonProperty("cargoDischargingRatePerTankM3_Hr")
  private List<Map<String, String>> tankWiseCargoDischargingRates;

  private List<DischargingPlanPortWiseDetails> dischargePlanPortWiseDetails;

  @JsonProperty("Cleaning")
  private Cleaning cleaning;
}
