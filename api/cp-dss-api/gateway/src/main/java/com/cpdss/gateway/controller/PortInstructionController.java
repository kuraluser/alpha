/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.service.InstructionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class PortInstructionController {

  private static final String CORRELATION_ID_HEADER = "correlationId";

  @Autowired InstructionService instructionService;

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
}
