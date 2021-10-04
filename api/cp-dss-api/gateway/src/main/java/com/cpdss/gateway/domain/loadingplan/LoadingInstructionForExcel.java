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
public class LoadingInstructionForExcel {

  private Integer group;
  private String heading;
  private String instruction;
}
