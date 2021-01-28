/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynopticalCargoBallastRecord {

  private Long tankId;

  private String tankName;

  private BigDecimal actualWeight;

  private BigDecimal plannedWeight;

  private BigDecimal capacity;
  
  private String abbreviation;
  
  private Long cargoId;
  
  private String colorCode;
  
  private BigDecimal correctedUllage;
}
