/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.loadicator;

import static java.lang.String.valueOf;

import com.cpdss.common.constants.AlgoErrorHeaderConstants;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
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
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.domain.CommunicationStatus;
import com.cpdss.loadingplan.domain.algo.LoadicatorAlgoResponse;
import com.cpdss.loadingplan.domain.algo.LoadicatorBallastDetails;
import com.cpdss.loadingplan.domain.algo.LoadicatorCommingleDetails;
import com.cpdss.loadingplan.domain.algo.LoadicatorResult;
import com.cpdss.loadingplan.domain.algo.LoadicatorRobDetails;
import com.cpdss.loadingplan.domain.algo.LoadicatorStage;
import com.cpdss.loadingplan.domain.algo.LoadicatorStowageDetails;
import com.cpdss.loadingplan.domain.algo.LoadingPlanLoadicatorDetails;
import com.cpdss.loadingplan.domain.algo.UllageEditLoadicatorAlgoRequest;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingInformationStatus;
import com.cpdss.loadingplan.entity.PortLoadingPlanBallastTempDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanCommingleTempDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanRobDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanStabilityParameters;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageTempDetails;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.repository.AlgoErrorHeadingRepository;
import com.cpdss.loadingplan.repository.AlgoErrorsRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanBallastDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanBallastTempDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanCommingleTempDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanRobDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStabilityParametersRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageTempDetailsRepository;
import com.cpdss.loadingplan.service.LoadingPlanCommunicationService;
import com.cpdss.loadingplan.service.LoadingPlanService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
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

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;

  @Value("${cpdss.build.env}")
  private String env;

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

  @Autowired AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @Autowired AlgoErrorsRepository algoErrorsRepository;

  @Autowired
  PortLoadingPlanCommingleTempDetailsRepository portLoadingPlanCommingleDetailsTempRepository;

  @Autowired LoadingPlanAlgoService loadingPlanAlgoService;
  @Autowired LoadicatorService loadicatorService;
  @Autowired LoadingPlanService loadingPlanService;
  @Autowired LoadingPlanStagingService loadingPlanStagingService;
  @Autowired LoadingPlanCommunicationStatusRepository loadingPlanCommunicationStatusRepository;
  @Autowired private LoadingPlanCommunicationService communicationService;
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
    log.info("Inside saveLoadicatorInfoForUllageUpdate method");
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
    log.info("loadingInfoOpt object:{}", loadingInfoOpt.get());
    String processId = UUID.randomUUID().toString();
    if (enableCommunication && env.equals("ship")) {
      JsonArray jsonArray =
          loadingPlanStagingService.getCommunicationData(
              com.cpdss.loadingplan.utility.LoadingPlanConstants.ULLAGE_UPDATE_SHIP_TO_SHORE,
              processId,
              MessageTypes.ULLAGE_UPDATE.getMessageType(),
              loadingInfoOpt.get().getId(),
              null);

      log.info("Json Array in update ullage service: " + jsonArray.toString());
      EnvoyWriter.WriterReply ewReply =
          communicationService.passRequestPayloadToEnvoyWriter(
              jsonArray.toString(),
              loadingInfoOpt.get().getVesselXId(),
              MessageTypes.ULLAGE_UPDATE.getMessageType());

      if (LoadingPlanConstants.SUCCESS.equals(ewReply.getResponseStatus().getStatus())) {
        log.info("------- Envoy writer has called successfully : " + ewReply.toString());
        LoadingPlanCommunicationStatus loadingPlanCommunicationStatus =
            new LoadingPlanCommunicationStatus();
        if (ewReply.getMessageId() != null) {
          loadingPlanCommunicationStatus.setMessageUUID(ewReply.getMessageId());
          loadingPlanCommunicationStatus.setCommunicationStatus(
              CommunicationStatus.UPLOAD_WITH_HASH_VERIFIED.getId());
        }
        loadingPlanCommunicationStatus.setReferenceId(loadingInfoOpt.get().getId());
        loadingPlanCommunicationStatus.setMessageType(MessageTypes.ULLAGE_UPDATE.getMessageType());
        loadingPlanCommunicationStatus.setCommunicationDateTime(LocalDateTime.now());
        LoadingPlanCommunicationStatus loadableStudyCommunicationStatus =
            this.loadingPlanCommunicationStatusRepository.save(loadingPlanCommunicationStatus);
        log.info(
            "Communication table update for ullage update: "
                + loadingPlanCommunicationStatus.getId());
        Optional<LoadingInformationStatus> loadingInfoStatusOpt =
            loadingPlanAlgoService.getLoadingInformationStatus(
                LoadingPlanConstants.UPDATE_ULLAGE_COMMUNICATED_TO_SHORE);
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
    } else {
      Optional<LoadingInformationStatus> loadingInfoStatusOpt =
          loadingPlanAlgoService.getLoadingInformationStatus(
              LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_STARTED_ID);
      log.info("LoadingInformationStatus obj:{}", loadingInfoStatusOpt.get());
      loadingPlanService.updateLoadingPlanStatus(
          loadingInfoOpt.get(),
          loadingInfoStatusOpt.get(),
          request.getUpdateUllage(0).getArrivalDepartutre());
      loadingPlanAlgoService.createLoadingInformationAlgoStatus(
          loadingInfoOpt.get(),
          processId,
          loadingInfoStatusOpt.get(),
          request.getUpdateUllage(0).getArrivalDepartutre());
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
        log.info("Algo request for ullage update:{}", algoRequest);
        saveUllageEditLoadicatorRequestJson(algoRequest, loadingInfoOpt.get().getId());
        checkStabilityWithAlgo(
            loadingInfoOpt.get(),
            algoRequest,
            processId,
            request.getUpdateUllage(0).getArrivalDepartutre());
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
      BigDecimal sg = null;
      List<BigDecimal> specificGravities =
          tempBallastDetails.stream()
              .filter(ballast -> (ballast.getSg() != null) && (ballast.getSg() != BigDecimal.ZERO))
              .map(ballast -> ballast.getSg())
              .collect(Collectors.toList());

      if (!specificGravities.isEmpty()) {
        sg = specificGravities.get(0);
      }
      List<PortLoadingPlanRobDetails> robDetails =
          portLoadingPlanRobDetailsRepository
              .findByLoadingInformationAndConditionTypeAndValueTypeAndIsActive(
                  loadingInfoOpt.get().getId(),
                  request.getUpdateUllage(0).getArrivalDepartutre(),
                  LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE,
                  true);
      List<PortLoadingPlanCommingleTempDetails> tempCommingleDetails =
          portLoadingPlanCommingleDetailsTempRepository
              .findByLoadingInformationAndConditionTypeAndIsActive(
                  loadingInfoOpt.get().getId(),
                  request.getUpdateUllage(0).getArrivalDepartutre(),
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
      PortInfo.PortReply portReply =
          loadicatorService.getPortInfoForLoadicator(loadingInfoOpt.get());

      loadicatorRequestBuilder.setTypeId(
          LoadingPlanConstants.LOADING_INFORMATION_LOADICATOR_TYPE_ID);
      loadicatorRequestBuilder.setIsUllageUpdate(true);
      loadicatorRequestBuilder.setConditionType(request.getUpdateUllage(0).getArrivalDepartutre());
      StowagePlan.Builder stowagePlanBuilder = StowagePlan.newBuilder();
      loadicatorService.buildStowagePlan(
          loadingInfoOpt.get(),
          0,
          processId,
          cargoReply,
          vesselReply,
          portReply,
          stowagePlanBuilder,
          sg);
      buildStowagePlanDetails(
          loadingInfoOpt.get(),
          tempStowageDetails,
          tempCommingleDetails,
          cargoNomDetails,
          vesselReply,
          cargoReply,
          stowagePlanBuilder);
      buildCargoDetails(
          loadingInfoOpt.get(),
          cargoNomDetails,
          tempStowageDetails,
          tempCommingleDetails,
          cargoReply,
          stowagePlanBuilder);
      buildBallastDetails(
          loadingInfoOpt.get(), tempBallastDetails, vesselReply, stowagePlanBuilder);
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

      Optional<LoadingInformationStatus> loadicatorVerificationStatusOpt =
          loadingPlanAlgoService.getLoadingInformationStatus(
              LoadingPlanConstants.UPDATE_ULLAGE_LOADICATOR_VERIFICATION_STARTED_ID);

      loadingPlanService.updateLoadingPlanStatus(
          loadingInfoOpt.get(),
          loadingInfoStatusOpt.get(),
          request.getUpdateUllage(0).getArrivalDepartutre());

      loadingPlanAlgoService.updateLoadingInfoAlgoStatus(
          loadingInfoOpt.get(), processId, loadicatorVerificationStatusOpt.get());
      return processId;
    }
    return null;
  }

  /**
   * @param commingleDetails
   * @param loadingInformation
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  @SuppressWarnings("unchecked")
  private void buildLoadicatorCargoDetailsForCommingleCargo(
      List<PortLoadingPlanCommingleTempDetails> commingleDetails,
      LoadingInformation loadingInformation,
      CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    commingleDetails.stream()
        .filter(
            distinctByKeys(
                PortLoadingPlanCommingleTempDetails::getCargoNomination1XId,
                PortLoadingPlanCommingleTempDetails::getCargoNomination2XId))
        .forEach(
            commingle -> {
              Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
              cargoBuilder.setCargoAbbrev(commingle.getGrade());
              Optional.ofNullable(String.valueOf(commingle.getApi()))
                  .ifPresent(cargoBuilder::setApi);
              Optional.ofNullable(String.valueOf(commingle.getTemperature()))
                  .ifPresent(cargoBuilder::setStandardTemp);
              Optional.ofNullable(loadingInformation.getPortXId())
                  .ifPresent(cargoBuilder::setPortId);
              Optional.ofNullable(stowagePlanBuilder.getStowageId())
                  .ifPresent(cargoBuilder::setStowageId);
              stowagePlanBuilder.addCargoInfo(cargoBuilder.build());
            });
  }

  @SuppressWarnings("unchecked")
  private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
    final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

    return t -> {
      final List<?> keys =
          Arrays.stream(keyExtractors).map(ke -> ke.apply(t)).collect(Collectors.toList());

      return seen.putIfAbsent(keys, Boolean.TRUE) == null;
    };
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
    if (algoRequest.getStages().isEmpty()) {
      algoRequest.setStages(null);
    }
    try {
      LoadicatorAlgoResponse algoResponse =
          restTemplate.postForObject(loadicatorUrl, algoRequest, LoadicatorAlgoResponse.class);
      log.info("Algo response for ullage update:{}", algoResponse);
      saveLoadicatorResponseJson(algoResponse, loadingInformation.getId());

      algoResponse.getLoadicatorResults().get(0).getJudgement().removeIf(error -> error.isEmpty());
      if (algoResponse.getLoadicatorResults().get(0).getJudgement().size() > 0) {
        updateLoadingPlanStatuses(
            loadingInformation,
            LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_FAILED_ID,
            processId,
            conditionType);
        saveLoadingPlanAlgoErrors(
            algoResponse.getLoadicatorResults().get(0).getJudgement(),
            loadingInformation,
            conditionType);
        // algoerror communication
        try {
          log.info("Communication side started for ullage update loadicator on With Algo Errors");
          ullageUpdateSaveForCommunication(
              com.cpdss.loadingplan.utility.LoadingPlanConstants.ULLAGE_UPDATE_ALGO_ERRORS,
              loadingInformation.getId(),
              MessageTypes.ULLAGE_UPDATE_LOADICATOR_ON_LGORESULT.getMessageType(),
              null,
              loadingInformation.getVesselXId());
        } catch (Exception ex) {
          log.error("Error occured when communicate algo errors", ex.getMessage());
        }
      } else {
        saveLoadingPlanStabilityParameters(
            loadingInformation,
            algoResponse,
            conditionType,
            LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE);
        updateLoadingPlanStatuses(
            loadingInformation,
            LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID,
            processId,
            conditionType);
        loadingPlanService.saveUpdatedLoadingPlanDetails(loadingInformation, conditionType);
        log.info("Ullage update with loadicator off after algo call for communication tables");
        ullageUpdateSaveForCommunication(
            com.cpdss.loadingplan.utility.LoadingPlanConstants
                .ULLAGE_UPDATE_SHORE_TO_SHIP_LOADICATOR_OFF,
            loadingInformation.getId(),
            MessageTypes.ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT.getMessageType(),
            algoResponse.getProcessId(),
            loadingInformation.getVesselXId());
      }
    } catch (HttpStatusCodeException e) {
      log.error("Error occured in ALGO side while calling loadicator_results API");
      updateLoadingPlanStatuses(
          loadingInformation,
          LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_FAILED_ID,
          processId,
          conditionType);
      loadingPlanAlgoService.saveAlgoInternalError(
          loadingInformation, conditionType, Lists.newArrayList(e.getResponseBodyAsString()));
      try {
        log.info("Communication side started for ullage update loadicator off With Algo Errors");
        ullageUpdateSaveForCommunication(
            com.cpdss.loadingplan.utility.LoadingPlanConstants.ULLAGE_UPDATE_ALGO_ERRORS,
            loadingInformation.getId(),
            MessageTypes.ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT.getMessageType(),
            null,
            loadingInformation.getVesselXId());
      } catch (Exception ex) {
        log.error("Error occured when communicate algo errors", ex.getMessage());
      }
    }
  }

  private void ullageUpdateSaveForCommunication(
      List<String> processIdentifiers,
      Long loadingInfoId,
      String messageType,
      String pyUserId,
      Long vesselId)
      throws GenericServiceException {
    if (enableCommunication && !env.equals("ship")) {
      JsonArray jsonArray =
          loadingPlanStagingService.getCommunicationData(
              processIdentifiers,
              UUID.randomUUID().toString(),
              messageType,
              loadingInfoId,
              pyUserId);
      log.info("Json Array get in After Algo call: " + jsonArray.toString());
      EnvoyWriter.WriterReply ewReply =
          communicationService.passRequestPayloadToEnvoyWriter(
              jsonArray.toString(), vesselId, messageType);
      log.info("------- Envoy writer has called successfully in shore: " + ewReply.toString());
      LoadingPlanCommunicationStatus loadingPlanCommunicationStatus =
          new LoadingPlanCommunicationStatus();
      if (ewReply.getMessageId() != null) {
        loadingPlanCommunicationStatus.setMessageUUID(ewReply.getMessageId());
        loadingPlanCommunicationStatus.setCommunicationStatus(
            CommunicationStatus.RECEIVED_WITH_HASH_VERIFIED.getId());
      }
      loadingPlanCommunicationStatus.setReferenceId(loadingInfoId);
      loadingPlanCommunicationStatus.setMessageType(messageType);
      loadingPlanCommunicationStatus.setCommunicationDateTime(LocalDateTime.now());
      LoadingPlanCommunicationStatus loadableStudyCommunicationStatus =
          this.loadingPlanCommunicationStatusRepository.save(loadingPlanCommunicationStatus);
      log.info(
          "Communication table update for ullageupdate with loadicator on: "
              + loadingPlanCommunicationStatus.getId());
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
   * @param tempCommingleDetails
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  private void buildCargoDetails(
      LoadingInformation loadingInformation,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      List<PortLoadingPlanStowageTempDetails> tempStowageDetails,
      List<PortLoadingPlanCommingleTempDetails> tempCommingleDetails,
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
    buildLoadicatorCargoDetailsForCommingleCargo(
        tempCommingleDetails, loadingInformation, cargoReply, stowagePlanBuilder);
  }

  /**
   * Builds stowage plan details
   *
   * @param loadingInformation
   * @param tempStowageDetails
   * @param tempCommingleDetails
   * @param cargoNomDetails
   * @param vesselReply
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  private void buildStowagePlanDetails(
      LoadingInformation loadingInformation,
      List<PortLoadingPlanStowageTempDetails> tempStowageDetails,
      List<PortLoadingPlanCommingleTempDetails> tempCommingleDetails,
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
    buildLoadicatorStowagePlanDetailsForCommingleCargo(
        tempCommingleDetails, loadingInformation, cargoReply, vesselReply, stowagePlanBuilder);
  }

  /**
   * @param commingleDetails
   * @param loadingInformation
   * @param cargoReply
   * @param vesselReply
   * @param stowagePlanBuilder
   */
  private void buildLoadicatorStowagePlanDetailsForCommingleCargo(
      List<PortLoadingPlanCommingleTempDetails> commingleDetails,
      LoadingInformation loadingInformation,
      CargoReply cargoReply,
      VesselReply vesselReply,
      Builder stowagePlanBuilder) {
    commingleDetails.forEach(
        commingle -> {
          Loadicator.StowageDetails.Builder stowageDetailsBuilder =
              Loadicator.StowageDetails.newBuilder();
          Optional.ofNullable(commingle.getTankId()).ifPresent(stowageDetailsBuilder::setTankId);

          Optional.ofNullable(commingle.getQuantity())
              .ifPresent(quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));

          Optional.ofNullable(loadingInformation.getPortXId())
              .ifPresent(stowageDetailsBuilder::setPortId);
          Optional.ofNullable(stowageDetailsBuilder.getStowageId())
              .ifPresent(stowageDetailsBuilder::setStowageId);
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(commingle.getTankId()))
                  .findAny();
          if (tankDetail.isPresent()) {
            Optional.ofNullable(tankDetail.get().getTankName())
                .ifPresent(stowageDetailsBuilder::setTankName);
            Optional.ofNullable(tankDetail.get().getShortName())
                .ifPresent(stowageDetailsBuilder::setShortName);
          }

          stowageDetailsBuilder.setCargoName(commingle.getGrade());
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
    log.info("Algo reuest for ullage update:{}", algoRequest);
    saveUllageEditLoadicatorRequestJson(algoRequest, loadingInfoOpt.get().getId());
    try {
      LoadicatorAlgoResponse algoResponse =
          restTemplate.postForObject(loadicatorUrl, algoRequest, LoadicatorAlgoResponse.class);
      log.info("Algo response for ullage update:{}", algoResponse);
      saveLoadicatorResponseJson(algoResponse, loadingInfoOpt.get().getId());

      algoResponse
          .getLoadicatorResults()
          .get(0)
          .getErrorDetails()
          .removeIf(error -> error.isEmpty());

      if (algoResponse.getLoadicatorResults().get(0).getErrorDetails().size() > 0
          || algoResponse.getLoadicatorResults().get(0).getJudgement().size() > 0) {
        updateLoadingPlanStatuses(
            loadingInfoOpt.get(),
            LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_FAILED_ID,
            request.getProcessId(),
            request.getConditionType());
        saveLoadingPlanLoadicatorErrors(
            algoResponse.getLoadicatorResults().get(0).getErrorDetails(),
            algoResponse.getLoadicatorResults().get(0).getJudgement(),
            loadingInfoOpt.get(),
            request.getConditionType());
        // algo error communication
        try {
          log.info("Communication side started for ullage update loadicator on With Algo Errors");
          ullageUpdateSaveForCommunication(
              com.cpdss.loadingplan.utility.LoadingPlanConstants.ULLAGE_UPDATE_ALGO_ERRORS,
              loadingInfoOpt.get().getId(),
              MessageTypes.ULLAGE_UPDATE_LOADICATOR_ON_LGORESULT.getMessageType(),
              null,
              loadingInfoOpt.get().getVesselXId());
        } catch (Exception ex) {
          log.error("Error occured when communicate algo errors", ex.getMessage());
        }
      } else {
        saveLoadingPlanStabilityParameters(
            loadingInfoOpt.get(),
            algoResponse,
            request.getConditionType(),
            LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE);
        loadingPlanService.saveUpdatedLoadingPlanDetails(
            loadingInfoOpt.get(), request.getConditionType());
        updateLoadingPlanStatuses(
            loadingInfoOpt.get(),
            LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID,
            request.getProcessId(),
            request.getConditionType());
        log.info("Ullage update with loadicator on after algo call for communication tables");
        ullageUpdateSaveForCommunication(
            com.cpdss.loadingplan.utility.LoadingPlanConstants
                .ULLAGE_UPDATE_SHORE_TO_SHIP_LOADICATOR_ON,
            loadingInfoOpt.get().getId(),
            MessageTypes.ULLAGE_UPDATE_LOADICATOR_ON_LGORESULT.getMessageType(),
            algoResponse.getProcessId(),
            loadingInfoOpt.get().getVesselXId());
      }
    } catch (HttpStatusCodeException e) {
      log.error("Error occured in ALGO side while calling loadicator_results API");
      updateLoadingPlanStatuses(
          loadingInfoOpt.get(),
          LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_FAILED_ID,
          request.getProcessId(),
          request.getConditionType());
      loadingPlanAlgoService.saveAlgoInternalError(
          loadingInfoOpt.get(),
          request.getConditionType(),
          Lists.newArrayList(e.getResponseBodyAsString()));
      try {
        log.info(
            "Communication side started for ullage update loadicator on With Algo Internal Errors");
        ullageUpdateSaveForCommunication(
            com.cpdss.loadingplan.utility.LoadingPlanConstants.ULLAGE_UPDATE_ALGO_ERRORS,
            loadingInfoOpt.get().getId(),
            MessageTypes.ULLAGE_UPDATE_LOADICATOR_ON_LGORESULT.getMessageType(),
            null,
            loadingInfoOpt.get().getVesselXId());
      } catch (Exception ex) {
        log.error("Error occured when communicate algo errors", ex.getMessage());
      }
    }
  }

  private void updateLoadingPlanStatuses(
      LoadingInformation loadingInformation, Long statusId, String processId, Integer conditionType)
      throws GenericServiceException, NumberFormatException, IllegalAccessException,
          InvocationTargetException {
    Optional<LoadingInformationStatus> loadingInfoStatusOpt =
        loadingPlanAlgoService.getLoadingInformationStatus(statusId);
    loadingPlanService.updateLoadingPlanStatus(
        loadingInformation, loadingInfoStatusOpt.get(), conditionType);
    loadingPlanAlgoService.updateLoadingInfoAlgoStatus(
        loadingInformation, processId, loadingInfoStatusOpt.get());
  }

  /**
   * @param errorDetails
   * @param judgement
   * @param loadingInformation
   * @param conditionType
   */
  private void saveLoadingPlanLoadicatorErrors(
      List<String> errorDetails,
      List<String> judgement,
      LoadingInformation loadingInformation,
      int conditionType) {
    algoErrorHeadingRepository.deleteByLoadingInformationAndConditionType(
        loadingInformation, conditionType);
    algoErrorsRepository.deleteByLoadingInformationAndConditionType(
        loadingInformation, conditionType);
    if (!errorDetails.isEmpty()) {
      loadingPlanAlgoService.saveAlgoErrorEntity(
          loadingInformation,
          AlgoErrorHeaderConstants.LOADICATOR_ERRORS,
          conditionType,
          errorDetails);
    }
    if (!judgement.isEmpty()) {
      loadingPlanAlgoService.saveAlgoErrorEntity(
          loadingInformation,
          AlgoErrorHeaderConstants.ALGO_STABILITY_ERRORS,
          conditionType,
          judgement);
    }
  }

  private void saveLoadingPlanAlgoErrors(
      List<String> judgement, LoadingInformation loadingInformation, int conditionType) {

    loadingPlanAlgoService.createAlgoErrors(
        loadingInformation,
        AlgoErrorHeaderConstants.ALGO_STABILITY_ERRORS,
        conditionType,
        judgement);
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
    portLoadingPlanStabilityParametersRepository
        .deleteByLoadingInformationIdAndConditionTypeAndValueType(
            loadingInformation.getId(), conditionType, valueType);
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
        loadingInformation,
        result,
        stabilityParameters,
        conditionType,
        valueType,
        Optional.empty());
    portLoadingPlanStabilityParametersRepository.save(stabilityParameters);
  }

  /**
   * @param algoResponse
   * @param loadingInfoId
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
   * @param loadingInfoId
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
    algoRequest.setPortRotationId(loadingInformation.getPortRotationXId());
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
    List<PortLoadingPlanCommingleTempDetails> tempCommingleDetails =
        portLoadingPlanCommingleDetailsTempRepository
            .findByLoadingInformationAndConditionTypeAndIsActive(
                loadingInformation.getId(), request.getConditionType(), true);

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

    List<LoadicatorCommingleDetails> loadicatorCommingleDetails =
        new ArrayList<LoadicatorCommingleDetails>();
    tempCommingleDetails.forEach(
        commingle -> {
          LoadicatorCommingleDetails commingleDetail = new LoadicatorCommingleDetails();
          buildCommingleDetails(commingle, commingleDetail);
          loadicatorCommingleDetails.add(commingleDetail);
        });
    loadingPlanLoadicatorDetails.setCommingleDetails(loadicatorCommingleDetails);

    algoRequest.setPlanDetails(loadingPlanLoadicatorDetails);
  }

  /**
   * @param commingle
   * @param commingleDetail
   */
  private void buildCommingleDetails(
      PortLoadingPlanCommingleTempDetails commingle, LoadicatorCommingleDetails commingleDetail) {
    Optional.ofNullable(commingle.getGrade()).ifPresent(commingleDetail::setAbbreviation);
    commingleDetail.setApi(
        StringUtils.isEmpty(commingle.getApi()) ? null : new BigDecimal(commingle.getApi()));
    Optional.ofNullable(commingle.getCargo1XId()).ifPresent(commingleDetail::setCargo1Id);
    Optional.ofNullable(commingle.getCargo2XId()).ifPresent(commingleDetail::setCargo2Id);
    Optional.ofNullable(commingle.getCargoNomination1XId())
        .ifPresent(commingleDetail::setCargoNomination1Id);
    Optional.ofNullable(commingle.getCargoNomination2XId())
        .ifPresent(commingleDetail::setCargoNomination2Id);
    Optional.ofNullable(commingle.getId()).ifPresent(commingleDetail::setId);
    commingleDetail.setQuantityMT(
        StringUtils.isEmpty(commingle.getQuantity())
            ? null
            : new BigDecimal(commingle.getQuantity()));
    commingleDetail.setQuantityM3(
        StringUtils.isEmpty(commingle.getQuantityM3())
            ? null
            : new BigDecimal(commingle.getQuantityM3()));
    Optional.ofNullable(commingle.getTankId()).ifPresent(commingleDetail::setTankId);
    commingleDetail.setTemperature(
        StringUtils.isEmpty(commingle.getTemperature())
            ? null
            : new BigDecimal(commingle.getTemperature()));
    commingleDetail.setUllage(
        StringUtils.isEmpty(commingle.getUllage()) ? null : new BigDecimal(commingle.getUllage()));
    commingleDetail.setQuantity1MT(
        StringUtils.isEmpty(commingle.getQuantity1MT())
            ? null
            : new BigDecimal(commingle.getQuantity1MT()));
    commingleDetail.setQuantity2MT(
        StringUtils.isEmpty(commingle.getQuantity2MT())
            ? null
            : new BigDecimal(commingle.getQuantity2MT()));
    commingleDetail.setQuantity1M3(
        StringUtils.isEmpty(commingle.getQuantity1M3())
            ? null
            : new BigDecimal(commingle.getQuantity1M3()));
    commingleDetail.setQuantity2M3(
        StringUtils.isEmpty(commingle.getQuantity2M3())
            ? null
            : new BigDecimal(commingle.getQuantity2M3()));
    commingleDetail.setUllage1(
        StringUtils.isEmpty(commingle.getUllage1())
            ? null
            : new BigDecimal(commingle.getUllage1()));
    commingleDetail.setUllage2(
        StringUtils.isEmpty(commingle.getUllage2())
            ? null
            : new BigDecimal(commingle.getUllage2()));
    Optional.ofNullable(commingle.getConditionType()).ifPresent(commingleDetail::setConditionType);
    Optional.ofNullable(commingle.getValueType()).ifPresent(commingleDetail::setValueType);
  }
}
