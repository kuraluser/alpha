/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

@Data
public class CargoBillOfLadding {
  private Long cargoId;
  private String cargoColor;
  private String cargoName;
  private String cargoAbbrevation;
  private Long cargoNominationId;
  private Boolean cargoToBeLoaded;
  private Boolean cargoToBeDischarged;
  private Boolean cargoLoaded;
  private Boolean cargoDischarged;
  private List<BillOfLadding> billOfLaddings;
}
