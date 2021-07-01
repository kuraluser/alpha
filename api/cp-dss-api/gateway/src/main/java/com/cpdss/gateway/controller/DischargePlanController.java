/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyRequest;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyResponse;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyUpdateResponse;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.OnHandQuantityResponse;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.PortRotationResponse;
import com.cpdss.gateway.service.DischargeStudyService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** @Author jerin.g */
@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class DischargePlanController {

  private static final String CORRELATION_ID_HEADER = "correlationId";

  @Autowired private DischargeStudyService dischargeStudyService;

  /**
   * Save discharge study
   *
   * @param vesselId - the vessel id for which discharge study is created
   * @param voyageId - the voyage id for which discharge study is created
   * @param request - the request body {@link DischargeStudyRequest}
   * @param headers - the http request header
   * @return {@link LoadableStudyResponse}
   * @throws CommonRestException
   */
  @PostMapping(
      value = "/vessels/{vesselId}/voyages/{voyageId}/discharge-study",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public LoadableStudyResponse saveDischargeStudy(
      @PathVariable Long vesselId,
      @PathVariable Long voyageId,
      @Valid final DischargeStudyRequest request,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      if (request.getName() == null || request.getName().isEmpty()) {
        throw new GenericServiceException(
            "No name found", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
      }
      log.info("saveDischargeStudy: {}", getClientIp());
      request.setVesselId(vesselId);
      request.setVoyageId(voyageId);
      return this.dischargeStudyService.saveDischargeStudy(
          request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving discharge study", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error when saving discharge study", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @PutMapping(
      value = "/discharge-studies/{dischargeStudyId}",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public DischargeStudyUpdateResponse updateDischargeStudies(
      @PathVariable Long dischargeStudyId,
      @Valid final DischargeStudyRequest request,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      if (request.getName() == null || request.getName().isEmpty()) {
        throw new GenericServiceException(
            "No name found", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
      }
      log.info("updateDischargeStudy: {}", getClientIp());
      return this.dischargeStudyService.updateDischargeStudy(
          request, headers.getFirst(CORRELATION_ID_HEADER), dischargeStudyId);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when updating discharge study", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error when updating discharge study", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Returns the caller ip for debugging
   *
   * @return
   */
  private static String getClientIp() {
    HttpServletRequest curRequest =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String remoteAddr = "";
    remoteAddr = curRequest.getHeader("X-FORWARDED-FOR");
    if (remoteAddr == null || "".equals(remoteAddr)) {
      remoteAddr = curRequest.getRemoteAddr();
    }
    return remoteAddr;
  }

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
          vesselId, voyageId, dischargeStudyId, headers.getFirst(CORRELATION_ID_HEADER));
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

  /**
   * @param request
   * @param dischargeStudyId
   * @param id
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/ports/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PortRotationResponse savePortRotation(
      @RequestBody @Valid PortRotation request,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long dischargeStudyId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long id,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("savePortRotation: {}");
      request.setId(id);
      request.setLoadableStudyId(dischargeStudyId);
      return this.dischargeStudyService.savePortRotation(
          request, headers.getFirst(CORRELATION_ID_HEADER), headers);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study - port rotation", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when saving loadable study - port rotation", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
  /**
   * Delete port rotation by id
   *
   * @param id
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @DeleteMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/ports/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PortRotationResponse deletePortRotation(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long dischargeStudyId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long id,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("deletePortRotation: {}");
      return this.dischargeStudyService.deletePortRotation(
          dischargeStudyId, id, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when deleting port rotation", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when deleting port rotation", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @GetMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/port-rotation/{portRotationId}/on-hand-quantities")
  public OnHandQuantityResponse getOnHandQuantity(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long dischargeStudyId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("getOnHandQuantity");
      return this.dischargeStudyService.getOnHandQuantity(
          vesselId, dischargeStudyId, portRotationId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching on hand quantities", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when fetching on hand quantities", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
}
