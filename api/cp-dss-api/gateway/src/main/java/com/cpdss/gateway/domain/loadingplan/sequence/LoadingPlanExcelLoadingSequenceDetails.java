/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.cpdss.gateway.domain.dischargeplan.CargoPumpDetailsForSequence;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadingPlanExcelLoadingSequenceDetails {

  private List<TankCategoryForSequence> cargoTanks;
  private List<TankCategoryForSequence> ballastTanks;
  private StabilityParamsOfLoadingSequence stabilityParams;
  private List<CargoPumpDetailsForSequence> ballastPumps;
  private List<LoadingRateForSequence> loadingRates;
  private List<String> tickPoints;
  private List<TankWithSequenceDetails> ballastTankUllage;
  private String initialLoadingRate;
}
