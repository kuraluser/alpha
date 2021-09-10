/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.cargo;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OnHandQuantity {

  private Long id;

  private Long portId;

  private Long fuelTypeId;

  private String fuelTypeName;

  private String fuelTypeShortName;

  private Long tankId;

  private String tankName;

  private Long loadableStudyId;

  private BigDecimal arrivalVolume;

  private BigDecimal arrivalQuantity;

  private BigDecimal actualArrivalQuantity;

  private BigDecimal departureVolume;

  private BigDecimal departureQuantity;

  private BigDecimal actualDepartureQuantity;

  private String colorCode;

  private BigDecimal density;

  private Long portRotationId;

  private Boolean isPortRotationOhqComplete;
}
