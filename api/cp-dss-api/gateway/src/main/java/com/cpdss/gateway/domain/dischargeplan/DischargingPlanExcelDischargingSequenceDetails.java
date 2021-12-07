/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.gateway.domain.loadingplan.sequence.LoadingRateForSequence;
import com.cpdss.gateway.domain.loadingplan.sequence.StabilityParamsOfLoadingSequence;
import com.cpdss.gateway.domain.loadingplan.sequence.TankCategoryForSequence;
import com.cpdss.gateway.domain.loadingplan.sequence.TankWithSequenceDetails;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DischargingPlanExcelDischargingSequenceDetails {

  private List<TankCategoryForSequence> cargoTanks;
  private List<TankCategoryForSequence> ballastTanks;
  private StabilityParamsOfLoadingSequence stabilityParams;
  private List<LoadingRateForSequence> dischargingRates;
  private List<String> driveOilTanks;
  private List<?> tankToTanks;
  private List<CargoPumpDetailsForSequence> cargoPumps;
  private List<CargoPumpDetailsForSequence> ballastPumps;
  private List<String> tickPoints;
  private List<TankWithSequenceDetails> ballastTankUllage;
}
