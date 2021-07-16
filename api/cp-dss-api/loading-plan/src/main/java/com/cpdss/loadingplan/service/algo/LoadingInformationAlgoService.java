/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.algo;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest;
import com.cpdss.common.generated.LoadableStudy.JsonRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoAlgoRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.domain.algo.LoadingInformationAlgoRequest;
import com.cpdss.loadingplan.domain.algo.LoadingInformationAlgoResponse;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingInformationAlgoStatus;
import com.cpdss.loadingplan.entity.LoadingInformationStatus;
import com.cpdss.loadingplan.repository.LoadingInformationAlgoStatusRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingInformationStatusRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class LoadingInformationAlgoService {

  @Value(value = "${algo.planGenerationUrl}")
  private String planGenerationUrl;

  @Autowired RestTemplate restTemplate;

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired LoadingInformationStatusRepository loadingInfoStatusRepository;
  @Autowired LoadingInformationAlgoStatusRepository loadingInfoAlgoStatusRepository;

  @Autowired LoadingInformationAlgoRequestBuilderService loadingInfoAlgoRequestBuilderService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyService;

  /**
   * Generates Loading plan
   *
   * @param request
   * @throws GenericServiceException
   */
  public void generateLoadingPlan(LoadingInfoAlgoRequest request) throws GenericServiceException {
    log.info("Generating Loading Plan");
    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(request.getLoadingInfoId());

    if (loadingInfoOpt.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information " + request.getLoadingInfoId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    LoadingInformationAlgoRequest algoRequest =
        loadingInfoAlgoRequestBuilderService.createAlgoRequest(request);

    saveLoadingInformationRequestJson(algoRequest, request.getLoadingInfoId());

    LoadingInformationAlgoResponse response =
        restTemplate.postForObject(
            planGenerationUrl, algoRequest, LoadingInformationAlgoResponse.class);

    Optional<LoadingInformationStatus> loadingInfoStatusOpt =
        loadingInfoStatusRepository.findByIdAndIsActive(
            LoadingPlanConstants.LOADING_INFORMATION_PROCESSING_STARTED_ID, true);
    if (loadingInfoStatusOpt.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information status with id "
              + LoadingPlanConstants.LOADING_INFORMATION_PROCESSING_STARTED_ID,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    loadingInfoOpt.get().setLoadingInformationStatus(loadingInfoStatusOpt.get());
    loadingInformationRepository.save(loadingInfoOpt.get());
    updateLoadingInformationAlgoStatus(
        loadingInfoOpt.get(), response.getProcessId(), loadingInfoStatusOpt.get());
  }

  /** @param request void */
  public void saveAlgoLoadingPlanStatus(AlgoStatusRequest request) throws GenericServiceException {
    Optional<LoadingInformationAlgoStatus> loadingInfoStatusOpt =
        loadingInfoAlgoStatusRepository.findByProcessIdAndIsActiveTrue(request.getProcesssId());
    if (loadingInfoStatusOpt.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information " + request.getProcesssId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    loadingInfoAlgoStatusRepository.updateLoadingInformationAlgoStatus(
        request.getLoadableStudystatusId(), request.getProcesssId());
  }

  /**
   * Updates ALGO status of Loading Information
   *
   * @param loadingInformation
   * @param processId
   * @param status
   */
  public void updateLoadingInformationAlgoStatus(
      LoadingInformation loadingInformation, String processId, LoadingInformationStatus status) {
    LoadingInformationAlgoStatus algoStatus = new LoadingInformationAlgoStatus();
    algoStatus.setIsActive(true);
    algoStatus.setLoadingInformation(loadingInformation);
    algoStatus.setLoadingInformationStatus(status);
    algoStatus.setProcessId(processId);
    algoStatus.setVesselXId(loadingInformation.getVesselXId());
    loadingInfoAlgoStatusRepository.save(algoStatus);
  }

  /**
   * Saves the Loading Information ALGO Request JSON to DB.
   *
   * @param algoRequest
   * @param loadingInfoId
   * @throws GenericServiceException
   */
  private void saveLoadingInformationRequestJson(
      LoadingInformationAlgoRequest algoRequest, Long loadingInfoId)
      throws GenericServiceException {
    log.info("Saving Loading Information ALGO request to DB");
    JsonRequest.Builder jsonBuilder = JsonRequest.newBuilder();
    jsonBuilder.setReferenceId(loadingInfoId);
    jsonBuilder.setJsonTypeId(LoadingPlanConstants.LOADING_INFORMATION_REQUEST_JSON_TYPE_ID);
    ObjectMapper mapper = new ObjectMapper();
    try {
      jsonBuilder.setJson(mapper.writeValueAsString(algoRequest));
      this.loadableStudyService.saveJson(jsonBuilder.build());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Could not save request JSON to DB",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }
}
