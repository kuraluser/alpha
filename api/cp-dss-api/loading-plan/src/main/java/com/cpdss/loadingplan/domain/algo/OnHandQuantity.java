/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

/**
 * DTO for On hand quantity
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_EMPTY)
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
