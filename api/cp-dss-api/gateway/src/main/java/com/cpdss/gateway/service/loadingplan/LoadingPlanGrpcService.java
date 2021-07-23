/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoSaveResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest.Builder;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.domain.loadingplan.CargoVesselTankDetails;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import java.util.List;

public interface LoadingPlanGrpcService {

  VoyageResponse getActiveVoyageDetails(Long vesselId) throws GenericServiceException;

  Object getPortRotationDetailsForActiveVoyage(Long vesselId);

  LoadableStudy.LoadingSynopticResponse fetchSynopticRecordForPortRotationArrivalCondition(
      Long portRId) throws GenericServiceException;

  PortInfo.PortDetail fetchPortDetailByPortId(Long portId) throws GenericServiceException;

  LoadingPlanModels.LoadingInformation fetchLoadingInformation(
      Long vesselId, Long voyageId, Long loadingInfoId, Long patternId, Long portRotationId)
      throws GenericServiceException;

  CargoVesselTankDetails fetchPortWiseCargoDetails(
      Long vesselId,
      Long voyageId,
      Long loadableStudyId,
      Long portId,
      Long portOrder,
      Long portRotationId,
      String operationType);

  List<LoadableStudy.LoadableQuantityCargoDetails> fetchLoadablePlanCargoDetails(
      Long patternId, String operationType, Long portRotationId, Long portId);

  LoadingInfoSaveResponse saveLoadingInformation(LoadingInformation loadingInformation);

  Boolean updateUllageAtLoadingPlan(LoadingPlanModels.UpdateUllageLoadingRequest request)
      throws GenericServiceException;

  ResponseStatus generateLoadingPlan(Long loadingInfoId);

  RuleResponse saveOrGetLoadingPlanRules(LoadingPlanModels.LoadingPlanRuleRequest.Builder builder)
      throws GenericServiceException;

  LoadingSequenceReply getLoadingSequence(Builder builder) throws GenericServiceException;
}
