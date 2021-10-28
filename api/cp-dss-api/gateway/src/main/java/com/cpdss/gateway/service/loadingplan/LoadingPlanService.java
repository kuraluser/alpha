/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.loadingplan.LoadingInformation;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationRequest;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoRequest;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface LoadingPlanService {

  Object getLoadingPortRotationDetails(Long vesselId, Long portRId) throws GenericServiceException;

  LoadingInformation getLoadingInformationByPortRotation(Long vesselId, Long planId, Long portRId)
      throws GenericServiceException;

  public LoadingInformationResponse saveLoadingInformation(
      LoadingInformationRequest request, String correlationId) throws GenericServiceException;

  RuleResponse getLoadingPlanRules(Long vesselId, Long voyageId, Long loadingInfoId)
      throws GenericServiceException;

  RuleResponse saveLoadingPlanRules(
      Long vesselId, Long voyageId, Long loadingInfoId, RuleRequest ruleRequest)
      throws GenericServiceException;

  LoadingSequenceResponse getLoadingSequence(Long vesselId, Long voyageId, Long infoId)
      throws GenericServiceException;

  LoadingPlanAlgoResponse saveLoadingPlan(
      Long vesselId, Long voyageId, Long infoId, LoadingPlanAlgoRequest loadingPlanAlgoRequest)
      throws GenericServiceException, Exception;

  LoadingPlanResponse getLoadingPlan(Long vesselId, Long voyageId, Long infoId, Long portRotationId)
      throws GenericServiceException;

  public LoadingUpdateUllageResponse getUpdateUllageDetails(
      Long vesselId, Long patternId, Long portId, String operationType, boolean isDischarging)
      throws GenericServiceException;

  UllageBillReply getLoadableStudyShoreTwo(
      String first, UllageBillRequest inputData, boolean isDischarging)
      throws GenericServiceException;

  UploadTideDetailResponse uploadLoadingTideDetails(
      Long loadingId, MultipartFile file, String correlationId, String portName, Long portId)
      throws IOException, GenericServiceException;

  byte[] downloadLoadingPortTideDetails(Long loadingId) throws GenericServiceException;

  public void buildTankLayout(Long vesselId, LoadingPlanResponse loadingPlanResponse);

  public com.cpdss.common.generated.LoadableStudy.CargoNominationReply getCargoNominationsByStudyId(
      Long studyId);

  ListOfUllageReportResponse importUllageReportFile(
      MultipartFile file,
      String tankDetails,
      Long infoId,
      Long cargoNominationId,
      String correlationId,
      boolean isLoading,
      Long vesselId)
      throws IOException, GenericServiceException;
}
