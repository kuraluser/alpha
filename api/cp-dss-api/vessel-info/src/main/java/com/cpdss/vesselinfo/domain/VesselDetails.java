/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/** @Author jerin.g */
@Data
@AllArgsConstructor
public class VesselDetails {

  private BigDecimal displacmentDraftRestriction;

  private BigDecimal vesselLightWeight;

  private BigDecimal deadWeight;

  private String draftConditionName;

  private BigDecimal constant;
}
