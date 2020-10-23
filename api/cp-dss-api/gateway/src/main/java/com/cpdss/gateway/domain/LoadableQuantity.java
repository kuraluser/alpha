/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadableQuantity {
  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String limitingDraft;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String estSeaDensity;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String tpc;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String estSagging;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String displacmentDraftRestriction;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String vesselLightWeight;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String dwt;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String sgCorrection;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String saggingDeduction;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String estFOOnBoard;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String estDOOnBoard;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String estFreshWaterOnBoard;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String constant;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String otherIfAny;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String totalQuantity;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String distanceFromLastPort;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String vesselAverageSpeed;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String foConsumptionPerDay;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String estTotalFOConsumption;

  private Integer loadableStudyId;

  private String updateDateAndTime;
}
