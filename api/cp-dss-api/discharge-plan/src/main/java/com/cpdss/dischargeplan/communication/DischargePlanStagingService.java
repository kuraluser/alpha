/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.communication;

import static com.cpdss.dischargeplan.service.utility.ProcessIdentifiers.port_discharge_plan_commingle_details_temp;

import com.cpdss.common.communication.CommunicationConstants;
import com.cpdss.common.communication.StagingService;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.*;
import com.cpdss.dischargeplan.service.utility.DischargePlanConstants;
import com.cpdss.dischargeplan.service.utility.ProcessIdentifiers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @Author Selvy Thomas */
@Log4j2
@Service
public class DischargePlanStagingService extends StagingService {

  @Autowired private DischargePlanStagingRepository dischargePlanStagingRepository;
  @Autowired private DischargingDelayRepository dischargingDelayRepository;
  @Autowired private DischargingSequenceRepository dischargingSequenceRepository;
  @Autowired private DischargeInformationStatusRepository dischargeInformationStatusRepository;
  @Autowired private DischargeStageDurationRepository dischargeStageDurationRepository;
  @Autowired private DischargeStageMinAmountRepository dischargeStageMinAmountRepository;
  @Autowired private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @Autowired private DischargeInformationRepository dischargeInformationRepository;

  @Autowired
  private DischargingPlanPortWiseDetailsRepository dischargingPlanPortWiseDetailsRepository;

  @Autowired private DischargePlanDataTransferOutBoundRepository dataTransferOutBoundRepository;
  @Autowired private DischargePlanDataTransferInBoundRepository dataTransferInBoundRepository;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  @GrpcClient("loadingPlanService")
  private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanServiceBlockingStub;

  /**
   * Constructor for injecting
   *
   * @param dischargePlanStagingRepository staging repo
   * @param dataTransferOutBoundRepository outbound repo
   * @param dataTransferInBoundRepository inbound repo
   */
  public DischargePlanStagingService(
      @Autowired DischargePlanStagingRepository dischargePlanStagingRepository,
      @Autowired DischargePlanDataTransferOutBoundRepository dataTransferOutBoundRepository,
      @Autowired DischargePlanDataTransferInBoundRepository dataTransferInBoundRepository) {
    super(
        dischargePlanStagingRepository,
        dataTransferOutBoundRepository,
        dataTransferInBoundRepository);
  }

  Long dischargingPatternId = null;
  Long synopticTableXid = null;

  /**
   * getCommunicationData method for get JsonArray from processIdentifierList
   *
   * @param processIdentifierList - list of processIdentifier
   * @param processId - processId
   * @param processGroupId - processGroupId
   * @param dischargeInformationId- -dischargeInformationId value
   * @param pyUserId- processId of algoResponse
   * @return JsonArray
   */
  public JsonArray getCommunicationData(
      List<String> processIdentifierList,
      String processId,
      String processGroupId,
      Long dischargeInformationId,
      String pyUserId)
      throws GenericServiceException {

    log.debug(
        "Converting Tables -> JSON ::: MessageType: {}, Reference referenceId: {}, ProcessId: {}, Tables: {}, pyUserId: {}",
        processGroupId,
        dischargeInformationId,
        processId,
        processIdentifierList,
        pyUserId);

    String dependantProcessId = null;
    String dependantProcessModule = null;

    // Communication referenceId set as patternId between Discharge Plan and Discharge Study
    if (MessageTypes.DISCHARGEPLAN.getMessageType().equals(processGroupId)) {
      // Check prev module communicated
      final DischargeInformation dischargeInformation =
          dischargeInformationRepository
              .findById(dischargeInformationId)
              .orElseThrow(
                  () -> {
                    log.error("DischargeInformation not found. Id: {}", dischargeInformationId);
                    return new GenericServiceException(
                        "DischargeInformation not found. Id: " + dischargeInformationId,
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
                  });

      if (!isCommunicated(
          MessageTypes.DISCHARGESTUDY.getMessageType(),
          dischargeInformation.getDischargingPatternXid(),
          CommunicationConstants.CommunicationModule.LOADABLE_STUDY.getModuleName())) {
        Common.CommunicationTriggerRequest communicationTriggerRequest =
            Common.CommunicationTriggerRequest.newBuilder()
                .setReferenceId(dischargeInformation.getDischargingPatternXid())
                .setMessageType(MessageTypes.DISCHARGESTUDY.getMessageType())
                .build();
        log.info(
            "Calling DischargeStudy triggerCommunication with request :{}",
            communicationTriggerRequest);
        final Common.CommunicationTriggerResponse response =
            loadableStudyServiceBlockingStub.triggerCommunication(communicationTriggerRequest);

        dependantProcessId = response.getProcessId();
        dependantProcessModule =
            CommunicationConstants.CommunicationModule.LOADABLE_STUDY.getModuleName();

        // Communication trigger failure
        if (!DischargePlanConstants.SUCCESS.equals(response.getResponseStatus().getStatus())) {
          log.error(
              "Previous module trigger failed. MessageType: {}, ReferenceId: {}, Module: {}, Response: {}",
              MessageTypes.DISCHARGESTUDY.getMessageType(),
              dischargeInformationId,
              CommunicationConstants.CommunicationModule.LOADABLE_STUDY.getModuleName(),
              response);
          throw new GenericServiceException(
              "Previous module trigger failed. ReferenceId: " + dischargeInformationId,
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
      }
    }
    JsonArray array = new JsonArray();
    List<String> processedList = new ArrayList<>();
    List<Long> dischargingDelaysIds = null;
    List<Long> algoErrorHeadingsIds = null;
    List<Long> dischargingSequenceIds = null;
    List<Long> dischargingPlanPortWiseDetailsIds = null;
    for (String processIdentifier : processIdentifierList) {
      if (processedList.contains(processIdentifier)) {
        log.info("Table already fetched :" + processIdentifier);
        continue;
      }
      List<Object> object = new ArrayList<Object>();
      switch (ProcessIdentifiers.valueOf(processIdentifier)) {
        case discharging_information:
          {
            getDischargingInformation(
                array,
                dischargeInformationId,
                processIdentifier,
                processId,
                processGroupId,
                processedList,
                object,
                dependantProcessId,
                dependantProcessModule);
            break;
          }
        case cow_plan_details:
          {
            String cowPlanDetailJson =
                dischargePlanStagingRepository.getCowPlanDetailWithDischargeId(
                    dischargeInformationId);
            if (cowPlanDetailJson != null) {
              JsonArray cowPlanDetail = JsonParser.parseString(cowPlanDetailJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  cowPlanDetail,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case cow_with_different_cargo:
          {
            String cowWithDifferentCargosJson =
                dischargePlanStagingRepository.getCowWithDifferentCargoWithDischargeId(
                    dischargeInformationId);
            if (cowWithDifferentCargosJson != null) {
              JsonArray cowWithDifferentCargos =
                  JsonParser.parseString(cowWithDifferentCargosJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  cowWithDifferentCargos,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case cow_tank_details:
          {
            String cowTankDetailsJson =
                dischargePlanStagingRepository.getCowTankDetailWithDischargeId(
                    dischargeInformationId);
            if (cowTankDetailsJson != null) {
              JsonArray cowTankDetails =
                  JsonParser.parseString(cowTankDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  cowTankDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case discharging_delay:
          {
            String dischargingDelaysJson =
                dischargePlanStagingRepository.getDischargingDelayWithDischargeId(
                    dischargeInformationId);
            if (dischargingDelaysJson != null) {
              JsonArray dischargingDelays =
                  JsonParser.parseString(dischargingDelaysJson).getAsJsonArray();
              List<DischargingDelay> dischargingDelaysList =
                  dischargingDelayRepository.findByDischargingInformationId(dischargeInformationId);
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  dischargingDelays,
                  dependantProcessId,
                  dependantProcessModule);
              if (dischargingDelaysList != null && !dischargingDelaysList.isEmpty()) {
                dischargingDelaysIds =
                    dischargingDelaysList.stream()
                        .map(DischargingDelay::getId)
                        .collect(Collectors.toList());
              }
            }
            break;
          }
        case discharging_delay_reason:
          {
            if (dischargingDelaysIds != null && !dischargingDelaysIds.isEmpty()) {
              String dischargingDelayReasonsJson =
                  dischargePlanStagingRepository.getDischargingDelayReasonWithDischargingDelayIds(
                      dischargingDelaysIds);
              if (dischargingDelayReasonsJson != null) {
                JsonArray dischargingDelayReasons =
                    JsonParser.parseString(dischargingDelayReasonsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    dischargingDelayReasons,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case discharging_machinary_in_use:
          {
            String dischargingMachineryInUsesJson =
                dischargePlanStagingRepository.getDischargingMachineryInUseWithDischargeId(
                    dischargeInformationId);
            if (dischargingMachineryInUsesJson != null) {
              JsonArray dischargingMachineryInUses =
                  JsonParser.parseString(dischargingMachineryInUsesJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  dischargingMachineryInUses,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case discharging_port_tide_details:
          {
            String portTideDetailsJson =
                dischargePlanStagingRepository.getPortTideDetailWithDischargeId(
                    dischargeInformationId);
            if (portTideDetailsJson != null) {
              JsonArray portTideDetails =
                  JsonParser.parseString(portTideDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portTideDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }

        case discharging_berth_details:
          {
            String dischargingBerthDetailsJson =
                dischargePlanStagingRepository.getDischargingBerthDetailWithDischargeId(
                    dischargeInformationId);
            if (dischargingBerthDetailsJson != null) {
              JsonArray dischargingBerthDetails =
                  JsonParser.parseString(dischargingBerthDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  dischargingBerthDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case discharging_information_algo_status:
          {
            String dischargingInformationAlgoStatusJson =
                dischargePlanStagingRepository.getDischargingInformationAlgoStatusWithDischargeId(
                    dischargeInformationId);
            if (dischargingInformationAlgoStatusJson != null) {
              JsonArray dischargingInformationAlgoStatus =
                  JsonParser.parseString(dischargingInformationAlgoStatusJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  dischargingInformationAlgoStatus,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }

        case algo_error_heading:
          {
            String algoErrorHeadingsJson =
                dischargePlanStagingRepository.getAlgoErrorHeadingWithDischargeId(
                    dischargeInformationId);
            if (algoErrorHeadingsJson != null) {
              JsonArray algoErrorHeadings =
                  JsonParser.parseString(algoErrorHeadingsJson).getAsJsonArray();
              List<AlgoErrorHeading> algoErrorHeadingsList =
                  algoErrorHeadingRepository.findByDischargingInformationId(dischargeInformationId);
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  algoErrorHeadings,
                  dependantProcessId,
                  dependantProcessModule);
              if (algoErrorHeadingsList != null && !algoErrorHeadingsList.isEmpty()) {
                algoErrorHeadingsIds =
                    algoErrorHeadingsList.stream()
                        .map(AlgoErrorHeading::getId)
                        .collect(Collectors.toList());
              }
            }
            break;
          }
        case algo_errors:
          {
            if (algoErrorHeadingsIds != null && !algoErrorHeadingsIds.isEmpty()) {
              String algoErrorsJson =
                  dischargePlanStagingRepository.getAlgoErrorsWithAlgoErrorHeadingIds(
                      algoErrorHeadingsIds);
              if (algoErrorsJson != null) {
                JsonArray algoErrors = JsonParser.parseString(algoErrorsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    algoErrors,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case discharging_sequence:
          {
            String dischargingSequencesJson =
                dischargePlanStagingRepository.getDischargingSequenceWithDischargeId(
                    dischargeInformationId);
            if (dischargingSequencesJson != null) {
              JsonArray dischargingSequences =
                  JsonParser.parseString(dischargingSequencesJson).getAsJsonArray();
              List<DischargingSequence> dischargingSequencesList =
                  dischargingSequenceRepository.findByDischargeInformationId(
                      dischargeInformationId);
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  dischargingSequences,
                  dependantProcessId,
                  dependantProcessModule);
              if (dischargingSequencesList != null && !dischargingSequencesList.isEmpty()) {
                dischargingSequenceIds =
                    dischargingSequencesList.stream()
                        .map(DischargingSequence::getId)
                        .collect(Collectors.toList());
              }
            }
            break;
          }
        case ballast_valves:
          {
            if (dischargingSequenceIds != null && !dischargingSequenceIds.isEmpty()) {
              String ballastValvesJson =
                  dischargePlanStagingRepository.getBallastValveWithDischargingSequenceIds(
                      dischargingSequenceIds);
              if (ballastValvesJson != null) {
                JsonArray ballastValves =
                    JsonParser.parseString(ballastValvesJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    ballastValves,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case cargo_valves:
          {
            if (dischargingSequenceIds != null && !dischargingSequenceIds.isEmpty()) {
              String cargoValvesJson =
                  dischargePlanStagingRepository.getCargoValveWithDischargingSequenceIds(
                      dischargingSequenceIds);
              if (cargoValvesJson != null) {
                JsonArray cargoValves = JsonParser.parseString(cargoValvesJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    cargoValves,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case cargo_discharging_rate:
          {
            if (dischargingSequenceIds != null && !dischargingSequenceIds.isEmpty()) {
              String cargoDischargingRatesJson =
                  dischargePlanStagingRepository.getCargoDischargingRateWithDischargingSequenceIds(
                      dischargingSequenceIds);
              if (cargoDischargingRatesJson != null) {
                JsonArray cargoDischargingRates =
                    JsonParser.parseString(cargoDischargingRatesJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    cargoDischargingRates,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case ballast_operation:
          {
            if (dischargingSequenceIds != null && !dischargingSequenceIds.isEmpty()) {
              String ballastOperationsJson =
                  dischargePlanStagingRepository.getBallastOperationWithDischargingSequenceIds(
                      dischargingSequenceIds);
              if (ballastOperationsJson != null) {
                JsonArray ballastOperations =
                    JsonParser.parseString(ballastOperationsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    ballastOperations,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case discharging_plan_portwise_details:
          {
            if (dischargingSequenceIds != null && !dischargingSequenceIds.isEmpty()) {
              String dischargingPlanPortWiseDetailsJson =
                  dischargePlanStagingRepository
                      .getDischargingPlanPortWiseDetailsWithDischargingSequenceIds(
                          dischargingSequenceIds);
              if (dischargingPlanPortWiseDetailsJson != null) {
                JsonArray dischargingPlanPortWiseDetails =
                    JsonParser.parseString(dischargingPlanPortWiseDetailsJson).getAsJsonArray();
                dischargingPlanPortWiseDetailsIds =
                    dischargingPlanPortWiseDetailsRepository.findByDischargingSequenceIds(
                        dischargingSequenceIds);
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    dischargingPlanPortWiseDetails,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case ballasting_rate:
          {
            if (dischargingSequenceIds != null && !dischargingSequenceIds.isEmpty()) {
              String ballastingRatesJson =
                  dischargePlanStagingRepository.getBallastingRateWithDischargingSequenceIds(
                      dischargingSequenceIds);
              if (ballastingRatesJson != null) {
                JsonArray ballastingRates =
                    JsonParser.parseString(ballastingRatesJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    ballastingRates,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case deballasting_rate:
          {
            if (dischargingSequenceIds != null && !dischargingSequenceIds.isEmpty()) {
              String deballastingRatesJson =
                  dischargePlanStagingRepository.getDeballastingRateWithDischargingSequenceIds(
                      dischargingSequenceIds);
              if (deballastingRatesJson != null) {
                JsonArray deballastingRates =
                    JsonParser.parseString(deballastingRatesJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    deballastingRates,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case discharging_plan_stowage_details:
          {
            if (dischargingPlanPortWiseDetailsIds != null
                && !dischargingPlanPortWiseDetailsIds.isEmpty()) {
              String dischargingPlanStowageDetailsJson =
                  dischargePlanStagingRepository
                      .getDischargingPlanStowageDetaWithDischargingPlanPortWiseDeta(
                          dischargingPlanPortWiseDetailsIds);
              if (dischargingPlanStowageDetailsJson != null) {
                JsonArray dischargingPlanStowageDetails =
                    JsonParser.parseString(dischargingPlanStowageDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    dischargingPlanStowageDetails,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case discharging_plan_ballast_details:
          {
            if (dischargingPlanPortWiseDetailsIds != null
                && !dischargingPlanPortWiseDetailsIds.isEmpty()) {
              String dischargingPlanBallastDetailsJson =
                  dischargePlanStagingRepository
                      .getDischargingPlanBallastDetaWithDischargingPlanPortWiseDeta(
                          dischargingPlanPortWiseDetailsIds);
              if (dischargingPlanBallastDetailsJson != null) {
                JsonArray dischargingPlanBallastDetails =
                    JsonParser.parseString(dischargingPlanBallastDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    dischargingPlanBallastDetails,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case discharging_plan_rob_details:
          {
            if (dischargingPlanPortWiseDetailsIds != null
                && !dischargingPlanPortWiseDetailsIds.isEmpty()) {
              String dischargingPlanRobDetailsJson =
                  dischargePlanStagingRepository
                      .getDischargingPlanRobDetailsWithDischargingPlanPortWiseDeta(
                          dischargingPlanPortWiseDetailsIds);
              if (dischargingPlanRobDetailsJson != null) {
                JsonArray dischargingPlanRobDetails =
                    JsonParser.parseString(dischargingPlanRobDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    dischargingPlanRobDetails,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case discharging_plan_stability_parameters:
          {
            if (dischargingPlanPortWiseDetailsIds != null
                && !dischargingPlanPortWiseDetailsIds.isEmpty()) {
              String dischargingPlanStabilityParametersJson =
                  dischargePlanStagingRepository
                      .getDischargingPlanStabilityParametersWithDischargingPlanPortWiseDeta(
                          dischargingPlanPortWiseDetailsIds);
              if (dischargingPlanStabilityParametersJson != null) {
                JsonArray dischargingPlanStabilityParameters =
                    JsonParser.parseString(dischargingPlanStabilityParametersJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    dischargingPlanStabilityParameters,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case discharging_plan_commingle_details:
          {
            if (dischargingPlanPortWiseDetailsIds != null
                && !dischargingPlanPortWiseDetailsIds.isEmpty()) {
              String dischargingPlanCommingleDetailsJson =
                  dischargePlanStagingRepository
                      .getDischargingPlanCommingleDetailsWithDischargingPlanPortWiseDeta(
                          dischargingPlanPortWiseDetailsIds);
              if (dischargingPlanCommingleDetailsJson != null) {
                JsonArray dischargingPlanCommingleDetails =
                    JsonParser.parseString(dischargingPlanCommingleDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    dischargingPlanCommingleDetails,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case discharging_sequence_stability_parameters:
          {
            String dischargingSequenceStabilityParamJson =
                dischargePlanStagingRepository
                    .getPortDischargingSequenceStabilityParamsWithDischargeId(
                        dischargeInformationId);
            if (dischargingSequenceStabilityParamJson != null) {
              JsonArray dischargingSequenceStabilityParam =
                  JsonParser.parseString(dischargingSequenceStabilityParamJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  dischargingSequenceStabilityParam,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case port_discharging_plan_stowage_ballast_details:
          {
            String portDischargingPlanBallastDetailsJson =
                dischargePlanStagingRepository.getPortDischargingPlanBallastDetailsWithDischargeId(
                    dischargeInformationId);
            if (portDischargingPlanBallastDetailsJson != null) {
              JsonArray portDischargingPlanBallastDetails =
                  JsonParser.parseString(portDischargingPlanBallastDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portDischargingPlanBallastDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case port_discharging_plan_rob_details:
          {
            String portDischargingPlanRobDetailsJson =
                dischargePlanStagingRepository.getPortPortDischargingPlanRobDetailsWithDischargeId(
                    dischargeInformationId);
            if (portDischargingPlanRobDetailsJson != null) {
              JsonArray portDischargingPlanRobDetails =
                  JsonParser.parseString(portDischargingPlanRobDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portDischargingPlanRobDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case port_discharging_plan_stability_parameters:
          {
            String portDischargingPlanStabilityParamJson =
                dischargePlanStagingRepository
                    .getPortDischargingPlanStabilityParametersWithDischargeId(
                        dischargeInformationId);
            if (portDischargingPlanStabilityParamJson != null) {
              JsonArray portDischargingPlanStabilityParam =
                  JsonParser.parseString(portDischargingPlanStabilityParamJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portDischargingPlanStabilityParam,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case port_discharging_plan_stowage_details:
          {
            String portDischargingPlanStowageDetailsJson =
                dischargePlanStagingRepository.getPortDischargingPlanStowageDetailsWithDischargeId(
                    dischargeInformationId);
            if (portDischargingPlanStowageDetailsJson != null) {
              JsonArray portDischargingPlanStowageDetails =
                  JsonParser.parseString(portDischargingPlanStowageDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portDischargingPlanStowageDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case port_discharge_plan_commingle_details:
          {
            String portDischargingPlanCommingleDetailsJson =
                dischargePlanStagingRepository
                    .getPortDischargingPlanCommingleDetailsWithDischargeId(dischargeInformationId);
            if (portDischargingPlanCommingleDetailsJson != null) {
              JsonArray portDischargingPlanCommingleDetails =
                  JsonParser.parseString(portDischargingPlanCommingleDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portDischargingPlanCommingleDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case port_discharge_plan_commingle_details_temp:
          {
            String portDischargingPlanCommingleDetailsTempJson =
                dischargePlanStagingRepository
                    .getPortDischargingPlanCommingleDetailsTempWithDischargeId(
                        dischargeInformationId);
            if (portDischargingPlanCommingleDetailsTempJson != null) {
              JsonArray portDischargingPlanCommingleDetailsTemp =
                  JsonParser.parseString(portDischargingPlanCommingleDetailsTempJson)
                      .getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portDischargingPlanCommingleDetailsTemp,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case json_data:
          {
            LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
            builder.setId(dischargeInformationId);
            LoadableStudy.LoadableStudyCommunicationReply reply =
                this.loadableStudyServiceBlockingStub.getJsonDataForDischargePlanCommunication(
                    builder.build());
            if (DischargePlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
              if (!reply.getDataJson().isEmpty()) {
                JsonArray jsonData = JsonParser.parseString(reply.getDataJson()).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    jsonData,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case loadable_pattern:
          {
            if (dischargingPatternId != null) {
              LoadableStudy.LoadableStudyPatternCommunicationRequest.Builder builder =
                  LoadableStudy.LoadableStudyPatternCommunicationRequest.newBuilder();
              builder.setId(dischargingPatternId);
              LoadableStudy.LoadableStudyPatternCommunicationReply reply =
                  this.loadableStudyServiceBlockingStub.getLoadablePatternForCommunication(
                      builder.build());
              if (DischargePlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                if (reply.getDataJson() != null) {
                  JsonArray loadablePattern =
                      JsonParser.parseString(reply.getDataJson()).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      processIdentifier,
                      processId,
                      processGroupId,
                      processedList,
                      loadablePattern,
                      dependantProcessId,
                      dependantProcessModule);
                }
              }
            }
            break;
          }
        case bill_of_ladding:
          {
            String billOfLandingJson =
                dischargePlanStagingRepository.getBillOfLandingWithDischargeInfoId(
                    dischargeInformationId);
            if (billOfLandingJson != null) {
              JsonArray billOfLanding = JsonParser.parseString(billOfLandingJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  billOfLanding,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case synoptical_table:
          {
            if (synopticTableXid != null) {
              LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                  LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
              builder.setId(synopticTableXid);
              LoadableStudy.LoadableStudyCommunicationReply reply =
                  this.loadableStudyServiceBlockingStub.getSynopticalDataForCommunication(
                      builder.build());
              if (DischargePlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                if (reply.getDataJson() != null) {
                  JsonArray synopticalTableData =
                      JsonParser.parseString(reply.getDataJson()).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      processIdentifier,
                      processId,
                      processGroupId,
                      processedList,
                      synopticalTableData,
                      dependantProcessId,
                      dependantProcessModule);
                }
              }
            }
            break;
          }
        case pyuser:
          {
            if (pyUserId != null) {
              LoadingPlanModels.LoadingPlanCommunicationRequest.Builder builder =
                  LoadingPlanModels.LoadingPlanCommunicationRequest.newBuilder();
              builder.setId(pyUserId);
              LoadingPlanModels.LoadingPlanCommunicationReply reply =
                  this.loadingPlanServiceBlockingStub.getPyUserForCommunication(builder.build());
              if (DischargePlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                if (reply.getDataJson() != null) {
                  JsonArray pyUserData =
                      JsonParser.parseString(reply.getDataJson()).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      processIdentifier,
                      processId,
                      processGroupId,
                      processedList,
                      pyUserData,
                      dependantProcessId,
                      dependantProcessModule);
                }
              }
            }
            break;
          }
      }
    }
    // Save to outbound table
    saveDataTransferOutBound(processGroupId, dischargeInformationId);
    return array;
  }

  private void getDischargingInformation(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      List<Object> object,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    String dischargeInformationJson =
        dischargePlanStagingRepository.getDischargeInformationJson(id);
    if (dischargeInformationJson != null) {
      JsonObject dischargeInformation =
          JsonParser.parseString(dischargeInformationJson)
              .getAsJsonArray()
              .get(0)
              .getAsJsonObject();
      Long dischargingInformationStatusId =
          !dischargeInformation.get("discharging_status_xid").isJsonNull()
              ? dischargeInformation.get("discharging_status_xid").getAsLong()
              : null;
      Long dischargingStagesMinAmtId =
          dischargeInformation.get("stages_min_amount_xid").getAsLong();
      Long dischargingStagesDurationId =
          dischargeInformation.get("stages_duration_xid").getAsLong();
      dischargingPatternId = dischargeInformation.get("discharging_pattern_xid").getAsLong();
      synopticTableXid = dischargeInformation.get("synoptical_table_xid").getAsLong();
      if (dischargingInformationStatusId != null) {
        getDischargingInformationStatus(
            array,
            dischargingInformationStatusId,
            "discharging_information_status",
            processId,
            processGroupId,
            processedList,
            dependantProcessId,
            dependantProcessModule);
      }
      if (dischargingStagesMinAmtId != null) {
        getDischargingStagesMinAmount(
            array,
            dischargingStagesMinAmtId,
            "discharging_stages_min_amount",
            processId,
            processGroupId,
            processedList,
            dependantProcessId,
            dependantProcessModule);
      }
      if (dischargingStagesDurationId != null) {
        getDischargingStagesDuration(
            array,
            dischargingStagesDurationId,
            "discharging_stages_duration",
            processId,
            processGroupId,
            processedList,
            dependantProcessId,
            dependantProcessModule);
      }
      object.addAll(Arrays.asList(dischargeInformation));
      addIntoProcessedList(
          array,
          object,
          processIdentifier,
          processId,
          processGroupId,
          processedList,
          null,
          dependantProcessId,
          dependantProcessModule);
    }
  }

  private void getDischargingInformationStatus(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    Optional<DischargingInformationStatus> dischargingInformationStatusObj =
        dischargeInformationStatusRepository.findById(id);
    if (!dischargingInformationStatusObj.isEmpty()) {
      dischargingInformationStatusObj.get().setDischargingInformations(null);
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(dischargingInformationStatusObj.get()));
      addIntoProcessedList(
          array,
          object,
          processIdentifier,
          processId,
          processGroupId,
          processedList,
          null,
          dependantProcessId,
          dependantProcessModule);
    }
  }

  private void getDischargingStagesMinAmount(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    Optional<DischargingStagesMinAmount> dischargingStagesMinAmountObj =
        dischargeStageMinAmountRepository.findById(id);
    if (!dischargingStagesMinAmountObj.isEmpty()) {
      dischargingStagesMinAmountObj.get().setDischargeInformation(null);
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(dischargingStagesMinAmountObj.get()));
      addIntoProcessedList(
          array,
          object,
          processIdentifier,
          processId,
          processGroupId,
          processedList,
          null,
          dependantProcessId,
          dependantProcessModule);
    }
  }

  private void getDischargingStagesDuration(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    Optional<DischargingStagesDuration> dischargingStagesDurationObj =
        dischargeStageDurationRepository.findById(id);
    if (!dischargingStagesDurationObj.isEmpty()) {
      dischargingStagesDurationObj.get().setDischargingInformation(null);
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(dischargingStagesDurationObj.get()));
      addIntoProcessedList(
          array,
          object,
          processIdentifier,
          processId,
          processGroupId,
          processedList,
          null,
          dependantProcessId,
          dependantProcessModule);
    }
  }
}
