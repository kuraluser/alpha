/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.CargosResponse;
import com.cpdss.gateway.domain.PortsResponse;
import com.cpdss.gateway.domain.TimezoneRestResponse;
import com.cpdss.gateway.domain.cargomaster.CargoDetailedResponse;
import com.cpdss.gateway.domain.cargomaster.CargosDetailedResponse;
import com.cpdss.gateway.service.CargoPortInfoService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** Gateway controller for cargo and port info related operations */
@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class CargoPortInfoController {

  private static final String CORRELATION_ID_HEADER = "correlationId";

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
      log.info("getPortsByCargo: {}", getClientIp());
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
      log.info("getPorts: {}", getClientIp());
      response = cargoPortInfoService.getPorts(headers);
    } catch (Exception e) {
      log.error("Error in getPorts ", e);
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
   * Retrieve cargos information from cargo master
   *
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping("/cargos")
  public CargosResponse getCargos(@RequestHeader HttpHeaders headers) throws CommonRestException {
    CargosResponse response = null;
    try {
      log.info("getCargos: {}", getClientIp());
      response = cargoPortInfoService.getCargos(headers);
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
   * Fetch available timezones in table Timezone, 'cpdss-ports'
   *
   * <p>Grpc call to port-info
   *
   * @see com.cpdss.gateway.domain.Timezone
   * @param headers
   * @return {@link com.cpdss.gateway.domain.TimezoneRestResponse}
   * @throws GenericServiceException
   * @throws CommonRestException
   */
  @GetMapping("/global-timezones")
  public TimezoneRestResponse getTimezones(@RequestHeader HttpHeaders headers)
      throws GenericServiceException, CommonRestException {
    TimezoneRestResponse response = null;
    try {
      response = cargoPortInfoService.getTimezones();
      response.getResponseStatus().setCorrelationId(headers.getFirst(CORRELATION_ID_HEADER));
      log.info(
          "Fetch all timezone success - CORRELATION_ID - {}",
          headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("Error in timezone fetch, Message {}", e.getMessage());
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
   * Retrieve detailed cargos information from cargo master
   *
   * @param headers
   * @param pageSize
   * @param page
   * @param sortBy
   * @param orderBy
   * @param params
   * @return response
   * @throws CommonRestException
   */
  @GetMapping("/master/cargos")
  public CargosDetailedResponse getDetailedCargos(
      @RequestHeader HttpHeaders headers,
      @RequestParam(required = false, defaultValue = "10") int pageSize,
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "crudeType") String sortBy,
      @RequestParam(required = false, defaultValue = "asc") String orderBy,
      @RequestParam Map<String, String> params)
      throws CommonRestException {
    CargosDetailedResponse response;
    try {
      log.info("getCargos: {}", getClientIp());
      response =
          cargoPortInfoService.getCargosDetailed(
              page, pageSize, sortBy, orderBy, params, CORRELATION_ID_HEADER);
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
   * Retrieve detailed cargos information from cargo master
   *
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping("/master/cargos/{cargoId}")
  public CargoDetailedResponse getDetailedCargoById(
      @PathVariable Long cargoId, @RequestHeader HttpHeaders headers) throws CommonRestException {
    CargoDetailedResponse response;
    try {
      log.info("getCargos: {}", getClientIp());
      response = cargoPortInfoService.getCargosDetailedById(headers, cargoId);
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
   * Delete API for deletion of cargo using cargoId
   *
   * @param cargoId
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @DeleteMapping("/master/cargos/{cargoId}")
  public CargoDetailedResponse deleteCargoById(
      @PathVariable Long cargoId, @RequestHeader HttpHeaders headers) throws CommonRestException {
    CargoDetailedResponse response;
    try {
      log.info("Deleting cargo with id: {}", cargoId);
      response = cargoPortInfoService.deleteCargoById(CORRELATION_ID_HEADER, cargoId);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
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
