/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CargoForCowDetails {
  private Long cargoId;
  private Long cargoNominationId;
  private Long washingCargoId;
  private Long washingCargoNominationId;
  private List<Long> tankIds;
}
