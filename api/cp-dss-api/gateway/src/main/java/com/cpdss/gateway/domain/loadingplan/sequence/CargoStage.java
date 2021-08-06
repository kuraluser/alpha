/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.List;
import lombok.Data;

@Data
public class CargoStage {
  private String name;
  private Long start;
  private Long end;
  private List<Cargo> cargos;
}
