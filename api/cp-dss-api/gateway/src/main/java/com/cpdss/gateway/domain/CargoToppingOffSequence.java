/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
public class CargoToppingOffSequence {

  @JsonInclude(Include.NON_NULL)
  Long tankId;

  @JsonInclude(Include.NON_NULL)
  String shortName;

  @JsonInclude(Include.NON_NULL)
  Integer sequenceOrder;
}
