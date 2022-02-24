/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.communication;

import static com.cpdss.common.communication.CommunicationConstants.*;
import static com.cpdss.common.utils.MessageTypes.getMessageType;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LS_STATUS_CONFIRMED;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;
import static org.springframework.util.StringUtils.hasLength;

import com.cpdss.common.communication.StagingService;
import com.cpdss.common.domain.DependentProcessResponse;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.repository.communication.LoadableStudyDataTransferInBoundRepository;
import com.cpdss.loadablestudy.repository.communication.LoadableStudyDataTransferOutBoundRepository;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADABLE_STUDY_COLUMNS;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants.VESSEL_INFO_TABLES;
import com.cpdss.loadablestudy.utility.ProcessIdentifiers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** @author Selvy Thomas */
@Log4j2
@Service
public class LoadableStudyStagingService extends StagingService {

  /**
   * Constructor for injecting
   *
   * @param loadableStudyStagingRepository staging repo
   * @param dataTransferOutBoundRepository outbound repo
   * @param dataTransferInBoundRepository inbound repo
   */
  public LoadableStudyStagingService(
      @Autowired LoadableStudyStagingRepository loadableStudyStagingRepository,
      @Autowired LoadableStudyDataTransferOutBoundRepository dataTransferOutBoundRepository,
      @Autowired LoadableStudyDataTransferInBoundRepository dataTransferInBoundRepository) {
    super(
        loadableStudyStagingRepository,
        dataTransferOutBoundRepository,
        dataTransferInBoundRepository);
  }

  // GRPC Services
  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("loadingPlanService")
  private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanServiceBlockingStub;

  // Repositories
  @Autowired private LoadableStudyStagingRepository loadableStudyStagingRepository;
  @Autowired private LoadableStudyDataTransferOutBoundRepository dataTransferOutBoundRepository;
  @Autowired private LoadableStudyDataTransferInBoundRepository dataTransferInBoundRepository;
  @Autowired private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @Autowired private LoadablePatternRepository loadablePatternRepository;
  @Autowired private SynopticalTableRepository synopticalTableRepository;
  @Autowired private CargoNominationRepository cargoNominationRepository;
  @Autowired private LoadableStudyRepository loadableStudyRepository;

  /**
   * getCommunicationData method for get JsonArray from processIdentifierList
   *
   * @param processIdentifierList - list of processIdentifier
   * @param processId - processId
   * @param processGroupId - processGroupId
   * @param referenceId - referenceId can be loadableStudyId or patternId
   * @param pyUserId - pyUserId
   * @return JsonArray
   * @throws GenericServiceException Exception if module not configured
   */
  public JsonArray getCommunicationData(
      List<String> processIdentifierList,
      String processId,
      String processGroupId,
      Long referenceId,
      String pyUserId)
      throws GenericServiceException {

    log.debug(
        "Converting Tables -> JSON ::: MessageType: {}, Reference Id: {}, ProcessId: {}, Tables: {}, PyUserId:{}",
        processGroupId,
        referenceId,
        processId,
        processIdentifierList,
        pyUserId);

    // Check previous module communicated
    DependentProcessResponse dependentProcessResponse =
        checkPrevModuleCommunicated(processGroupId, referenceId);

    JsonArray array = new JsonArray();
    List<String> processedList = new ArrayList<>();
    List<Long> algoErrorHeadingsIds = null;
    List<Long> loadablePatternIds = getLoadablePatternIds(referenceId, processGroupId);
    Long loadableStudyId = getLoadableStudyId(referenceId, processGroupId);
    List<Long> synopticalTableIds;
    List<Long> cargoNominationIds = null;
    Long voyageId = null;
    Long vesselId = null;
    for (String processIdentifier : processIdentifierList) {
      if (processedList.contains(processIdentifier)) {
        log.info("Table already fetched : {}", processIdentifier);
        continue;
      }
      List<Object> object = new ArrayList<>();
      try {
        switch (ProcessIdentifiers.valueOf(processIdentifier)) {
          case loadable_study:
            {
              String loadableStudyJson =
                  loadableStudyStagingRepository.getLoadableStudyWithId(loadableStudyId);
              if (loadableStudyJson != null) {
                JsonArray loadableStudy =
                    JsonParser.parseString(loadableStudyJson).getAsJsonArray();
                JsonObject loadableStudyJsonObj = loadableStudy.get(0).getAsJsonObject();
                voyageId = loadableStudyJsonObj.get("voyage_xid").getAsLong();
                vesselId =
                    loadableStudyJsonObj
                        .get(LOADABLE_STUDY_COLUMNS.VESSEL_XID.getColumnName())
                        .getAsLong();
                String voyageJson = loadableStudyStagingRepository.getVoyageWithId(voyageId);
                if (voyageJson != null) {
                  JsonArray voyage = JsonParser.parseString(voyageJson).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      "voyage",
                      processId,
                      processGroupId,
                      processedList,
                      voyage,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
                }
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadableStudy,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
              }
              break;
            }
          case comingle_cargo:
            {
              String comingleCargoJson =
                  loadableStudyStagingRepository.getCommingleCargoWithLoadableStudyId(
                      loadableStudyId);
              if (comingleCargoJson != null) {
                JsonArray comingleCargo =
                    JsonParser.parseString(comingleCargoJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    comingleCargo,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    cargoNomination,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    loadableStudyPortRotation,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    onHandQuantity,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    onBoardQuantity,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    loadableQuantity,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    jsonData,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    loadableStudyAlgoStatus,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    loadablePattern,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    algoErrorHeading,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                      algoErrors,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
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
                      loadablePlanConstraints,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
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
                      loadablePlanQuantity,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
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
                      loadablePlanCommingleDetails,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
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
                      loadablePatternCargoToppingOffSequence,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
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
                      loadablePlanStowageDetails,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
                }
              }
              break;
            }
          case loadable_plan_ballast_details:
            {
              if (loadablePatternIds != null && !loadablePatternIds.isEmpty()) {
                String loadablePlanBallastDetailsJson =
                    loadableStudyStagingRepository
                        .getLoadablePlanBallastDetailsWithLoadablePatternId(loadablePatternIds);
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
                      loadablePlanBallastDetails,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
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
                      loadablePlanComminglePortwiseDetails,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
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
                      stabilityParameters,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
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
                      loadablePatternCargoDetails,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
                }
              }
              break;
            }
          case loadable_plan_stowage_ballast_details:
            {
              String loadablePlanJson =
                  loadableStudyStagingRepository.getLoadablePlanWithLoadableStudyId(
                      loadableStudyId);
              if (loadablePlanJson != null) {
                JsonArray loadablePlan = JsonParser.parseString(loadablePlanJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    ProcessIdentifiers.loadable_plan.name(),
                    processId,
                    processGroupId,
                    processedList,
                    loadablePlan,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    loadablePlanStowageBallastDetails,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    synopticalTable,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
              }
              break;
            }
          case loadicator_data_for_synoptical_table:
            {
              synopticalTableIds =
                  synopticalTableRepository.getIdsByLoadableStudyId(loadableStudyId);
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
                    synopticalTableLoadicatorData,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    JsonParser.parseString(cargoNominationOperationDetailsDataJson)
                        .getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    cargoNominationOperationDetailsData,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    communicationStatusUpdateData,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                      cowHistory,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
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
                      dischargePatternQuantityCargoPortwiseDetails,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
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
                    loadableStudyRules,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    loadableStudyRuleInput,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    loadablePlanComments,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    loadablePlanStowageDetailsTemp,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                    loadablePatternAlgoStatus,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
              }
              break;
            }
          case discharge_cow_details:
            {
              String dischargeStudyCowDetailJson =
                  loadableStudyStagingRepository.getDischargeStudyCowDetailWithDischargeStudyId(
                      loadableStudyId);
              if (dischargeStudyCowDetailJson != null) {
                JsonArray dischargeStudyCowDetail =
                    JsonParser.parseString(dischargeStudyCowDetailJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    dischargeStudyCowDetail,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
              }
              break;
            }
          case rule_vessel_mapping:
            {
              final String ruleVesselMappingJson =
                  getCommunicationDataFromVesselInfo(
                      processIdentifier, Collections.singletonList(vesselId));
              if (null != ruleVesselMappingJson) {
                JsonArray ruleVesselMapping =
                    JsonParser.parseString(ruleVesselMappingJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    ruleVesselMapping,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());

                // Get rule_vessel_mapping_input data
                List<Long> ruleVesselMappingIds = new ArrayList<>();
                ruleVesselMapping.forEach(
                    jsonElement ->
                        ruleVesselMappingIds.add(
                            jsonElement
                                .getAsJsonObject()
                                .get(LOADABLE_STUDY_COLUMNS.ID.getColumnName())
                                .getAsLong()));

                // TODO rule_vessel_mapping_input to be fetched in a separate case in switch
                final String ruleVesselMappingInputJson =
                    getCommunicationDataFromVesselInfo(
                        VESSEL_INFO_TABLES.RULE_VESSEL_MAPPING_INPUT.getTableName(),
                        ruleVesselMappingIds);
                if (null != ruleVesselMappingInputJson) {
                  JsonArray ruleVesselMappingInput =
                      JsonParser.parseString(ruleVesselMappingInputJson).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      VESSEL_INFO_TABLES.RULE_VESSEL_MAPPING_INPUT.getTableName(),
                      processId,
                      processGroupId,
                      processedList,
                      ruleVesselMappingInput,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
                }
              }
              break;
            }
          case loadable_study_attachments:
            {
              final String loadableStudyAttachmentsJson =
                  loadableStudyStagingRepository.getLoadableStudyAttachments(loadableStudyId);

              if (hasLength(loadableStudyAttachmentsJson)) {
                JsonArray loadableStudyAttachments =
                    JsonParser.parseString(loadableStudyAttachmentsJson).getAsJsonArray();
                addIntoProcessedList(
                    array,
                    object,
                    processIdentifier,
                    processId,
                    processGroupId,
                    processedList,
                    loadableStudyAttachments,
                    dependentProcessResponse.getDependantProcessId(),
                    dependentProcessResponse.getDependantProcessModule());
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
                if (LoadableStudiesConstants.SUCCESS.equals(
                    reply.getResponseStatus().getStatus())) {
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
                        dependentProcessResponse.getDependantProcessId(),
                        dependentProcessResponse.getDependantProcessModule());
                  }
                }
              }
              break;
            }
          case port_wise_time_required_for_loading:
            {
              if (loadablePatternIds != null && !loadablePatternIds.isEmpty()) {
                String portWiseTimeRequiredForLoadingJson =
                    loadableStudyStagingRepository
                        .getPortWiseTimeRequiredForLoadingWithLoadablePatternIds(
                            loadablePatternIds);
                if (portWiseTimeRequiredForLoadingJson != null) {
                  JsonArray portWiseTimeRequiredForLoading =
                      JsonParser.parseString(portWiseTimeRequiredForLoadingJson).getAsJsonArray();
                  addIntoProcessedList(
                      array,
                      object,
                      processIdentifier,
                      processId,
                      processGroupId,
                      processedList,
                      portWiseTimeRequiredForLoading,
                      dependentProcessResponse.getDependantProcessId(),
                      dependentProcessResponse.getDependantProcessModule());
                }
              }
              break;
            }
          default:
            log.warn("Process Identifier Not Configured: {}", processIdentifier);
            break;
        }
      } catch (Exception e) {
        log.error("Process Identifier: {}", processIdentifier, e);
        throw new GenericServiceException(
            "Process Identifier: " + processIdentifier,
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
    }

    // Save to outbound table
    saveDataTransferOutBound(processGroupId, referenceId);
    return array;
  }

  /**
   * Method to get data from vessel-info service
   *
   * @param tableName tableName value
   * @param ids id values for which the records are to be fetched
   * @return JSON string of records in vessel-info service
   */
  private String getCommunicationDataFromVesselInfo(final String tableName, final List<Long> ids) {
    Common.CommunicationDataGetRequest vesselInfoDataGetRequest =
        Common.CommunicationDataGetRequest.newBuilder()
            .setTableName(tableName)
            .addAllId(ids)
            .build();

    final Common.CommunicationDataResponse vesselInfoDataResponse =
        vesselInfoGrpcService.getVesselData(vesselInfoDataGetRequest);
    if (SUCCESS.equals(vesselInfoDataResponse.getResponseStatus().getStatus())) {
      return vesselInfoDataResponse.getDataJson();
    }
    log.error(
        "External get failed. Service: {}, Table: {}, GRPC Request: {}",
        "vessel-info",
        tableName,
        vesselInfoDataGetRequest);
    return null;
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

  /**
   * Method to check if previous module is communicated
   *
   * @param processGroupId processGroupId/messageType value
   * @param referenceId reference Id value
   * @return DependentProcessResponse object
   * @throws GenericServiceException Exception on failure
   */
  private DependentProcessResponse checkPrevModuleCommunicated(
      final String processGroupId, final Long referenceId) throws GenericServiceException {

    try {
      final MessageTypes messageType = getMessageType(processGroupId);
      Long dependentReferenceId = null;
      final CommunicationModule dependentProcessModule = getDependantProcessModule(messageType);

      if (MessageTypes.DISCHARGESTUDY.equals(messageType)
          || MessageTypes.DISCHARGESTUDY_WITHOUT_ALGO.equals(messageType)) {
        final LoadableStudy dischargeStudy =
            loadableStudyRepository
                .findById(referenceId)
                .orElseThrow(
                    () -> {
                      log.error("DischargeStudy not found. Id: {}", referenceId);
                      return new GenericServiceException(
                          "DischargeStudy not found. Id: " + referenceId,
                          CommonErrorCodes.E_GEN_INTERNAL_ERR,
                          HttpStatusCode.INTERNAL_SERVER_ERROR);
                    });
        // Communication referenceId set as patternId between DischargeStudy and LoadingPlan
        // at Lp service
        List<LoadablePattern> loadablePatterns =
            loadablePatternRepository.findConfirmedPatternByLoadableStudyId(
                dischargeStudy.getConfirmedLoadableStudyId(), LS_STATUS_CONFIRMED);
        if (!CollectionUtils.isEmpty(loadablePatterns)) {
          dependentReferenceId =
              loadablePatterns.stream()
                  .findAny()
                  .orElseThrow(
                      () -> {
                        log.error(
                            "Loadable pattern not found. LS Id: {}",
                            dischargeStudy.getConfirmedLoadableStudyId());
                        return new GenericServiceException(
                            "Loadable pattern not found. LS Id: "
                                + dischargeStudy.getConfirmedLoadableStudyId(),
                            CommonErrorCodes.E_GEN_INTERNAL_ERR,
                            HttpStatusCode.INTERNAL_SERVER_ERROR);
                      })
                  .getId();
        }
      } else if (MessageTypes.VALIDATEPLAN.equals(messageType)) {
        // referenceId will be patternId for validate plan / stowage edit
        dependentReferenceId = referenceId;
      }

      // Trigger communication based on communication check
      if (Objects.nonNull(dependentProcessModule)
          && Objects.nonNull(dependentReferenceId)
          && !isCommunicated(
              messageType.getDependentMessageType().getMessageType(),
              dependentReferenceId,
              dependentProcessModule.getModuleName())) {
        final Common.CommunicationTriggerResponse response =
            triggerCommunication(
                messageType.getDependentMessageType(),
                dependentReferenceId,
                dependentProcessModule);
        return new DependentProcessResponse(
            response.getProcessId(), dependentProcessModule.getModuleName());
      }
    } catch (IllegalArgumentException e) {
      log.error(
          "Invalid argument in processGroupId: {}. Reference Id: {}, Trace: {}",
          processGroupId,
          referenceId,
          e);
    }
    return new DependentProcessResponse(null, null);
  }
}
