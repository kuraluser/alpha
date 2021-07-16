/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.algo;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.JsonRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoAlgoRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStowageDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.domain.algo.LoadingInformationAlgoRequest;
import com.cpdss.loadingplan.domain.algo.LoadingInformationAlgoResponse;
import com.cpdss.loadingplan.entity.BallastValve;
import com.cpdss.loadingplan.entity.CargoValve;
import com.cpdss.loadingplan.entity.DeballastingRate;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingInformationAlgoStatus;
import com.cpdss.loadingplan.entity.LoadingInformationStatus;
import com.cpdss.loadingplan.entity.LoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingPlanRobDetails;
import com.cpdss.loadingplan.repository.BallastValveRepository;
import com.cpdss.loadingplan.repository.CargoValveRepository;
import com.cpdss.loadingplan.repository.DeballastingRateRepository;
import com.cpdss.loadingplan.repository.LoadingInformationAlgoStatusRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingInformationStatusRepository;
import com.cpdss.loadingplan.repository.LoadingPlanBallastDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanPortWiseDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanRobDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingSequenceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class LoadingPlanAlgoService {

  @Value(value = "${algo.planGenerationUrl}")
  private String planGenerationUrl;

  @Autowired RestTemplate restTemplate;

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired LoadingInformationStatusRepository loadingInfoStatusRepository;
  @Autowired LoadingInformationAlgoStatusRepository loadingInfoAlgoStatusRepository;

  @Autowired LoadingSequenceRepository loadingSequenceRepository;
  @Autowired BallastValveRepository ballastValveRepository;
  @Autowired CargoValveRepository cargoValveRepository;
  @Autowired DeballastingRateRepository deballastingRateRepository;
  @Autowired LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;
  @Autowired LoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @Autowired LoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;
  @Autowired LoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;

  @Autowired LoadingInformationAlgoRequestBuilderService loadingInfoAlgoRequestBuilderService;
  @Autowired LoadingPlanBuilderService loadingPlanBuilderService;

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

  public void saveLoadingPlan(LoadingPlanSaveRequest request) throws GenericServiceException {
    log.info(
        "Saving Loading plan and sequence of loading information {}", request.getLoadingInfoId());

    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(request.getLoadingInfoId());
    if (loadingInfoOpt.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information " + request.getLoadingInfoId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    request.getLoadingSequencesList().stream()
        .forEach(
            sequence -> {
              saveLoadingSequence(sequence, loadingInfoOpt.get());
            });
  }

  private void saveLoadingSequence(
      LoadingSequence sequence, LoadingInformation loadingInformation) {
    com.cpdss.loadingplan.entity.LoadingSequence loadingSequence =
        new com.cpdss.loadingplan.entity.LoadingSequence();
    loadingPlanBuilderService.buildLoadingSequence(loadingSequence, sequence, loadingInformation);
    com.cpdss.loadingplan.entity.LoadingSequence savedLoadingSequence =
        loadingSequenceRepository.save(loadingSequence);

    saveBallastValves(savedLoadingSequence, sequence.getBallastValvesList());
    saveCargoValves(savedLoadingSequence, sequence.getCargoValvesList());
    saveDeBallastingRates(savedLoadingSequence, sequence.getDeBallastingRatesList());
    saveLoadingPlanPortWiseDetails(
        savedLoadingSequence, sequence.getLoadingPlanPortWiseDetailsList());
    sequence.getLoadingRatesList();
  }

  private void saveLoadingPlanPortWiseDetails(
      com.cpdss.loadingplan.entity.LoadingSequence loadingSequence,
      List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsList) {
    loadingPlanPortWiseDetailsList.forEach(
        details -> {
          com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails portWiseDetails =
              new com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails();
          loadingPlanBuilderService.buildLoadingPlanPortWiseDetails(
              loadingSequence, portWiseDetails, details);
          com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails savedPortWiseDetails =
              loadingPlanPortWiseDetailsRepository.save(portWiseDetails);
          saveDeBallastingRates(savedPortWiseDetails, details.getDeballastingRatesList());
          saveLoadingPlanBallastDetails(
              savedPortWiseDetails, details.getLoadingPlanBallastDetailsList());
          saveLoadingPlanRobDetails(savedPortWiseDetails, details.getLoadingPlanRobDetailsList());
          saveLoadingPlanStowageDetails(
              savedPortWiseDetails, details.getLoadingPlanStowageDetailsList());
        });
  }

  private void saveLoadingPlanStowageDetails(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      List<LoadingPlanStowageDetails> loadingPlanStowageDetailsList) {
    loadingPlanStowageDetailsList.forEach(
        stowage -> {
          com.cpdss.loadingplan.entity.LoadingPlanStowageDetails stowageDetails =
              new com.cpdss.loadingplan.entity.LoadingPlanStowageDetails();
          loadingPlanBuilderService.buildLoadingPlanStowageDetails(
              loadingPlanPortWiseDetails, stowageDetails, stowage);
          loadingPlanStowageDetailsRepository.save(stowageDetails);
        });
  }

  private void saveLoadingPlanRobDetails(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      List<LoadingPlanTankDetails> loadingPlanRobDetailsList) {
    loadingPlanRobDetailsList.forEach(
        rob -> {
          LoadingPlanRobDetails robDetails = new LoadingPlanRobDetails();
          loadingPlanBuilderService.buildLoadingPlanRobDetails(
              loadingPlanPortWiseDetails, robDetails, rob);
          loadingPlanRobDetailsRepository.save(robDetails);
        });
  }

  private void saveLoadingPlanBallastDetails(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      List<LoadingPlanTankDetails> loadingPlanBallastDetailsList) {
    loadingPlanBallastDetailsList.forEach(
        ballast -> {
          LoadingPlanBallastDetails ballastDetails = new LoadingPlanBallastDetails();
          loadingPlanBuilderService.buildLoadingPlanBallastDetails(
              loadingPlanPortWiseDetails, ballastDetails, ballast);
          loadingPlanBallastDetailsRepository.save(ballastDetails);
        });
  }

  private void saveDeBallastingRates(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      List<DeBallastingRate> deballastingRatesList) {
    deballastingRatesList.forEach(
        rate -> {
          DeballastingRate deballastingRate = new DeballastingRate();
          loadingPlanBuilderService.buildDeBallastingRate(
              loadingPlanPortWiseDetails, deballastingRate, rate);
          deballastingRateRepository.save(deballastingRate);
        });
  }

  private void saveDeBallastingRates(
      com.cpdss.loadingplan.entity.LoadingSequence loadingSequence,
      List<DeBallastingRate> deBallastingRatesList) {
    deBallastingRatesList.forEach(
        rate -> {
          DeballastingRate deballastingRate = new DeballastingRate();
          loadingPlanBuilderService.buildDeBallastingRate(loadingSequence, deballastingRate, rate);
          deballastingRateRepository.save(deballastingRate);
        });
  }

  private void saveCargoValves(
      com.cpdss.loadingplan.entity.LoadingSequence loadingSequence, List<Valve> cargoValvesList) {
    cargoValvesList.forEach(
        valve -> {
          CargoValve cargoValve = new CargoValve();
          loadingPlanBuilderService.buildCargoValve(loadingSequence, cargoValve, valve);
          cargoValveRepository.save(cargoValve);
        });
  }

  private void saveBallastValves(
      com.cpdss.loadingplan.entity.LoadingSequence loadingSequence, List<Valve> ballastValvesList) {
    ballastValvesList.forEach(
        valve -> {
          BallastValve ballastValve = new BallastValve();
          loadingPlanBuilderService.buildBallastValve(loadingSequence, ballastValve, valve);
          this.ballastValveRepository.save(ballastValve);
        });
  }
}
