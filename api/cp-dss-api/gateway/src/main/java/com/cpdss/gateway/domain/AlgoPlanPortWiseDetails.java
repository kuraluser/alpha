/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;

@Data
public class AlgoPlanPortWiseDetails {
  private String time;

  private String ballastVol;
  private Map<String, String> cargoVol;

  @JsonProperty("deballastingRateM3_Hr")
  private Map<String, String> deballastingRates;

  private String foreDraft;
  private String afterDraft;
  private String meanDraft;
  private String trim;

  @JsonProperty("heel")
  private String list;

  private String airDraft;
  private String bendinMoment;
  private String shearForce;
  private Map<String, String> ballastingRateM3_Hr;
}
