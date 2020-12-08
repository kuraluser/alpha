/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePatternDetailsResponse {
  private Long id;
  private String tankShortName;
  private String cargo1Abbrivation;
  private String cargo2Abbrivation;
  private String grade;
  private String quantity;
  private String api;
  private String temperature;
  private String cargo1Quantity;
  private String cargo2Quantity;
  private String cargo1Percentage;
  private String cargo2Percentage;
  private CommonSuccessResponse responseStatus;
}
