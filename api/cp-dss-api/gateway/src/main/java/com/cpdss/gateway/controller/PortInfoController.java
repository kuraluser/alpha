/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.CountrysResponse;
import com.cpdss.gateway.domain.PortDetailResponse;
import com.cpdss.gateway.service.InstructionService;
import com.cpdss.gateway.service.PortInfoService;
import javax.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class PortInfoController {

  private static final String CORRELATION_ID_HEADER = "correlationId";

  @Autowired InstructionService instructionService;

  @Autowired PortInfoService portInfoService;

  @GetMapping("/port-instructions")
  public Object getDischargeStudyByVoyage(@RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.instructionService.getInstructions(headers.getFirst(CORRELATION_ID_HEADER));
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
   * Get port master details with berth information for the requested port id.
   *
   * @param portId
   * @param headers
   * @return PortDetailResponse
   * @throws CommonRestException
   */
  @GetMapping("/portInfo/{portId}")
  public PortDetailResponse getPortDetailsByPortId(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long portId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.portInfoService.getPortInformationByPortId(
          portId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching getPortInformationByPortId", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error fetching getPortInformationByPortId", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Get all countries.
   *
   * @param headers
   * @return CountrysResponse
   * @throws CommonRestException
   */
  @GetMapping("/countries")
  public CountrysResponse getAllCountrys(@RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return this.portInfoService.getAllCountrys(headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching getPortInformationByPortId", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error fetching getPortInformationByPortId", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
}
