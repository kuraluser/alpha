/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.entity.LoadingInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class LoadingPlanService {

  @Autowired LoadingInformationService loadingInformationService;
  @Autowired CargoToppingOffSequenceService cargoToppingOffSequenceService;
  @Autowired LoadablePlanBallastDetailsService loadablePlanBallastDetailsService;
  @Autowired LoadablePlanCommingleDetailsService loadablePlanCommingleDetailsService;
  @Autowired LoadablePlanQuantityService loadablePlanQuantityService;
  @Autowired LoadablePlanStowageDetailsService loadablePlanStowageDetailsService;

  /**
   * @param request
   * @param builder
   */
  public void loadingPlanSynchronization(
      LoadingPlanSyncDetails request, LoadingPlanSyncReply.Builder builder) {
    try {
      LoadingInformation loadingInformation = new LoadingInformation();
      LoadingInformation savedLoadingInformation =
          loadingInformationService.saveLoadingInformation(
              request.getLoadingInformationDetail(), loadingInformation);
      cargoToppingOffSequenceService.saveCargoToppingOffSequenceList(
          request.getCargoToppingOffSequencesList(), savedLoadingInformation);
      loadablePlanBallastDetailsService.saveLoadablePlanBallastDetailsList(
          request.getLoadablePlanDetailsReply().getLoadablePlanBallastDetailsList(),
          savedLoadingInformation);
      loadablePlanCommingleDetailsService.saveLoadablePlanCommingleDetailsList(
          request.getLoadablePlanDetailsReply().getLoadableQuantityCommingleCargoDetailsList(),
          savedLoadingInformation);
      loadablePlanQuantityService.saveLoadablePlanQuantyList(
          request.getLoadablePlanDetailsReply().getLoadableQuantityCargoDetailsList(),
          savedLoadingInformation);
      loadablePlanStowageDetailsService.saveLoadablePlanStowageDetailsList(
          request.getLoadablePlanDetailsReply().getLoadablePlanStowageDetailsList(),
          savedLoadingInformation);
      log.info(
          "Saved LoadingInformation on port "
              + request.getLoadingInformationDetail().getPortId()
              + " of Loadable pattern "
              + request.getLoadingInformationDetail().getLoadablePatternId());
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setMessage("Successfully saved loading information in database")
              .setStatus(LoadingPlanConstants.SUCCESS)
              .build());
    } catch (Exception e) {
      log.info(
          "Failed to save LoadingInformation on port "
              + request.getLoadingInformationDetail().getPortId()
              + " of Loadable pattern "
              + request.getLoadingInformationDetail().getLoadablePatternId());
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setMessage("Error occured while saving loading information in database")
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setStatus(LoadingPlanConstants.SUCCESS)
              .build());
      e.printStackTrace();
    }
  }
}
