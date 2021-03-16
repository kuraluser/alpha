/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author suhail.k */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoHistory {

  private Long tankId;

  private Long cargoNominationId;

  private Long cargoId;

  private String cargoColor;

  private String abbreviation;

  private BigDecimal quantity;

  private BigDecimal api;
}
