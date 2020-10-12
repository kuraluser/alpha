/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.Voyage;
import com.cpdss.gateway.service.GatewayService;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Validated
@RestController
@RequestMapping("/api")
public class GatewayController {

  @Autowired private GatewayService gatewayService;

  /**
   * 
   * @param voyage
   * @param vesselId
   * @param headers
   * @return
   * @throws CommonRestException
   * CommonSuccessResponse
   */
  @PostMapping("/{env}/vessels/{vesselId}/voyages")
  public CommonSuccessResponse saveVoyage(
      @RequestBody @Valid Voyage voyage,
      @PathVariable long vesselId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    CommonSuccessResponse response = null;
    try {
      Long companyId = 1L; // TODO get the companyId from userContext in keycloak token
      response = gatewayService.saveVoyage(voyage, companyId, vesselId, headers);
    } catch (Exception e) {
      log.error("Error in save voyage ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatus.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
    return response;
  }
}
