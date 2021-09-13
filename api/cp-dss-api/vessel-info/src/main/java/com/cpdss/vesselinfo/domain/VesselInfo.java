/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VesselInfo {
  private Long id;
  private String name;
  private String imoNumber;
  private String typeOfShip;
  private String code;
  private BigDecimal deadweightConstant;
  private BigDecimal provisionalConstant;
  private BigDecimal maxLoadRate;
  private BigDecimal minLoadRate;
  private BigDecimal lightWeight;
  private Boolean hasLoadicator;
}
