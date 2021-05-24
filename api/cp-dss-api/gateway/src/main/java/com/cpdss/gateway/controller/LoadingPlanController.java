/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationResponse;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import javax.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class LoadingPlanController {

  @Autowired LoadingPlanService loadingPlanService;

  /**
   * Loading Information Get API,
   *
   * <p>Some data collect from Loadable study service and others in Loading plan DB
   *
   * @param vesselId - Long
   * @param voyageId - Long
   * @param planId - Long
   * @param portRotationId - Long
   * @return - LoadingInformationResponse
   */
  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/loading-plan/{id}/loading-information/{portRotationId}")
  public LoadingInformationResponse getLoadingInformation(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long planId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId) {
    LoadingInformationResponse loadingInformationResponse =
        this.loadingPlanService.getLoadingInformationByPortRotation(
            vesselId, voyageId, planId, portRotationId);
    return loadingInformationResponse;
  }
}
