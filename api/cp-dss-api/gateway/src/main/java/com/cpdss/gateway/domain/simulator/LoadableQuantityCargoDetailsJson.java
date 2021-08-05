/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.simulator;

import java.util.List;
import lombok.Data;

@Data
public class LoadableQuantityCargoDetailsJson {
  private Double estimatedAPI;
  private Double estimatedTemp;
  private Double minTolerence;
  private Double maxTolerence;
  private Double loadableMT;
  private Double differencePercentage;
  private Long cargoId;
  private Double orderedQuantity;
  private String cargoAbbreviation;
  private String colorCode;
  private Long priority;
  private Long loadingOrder;
  private Long cargoNominationId;
  private Double slopQuantity;
  private Double timeRequiredForLoading;
  private Double cargoNominationTemperature;
  private Double cargoNominationQuantity;
  private Double orderQuantity;
  private Double maxLoadingRate;
  private List<ToppingOffSequenceJson> toppingSequence;
  private Object loadingPorts;
}
