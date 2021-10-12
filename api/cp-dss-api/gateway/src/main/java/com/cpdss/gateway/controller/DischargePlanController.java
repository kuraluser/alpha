/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.CommonResponse;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.OnHandQuantity;
import com.cpdss.gateway.domain.OnHandQuantityResponse;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.PortRotationResponse;
import com.cpdss.gateway.domain.PortWiseCargoResponse;
import com.cpdss.gateway.domain.RuleRequest;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.domain.UllageBillReply;
import com.cpdss.gateway.domain.UllageBillRequest;
import com.cpdss.gateway.domain.UploadTideDetailResponse;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyCargoResponse;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyRequest;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyResponse;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyUpdateResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargeInformation;
import com.cpdss.gateway.domain.dischargeplan.DischargePlanResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargeUpdateUllageResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInformationRequest;
import com.cpdss.gateway.domain.dischargeplan.DischargingInformationResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsSaveRequest;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsSaveResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsStatus;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsUpdateRequest;
import com.cpdss.gateway.service.DischargeStudyService;
import com.cpdss.gateway.service.dischargeplan.DischargeInformationGrpcService;
import com.cpdss.gateway.service.dischargeplan.DischargeInformationService;
import com.cpdss.gateway.service.dischargeplan.DischargingInstructionService;

import lombok.extern.log4j.Log4j2;

/** @Author jerin.g */
@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class DischargePlanController {

  private static final String CORRELATION_ID_HEADER = "correlationId";

  private static final String DISCHARGING_PORT_TIDE_DETAIL_FILE_NAME =
      "Discharging_port_tide_details.xlsx";

  @Autowired private DischargeStudyService dischargeStudyService;

  @Autowired private DischargeInformationGrpcService dischargeInformationGrpcService;

  @Autowired private DischargeInformationService dischargeInformationService;

  @Autowired private DischargingInstructionService dischargingInstructionService;

  /**
   * Delete port rotation by id
   *
   * @param dischargeStudyId
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @DeleteMapping(
      value = "/discharge-studies/{dischargeStudyId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public DischargeStudyResponse deleteDischargeStudy(
      @PathVariable Long dischargeStudyId, @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("dischargeStudy: {}");
      return this.dischargeStudyService.deleteDischargeStudy(
          dischargeStudyId, headers.getFirst(CORRELATION_ID_HEADER));
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

  /**
   * Save and update discharge study with back loading if there is any
   *
   * @param vesselId - the vessel id for which discharge study is created
   * @param voyageId - the voyage id for which discharge study is created
   * @param request - the request body {@link DischargeStudyRequest}
   * @param headers - the http request header
   * @return {@link LoadableStudyResponse}
   * @throws CommonRestException
   */
  @PostMapping(value = "/discharge-studies")
  public LoadableStudyResponse saveDischargeStudyWithBackloading(
      @RequestBody final DischargeStudyCargoResponse request, @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      if (request.getDischargeStudyId() == null || request.getDischargeStudyId() == 0) {
        throw new GenericServiceException(
            "No DischargeStudy found",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      log.info("saveDischargeStudy: {}", getClientIp());
      return this.dischargeStudyService.saveDischargeStudyWithBackloaing(
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
  /**
   * @param dischargeStudyId discharge study id to update
   * @param request values to update in discharge study
   * @param headers
   * @return
   * @throws CommonRestException
   */
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
   * Retrieve cargos information from cargo master based on the ports
   *
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping("/discharge-studies/{dischargeStudyId}/port-cargos")
  public PortWiseCargoResponse getCargosByPorts(
      @PathVariable Long dischargeStudyId, @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    PortWiseCargoResponse response = null;
    try {
      log.info("getCargos: {}", getClientIp());
      response = dischargeStudyService.getCargosByPorts(dischargeStudyId, headers);
    } catch (Exception e) {
      log.error("Error in getCargos ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
    return response;
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
          vesselId, voyageId, dischargeStudyId, headers.getFirst(CORRELATION_ID_HEADER), headers);
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

  @PostMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}"
              + "/port-rotation/{portRotationId}/on-hand-quantities/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public OnHandQuantityResponse saveOnHandQuantity(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long dischargeStudyId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long id,
      @RequestBody @Valid OnHandQuantity request,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      request.setId(id);
      request.setPortRotationId(portRotationId);
      request.setLoadableStudyId(dischargeStudyId);
      return this.dischargeStudyService.saveOnHandQuantity(
          request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving on hand quantities", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when saving on hand quantities", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/cargoByPort")
  public DischargeStudyCargoResponse getDischargeStudyCargoByVoyage(
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
      return this.dischargeStudyService.getDischargeStudyCargoByVoyage(
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
   * @throws CommonRestException
   */
  @GetMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/discharge-pattern-details")
  public DischargeStudyCargoResponse getDischargePatternDetails(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long dischargeStudyId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.dischargeStudyService.getDischargePatternDetails(
          dischargeStudyId, vesselId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when getLoadablePatternDetails", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when getLoadablePatternDetails", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
  /**
   * @param dischargeStudyId
   * @param dischargePatternId
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @PostMapping(value = "discharge-studies/{dischargeStudyId}/confirm-plan/{dischargePatternId}")
  public CommonResponse confirmPlan(
      @PathVariable Long dischargeStudyId,
      @PathVariable Long dischargePatternId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.dischargeStudyService.confirmPlan(
          dischargeStudyId, dischargePatternId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when confirmPlan", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when confirmPlan", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * To save rule against discharging
   *
   * @param vesselId
   * @param dischargingInfoId
   * @param dischargeRuleRequest
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      value = "/vessels/{vesselId}/discharge-info-rule/{dischargingInfoId}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public RuleResponse saveRulesForDischarging(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long dischargingInfoId,
      @RequestBody RuleRequest dischargeRuleRequest,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.dischargeInformationGrpcService.getOrSaveRulesForDischarge(
          vesselId,
          dischargingInfoId,
          dischargeRuleRequest,
          headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving rules against discharge", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when saving rules for discharge", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * To retrieve rule against discharge
   *
   * @param vesselId
   * @param dischargingInfoId
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping(
      value = "/vessels/{vesselId}/discharge-info-rule/{dischargingInfoId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public RuleResponse getRulesForDischarging(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long dischargingInfoId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.dischargeInformationGrpcService.getOrSaveRulesForDischarge(
          vesselId, dischargingInfoId, null, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching rules against discharge", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when fetching rules for discharge", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-info/{infoId}/port-rotation/{portRotationId}")
  public DischargeInformation getDischargeInformationByPortRId(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId)
      throws CommonRestException {
    try {
      return dischargeInformationService.getDischargeInformation(
          vesselId, voyageId, portRotationId);
    } catch (GenericServiceException e) {
      log.error("get discharge info", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * To upload and save the discharging tide details to database
   *
   * @param headers
   * @param file
   * @return UploadTideDetailResponse
   * @throws CommonRestException
   */
  @PostMapping(
      value = "/discharging/{dischargingId}/upload/port-tide-details",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UploadTideDetailResponse uploadTideDetails(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long dischargingId,
      @RequestHeader HttpHeaders headers,
      @RequestParam(name = "file", required = true) MultipartFile file,
      @RequestParam(name = "portName", required = true) String portName,
      @RequestParam(name = "portId", required = true) Long portId)
      throws CommonRestException {
    try {
      log.debug("inside controller");
      return this.dischargeInformationGrpcService.dischargeUploadLoadingTideDetails(
          dischargingId, file, headers.getFirst(CORRELATION_ID_HEADER), portName, portId);

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when upload tide details", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when upload tide details", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * API to download discharging port tide details
   *
   * @return Port tide details in .xlsx format
   * @throws CommonRestException Exception object
   */
  @GetMapping(
      value = "/discharging/download/port-tide-template",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public HttpEntity<ByteArrayResource> downloadDischargingTideDetails(
      @RequestHeader HttpHeaders headers,
      @RequestParam(required = false, defaultValue = "0") Long dischargingId)
      throws CommonRestException {

    try {
      log.debug("inside controller");
      HttpHeaders header = new HttpHeaders();
      header.setContentType(new MediaType("application", "force-download"));
      header.set(
          HttpHeaders.CONTENT_DISPOSITION,
          "attachment; filename=" + DISCHARGING_PORT_TIDE_DETAIL_FILE_NAME);
      return new HttpEntity<>(
          new ByteArrayResource(
              dischargeInformationGrpcService.downloadLoadingPortTideDetails(dischargingId)),
          header);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in downloadDischargingPortTideDetails method", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception in downloadDischanrgingPortTideDetails method", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
  /**
   * Get Loading Sequence API
   *
   * @param headers
   * @param vesselId
   * @param voyageId
   * @param infoId
   * @return
   * @throws CommonRestException
   */
  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/discharging-info/{infoId}/discharging-plan/{portRotationId}")
  public DischargePlanResponse getDischargePlan(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId)
      throws CommonRestException {
    try {
      log.info(
          "Get Discharging Plan API for vessel {}, voyage {}, loading information {}",
          vesselId,
          voyageId,
          infoId);
      return dischargeInformationService.getDischargingPlan(
          vesselId, voyageId, infoId, portRotationId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in Get Loading Plan API");
      e.printStackTrace();
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in Get Discharging Plan API");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Save new Discharging Instruction
   *
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      "/new-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}")
  public DischargingInstructionsSaveResponse addDischargingInstructions(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @RequestBody DischargingInstructionsSaveRequest request)
      throws CommonRestException {
    try {
      log.info("Adding new Discharging instruction");
      return dischargingInstructionService.addDischargingInstruction(
          vesselId, infoId, portRotationId, request);

    } catch (GenericServiceException e) {
      log.error("Adding new Discharging Instruction failed");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in Adding new Discharging instruction");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Update Discharging Instruction status
   *
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      "/update-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}")
  public DischargingInstructionsSaveResponse updateDischargingInstructions(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @RequestBody DischargingInstructionsUpdateRequest request)
      throws CommonRestException {
    try {
      log.info("Updating Discharging instruction status for {}", vesselId);
      return dischargingInstructionService.updateDischargingInstructions(
          vesselId, infoId, portRotationId, request);

    } catch (GenericServiceException e) {
      log.error("Updating Discharging instruction status for {} failed", vesselId);
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in Updating Discharging instruction status for {} ", vesselId);
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Edit Discharging Instruction
   *
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      "/edit-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}")
  public DischargingInstructionsSaveResponse editDischargingInstructions(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @RequestBody DischargingInstructionsStatus request)
      throws CommonRestException {
    try {
      log.info("Editing Discharging instruction , id{}", request.getInstructionId());
      return dischargingInstructionService.editDischargingInstructions(
          vesselId, infoId, portRotationId, request);

    } catch (GenericServiceException e) {
      log.error("Editing Discharging instruction failed");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in Editing Discharging instruction");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Retrieve all discharging Instructions
   *
   * @return
   * @throws CommonRestException
   */
  @GetMapping("/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}")
  public DischargingInstructionResponse getAllDischargingInstructions(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId)
      throws CommonRestException {
    try {
      log.info(
          "Getting all Discharging instructions of vesselID: {} on port: {}",
          vesselId,
          portRotationId);
      return dischargingInstructionService.getDischargingInstructions(
          vesselId, infoId, portRotationId);

    } catch (GenericServiceException e) {
      log.error("Getting all Discharging instructions Failed error");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in Getting all Discharging instructions");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Delete Discharging Instruction
   *
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      "/delete-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}")
  public DischargingInstructionsSaveResponse deleteDischargingInstructions(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @RequestBody DischargingInstructionsStatus request)
      throws CommonRestException {
    try {
      log.info("Deleting Discharging instruction , id{}", request.getInstructionId());
      return dischargingInstructionService.deleteDischargingInstructions(
          vesselId, infoId, portRotationId, request);

    } catch (GenericServiceException e) {
      log.error("Deleting Discharging instruction failed");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in Deleting Discharging instruction");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Retrieve all update ullage details
   *
   * @return
   * @throws CommonRestException
   */
  // reusing the response which is already created for LS
  @GetMapping(
      "/vessels/{vesselId}/pattern/{patternId}/port/{portRotationId}/discharging/ullage/{operationType}")
  public DischargeUpdateUllageResponse getUpdateUllageDetails(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long patternId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @PathVariable String operationType)
      throws CommonRestException {
    try {
      log.info(
          "Getting all update ullage details of vesselID: {} of pattern: {} with port rotation id: {}",
          vesselId,
          patternId,
          portRotationId);
      return dischargeInformationService.getUpdateUllageDetails(
          vesselId, patternId, portRotationId, operationType);

    } catch (GenericServiceException e) {
      log.error("Getting update ullage details Failed error");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in \"Getting update ullage details");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Delete Discharging Instruction
   *
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      value = "/discharge/ullage-update",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UllageBillReply updateUllage(
      @RequestHeader HttpHeaders headers, @RequestBody UllageBillRequest inputData)
      throws CommonRestException {
    try {
      return dischargeInformationService.updateUllage(
          inputData, headers.getFirst(CORRELATION_ID_HEADER));

    } catch (GenericServiceException e) {
      log.error("Deleting Discharging instruction failed");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in Deleting Discharging instruction");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Save Discharging Information API
   *
   * @param request
   * @param headers
   * @param vesselId
   * @param voyageId
   * @return
   * @throws CommonRestException
   */
  @PostMapping("/vessels/{vesselId}/voyages/{voyageId}/discharging-info")
  public DischargingInformationResponse saveDischargingInformation(
      @RequestBody @Valid DischargingInformationRequest request,
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId)
      throws CommonRestException {
    try {
      log.info("Save Loading Info, api for vessel {}, voyage {}", vesselId, voyageId);
      return this.dischargeInformationGrpcService.saveDischargingInformation(
          request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("Exception in Save Loading Information API");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
}
