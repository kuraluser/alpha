/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

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
  private String LoadLineZone;
}
