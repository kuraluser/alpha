/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;


import java.util.List;

import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanExcelLoadingSequenceDetails;

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
  private String cargoNames;
}
