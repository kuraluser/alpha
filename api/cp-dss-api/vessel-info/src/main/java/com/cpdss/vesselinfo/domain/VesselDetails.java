/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/** @Author jerin.g */
@Data
@AllArgsConstructor
public class VesselDetails {
  private BigDecimal displacmentDraftRestriction;
  private String vesselLightWeight;
  private String constant;
}