/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

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
  private Boolean cargoLoadingVisible;
  private Boolean machineryInuseVisible;
}
