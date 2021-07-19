/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

@Data
public class PortWiseCargo {
  private Long portId;
  private List<Cargo> cargos;
}
