/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class LoadingSequenceResponse {

  private Date minXAxisValue;
  private List<Cargo> cargos;
  private List<Ballast> ballasts;
  private List<BallastPump> gravity;
  private List<BallastPump> ballastPumps;
  private List<CargoLoadingRate> cargoLoadingRates;
  private List<FlowRate> flowRates;
  private List<TankCategory> cargoTankCategories;
  private List<TankCategory> ballastTankCategories;
  private List<PumpCategory> ballastPumpCategories;
  private List<Long> stageTickPositions;
  private List<StabilityParam> stabilityParams;
}
