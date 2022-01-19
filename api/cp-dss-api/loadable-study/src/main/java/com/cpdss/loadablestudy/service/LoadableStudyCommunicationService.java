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

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.common.utils.StagingStatus;
import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.domain.CommunicationStatus;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.repository.communication.LoadableStudyDataTransferInBoundRepository;
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

  @Autowired LoadableStudyDataTransferInBoundRepository dataTransferInBoundRepository;

  @Autowired LoadableStudyRuleRepository loadableStudyRuleRepository;
  @Autowired LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;
  @Autowired LoadablePlanCommentsRepository loadablePlanCommentsRepository;
  @Autowired LoadablePlanStowageDetailsTempRepository loadablePlanStowageDetailsTempRepository;

  @Autowired private GenerateDischargeStudyJson generateDischargeStudyJson;
  @Autowired private VoyageStatusRepository voyageStatusRepository;
  @Autowired private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @Autowired private DischargeStudyCowDetailRepository dischargeStudyCowDetailRepository;
  @Autowired private LoadableStudyAttachmentsRepository loadableStudyAttachmentsRepository;
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
  private List<LoadableStudyAttachments> loadableStudyAttachmentsStage = null;

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
                            MessageTypes.ALGORESULT.getMessageType(),
                            MessageTypes.LOADABLESTUDY_WITHOUT_ALGO.getMessageType())
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
          case loadable_study_attachments:
            {
              Type type = new TypeToken<ArrayList<LoadableStudyAttachments>>() {}.getType();
              loadableStudyAttachmentsStage =
                  bindDataToEntity(
                      new LoadableStudyAttachments(),
                      type,
                      LoadableStudyTables.LOADABLE_STUDY_ATTACHMENTS,
                      data,
                      dataTransferStage.getId());
              break;
            }
          default:
            log.warn(
                "Process Identifier Not Configured: {}", dataTransferStage.getProcessIdentifier());
            break;
        }
      }

      // Save all -save order should not be changed
      boolean saved = false;
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
        saveLoadableStudyAttachments();
        loadableStudyStagingService.updateStatusCompletedForProcessId(
            processId, StagingStatus.COMPLETED.getStatus());
        log.info("updated status to completed for processId:" + processId);
        saved = true;
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

      if (saved) {
        // Generate pattern with communicated data at shore
        MessageTypes messageType = MessageTypes.getMessageType(processGroupId);

        if (CPDSS_BUILD_ENV_SHORE.equals(env)) {
          try {
            switch (messageType) {
              case LOADABLESTUDY:
                {
                  com.cpdss.common.generated.LoadableStudy.AlgoRequest algoRequest =
                      com.cpdss.common.generated.LoadableStudy.AlgoRequest.newBuilder()
                          .setLoadableStudyId(loadableStudyStage.getId())
                          .build();
                  com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder algoReply =
                      com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
                  loadablePatternService.generateLoadablePatterns(algoRequest, algoReply);
                  log.info("Invoking generateLoadablePatterns method.");
                  break;
                }
              case VALIDATEPLAN:
                {
                  com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest algoRequest =
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest
                          .newBuilder()
                          .setLoadablePatternId(loadablePatternStage.get(0).getId())
                          .build();
                  com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder algoReply =
                      com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
                  loadablePlanService.validateLoadablePlan(algoRequest, algoReply);
                  log.info("Invoking validateLoadablePlan method.");
                  break;
                }
              case DISCHARGESTUDY:
                {
                  com.cpdss.common.generated.LoadableStudy.AlgoRequest algoRequest =
                      com.cpdss.common.generated.LoadableStudy.AlgoRequest.newBuilder()
                          .setLoadableStudyId(loadableStudyStage.getId())
                          .build();
                  com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder algoReply =
                      com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
                  generateDischargeStudyJson.generateDischargePatterns(algoRequest, algoReply);
                  log.info("Invoking generateDischargePatterns method in Discharge Study.");
                  break;
                }
              case LOADABLESTUDY_WITHOUT_ALGO:
                {
                  // Update inbound status - shore
                  dataTransferInBoundRepository.updateStatus(
                      processId, StagingStatus.COMPLETED.getStatus());
                  break;
                }
              default:
                {
                  log.warn(
                      "Trigger after save not configured for MessageType: {}, ENV: {}",
                      messageType.getMessageType(),
                      CPDSS_BUILD_ENV_SHORE);
                  break;
                }
            }
          } catch (IOException e) {
            log.error("Exception calling generate loadable patterns.", e);
            throw new GenericServiceException(
                "Exception calling generate loadable patterns",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR,
                e);
          }
        } else if (CPDSS_BUILD_ENV_SHIP.equals(env)) {
          // Update outbound status
          loadableStudyStagingService.saveDataTransferOutBound(
              processGroupId, loadableStudyStage.getId(), true);

          if (MessageTypes.ALGORESULT.equals(messageType)) {
            // Update communication status table with final state
            loadableStudyCommunicationStatusRepository.updateCommunicationStatus(
                CommunicationStatus.COMPLETED.getId(), false, loadableStudyStage.getId());
          } else if (MessageTypes.PATTERNDETAIL.equals(messageType)) {
            // Update communication status table with final state
            loadableStudyCommunicationStatusRepository.updateCommunicationStatus(
                CommunicationStatus.COMPLETED.getId(), false, loadablePatternStage.get(0).getId());
          }
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
            if (isValidStageEntity(
                cargoNominationOperationDetailsStage,
                LoadableStudyTables.CARGO_NOMINATION_OPERATION_DETAILS.getTable())) {
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

              // setting cargoNominationPortDetails to cargoNomination
              Set<CargoNominationPortDetails> cargoNominationPortDetails =
                  new HashSet<>(cargoNominationOperationDetailsStage);
              cargoNomination.setCargoNominationPortDetails(cargoNominationPortDetails);
            }
          });

      // Save data
      cargoNominationStage = cargoNominationRepository.saveAll(cargoNominationStage);
      logSavedEntity(cargoNominationStage);
    }
  }

  /** Method to save cargo_nomination_operation_details table */
  @SuppressWarnings("unused")
  private void saveCargoNominationOperationDetails() {
    currentTableName = LoadableStudyTables.CARGO_NOMINATION_OPERATION_DETAILS.getTable();

    if (isValidStageEntity(cargoNominationOperationDetailsStage, currentTableName)) {
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

      // Save data
      cargoNominationOperationDetailsStage =
          cargoNominationOperationDetailsRepository.saveAll(cargoNominationOperationDetailsStage);
      logSavedEntity(cargoNominationOperationDetailsStage);
    }
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
      loadableStudyPortRotationStageCommunication =
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
        if (isValidStageEntity(
            loadableStudyPortRotationStage,
            LoadableStudyTables.LOADABLE_STUDY_PORT_ROTATION.getTable())) {
          for (LoadableStudyPortRotation lspr : loadableStudyPortRotationStage) {
            if (Objects.equals(ohqStage.getCommunicationRelatedEntityId(), lspr.getId())) {
              ohqStage.setPortRotation(lspr);
            }
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
        if (isValidStageEntity(
            loadableStudyPortRotationStage,
            LoadableStudyTables.LOADABLE_STUDY_PORT_ROTATION.getTable())) {
          for (LoadableStudyPortRotation lspr : loadableStudyPortRotationStage) {
            if (Objects.equals(lqStage.getCommunicationRelatedEntityId(), lspr.getId())) {
              lqStage.setLoadableStudyPortRotation(lspr);
            }
          }
        }
      }

      // Save data
      loadableQuantityStage = loadableQuantityRepository.saveAll(loadableQuantityStage);
      logSavedEntity(loadableQuantityStage);
    }
  }

  /** Method to save synoptical_table table */
  private void saveSynopticalTable() {
    currentTableName = LoadableStudyTables.SYNOPTICAL_TABLE.getTable();

    if (isValidStageEntity(synopticalTableStage, currentTableName)) {
      for (SynopticalTable stStage : synopticalTableStage) {
        Optional<SynopticalTable> st = synopticalTableRepository.findById(stStage.getId());
        setEntityDocFields(stStage, st);

        stStage.setLoadableStudyXId(loadableStudyStage.getId());
        Optional<LoadableStudyPortRotation> loadableStudyPortRotationOpt =
            loadableStudyPortRotationRepository.findById(stStage.getCommunicationRelatedEntityId());
        loadableStudyPortRotationOpt.ifPresent(stStage::setLoadableStudyPortRotation);
      }

      // Save data
      synopticalTableStage = synopticalTableRepository.saveAll(synopticalTableStage);
      logSavedEntity(synopticalTableStage);
    }
  }

  /**
   * Method to save json_data table
   *
   * @throws GenericServiceException Exception when JSON type not found in table
   */
  private void saveJsonData() throws GenericServiceException {
    currentTableName = LoadableStudyTables.JSON_DATA.getTable();

    if (isValidStageEntity(jsonDataStage, currentTableName)) {
      for (JsonData jsonData : jsonDataStage) {
        JsonType jsonType =
            jsonTypeRepository
                .findById(jsonData.getCommunicationRelatedEntityId())
                .orElseThrow(
                    () -> {
                      log.error(
                          "Master data not found in json_type. Id: {}",
                          jsonData.getCommunicationRelatedEntityId());
                      return new GenericServiceException(
                          "Master data not found in json_type. Id: "
                              + jsonData.getCommunicationRelatedEntityId(),
                          CommonErrorCodes.E_GEN_INTERNAL_ERR,
                          HttpStatusCode.INTERNAL_SERVER_ERROR);
                    });

        Optional<JsonData> jsonDataOpt = jsonDataRepository.findById(jsonData.getId());
        setEntityDocFields(jsonData, jsonDataOpt);

        jsonData.setJsonTypeXId(jsonType);
      }

      // Save data
      jsonDataStage = jsonDataRepository.saveAll(jsonDataStage);
      logSavedEntity(jsonDataStage);
    }
  }

  /**
   * Method to save loadable_study_algo_status table
   *
   * @throws GenericServiceException Exception when master data not found
   */
  private void saveLoadableStudyAlgoStatus() throws GenericServiceException {
    currentTableName = LoadableStudyTables.LOADABLE_STUDY_ALGO_STATUS.getTable();

    if (isValidStageEntity(loadableStudyAlgoStatusStage, currentTableName)) {
      LoadableStudyStatus loadableStudyStatus =
          loadableStudyStatusRepository
              .findById(loadableStudyAlgoStatusStage.getCommunicationRelatedEntityId())
              .orElseThrow(
                  () -> {
                    log.error(
                        "Master data not found in loadable_study_status. Id: {}",
                        loadableStudyAlgoStatusStage.getCommunicationRelatedEntityId());
                    return new GenericServiceException(
                        "Master data not found in loadable_study_status. Id: "
                            + loadableStudyAlgoStatusStage.getCommunicationRelatedEntityId(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
                  });

      Optional<LoadableStudyAlgoStatus> loadableStudyAlgoStatusOpt =
          loadableStudyAlgoStatusRepository.findByLoadableStudyId(loadableStudyStage.getId());
      setEntityDocFields(loadableStudyAlgoStatusStage, loadableStudyAlgoStatusOpt);

      loadableStudyAlgoStatusOpt.ifPresentOrElse(
          loadableStudyAlgoStatus -> {
            loadableStudyAlgoStatus.setProcessId(loadableStudyAlgoStatusStage.getProcessId());
            loadableStudyAlgoStatusStage = loadableStudyAlgoStatus;
          },
          () -> loadableStudyAlgoStatusStage.setLoadableStudy(loadableStudyStage));
      loadableStudyAlgoStatusStage.setGeneratedFromShore(true);
      loadableStudyAlgoStatusStage.setLoadableStudyStatus(loadableStudyStatus);

      // Save data
      loadableStudyAlgoStatusStage =
          loadableStudyAlgoStatusRepository.save(loadableStudyAlgoStatusStage);
      logSavedEntity(loadableStudyAlgoStatusStage);
    }
  }

  /** Method to save loadable_plan table */
  @SuppressWarnings("unused")
  private void saveLoadablePlan() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN.getTable();

    if (isValidStageEntity(loadablePlanStage, currentTableName)) {
      for (LoadablePlan loadablePlan : loadablePlanStage) {
        Optional<LoadablePlan> loadablePlanOpt =
            loadablePlanRepository.findById(loadablePlan.getId());
        setEntityDocFields(loadablePlan, loadablePlanOpt);

        loadablePlan.setLoadablePatternXId(
            getLoadablePattern(loadablePlan.getCommunicationRelatedEntityId()).orElse(null));
      }

      // Save data
      loadablePlanStage = loadablePlanRepository.saveAll(loadablePlanStage);
      logSavedEntity(loadablePlanStage);
    }
  }

  /** Method to save loadable_pattern table */
  private void saveLoadablePattern() {
    currentTableName = LoadableStudyTables.LOADABLE_PATTERN.getTable();

    if (isValidStageEntity(loadablePatternStage, currentTableName)) {
      for (LoadablePattern lp : loadablePatternStage) {
        Optional<LoadablePattern> loadablePatternOpt =
            loadablePatternRepository.findById(lp.getId());
        setEntityDocFields(lp, loadablePatternOpt);

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

  /** Method to save algo_error_heading table */
  private void saveAlgoErrorHeading() {
    currentTableName = LoadableStudyTables.ALGO_ERROR_HEADING.getTable();

    if (isValidStageEntity(algoErrorHeadingStage, currentTableName)) {
      for (AlgoErrorHeading arStage : algoErrorHeadingStage) {
        Optional<AlgoErrorHeading> algoErrorHeadingOpt =
            algoErrorHeadingRepository.findById(arStage.getId());
        setEntityDocFields(arStage, algoErrorHeadingOpt);

        if (null != arStage.getCommunicationRelatedEntityId()) {
          arStage.setLoadablePattern(
              getLoadablePattern(arStage.getCommunicationRelatedEntityId()).orElse(null));
        } else {
          arStage.setLoadableStudy(loadableStudyStage);
        }
      }

      // Save data
      algoErrorHeadingStage = algoErrorHeadingRepository.saveAll(algoErrorHeadingStage);
      logSavedEntity(algoErrorHeadingStage);
    }
  }

  /** Method to save algo_errors table */
  private void saveAlgoErrors() {
    currentTableName = LoadableStudyTables.ALGO_ERRORS.getTable();

    if (isValidStageEntity(algoErrorsStage, currentTableName)) {
      for (AlgoErrors algoErrors : algoErrorsStage) {
        Optional<AlgoErrors> algoErrorsOptional = algoErrorsRepository.findById(algoErrors.getId());
        setEntityDocFields(algoErrors, algoErrorsOptional);

        if (isValidStageEntity(
            algoErrorHeadingStage, LoadableStudyTables.ALGO_ERROR_HEADING.getTable())) {
          for (AlgoErrorHeading algoErrorHeading : algoErrorHeadingStage) {
            if (Objects.equals(
                algoErrorHeading.getId(), algoErrors.getCommunicationRelatedEntityId())) {
              algoErrors.setAlgoErrorHeading(algoErrorHeading);
            }
          }
        }
      }

      // Save data
      algoErrorsStage = algoErrorsRepository.saveAll(algoErrorsStage);
      logSavedEntity(algoErrorsStage);
    }
  }

  /** Method to save loadable_plan_constraints table */
  private void saveLoadablePlanConstraints() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_CONSTRAINTS.getTable();

    if (isValidStageEntity(loadablePlanConstraintsStage, currentTableName)) {
      for (LoadablePlanConstraints lpConstraint : loadablePlanConstraintsStage) {
        Optional<LoadablePlanConstraints> loadablePlanConstraintsOpt =
            loadablePlanConstraintsRespository.findById(lpConstraint.getId());
        setEntityDocFields(lpConstraint, loadablePlanConstraintsOpt);

        lpConstraint.setLoadablePattern(
            getLoadablePattern(lpConstraint.getCommunicationRelatedEntityId()).orElse(null));
      }

      loadablePlanConstraintsStage =
          loadablePlanConstraintsRespository.saveAll(loadablePlanConstraintsStage);
      logSavedEntity(loadablePlanConstraintsStage);
    }
  }

  /** Method to save loadable_plan_quantity table */
  private void saveLoadablePlanQuantity() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_QUANTITY.getTable();

    if (isValidStageEntity(loadablePlanQuantityStage, currentTableName)) {
      for (LoadablePlanQuantity lpQuantity : loadablePlanQuantityStage) {
        Optional<LoadablePlanQuantity> loadablePlanConstraintsOpt =
            loadablePlanQuantityRepository.findById(lpQuantity.getId());
        setEntityDocFields(lpQuantity, loadablePlanConstraintsOpt);

        lpQuantity.setLoadablePattern(
            getLoadablePattern(lpQuantity.getCommunicationRelatedEntityId()).orElse(null));
      }

      // Save data
      loadablePlanQuantityStage = loadablePlanQuantityRepository.saveAll(loadablePlanQuantityStage);
      logSavedEntity(loadablePlanQuantityStage);
    }
  }

  /** Method to save loadable_plan_commingle_details table */
  private void saveLoadablePlanCommingleDetails() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_COMMINGLE_DETAILS.getTable();

    if (isValidStageEntity(loadablePlanCommingleDetailsStage, currentTableName)) {
      for (LoadablePlanCommingleDetails lpCommingleDetail : loadablePlanCommingleDetailsStage) {
        Optional<LoadablePlanCommingleDetails> lpCommingleDetailsOpt =
            loadablePlanCommingleDetailsRepository.findById(lpCommingleDetail.getId());
        setEntityDocFields(lpCommingleDetail, lpCommingleDetailsOpt);

        LoadablePattern loadablePattern =
            loadablePatternRepository
                .findById(lpCommingleDetail.getCommunicationRelatedEntityId())
                .orElse(null);
        lpCommingleDetail.setLoadablePattern(loadablePattern);
      }

      // Save data
      loadablePlanCommingleDetailsStage =
          loadablePlanCommingleDetailsRepository.saveAll(loadablePlanCommingleDetailsStage);
      logSavedEntity(loadablePlanCommingleDetailsStage);
    }
  }

  /** Method to save loadable_pattern_cargo_topping_off_sequence table */
  private void saveLoadablePatternCargoToppingOffSequence() {
    currentTableName = LoadableStudyTables.LOADABLE_PATTERN_CARGO_TOPPING_OFF_SEQUENCE.getTable();

    if (isValidStageEntity(loadablePatternCargoToppingOffSequenceStage, currentTableName)) {
      for (LoadablePatternCargoToppingOffSequence lpCargoToppingOffSequence :
          loadablePatternCargoToppingOffSequenceStage) {
        Optional<LoadablePatternCargoToppingOffSequence> lpCargoToppingOffSequenceOpt =
            loadablePatternCargoToppingOffSequenceRepository.findById(
                lpCargoToppingOffSequence.getId());
        setEntityDocFields(lpCargoToppingOffSequence, lpCargoToppingOffSequenceOpt);

        lpCargoToppingOffSequence.setLoadablePattern(
            getLoadablePattern(lpCargoToppingOffSequence.getCommunicationRelatedEntityId())
                .orElse(null));
      }

      // Save data
      loadablePatternCargoToppingOffSequenceStage =
          loadablePatternCargoToppingOffSequenceRepository.saveAll(
              loadablePatternCargoToppingOffSequenceStage);
      logSavedEntity(loadablePatternCargoToppingOffSequenceStage);
    }
  }

  /** Method to save loadable_plan_stowage_details table */
  private void saveLoadablePlanStowageDetails() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_STOWAGE_DETAILS.getTable();

    if (isValidStageEntity(loadablePlanStowageDetailsStage, currentTableName)) {
      for (LoadablePlanStowageDetails lpStowageDetail : loadablePlanStowageDetailsStage) {
        Optional<LoadablePlanStowageDetails> lpStowageDetailsOpt =
            loadablePlanStowageDetailsRespository.findById(lpStowageDetail.getId());
        setEntityDocFields(lpStowageDetail, lpStowageDetailsOpt);

        LoadablePlan loadablePlan =
            getLoadablePlan(lpStowageDetail.getCommunicationRelatedEntityId()).orElse(null);
        lpStowageDetail.setLoadablePlan(loadablePlan);
        LoadablePattern loadablePattern =
            getLoadablePattern(lpStowageDetail.getCommunicationRelatedEntityId()).orElse(null);
        lpStowageDetail.setLoadablePattern(loadablePattern);
      }

      loadablePlanStowageDetailsStage =
          loadablePlanStowageDetailsRespository.saveAll(loadablePlanStowageDetailsStage);
      logSavedEntity(loadablePlanStowageDetailsStage);
    }
  }

  /** Method to save loadable_plan_ballast_details table */
  private void saveLoadablePlanBallastDetails() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_BALLAST_DETAILS.getTable();

    if (isValidStageEntity(loadablePlanBallastDetailsStage, currentTableName)) {
      for (LoadablePlanBallastDetails lpBallastDetail : loadablePlanBallastDetailsStage) {
        Optional<LoadablePlanBallastDetails> lpBallastDetailsOpt =
            loadablePlanBallastDetailsRepository.findById(lpBallastDetail.getId());
        setEntityDocFields(lpBallastDetail, lpBallastDetailsOpt);

        lpBallastDetail.setLoadablePattern(
            getLoadablePattern(lpBallastDetail.getCommunicationRelatedEntityId()).orElse(null));
      }

      // Save data
      loadablePlanBallastDetailsStage =
          loadablePlanBallastDetailsRepository.saveAll(loadablePlanBallastDetailsStage);
      logSavedEntity(loadablePlanBallastDetailsStage);
    }
  }

  /** Method to save loadable_plan_commingle_details_portwise table */
  private void saveLoadablePlanCommingleDetailsPortwise() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE.getTable();

    if (isValidStageEntity(loadablePlanComminglePortwiseDetailsStage, currentTableName)) {
      for (LoadablePlanComminglePortwiseDetails lpComminglePortwiseDetail :
          loadablePlanComminglePortwiseDetailsStage) {
        Optional<LoadablePlanComminglePortwiseDetails> lpComminglePortwiseDetailsOpt =
            loadablePlanCommingleDetailsPortwiseRepository.findById(
                lpComminglePortwiseDetail.getId());
        setEntityDocFields(lpComminglePortwiseDetail, lpComminglePortwiseDetailsOpt);

        lpComminglePortwiseDetail.setLoadablePattern(
            getLoadablePattern(lpComminglePortwiseDetail.getCommunicationRelatedEntityId())
                .orElse(null));
      }

      // Save data
      loadablePlanComminglePortwiseDetailsStage =
          loadablePlanCommingleDetailsPortwiseRepository.saveAll(
              loadablePlanComminglePortwiseDetailsStage);
      logSavedEntity(loadablePlanComminglePortwiseDetailsStage);
    }
  }

  /** Method to save communication_status_update table */
  private void saveCommunicationStatusUpdate(String messageType) {
    currentTableName = LoadableStudyTables.COMMUNICATION_STATUS_UPDATE.getTable();

    if (MessageTypes.VALIDATEPLAN.getMessageType().equals(messageType)) {
      // Save data
      LoadableStudyCommunicationStatus savedEntity =
          loadableStudyServiceShore.updateCommunicationStatus(
              UUID.randomUUID().toString(), loadablePatternStage.get(0).getId());
      logSavedEntity(savedEntity);
    }
  }

  /** Method to save stability_parameters table */
  private void saveStabilityParameter() {
    currentTableName = LoadableStudyTables.STABILITY_PARAMETERS.getTable();

    if (isValidStageEntity(stabilityParametersStage, currentTableName)) {
      for (StabilityParameters stabilityParameter : stabilityParametersStage) {
        Optional<StabilityParameters> stabilityParametersOpt =
            stabilityParameterRepository.findById(stabilityParameter.getId());
        setEntityDocFields(stabilityParameter, stabilityParametersOpt);

        stabilityParameter.setLoadablePattern(
            getLoadablePattern(stabilityParameter.getCommunicationRelatedEntityId()).orElse(null));
      }

      // Save data
      stabilityParametersStage = stabilityParameterRepository.saveAll(stabilityParametersStage);
      logSavedEntity(stabilityParametersStage);
    }
  }

  /** Method to loadable_pattern_cargo_details table */
  private void saveLoadablePatternCargoDetails() {
    currentTableName = LoadableStudyTables.LOADABLE_PATTERN_CARGO_DETAILS.getTable();

    if (isValidStageEntity(loadablePatternCargoDetailsStage, currentTableName)) {
      for (LoadablePatternCargoDetails lpCargoDetail : loadablePatternCargoDetailsStage) {
        Optional<LoadablePatternCargoDetails> loadablePatternCargoDetailsOpt =
            loadablePatternCargoDetailsRepository.findById(lpCargoDetail.getId());
        setEntityDocFields(lpCargoDetail, loadablePatternCargoDetailsOpt);
      }

      // Save data
      loadablePatternCargoDetailsStage =
          loadablePatternCargoDetailsRepository.saveAll(loadablePatternCargoDetailsStage);
      logSavedEntity(loadablePatternCargoDetailsStage);
    }
  }

  /** Method to save loadable_plan_stowage_ballast_details table */
  private void saveLoadablePlanStowageBallastDetails() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS.getTable();

    if (isValidStageEntity(loadablePlanStowageBallastDetailsStage, currentTableName)) {
      for (LoadablePlanStowageBallastDetails lpStowageBallastDetail :
          loadablePlanStowageBallastDetailsStage) {
        Optional<LoadablePlanStowageBallastDetails> loadablePatternCargoDetailsOptional =
            loadablePlanStowageBallastDetailsRepository.findById(lpStowageBallastDetail.getId());
        setEntityDocFields(lpStowageBallastDetail, loadablePatternCargoDetailsOptional);

        lpStowageBallastDetail.setLoadablePlan(
            getLoadablePlan(lpStowageBallastDetail.getCommunicationRelatedEntityId()).orElse(null));
      }

      // Save data
      loadablePlanStowageBallastDetailsStage =
          loadablePlanStowageBallastDetailsRepository.saveAll(
              loadablePlanStowageBallastDetailsStage);
      logSavedEntity(loadablePlanStowageBallastDetailsStage);
    }
  }

  /** Method to save loadicator_data_for_synoptical_table table */
  private void saveSynopticalTableLoadicatorData() {
    currentTableName = LoadableStudyTables.LOADICATOR_DATA_FOR_SYNOPTICAL_TABLE.getTable();

    if (isValidStageEntity(synopticalTableLoadicatorDataStage, currentTableName)) {
      for (SynopticalTableLoadicatorData sTableLoadicatorData :
          synopticalTableLoadicatorDataStage) {
        Optional<SynopticalTableLoadicatorData> sTableLoadicatorDataOpt =
            synopticalTableLoadicatorDataRepository.findById(sTableLoadicatorData.getId());
        setEntityDocFields(sTableLoadicatorData, sTableLoadicatorDataOpt);

        if (isValidStageEntity(
            synopticalTableStage, LoadableStudyTables.SYNOPTICAL_TABLE.getTable())) {
          for (SynopticalTable st : synopticalTableStage) {
            if (Objects.equals(
                sTableLoadicatorData.getCommunicationRelatedEntityId(), st.getId())) {
              sTableLoadicatorData.setSynopticalTable(st);
            }
          }
        }
      }

      // Save data
      synopticalTableLoadicatorDataStage =
          synopticalTableLoadicatorDataRepository.saveAll(synopticalTableLoadicatorDataStage);
      logSavedEntity(synopticalTableLoadicatorDataStage);
    }
  }

  /** Method to save cow_history table */
  private void saveCowHistory() {
    currentTableName = LoadableStudyTables.COW_HISTORY.getTable();

    if (isValidStageEntity(cowHistoryStage, currentTableName)) {
      for (CowHistory cowHistory : cowHistoryStage) {
        Optional<CowHistory> cowHistoryOpt = cowHistoryRepository.findById(cowHistory.getId());
        setEntityDocFields(cowHistory, cowHistoryOpt);
      }

      // Save data
      cowHistoryStage = cowHistoryRepository.saveAll(cowHistoryStage);
      logSavedEntity(cowHistoryStage);
    }
  }

  /** Method to save discharge_quantity_cargo_details table */
  private void saveDischargePatternQuantityCargoPortwiseDetails() {
    currentTableName = LoadableStudyTables.DISCHARGE_QUANTITY_CARGO_DETAILS.getTable();

    if (isValidStageEntity(dischargePatternQuantityCargoPortwiseDetailsStage, currentTableName)) {
      for (DischargePatternQuantityCargoPortwiseDetails dischargenQuantityDetails :
          dischargePatternQuantityCargoPortwiseDetailsStage) {
        Optional<DischargePatternQuantityCargoPortwiseDetails> dischargenQuantityDetailsOpt =
            dischargePatternQuantityCargoPortwiseRepository.findById(
                dischargenQuantityDetails.getId());
        setEntityDocFields(dischargenQuantityDetails, dischargenQuantityDetailsOpt);
      }

      // Save data
      dischargePatternQuantityCargoPortwiseDetailsStage =
          dischargePatternQuantityCargoPortwiseRepository.saveAll(
              dischargePatternQuantityCargoPortwiseDetailsStage);
      logSavedEntity(dischargePatternQuantityCargoPortwiseDetailsStage);
    }
  }

  /** Method to save loadable_study_rules table */
  private void saveLoadableStudyRules() {
    currentTableName = LoadableStudyTables.LOADABLE_STUDY_RULES.getTable();

    if (isValidStageEntity(loadableStudyRulesStage, currentTableName)) {
      for (LoadableStudyRules loadableStudyRules : loadableStudyRulesStage) {
        Optional<LoadableStudyRules> loadableStudyRulesOpt =
            loadableStudyRuleRepository.findById(loadableStudyRules.getId());
        setEntityDocFields(loadableStudyRules, loadableStudyRulesOpt);

        loadableStudyRules.setLoadableStudy(loadableStudyStage);
      }

      // Save data
      loadableStudyRulesStage = loadableStudyRuleRepository.saveAll(loadableStudyRulesStage);
      logSavedEntity(loadableStudyRulesStage);
    }
  }

  /**
   * Method to save loadable_study_rule_input table
   *
   * @throws GenericServiceException Exception when rule not found
   */
  private void saveLoadableStudyRuleInputs() throws GenericServiceException {
    currentTableName = LoadableStudyTables.LOADABLE_STUDY_RULE_INPUT.getTable();

    if (isValidStageEntity(loadableStudyRuleInputsStage, currentTableName)) {
      for (LoadableStudyRuleInput loadableStudyRuleInput : loadableStudyRuleInputsStage) {
        Optional<LoadableStudyRuleInput> loadableStudyRuleInputOpt =
            loadableStudyRuleInputRepository.findById(loadableStudyRuleInput.getId());
        setEntityDocFields(loadableStudyRuleInput, loadableStudyRuleInputOpt);

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
      }

      // Save data
      loadableStudyRuleInputsStage =
          loadableStudyRuleInputRepository.saveAll(loadableStudyRuleInputsStage);
      logSavedEntity(loadableStudyRuleInputsStage);
    }
  }

  /**
   * Method to save loadable_plan_comments table
   *
   * @throws GenericServiceException Exception when pattern not found
   */
  private void saveLoadablePlanComments() throws GenericServiceException {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_COMMENTS.getTable();

    if (isValidStageEntity(loadablePlanCommentsStage, currentTableName)) {
      for (LoadablePlanComments comment : loadablePlanCommentsStage) {
        Optional<LoadablePlanComments> commentOpt =
            loadablePlanCommentsRepository.findById(comment.getId());
        setEntityDocFields(comment, commentOpt);

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
      }

      // Save data
      loadablePlanCommentsStage = loadablePlanCommentsRepository.saveAll(loadablePlanCommentsStage);
      logSavedEntity(loadablePlanCommentsStage);
    }
  }

  /** Method to save loadable_plan_stowage_details_temp table */
  private void saveLoadablePlanStowageDetailsTemp() {
    currentTableName = LoadableStudyTables.LOADABLE_PLAN_STOWAGE_DETAILS_TEMP.getTable();

    if (isValidStageEntity(loadablePlanStowageDetailsTempStage, currentTableName)) {
      for (LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp :
          loadablePlanStowageDetailsTempStage) {
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
      loadablePlanStowageDetailsTempStage =
          loadablePlanStowageDetailsTempRepository.saveAll(loadablePlanStowageDetailsTempStage);
      logSavedEntity(loadablePlanStowageDetailsTempStage);
    }
  }

  /**
   * Method to save loadable_pattern_algo_status table
   *
   * @throws GenericServiceException Exception when master data not found
   */
  private void saveLoadablePatternAlgoStatus() throws GenericServiceException {
    currentTableName = LoadableStudyTables.LOADABLE_PATTERN_ALGO_STATUS.getTable();

    if (isValidStageEntity(loadablePatternAlgoStatusStage, currentTableName)) {
      LoadableStudyStatus loadableStudyStatus =
          loadableStudyStatusRepository
              .findById(
                  loadablePatternAlgoStatusStage
                      .getCommunicationRelatedIdMap()
                      .get("loadable_study_status"))
              .orElseThrow(
                  () -> {
                    log.error(
                        "Master data not found in loadable_study_status. Id: {}",
                        loadablePatternAlgoStatusStage
                            .getCommunicationRelatedIdMap()
                            .get("loadable_study_status"));
                    return new GenericServiceException(
                        "Master data not found in loadable_study_status. Id: "
                            + loadablePatternAlgoStatusStage
                                .getCommunicationRelatedIdMap()
                                .get("loadable_study_status"),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
                  });

      Optional<LoadablePatternAlgoStatus> lpAlgoStatusOpt =
          loadablePatternAlgoStatusRepository.findByLoadablePatternId(
              loadablePatternAlgoStatusStage
                  .getCommunicationRelatedIdMap()
                  .get("loadabale_pattern_xid"));
      setEntityDocFields(loadablePatternAlgoStatusStage, lpAlgoStatusOpt);

      lpAlgoStatusOpt.ifPresentOrElse(
          loadablePatternAlgoStatus -> loadablePatternAlgoStatusStage = loadablePatternAlgoStatus,
          () -> {
            Optional<LoadablePattern> loadablePattern =
                loadablePatternRepository.findById(
                    loadablePatternAlgoStatusStage.getLoadablePattern().getId());
            loadablePatternAlgoStatusStage.setLoadablePattern(loadablePattern.orElse(null));
          });
      loadablePatternAlgoStatusStage.setGenerateFromShore(true);
      loadablePatternAlgoStatusStage.setLoadableStudyStatus(loadableStudyStatus);

      // Save data
      loadablePatternAlgoStatusStage =
          loadablePatternAlgoStatusRepository.save(loadablePatternAlgoStatusStage);
      logSavedEntity(loadablePatternAlgoStatusStage);
    }
  }

  /** Method to save discharge_cow_details table */
  private void saveDischargeStudyCowDetail() {
    currentTableName = LoadableStudyTables.DISCHARGE_COW_DETAILS.getTable();

    if (isValidStageEntity(dischargeStudyCowDetailStage, currentTableName)) {
      for (DischargeStudyCowDetail dischargeStudyCowDetail : dischargeStudyCowDetailStage) {
        Optional<DischargeStudyCowDetail> dischargeStudyCowDetailOpt =
            dischargeStudyCowDetailRepository.findById(dischargeStudyCowDetail.getId());
        setEntityDocFields(dischargeStudyCowDetail, dischargeStudyCowDetailOpt);
      }

      // Save data
      dischargeStudyCowDetailStage =
          dischargeStudyCowDetailRepository.saveAll(dischargeStudyCowDetailStage);
      logSavedEntity(dischargeStudyCowDetailStage);
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

  /** Method to save loadable_study_attachments table */
  private void saveLoadableStudyAttachments() {
    currentTableName = LoadableStudyTables.LOADABLE_STUDY_ATTACHMENTS.getTable();

    if (isValidStageEntity(loadableStudyAttachmentsStage, currentTableName)) {
      for (LoadableStudyAttachments loadableStudyAttachment : loadableStudyAttachmentsStage) {
        Optional<LoadableStudyAttachments> loadableStudyAttachmentOpt =
            loadableStudyAttachmentsRepository.findById(loadableStudyAttachment.getId());
        setEntityDocFields(loadableStudyAttachment, loadableStudyAttachmentOpt);
      }

      // Save data
      loadableStudyAttachmentsRepository.saveAll(loadableStudyAttachmentsStage);
      logSavedEntity(loadableStudyAttachmentsStage);
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
    loadableStudyAttachmentsStage = null;
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
