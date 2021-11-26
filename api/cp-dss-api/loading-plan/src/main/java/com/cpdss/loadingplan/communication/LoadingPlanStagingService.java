/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.communication;

import com.cpdss.common.communication.StagingService;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.loadingplan.domain.VoyageActivate;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.utility.LoadingPlanConstants;
import com.cpdss.loadingplan.utility.ProcessIdentifiers;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @Author Selvy Thomas */
@Log4j2
@Service
public class LoadingPlanStagingService extends StagingService {

  @Autowired private LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;
  @Autowired private LoadingSequenceRepository loadingSequenceRepository;
  @Autowired private StageOffsetRepository stageOffsetRepository;
  @Autowired private StageDurationRepository stageDurationRepository;
  @Autowired private LoadingInformationStatusRepository loadingInformationStatusRepository;
  @Autowired private LoadingPlanStagingRepository loadingPlanStagingRepository;
  @Autowired private PyUserRepository pyUserRepository;
  @Autowired private AlgoErrorHeadingRepository algoErrorHeadingRepository;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  public LoadingPlanStagingService(
      @Autowired LoadingPlanStagingRepository loadingPlanStagingRepository) {
    super(loadingPlanStagingRepository);
  }

  LoadingInformation loadingInformation = null;
  List<Long> loadingPlanPortWiseDetailsIds = null;
  List<Long> loadingSequenceIds = null;
  List<Long> algoErrorHeadingsIds = null;
  Long voyageId = null;
  Long loadablePatternId = null;
  /**
   * getCommunicationData method for get JsonArray from processIdentifierList
   *
   * @param processIdentifierList - list of processIdentifier
   * @param processId - processId
   * @param processGroupId - processGroupId
   * @param Id- id
   * @param pyUserId- processId of algoResponse
   * @return JsonArray
   */
  public JsonArray getCommunicationData(
      List<String> processIdentifierList,
      String processId,
      String processGroupId,
      Long Id,
      String pyUserId) {
    log.info("LoadingPlanStaging Service processidentifier list:" + processIdentifierList);
    JsonArray array = new JsonArray();
    List<String> processedList = new ArrayList<>();
    for (String processIdentifier : processIdentifierList) {
      if (processedList.contains(processIdentifier)) {
        log.info("Table already fetched :" + processIdentifier);
        continue;
      }
      JsonObject jsonObject = new JsonObject();
      List<Object> object = new ArrayList<Object>();
      switch (ProcessIdentifiers.valueOf(processIdentifier)) {
        case loading_information:
          {
            getLoadingInformation(
                array, Id, processIdentifier, processId, processGroupId, processedList, object);
            break;
          }
        case cargo_topping_off_sequence:
          {
            String cargoToppingOffSequencesJson =
                loadingPlanStagingRepository.getCargoToppingOffSequenceWithLoadingId(Id);
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
                  cargoToppingOffSequences);
            }
            break;
          }
        case loading_berth_details:
          {
            String loadingBerthDetailsJson =
                loadingPlanStagingRepository.getLoadingBerthDetailWithLoadingId(Id);
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
                  loadingBerthDetails);
            }
            break;
          }
        case loading_delay:
          {
            String loadingDelayJson = loadingPlanStagingRepository.getLoadingDelayWithLoadingId(Id);
            if (loadingDelayJson != null) {
              JsonArray loadingDelay = JsonParser.parseString(loadingDelayJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingDelay);
            }
            break;
          }
        case loading_machinary_in_use:
          {
            String loadingMachineryInUseJson =
                loadingPlanStagingRepository.getLoadingMachineryInUseWithLoadingId(Id);
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
                  loadingMachineryInUse);
            }
            break;
          }
        case loading_sequence:
          {
            String loadingSequenceJson =
                loadingPlanStagingRepository.getLoadingSequenceByLoadingId(Id);
            if (loadingSequenceJson != null) {
              JsonArray loadingSequence =
                  JsonParser.parseString(loadingSequenceJson).getAsJsonArray();
              List<LoadingSequence> loadingSequenceList =
                  loadingSequenceRepository.findByLoadingInformationId(Id);
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadingSequence);
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
                array, Id, processIdentifier, processId, processGroupId, processedList, object);
            break;
          }
        case port_loading_plan_stability_parameters:
          {
            String portLoadingPlanStabilityParamJson =
                loadingPlanStagingRepository.getPortLoadingPlanStabilityParamWithLoadingId(Id);
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
                  portLoadingPlanStabilityParams);
            }
            break;
          }

        case port_loading_plan_rob_details:
          {
            String portLoadingPlanRobDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanRobDetailsWithLoadingId(Id);
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
                  portLoadingPlanRobDetails);
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
                    loadingPlanBallastDetails);
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
                    loadingPlanRobDetails);
              }
            }
            break;
          }

        case port_loading_plan_stowage_ballast_details:
          {
            String portLoadingPlanBallastDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanBallastDetailsWithLoadingId(Id);
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
                  portLoadingPlanBallastDetails);
            }
            break;
          }
        case port_loading_plan_stowage_ballast_details_temp:
          {
            String portLoadingPlanBallastTempDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanBallastTempDetailsWithLoadingId(Id);
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
                  portLoadingPlanBallastTempDetails);
            }
            break;
          }
        case port_loading_plan_stowage_details:
          {
            String portLoadingPlanStowageDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanStowageDetailsWithLoadingId(Id);
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
                  portLoadingPlanStowageDetails);
            }
            break;
          }
        case port_loading_plan_stowage_details_temp:
          {
            String portLoadingPlanStowageTempDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanStowageTempDetailsWithLoadingId(Id);
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
                  portLoadingPlanStowageTempDetails);
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
                    loadingPlanStowageDetails);
              }
            }
            break;
          }
        case loading_sequence_stability_parameters:
          {
            String loadingSequenceStabilityParametersJson =
                loadingPlanStagingRepository.getLoadingSequenceStabilityParametersWithLoadingId(Id);
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
                  loadingSequenceStabilityParameters);
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
                    loadingPlanStabilityParameters);
              }
            }
            break;
          }
        case port_loadable_plan_commingle_details_temp:
          {
            String portLoadingPlanCommingleTempDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanCommingleTempDetailsWithLoadingId(
                    Id);
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
                  portLoadingPlanCommingleTempDetails);
            }
            break;
          }
        case port_loadable_plan_commingle_details:
          {
            String portLoadingPlanCommingleDetailsJson =
                loadingPlanStagingRepository.getPortLoadingPlanCommingleDetailsWithLoadingId(Id);
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
                  portLoadingPlanCommingleDetails);
            }
            break;
          }
        case bill_of_ladding:
          {
            String billOfLandingJson =
                loadingPlanStagingRepository.getBillOfLandingWithLoadingId(Id);
            if (billOfLandingJson != null) {
              JsonArray billOfLanding = JsonParser.parseString(billOfLandingJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  billOfLanding);
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
                    null);
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
                    null);
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
                      loadablePattern);
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
                      synopticalTableLoadicatorData);
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
                    ballastOperation);
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
                    eductionOperation);
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
                    cargoLoadingRate);
              }
            }
            break;
          }
        case json_data:
          {
            LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
                LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
            builder.setId(Id);
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
                    jsonData);
              }
            }
            break;
          }
        case loading_port_tide_details:
          {
            String portTideDetailJson =
                loadingPlanStagingRepository.getPortTideDetailWithLoadingId(Id);
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
                  portTideDetail);
            }
            break;
          }
        case algo_error_heading:
          {
            String algoErrorHeadingJson =
                loadingPlanStagingRepository.getAlgoErrorHeadingWithLoadingId(Id);
            if (algoErrorHeadingJson != null) {
              algoErrorHeadingsIds =
                  algoErrorHeadingRepository.getAlgoErrorHeadingIdWithLoadingInformationId(Id);
              JsonArray algoErrorHeading =
                  JsonParser.parseString(algoErrorHeadingJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  algoErrorHeading);
            }
            break;
          }
        case algo_errors:
          {
            if (algoErrorHeadingsIds != null && !algoErrorHeadingsIds.isEmpty()) {
              String algoErrorsJson =
                  loadingPlanStagingRepository.getAlgoErrorsWithAlgoErrorHeadingIds(
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
                    algoErrors);
              }
            }
            break;
          }
        case loading_instructions:
          {
            String loadingInstructionJson =
                loadingPlanStagingRepository.getLoadingInstructionWithLoadingId(Id);
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
                  loadingInstruction);
            }
            break;
          }
      }
    }
    return array;
  }

  private void addIntoProcessedList(
      JsonArray array,
      List<Object> object,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      JsonArray jsonArray) {
    array.add(createJsonObject(object, processIdentifier, processId, processGroupId, jsonArray));
    processedList.add(processIdentifier);
  }

  private void getLoadingInformation(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      List<Object> object) {
    String loadingInformationJson = loadingPlanStagingRepository.getLoadingInformationJson(id);
    if (loadingInformationJson != null) {
      JsonObject loadingInfoJsonObj =
          JsonParser.parseString(loadingInformationJson).getAsJsonArray().get(0).getAsJsonObject();
      voyageId = loadingInfoJsonObj.get("voyage_xid").getAsLong();
      loadablePatternId = loadingInfoJsonObj.get("loadable_pattern_xid").getAsLong();
      Long stageOffsetId = loadingInfoJsonObj.get("stages_min_amount_xid").getAsLong();
      Long stageDurationId = loadingInfoJsonObj.get("stages_duration_xid").getAsLong();
      Long loadingInformationStatusId = loadingInfoJsonObj.get("loading_status_xid").getAsLong();
      Long arrivalStatusId = loadingInfoJsonObj.get("arrival_status_xid").getAsLong();
      Long departureStatusId = loadingInfoJsonObj.get("departure_status_xid").getAsLong();
      if (stageOffsetId != null) {
        getStageOffset(
            array, stageOffsetId, "stages_min_amount", processId, processGroupId, processedList);
      }
      if (stageDurationId != null) {
        getStageDuration(
            array, stageDurationId, "stages_duration", processId, processGroupId, processedList);
      }
      if (loadingInformationStatusId != null) {
        getLoadingInformationStatus(
            array,
            loadingInformationStatusId,
            "loading_information_status",
            processId,
            processGroupId,
            processedList);
      }
      if (arrivalStatusId != null) {
        getArrivalStatus(
            array,
            arrivalStatusId,
            "loading_information_arrival_status",
            processId,
            processGroupId,
            processedList);
      }
      if (departureStatusId != null) {
        getDepartureStatus(
            array,
            departureStatusId,
            "loading_information_departure_status",
            processId,
            processGroupId,
            processedList);
      }
      object.addAll(Arrays.asList(loadingInfoJsonObj));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList, null);
    }
  }

  private void getStageOffset(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList) {
    Optional<StageOffset> stageOffsetObj = stageOffsetRepository.findById(id);
    if (!stageOffsetObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(stageOffsetObj.get()));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList, null);
    }
  }

  private void getStageDuration(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList) {
    Optional<StageDuration> stageDurationObj = stageDurationRepository.findById(id);
    if (!stageDurationObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(stageDurationObj.get()));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList, null);
    }
  }

  private void getLoadingInformationStatus(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList) {
    Optional<LoadingInformationStatus> loadingInformationStatusObj =
        loadingInformationStatusRepository.findById(id);
    if (!loadingInformationStatusObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(loadingInformationStatusObj.get()));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList, null);
    }
  }

  private void getArrivalStatus(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList) {
    Optional<LoadingInformationStatus> arrivalStatusObj =
        loadingInformationStatusRepository.findById(id);
    if (!arrivalStatusObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(arrivalStatusObj.get()));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList, null);
    }
  }

  private void getDepartureStatus(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList) {
    Optional<LoadingInformationStatus> departureStatusObj =
        loadingInformationStatusRepository.findById(id);
    if (!departureStatusObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(departureStatusObj.get()));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList, null);
    }
  }

  private void getLoadingPlanPortWiseDetails(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      List<Object> object) {
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
            loadingPlanPortWiseDetails);
        loadingPlanPortWiseDetailsIds =
            loadingPlanPortWiseDetailsList.stream()
                .map(LoadingPlanPortWiseDetails::getId)
                .collect(Collectors.toList());
      }
    }
  }
  /**
   * method for create JsonObject
   *
   * @param list - List
   * @param processIdentifier - processId
   * @param processId - processGroupId
   * @param processGroupId- processGroupId
   * @return JsonObject
   */
  public JsonObject createJsonObject(
      List<Object> list,
      String processIdentifier,
      String processId,
      String processGroupId,
      JsonArray jsonArray) {

    JsonObject jsonObject = new JsonObject();
    JsonObject metaData = new JsonObject();
    metaData.addProperty("processIdentifier", processIdentifier);
    metaData.addProperty("processId", processId);
    metaData.addProperty("processGroupId", processGroupId);
    jsonObject.add("meta_data", metaData);
    if (jsonArray != null) {
      jsonObject.add("data", jsonArray);
    } else {
      JsonArray array = new JsonArray();
      for (Object obj : list) {
        array.add(new Gson().toJson(obj));
      }
      jsonObject.add("data", array);
    }
    return jsonObject;
  }

  public LoadableStudy.VoyageActivateReply getVoyage(
      LoadableStudy.VoyageActivateRequest grpcRequest) {
    return this.loadableStudyServiceBlockingStub.getVoyage(grpcRequest);
  }
}
