/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TankWithSequenceDetails {

  private Long tankId;
  private List<String> ullage;
  private List<QuantityLoadingStatus> status;
}
