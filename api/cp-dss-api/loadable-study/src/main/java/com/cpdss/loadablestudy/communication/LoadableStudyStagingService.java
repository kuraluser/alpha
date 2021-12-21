/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.communication;

import com.cpdss.common.communication.StagingService;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.repository.AlgoErrorHeadingRepository;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanRepository;
import com.cpdss.loadablestudy.repository.SynopticalTableRepository;
import com.cpdss.loadablestudy.utility.ProcessIdentifiers;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @Author Selvy Thomas */
@Log4j2
@Service
public class LoadableStudyStagingService extends StagingService {

  @Autowired private LoadableStudyStagingRepository loadableStudyStagingRepository;
  @Autowired private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @Autowired private LoadablePatternRepository loadablePatternRepository;
  @Autowired private LoadablePlanRepository loadablePlanRepository;
  @Autowired private SynopticalTableRepository synopticalTableRepository;
  @Autowired private CargoNominationRepository cargoNominationRepository;

  public LoadableStudyStagingService(
      @Autowired LoadableStudyStagingRepository loadableStudyStagingRepository) {
    super(loadableStudyStagingRepository);
  }

  /**
   * getCommunicationData method for get JsonArray from processIdentifierList
   *
   * @param processIdentifierList - list of processIdentifier
   * @param processId - processId
   * @param processGroupId - processGroupId
   * @param Id- id
   * @return JsonArray
   */
  public JsonArray getCommunicationData(
      List<String> processIdentifierList, String processId, String processGroupId, Long Id) {
    log.info("LoadingPlanStaging Service processidentifier list:" + processIdentifierList);
    JsonArray array = new JsonArray();
    List<String> processedList = new ArrayList<>();
    List<Long> algoErrorHeadingsIds = null;
    List<Long> loadablePatternIds = getLoadablePatternIds(Id, processGroupId);
    Long loadableStudyId = getLoadableStudyId(Id, processGroupId);
    List<Long> loadablePlanIds = null;
    List<Long> synopticalTableIds = null;
    List<Long> cargoNominationIds = null;
    Long voyageId = null;
    for (String processIdentifier : processIdentifierList) {
      if (processedList.contains(processIdentifier)) {
        log.info("Table already fetched :" + processIdentifier);
        continue;
      }
      JsonObject jsonObject = new JsonObject();
      List<Object> object = new ArrayList<Object>();
      switch (ProcessIdentifiers.valueOf(processIdentifier)) {
        case loadable_study:
          {
            String loadableStudyJson =
                loadableStudyStagingRepository.getLoadableStudyWithId(loadableStudyId);
            if (loadableStudyJson != null) {
              JsonArray loadableStudy = JsonParser.parseString(loadableStudyJson).getAsJsonArray();
              JsonObject loadableStudyJsonObj = loadableStudy.get(0).getAsJsonObject();
              voyageId = loadableStudyJsonObj.get("voyage_xid").getAsLong();
              if (voyageId != null) {
                String voyageJson = loadableStudyStagingRepository.getVoyageWithId(voyageId);
                if (voyageJson != null) {
                  JsonArray voyage = JsonParser.parseString(voyageJson).getAsJsonArray();
                  addIntoProcessedList(
                      array, object, "voyage", processId, processGroupId, processedList, voyage);
                }
              }
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadableStudy);
            }
            break;
          }
        case comingle_cargo:
          {
            String comingleCargoJson =
                loadableStudyStagingRepository.getCommingleCargoWithLoadableStudyId(
                    loadableStudyId);
            if (comingleCargoJson != null) {
              JsonArray comingleCargo = JsonParser.parseString(comingleCargoJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  comingleCargo);
            }
            break;
          }
        case cargo_nomination:
          {
            String cargoNominationJson =
                loadableStudyStagingRepository.getCargoNominationWithLoadableStudyId(
                    loadableStudyId);
            if (cargoNominationJson != null) {
              JsonArray cargoNomination =
                  JsonParser.parseString(cargoNominationJson).getAsJsonArray();
              cargoNominationIds =
                  cargoNominationRepository.getIdsByLoadableStudyId(loadableStudyId);
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  cargoNomination);
            }
            break;
          }
        case loadable_study_port_rotation:
          {
            String loadableStudyPortRotationJson =
                loadableStudyStagingRepository.getLoadableStudyPortRotationWithLoadableStudyId(
                    loadableStudyId);
            if (loadableStudyPortRotationJson != null) {
              JsonArray loadableStudyPortRotation =
                  JsonParser.parseString(loadableStudyPortRotationJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadableStudyPortRotation);
            }
            break;
          }
        case on_hand_quantity:
          {
            String onHandQuantityJson =
                loadableStudyStagingRepository.getOnHandQuantityWithLoadableStudyId(
                    loadableStudyId);
            if (onHandQuantityJson != null) {
              JsonArray onHandQuantity =
                  JsonParser.parseString(onHandQuantityJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  onHandQuantity);
            }
            break;
          }
        case on_board_quantity:
          {
            String onBoardQuantityJson =
                loadableStudyStagingRepository.getOnBoardQuantityWithLoadableStudyId(
                    loadableStudyId);
            if (onBoardQuantityJson != null) {
              JsonArray onBoardQuantity =
                  JsonParser.parseString(onBoardQuantityJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  onBoardQuantity);
            }
            break;
          }
        case loadable_quantity:
          {
            String loadableQuantityJson =
                loadableStudyStagingRepository.getLoadableQuantityWithLoadableStudyId(
                    loadableStudyId);
            if (loadableQuantityJson != null) {
              JsonArray loadableQuantity =
                  JsonParser.parseString(loadableQuantityJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadableQuantity);
            }
            break;
          }
        case json_data:
          {
            String jsonDataJson =
                loadableStudyStagingRepository.getJsonDataWithLoadableStudyId(loadableStudyId);
            if (jsonDataJson != null) {
              JsonArray jsonData = JsonParser.parseString(jsonDataJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  jsonData);
            }
            break;
          }
        case loadable_study_algo_status:
          {
            String loadableStudyAlgoStatusJson =
                loadableStudyStagingRepository.getLoadableStudyAlgoStatusWithLoadableStudyId(
                    loadableStudyId);
            if (loadableStudyAlgoStatusJson != null) {
              JsonArray loadableStudyAlgoStatus =
                  JsonParser.parseString(loadableStudyAlgoStatusJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadableStudyAlgoStatus);
            }
            break;
          }
        case loadable_pattern:
          {
            String loadablePatternJson =
                loadableStudyStagingRepository.getLoadablePatterns(loadablePatternIds);
            if (loadablePatternJson != null) {
              JsonArray loadablePattern =
                  JsonParser.parseString(loadablePatternJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadablePattern);
            }
            break;
          }
        case algo_error_heading:
          {
            String algoErrorHeadingJson =
                loadableStudyStagingRepository.getAlgoErrorHeadingWithLoadableStudyId(
                    loadableStudyId);
            if (algoErrorHeadingJson != null) {
              algoErrorHeadingsIds =
                  algoErrorHeadingRepository.getAlgoErrorIdWithLoadableStudyId(loadableStudyId);
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
                  loadableStudyStagingRepository.getAlgoErrorsWithAlgoErrorHeadingIds(
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
        case loadable_plan_constraints:
          {
            if (loadablePatternIds != null && !loadablePatternIds.isEmpty()) {
              String loadablePlanConstraintsJson =
                  loadableStudyStagingRepository.getLoadablePlanConstraintsWithLoadablePatternId(
                      loadablePatternIds);
              if (loadablePlanConstraintsJson != null) {
                JsonArray loadablePlanConstraints =
                    JsonParser.parseString(loadablePlanConstraintsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadablePlanConstraints);
              }
            }
            break;
          }
        case loadable_plan_quantity:
          {
            if (loadablePatternIds != null && !loadablePatternIds.isEmpty()) {
              String loadablePlanQuantityJson =
                  loadableStudyStagingRepository.getLoadablePlanQuantityWithLoadablePatternId(
                      loadablePatternIds);
              if (loadablePlanQuantityJson != null) {
                JsonArray loadablePlanQuantity =
                    JsonParser.parseString(loadablePlanQuantityJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadablePlanQuantity);
              }
            }
            break;
          }
        case loadable_plan_commingle_details:
          {
            if (loadablePatternIds != null && !loadablePatternIds.isEmpty()) {
              String loadablePlanCommingleDetailsJson =
                  loadableStudyStagingRepository
                      .getLoadablePlanCommingleDetailsWithLoadablePatternId(loadablePatternIds);
              if (loadablePlanCommingleDetailsJson != null) {
                JsonArray loadablePlanCommingleDetails =
                    JsonParser.parseString(loadablePlanCommingleDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadablePlanCommingleDetails);
              }
            }
            break;
          }
        case loadable_pattern_cargo_topping_off_sequence:
          {
            if (loadablePatternIds != null && !loadablePatternIds.isEmpty()) {
              String loadablePatternCargoToppingOffSequenceJson =
                  loadableStudyStagingRepository
                      .getLoadablePatternCargoToppingOffSequenceWithLoadablePatternId(
                          loadablePatternIds);
              if (loadablePatternCargoToppingOffSequenceJson != null) {
                JsonArray loadablePatternCargoToppingOffSequence =
                    JsonParser.parseString(loadablePatternCargoToppingOffSequenceJson)
                        .getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadablePatternCargoToppingOffSequence);
              }
            }
            break;
          }
        case loadable_plan_stowage_details:
          {
            if (null != loadablePatternIds && !loadablePatternIds.isEmpty()) {
              String loadablePlanStowageDetailsJson =
                  loadableStudyStagingRepository
                      .getLoadablePlanStowageDetailsWithLoadablePatternIds(loadablePatternIds);
              if (null != loadablePlanStowageDetailsJson) {
                JsonArray loadablePlanStowageDetails =
                    JsonParser.parseString(loadablePlanStowageDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadablePlanStowageDetails);
              }
            }
            break;
          }
        case loadable_plan_ballast_details:
          {
            if (loadablePatternIds != null && !loadablePatternIds.isEmpty()) {
              String loadablePlanBallastDetailsJson =
                  loadableStudyStagingRepository.getLoadablePlanBallastDetailsWithLoadablePatternId(
                      loadablePatternIds);
              if (loadablePlanBallastDetailsJson != null) {
                JsonArray loadablePlanBallastDetails =
                    JsonParser.parseString(loadablePlanBallastDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadablePlanBallastDetails);
              }
            }
            break;
          }
        case loadable_plan_commingle_details_portwise:
          {
            if (loadablePatternIds != null && !loadablePatternIds.isEmpty()) {
              String loadablePlanComminglePortwiseDetailsJson =
                  loadableStudyStagingRepository
                      .getLoadablePlanComminglePortwiseDetailsWithLoadablePatternId(
                          loadablePatternIds);
              if (loadablePlanComminglePortwiseDetailsJson != null) {
                JsonArray loadablePlanComminglePortwiseDetails =
                    JsonParser.parseString(loadablePlanComminglePortwiseDetailsJson)
                        .getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadablePlanComminglePortwiseDetails);
              }
            }
            break;
          }
        case stability_parameters:
          {
            if (loadablePatternIds != null && !loadablePatternIds.isEmpty()) {
              String stabilityParametersJson =
                  loadableStudyStagingRepository.getStabilityParametersWithLoadablePatternId(
                      loadablePatternIds);
              if (stabilityParametersJson != null) {
                JsonArray stabilityParameters =
                    JsonParser.parseString(stabilityParametersJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    stabilityParameters);
              }
            }
            break;
          }
        case loadable_pattern_cargo_details:
          {
            if (loadablePatternIds != null && !loadablePatternIds.isEmpty()) {
              String loadablePatternCargoDetailsJson =
                  loadableStudyStagingRepository
                      .getLoadablePatternCargoDetailsWithLoadablePatternId(loadablePatternIds);
              if (loadablePatternCargoDetailsJson != null) {
                JsonArray loadablePatternCargoDetails =
                    JsonParser.parseString(loadablePatternCargoDetailsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadablePatternCargoDetails);
              }
            }
            break;
          }
        case loadable_plan_stowage_ballast_details:
          {
            String loadablePlanJson =
                loadableStudyStagingRepository.getLoadablePlanWithLoadableStudyId(loadableStudyId);
            if (loadablePlanJson != null) {
              JsonArray loadablePlan = JsonParser.parseString(loadablePlanJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  ProcessIdentifiers.loadable_plan.name(),
                  processId,
                  processGroupId,
                  processedList,
                  loadablePlan);
            }
            String loadablePlanStowageBallastDetailsJson =
                loadableStudyStagingRepository.getLoadablePlanStowageBallastDetails(
                    loadablePatternIds);
            if (loadablePlanStowageBallastDetailsJson != null) {
              JsonArray loadablePlanStowageBallastDetails =
                  JsonParser.parseString(loadablePlanStowageBallastDetailsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadablePlanStowageBallastDetails);
            }
            break;
          }
        case synoptical_table:
          {
            String synopticalTableJson =
                loadableStudyStagingRepository.getSynopticalTableWithLoadableStudyId(
                    loadableStudyId);
            if (synopticalTableJson != null) {
              JsonArray synopticalTable =
                  JsonParser.parseString(synopticalTableJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  synopticalTable);
            }
            break;
          }
        case loadicator_data_for_synoptical_table:
          {
            synopticalTableIds = synopticalTableRepository.getIdsByLoadableStudyId(loadableStudyId);
            String synopticalTableLoadicatorDataJson =
                loadableStudyStagingRepository.getSynopticalTblLoadicatorDataWithSynopticalTblId(
                    synopticalTableIds);
            if (synopticalTableLoadicatorDataJson != null) {
              JsonArray synopticalTableLoadicatorData =
                  JsonParser.parseString(synopticalTableLoadicatorDataJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  synopticalTableLoadicatorData);
            }
            break;
          }
        case cargo_nomination_operation_details:
          {
            String cargoNominationOperationDetailsDataJson =
                loadableStudyStagingRepository
                    .getCargoNominationOperationDetailsWithCargoNominationId(cargoNominationIds);
            if (null != cargoNominationOperationDetailsDataJson) {
              JsonArray cargoNominationOperationDetailsData =
                  JsonParser.parseString(cargoNominationOperationDetailsDataJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  cargoNominationOperationDetailsData);
            }
            break;
          }
        case communication_status_update:
          {
            String communicationStatusUpdateDataJson =
                loadableStudyStagingRepository.getCommunicationStatusUpdate(
                    loadableStudyId, processGroupId);
            if (null != communicationStatusUpdateDataJson) {
              JsonArray communicationStatusUpdateData =
                  JsonParser.parseString(communicationStatusUpdateDataJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  communicationStatusUpdateData);
            }
            break;
          }
        case cow_history:
          {
            if (voyageId != null) {
              String cowHistoryJson =
                  loadableStudyStagingRepository.getCowHistoryWithVoyageId(voyageId);
              if (cowHistoryJson != null) {
                JsonArray cowHistory = JsonParser.parseString(cowHistoryJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    cowHistory);
              }
            }
            break;
          }
        case discharge_quantity_cargo_details:
          {
            if (loadablePatternIds != null && !loadablePatternIds.isEmpty()) {
              String dischargePatternQuantityCargoPortwiseDetailsJson =
                  loadableStudyStagingRepository
                      .getDischargePatternQuantityCargoPortwiseDetailsWithLoadablePatternId(
                          loadablePatternIds);
              if (dischargePatternQuantityCargoPortwiseDetailsJson != null) {
                JsonArray dischargePatternQuantityCargoPortwiseDetails =
                    JsonParser.parseString(dischargePatternQuantityCargoPortwiseDetailsJson)
                        .getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    dischargePatternQuantityCargoPortwiseDetails);
              }
            }
            break;
          }
        case loadable_study_rules:
          {
            String loadableStudyRulesJson =
                loadableStudyStagingRepository.getLoadableStudyRules(loadableStudyId);
            if (null != loadableStudyRulesJson) {
              JsonArray loadableStudyRules =
                  JsonParser.parseString(loadableStudyRulesJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadableStudyRules);
            }
            break;
          }
        case loadable_study_rule_input:
          {
            String loadableStudyRuleInputJson =
                loadableStudyStagingRepository.getLoadableStudyRuleInput(loadableStudyId);
            if (null != loadableStudyRuleInputJson) {
              JsonArray loadableStudyRuleInput =
                  JsonParser.parseString(loadableStudyRuleInputJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadableStudyRuleInput);
            }
            break;
          }
        case loadable_plan_comments:
          {
            String loadablePlanCommentsJson =
                loadableStudyStagingRepository.getLoadablePlanComments(loadablePatternIds);
            if (null != loadablePlanCommentsJson) {
              JsonArray loadablePlanComments =
                  JsonParser.parseString(loadablePlanCommentsJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadablePlanComments);
            }
            break;
          }
        case loadable_plan_stowage_details_temp:
          {
            String loadablePlanStowageDetailsTempJson =
                loadableStudyStagingRepository.getLoadablePlanStowageDetailsTemp(
                    loadablePatternIds);
            if (null != loadablePlanStowageDetailsTempJson) {
              JsonArray loadablePlanStowageDetailsTemp =
                  JsonParser.parseString(loadablePlanStowageDetailsTempJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadablePlanStowageDetailsTemp);
            }
            break;
          }
        case loadable_pattern_algo_status:
          {
            String loadablePatternAlgoStatusJson =
                loadableStudyStagingRepository.getLoadablePatternAlgoStatus(loadablePatternIds);
            if (null != loadablePatternAlgoStatusJson) {
              JsonArray loadablePatternAlgoStatus =
                  JsonParser.parseString(loadablePatternAlgoStatusJson).getAsJsonArray();
              addIntoProcessedList(
                  array,
                  object,
                  processIdentifier,
                  processId,
                  processGroupId,
                  processedList,
                  loadablePatternAlgoStatus);
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

  /**
   * Method to get loadable pattern ids based on the message type
   *
   * @param id id value
   * @param messageType message type value
   * @return list of loadable pattern ids for the given id
   */
  private List<Long> getLoadablePatternIds(Long id, String messageType) {
    if (MessageTypes.VALIDATEPLAN.getMessageType().equals(messageType)
        || MessageTypes.PATTERNDETAIL.getMessageType().equals(messageType)) {
      return Collections.singletonList(id);
    }
    return loadablePatternRepository.getLoadablePatternIds(id);
  }

  /**
   * Method to get loadable study id based on message type
   *
   * @param id id value
   * @param messageType message type value
   * @return loadable study id
   */
  private Long getLoadableStudyId(Long id, String messageType) {
    if (MessageTypes.VALIDATEPLAN.getMessageType().equals(messageType)
        || MessageTypes.PATTERNDETAIL.getMessageType().equals(messageType)) {
      return loadablePatternRepository.getLoadableStudyId(id);
    }
    return id;
  }
}
