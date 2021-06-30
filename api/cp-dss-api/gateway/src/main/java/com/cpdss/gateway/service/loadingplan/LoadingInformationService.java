/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.gateway.domain.LoadableQuantityCargoDetails;
import com.cpdss.gateway.domain.UpdateUllage;
import com.cpdss.gateway.domain.loadingplan.*;
import java.util.List;

public interface LoadingInformationService {

  /**
   * Sunrise and Sunset Data must get and save at Synoptic Table in LS
   *
   * @param vesselId Long
   * @param voyageId Long
   * @param portRId Long
   * @return - LoadingDetails
   */
  LoadingDetails getLoadingDetailsByPortRotationId(
      LoadingPlanModels.LoadingDetails var1,
      Long vesselId,
      Long voyageId,
      Long portRId,
      Long portId);

  LoadingRates getLoadingRateForVessel(LoadingPlanModels.LoadingRates var1, Long vesselId);

  /**
   * Berth Data is based on Port, So The Port Id must be pass to Port-Info Service
   *
   * @param portId - Long
   * @return - BerthDetails
   */
  List<BerthDetails> getMasterBerthDetailsByPortId(Long portId);

  List<BerthDetails> buildLoadingPlanBerthDetails(List<LoadingPlanModels.LoadingBerths> var1);

  CargoMachineryInUse getCargoMachinesInUserFromVessel(
      List<LoadingPlanModels.LoadingMachinesInUse> var1, Long vesselId);

  LoadingStages getLoadingStagesAndMasters(LoadingPlanModels.LoadingStages var1);

  List<ToppingOffSequence> getToppingOffSequence(List<LoadingPlanModels.LoadingToppingOff> var1);

  List<LoadableQuantityCargoDetails> getLoadablePlanCargoDetailsByPort(
      Long patternId, String operationType, Long portRotationId, Long portId);

  LoadingSequences getLoadingSequence(LoadingPlanModels.LoadingDelay loadingDelay);

  LoadingInformationResponse saveLoadingInformation(
      LoadingInformationRequest request, String correlationId) throws GenericServiceException;

  UpdateUllage processUpdateUllage(
      Long vesselId,
      Long voyageId,
      Long loadingInfoId,
      Long portRotationId,
      UpdateUllage updateUllage,
      String correlationId)
      throws GenericServiceException;

  LoadingInfoAlgoResponse generateLoadingPlan(Long infoId) throws GenericServiceException;
}
