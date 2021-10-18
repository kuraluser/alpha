/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StabilityParamsOfLoadingSequence {

  private List<String> fw;
  private List<String> after;
  private List<String> trim;
  private List<String> gm;
  private List<String> ukc;
  private List<String> shearingForce;
  private List<String> bm;
}
