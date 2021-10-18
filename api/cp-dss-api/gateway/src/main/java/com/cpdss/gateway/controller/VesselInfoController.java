/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.RuleRequest;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.domain.VesselDetailsResponse;
import com.cpdss.gateway.domain.VesselResponse;
import com.cpdss.gateway.domain.VesselTankResponse;
import com.cpdss.gateway.domain.VesselsInfoResponse;
import com.cpdss.gateway.service.VesselInfoService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.websocket.server.PathParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gateway controller for vessel related operations
 *
 * @author suhail.k
 */
@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class VesselInfoController {

  @Autowired private VesselInfoService vesselInfoService;

  private static final String CORRELATION_ID_HEADER = "correlationId";

  @GetMapping(value = "/vessels", produces = MediaType.APPLICATION_JSON_VALUE)
  public VesselResponse getVesselsByCompany(@RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      final Long companyId = 1L;
      return this.vesselInfoService.getVesselsByCompany(
          companyId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching vessels", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when fetching vessels", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
  /**
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping(value = "/vessel/{vesselId}/cargo-tanks", produces = MediaType.APPLICATION_JSON_VALUE)
  public VesselTankResponse getVesselTanks(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.vesselInfoService.getCargoVesselTanks(
          vesselId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching vessels", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when fetching vessels", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @GetMapping(value = "/vessel-details/{vesselId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public VesselDetailsResponse getVesselsDetails(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @RequestHeader HttpHeaders headers,
      @PathParam("enableValveSeq") boolean enableValveSeq)
      throws CommonRestException {
    try {
      return this.vesselInfoService.getVesselsDetails(
          vesselId, headers.getFirst(CORRELATION_ID_HEADER), enableValveSeq);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching vessels", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when fetching vessels", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * To save rule for vessel
   *
   * @param vesselId
   * @param sectionId
   * @param vesselRuleRequest
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      value = "/vessel-rule/vessels/{vesselId}/ruleMasterSectionId/{sectionId}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public RuleResponse saveRulesForVessel(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable
          @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          @Max(value = 3, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long sectionId,
      @RequestBody RuleRequest vesselRuleRequest,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.vesselInfoService.getRulesByVesselIdAndSectionId(
          vesselId, sectionId, vesselRuleRequest, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching vessels", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when fetching vessels", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * To retrieve rule for vessel
   *
   * @param vesselId
   * @param sectionId
   * @param vesselRuleRequest
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping(
      value = "/vessel-rule/vessels/{vesselId}/ruleMasterSectionId/{sectionId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public RuleResponse getAllRulesForVessel(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable
          @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          @Max(value = 3, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long sectionId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.vesselInfoService.getRulesByVesselIdAndSectionId(
          vesselId, sectionId, null, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching vessels", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when fetching vessels", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
  
  /**
   * Get all vessel informations
   * @param headers
   * @param pageSize
   * @param pageNo
   * @param sortBy
   * @param orderBy
   * @param vesselName
   * @param vesselType
   * @param builder
   * @param dateOfLaunch
   * @return VesselsInfoResponse
   * @throws CommonRestException
   */
  @GetMapping(value = "/all-vessels-info", produces = MediaType.APPLICATION_JSON_VALUE)
  public VesselsInfoResponse getAllVesselInformation(
      @RequestHeader HttpHeaders headers,
      @RequestParam(required = false, defaultValue = "10") int pageSize,
      @RequestParam(required = false, defaultValue = "0") int pageNo,
      @RequestParam(required = false) String sortBy,
      @RequestParam(required = false) String orderBy,
      @RequestParam(required = false) String vesselName,
      @RequestParam(required = false) String vesselType,
      @RequestParam(required = false) String builder,
      @RequestParam(required = false) String dateOfLaunch)
      throws CommonRestException {
    try {

    	log.info("inside fetching vessels");
      return this.vesselInfoService.getAllVesselsInormation(pageSize, pageNo, sortBy, orderBy, 
    		  vesselName, vesselType, builder, dateOfLaunch, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching all vessels", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when fetching all vessels", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
}
