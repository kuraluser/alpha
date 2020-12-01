/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.VesselResponse;
import com.cpdss.gateway.service.VesselInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
}