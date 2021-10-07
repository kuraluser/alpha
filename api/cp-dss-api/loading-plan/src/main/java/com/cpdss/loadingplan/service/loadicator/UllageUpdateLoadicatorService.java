/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.loadicator;

import static java.lang.String.valueOf;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.JsonRequest;
import com.cpdss.common.generated.Loadicator;
import com.cpdss.common.generated.Loadicator.StowagePlan;
import com.cpdss.common.generated.Loadicator.StowagePlan.Builder;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.domain.algo.LoadicatorAlgoResponse;
import com.cpdss.loadingplan.domain.algo.LoadicatorBallastDetails;
import com.cpdss.loadingplan.domain.algo.LoadicatorResult;
import com.cpdss.loadingplan.domain.algo.LoadicatorRobDetails;
import com.cpdss.loadingplan.domain.algo.LoadicatorStage;
import com.cpdss.loadingplan.domain.algo.LoadicatorStowageDetails;
import com.cpdss.loadingplan.domain.algo.LoadingPlanLoadicatorDetails;
import com.cpdss.loadingplan.domain.algo.UllageEditLoadicatorAlgoRequest;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingInformationStatus;
import com.cpdss.loadingplan.entity.PortLoadingPlanBallastTempDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanRobDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanStabilityParameters;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageTempDetails;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanBallastDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanBallastTempDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanRobDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStabilityParametersRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageTempDetailsRepository;
import com.cpdss.loadingplan.service.LoadingPlanService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/** @author pranav.k */
@Slf4j
@Service
@Transactional
public class UllageUpdateLoadicatorService {

  @Value(value = "${algo.loadicatorUrl}")
  private String loadicatorUrl;

  @Value(value = "${loadingplan.attachment.rootFolder}")
  private String rootFolder;

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;
  @Autowired PortLoadingPlanBallastDetailsRepository portLoadingPlanBallastDetailsRepository;
  @Autowired PortLoadingPlanRobDetailsRepository portLoadingPlanRobDetailsRepository;

  @Autowired
  PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageDetailsTempRepository;

  @Autowired
  PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastDetailsTempRepository;

  @Autowired
  PortLoadingPlanStabilityParametersRepository portLoadingPlanStabilityParametersRepository;

  @Autowired LoadingPlanAlgoService loadingPlanAlgoService;
  @Autowired LoadicatorService loadicatorService;
  @Autowired LoadingPlanService loadingPlanService;

  @Autowired RestTemplate restTemplate;

  /**
   * Sends StowagePlans to loadicator-integration MS for Loadicator processing.
   *
   * @param request
   * @throws GenericServiceException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public String saveLoadicatorInfoForUllageUpdate(UllageBillRequest request)
      throws GenericServiceException, IllegalAccessException, InvocationTargetException {
    Loadicator.LoadicatorRequest.Builder loadicatorRequestBuilder =
        Loadicator.LoadicatorRequest.newBuilder();
    Long loadingInfoId = request.getUpdateUllage(0).getLoadingInformationId();
    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(loadingInfoId);
    if (loadingInfoOpt.isEmpty()) {
      log.info("Cannot find loading information with id {}", loadingInfoId);
      throw new GenericServiceException(
          "Could not find loading information " + loadingInfoId,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    String processId = UUID.randomUUID().toString();
    VesselInfo.VesselReply vesselReply =
        loadicatorService.getVesselDetailsForLoadicator(loadingInfoOpt.get());
    if (!vesselReply.getVesselsList().get(0).getHasLoadicator()) {
      log.info("Vessel has no loadicator");
      UllageEditLoadicatorAlgoRequest algoRequest = new UllageEditLoadicatorAlgoRequest();
      LoadingInfoLoadicatorDataRequest.Builder loadicatorDataRequestBuilder =
          LoadingInfoLoadicatorDataRequest.newBuilder();
      loadicatorDataRequestBuilder.setProcessId(processId);
      loadicatorDataRequestBuilder.setConditionType(
          request.getUpdateUllage(0).getArrivalDepartutre());
      loadicatorDataRequestBuilder.setProcessId(processId);
      buildUllageEditLoadicatorAlgoRequest(
          loadingInfoOpt.get(), loadicatorDataRequestBuilder.build(), algoRequest);
      saveUllageEditLoadicatorRequestJson(algoRequest, loadingInfoOpt.get().getId());
      checkStabilityWithAlgo(
          loadingInfoOpt.get(),
          algoRequest,
          processId,
          request.getUpdateUllage(0).getArrivalDepartutre());
      Optional<LoadingInformationStatus> loadingInfoStatusOpt =
          loadingPlanAlgoService.getLoadingInformationStatus(
              LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID);
      loadingPlanService.updateLoadingPlanStatus(
          loadingInfoOpt.get(),
          loadingInfoStatusOpt.get(),
          request.getUpdateUllage(0).getArrivalDepartutre());
      loadingPlanAlgoService.createLoadingInformationAlgoStatus(
          loadingInfoOpt.get(),
          processId,
          loadingInfoStatusOpt.get(),
          request.getUpdateUllage(0).getArrivalDepartutre());
      loadingPlanService.saveUpdatedLoadingPlanDetails(
          loadingInfoOpt.get(), request.getUpdateUllage(0).getArrivalDepartutre());
      log.info(
          "Saved updated loading plan details of loading information {}",
          loadingInfoOpt.get().getId());
      return processId;
    }
    List<PortLoadingPlanStowageTempDetails> tempStowageDetails =
        portLoadingPlanStowageDetailsTempRepository
            .findByLoadingInformationAndConditionTypeAndIsActive(
                loadingInfoOpt.get().getId(),
                request.getUpdateUllage(0).getArrivalDepartutre(),
                true);
    List<PortLoadingPlanBallastTempDetails> tempBallastDetails =
        portLoadingPlanBallastDetailsTempRepository
            .findByLoadingInformationAndConditionTypeAndIsActive(
                loadingInfoOpt.get().getId(),
                request.getUpdateUllage(0).getArrivalDepartutre(),
                true);
    List<PortLoadingPlanRobDetails> robDetails =
        portLoadingPlanRobDetailsRepository
            .findByLoadingInformationAndConditionTypeAndValueTypeAndIsActive(
                loadingInfoOpt.get().getId(),
                request.getUpdateUllage(0).getArrivalDepartutre(),
                LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE,
                true);
    Set<Long> cargoNominationIds = new LinkedHashSet<Long>();

    cargoNominationIds.addAll(
        tempStowageDetails.stream()
            .map(PortLoadingPlanStowageTempDetails::getCargoNominationXId)
            .collect(Collectors.toList()));
    Map<Long, CargoNominationDetail> cargoNomDetails =
        loadicatorService.getCargoNominationDetails(cargoNominationIds);
    CargoInfo.CargoReply cargoReply =
        loadicatorService.getCargoInfoForLoadicator(loadingInfoOpt.get());
    PortInfo.PortReply portReply = loadicatorService.getPortInfoForLoadicator(loadingInfoOpt.get());

    loadicatorRequestBuilder.setTypeId(LoadingPlanConstants.LOADING_INFORMATION_LOADICATOR_TYPE_ID);
    loadicatorRequestBuilder.setIsUllageUpdate(true);
    loadicatorRequestBuilder.setConditionType(request.getUpdateUllage(0).getArrivalDepartutre());
    StowagePlan.Builder stowagePlanBuilder = StowagePlan.newBuilder();
    loadicatorService.buildStowagePlan(
        loadingInfoOpt.get(), 0, processId, cargoReply, vesselReply, portReply, stowagePlanBuilder);
    buildStowagePlanDetails(
        loadingInfoOpt.get(),
        tempStowageDetails,
        cargoNomDetails,
        vesselReply,
        cargoReply,
        stowagePlanBuilder);
    buildCargoDetails(
        loadingInfoOpt.get(), cargoNomDetails, tempStowageDetails, cargoReply, stowagePlanBuilder);
    buildBallastDetails(loadingInfoOpt.get(), tempBallastDetails, vesselReply, stowagePlanBuilder);
    buildRobDetails(loadingInfoOpt.get(), robDetails, vesselReply, stowagePlanBuilder);
    loadicatorRequestBuilder.addStowagePlanDetails(stowagePlanBuilder.build());
    Loadicator.LoadicatorReply reply =
        loadicatorService.saveLoadicatorInfo(loadicatorRequestBuilder.build());
    if (!reply.getResponseStatus().getStatus().equals(LoadingPlanConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to send Stowage plans to Loadicator",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    Optional<LoadingInformationStatus> loadingInfoStatusOpt =
        loadingPlanAlgoService.getLoadingInformationStatus(
            LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_STARTED_ID);

    loadingPlanService.updateLoadingPlanStatus(
        loadingInfoOpt.get(),
        loadingInfoStatusOpt.get(),
        request.getUpdateUllage(0).getArrivalDepartutre());

    loadingPlanAlgoService.createLoadingInformationAlgoStatus(
        loadingInfoOpt.get(),
        processId,
        loadingInfoStatusOpt.get(),
        request.getUpdateUllage(0).getArrivalDepartutre());
    return processId;
  }

  /**
   * @param conditionType
   * @param processId
   * @param algoRequest
   * @throws GenericServiceException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   * @throws NumberFormatException
   */
  private void checkStabilityWithAlgo(
      LoadingInformation loadingInformation,
      UllageEditLoadicatorAlgoRequest algoRequest,
      String processId,
      int conditionType)
      throws GenericServiceException, NumberFormatException, IllegalAccessException,
          InvocationTargetException {
    LoadicatorAlgoResponse algoResponse =
        restTemplate.postForObject(loadicatorUrl, algoRequest, LoadicatorAlgoResponse.class);
    saveLoadicatorResponseJson(algoResponse, loadingInformation.getId());

    if (algoResponse.getLoadicatorResults().isEmpty()) {
      Optional<LoadingInformationStatus> validationFailedStatusOpt =
          loadingPlanAlgoService.getLoadingInformationStatus(
              LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID);
      loadingPlanService.updateLoadingPlanStatus(
          loadingInformation, validationFailedStatusOpt.get(), conditionType);
      loadingPlanAlgoService.updateLoadingInfoAlgoStatus(
          loadingInformation, processId, validationFailedStatusOpt.get());
    } else {
      if (algoResponse.getLoadicatorResults().get(0).getErrorDetails().size() > 0) {
        Optional<LoadingInformationStatus> validationFailedStatusOpt =
            loadingPlanAlgoService.getLoadingInformationStatus(
                LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID);
        loadingPlanService.updateLoadingPlanStatus(
            loadingInformation, validationFailedStatusOpt.get(), conditionType);
        loadingPlanAlgoService.updateLoadingInfoAlgoStatus(
            loadingInformation, processId, validationFailedStatusOpt.get());
      } else {
        saveLoadingPlanStabilityParameters(
            loadingInformation,
            algoResponse,
            conditionType,
            LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE);
        Optional<LoadingInformationStatus> loadingInfoStatusOpt =
            loadingPlanAlgoService.getLoadingInformationStatus(
                LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID);
        loadingPlanService.updateLoadingPlanStatus(
            loadingInformation, loadingInfoStatusOpt.get(), conditionType);
        loadingPlanAlgoService.updateLoadingInfoAlgoStatus(
            loadingInformation, processId, loadingInfoStatusOpt.get());
        loadingPlanService.saveUpdatedLoadingPlanDetails(loadingInformation, conditionType);
      }
    }
  }

  /**
   * Build ROB details for Stowage Plan
   *
   * @param loadingInformation
   * @param robDetails
   * @param vesselReply
   * @param stowagePlanBuilder
   */
  private void buildRobDetails(
      LoadingInformation loadingInformation,
      List<PortLoadingPlanRobDetails> robDetails,
      VesselReply vesselReply,
      Builder stowagePlanBuilder) {
    robDetails.forEach(
        rob -> {
          Loadicator.OtherTankInfo.Builder otherTankBuilder = Loadicator.OtherTankInfo.newBuilder();
          otherTankBuilder.setTankId(rob.getTankXId());
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(rob.getTankXId()))
                  .findAny();
          if (tankDetail.isPresent()) {
            Optional.ofNullable(tankDetail.get().getTankName())
                .ifPresent(otherTankBuilder::setTankName);
            Optional.ofNullable(tankDetail.get().getShortName())
                .ifPresent(otherTankBuilder::setShortName);
          }
          Optional.ofNullable(rob.getQuantity())
              .ifPresent(qty -> otherTankBuilder.setQuantity(valueOf(qty)));
          stowagePlanBuilder.addOtherTankInfo(otherTankBuilder.build());
        });
  }

  /**
   * Builds ballast details for Stowage Plan
   *
   * @param loadingInformation
   * @param tempBallastDetails
   * @param vesselReply
   * @param stowagePlanBuilder
   */
  private void buildBallastDetails(
      LoadingInformation loadingInformation,
      List<PortLoadingPlanBallastTempDetails> tempBallastDetails,
      VesselReply vesselReply,
      Builder stowagePlanBuilder) {
    tempBallastDetails.forEach(
        ballast -> {
          Loadicator.BallastInfo.Builder ballastBuilder = Loadicator.BallastInfo.newBuilder();
          Optional.ofNullable(String.valueOf(ballast.getQuantity()))
              .ifPresent(ballastBuilder::setQuantity);
          Optional.ofNullable(stowagePlanBuilder.getStowageId())
              .ifPresent(ballastBuilder::setStowageId);
          Optional.ofNullable(ballast.getTankXId()).ifPresent(ballastBuilder::setTankId);
          Optional.ofNullable(loadingInformation.getPortXId()).ifPresent(ballastBuilder::setPortId);
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(ballast.getTankXId()))
                  .findAny();
          if (tankDetail.isPresent()) {
            Optional.ofNullable(tankDetail.get().getTankName())
                .ifPresent(ballastBuilder::setTankName);
            Optional.ofNullable(tankDetail.get().getShortName())
                .ifPresent(ballastBuilder::setShortName);
          }
          stowagePlanBuilder.addBallastInfo(ballastBuilder.build());
        });
  }

  /**
   * Builds Cargo Details for Stowage Plan
   *
   * @param loadingInformation
   * @param cargoNomDetails
   * @param tempStowageDetails
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  private void buildCargoDetails(
      LoadingInformation loadingInformation,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      List<PortLoadingPlanStowageTempDetails> tempStowageDetails,
      CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    tempStowageDetails.stream()
        .map(stowage -> stowage.getCargoNominationXId())
        .filter(cargoNominationId -> cargoNominationId.longValue() != 0)
        .collect(Collectors.toSet())
        .forEach(
            cargoNominationId -> {
              Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
              Optional.ofNullable(
                      String.valueOf(cargoNomDetails.get(cargoNominationId).getAbbreviation()))
                  .ifPresent(cargoBuilder::setCargoAbbrev);
              Optional<PortLoadingPlanStowageTempDetails> stowageOpt =
                  tempStowageDetails.stream()
                      .filter(stwg -> stwg.getCargoNominationXId().equals(cargoNominationId))
                      .findAny();
              stowageOpt.ifPresent(
                  stwg -> {
                    Optional.ofNullable(String.valueOf(stwg.getApi()))
                        .ifPresent(cargoBuilder::setApi);
                    Optional.ofNullable(String.valueOf(stwg.getTemperature()))
                        .ifPresent(cargoBuilder::setStandardTemp);
                  });
              Optional.ofNullable(cargoNomDetails.get(cargoNominationId).getCargoId())
                  .ifPresent(cargoBuilder::setCargoId);
              Optional.ofNullable(loadingInformation.getPortXId())
                  .ifPresent(cargoBuilder::setPortId);
              Optional.ofNullable(stowagePlanBuilder.getStowageId())
                  .ifPresent(cargoBuilder::setStowageId);
              stowagePlanBuilder.addCargoInfo(cargoBuilder.build());
            });
  }

  /**
   * Builds stowage plan details
   *
   * @param loadingInformation
   * @param tempStowageDetails
   * @param cargoNomDetails
   * @param vesselReply
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  private void buildStowagePlanDetails(
      LoadingInformation loadingInformation,
      List<PortLoadingPlanStowageTempDetails> tempStowageDetails,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      VesselReply vesselReply,
      CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    tempStowageDetails.stream()
        .filter(stowage -> stowage.getCargoNominationXId().longValue() != 0)
        .forEach(
            stowage -> {
              Loadicator.StowageDetails.Builder stowageDetailsBuilder =
                  Loadicator.StowageDetails.newBuilder();
              CargoNominationDetail cargoNomDetail =
                  cargoNomDetails.get(stowage.getCargoNominationXId());
              Optional.ofNullable(cargoNomDetail.getCargoId())
                  .ifPresent(stowageDetailsBuilder::setCargoId);
              Optional.ofNullable(stowage.getTankXId()).ifPresent(stowageDetailsBuilder::setTankId);
              Optional.ofNullable(stowage.getQuantity())
                  .ifPresent(
                      quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));
              Optional.ofNullable(cargoNomDetail.getAbbreviation())
                  .ifPresent(stowageDetailsBuilder::setCargoName);
              Optional.ofNullable(loadingInformation.getPortXId())
                  .ifPresent(stowageDetailsBuilder::setPortId);
              Optional.ofNullable(stowageDetailsBuilder.getStowageId())
                  .ifPresent(stowageDetailsBuilder::setStowageId);
              Optional<VesselInfo.VesselTankDetail> tankDetail =
                  vesselReply.getVesselTanksList().stream()
                      .filter(tank -> Long.valueOf(tank.getTankId()).equals(stowage.getTankXId()))
                      .findAny();
              if (tankDetail.isPresent()) {
                Optional.ofNullable(tankDetail.get().getTankName())
                    .ifPresent(stowageDetailsBuilder::setTankName);
                Optional.ofNullable(tankDetail.get().getShortName())
                    .ifPresent(stowageDetailsBuilder::setShortName);
              }
              stowagePlanBuilder.addStowageDetails(stowageDetailsBuilder.build());
            });
  }

  /**
   * @param request
   * @throws GenericServiceException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public void getLoadicatorData(LoadingInfoLoadicatorDataRequest request)
      throws GenericServiceException, IllegalAccessException, InvocationTargetException {
    log.info(
        "Recieved stability parameters of Loading Plam of Loading Information {} from Loadicator",
        request.getLoadingInformationId());
    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(request.getLoadingInformationId());
    if (loadingInfoOpt.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information " + request.getLoadingInformationId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    UllageEditLoadicatorAlgoRequest algoRequest = new UllageEditLoadicatorAlgoRequest();
    buildUllageEditLoadicatorAlgoRequest(loadingInfoOpt.get(), request, algoRequest);
    saveUllageEditLoadicatorRequestJson(algoRequest, loadingInfoOpt.get().getId());

    LoadicatorAlgoResponse algoResponse =
        restTemplate.postForObject(loadicatorUrl, algoRequest, LoadicatorAlgoResponse.class);
    saveLoadicatorResponseJson(algoResponse, loadingInfoOpt.get().getId());

    if (algoResponse.getLoadicatorResults().get(0).getErrorDetails().size() > 0) {

      Optional<LoadingInformationStatus> validationFailedStatusOpt =
          loadingPlanAlgoService.getLoadingInformationStatus(
              LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID);
      loadingPlanService.updateLoadingPlanStatus(
          loadingInfoOpt.get(), validationFailedStatusOpt.get(), request.getConditionType());
      loadingPlanAlgoService.updateLoadingInfoAlgoStatus(
          loadingInfoOpt.get(), request.getProcessId(), validationFailedStatusOpt.get());

    } else {
      saveLoadingPlanStabilityParameters(
          loadingInfoOpt.get(),
          algoResponse,
          request.getConditionType(),
          LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE);
      Optional<LoadingInformationStatus> loadingInfoStatusOpt =
          loadingPlanAlgoService.getLoadingInformationStatus(
              LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID);
      loadingPlanService.updateLoadingPlanStatus(
          loadingInfoOpt.get(), loadingInfoStatusOpt.get(), request.getConditionType());
      loadingPlanAlgoService.updateLoadingInfoAlgoStatus(
          loadingInfoOpt.get(), request.getProcessId(), loadingInfoStatusOpt.get());
      loadingPlanService.saveUpdatedLoadingPlanDetails(
          loadingInfoOpt.get(), request.getConditionType());
    }
  }

  /**
   * @param loadingInformation
   * @param algoResponse
   * @param conditionType
   */
  private void saveLoadingPlanStabilityParameters(
      LoadingInformation loadingInformation,
      LoadicatorAlgoResponse algoResponse,
      int conditionType,
      int valueType) {
    PortLoadingPlanStabilityParameters stabilityParameters =
        new PortLoadingPlanStabilityParameters();
    buildLoadingPlanStabilityParams(
        algoResponse, loadingInformation, conditionType, valueType, stabilityParameters);
    portLoadingPlanStabilityParametersRepository.save(stabilityParameters);
  }

  /**
   * @param algoResponse
   * @param loadingInformation
   * @param conditionType
   * @param valueType
   * @param stabilityParameters
   */
  private void buildLoadingPlanStabilityParams(
      LoadicatorAlgoResponse algoResponse,
      LoadingInformation loadingInformation,
      int conditionType,
      int valueType,
      PortLoadingPlanStabilityParameters stabilityParameters) {
    LoadicatorResult result = algoResponse.getLoadicatorResults().get(0);
    loadicatorService.buildPortStabilityParams(
        loadingInformation, result, stabilityParameters, conditionType, valueType);
    portLoadingPlanStabilityParametersRepository.save(stabilityParameters);
  }

  /**
   * @param algoResponse
   * @param id
   * @throws GenericServiceException
   */
  private void saveLoadicatorResponseJson(LoadicatorAlgoResponse algoResponse, Long loadingInfoId)
      throws GenericServiceException {
    log.info("Saving Loadicator response to Loadable study DB");
    JsonRequest.Builder jsonBuilder = JsonRequest.newBuilder();
    jsonBuilder.setReferenceId(loadingInfoId);
    jsonBuilder.setJsonTypeId(
        LoadingPlanConstants.UPDATE_ULLAGE_EDIT_LOADICATOR_RESPONSE_JSON_TYPE_ID);
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(
          new File(
              this.rootFolder
                  + "/json/loadingPlanEditLoadicatorResponse_"
                  + loadingInfoId
                  + ".json"),
          algoResponse);
      jsonBuilder.setJson(mapper.writeValueAsString(algoResponse));
      loadicatorService.saveJson(jsonBuilder);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Could not save response JSON to DB",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } catch (IOException e) {
      throw new GenericServiceException(
          "Could not save response JSON to Filesystem",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * @param algoRequest
   * @param id
   * @throws GenericServiceException
   */
  private void saveUllageEditLoadicatorRequestJson(
      UllageEditLoadicatorAlgoRequest algoRequest, Long loadingInfoId)
      throws GenericServiceException {
    log.info("Saving Loadicator request to Loadable study DB");
    JsonRequest.Builder jsonBuilder = JsonRequest.newBuilder();
    jsonBuilder.setReferenceId(loadingInfoId);
    jsonBuilder.setJsonTypeId(LoadingPlanConstants.UPDATE_ULLAGE_LOADICATOR_REQUEST_JSON_TYPE_ID);
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(
          new File(
              this.rootFolder
                  + "/json/loadingPlanEditLoadicatorRequest_"
                  + loadingInfoId
                  + ".json"),
          algoRequest);
      jsonBuilder.setJson(mapper.writeValueAsString(algoRequest));
      loadicatorService.saveJson(jsonBuilder);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Could not save request JSON to DB",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } catch (IOException e) {
      throw new GenericServiceException(
          "Could not save request JSON to Filesystem",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * @param loadingInformation
   * @param request
   * @param algoRequest
   */
  private void buildUllageEditLoadicatorAlgoRequest(
      LoadingInformation loadingInformation,
      LoadingInfoLoadicatorDataRequest request,
      UllageEditLoadicatorAlgoRequest algoRequest) {
    algoRequest.setLoadingInformationId(loadingInformation.getId());
    algoRequest.setProcessId(request.getProcessId());
    algoRequest.setVesselId(loadingInformation.getVesselXId());
    algoRequest.setPortId(loadingInformation.getPortXId());
    algoRequest.setLoadableStudyProcessId(loadingInformation.getLoadableStudyProcessId());
    List<LoadicatorStage> stages = new ArrayList<LoadicatorStage>();
    request
        .getLoadingInfoLoadicatorDetailsList()
        .forEach(
            loadicatorDetails -> {
              LoadicatorStage loadicatorStage = new LoadicatorStage();
              loadicatorStage.setTime(loadicatorDetails.getTime());
              loadicatorService.buildLdTrim(loadicatorDetails.getLDtrim(), loadicatorStage);
              loadicatorService.buildLdIntactStability(
                  loadicatorDetails.getLDIntactStability(), loadicatorStage);
              loadicatorService.buildLdStrength(loadicatorDetails.getLDStrength(), loadicatorStage);
              stages.add(loadicatorStage);
            });
    algoRequest.setStages(stages);

    LoadingPlanLoadicatorDetails loadingPlanLoadicatorDetails = new LoadingPlanLoadicatorDetails();

    List<PortLoadingPlanStowageTempDetails> tempStowageDetails =
        portLoadingPlanStowageDetailsTempRepository
            .findByLoadingInformationAndConditionTypeAndIsActive(
                loadingInformation.getId(), request.getConditionType(), true);
    List<PortLoadingPlanBallastTempDetails> tempBallastDetails =
        portLoadingPlanBallastDetailsTempRepository
            .findByLoadingInformationAndConditionTypeAndIsActive(
                loadingInformation.getId(), request.getConditionType(), true);
    List<PortLoadingPlanRobDetails> robDetails =
        portLoadingPlanRobDetailsRepository
            .findByLoadingInformationAndConditionTypeAndValueTypeAndIsActive(
                loadingInformation.getId(),
                request.getConditionType(),
                LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE,
                true);

    List<LoadicatorStowageDetails> loadicatorStowageDetails =
        new ArrayList<LoadicatorStowageDetails>();
    tempStowageDetails.forEach(
        stowage -> {
          LoadicatorStowageDetails stowageDetails = new LoadicatorStowageDetails();
          BeanUtils.copyProperties(stowage, stowageDetails);
          loadicatorStowageDetails.add(stowageDetails);
        });
    loadingPlanLoadicatorDetails.setStowageDetails(loadicatorStowageDetails);
    List<LoadicatorBallastDetails> loadicatorBallastDetails =
        new ArrayList<LoadicatorBallastDetails>();
    tempBallastDetails.forEach(
        ballast -> {
          LoadicatorBallastDetails ballastDetails = new LoadicatorBallastDetails();
          BeanUtils.copyProperties(ballast, ballastDetails);
          loadicatorBallastDetails.add(ballastDetails);
        });

    loadingPlanLoadicatorDetails.setBallastDetails(loadicatorBallastDetails);
    List<LoadicatorRobDetails> loadicatorRobDetails = new ArrayList<LoadicatorRobDetails>();
    robDetails.forEach(
        rob -> {
          LoadicatorRobDetails robDetail = new LoadicatorRobDetails();
          BeanUtils.copyProperties(rob, robDetail);
          loadicatorRobDetails.add(robDetail);
        });
    loadingPlanLoadicatorDetails.setRobDetails(loadicatorRobDetails);
    algoRequest.setPlanDetails(loadingPlanLoadicatorDetails);
  }
}
