/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

// region Import
import static com.cpdss.common.communication.StagingService.isValidStageEntity;
import static com.cpdss.common.communication.StagingService.logSavedEntity;
import static com.cpdss.common.communication.StagingService.setEntityDocFields;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.CPDSS_BUILD_ENV_SHORE;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LoadableStudyTables;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.common.utils.StagingStatus;
import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADABLE_STUDY_COLUMNS;
import com.cpdss.loadablestudy.utility.ProcessIdentifiers;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

// endregion

/*
Author(S) - Mahesh KM , Selvy Thomas
Purpose - Communicating Lodable study related tables to ship to shore and vice versa
 */
@Log4j2
@Service
@Transactional
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoadableStudyCommunicationService {

  // region Autowired
  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Autowired private LoadableStudyStagingService loadableStudyStagingService;
  @Autowired private LoadableStudyRepository loadableStudyRepository;
  @Autowired private VoyageRepository voyageRepository;
  @Autowired private CommingleCargoRepository commingleCargoRepository;
  @Autowired private CargoNominationRepository cargoNominationRepository;
  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired
  private LoadableStudyPortRotationCommuncationRepository
      loadableStudyPortRotationCommuncationRepository;

  @Autowired private OnHandQuantityRepository onHandQuantityRepository;
  @Autowired private OnBoardQuantityRepository onBoardQuantityRepository;
  @Autowired private LoadableQuantityRepository loadableQuantityRepository;
  @Autowired private SynopticalTableRepository synopticalTableRepository;
  @Autowired private JsonDataRepository jsonDataRepository;
  @Autowired private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @Autowired private LoadablePatternRepository loadablePatternRepository;
  @Autowired private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @Autowired private AlgoErrorsRepository algoErrorsRepository;
  @Autowired private LoadablePlanConstraintsRespository loadablePlanConstraintsRespository;
  @Autowired private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @Autowired private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;

  @Autowired
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  @Autowired
  private LoadablePatternCargoToppingOffSequenceRepository
      loadablePatternCargoToppingOffSequenceRepository;

  @Autowired private LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @Autowired private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;

  @Autowired
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @Autowired private StabilityParameterRepository stabilityParameterRepository;
  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @Autowired
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired
  private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  @Autowired CargoOperationRepository cargoOperationRepository;

  @Autowired private JsonTypeRepository jsonTypeRepository;
  @Autowired private LoadableStudyStatusRepository loadableStudyStatusRepository;
  @Autowired private LoadablePlanRepository loadablePlanRepository;

  @Autowired
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @Autowired private LoadablePatternService loadablePatternService;
  @Autowired private LoadablePlanService loadablePlanService;
  @Autowired private CowHistoryRepository cowHistoryRepository;
  @Autowired private LoadableStudyServiceShore loadableStudyServiceShore;

  @Autowired
  private DischargePatternQuantityCargoPortwiseRepository
      dischargePatternQuantityCargoPortwiseRepository;

  @Autowired LoadableStudyRuleRepository loadableStudyRuleRepository;
  @Autowired LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;
  @Autowired LoadablePlanCommentsRepository loadablePlanCommentsRepository;
  @Autowired LoadablePlanStowageDetailsTempRepository loadablePlanStowageDetailsTempRepository;

  @Autowired private GenerateDischargeStudyJson generateDischargeStudyJson;
  @Autowired private VoyageStatusRepository voyageStatusRepository;
  @Autowired private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @Autowired private DischargeStudyCowDetailRepository dischargeStudyCowDetailRepository;
  // endregion

  // region Declarations
  private LoadableStudy loadableStudyStage = null;
  private Voyage voyageStage = null;
  private List<CommingleCargo> commingleCargoStage = null;
  private List<CargoNomination> cargoNominationStage = null;
  private List<LoadableStudyPortRotation> loadableStudyPortRotationStage = null;
  private List<LoadableStudyPortRotationCommunication> loadableStudyPortRotationStageCommunication =
      null;
  private List<OnHandQuantity> onHandQuantityStage = null;
  private List<OnBoardQuantity> onBoardQuantityStage = null;
  private List<LoadableQuantity> loadableQuantityStage = null;
  private List<SynopticalTable> synopticalTableStage = null;
  private List<JsonData> jsonDataStage = null;
  private LoadableStudyAlgoStatus loadableStudyAlgoStatusStage = null;
  private List<LoadablePattern> loadablePatternStage = null;
  private List<AlgoErrorHeading> algoErrorHeadingStage = null;
  private List<AlgoErrors> algoErrorsStage = null;
  private List<LoadablePlanConstraints> loadablePlanConstraintsStage = null;
  private List<LoadablePlanQuantity> loadablePlanQuantityStage = null;
  private List<LoadablePlanCommingleDetails> loadablePlanCommingleDetailsStage = null;
  private List<LoadablePatternCargoToppingOffSequence> loadablePatternCargoToppingOffSequenceStage =
      null;
  private List<LoadablePlanStowageDetails> loadablePlanStowageDetailsStage = null;
  private List<LoadablePlanBallastDetails> loadablePlanBallastDetailsStage = null;
  private List<LoadablePlanComminglePortwiseDetails> loadablePlanComminglePortwiseDetailsStage =
      null;
  private List<StabilityParameters> stabilityParametersStage = null;
  private List<LoadablePatternCargoDetails> loadablePatternCargoDetailsStage = null;
  private List<LoadablePlanStowageBallastDetails> loadablePlanStowageBallastDetailsStage = null;
  private List<SynopticalTableLoadicatorData> synopticalTableLoadicatorDataStage = null;
  private List<LoadablePlan> loadablePlanStage = null;
  private List<CargoNominationPortDetails> cargoNominationOperationDetailsStage = null;
  private List<CowHistory> cowHistoryStage = null;
  private List<DischargePatternQuantityCargoPortwiseDetails>
      dischargePatternQuantityCargoPortwiseDetailsStage = null;
  private List<LoadableStudyRules> loadableStudyRulesStage = null;
  private List<LoadableStudyRuleInput> loadableStudyRuleInputsStage = null;
  private List<LoadablePlanComments> loadablePlanCommentsStage = null;
  private List<LoadablePlanStowageDetailsTemp> loadablePlanStowageDetailsTempStage = null;
  private LoadablePatternAlgoStatus loadablePatternAlgoStatusStage = null;
  private List<DischargeStudyCowDetail> dischargeStudyCowDetailStage = null;
  private String ruleVesselMappingStage = null;
  private String ruleVesselMappingInputStage = null;
  HashMap<String, Long> idMap = new HashMap<>();
  Long voyageId;
  Long loadableStudyStatusId;
  Long voyageStatusId;
  String currentTableName = "";
  // endregion

  // region Get Methods
  public void getLoadableStudyStagingData(String status, String env, String taskName)
      throws GenericServiceException {
    log.info(
        "Inside getLoadableStudyStagingData for env:{}, status:{}, taskName: {}",
        env,
        status,
        taskName);
    String retryStatus = getRetryStatus(status);
    List<DataTransferStage> dataTransferStagesWithStatus = getDataTransferWithStatus(status);
    List<DataTransferStage> dataTransferStages =
        dataTransferStagesWithStatus.stream()
            .filter(
                dataTransfer ->
                    Arrays.asList(
                            MessageTypes.LOADABLESTUDY.getMessageType(),
                            MessageTypes.ALGORESULT.getMessageType())
                        .contains(dataTransfer.getProcessGroupId()))
            .collect(Collectors.toList());
    log.info("DataTransferStages in LOADABLE_STUDY_DATA_UPDATE task:" + dataTransferStages);
    if (!dataTransferStages.isEmpty()) {
      processStagingData(dataTransferStages, env, retryStatus);
    }
  }

  public void getStowageStagingData(String status, String env, String taskName)
      throws GenericServiceException {
    log.info(
        "Inside getStowageStagingData for env:{}, status:{}, taskName: {}", env, status, taskName);
    String retryStatus = getRetryStatus(status);
    List<DataTransferStage> dataTransferStagesWithStatus = getDataTransferWithStatus(status);
    List<DataTransferStage> dataTransferStages =
        dataTransferStagesWithStatus.stream()
            .filter(
                dataTransfer ->
                    Arrays.asList(
                            MessageTypes.VALIDATEPLAN.getMessageType(),
                            MessageTypes.PATTERNDETAIL.getMessageType())
                        .contains(dataTransfer.getProcessGroupId()))
            .collect(Collectors.toList());
    log.info("DataTransferStages in STOWAGE_DATA_UPDATE task:" + dataTransferStages);
    if (!dataTransferStages.isEmpty()) {
      processStagingData(dataTransferStages, env, retryStatus);
    }
  }

  public void getDischargeStudyStagingData(String status, String env, String taskName)
      throws GenericServiceException {
    log.info(
        "Inside getDischargeStudyStagingData for env:{}, status:{}, taskName:{}",
        env,
        status,
        taskName);
    String retryStatus = getRetryStatus(status);
    List<DataTransferStage> dataTransferStagesWithStatus = getDataTransferWithStatus(status);
    List<DataTransferStage> dataTransferStages =
        dataTransferStagesWithStatus.stream()
            .filter(
                dataTransfer ->
                    Objects.equals(
                        MessageTypes.DISCHARGESTUDY.getMessageType(),
                        dataTransfer.getProcessGroupId()))
            .collect(Collectors.toList());
    log.info("DataTransferStages in DISCHARGE_STUDY_DATA_UPDATE task:" + dataTransferStages);
    if (!dataTransferStages.isEmpty()) {
      processStagingData(dataTransferStages, env, retryStatus);
    }
  }

  private String getRetryStatus(String status) {
    String retryStatus = StagingStatus.RETRY.getStatus();
    if (status.equals(retryStatus)) {
      retryStatus = StagingStatus.FAILED.getStatus();
    }
    return retryStatus;
  }

  private List<DataTransferStage> getDataTransferWithStatus(String status) {
    List<DataTransferStage> dataTransferStagesWithStatus = null;
    if (status.equals(StagingStatus.IN_PROGRESS.getStatus())) {
      dataTransferStagesWithStatus =
          loadableStudyStagingService.getAllWithStatusAndTime(
              status, LocalDateTime.now().minusMinutes(10));
    } else {
      dataTransferStagesWithStatus = loadableStudyStagingService.getAllWithStatus(status);
    }
    return dataTransferStagesWithStatus;
  }

  private Optional<LoadablePattern> getLoadablePattern(Long id) {
    return loadablePatternRepository.findById(id);
  }

  /**
   * Method to get loadable plan from loadable_plan Id
   *
   * @param loadablePlanId loadablePlanId
   * @return LoadablePlan entity
   */
  private Optional<LoadablePlan> getLoadablePlan(Long loadablePlanId) {
    return null != loadablePlanId
        ? loadablePlanRepository.findById(loadablePlanId)
        : Optional.empty();
  }
  // endregion

  // region Process
  public void processStagingData(
      List<DataTransferStage> dataTransferStages, String env, String retryStatus)
      throws GenericServiceException {
    log.info("Inside processStagingData");
    Map<String, List<DataTransferStage>> dataTransferByProcessId =
        dataTransferStages.stream().collect(Collectors.groupingBy(DataTransferStage::getProcessId));
    log.info("processId group:" + dataTransferByProcessId);
    for (Map.Entry<String, List<DataTransferStage>> entry : dataTransferByProcessId.entrySet()) {
      clear();
      String processId = entry.getKey();

      loadableStudyStagingService.updateStatusForProcessId(
          processId, StagingStatus.IN_PROGRESS.getStatus());
      log.info(
          "updated status to in_progress for processId:{} and time:{}",
          processId,
          LocalDateTime.now());
      String processGroupId = entry.getValue().get(0).getProcessGroupId();
      for (DataTransferStage dataTransferStage : entry.getValue()) {
        String dataTransferString = dataTransferStage.getData();
        String data;
        if (dataTransferStage.getProcessIdentifier().equals("pyuser")) {
          data = JsonParser.parseString(dataTransferString).getAsJsonArray().get(0).toString();
        } else if (dataTransferStage.getProcessIdentifier().equals("json_data")) {
          data = JsonParser.parseString(dataTransferString).getAsJsonArray().toString();
        } else {
          data = replaceString(dataTransferString);
        }
        switch (ProcessIdentifiers.valueOf(dataTransferStage.getProcessIdentifier())) {
          case voyage:
            {
              Type type = new TypeToken<Voyage>() {}.getType();
              voyageStatusId = getVoyageStatus(data);
              voyageStage =
                  bindDataToEntity(
                      new Voyage(),
                      type,
                      LoadableStudyTables.VOYAGE,
                      data,
                      dataTransferStage.getId());
              break;
            }
          case loadable_study:
            {
              Type type = new TypeToken<LoadableStudy>() {}.getType();
              voyageId = getVoyageId(data);
              loadableStudyStatusId = getLoadableStudyStatusId(data);
              loadableStudyStage =
                  bindDataToEntity(
                      new LoadableStudy(),
                      type,
                      LoadableStudyTables.LOADABLE_STUDY,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.VOYAGE_XID.getColumnName());
              break;
            }
          case comingle_cargo:
            {
              Type type = new TypeToken<ArrayList<CommingleCargo>>() {}.getType();
              commingleCargoStage =
                  bindDataToEntity(
                      new CommingleCargo(),
                      type,
                      LoadableStudyTables.COMINGLE_CARGO,
                      data,
                      dataTransferStage.getId());
              break;
            }
          case loadable_study_port_rotation:
            {
              Type type =
                  new TypeToken<ArrayList<LoadableStudyPortRotationCommunication>>() {}.getType();
              loadableStudyPortRotationStageCommunication =
                  bindDataToEntity(
                      new LoadableStudyPortRotation(),
                      type,
                      LoadableStudyTables.LOADABLE_STUDY_PORT_ROTATION,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.OPERATION_XID.getColumnName());
              break;
            }
          case on_hand_quantity:
            {
              Type type = new TypeToken<ArrayList<OnHandQuantity>>() {}.getType();
              onHandQuantityStage =
                  bindDataToEntity(
                      new OnHandQuantity(),
                      type,
                      LoadableStudyTables.ON_HAND_QUANTITY,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.PORT_ROTATION_XID.getColumnName());
              break;
            }
          case on_board_quantity:
            {
              Type type = new TypeToken<ArrayList<OnBoardQuantity>>() {}.getType();
              onBoardQuantityStage =
                  bindDataToEntity(
                      new OnBoardQuantity(),
                      type,
                      LoadableStudyTables.ON_BOARD_QUANTITY,
                      data,
                      dataTransferStage.getId());
              break;
            }
          case loadable_quantity:
            {
              Type type = new TypeToken<ArrayList<LoadableQuantity>>() {}.getType();
              loadableQuantityStage =
                  bindDataToEntity(
                      new LoadableQuantity(),
                      type,
                      LoadableStudyTables.LOADABLE_QUANTITY,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.PORT_ROTATION_XID.getColumnName());
              break;
            }
          case synoptical_table:
            {
              Type type = new TypeToken<ArrayList<SynopticalTable>>() {}.getType();
              synopticalTableStage =
                  bindDataToEntity(
                      new SynopticalTable(),
                      type,
                      LoadableStudyTables.SYNOPTICAL_TABLE,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.PORT_ROTATION_XID.getColumnName());
              break;
            }
          case json_data:
            {
              Type type = new TypeToken<ArrayList<JsonData>>() {}.getType();
              jsonDataStage =
                  bindDataToEntity(
                      new JsonData(),
                      type,
                      LoadableStudyTables.JSON_DATA,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.JSON_TYPE_XID.getColumnName());
              break;
            }
          case loadable_study_algo_status:
            {
              Type type = new TypeToken<LoadableStudyAlgoStatus>() {}.getType();
              loadableStudyAlgoStatusStage =
                  bindDataToEntity(
                      new LoadableStudyAlgoStatus(),
                      type,
                      LoadableStudyTables.LOADABLE_STUDY_ALGO_STATUS,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_STUDY_STATUS.getColumnName());
              break;
            }
          case loadable_pattern:
            {
              Type type = new TypeToken<ArrayList<LoadablePattern>>() {}.getType();
              loadablePatternStage =
                  bindDataToEntity(
                      new LoadablePattern(),
                      type,
                      LoadableStudyTables.LOADABLE_PATTERN,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLESTUDY_XID.getColumnName());
              break;
            }
          case algo_error_heading:
            {
              Type type = new TypeToken<ArrayList<AlgoErrorHeading>>() {}.getType();
              algoErrorHeadingStage =
                  bindDataToEntity(
                      new AlgoErrorHeading(),
                      type,
                      LoadableStudyTables.ALGO_ERROR_HEADING,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PATTERN_XID.getColumnName());
              break;
            }
          case algo_errors:
            {
              Type type = new TypeToken<ArrayList<AlgoErrors>>() {}.getType();
              algoErrorsStage =
                  bindDataToEntity(
                      new AlgoErrors(),
                      type,
                      LoadableStudyTables.ALGO_ERRORS,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.ERROR_HEADING_XID.getColumnName());
              break;
            }
          case loadable_plan_constraints:
            {
              Type type = new TypeToken<ArrayList<LoadablePlanConstraints>>() {}.getType();
              loadablePlanConstraintsStage =
                  bindDataToEntity(
                      new LoadablePlanConstraints(),
                      type,
                      LoadableStudyTables.LOADABLE_PLAN_CONSTRAINTS,
                      data,
                      dataTransferStage.getId(),
                      "loadable_pattern_xid");
              break;
            }
          case loadable_plan_quantity:
            {
              Type type = new TypeToken<ArrayList<LoadablePlanQuantity>>() {}.getType();
              loadablePlanQuantityStage =
                  bindDataToEntity(
                      new LoadablePlanQuantity(),
                      type,
                      LoadableStudyTables.LOADABLE_PLAN_QUANTITY,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PATTERN_XID.getColumnName());
              break;
            }
          case loadable_plan_commingle_details:
            {
              Type type = new TypeToken<ArrayList<LoadablePlanCommingleDetails>>() {}.getType();
              loadablePlanCommingleDetailsStage =
                  bindDataToEntity(
                      new LoadablePlanCommingleDetails(),
                      type,
                      LoadableStudyTables.LOADABLE_PLAN_COMMINGLE_DETAILS,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PATTERN_XID.getColumnName());
              break;
            }
          case loadable_pattern_cargo_topping_off_sequence:
            {
              Type type =
                  new TypeToken<ArrayList<LoadablePatternCargoToppingOffSequence>>() {}.getType();
              loadablePatternCargoToppingOffSequenceStage =
                  bindDataToEntity(
                      new LoadablePatternCargoToppingOffSequence(),
                      type,
                      LoadableStudyTables.LOADABLE_PATTERN_CARGO_TOPPING_OFF_SEQUENCE,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PATTERN_XID.getColumnName());
              break;
            }
          case loadable_plan_stowage_details:
            {
              Type type = new TypeToken<ArrayList<LoadablePlanStowageDetails>>() {}.getType();
              loadablePlanStowageDetailsStage =
                  bindDataToEntity(
                      new LoadablePlanStowageDetails(),
                      type,
                      LoadableStudyTables.LOADABLE_PLAN_STOWAGE_DETAILS,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PATTERN_XID.getColumnName());
              break;
            }
          case loadable_plan_ballast_details:
            {
              Type type = new TypeToken<ArrayList<LoadablePlanBallastDetails>>() {}.getType();
              loadablePlanBallastDetailsStage =
                  bindDataToEntity(
                      new LoadablePlanBallastDetails(),
                      type,
                      LoadableStudyTables.LOADABLE_PLAN_BALLAST_DETAILS,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PATTERN_XID.getColumnName());
              break;
            }
          case loadable_plan_commingle_details_portwise:
            {
              Type type =
                  new TypeToken<ArrayList<LoadablePlanComminglePortwiseDetails>>() {}.getType();
              loadablePlanComminglePortwiseDetailsStage =
                  bindDataToEntity(
                      new LoadablePlanComminglePortwiseDetails(),
                      type,
                      LoadableStudyTables.LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PATTERN_XID.getColumnName());
              break;
            }
          case cargo_nomination:
            {
              Type type = new TypeToken<ArrayList<CargoNomination>>() {}.getType();
              cargoNominationStage =
                  bindDataToEntity(
                      new CargoNomination(),
                      type,
                      LoadableStudyTables.CARGO_NOMINATION,
                      data,
                      dataTransferStage.getId());
              break;
            }
          case cargo_nomination_operation_details:
            {
              Type type = new TypeToken<ArrayList<CargoNominationPortDetails>>() {}.getType();
              cargoNominationOperationDetailsStage =
                  bindDataToEntity(
                      new CargoNominationPortDetails(),
                      type,
                      LoadableStudyTables.CARGO_NOMINATION_OPERATION_DETAILS,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.CARGO_NOMINATION_XID.getColumnName());
              break;
            }
          case stability_parameters:
            {
              Type type = new TypeToken<ArrayList<StabilityParameters>>() {}.getType();
              stabilityParametersStage =
                  bindDataToEntity(
                      new StabilityParameters(),
                      type,
                      LoadableStudyTables.STABILITY_PARAMETERS,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PATTERN_XID.getColumnName());
              break;
            }
          case loadable_pattern_cargo_details:
            {
              Type type = new TypeToken<ArrayList<LoadablePatternCargoDetails>>() {}.getType();
              loadablePatternCargoDetailsStage =
                  bindDataToEntity(
                      new LoadablePatternCargoDetails(),
                      type,
                      LoadableStudyTables.LOADABLE_PATTERN_CARGO_DETAILS,
                      data,
                      dataTransferStage.getId());
              break;
            }
          case loadable_plan_stowage_ballast_details:
            {
              Type type =
                  new TypeToken<ArrayList<LoadablePlanStowageBallastDetails>>() {}.getType();
              loadablePlanStowageBallastDetailsStage =
                  bindDataToEntity(
                      new LoadablePlanStowageBallastDetails(),
                      type,
                      LoadableStudyTables.LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PLAN_XID.getColumnName());
              break;
            }
          case loadicator_data_for_synoptical_table:
            {
              Type type = new TypeToken<ArrayList<SynopticalTableLoadicatorData>>() {}.getType();
              synopticalTableLoadicatorDataStage =
                  bindDataToEntity(
                      new SynopticalTableLoadicatorData(),
                      type,
                      LoadableStudyTables.LOADICATOR_DATA_FOR_SYNOPTICAL_TABLE,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.SYNOPTICAL_TABLE_XID.getColumnName());
              break;
            }
          case cow_history:
            {
              Type type = new TypeToken<ArrayList<CowHistory>>() {}.getType();
              cowHistoryStage =
                  bindDataToEntity(
                      new CowHistory(),
                      type,
                      LoadableStudyTables.COW_HISTORY,
                      data,
                      dataTransferStage.getId());
              break;
            }
          case discharge_quantity_cargo_details:
            {
              Type type =
                  new TypeToken<
                      ArrayList<DischargePatternQuantityCargoPortwiseDetails>>() {}.getType();
              dischargePatternQuantityCargoPortwiseDetailsStage =
                  bindDataToEntity(
                      new DischargePatternQuantityCargoPortwiseDetails(),
                      type,
                      LoadableStudyTables.DISCHARGE_QUANTITY_CARGO_DETAILS,
                      data,
                      dataTransferStage.getId());
              break;
            }
          case loadable_study_rules:
            {
              Type type = new TypeToken<ArrayList<LoadableStudyRules>>() {}.getType();
              loadableStudyRulesStage =
                  bindDataToEntity(
                      new LoadableStudyRules(),
                      type,
                      LoadableStudyTables.LOADABLE_STUDY_RULES,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_STUDY_XID.getColumnName());
              break;
            }
          case loadable_study_rule_input:
            {
              Type type = new TypeToken<ArrayList<LoadableStudyRuleInput>>() {}.getType();
              loadableStudyRuleInputsStage =
                  bindDataToEntity(
                      new LoadableStudyRuleInput(),
                      type,
                      LoadableStudyTables.LOADABLE_STUDY_RULE_INPUT,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_STUDY_RULE_XID.getColumnName());
              break;
            }
          case loadable_plan_comments:
            {
              Type type = new TypeToken<ArrayList<LoadablePlanComments>>() {}.getType();
              loadablePlanCommentsStage =
                  bindDataToEntity(
                      new LoadablePlanComments(),
                      type,
                      LoadableStudyTables.LOADABLE_PLAN_COMMENTS,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PATTERN_XID.getColumnName());
              break;
            }
          case loadable_plan_stowage_details_temp:
            {
              Type type = new TypeToken<ArrayList<LoadablePlanStowageDetailsTemp>>() {}.getType();
              loadablePlanStowageDetailsTempStage =
                  bindDataToEntity(
                      new LoadablePlanStowageDetailsTemp(),
                      type,
                      LoadableStudyTables.LOADABLE_PLAN_STOWAGE_DETAILS_TEMP,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.STOWAGE_DETAILS_XID.getColumnName(),
                      LOADABLE_STUDY_COLUMNS.BALLAST_DETAILS_XID.getColumnName(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PATTERN_XID.getColumnName(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PLAN_COMMINGLE_DETAILS_XID.getColumnName());
              break;
            }
          case loadable_pattern_algo_status:
            {
              Type type = new TypeToken<LoadablePatternAlgoStatus>() {}.getType();
              loadablePatternAlgoStatusStage =
                  bindDataToEntity(
                      new LoadablePatternAlgoStatus(),
                      type,
                      LoadableStudyTables.LOADABLE_PATTERN_ALGO_STATUS,
                      data,
                      dataTransferStage.getId(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_PATTERN_XID.getColumnName(),
                      LOADABLE_STUDY_COLUMNS.LOADABLE_STUDY_STATUS.getColumnName());
              break;
            }
          case discharge_cow_details:
            {
              Type type = new TypeToken<ArrayList<DischargeStudyCowDetail>>() {}.getType();
              dischargeStudyCowDetailStage =
                  bindDataToEntity(
                      new DischargeStudyCowDetail(),
                      type,
                      LoadableStudyTables.DISCHARGE_COW_DETAILS,
                      data,
                      dataTransferStage.getId());
              break;
            }
          case rule_vessel_mapping:
            {
              ruleVesselMappingStage = data;
              break;
            }
          case rule_vessel_mapping_input:
            {
              ruleVesselMappingInputStage = data;
              break;
            }
          default:
            log.warn(
                "Process Identifier Not Configured: {}", dataTransferStage.getProcessIdentifier());
            break;
        }
      }

      // Save all -save order should not be changed
      try {
        saveVoyage();
        saveLoadableStudy();
        saveCargoNomination();
        saveCommingleCargo();
        saveLoadableStudyPortRotation();
        saveOnHandQuantity();
        saveOnBoardQuantity();
        saveLoadableQuantity();
        saveSynopticalTable();
        saveJsonData();
        saveLoadableStudyAlgoStatus();
        saveLoadablePattern();
        saveAlgoErrorHeading();
        saveAlgoErrors();
        saveLoadablePlanConstraints();
        saveLoadablePlanQuantity();
        saveLoadablePlanCommingleDetails();
        saveLoadablePatternCargoToppingOffSequence();
        saveLoadablePlanStowageDetails();
        saveLoadablePlanBallastDetails();
        saveLoadablePlanCommingleDetailsPortwise();
        saveStabilityParameter();
        saveLoadablePatternCargoDetails();
        saveLoadablePlanStowageBallastDetails();
        saveSynopticalTableLoadicatorData();
        saveCowHistory();
        saveDischargePatternQuantityCargoPortwiseDetails();
        saveLoadableStudyRules();
        saveLoadableStudyRuleInputs();
        saveLoadablePlanComments();
        saveLoadablePlanStowageDetailsTemp();
        saveLoadablePatternAlgoStatus();
        saveDischargeStudyCowDetail();
        saveRuleVesselMapping();
        saveRuleVesselMappingInput();
        saveCommunicationStatusUpdate(processGroupId);
      } catch (ResourceAccessException e) {
        log.error(
            "Save failed loadable study communication data: processId: {}. Sent for retry.",
            processId,
            e);
        updateStatusInExceptionCase(
            idMap.get(currentTableName), processId, retryStatus, e.getMessage());
      } catch (Exception e) {
        log.error("Save failed loadable study communication data: processId: {}", processId, e);
        updateStatusInExceptionCase(
            idMap.get(currentTableName),
            processId,
            StagingStatus.FAILED.getStatus(),
            e.getMessage());
      }
      loadableStudyStagingService.updateStatusCompletedForProcessId(
          processId, StagingStatus.COMPLETED.getStatus());
      log.info("updated status to completed for processId:" + processId);
      // Generate pattern with communicated data at shore
      if (CPDSS_BUILD_ENV_SHORE.equals(env)) {
        try {
          if (processGroupId.equals(MessageTypes.LOADABLESTUDY.getMessageType())) {
            com.cpdss.common.generated.LoadableStudy.AlgoRequest algoRequest =
                com.cpdss.common.generated.LoadableStudy.AlgoRequest.newBuilder()
                    .setLoadableStudyId(loadableStudyStage.getId())
                    .build();
            com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder algoReply =
                com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
            loadablePatternService.generateLoadablePatterns(algoRequest, algoReply);
            log.info("Invoking generateLoadablePatterns method.");
          } else if (processGroupId.equals(MessageTypes.VALIDATEPLAN.getMessageType())) {
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest algoRequest =
                com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.newBuilder()
                    .setLoadablePatternId(loadablePatternStage.get(0).getId())
                    .build();
            com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder algoReply =
                com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
            loadablePlanService.validateLoadablePlan(algoRequest, algoReply);
            log.info("Invoking validateLoadablePlan method.");
          } else if (processGroupId.equals(MessageTypes.DISCHARGESTUDY.getMessageType())) {
            com.cpdss.common.generated.LoadableStudy.AlgoRequest algoRequest =
                com.cpdss.common.generated.LoadableStudy.AlgoRequest.newBuilder()
                    .setLoadableStudyId(loadableStudyStage.getId())
                    .build();
            com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder algoReply =
                com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
            generateDischargeStudyJson.generateDischargePatterns(algoRequest, algoReply);
            log.info("Invoking generateDischargePatterns method in Discharge Study.");
          }
        } catch (IOException e) {
          log.error("Exception calling generate loadable patterns.", e);
          throw new GenericServiceException(
              "Exception calling generate loadable patterns",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR,
              e);
        }
      }
    }
  }

  private Long getVoyageStatus(String json) {
    JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
    return jsonArray.get(0).getAsJsonObject().get("voyage_status").getAsLong();
  }
  // endregion

  // region Save Methods

  /** Method to save loadable study */
  private void saveLoadableStudy() {
    currentTableName =
        LoadableStudyTables.LOADABLE_STUDY.getTable(); // Mandatory to set current table name

    if (isValidStageEntity(loadableStudyStage, currentTableName)) { // Mandatory to validate entity
      Optional<LoadableStudy> optionalLoadableStudy =
          loadableStudyRepository.findById(loadableStudyStage.getId());
      setEntityDocFields(
          loadableStudyStage, optionalLoadableStudy); // Mandatory to set EntityDoc fields

      loadableStudyStage.setVoyage(voyageStage);
      loadableStudyStage.setLoadableStudyStatus(
          loadableStudyStatusRepository.findById(loadableStudyStatusId).orElse(null));

      // Save data
      loadableStudyStage = loadableStudyRepository.save(loadableStudyStage);
      logSavedEntity(loadableStudyStage); // Mandatory to log saved entity
    }
  }

  /** Method to save voyage */
  private void saveVoyage() {
    currentTableName = LoadableStudyTables.VOYAGE.getTable();

    if (isValidStageEntity(voyageStage, currentTableName)) {
      Optional<Voyage> optionalVoyage = voyageRepository.findById(voyageStage.getId());
      setEntityDocFields(voyageStage, optionalVoyage);

      Optional<VoyageStatus> voyageStatus = voyageStatusRepository.findById(voyageStatusId);
      voyageStatus.ifPresent(status -> voyageStage.setVoyageStatus(status));

      // Save data
      voyageStage = voyageRepository.save(voyageStage);
      logSavedEntity(voyageStage);
    } else {
      voyageStage = voyageRepository.findById(voyageId).orElse(null);
    }
  }

  /** Method to save commingle cargo */
  private void saveCommingleCargo() {
    currentTableName = LoadableStudyTables.COMINGLE_CARGO.getTable();

    if (isValidStageEntity(commingleCargoStage, currentTableName)) {
      commingleCargoStage.forEach(
          commingleCargo -> {
            Optional<CommingleCargo> optionalCommingleCargo =
                commingleCargoRepository.findById(commingleCargo.getId());
            setEntityDocFields(commingleCargo, optionalCommingleCargo);
          });

      // Save data
      commingleCargoStage = commingleCargoRepository.saveAll(commingleCargoStage);
      logSavedEntity(commingleCargoStage);
    }
  }

  /** Method to save cargo nomination */
  private void saveCargoNomination() {
    currentTableName = LoadableStudyTables.CARGO_NOMINATION.getTable();

    if (isValidStageEntity(cargoNominationStage, currentTableName)) {
      cargoNominationStage.forEach(
          cargoNomination -> {
            Optional<CargoNomination> optionalCargoNomination =
                cargoNominationRepository.findById(cargoNomination.getId());
            setEntityDocFields(cargoNomination, optionalCargoNomination);

            // setting cargoNomination to CargoNominationPortDetails
            if (!isEmpty(cargoNominationOperationDetailsStage)) {
              cargoNominationOperationDetailsStage.forEach(
                  cargoNominationOperationDetails -> {
                    Optional<CargoNominationPortDetails> optionalCargoNominationOperationDetails =
                        cargoNominationOperationDetailsRepository.findById(
                            cargoNominationOperationDetails.getId());
                    setEntityDocFields(
                        cargoNominationOperationDetails, optionalCargoNominationOperationDetails);

                    if (cargoNomination
                        .getId()
                        .equals(
                            cargoNominationOperationDetails.getCommunicationRelatedEntityId())) {
                      cargoNominationOperationDetails.setCargoNomination(cargoNomination);
                    }
                  });
            }
            // setting cargoNominationPortDetails to cargoNomination
            Set<CargoNominationPortDetails> cargoNominationPortDetails =
                new HashSet<>(cargoNominationOperationDetailsStage);
            cargoNomination.setCargoNominationPortDetails(cargoNominationPortDetails);
          });

      // Save data
      cargoNominationStage = cargoNominationRepository.saveAll(cargoNominationStage);
      logSavedEntity(cargoNominationStage);
    }
  }

  /** Method to save cargo nomination operation details */
  @SuppressWarnings("unused")
  // TODO refactor v2
  private void saveCargoNominationOperationDetails() {
    currentTableName = LoadableStudyTables.CARGO_NOMINATION_OPERATION_DETAILS.getTable();
    if (null == cargoNominationOperationDetailsStage
        || cargoNominationOperationDetailsStage.isEmpty()) {
      log.info("Communication XXXXXXX  CargoNomination Operation Details is empty");
      return;
    }

    cargoNominationOperationDetailsStage.forEach(
        cargoNominationOperationDetails -> {
          Optional<CargoNominationPortDetails> optionalCargoNominationOperationDetails =
              cargoNominationOperationDetailsRepository.findById(
                  cargoNominationOperationDetails.getId());
          setEntityDocFields(
              cargoNominationOperationDetails, optionalCargoNominationOperationDetails);

          Optional<CargoNomination> optionalCargoNomination =
              cargoNominationRepository.findById(
                  cargoNominationOperationDetails.getCommunicationRelatedEntityId());
          optionalCargoNomination.ifPresent(cargoNominationOperationDetails::setCargoNomination);
        });
    cargoNominationOperationDetailsRepository.saveAll(cargoNominationOperationDetailsStage);
    log.info("Communication #######  CargoNomination Operation Details saved");
  }

  /** Method to save loadable study port rotation */
  private void saveLoadableStudyPortRotation() {
    currentTableName = LoadableStudyTables.LOADABLE_STUDY_PORT_ROTATION.getTable();

    if (isValidStageEntity(loadableStudyPortRotationStageCommunication, currentTableName)) {
      for (LoadableStudyPortRotationCommunication lsprStage :
          loadableStudyPortRotationStageCommunication) {
        Optional<LoadableStudyPortRotationCommunication> loadableStudyPortRotation =
            loadableStudyPortRotationCommuncationRepository.findById(lsprStage.getId());
        setEntityDocFields(lsprStage, loadableStudyPortRotation);

        lsprStage.setLoadableStudy(loadableStudyStage);
        Optional<CargoOperation> cargoOperationOpt =
            cargoOperationRepository.findById(lsprStage.getCommunicationRelatedEntityId());
        cargoOperationOpt.ifPresent(lsprStage::setOperation);
      }

      // Save data
      // TODO Check the below implementation
      loadableStudyPortRotationCommuncationRepository.saveAll(
          loadableStudyPortRotationStageCommunication);
      loadableStudyPortRotationStage =
          loadableStudyPortRotationRepository.findByLoadableStudy(loadableStudyStage);
      logSavedEntity(loadableStudyPortRotationStageCommunication);
    }
  }

  /** Method to save on hand quantity */
  private void saveOnHandQuantity() {
    currentTableName = LoadableStudyTables.ON_HAND_QUANTITY.getTable();

    if (isValidStageEntity(onHandQuantityStage, currentTableName)) {
      for (OnHandQuantity ohqStage : onHandQuantityStage) {
        Optional<OnHandQuantity> ohq = onHandQuantityRepository.findById(ohqStage.getId());
        setEntityDocFields(ohqStage, ohq);

        ohqStage.setLoadableStudy(loadableStudyStage);
        for (LoadableStudyPortRotation lspr : loadableStudyPortRotationStage) {
          if (Objects.equals(ohqStage.getCommunicationRelatedEntityId(), lspr.getId())) {
            ohqStage.setPortRotation(lspr);
          }
        }
      }

      // Save data
      onHandQuantityStage = onHandQuantityRepository.saveAll(onHandQuantityStage);
      logSavedEntity(onHandQuantityStage);
    }
  }

  /** Method to save on board quantity */
  private void saveOnBoardQuantity() {
    currentTableName = LoadableStudyTables.ON_BOARD_QUANTITY.getTable();

    if (isValidStageEntity(onBoardQuantityStage, currentTableName)) {
      for (OnBoardQuantity obqStage : onBoardQuantityStage) {
        Optional<OnBoardQuantity> obqOpt = onBoardQuantityRepository.findById(obqStage.getId());
        setEntityDocFields(obqStage, obqOpt);

        obqStage.setLoadableStudy(loadableStudyStage);
      }

      // Save data
      onBoardQuantityStage = onBoardQuantityRepository.saveAll(onBoardQuantityStage);
      logSavedEntity(onBoardQuantityStage);
    }
  }

  /** Method to save loadable quantity */
  private void saveLoadableQuantity() {
    currentTableName = LoadableStudyTables.LOADABLE_QUANTITY.getTable();

    if (isValidStageEntity(loadableQuantityStage, currentTableName)) {
      for (LoadableQuantity lqStage : loadableQuantityStage) {
        Optional<LoadableQuantity> lq = loadableQuantityRepository.findById(lqStage.getId());
        setEntityDocFields(lqStage, lq);

        lqStage.setLoadableStudyXId(loadableStudyStage);
        for (LoadableStudyPortRotation lspr : loadableStudyPortRotationStage) {
          if (Objects.equals(lqStage.getCommunicationRelatedEntityId(), lspr.getId())) {
            lqStage.setLoadableStudyPortRotation(lspr);
          }
        }
      }

      // Save data
      loadableQuantityStage = loadableQuantityRepository.saveAll(loadableQuantityStage);
      logSavedEntity(loadableQuantityStage);
    }
  }

  /** Method to save synoptical table */
  // TODO refactor v2
  private void saveSynopticalTable() {
    currentTableName = LoadableStudyTables.SYNOPTICAL_TABLE.getTable();
    if (null == synopticalTableStage || synopticalTableStage.isEmpty()) {
      log.info("Communication XXXXXXX  synopticalTable is empty");
      return;
    }
    for (SynopticalTable stStage : synopticalTableStage) {
      stStage.setLoadableStudyXId(loadableStudyStage.getId());
      Optional<SynopticalTable> st = synopticalTableRepository.findById(stStage.getId());
      setEntityDocFields(stStage, st);

      Optional<LoadableStudyPortRotation> loadableStudyPortRotationOpt =
          loadableStudyPortRotationRepository.findById(stStage.getCommunicationRelatedEntityId());
      loadableStudyPortRotationOpt.ifPresent(stStage::setLoadableStudyPortRotation);
    }

    synopticalTableStage = synopticalTableRepository.saveAll(synopticalTableStage);
    log.info("Communication #######  synopticalTable saved with id:" + synopticalTableStage);
  }

  /** Method to save json data */
  // TODO refactor v2
  private void saveJsonData() {
    currentTableName = LoadableStudyTables.JSON_DATA.getTable();
    if (null == jsonDataStage || jsonDataStage.isEmpty()) {
      log.info("Communication XXXXXXX  JSON_DATA is empty");
      return;
    }
    jsonDataStage.forEach(
        jsonData -> {
          Optional<JsonType> jsonType =
              jsonTypeRepository.findById(jsonData.getCommunicationRelatedEntityId());
          if (jsonType.isPresent()) {
            jsonData.setJsonTypeXId(jsonType.get());
            Optional<JsonData> jData = jsonDataRepository.findById(jsonData.getId());
            setEntityDocFields(jsonData, jData);
          } else {
            log.info(
                "Communication XXXXXXX  JsonData is not saved , Json Type is not found : "
                    + jsonData.getCommunicationRelatedEntityId());
          }
        });
    jsonDataStage = jsonDataRepository.saveAll(jsonDataStage);
    log.info("Communication #######  JsonData saved");
  }

  /** Method to save loadable study algo status */
  // TODO refactor v2
  private void saveLoadableStudyAlgoStatus() {
    currentTableName = LoadableStudyTables.LOADABLE_STUDY_ALGO_STATUS.getTable();
    if (null == loadableStudyAlgoStatusStage) {
      log.info("Communication XXXXXXX  LOADABLE_STUDY_ALGO_STATUS is empty");
      return;
    }
    Optional<LoadableStudyStatus> loadableStudyStatus =
        loadableStudyStatusRepository.findById(
            loadableStudyAlgoStatusStage.getCommunicationRelatedEntityId());
    if (loadableStudyStatus.isPresent()) {
      loadableStudyAlgoStatusRepository
          .findByLoadableStudyId(loadableStudyStage.getId())
          .ifPresentOrElse(
              loadableStudyAlgoStatus -> {
                loadableStudyAlgoStatus.setProcessId(loadableStudyAlgoStatusStage.getProcessId());
                loadableStudyAlgoStatusStage = loadableStudyAlgoStatus;
              },
              () -> loadableStudyAlgoStatusStage.setLoadableStudy(loadableStudyStage));
      setEntityDocFields(loadableStudyAlgoStatusStage, loadableStudyStatus);
      loadableStudyAlgoStatusStage.setGeneratedFromShore(true);
      loadableStudyAlgoStatusStage.setLoadableStudyStatus(loadableStudyStatus.get());
      loadableStudyAlgoStatusStage =
          loadableStudyAlgoStatusRepository.save(loadableStudyAlgoStatusStage);
      log.info(
          "Communication #######  loadableStudyAlgoStatus saved with id:"
              + loadableStudyAlgoStatusStage.getId());

    } else {
      log.info(
          "Communication XXXXXXX  loadableStudyAlgoStatus is not saved , loadableStudyStatus is not found : "
              + loadableStudyAlgoStatusStage.getCommunicationRelatedEntityId());
    }
  }

  /** Method to save loadable plan */
  @SuppressWarnings("unused")
  // TODO refactor v2
  private void saveLoadablePlan() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN.getTable();
    if (null == loadablePlanStage || loadablePlanStage.isEmpty()) {
      log.info("Communication XXXXXXX  LoadablePlan is empty");
      return;
    }
    for (LoadablePlan lPlan : loadablePlanStage) {
      Optional<LoadablePlan> loadablePlanOptional = loadablePlanRepository.findById(lPlan.getId());
      setEntityDocFields(lPlan, loadablePlanOptional);
      lPlan.setLoadablePatternXId(
          getLoadablePattern(lPlan.getCommunicationRelatedEntityId()).orElse(null));

      loadablePlanRepository.saveAll(loadablePlanStage);
      log.info("Communication #######  LoadablePlan saved");
    }
  }

  /** Method to save loadable pattern */
  private void saveLoadablePattern() {
    currentTableName = LoadableStudyTables.LOADABLE_PATTERN.getTable();

    if (isValidStageEntity(loadablePatternStage, currentTableName)) {
      for (LoadablePattern lp : loadablePatternStage) {
        Optional<LoadablePattern> loadablePatternOptional =
            loadablePatternRepository.findById(lp.getId());
        setEntityDocFields(lp, loadablePatternOptional);

        // Set loadable study
        Optional.ofNullable(loadableStudyStage)
            .ifPresentOrElse(
                loadableStudy -> lp.setLoadableStudy(loadableStudyStage),
                () ->
                    lp.setLoadableStudy(
                        loadableStudyRepository
                            .findById(lp.getCommunicationRelatedEntityId())
                            .orElse(null)));
      }

      // Save data
      loadablePatternStage = loadablePatternRepository.saveAll(loadablePatternStage);
      logSavedEntity(loadablePatternStage);
    }
  }

  /** Method to save algo error heading */
  // TODO refactor v2
  private void saveAlgoErrorHeading() {
    currentTableName = LoadableStudyTables.ALGO_ERROR_HEADING.getTable();
    if (null == algoErrorHeadingStage || algoErrorHeadingStage.isEmpty()) {
      log.info("Communication XXXXXXX  AlgoErrorHeading  is empty");
      return;
    }
    for (AlgoErrorHeading arStage : algoErrorHeadingStage) {
      log.info("Communication +++++++  AlgoErrorHeading  id : " + arStage.getId());
      Optional<AlgoErrorHeading> algoErrorHeadingOptional =
          algoErrorHeadingRepository.findById(arStage.getId());
      setEntityDocFields(arStage, algoErrorHeadingOptional);
      log.info(
          "Communication +++++++  AlgoErrorHeading  CommunicationRelatedEntityId : "
              + arStage.getCommunicationRelatedEntityId());
      if (arStage.getCommunicationRelatedEntityId() != null) {
        arStage.setLoadablePattern(
            getLoadablePattern(arStage.getCommunicationRelatedEntityId()).orElse(null));
      } else {
        arStage.setLoadableStudy(loadableStudyStage);
      }
    }

    algoErrorHeadingStage = algoErrorHeadingRepository.saveAll(algoErrorHeadingStage);
    log.info("Communication #######  AlgoErrorHeading  saved");
  }

  /** Method to save algo errors */
  // TODO refactor v2
  private void saveAlgoErrors() {
    currentTableName = LoadableStudyTables.ALGO_ERRORS.getTable();
    if (null == algoErrorsStage || algoErrorsStage.isEmpty()) {
      log.info("Communication XXXXXXX  ALGO_ERRORS  is empty");
      return;
    }
    for (AlgoErrors ae : algoErrorsStage) {
      Optional<AlgoErrors> algoErrorsOptional = algoErrorsRepository.findById(ae.getId());
      setEntityDocFields(ae, algoErrorsOptional);

      for (AlgoErrorHeading aeh : algoErrorHeadingStage) {
        if (Objects.equals(aeh.getId(), ae.getCommunicationRelatedEntityId())) {
          ae.setAlgoErrorHeading(aeh);
        }
      }
    }
    algoErrorsStage = algoErrorsRepository.saveAll(algoErrorsStage);
    log.info("Communication #######  AlgoErrors  saved");
  }

  /** Method to save loadable plan constraints */
  // TODO refactor v2
  private void saveLoadablePlanConstraints() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_CONSTRAINTS.getTable();
    if (null == loadablePlanConstraintsStage || loadablePlanConstraintsStage.isEmpty()) {
      log.info("Communication XXXXXXX  LoadablePlanConstraints is empty");
      return;
    }
    for (LoadablePlanConstraints lpConstraint : loadablePlanConstraintsStage) {
      Optional<LoadablePlanConstraints> loadablePlanConstraintsOptional =
          loadablePlanConstraintsRespository.findById(lpConstraint.getId());
      setEntityDocFields(lpConstraint, loadablePlanConstraintsOptional);
      lpConstraint.setLoadablePattern(
          getLoadablePattern(lpConstraint.getCommunicationRelatedEntityId()).orElse(null));
    }

    loadablePlanConstraintsStage =
        loadablePlanConstraintsRespository.saveAll(loadablePlanConstraintsStage);
    log.info("Communication #######  LoadablePlanConstraints are saved");
  }

  /** Method to save loadable plan quantity */
  // TODO refactor v2
  private void saveLoadablePlanQuantity() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_QUANTITY.getTable();
    if (null == loadablePlanQuantityStage || loadablePlanQuantityStage.isEmpty()) {
      log.info("Communication XXXXXXX  LoadablePlanQuantity is empty");
      return;
    }
    for (LoadablePlanQuantity lpQuantity : loadablePlanQuantityStage) {
      Optional<LoadablePlanQuantity> loadablePlanConstraintsOptional =
          loadablePlanQuantityRepository.findById(lpQuantity.getId());
      setEntityDocFields(lpQuantity, loadablePlanConstraintsOptional);
      lpQuantity.setLoadablePattern(
          getLoadablePattern(lpQuantity.getCommunicationRelatedEntityId()).orElse(null));
    }

    loadablePlanQuantityStage = loadablePlanQuantityRepository.saveAll(loadablePlanQuantityStage);
    log.info("Communication #######  LoadablePlanQuantity are saved");
  }

  /** Method to save loadable plan commingle details */
  // TODO refactor v2
  private void saveLoadablePlanCommingleDetails() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_COMMINGLE_DETAILS.getTable();
    if (null == loadablePlanCommingleDetailsStage || loadablePlanCommingleDetailsStage.isEmpty()) {
      log.info("Communication XXXXXXX  loadable_plan_commingle_details is empty");
      return;
    }
    // Set relations and version
    for (LoadablePlanCommingleDetails lpCommingleDetail : loadablePlanCommingleDetailsStage) {
      Optional<LoadablePlanCommingleDetails> lpCommingleDetailsOptional =
          loadablePlanCommingleDetailsRepository.findById(lpCommingleDetail.getId());
      LoadablePattern loadablePattern =
          loadablePatternRepository
              .findById(lpCommingleDetail.getCommunicationRelatedEntityId())
              .orElse(null);
      lpCommingleDetail.setLoadablePattern(loadablePattern);
      setEntityDocFields(lpCommingleDetail, lpCommingleDetailsOptional);
    }

    loadablePlanCommingleDetailsStage =
        loadablePlanCommingleDetailsRepository.saveAll(loadablePlanCommingleDetailsStage);
    log.info(
        "Communication #######  loadable_plan_commingle_details are saved. Entries: {}",
        loadablePlanCommingleDetailsStage.size());
  }

  /** Method to save loadable pattern cargo topping off sequence */
  // TODO refactor v2
  private void saveLoadablePatternCargoToppingOffSequence() {
    currentTableName = LoadableStudyTables.LOADABLE_PATTERN_CARGO_TOPPING_OFF_SEQUENCE.getTable();
    if (null == loadablePatternCargoToppingOffSequenceStage
        || loadablePatternCargoToppingOffSequenceStage.isEmpty()) {
      log.info("Communication XXXXXXX  LoadablePatternCargoToppingOffSequence is empty");
      return;
    }
    for (LoadablePatternCargoToppingOffSequence lpCargoToppingOffSequence :
        loadablePatternCargoToppingOffSequenceStage) {
      Optional<LoadablePatternCargoToppingOffSequence> lpCargoToppingOffSequenceOptional =
          loadablePatternCargoToppingOffSequenceRepository.findById(
              lpCargoToppingOffSequence.getId());
      setEntityDocFields(lpCargoToppingOffSequence, lpCargoToppingOffSequenceOptional);
      lpCargoToppingOffSequence.setLoadablePattern(
          getLoadablePattern(lpCargoToppingOffSequence.getCommunicationRelatedEntityId())
              .orElse(null));
    }

    loadablePatternCargoToppingOffSequenceStage =
        loadablePatternCargoToppingOffSequenceRepository.saveAll(
            loadablePatternCargoToppingOffSequenceStage);
    log.info("Communication #######  LoadablePatternCargoToppingOffSequence are saved");
  }

  /** Method to save loadable plan stowage details */
  // TODO refactor v2
  private void saveLoadablePlanStowageDetails() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_STOWAGE_DETAILS.getTable();
    if (null == loadablePlanStowageDetailsStage || loadablePlanStowageDetailsStage.isEmpty()) {
      log.info("Communication XXXXXXX  LoadablePlanStowageDetails is empty");
      return;
    }
    for (LoadablePlanStowageDetails lpStowageDetail : loadablePlanStowageDetailsStage) {
      Optional<LoadablePlanStowageDetails> lpStowageDetailsOptional =
          loadablePlanStowageDetailsRespository.findById(lpStowageDetail.getId());
      setEntityDocFields(lpStowageDetail, lpStowageDetailsOptional);

      LoadablePlan loadablePlan =
          getLoadablePlan(lpStowageDetail.getCommunicationRelatedEntityId()).orElse(null);
      lpStowageDetail.setLoadablePlan(loadablePlan);
      LoadablePattern loadablePattern =
          getLoadablePattern(lpStowageDetail.getCommunicationRelatedEntityId()).orElse(null);
      lpStowageDetail.setLoadablePattern(loadablePattern);
    }

    loadablePlanStowageDetailsStage =
        loadablePlanStowageDetailsRespository.saveAll(loadablePlanStowageDetailsStage);
    log.info("Communication #######  LoadablePlanStowageDetails are saved");
  }

  /** Method to save loadable plan ballast details */
  // TODO refactor v2
  private void saveLoadablePlanBallastDetails() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_BALLAST_DETAILS.getTable();
    if (loadablePlanBallastDetailsStage == null || loadablePlanBallastDetailsStage.isEmpty()) {
      log.info("Communication XXXXXXX  LoadablePlanBallastDetails is empty");
      return;
    }
    for (LoadablePlanBallastDetails lpBallastDetail : loadablePlanBallastDetailsStage) {
      Optional<LoadablePlanBallastDetails> lpBallastDetailsOptional =
          loadablePlanBallastDetailsRepository.findById(lpBallastDetail.getId());
      setEntityDocFields(lpBallastDetail, lpBallastDetailsOptional);
      lpBallastDetail.setLoadablePattern(
          getLoadablePattern(lpBallastDetail.getCommunicationRelatedEntityId()).orElse(null));
    }

    loadablePlanBallastDetailsStage =
        loadablePlanBallastDetailsRepository.saveAll(loadablePlanBallastDetailsStage);
    log.info("Communication #######  LoadablePlanBallastDetails are saved");
  }

  /** Method to save loadable plan commingle details port-wise */
  // TODO refactor v2
  private void saveLoadablePlanCommingleDetailsPortwise() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE.getTable();
    if (null == loadablePlanComminglePortwiseDetailsStage
        || loadablePlanComminglePortwiseDetailsStage.isEmpty()) {
      log.info("Communication XXXXXXX  LoadablePlanComminglePortwiseDetails is empty");
      return;
    }
    for (LoadablePlanComminglePortwiseDetails lpComminglePortwiseDetail :
        loadablePlanComminglePortwiseDetailsStage) {
      Optional<LoadablePlanComminglePortwiseDetails> lpComminglePortwiseDetailsOptional =
          loadablePlanCommingleDetailsPortwiseRepository.findById(
              lpComminglePortwiseDetail.getId());
      setEntityDocFields(lpComminglePortwiseDetail, lpComminglePortwiseDetailsOptional);
      lpComminglePortwiseDetail.setLoadablePattern(
          getLoadablePattern(lpComminglePortwiseDetail.getCommunicationRelatedEntityId())
              .orElse(null));
    }

    loadablePlanComminglePortwiseDetailsStage =
        loadablePlanCommingleDetailsPortwiseRepository.saveAll(
            loadablePlanComminglePortwiseDetailsStage);
    log.info("Communication #######  LoadablePlanComminglePortwiseDetails are saved");
  }

  /** Method to save communication status update table */
  // TODO refactor v2
  private void saveCommunicationStatusUpdate(String messageType) {
    if (MessageTypes.VALIDATEPLAN.getMessageType().equals(messageType)) {
      loadableStudyServiceShore.updateCommunicationStatus(
          UUID.randomUUID().toString(), loadablePatternStage.get(0).getId());
      log.info("Communication #######  communication_status_update table saved");
    }
  }

  /** Method to save stability parameter */
  // TODO refactor v2
  private void saveStabilityParameter() {
    currentTableName = LoadableStudyTables.STABILITY_PARAMETERS.getTable();
    if (null == stabilityParametersStage || stabilityParametersStage.isEmpty()) {
      log.info("Communication XXXXXXX  StabilityParameters is empty");
      return;
    }
    for (StabilityParameters stabilityParameter : stabilityParametersStage) {
      Optional<StabilityParameters> stabilityParametersOptional =
          stabilityParameterRepository.findById(stabilityParameter.getId());
      setEntityDocFields(stabilityParameter, stabilityParametersOptional);
      stabilityParameter.setLoadablePattern(
          getLoadablePattern(stabilityParameter.getCommunicationRelatedEntityId()).orElse(null));
    }

    stabilityParameterRepository.saveAll(stabilityParametersStage);
    log.info("Communication #######  StabilityParameters are saved");
  }

  /** Method to save loadable pattern cargo details */
  // TODO refactor v2
  private void saveLoadablePatternCargoDetails() {
    currentTableName = LoadableStudyTables.LOADABLE_PATTERN_CARGO_DETAILS.getTable();
    if (null == loadablePatternCargoDetailsStage || loadablePatternCargoDetailsStage.isEmpty()) {
      log.info("Communication XXXXXXX  LoadablePatternCargoDetails is empty");
      return;
    }
    for (LoadablePatternCargoDetails lpCargoDetail : loadablePatternCargoDetailsStage) {
      Optional<LoadablePatternCargoDetails> loadablePatternCargoDetailsOptional =
          loadablePatternCargoDetailsRepository.findById(lpCargoDetail.getId());
      setEntityDocFields(lpCargoDetail, loadablePatternCargoDetailsOptional);
    }

    loadablePatternCargoDetailsRepository.saveAll(loadablePatternCargoDetailsStage);
    log.info("Communication #######  LoadablePatternCargoDetails are saved");
  }

  /** Method to save loadable_plan_stowage_ballast_details */
  // TODO refactor v2
  private void saveLoadablePlanStowageBallastDetails() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS.getTable();
    if (null == loadablePlanStowageBallastDetailsStage
        || loadablePlanStowageBallastDetailsStage.isEmpty()) {
      log.info("Communication XXXXXXX  {} is empty", currentTableName);
      return;
    }
    for (LoadablePlanStowageBallastDetails lpStowageBallastDetail :
        loadablePlanStowageBallastDetailsStage) {
      Optional<LoadablePlanStowageBallastDetails> loadablePatternCargoDetailsOptional =
          loadablePlanStowageBallastDetailsRepository.findById(lpStowageBallastDetail.getId());
      setEntityDocFields(lpStowageBallastDetail, loadablePatternCargoDetailsOptional);
      lpStowageBallastDetail.setLoadablePlan(
          getLoadablePlan(lpStowageBallastDetail.getCommunicationRelatedEntityId()).orElse(null));
    }

    loadablePlanStowageBallastDetailsRepository.saveAll(loadablePlanStowageBallastDetailsStage);
    log.info(
        "Communication #######  {} are saved. Entries: {}",
        currentTableName,
        loadablePlanStowageBallastDetailsStage.size());
  }

  /** Method to save synoptical table loadicator data */
  // TODO refactor v2
  private void saveSynopticalTableLoadicatorData() {
    currentTableName = LoadableStudyTables.LOADICATOR_DATA_FOR_SYNOPTICAL_TABLE.getTable();
    if (null == synopticalTableLoadicatorDataStage
        || synopticalTableLoadicatorDataStage.isEmpty()) {
      log.info("Communication XXXXXXX  SynopticalTableLoadicatorData is empty");
      return;
    }
    for (SynopticalTableLoadicatorData sTableLoadicatorData : synopticalTableLoadicatorDataStage) {
      Optional<SynopticalTableLoadicatorData> sTableLoadicatorDataOptional =
          synopticalTableLoadicatorDataRepository.findById(sTableLoadicatorData.getId());
      setEntityDocFields(sTableLoadicatorData, sTableLoadicatorDataOptional);
      for (SynopticalTable st : synopticalTableStage) {
        if (Objects.equals(sTableLoadicatorData.getCommunicationRelatedEntityId(), st.getId())) {
          sTableLoadicatorData.setSynopticalTable(st);
        }
      }
    }
    synopticalTableLoadicatorDataStage =
        synopticalTableLoadicatorDataRepository.saveAll(synopticalTableLoadicatorDataStage);
    log.info("Communication #######  SynopticalTableLoadicatorData are saved");
  }

  /** Method to save CowHistory table */
  // TODO refactor v2
  private void saveCowHistory() {
    currentTableName = LoadableStudyTables.COW_HISTORY.getTable();
    if (null == cowHistoryStage || cowHistoryStage.isEmpty()) {
      log.info("Communication XXXXXXX  CowHistoryData is empty");
      return;
    }
    for (CowHistory cowHistory : cowHistoryStage) {
      Optional<CowHistory> cowHistoryOptional = cowHistoryRepository.findById(cowHistory.getId());
      setEntityDocFields(cowHistory, cowHistoryOptional);
    }
    cowHistoryRepository.saveAll(cowHistoryStage);
    log.info("Communication #######  CowHistory are saved");
  }

  /** Method to save DischargePatternQuantityCargoPortwiseDetails table */
  // TODO refactor v2
  private void saveDischargePatternQuantityCargoPortwiseDetails() {
    currentTableName = LoadableStudyTables.DISCHARGE_QUANTITY_CARGO_DETAILS.getTable();
    if (null == dischargePatternQuantityCargoPortwiseDetailsStage
        || dischargePatternQuantityCargoPortwiseDetailsStage.isEmpty()) {
      log.info("Communication XXXXXXX  DischargePatternQuantityCargoPortwiseDetails is empty");
      return;
    }
    for (DischargePatternQuantityCargoPortwiseDetails dischargenQuantityDetails :
        dischargePatternQuantityCargoPortwiseDetailsStage) {
      Optional<DischargePatternQuantityCargoPortwiseDetails> dischargenQuantityDetailsOptional =
          dischargePatternQuantityCargoPortwiseRepository.findById(
              dischargenQuantityDetails.getId());
      setEntityDocFields(dischargenQuantityDetails, dischargenQuantityDetailsOptional);
    }
    dischargePatternQuantityCargoPortwiseRepository.saveAll(
        dischargePatternQuantityCargoPortwiseDetailsStage);
    log.info("Communication #######  DischargePatternQuantityCargoPortwiseDetails are saved");
  }

  /** Method to save loadable_study_rules table */
  // TODO refactor v2
  private void saveLoadableStudyRules() {
    currentTableName = LoadableStudyTables.LOADABLE_STUDY_RULES.getTable();

    if (!isEmpty(loadableStudyRulesStage)) {
      for (LoadableStudyRules loadableStudyRules : loadableStudyRulesStage) {
        // Set detached entities
        Optional<LoadableStudyRules> loadableStudyRulesOpt =
            loadableStudyRuleRepository.findById(loadableStudyRules.getId());
        loadableStudyRules.setLoadableStudy(loadableStudyStage);

        // Set version
        setEntityDocFields(loadableStudyRules, loadableStudyRulesOpt);
      }

      // Save data
      loadableStudyRuleRepository.saveAll(loadableStudyRulesStage);
      log.info(
          "Communication #######  loadable_study_rules saved. Entries: {}",
          loadableStudyRulesStage.size());
    }
  }

  /**
   * Method to save loadable_study_rule_input table
   *
   * @throws GenericServiceException Exception when rule not found
   */
  // TODO refactor v2
  private void saveLoadableStudyRuleInputs() throws GenericServiceException {
    currentTableName = LoadableStudyTables.LOADABLE_STUDY_RULE_INPUT.getTable();

    if (!isEmpty(loadableStudyRuleInputsStage)) {
      for (LoadableStudyRuleInput loadableStudyRuleInput : loadableStudyRuleInputsStage) {
        // Set detached entities
        LoadableStudyRules loadableStudyRule =
            loadableStudyRuleRepository
                .findById(loadableStudyRuleInput.getCommunicationRelatedEntityId())
                .orElseThrow(
                    () ->
                        new GenericServiceException(
                            String.format(
                                "Loadable study rule not found for Id: %d",
                                loadableStudyRuleInput.getCommunicationRelatedEntityId()),
                            CommonErrorCodes.E_GEN_INTERNAL_ERR,
                            HttpStatusCode.INTERNAL_SERVER_ERROR));
        loadableStudyRuleInput.setLoadableStudyRuleXId(loadableStudyRule);

        // Set version
        Optional<LoadableStudyRuleInput> loadableStudyRuleInputOpt =
            loadableStudyRuleInputRepository.findById(loadableStudyRuleInput.getId());
        setEntityDocFields(loadableStudyRuleInput, loadableStudyRuleInputOpt);
      }

      // Save data
      loadableStudyRuleInputRepository.saveAll(loadableStudyRuleInputsStage);
      log.info(
          "Communication #######  loadable_study_rule_input saved. Entries: {}",
          loadableStudyRuleInputsStage.size());
    }
  }

  /**
   * Method to save loadable_plan_comments table
   *
   * @throws GenericServiceException Exception when pattern not found
   */
  // TODO refactor v2
  private void saveLoadablePlanComments() throws GenericServiceException {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_COMMENTS.getTable();

    if (!isEmpty(loadablePlanCommentsStage)) {
      for (LoadablePlanComments comment : loadablePlanCommentsStage) {
        // Set detached entities
        LoadablePattern loadablePattern =
            loadablePatternRepository
                .findById(comment.getCommunicationRelatedEntityId())
                .orElseThrow(
                    () ->
                        new GenericServiceException(
                            String.format(
                                "Loadable pattern not found for Id: %d",
                                comment.getCommunicationRelatedEntityId()),
                            CommonErrorCodes.E_GEN_INTERNAL_ERR,
                            HttpStatusCode.INTERNAL_SERVER_ERROR));
        comment.setLoadablePattern(loadablePattern);

        // Set version
        Optional<LoadablePlanComments> commentOpt =
            loadablePlanCommentsRepository.findById(comment.getId());
        setEntityDocFields(comment, commentOpt);
      }

      // Save data
      loadablePlanCommentsRepository.saveAll(loadablePlanCommentsStage);
      log.info(
          "Communication #######  loadable_plan_comments saved. Entries: {}",
          loadablePlanCommentsStage.size());
    }
  }

  /** Method to save loadable_plan_stowage_details_temp table */
  // TODO refactor v2
  private void saveLoadablePlanStowageDetailsTemp() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_STOWAGE_DETAILS_TEMP.getTable();

    if (!isEmpty(loadablePlanStowageDetailsTempStage)) {
      for (LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp :
          loadablePlanStowageDetailsTempStage) {

        // Set version
        Optional<LoadablePlanStowageDetailsTemp> loadablePlanStowageDetailsTempOpt =
            loadablePlanStowageDetailsTempRepository.findById(
                loadablePlanStowageDetailsTemp.getId());
        setEntityDocFields(loadablePlanStowageDetailsTemp, loadablePlanStowageDetailsTempOpt);

        // Set stowage details
        loadablePlanStowageDetailsTemp.setLoadablePlanStowageDetails(
            emptyIfNull(loadablePlanStowageDetailsStage).stream()
                .filter(
                    loadablePlanStowageDetails ->
                        loadablePlanStowageDetails
                            .getId()
                            .equals(
                                loadablePlanStowageDetailsTemp
                                    .getCommunicationRelatedIdMap()
                                    .get("stowage_details_xid")))
                .findFirst()
                .orElse(null));

        // Set ballast details
        loadablePlanStowageDetailsTemp.setLoadablePlanBallastDetails(
            emptyIfNull(loadablePlanBallastDetailsStage).stream()
                .filter(
                    loadablePlanBallastDetails ->
                        loadablePlanBallastDetails
                            .getId()
                            .equals(
                                loadablePlanStowageDetailsTemp
                                    .getCommunicationRelatedIdMap()
                                    .get("ballast_details_xid")))
                .findFirst()
                .orElse(null));

        // Set loadable_pattern details
        loadablePlanStowageDetailsTemp.setLoadablePattern(
            emptyIfNull(loadablePatternStage).stream()
                .filter(
                    loadablePattern ->
                        loadablePattern
                            .getId()
                            .equals(
                                loadablePlanStowageDetailsTemp
                                    .getCommunicationRelatedIdMap()
                                    .get("loadable_pattern_xid")))
                .findFirst()
                .orElse(null));

        // Set loadable_plan_commingle details
        loadablePlanStowageDetailsTemp.setLoadablePlanCommingleDetails(
            emptyIfNull(loadablePlanCommingleDetailsStage).stream()
                .filter(
                    loadablePlanCommingleDetails ->
                        loadablePlanCommingleDetails
                            .getId()
                            .equals(
                                loadablePlanStowageDetailsTemp
                                    .getCommunicationRelatedIdMap()
                                    .get("loadable_plan_commingle_details_xid")))
                .findFirst()
                .orElse(null));
      }

      // Save data
      loadablePlanStowageDetailsTempRepository.saveAll(loadablePlanStowageDetailsTempStage);
      log.info(
          "Communication #######  loadable_plan_stowage_details_temp saved. Entries: {}",
          loadablePlanStowageDetailsTempStage.size());
    }
  }

  /** Method to save loadable_pattern_algo_status table */
  // TODO refactor v2
  private void saveLoadablePatternAlgoStatus() {
    currentTableName = LoadableStudyTables.LOADABLE_PATTERN_ALGO_STATUS.getTable();

    if (null == loadablePatternAlgoStatusStage) {
      log.info("Communication XXXXXXX  loadable_pattern_algo_status is empty");
      return;
    }
    Optional<LoadableStudyStatus> loadableStudyStatus =
        loadableStudyStatusRepository.findById(
            loadablePatternAlgoStatusStage
                .getCommunicationRelatedIdMap()
                .get("loadable_study_status"));

    // Update status for recent record
    if (loadableStudyStatus.isPresent()) {
      Optional<LoadablePatternAlgoStatus> lpAlgoStatus =
          loadablePatternAlgoStatusRepository.findByLoadablePatternId(
              loadablePatternAlgoStatusStage
                  .getCommunicationRelatedIdMap()
                  .get("loadabale_pattern_xid"));
      lpAlgoStatus.ifPresentOrElse(
          loadablePatternAlgoStatus -> loadablePatternAlgoStatusStage = loadablePatternAlgoStatus,
          () -> {
            Optional<LoadablePattern> loadablePattern =
                loadablePatternRepository.findById(
                    loadablePatternAlgoStatusStage.getLoadablePattern().getId());
            loadablePatternAlgoStatusStage.setLoadablePattern(loadablePattern.orElse(null));
          });
      setEntityDocFields(loadablePatternAlgoStatusStage, lpAlgoStatus);
      loadablePatternAlgoStatusStage.setGenerateFromShore(true);
      loadablePatternAlgoStatusStage.setLoadableStudyStatus(loadableStudyStatus.get());
      loadablePatternAlgoStatusStage =
          loadablePatternAlgoStatusRepository.save(loadablePatternAlgoStatusStage);
      log.info(
          "Communication #######  loadable_pattern_algo_status saved with id:"
              + loadablePatternAlgoStatusStage.getId());

    } else {
      log.info(
          "Communication XXXXXXX  loadable_pattern_algo_status is not saved , loadableStudyStatus is not found : "
              + loadablePatternAlgoStatusStage
                  .getCommunicationRelatedIdMap()
                  .get("loadable_study_status"));
    }
  }

  /** Method to save discharge_cow_details table */
  // TODO refactor v2
  private void saveDischargeStudyCowDetail() {
    currentTableName = LoadableStudyTables.DISCHARGE_COW_DETAILS.getTable();
    if (!isEmpty(dischargeStudyCowDetailStage)) {
      for (DischargeStudyCowDetail dischargeStudyCowDetail : dischargeStudyCowDetailStage) {
        // Set version
        Optional<DischargeStudyCowDetail> dischargeStudyCowDetailOpt =
            dischargeStudyCowDetailRepository.findById(dischargeStudyCowDetail.getId());
        setEntityDocFields(dischargeStudyCowDetail, dischargeStudyCowDetailOpt);
      }
      // Save data
      dischargeStudyCowDetailRepository.saveAll(dischargeStudyCowDetailStage);
      log.info(
          "Communication #######  discharge_cow_details saved. Entries: {}",
          dischargeStudyCowDetailStage.size());
    }
  }

  /** Method to save rule_vessel_mapping table */
  private void saveRuleVesselMapping() throws GenericServiceException {
    currentTableName = VESSEL_INFO_TABLES.RULE_VESSEL_MAPPING.getTableName();

    if (isValidStageEntity(ruleVesselMappingStage, currentTableName)) {
      updateCommunicationDataVesselInfo(currentTableName, ruleVesselMappingStage);
    }
  }

  /** Method to save rule_vessel_mapping_input table */
  private void saveRuleVesselMappingInput() throws GenericServiceException {
    currentTableName = VESSEL_INFO_TABLES.RULE_VESSEL_MAPPING_INPUT.getTableName();

    if (isValidStageEntity(ruleVesselMappingInputStage, currentTableName)) {
      updateCommunicationDataVesselInfo(currentTableName, ruleVesselMappingInputStage);
    }
  }

  // endregion

  // region Data Binding
  private <T> T bindDataToEntity(
      Object entity,
      Type listType,
      LoadableStudyTables table,
      String jsonData,
      Long dataTransferStageId,
      String... columnsToRemove) {
    try {
      HashMap<String, String> map = loadableStudyStagingService.getAttributeMapping(entity);
      JsonArray jsonArray =
          removeJsonFields(JsonParser.parseString(jsonData).getAsJsonArray(), map, columnsToRemove);

      idMap.put(table.getTable(), dataTransferStageId);
      if (listType.getTypeName().startsWith(ArrayList.class.getTypeName())) {
        return new Gson().fromJson(jsonArray, listType);
      }
      return new Gson().fromJson(jsonArray.get(0).getAsJsonObject(), listType);
    } catch (Exception e) {
      log.error(
          "Communication XXXXXXX Unable to bind the Json to object : "
              + entity.getClass().getName());
      log.error(e);
    }
    return null;
  }
  // endregion

  // region Utils

  /**
   * Method to update status in exception
   *
   * @param id id value
   * @param processId process id value
   * @param status status value
   * @param statusDescription status description
   */
  private void updateStatusInExceptionCase(
      Long id, String processId, String status, String statusDescription) {
    log.error("statusDescription:{}", statusDescription);
    loadableStudyStagingService.updateStatusAndStatusDescriptionForId(
        id, status, statusDescription, LocalDateTime.now());
    loadableStudyStagingService.updateStatusAndModifiedDateTimeForProcessId(
        processId, status, LocalDateTime.now());
  }

  private JsonArray removeJsonFields(JsonArray array, HashMap<String, String> map, String... xIds) {
    List<String> xIdList = xIds == null ? null : List.of(xIds);
    return removeJsonFields(array, map, xIdList);
  }

  private JsonArray removeJsonFields(
      JsonArray array, HashMap<String, String> map, List<String> xIds) {
    JsonArray json = loadableStudyStagingService.getAsEntityJson(map, array);
    JsonArray jsonArray = new JsonArray();
    for (JsonElement jsonElement : json) {
      JsonObject communicationRelatedIdMap = new JsonObject();
      final JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (xIds != null) {
        for (String xId : xIds) {
          if (xIds.size() == 1) {
            jsonObj.add("communicationRelatedEntityId", jsonObj.get(xId));
          } else {
            if (!"null".equals(jsonObj.get(xId).toString())) {
              communicationRelatedIdMap.addProperty(xId, jsonObj.get(xId).getAsLong());
            }
          }
          jsonObj.remove(xId);
        }
      }
      jsonObj.add("communicationRelatedIdMap", communicationRelatedIdMap);
      jsonArray.add(jsonObj);
    }
    return jsonArray;
  }

  private String replaceString(String str) {
    return str.replace("[\"", "[")
        .replace("\"]", "]")
        .replace("\"{", "{")
        .replace("}\"", "}")
        .replace("\\", "");
  }

  private Long getVoyageId(String json) {
    JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
    return jsonArray.get(0).getAsJsonObject().get("voyage_xid").getAsLong();
  }

  private Long getLoadableStudyStatusId(String json) {
    JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
    return jsonArray.get(0).getAsJsonObject().get("loadable_study_status_xid").getAsLong();
  }

  private void clear() {
    idMap.clear();
    loadableStudyStage = null;
    voyageStage = null;
    commingleCargoStage = null;
    cargoNominationStage = null;
    loadableStudyPortRotationStage = null;
    onHandQuantityStage = null;
    onBoardQuantityStage = null;
    loadableQuantityStage = null;
    synopticalTableStage = null;
    jsonDataStage = null;
    loadableStudyAlgoStatusStage = null;
    loadablePatternStage = null;
    algoErrorHeadingStage = null;
    algoErrorsStage = null;
    loadablePlanConstraintsStage = null;
    loadablePlanQuantityStage = null;
    loadablePlanCommingleDetailsStage = null;
    loadablePatternCargoToppingOffSequenceStage = null;
    loadablePlanStowageDetailsStage = null;
    loadablePlanBallastDetailsStage = null;
    loadablePlanComminglePortwiseDetailsStage = null;
    stabilityParametersStage = null;
    loadablePatternCargoDetailsStage = null;
    loadablePlanStowageBallastDetailsStage = null;
    synopticalTableLoadicatorDataStage = null;
    loadablePlanStage = null;
    cargoNominationOperationDetailsStage = null;
    cowHistoryStage = null;
    dischargePatternQuantityCargoPortwiseDetailsStage = null;
    idMap = new HashMap<>();
    voyageId = 0L;
    loadableStudyStatusId = 0L;
    currentTableName = "";
    loadableStudyPortRotationStageCommunication = null;
    loadableStudyRulesStage = null;
    loadableStudyRuleInputsStage = null;
    loadablePlanCommentsStage = null;
    loadablePlanStowageDetailsTempStage = null;
    loadablePatternAlgoStatusStage = null;
    dischargeStudyCowDetailStage = null;
    ruleVesselMappingStage = null;
    ruleVesselMappingInputStage = null;
  }

  /**
   * Method to update communication data in vessel-info service
   *
   * @param tableName tableName value
   * @param dataJson JSON array string to be updated
   * @throws GenericServiceException Exception on update failure
   */
  private void updateCommunicationDataVesselInfo(final String tableName, final String dataJson)
      throws GenericServiceException {
    final Common.CommunicationDataUpdateRequest vesselInfoUpdateRequest =
        Common.CommunicationDataUpdateRequest.newBuilder()
            .setTableName(tableName)
            .setDataJson(dataJson)
            .build();

    final Common.ResponseStatus vesselInfoUpdateResponse =
        vesselInfoGrpcService.updateVesselData(vesselInfoUpdateRequest);
    if (SUCCESS.equals(vesselInfoUpdateResponse.getStatus())) {
      logSavedEntity(currentTableName);
    } else {
      log.error(
          "External save failed. Service: {}, Table: {}, GRPC Response: {}",
          "vessel-info",
          currentTableName,
          vesselInfoUpdateResponse);
      throw new GenericServiceException(
          "External save failed. Table: " + currentTableName,
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  // endregion
}
