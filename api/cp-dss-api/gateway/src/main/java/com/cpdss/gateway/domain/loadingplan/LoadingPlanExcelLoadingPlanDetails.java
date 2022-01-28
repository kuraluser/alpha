/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadingPlanExcelLoadingPlanDetails {

  private String vesselName;
  private String voyageNumber;
  private String master;
  private String co;
  private String portName;
  private String eta;
  private String etd;
  private String vesselCompliance;
  private String loadLineZone;
  private ArrivalDeparcherCondition arrivalCondition;
  private ArrivalDeparcherCondition deparcherCondition;
  private List<CargoTobeLoaded> cargoTobeLoaded;
  private List<CargoTobeLoaded> cargoTobeDischarged;
  private List<BerthInformation> berthInformation;
  private List<LoadingPlanCommingleDetails> loadingPlanCommingleDetailsList;
  private String itemsAgreedWithTerminal;
  private String slopQuantity;
}
