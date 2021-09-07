/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoForCowDetails {
  private Long cargoId;
  private Long cargoNominationId;
  private Long washingCargoId;
  private Long washingCargoNominationId;
  private List<Long> tankIds;
}
