/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;

import com.cpdss.gateway.domain.dischargeplan.CowPlanForExcel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadingPlanExcelLoadingInstructionDetails {

  private List<LoadingInstructionForExcel> instructions;
  private VesselParticularsForExcel vesselPurticulars;
  private String manifoldNames;
  private String gsPump;
  private String igsPump;
  private String ballastPump;
  private String cargoPump;
  private String strippingPump;
  private Long ballastPumpCount;
  private String cargoPumpCount;
  private Long strippingPumpCount;
  private String cargoNames; 
  private CowPlanForExcel cowPlan;
}
