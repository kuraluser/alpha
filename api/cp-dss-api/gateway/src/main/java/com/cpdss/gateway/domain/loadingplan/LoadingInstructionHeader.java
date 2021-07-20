/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;
import lombok.Data;

@Data
public class LoadingInstructionHeader {

  private String headerName;

  private Boolean isHeaderChecked;

  List<LoadingInstructions> loadingInstructionlist;
}
