/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.loadingplan.LoadingInformation;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import javax.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class LoadingPlanController {

  @Autowired LoadingPlanService loadingPlanService;

  /**
   * Get API to collect the port rotation details of active Voyage
   *
   * @param vesselId Long
   * @param id Long: Always 0.
   * @return
   */
  @GetMapping("/vessels/{vesselId}/loading-plan/{id}")
  public Object getPortRotationDetails(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long id)
      throws CommonRestException {
    try {
      return loadingPlanService.getLoadingPortRotationDetails(vesselId, id);
    } catch (GenericServiceException e) {
      log.error("Custom exception in Loading Plan Port Rotation");
      e.printStackTrace();
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception in Loading Plan Port Rotation");
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
   * Loading Information Get API,
   *
   * <p>Some data collect from Loadable study service and others in Loading plan DB
   *
   * @param vesselId Long
   * @param planId Long
   * @param voyageId Long
   * @param portRotationId Long
   * @return LoadingInformation
   */
  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/loading-plan/{planId}/loading-information/{portRotationId}")
  public LoadingInformation getLoadingInformation(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long planId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId)
      throws CommonRestException {
    try {
      log.info("Get Loading Info, api for vessel {}, Port Rotation {}", vesselId, portRotationId);
      LoadingInformation var1 =
          this.loadingPlanService.getLoadingInformationByPortRotation(
              vesselId, planId, portRotationId);
      return var1;
    } catch (GenericServiceException e) {
      e.printStackTrace();
      log.error("Custom exception in Get Loading Information API - {}", e.getMessage());
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception in Get Loading Information API");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }
}
