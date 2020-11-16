/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.PortsResponse;
import com.cpdss.gateway.service.CargoPortInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Gateway controller for cargo and port info related operations */
@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class CargoPortInfoController {

  @Autowired private CargoPortInfoService cargoPortInfoService;

  /**
   * Retrieves port information for a specific cargo
   *
   * @param id
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping("/cargos/{id}/ports")
  public PortsResponse getPortsByCargo(@PathVariable Long id, @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    PortsResponse response = null;
    try {
      Long companyId = 1L; // TODO get the companyId from userContext in keycloak token
      response = cargoPortInfoService.getPortsByCargoId(id, headers);
    } catch (Exception e) {
      log.error("Error in getCargoNomination ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
    return response;
  }

  /**
   * Retrieve ports information from port master
   *
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping("/ports")
  public PortsResponse getPorts(@RequestHeader HttpHeaders headers) throws CommonRestException {
    PortsResponse response = null;
    try {
      Long companyId = 1L; // TODO get the companyId from userContext in keycloak token
      response = cargoPortInfoService.getPorts(companyId, headers);
    } catch (Exception e) {
      log.error("Error in getCargoNomination ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
    return response;
  }
}
