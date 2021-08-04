/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.algo;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest;
import com.cpdss.common.generated.LoadableStudy.JsonRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoAlgoRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.domain.algo.LoadingInformationAlgoRequest;
import com.cpdss.loadingplan.domain.algo.LoadingInformationAlgoResponse;
import com.cpdss.loadingplan.entity.BallastOperation;
import com.cpdss.loadingplan.entity.BallastValve;
import com.cpdss.loadingplan.entity.CargoLoadingRate;
import com.cpdss.loadingplan.entity.CargoValve;
import com.cpdss.loadingplan.entity.DeballastingRate;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingInformationAlgoStatus;
import com.cpdss.loadingplan.entity.LoadingInformationStatus;
import com.cpdss.loadingplan.entity.LoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingPlanRobDetails;
import com.cpdss.loadingplan.entity.LoadingSequenceStabilityParameters;
import com.cpdss.loadingplan.entity.PortLoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanRobDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanStabilityParameters;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageDetails;
import com.cpdss.loadingplan.repository.BallastOperationRepository;
import com.cpdss.loadingplan.repository.BallastValveRepository;
import com.cpdss.loadingplan.repository.CargoLoadingRateRepository;
import com.cpdss.loadingplan.repository.CargoValveRepository;
import com.cpdss.loadingplan.repository.DeballastingRateRepository;
import com.cpdss.loadingplan.repository.LoadingInformationAlgoStatusRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingInformationStatusRepository;
import com.cpdss.loadingplan.repository.LoadingPlanBallastDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanPortWiseDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanRobDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanStabilityParametersRepository;
import com.cpdss.loadingplan.repository.LoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingSequenceRepository;
import com.cpdss.loadingplan.repository.LoadingSequenceStabiltyParametersRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanBallastDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanRobDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStabilityParametersRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageDetailsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
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
  @Autowired LoadingPlanStabilityParametersRepository loadingPlanStabilityParametersRepository;
  @Autowired CargoLoadingRateRepository cargoLoadingRateRepository;
  @Autowired PortLoadingPlanBallastDetailsRepository portBallastDetailsRepository;
  @Autowired PortLoadingPlanRobDetailsRepository portRobDetailsRepository;
  @Autowired PortLoadingPlanStabilityParametersRepository portStabilityParamsRepository;
  @Autowired PortLoadingPlanStowageDetailsRepository portStowageDetailsRepository;
  @Autowired BallastOperationRepository ballastOperationRepository;
  @Autowired LoadingSequenceStabiltyParametersRepository loadingSequenceStabilityParamsRepository;

  @Autowired LoadingInformationAlgoRequestBuilderService loadingInfoAlgoRequestBuilderService;
  @Autowired LoadingPlanBuilderService loadingPlanBuilderService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyService;

  /**
   * CALL TO ALGO - for LOADING PLAN Generates Loading plan from Algo
   *
   * @param request Loading Information Id Only
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

    // Create JSON To Algo
    LoadingInformationAlgoRequest algoRequest =
        loadingInfoAlgoRequestBuilderService.createAlgoRequest(request);

    // Save Above JSON In LS json data Table
    saveLoadingInformationRequestJson(algoRequest, request.getLoadingInfoId());

    // Call To Algo End Point for Loading
    LoadingInformationAlgoResponse response =
        restTemplate.postForObject(
            planGenerationUrl, algoRequest, LoadingInformationAlgoResponse.class);

    // Set Loading Status
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
    createLoadingInformationAlgoStatus(
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
  public void createLoadingInformationAlgoStatus(
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
    log.info("Saving Loading Information ALGO request to Loadable study DB");
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

  /**
   * Saves Loading sequence and Plan
   *
   * @param request
   * @throws GenericServiceException
   */
  public void saveLoadingSequenceAndPlan(LoadingPlanSaveRequest request)
      throws GenericServiceException {
    log.info(
        "Saving Loading plan and sequence of loading information {}", request.getLoadingInfoId());

    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findById(request.getLoadingInfoId());
    if (loadingInfoOpt.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information " + request.getLoadingInfoId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    List<com.cpdss.loadingplan.entity.LoadingSequence> oldLoadingSequences =
        loadingSequenceRepository.findByLoadingInformationAndIsActive(loadingInfoOpt.get(), true);
    // Saving Loading Sequence
    request.getLoadingSequencesList().stream()
        .forEach(
            sequence -> {
              saveLoadingSequence(sequence, loadingInfoOpt.get());
            });

    log.info("Deleting Old Loading Sequences of LoadingInformation {}", request.getLoadingInfoId());
    deleteLoadingSequences(oldLoadingSequences);

    List<LoadingSequenceStabilityParameters> oldLoadingSequenceStabilityParameters =
        loadingSequenceStabilityParamsRepository.findByLoadingInformationAndIsActive(
            loadingInfoOpt.get(), true);

    saveLoadingSequenceStabilityParams(request, loadingInfoOpt.get());

    deleteLoadingSequenceStabilityParams(
        loadingInfoOpt.get(), oldLoadingSequenceStabilityParameters);

    List<PortLoadingPlanStabilityParameters> oldStabilityParams =
        portStabilityParamsRepository.findByLoadingInformationAndIsActive(
            loadingInfoOpt.get(), true);
    List<PortLoadingPlanStowageDetails> oldStowageDetails =
        portStowageDetailsRepository.findByLoadingInformationAndIsActive(
            loadingInfoOpt.get(), true);
    List<PortLoadingPlanBallastDetails> oldBallastDetails =
        portBallastDetailsRepository.findByLoadingInformationAndIsActive(
            loadingInfoOpt.get(), true);
    List<PortLoadingPlanRobDetails> oldRobDetails =
        portRobDetailsRepository.findByLoadingInformationAndIsActive(loadingInfoOpt.get(), true);

    // Saving Loading Plan
    saveLoadingPlan(request, loadingInfoOpt.get());

    log.info("Deleting Old Loading Plan of LoadingInformation {}", request.getLoadingInfoId());
    deleteLoadingPlan(oldStowageDetails, oldBallastDetails, oldRobDetails, oldStabilityParams);

    Optional<LoadingInformationStatus> loadingInfoStatusOpt =
        loadingInfoStatusRepository.findByIdAndIsActive(
            LoadingPlanConstants.LOADING_INFORMATION_PLAN_GENERATED_ID, true);
    if (loadingInfoStatusOpt.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information status with id "
              + LoadingPlanConstants.LOADING_INFORMATION_PLAN_GENERATED_ID,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    loadingInfoOpt.get().setLoadingInformationStatus(loadingInfoStatusOpt.get());
    loadingInformationRepository.save(loadingInfoOpt.get());
    updateLoadingInfoAlgoStatus(
        loadingInfoOpt.get(), request.getProcessId(), loadingInfoStatusOpt.get());
  }

  private void updateLoadingInfoAlgoStatus(
      LoadingInformation loadingInformation,
      String processId,
      LoadingInformationStatus loadingInformationStatus)
      throws GenericServiceException {
    Optional<LoadingInformationAlgoStatus> algoStatus =
        this.loadingInfoAlgoStatusRepository.findByProcessIdAndIsActiveTrue(processId);
    if (algoStatus.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information algo status with process id " + processId,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    algoStatus.get().setLoadingInformationStatus(loadingInformationStatus);
    this.loadingInfoAlgoStatusRepository.save(algoStatus.get());
  }

  private void deleteLoadingSequenceStabilityParams(
      LoadingInformation loadingInformation,
      List<LoadingSequenceStabilityParameters> oldLoadingSequenceStabilityParameters) {
    log.info(
        "Deleting Old Loading Sequence Stability Parameters of Loading Information {}",
        loadingInformation.getId());
    oldLoadingSequenceStabilityParameters.forEach(
        param -> {
          loadingSequenceStabilityParamsRepository.deleteById(param.getId());
        });
    ;
  }

  private void saveLoadingSequenceStabilityParams(
      LoadingPlanSaveRequest request, LoadingInformation loadingInformation) {
    log.info(
        "Saving Loading Sequence Stability Params for LoadingInformation {}, PortRotation",
        loadingInformation.getId(),
        loadingInformation.getPortRotationXId());
    request
        .getLoadingSequenceStabilityParametersList()
        .forEach(
            param -> {
              LoadingSequenceStabilityParameters stabilityParameters =
                  new LoadingSequenceStabilityParameters();
              loadingPlanBuilderService.buildLoadingSequenceStabilityParams(
                  loadingInformation, param, stabilityParameters);
              loadingSequenceStabilityParamsRepository.save(stabilityParameters);
            });
  }

  private void deleteLoadingPlan(
      List<PortLoadingPlanStowageDetails> oldStowageDetails,
      List<PortLoadingPlanBallastDetails> oldBallastDetails,
      List<PortLoadingPlanRobDetails> oldRobDetails,
      List<PortLoadingPlanStabilityParameters> oldStabilityParams) {

    oldBallastDetails.forEach(
        ballast -> {
          log.info("Deleting Port Loading Plan Ballast {}", ballast.getId());
          portBallastDetailsRepository.deleteById(ballast.getId());
        });

    oldRobDetails.forEach(
        rob -> {
          log.info("Deleting Port Loading Plan ROB {}", rob.getId());
          portRobDetailsRepository.deleteById(rob.getId());
        });

    oldStabilityParams.forEach(
        params -> {
          log.info("Deleting Port Loading Plan Stability Params {}", params.getId());
          portStabilityParamsRepository.deleteById(params.getId());
        });

    oldStowageDetails.forEach(
        stowage -> {
          log.info("Deleting Port Loading Plan Stowage {}", stowage.getId());
          portStowageDetailsRepository.deleteById(stowage.getId());
        });
  }

  private void deleteLoadingSequences(
      List<com.cpdss.loadingplan.entity.LoadingSequence> oldLoadingSequences) {
    oldLoadingSequences.forEach(
        loadingSequence -> {
          log.info("Deleting Loading Sequence {}", loadingSequence.getId());
          loadingSequenceRepository.deleteById(loadingSequence.getId());
          ballastOperationRepository.deleteByLoadingSequence(loadingSequence);
          cargoLoadingRateRepository.deleteByLoadingSequence(loadingSequence);
          deballastingRateRepository.deleteByLoadingSequence(loadingSequence);
          deleteLoadingPlanPortWiseDetailsByLoadingSequence(loadingSequence);
        });
  }

  private void deleteLoadingPlanPortWiseDetailsByLoadingSequence(
      com.cpdss.loadingplan.entity.LoadingSequence loadingSequence) {
    List<com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails> oldPortWiseDetails =
        loadingPlanPortWiseDetailsRepository.findByLoadingSequenceAndIsActiveTrueOrderById(
            loadingSequence);
    oldPortWiseDetails.forEach(
        loadingPlanPortWiseDetails -> {
          loadingPlanPortWiseDetailsRepository.deleteById(loadingPlanPortWiseDetails.getId());
          deballastingRateRepository.deleteByLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
          loadingPlanBallastDetailsRepository.deleteByLoadingPlanPortWiseDetails(
              loadingPlanPortWiseDetails);
          loadingPlanRobDetailsRepository.deleteByLoadingPlanPortWiseDetails(
              loadingPlanPortWiseDetails);
          loadingPlanStabilityParametersRepository.deleteByLoadingPlanPortWiseDetails(
              loadingPlanPortWiseDetails);
          loadingPlanStowageDetailsRepository.deleteByLoadingPlanPortWiseDetails(
              loadingPlanPortWiseDetails);
        });
  }

  private void saveLoadingPlan(
      LoadingPlanSaveRequest request, LoadingInformation loadingInformation) {
    savePortBallastDetails(loadingInformation, request.getPortLoadingPlanBallastDetailsList());
    savePortRobDetails(loadingInformation, request.getPortLoadingPlanRobDetailsList());
    savePortStabilityParams(
        loadingInformation, request.getPortLoadingPlanStabilityParametersList());
    savePortStowageDetails(loadingInformation, request.getPortLoadingPlanStowageDetailsList());
  }

  private void savePortStowageDetails(
      LoadingInformation loadingInformation,
      List<LoadingPlanTankDetails> portLoadingPlanStowageDetailsList) {
    log.info(
        "Saving Loading Plan Stowage Details for LoadingInformation {}, PortRotation",
        loadingInformation.getId(),
        loadingInformation.getPortRotationXId());
    portLoadingPlanStowageDetailsList.forEach(
        stowage -> {
          PortLoadingPlanStowageDetails stowageDetails = new PortLoadingPlanStowageDetails();
          loadingPlanBuilderService.buildPortStowage(loadingInformation, stowageDetails, stowage);
          portStowageDetailsRepository.save(stowageDetails);
        });
  }

  private void savePortStabilityParams(
      LoadingInformation loadingInformation,
      List<LoadingPlanStabilityParameters> portLoadingPlanStabilityParametersList) {
    log.info(
        "Saving Loading Plan Stability Parameters for LoadingInformation {}, PortRotation",
        loadingInformation.getId(),
        loadingInformation.getPortRotationXId());
    portLoadingPlanStabilityParametersList.forEach(
        params -> {
          PortLoadingPlanStabilityParameters stabilityParams =
              new PortLoadingPlanStabilityParameters();
          loadingPlanBuilderService.buildPortStabilityParams(
              loadingInformation, stabilityParams, params);
          portStabilityParamsRepository.save(stabilityParams);
        });
  }

  private void savePortRobDetails(
      LoadingInformation loadingInformation,
      List<LoadingPlanTankDetails> portLoadingPlanRobDetailsList) {
    log.info(
        "Saving Loading Plan ROB Details for LoadingInformation {}, PortRotation",
        loadingInformation.getId(),
        loadingInformation.getPortRotationXId());
    portLoadingPlanRobDetailsList.forEach(
        rob -> {
          PortLoadingPlanRobDetails robDetails = new PortLoadingPlanRobDetails();
          loadingPlanBuilderService.buildPortRob(loadingInformation, robDetails, rob);
          portRobDetailsRepository.save(robDetails);
        });
  }

  private void savePortBallastDetails(
      LoadingInformation loadingInformation,
      List<LoadingPlanTankDetails> portLoadingPlanBallastDetailsList) {
    log.info(
        "Saving Loading Plan Ballast Details for LoadingInformation {}, PortRotation",
        loadingInformation.getId(),
        loadingInformation.getPortRotationXId());
    portLoadingPlanBallastDetailsList.forEach(
        ballast -> {
          PortLoadingPlanBallastDetails ballastDetails = new PortLoadingPlanBallastDetails();
          loadingPlanBuilderService.buildPortBallast(loadingInformation, ballastDetails, ballast);
          portBallastDetailsRepository.save(ballastDetails);
        });
  }

  /**
   * Saves loading sequence
   *
   * @param sequence
   * @param loadingInformation
   */
  private void saveLoadingSequence(
      LoadingSequence sequence, LoadingInformation loadingInformation) {
    log.info("Saving Loading sequence of loading information {}", loadingInformation.getId());
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
    saveCargoLoadingRates(savedLoadingSequence, sequence.getLoadingRatesList());
    saveBallastPumps(savedLoadingSequence, sequence.getBallastOperationsList());
  }

  private void saveBallastPumps(
      com.cpdss.loadingplan.entity.LoadingSequence loadingSequence,
      List<PumpOperation> ballastOperationsList) {
    log.info("Saving Ballast Pumps for Loading Sequence {}", loadingSequence.getId());
    ballastOperationsList.forEach(
        pumpOperation -> {
          BallastOperation ballastOperation = new BallastOperation();
          loadingPlanBuilderService.buildBallastOperation(
              loadingSequence, ballastOperation, pumpOperation);
          ballastOperationRepository.save(ballastOperation);
        });
  }

  private void saveCargoLoadingRates(
      com.cpdss.loadingplan.entity.LoadingSequence loadingSequence,
      List<LoadingRate> loadingRatesList) {
    log.info("Saving Cargo Loading Rates for Loading Sequence {}", loadingSequence.getId());
    loadingRatesList.forEach(
        loadingRate -> {
          CargoLoadingRate cargoLoadingRate = new CargoLoadingRate();
          loadingPlanBuilderService.buildCargoLoadingRate(
              loadingSequence, cargoLoadingRate, loadingRate);
          cargoLoadingRateRepository.save(cargoLoadingRate);
        });
  }

  private void saveLoadingPlanPortWiseDetails(
      com.cpdss.loadingplan.entity.LoadingSequence loadingSequence,
      List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsList) {
    log.info(
        "Saving Loading Plan PortWise Details for Loading Sequence {}", loadingSequence.getId());
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
          saveLoadingPlanStabilityParameters(
              savedPortWiseDetails, details.getLoadingPlanStabilityParameters());
        });
  }

  private void saveLoadingPlanStabilityParameters(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      LoadingPlanStabilityParameters loadingPlanStabilityParameters) {
    log.info(
        "Saving Stability Parameters for LoadingPlanPortWiseDetails {}",
        loadingPlanPortWiseDetails.getId());
    com.cpdss.loadingplan.entity.LoadingPlanStabilityParameters parameters =
        new com.cpdss.loadingplan.entity.LoadingPlanStabilityParameters();
    loadingPlanBuilderService.buildStabilityParameters(
        loadingPlanPortWiseDetails, parameters, loadingPlanStabilityParameters);
    loadingPlanStabilityParametersRepository.save(parameters);
  }

  private void saveLoadingPlanStowageDetails(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      List<LoadingPlanTankDetails> loadingPlanStowageDetailsList) {
    log.info(
        "Saving Loading Plan Stowage Details for LoadingPlanPortWiseDetails {}",
        loadingPlanPortWiseDetails.getId());
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
    log.info(
        "Saving Loading Plan ROB Details for LoadingPlanPortWiseDetails {}",
        loadingPlanPortWiseDetails.getId());
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
    log.info(
        "Saving Loading Plan Ballast Details for LoadingPlanPortWiseDetails {}",
        loadingPlanPortWiseDetails.getId());
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
    log.info(
        "Saving DeBallastingRates for LoadingPlanPortWiseDetails {}",
        loadingPlanPortWiseDetails.getId());
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
    log.info("Saving DeBallastingRates for LoadingSequence {}", loadingSequence.getId());
    deBallastingRatesList.forEach(
        rate -> {
          DeballastingRate deballastingRate = new DeballastingRate();
          loadingPlanBuilderService.buildDeBallastingRate(loadingSequence, deballastingRate, rate);
          deballastingRateRepository.save(deballastingRate);
        });
  }

  private void saveCargoValves(
      com.cpdss.loadingplan.entity.LoadingSequence loadingSequence, List<Valve> cargoValvesList) {
    log.info("Saving CargoValves for LoadingSequence {}", loadingSequence.getId());
    cargoValvesList.forEach(
        valve -> {
          CargoValve cargoValve = new CargoValve();
          loadingPlanBuilderService.buildCargoValve(loadingSequence, cargoValve, valve);
          cargoValveRepository.save(cargoValve);
        });
  }

  private void saveBallastValves(
      com.cpdss.loadingplan.entity.LoadingSequence loadingSequence, List<Valve> ballastValvesList) {
    log.info("Saving BallastValves for LoadingSequence {}", loadingSequence.getId());
    ballastValvesList.forEach(
        valve -> {
          BallastValve ballastValve = new BallastValve();
          loadingPlanBuilderService.buildBallastValve(loadingSequence, ballastValve, valve);
          this.ballastValveRepository.save(ballastValve);
        });
  }
}
