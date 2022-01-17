/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.communication;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;

import com.cpdss.common.communication.CommunicationConstants.CommunicationModule;
import com.cpdss.common.communication.StagingService;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadingplan.domain.VoyageActivate;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.repository.communication.LoadingPlanDataTransferInBoundRepository;
import com.cpdss.loadingplan.repository.communication.LoadingPlanDataTransferOutBoundRepository;
import com.cpdss.loadingplan.utility.LoadingPlanConstants;
import com.cpdss.loadingplan.utility.ProcessIdentifiers;
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
import org.springframework.util.CollectionUtils;

/** @author Selvy Thomas */
@Log4j2
@Service
public class LoadingPlanStagingService extends StagingService {

  /**
   * Constructor for injecting
   *
   * @param loadingPlanStagingRepository staging repo
   * @param dataTransferOutBoundRepository outbound repo
   * @param dataTransferInBoundRepository inbound repo
   */
  public LoadingPlanStagingService(
      @Autowired LoadingPlanStagingRepository loadingPlanStagingRepository,
      @Autowired LoadingPlanDataTransferOutBoundRepository dataTransferOutBoundRepository,
      @Autowired LoadingPlanDataTransferInBoundRepository dataTransferInBoundRepository) {
    super(
        loadingPlanStagingRepository,
        dataTransferOutBoundRepository,
        dataTransferInBoundRepository);
  }

  // GRPC Services
  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  // Repositories
  @Autowired private LoadingPlanStagingRepository loadingPlanStagingRepository;
  @Autowired private LoadingPlanDataTransferOutBoundRepository dataTransferOutBoundRepository;
  @Autowired private LoadingPlanDataTransferInBoundRepository dataTransferInBoundRepository;
  @Autowired private LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;
  @Autowired private LoadingSequenceRepository loadingSequenceRepository;
  @Autowired private StageOffsetRepository stageOffsetRepository;
  @Autowired private StageDurationRepository stageDurationRepository;
  @Autowired private LoadingInformationStatusRepository loadingInformationStatusRepository;
  @Autowired private PyUserRepository pyUserRepository;
  @Autowired private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @Autowired private LoadingInformationRepository loadingInformationRepository;

  List<Long> loadingPlanPortWiseDetailsIds = null;
  List<Long> loadingSequenceIds = null;
  Long voyageId = null;
  Long loadablePatternId = null;
  Long synopticalTableXId = null;
  Long portXId = null;

  /**
   * getCommunicationData method for get JsonArray from processIdentifierList
   *
   * @param processIdentifierList - list of processIdentifier
   * @param processId - processId
   * @param processGroupId - processGroupId
   * @param referenceId- referenceId value
   * @param pyUserId- processId of algoResponse
   * @return JsonArray
   */
  public JsonArray getCommunicationData(
      List<String> processIdentifierList,
      String processId,
      String processGroupId,
      Long referenceId,
      String pyUserId)
      throws GenericServiceException {

    log.debug(
        "Converting Tables -> JSON ::: MessageType: {}, Reference referenceId: {}, ProcessId: {}, Tables: {}",
        processGroupId,
        referenceId,
        processId,
        processIdentifierList);

    String dependantProcessId = null;
    String dependantProcessModule = null;

    // Communication referenceId set as patternId between Loading Plan and Loadable Study to convert
    // to LS Id at LS service
    if (MessageTypes.LOADINGPLAN.getMessageType().equals(processGroupId)) {

      // Check prev module communicated
      final LoadingInformation loadingInfo =
          loadingInformationRepository
              .findById(referenceId)
              .orElseThrow(
                  () -> {
                    log.error("LoadingInformation not found. Id: {}", referenceId);
                    return new GenericServiceException(
                        "LoadingInformation not found. Id: " + referenceId,
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
                  });

      if (!isCommunicated(
          MessageTypes.LOADABLESTUDY.getMessageType(),
          loadingInfo.getLoadablePatternXId(),
          CommunicationModule.LOADABLE_STUDY.getModuleName())) {

        Common.CommunicationTriggerRequest communicationTriggerRequest =
            Common.CommunicationTriggerRequest.newBuilder()
                .setReferenceId(loadingInfo.getLoadablePatternXId())
                .setMessageType(MessageTypes.LOADABLESTUDY.getMessageType())
                .build();
        final Common.CommunicationTriggerResponse response =
            loadableStudyServiceBlockingStub.triggerCommunication(communicationTriggerRequest);

        // Communication trigger failure
        if (!SUCCESS.equals(response.getResponseStatus().getStatus())) {
          log.error(
              "Previous module trigger failed. MessageType: {}, ReferenceId: {}, Module: {}, Response: {}",
              MessageTypes.LOADABLESTUDY.getMessageType(),
              referenceId,
              CommunicationModule.LOADABLE_STUDY.getModuleName(),
              response);
          throw new GenericServiceException(
              "Previous module trigger failed. ReferenceId: " + referenceId,
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
        }

        dependantProcessId = response.getProcessId();
        dependantProcessModule = CommunicationModule.LOADABLE_STUDY.getModuleName();
      }
    }

    JsonArray array = new JsonArray();
    List<String> processedList = new ArrayList<>();
    for (String processIdentifier : processIdentifierList) {
      if (processedList.contains(processIdentifier)) {
        log.info("Table already fetched :" + processIdentifier);
        continue;
      }
      List<Object> object = new ArrayList<>();
      switch (ProcessIdentifiers.valueOf(processIdentifier)) {
        case loading_information:
          {
            getLoadingInformation(
                array,
                referenceId,
                processIdentifier,
                processId,
                processGroupId,
                processedList,
                object,
                dependantProcessId,
                dependantProcessModule);
            break;
          }
        case cargo_topping_off_sequence:
          {
            String cargoToppingOffSequencesJson =
                loadingPlanStagingRepository.getCargoToppingOffSequenceWithLoadingId(referenceId);
            if (cargoToppingOffSequencesJson != null) {
              JsonArray cargoToppingOffSequences =
                  JsonParser.parseString(cargoToppingOffSequencesJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  cargoToppingOffSequences,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case loading_berth_details:
          {
            String loadingBerthDetailsJson =
                loadingPlanStagingRepository.getLoadingBerthDetailWithLoadingId(referenceId);
            if (loadingBerthDetailsJson != null) {
              JsonArray loadingBerthDetails =
                  JsonParser.parseString(loadingBerthDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingBerthDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case loading_delay:
          {
            String loadingDelayJson =
                loadingPlanStagingRepository.getLoadingDelayWithLoadingId(referenceId);
            if (loadingDelayJson != null) {
              JsonArray loadingDelay = JsonParser.parseString(loadingDelayJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingDelay,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case loading_delay_reason:
          {
            String loadingDelayReasonJson =
                loadingPlanStagingRepository.getLoadingDelayReasonWithLoadingId(referenceId);
            if (loadingDelayReasonJson != null) {
              JsonArray loadingDelayReason =
                  JsonParser.parseString(loadingDelayReasonJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingDelayReason,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case loading_machinary_in_use:
          {
            String loadingMachineryInUseJson =
                loadingPlanStagingRepository.getLoadingMachineryInUseWithLoadingId(referenceId);
            if (loadingMachineryInUseJson != null) {
              JsonArray loadingMachineryInUse =
                  JsonParser.parseString(loadingMachineryInUseJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingMachineryInUse,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case loading_sequence:
          {
            String loadingSequenceJson =
                loadingPlanStagingRepository.getLoadingSequenceByLoadingId(referenceId);
            if (loadingSequenceJson != null) {
              JsonArray loadingSequence =
                  JsonParser.parseString(loadingSequenceJson).getAsJsonArray();
              List<LoadingSequence> loadingSequenceList =
                  loadingSequenceRepository.findByLoadingInformationId(referenceId);
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingSequence,
                  dependantProcessId,
                  dependantProcessModule);
              loadingSequenceIds =
                  loadingSequenceList.stream()
                      .map(LoadingSequence::getId)
                      .collect(Collectors.toList());
            }
            break;
          }
        case loading_plan_portwise_details:
          {
            getLoadingPlanPortWiseDetails(
                array,
                referenceId,
                processIdentifier,
                processId,
                processGroupId,
                processedList,
                object,
                dependantProcessId,
                dependantProcessModule);
            break;
          }
        case port_loading_plan_stability_parameters:
          {
            String portLoadingPlanStabilityParamJson =
                loadingPlanStagingRepository.getPortLoadingPlanStabilityParamWithLoadingId(
                    referenceId);
            if (portLoadingPlanStabilityParamJson != null) {
              JsonArray portLoadingPlanStabilityParams =
                  JsonParser.parseString(portLoadingPlanStabilityParamJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portLoadingPlanStabilityParams,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }

        case port_loading_plan_rob_details:
          {
            String portLoadingPlanRobDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanRobDetailsWithLoadingId(referenceId);
            if (portLoadingPlanRobDetailsJson != null) {
              JsonArray portLoadingPlanRobDetails =
                  JsonParser.parseString(portLoadingPlanRobDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portLoadingPlanRobDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case loading_plan_ballast_details:
          {
            if (loadingPlanPortWiseDetailsIds != null && !loadingPlanPortWiseDetailsIds.isEmpty()) {
              String loadingPlanBallastDetailsJson =
                  loadingPlanStagingRepository.getLoadingPlanBallastDetailsWithPortIds(
                      loadingPlanPortWiseDetailsIds);
              if (loadingPlanBallastDetailsJson != null) {
                JsonArray loadingPlanBallastDetails =
                    JsonParser.parseString(loadingPlanBallastDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadingPlanBallastDetails,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case loading_plan_rob_details:
          {
            if (loadingPlanPortWiseDetailsIds != null && !loadingPlanPortWiseDetailsIds.isEmpty()) {
              String loadingPlanRobDetailsJson =
                  loadingPlanStagingRepository.getLoadingPlanRobDetailsWithPortIds(
                      loadingPlanPortWiseDetailsIds);
              if (loadingPlanRobDetailsJson != null) {
                JsonArray loadingPlanRobDetails =
                    JsonParser.parseString(loadingPlanRobDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadingPlanRobDetails,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }

        case port_loading_plan_stowage_ballast_details:
          {
            String portLoadingPlanBallastDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanBallastDetailsWithLoadingId(
                    referenceId);
            if (portLoadingPlanBallastDetailsJson != null) {
              JsonArray portLoadingPlanBallastDetails =
                  JsonParser.parseString(portLoadingPlanBallastDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portLoadingPlanBallastDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case port_loading_plan_stowage_ballast_details_temp:
          {
            String portLoadingPlanBallastTempDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanBallastTempDetailsWithLoadingId(
                    referenceId);
            if (portLoadingPlanBallastTempDetailsJson != null) {
              JsonArray portLoadingPlanBallastTempDetails =
                  JsonParser.parseString(portLoadingPlanBallastTempDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portLoadingPlanBallastTempDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case port_loading_plan_stowage_details:
          {
            String portLoadingPlanStowageDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanStowageDetailsWithLoadingId(
                    referenceId);
            if (portLoadingPlanStowageDetailsJson != null) {
              JsonArray portLoadingPlanStowageDetails =
                  JsonParser.parseString(portLoadingPlanStowageDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portLoadingPlanStowageDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case port_loading_plan_stowage_details_temp:
          {
            String portLoadingPlanStowageTempDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanStowageTempDetailsWithLoadingId(
                    referenceId);
            if (portLoadingPlanStowageTempDetailsJson != null) {
              JsonArray portLoadingPlanStowageTempDetails =
                  JsonParser.parseString(portLoadingPlanStowageTempDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portLoadingPlanStowageTempDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }

        case loading_plan_stowage_details:
          {
            if (loadingPlanPortWiseDetailsIds != null && !loadingPlanPortWiseDetailsIds.isEmpty()) {
              String loadingPlanStowageDetailsJson =
                  loadingPlanStagingRepository.getLoadingPlanStowageDetailsWithPortIds(
                      loadingPlanPortWiseDetailsIds);
              if (loadingPlanStowageDetailsJson != null) {
                JsonArray loadingPlanStowageDetails =
                    JsonParser.parseString(loadingPlanStowageDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadingPlanStowageDetails,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case loading_sequence_stability_parameters:
          {
            String loadingSequenceStabilityParametersJson =
                loadingPlanStagingRepository.getLoadingSequenceStabilityParametersWithLoadingId(
                    referenceId);
            if (loadingSequenceStabilityParametersJson != null) {
              JsonArray loadingSequenceStabilityParameters =
                  JsonParser.parseString(loadingSequenceStabilityParametersJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingSequenceStabilityParameters,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case loading_plan_stability_parameters:
          {
            if (loadingPlanPortWiseDetailsIds != null && !loadingPlanPortWiseDetailsIds.isEmpty()) {
              String loadingPlanStabilityParametersJson =
                  loadingPlanStagingRepository.getLoadingPlanStabilityParametersWithPortIds(
                      loadingPlanPortWiseDetailsIds);
              if (loadingPlanStabilityParametersJson != null) {
                JsonArray loadingPlanStabilityParameters =
                    JsonParser.parseString(loadingPlanStabilityParametersJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadingPlanStabilityParameters,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case port_loadable_plan_commingle_details_temp:
          {
            String portLoadingPlanCommingleTempDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanCommingleTempDetailsWithLoadingId(
                    referenceId);
            if (portLoadingPlanCommingleTempDetailsJson != null) {
              JsonArray portLoadingPlanCommingleTempDetails =
                  JsonParser.parseString(portLoadingPlanCommingleTempDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portLoadingPlanCommingleTempDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case port_loadable_plan_commingle_details:
          {
            String portLoadingPlanCommingleDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanCommingleDetailsWithLoadingId(
                    referenceId);
            if (portLoadingPlanCommingleDetailsJson != null) {
              JsonArray portLoadingPlanCommingleDetails =
                  JsonParser.parseString(portLoadingPlanCommingleDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portLoadingPlanCommingleDetails,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case bill_of_ladding:
          {
            String billOfLandingJson =
                loadingPlanStagingRepository.getBillOfLandingWithLoadingId(referenceId);
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
        case pyuser:
          {
            if (pyUserId != null) {
              Optional<PyUser> pyUser = pyUserRepository.findById(pyUserId);
              if (pyUser.isPresent()) {
                object.add(pyUser.get());
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
            break;
          }
        case voyage:
          {
            if (voyageId != null) {
              LoadableStudy.VoyageActivateRequest.Builder builder =
                  LoadableStudy.VoyageActivateRequest.newBuilder();
              builder.setId(voyageId);
              LoadableStudy.VoyageActivateReply reply = getVoyage(builder.build());
              VoyageActivate voyageActivate = null;
              if (LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                voyageActivate =
                    new VoyageActivate(
                        reply.getVoyageActivateRequest().getId(),
                        reply.getVoyageActivateRequest().getVoyageStatus());
                object.add(voyageActivate);
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
            break;
          }
        case loadable_pattern:
          {
            if (loadablePatternId != null) {
              LoadableStudy.LoadableStudyPatternCommunicationRequest.Builder builder =
                  LoadableStudy.LoadableStudyPatternCommunicationRequest.newBuilder();
              builder.setId(loadablePatternId);
              LoadableStudy.LoadableStudyPatternCommunicationReply reply =
                  this.loadableStudyServiceBlockingStub.getLoadablePatternForCommunication(
                      builder.build());
              if (LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
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
        case loadicator_data_for_synoptical_table:
          {
            if (loadablePatternId != null) {
              LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                  LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
              builder.setId(loadablePatternId);
              LoadableStudy.LoadableStudyCommunicationReply reply =
                  this.loadableStudyServiceBlockingStub.getLoadicatorDataSynopticalForCommunication(
                      builder.build());
              if (LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                if (reply.getDataJson() != null) {
                  JsonArray synopticalTableLoadicatorData =
                      JsonParser.parseString(reply.getDataJson()).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      processIdentifier,
                      processId,
                      processGroupId,
                      processedList,
                      synopticalTableLoadicatorData,
                      dependantProcessId,
                      dependantProcessModule);
                }
              }
            }
            break;
          }
        case ballast_operation:
          {
            if (loadingSequenceIds != null && !loadingSequenceIds.isEmpty()) {
              String ballastOperationJson =
                  loadingPlanStagingRepository.getBallastOperationWithLoadingSeqIds(
                      loadingSequenceIds);
              if (ballastOperationJson != null) {
                JsonArray ballastOperation =
                    JsonParser.parseString(ballastOperationJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    ballastOperation,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case eduction_operation:
          {
            if (loadingSequenceIds != null && !loadingSequenceIds.isEmpty()) {
              String eductionOperationJson =
                  loadingPlanStagingRepository.getEductionOperationWithLoadingSeqIds(
                      loadingSequenceIds);
              if (eductionOperationJson != null) {
                JsonArray eductionOperation =
                    JsonParser.parseString(eductionOperationJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    eductionOperation,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case cargo_loading_rate:
          {
            if (loadingSequenceIds != null && !loadingSequenceIds.isEmpty()) {
              String cargoLoadingRateJson =
                  loadingPlanStagingRepository.getCargoLoadingRateWithLoadingSeqIds(
                      loadingSequenceIds);
              if (cargoLoadingRateJson != null) {
                JsonArray cargoLoadingRate =
                    JsonParser.parseString(cargoLoadingRateJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    cargoLoadingRate,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case json_data:
          {
            LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
            builder.setId(referenceId);
            LoadableStudy.LoadableStudyCommunicationReply reply =
                this.loadableStudyServiceBlockingStub.getJsonDataForCommunication(builder.build());
            if (LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
              if (reply.getDataJson() != null) {
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
        case loading_port_tide_details:
          {
            String portTideDetailJson =
                loadingPlanStagingRepository.getPortTideDetailWithLoadingId(referenceId);
            if (portTideDetailJson != null) {
              JsonArray portTideDetail =
                  JsonParser.parseString(portTideDetailJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  portTideDetail,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case algo_error_heading:
          {
            String algoErrorHeadingJson =
                loadingPlanStagingRepository.getAlgoErrorHeadingWithLoadingId(referenceId);
            if (algoErrorHeadingJson != null) {
              JsonArray algoErrorHeading =
                  JsonParser.parseString(algoErrorHeadingJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  algoErrorHeading,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case algo_errors:
          {
            String algoErrorsJson =
                loadingPlanStagingRepository.getAlgoErrorsWithAlgoErrorHeadingIds(referenceId);
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
            break;
          }
        case loading_instructions:
          {
            String loadingInstructionJson =
                loadingPlanStagingRepository.getLoadingInstructionWithLoadingId(referenceId);
            if (loadingInstructionJson != null) {
              JsonArray loadingInstruction =
                  JsonParser.parseString(loadingInstructionJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingInstruction,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case synoptical_table:
          {
            if (synopticalTableXId != null) {
              LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                  LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
              builder.setId(synopticalTableXId);
              LoadableStudy.LoadableStudyCommunicationReply reply =
                  this.loadableStudyServiceBlockingStub.getSynopticalDataForCommunication(
                      builder.build());
              if (LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                if (!reply.getDataJson().isEmpty()) {
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
        case loadable_study_port_rotation:
          {
            if (portXId != null && loadablePatternId != null) {
              LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                  LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
              builder.setId(portXId);
              builder.setLoadablePatternId(loadablePatternId);
              LoadableStudy.LoadableStudyCommunicationReply reply =
                  this.loadableStudyServiceBlockingStub
                      .getLoadableStudyPortRotationDataForCommunication(builder.build());
              if (LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                if (reply.getDataJson() != null) {
                  JsonArray loadableStudyPortRotationData =
                      JsonParser.parseString(reply.getDataJson()).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      processIdentifier,
                      processId,
                      processGroupId,
                      processedList,
                      loadableStudyPortRotationData,
                      dependantProcessId,
                      dependantProcessModule);
                }
              }
            }
            break;
          }
        case loading_information_algo_status:
          {
            String loadingInformationAlgoStatusJson =
                loadingPlanStagingRepository.getLoadingInformationAlgoStatusWithLoadingId(
                    referenceId);
            if (loadingInformationAlgoStatusJson != null) {
              JsonArray loadingInformationAlgoStatus =
                  JsonParser.parseString(loadingInformationAlgoStatusJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingInformationAlgoStatus,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case loading_plan_commingle_details:
          {
            if (!CollectionUtils.isEmpty(loadingPlanPortWiseDetailsIds)) {
              String loadingPlanCommingleDetailsJson =
                  loadingPlanStagingRepository.getLoadingPlanCommingleDetailsWithPortIds(
                      loadingPlanPortWiseDetailsIds);
              if (loadingPlanCommingleDetailsJson != null) {
                JsonArray loadingPlanCommingleDetails =
                    JsonParser.parseString(loadingPlanCommingleDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadingPlanCommingleDetails,
                    dependantProcessId,
                    dependantProcessModule);
              }
            }
            break;
          }
        case loadable_plan_stowage_ballast_details:
          {
            if (loadablePatternId != null) {
              LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                  LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
              builder.setId(loadablePatternId);
              LoadableStudy.LoadableStudyCommunicationReply reply =
                  this.loadableStudyServiceBlockingStub
                      .getLoadablePlanStowageBallastDetailsForCommunication(builder.build());
              if (LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                if (reply.getDataJson() != null) {
                  JsonArray loadablePlanStowageBallastDetailsData =
                      JsonParser.parseString(reply.getDataJson()).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      processIdentifier,
                      processId,
                      processGroupId,
                      processedList,
                      loadablePlanStowageBallastDetailsData,
                      dependantProcessId,
                      dependantProcessModule);
                }
              }
            }
            break;
          }
        case loadable_pattern_cargo_details:
          {
            if (loadablePatternId != null) {
              LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                  LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
              builder.setId(loadablePatternId);
              LoadableStudy.LoadableStudyCommunicationReply reply =
                  this.loadableStudyServiceBlockingStub
                      .getLoadablePatternCargoDetailsForCommunication(builder.build());
              if (LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                if (reply.getDataJson() != null) {
                  JsonArray loadablePatternCargoDetailsData =
                      JsonParser.parseString(reply.getDataJson()).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      processIdentifier,
                      processId,
                      processGroupId,
                      processedList,
                      loadablePatternCargoDetailsData,
                      dependantProcessId,
                      dependantProcessModule);
                }
              }
            }
            break;
          }
        case loadable_plan_commingle_details_portwise:
          {
            if (loadablePatternId != null) {
              LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                  LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
              builder.setId(loadablePatternId);
              LoadableStudy.LoadableStudyCommunicationReply reply =
                  this.loadableStudyServiceBlockingStub
                      .getLoadablePlanCommingleDetailsPortwiseForCommunication(builder.build());
              if (LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                if (reply.getDataJson() != null) {
                  JsonArray loadablePlanComminglePortwiseDetailsData =
                      JsonParser.parseString(reply.getDataJson()).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      processIdentifier,
                      processId,
                      processGroupId,
                      processedList,
                      loadablePlanComminglePortwiseDetailsData,
                      dependantProcessId,
                      dependantProcessModule);
                }
              }
            }
            break;
          }
        case on_board_quantity:
          {
            if (loadablePatternId != null) {
              LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                  LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
              builder.setId(loadablePatternId);
              LoadableStudy.LoadableStudyCommunicationReply reply =
                  this.loadableStudyServiceBlockingStub.getOnBoardQuantityForCommunication(
                      builder.build());
              if (LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                if (reply.getDataJson() != null) {
                  JsonArray onBoardQuantityData =
                      JsonParser.parseString(reply.getDataJson()).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      processIdentifier,
                      processId,
                      processGroupId,
                      processedList,
                      onBoardQuantityData,
                      dependantProcessId,
                      dependantProcessModule);
                }
              }
            }
            break;
          }
        case on_hand_quantity:
          {
            if (loadablePatternId != null) {
              LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                  LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
              builder.setId(loadablePatternId);
              LoadableStudy.LoadableStudyCommunicationReply reply =
                  this.loadableStudyServiceBlockingStub.getOnHandQuantityForCommunication(
                      builder.build());
              if (LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
                if (reply.getDataJson() != null) {
                  JsonArray onHandQuantityData =
                      JsonParser.parseString(reply.getDataJson()).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      processIdentifier,
                      processId,
                      processGroupId,
                      processedList,
                      onHandQuantityData,
                      dependantProcessId,
                      dependantProcessModule);
                }
              }
            }
            break;
          }
        case loading_rules:
          {
            String loadingRuleJson =
                loadingPlanStagingRepository.getLoadingRuleWithLoadingId(referenceId);
            if (loadingRuleJson != null) {
              JsonArray loadingRule = JsonParser.parseString(loadingRuleJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingRule,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        case loading_rule_input:
          {
            String loadingRuleInputJson =
                loadingPlanStagingRepository.getLoadingRuleInputWithLoadingId(referenceId);
            if (loadingRuleInputJson != null) {
              JsonArray loadingRuleInput =
                  JsonParser.parseString(loadingRuleInputJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingRuleInput,
                  dependantProcessId,
                  dependantProcessModule);
            }
            break;
          }
        default:
          log.warn("Process Identifier Not Configured: {}", processIdentifier);
          break;
      }
    }
    return array;
  }

  private void getLoadingInformation(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      List<Object> object,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    String loadingInformationJson = loadingPlanStagingRepository.getLoadingInformationJson(id);
    if (loadingInformationJson != null) {
      JsonObject loadingInfoJsonObj =
          JsonParser.parseString(loadingInformationJson).getAsJsonArray().get(0).getAsJsonObject();
      voyageId = loadingInfoJsonObj.get("voyage_xid").getAsLong();
      loadablePatternId = loadingInfoJsonObj.get("loadable_pattern_xid").getAsLong();
      synopticalTableXId = loadingInfoJsonObj.get("synoptical_table_xid").getAsLong();
      portXId = loadingInfoJsonObj.get("port_xid").getAsLong();
      Long stageOffsetId = loadingInfoJsonObj.get("stages_min_amount_xid").getAsLong();
      Long stageDurationId = loadingInfoJsonObj.get("stages_duration_xid").getAsLong();
      Long loadingInformationStatusId = loadingInfoJsonObj.get("loading_status_xid").getAsLong();
      Long arrivalStatusId = loadingInfoJsonObj.get("arrival_status_xid").getAsLong();
      Long departureStatusId = loadingInfoJsonObj.get("departure_status_xid").getAsLong();
      if (stageOffsetId != null) {
        getStageOffset(
            array,
            stageOffsetId,
            "stages_min_amount",
            processId,
            processGroupId,
            processedList,
            dependantProcessId,
            dependantProcessModule);
      }
      if (stageDurationId != null) {
        getStageDuration(
            array,
            stageDurationId,
            "stages_duration",
            processId,
            processGroupId,
            processedList,
            dependantProcessId,
            dependantProcessModule);
      }
      if (loadingInformationStatusId != null) {
        getLoadingInformationStatus(
            array,
            loadingInformationStatusId,
            "loading_information_status",
            processId,
            processGroupId,
            processedList,
            dependantProcessId,
            dependantProcessModule);
      }
      if (arrivalStatusId != null) {
        getArrivalStatus(
            array,
            arrivalStatusId,
            "loading_information_arrival_status",
            processId,
            processGroupId,
            processedList,
            dependantProcessId,
            dependantProcessModule);
      }
      if (departureStatusId != null) {
        getDepartureStatus(
            array,
            departureStatusId,
            "loading_information_departure_status",
            processId,
            processGroupId,
            processedList,
            dependantProcessId,
            dependantProcessModule);
      }
      object.addAll(Arrays.asList(loadingInfoJsonObj));
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

  private void getStageOffset(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    Optional<StageOffset> stageOffsetObj = stageOffsetRepository.findById(id);
    if (!stageOffsetObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(stageOffsetObj.get()));
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

  private void getStageDuration(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    Optional<StageDuration> stageDurationObj = stageDurationRepository.findById(id);
    if (!stageDurationObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(stageDurationObj.get()));
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

  private void getLoadingInformationStatus(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    Optional<LoadingInformationStatus> loadingInformationStatusObj =
        loadingInformationStatusRepository.findById(id);
    if (!loadingInformationStatusObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(loadingInformationStatusObj.get()));
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

  private void getArrivalStatus(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    Optional<LoadingInformationStatus> arrivalStatusObj =
        loadingInformationStatusRepository.findById(id);
    if (!arrivalStatusObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(arrivalStatusObj.get()));
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

  private void getDepartureStatus(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    Optional<LoadingInformationStatus> departureStatusObj =
        loadingInformationStatusRepository.findById(id);
    if (!departureStatusObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(departureStatusObj.get()));
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

  private void getLoadingPlanPortWiseDetails(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      List<Object> object,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    if (loadingSequenceIds != null && !loadingSequenceIds.isEmpty()) {
      String loadingPlanPortWiseDetailsJson =
          loadingPlanStagingRepository.getLoadingPlanPortWiseDetailsWithLoadingSeqIds(
              loadingSequenceIds);
      if (loadingPlanPortWiseDetailsJson != null) {
        JsonArray loadingPlanPortWiseDetails =
            JsonParser.parseString(loadingPlanPortWiseDetailsJson).getAsJsonArray();
        List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsList =
            loadingPlanPortWiseDetailsRepository.findByLoadingInformationId(id);
        addIntoProcessedList(
            array,
            object,
            processIdentifier,
            processId,
            processGroupId,
            processedList,
            loadingPlanPortWiseDetails,
            dependantProcessId,
            dependantProcessModule);
        loadingPlanPortWiseDetailsIds =
            loadingPlanPortWiseDetailsList.stream()
                .map(LoadingPlanPortWiseDetails::getId)
                .collect(Collectors.toList());
      }
    }
  }

  public LoadableStudy.VoyageActivateReply getVoyage(
      LoadableStudy.VoyageActivateRequest grpcRequest) {
    return this.loadableStudyServiceBlockingStub.getVoyage(grpcRequest);
  }
}
