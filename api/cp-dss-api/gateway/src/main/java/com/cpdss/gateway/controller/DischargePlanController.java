/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyResponse;
import com.cpdss.gateway.domain.PortRotationResponse;
import com.cpdss.gateway.service.DischargeStudyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/** @Author jerin.g */
@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class DischargePlanController {

  private static final String CORRELATION_ID_HEADER = "correlationId";

  @Autowired private DischargeStudyService dischargeStudyService;

  /**
   * @param vesselId
   * @param voyageId
   * @param dischargeStudyId
   * @param headers
   * @return
   * @throws CommonRestException DischargeStudyResponse
   */
  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/cargo-nomination")
  public DischargeStudyResponse getDischargeStudyByVoyage(
      @PathVariable
          @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long vesselId,
      @PathVariable
          @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long voyageId,
      @PathVariable
          @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long dischargeStudyId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.dischargeStudyService.getDischargeStudyByVoyage(
          vesselId,
          voyageId,
          dischargeStudyId,
          headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching getDischargeStudyByVoyage", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error fetching getDischargeStudyByVoyage", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * @param vesselId
   * @param voyageId
   * @param dischargeStudyId
   * @param headers
   * @return
   */
  @GetMapping("/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/ports")
  public PortRotationResponse getDischargeStudyPortByVoyage(
      @PathVariable
          @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long vesselId,
      @PathVariable
          @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long voyageId,
      @PathVariable
          @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long dischargeStudyId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.dischargeStudyService.getDischargeStudyPortDataByVoyage(
          vesselId, voyageId, dischargeStudyId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching getDischargeStudyPortByVoyage", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error fetching getDischargeStudyPortByVoyage", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
}
