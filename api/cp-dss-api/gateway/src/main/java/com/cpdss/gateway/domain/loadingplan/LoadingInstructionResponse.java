/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadingInstructionResponse {
  private List<LoadingInstructionSubHeader> loadingInstructionSubHeader;
  private List<LoadingInstructionGroup> loadingInstructionGroupList;
}
