/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

// region Import
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.CPDSS_BUILD_ENV_SHORE;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LoadableStudyTables;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.EntityDoc;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.common.utils.StagingStatus;
import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.utility.ProcessIdentifiers;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// endregion

/*
Author(S) - Mahesh KM , Selvy Thomas
Purpose - Communicating Lodable study related tables to ship to shore and vice versa
 */
@Log4j2
@Service
@Transactional
@Scope(value = "prototype")
public class LoadableStudyCommunicationService {

  // region Autowired
  @Autowired private LoadableStudyStagingService loadableStudyStagingService;
  @Autowired private LoadableStudyRepository loadableStudyRepository;
  @Autowired private VoyageRepository voyageRepository;
  @Autowired private CommingleCargoRepository commingleCargoRepository;
  @Autowired private CargoNominationRepository cargoNominationRepository;
  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
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
  // endregion

  // region Declarations
  private LoadableStudy loadableStudyStage = null;
  private Voyage voyageStage = null;
  private List<CommingleCargo> commingleCargoStage = null;
  private List<CargoNomination> cargoNominationStage = null;
  private List<LoadableStudyPortRotation> loadableStudyPortRotationStage = null;
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
  private List<LoadableStudyCommunicationStatus> loadableStudyCommunicationStatusStage = null;
  HashMap<String, Long> idMap = new HashMap<>();
  Long voyageId;
  Long loadableStudyStatusId;
  // endregion

  // region Get Methods
  public void getLoadableStudyStagingData(String status, String env, String taskName)
      throws GenericServiceException {
    log.info("Inside getLoadableStudyStagingData for env:{} and status:{}", env, status);
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

  private Optional<LoadablePlan> getLoadablePlan(Long id) {
    return loadablePlanRepository.findById(id);
  }
  // endregion

  // region Process
  public void processStagingData(
      List<DataTransferStage> dataTransferStages, String env, String retryStatus)
      throws GenericServiceException {
    log.info("Inside getStagingData");
    Map<String, List<DataTransferStage>> dataTransferByProcessId =
        dataTransferStages.stream().collect(Collectors.groupingBy(DataTransferStage::getProcessId));
    log.info("processId group:" + dataTransferByProcessId);
    for (Map.Entry<String, List<DataTransferStage>> entry : dataTransferByProcessId.entrySet()) {
      HashMap<String, Long> idMap = new HashMap<>();
      String processId = entry.getKey();

      loadableStudyStagingService.updateStatusForProcessId(
          processId, StagingStatus.IN_PROGRESS.getStatus());
      log.info(
          "updated status to in_progress for processId:{} and time:{}",
          processId,
          LocalDateTime.now());
      String processGroupId = null;
      Integer arrivalDeparture = null;
      processGroupId = entry.getValue().get(0).getProcessGroupId();
      for (DataTransferStage dataTransferStage : entry.getValue()) {
        Type listType = null;
        String dataTransferString = dataTransferStage.getData();
        String data = null;
        if (dataTransferStage.getProcessIdentifier().equals("loading_information")
            || dataTransferStage.getProcessIdentifier().equals("pyuser")) {
          data = JsonParser.parseString(dataTransferString).getAsJsonArray().get(0).toString();
        } else {
          data = replaceString(dataTransferString);
        }
        switch (ProcessIdentifiers.valueOf(dataTransferStage.getProcessIdentifier())) {
          case voyage:
            {
              Type type = new TypeToken<Voyage>() {}.getType();
              voyageStage =
                  bindDataToEntity(
                      new Voyage(),
                      type,
                      LoadableStudyTables.VOYAGE,
                      data,
                      dataTransferStage.getId(),
                      null);
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
                      "voyage_xid");
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
                      dataTransferStage.getId(),
                      null);
              break;
            }
          case loadable_study_port_rotation:
            {
              Type type = new TypeToken<ArrayList<LoadableStudyPortRotation>>() {}.getType();
              loadableStudyPortRotationStage =
                  bindDataToEntity(
                      new LoadableStudyPortRotation(),
                      type,
                      LoadableStudyTables.LOADABLE_STUDY_PORT_ROTATION,
                      data,
                      dataTransferStage.getId(),
                      "operation_xid");
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
                      "port_rotation_xid");
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
                      dataTransferStage.getId(),
                      null);
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
                      "port_rotation_xid");
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
                      "port_rotation_xid");
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
                      "json_type_xid");
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
                      "loadable_study_status");
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
                      "loadablePatternXId");
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
                      "loadable_pattern_xid");
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
                      "error_heading_xid");
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
                      "loadable_pattern_xid");
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
                      "loadable_pattern_xid");
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
                      "loadable_pattern_xid");
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
                      "loadable_pattern_xid");
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
                      "loadable_pattern_xid");
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
                      "loadable_pattern_xid");
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
                      dataTransferStage.getId(),
                      null);
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
                      "cargo_nomination_xid");
              break;
            }
          case communication_status_update:
            {
              Type type = new TypeToken<ArrayList<LoadableStudyCommunicationStatus>>() {}.getType();
              loadableStudyCommunicationStatusStage =
                  bindDataToEntity(
                      new LoadableStudyCommunicationStatus(),
                      type,
                      LoadableStudyTables.COMMUNICATION_STATUS_UPDATE,
                      data,
                      dataTransferStage.getId(),
                      null);
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
                      "loadable_pattern_xid");
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
                      dataTransferStage.getId(),
                      null);
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
                      null);
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
                      null);
              break;
            }
        }
      }

      // Save all -save order should not be changed
      saveVoyage();
      saveLoadableStudy();
      saveCargoNomination();
      saveCargoNominationOperationDetails();
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
      saveCommunicationStatusUpdate();
      saveStabilityParameter();
      saveLoadablePatternCargoDetails();
      saveLoadablePlanStowageBallastDetails();
      saveSynopticalTableLoadicatorData();

      loadableStudyStagingService.updateStatusCompletedForProcessId(
          processId, StagingStatus.COMPLETED.getStatus());
      log.info("updated status to completed for processId:" + processId);

      // Generate pattern with communicated data at shore
      if (CPDSS_BUILD_ENV_SHORE.equals(env)
          && MessageTypes.LOADABLESTUDY.getMessageType().equals(processGroupId)) {
        com.cpdss.common.generated.LoadableStudy.AlgoRequest algoRequest =
            com.cpdss.common.generated.LoadableStudy.AlgoRequest.newBuilder()
                .setLoadableStudyId(loadableStudyStage.getId())
                .build();
        com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder algoReply =
            com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
        try {
          loadablePatternService.generateLoadablePatterns(algoRequest, algoReply);
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
  // endregion

  // region Save Methods

  /** Method to save loadable study */
  private void saveLoadableStudy() {
    if (loadableStudyStage != null) {
      Optional<LoadableStudy> optionalLoadableStudy =
          loadableStudyRepository.findById(loadableStudyStage.getId());
      loadableStudyStage.setVersion(optionalLoadableStudy.map(EntityDoc::getVersion).orElse(null));
      loadableStudyStage.setVoyage(voyageStage);
      loadableStudyStage.setLoadableStudyStatus(
          loadableStudyStatusRepository.findById(loadableStudyStatusId).orElse(null));

      loadableStudyStage = loadableStudyRepository.save(loadableStudyStage);
      log.info("Communication ####### Loadable Study saved with id:" + loadableStudyStage.getId());
    }
  }

  /** Method to save voyage */
  private void saveVoyage() {
    if (null == voyageStage) {
      log.info("Communication XXXXXXX  Voyage is empty");
      voyageStage = voyageRepository.findById(voyageId).orElse(null);
      return;
    }
    Optional<Voyage> optionalVoyage = voyageRepository.findById(voyageStage.getId());
    voyageStage.setVersion(optionalVoyage.map(EntityDoc::getVersion).orElse(null));

    voyageStage = voyageRepository.save(voyageStage);
    log.info("Communication #######  Voyage saved with id:" + voyageStage.getId());
  }

  /** Method to save commingle cargo */
  private void saveCommingleCargo() {
    if (null == commingleCargoStage) {
      log.info("Communication XXXXXXX  CommingleCargo is empty");
      return;
    }

    commingleCargoStage.forEach(
        commingleCargo -> {
          Optional<CommingleCargo> optionalCommingleCargo =
              commingleCargoRepository.findById(commingleCargo.getId());
          Long version = optionalCommingleCargo.map(EntityDoc::getVersion).orElse(null);
          commingleCargo.setVersion(version);
        });
    commingleCargoStage = commingleCargoRepository.saveAll(commingleCargoStage);
    log.info("Communication #######  CommingleCargo saved with id:" + commingleCargoStage);
  }

  /** Method to save cargo nomination */
  private void saveCargoNomination() {
    if (null == cargoNominationStage) {
      log.info("Communication XXXXXXX  CargoNomination is empty");
      return;
    }

    cargoNominationStage.forEach(
        cargoNomination -> {
          Optional<CargoNomination> optionalCargoNomination =
              cargoNominationRepository.findById(cargoNomination.getId());
          cargoNomination.setVersion(
              optionalCargoNomination.map(EntityDoc::getVersion).orElse(null));
        });
    cargoNominationStage = cargoNominationRepository.saveAll(cargoNominationStage);
    log.info("Communication #######  CargoNomination saved");
  }

  /** Method to save cargo nomination operation details */
  private void saveCargoNominationOperationDetails() {
    if (null == cargoNominationOperationDetailsStage) {
      log.info("Communication XXXXXXX  CargoNomination Operation Details is empty");
      return;
    }

    cargoNominationOperationDetailsStage.forEach(
        cargoNominationOperationDetails -> {
          Optional<CargoNominationPortDetails> optionalCargoNominationOperationDetails =
              cargoNominationOperationDetailsRepository.findById(
                  cargoNominationOperationDetails.getId());
          cargoNominationOperationDetails.setVersion(
              optionalCargoNominationOperationDetails.map(EntityDoc::getVersion).orElse(null));

          Optional<CargoNomination> optionalCargoNomination =
              cargoNominationRepository.findById(
                  cargoNominationOperationDetails.getCommunicationRelatedEntityId());
          optionalCargoNomination.ifPresent(cargoNominationOperationDetails::setCargoNomination);
        });
    cargoNominationOperationDetailsStage =
        cargoNominationOperationDetailsRepository.saveAll(cargoNominationOperationDetailsStage);
    log.info("Communication #######  CargoNomination Operation Details saved");
  }

  /** Method to save loadable study port rotation */
  private void saveLoadableStudyPortRotation() {
    if (null == loadableStudyPortRotationStage) {
      log.info("Communication XXXXXXX  LoadableStudyPortRotation is empty");
      return;
    }
    for (LoadableStudyPortRotation lsprStage : loadableStudyPortRotationStage) {
      Optional<LoadableStudyPortRotation> loadableStudyPortRotation =
          loadableStudyPortRotationRepository.findById(lsprStage.getId());
      lsprStage.setVersion(null);
      loadableStudyPortRotation.ifPresent(
          studyPortRotation -> lsprStage.setVersion(studyPortRotation.getVersion()));
      lsprStage.setLoadableStudy(loadableStudyStage);
      Optional<CargoOperation> cargoOperationOpt =
          cargoOperationRepository.findById(lsprStage.getCommunicationRelatedEntityId());
      cargoOperationOpt.ifPresent(lsprStage::setOperation);
    }

    loadableStudyPortRotationStage =
        loadableStudyPortRotationRepository.saveAll(loadableStudyPortRotationStage);
    log.info("Communication #######  LoadableStudyPortRotation saved ");
  }

  /** Method to save on hand quantity */
  private void saveOnHandQuantity() {
    if (null != onHandQuantityStage && !onBoardQuantityStage.isEmpty()) {
      for (OnHandQuantity ohqStage : onHandQuantityStage) {
        ohqStage.setVersion(null);
        ohqStage.setLoadableStudy(loadableStudyStage);
        Optional<OnHandQuantity> ohq = onHandQuantityRepository.findById(ohqStage.getId());
        ohq.ifPresent(onHandQuantity -> ohqStage.setVersion(onHandQuantity.getVersion()));
        for (LoadableStudyPortRotation lspr : loadableStudyPortRotationStage) {
          Optional<LoadableStudyPortRotation> loadableStudyPortRotationOpt =
              loadableStudyPortRotationRepository.findById(lspr.getId());
          loadableStudyPortRotationOpt.ifPresent(ohqStage::setPortRotation);
        }
      }

      onHandQuantityStage = onHandQuantityRepository.saveAll(onHandQuantityStage);
      log.info("Communication #######  OnHandQuantity saved with id:" + onHandQuantityStage);
    }
  }

  /** Method to save on board quantity */
  private void saveOnBoardQuantity() {
    if (null != onBoardQuantityStage && !onBoardQuantityStage.isEmpty()) {
      for (OnBoardQuantity obqStage : onBoardQuantityStage) {
        obqStage.setVersion(null);
        obqStage.setLoadableStudy(loadableStudyStage);
        Optional<OnBoardQuantity> ohq = onBoardQuantityRepository.findById(obqStage.getId());
        ohq.ifPresent(onBoardQuantity -> obqStage.setVersion(onBoardQuantity.getVersion()));
      }

      onBoardQuantityStage = onBoardQuantityRepository.saveAll(onBoardQuantityStage);
      log.info("Communication #######  onBoardQuantity saved with id:" + onBoardQuantityStage);
    }
  }

  /** Method to save loadable quantity */
  private void saveLoadableQuantity() {
    if (null != loadableQuantityStage && !loadableQuantityStage.isEmpty()) {
      for (LoadableQuantity lqStage : loadableQuantityStage) {
        lqStage.setVersion(null);
        lqStage.setLoadableStudyXId(loadableStudyStage);
        Optional<LoadableQuantity> lq = loadableQuantityRepository.findById(lqStage.getId());
        lq.ifPresent(loadableQuantity -> lqStage.setVersion(loadableQuantity.getVersion()));

        Optional<LoadableStudyPortRotation> loadableStudyPortRotationOpt =
            loadableStudyPortRotationRepository.findById(lqStage.getCommunicationRelatedEntityId());
        loadableStudyPortRotationOpt.ifPresent(lqStage::setLoadableStudyPortRotation);
      }

      loadableQuantityStage = loadableQuantityRepository.saveAll(loadableQuantityStage);
      log.info("Communication #######  loadableQuantity saved with id:" + loadableQuantityStage);
    }
  }

  /** Method to save synoptical table */
  private void saveSynopticalTable() {
    if (null == synopticalTableStage) {
      log.info("Communication XXXXXXX  synopticalTable is empty");
      return;
    }
    for (SynopticalTable stStage : synopticalTableStage) {
      stStage.setVersion(null);
      stStage.setLoadableStudyXId(loadableStudyStage.getId());
      Optional<SynopticalTable> st = synopticalTableRepository.findById(stStage.getId());
      st.ifPresent(synopticalTable -> stStage.setVersion(synopticalTable.getVersion()));

      Optional<LoadableStudyPortRotation> loadableStudyPortRotationOpt =
          loadableStudyPortRotationRepository.findById(stStage.getCommunicationRelatedEntityId());
      loadableStudyPortRotationOpt.ifPresent(stStage::setLoadableStudyPortRotation);
    }

    synopticalTableStage = synopticalTableRepository.saveAll(synopticalTableStage);
    log.info("Communication #######  synopticalTable saved with id:" + synopticalTableStage);
  }

  /** Method to save json data */
  private void saveJsonData() {
    if (null != jsonDataStage && !jsonDataStage.isEmpty()) {
      jsonDataStage.forEach(
          jsonData -> {
            Optional<JsonType> jsonType =
                jsonTypeRepository.findById(jsonData.getCommunicationRelatedEntityId());
            if (jsonType.isPresent()) {
              jsonData.setJsonTypeXId(jsonType.get());
              Optional<JsonData> jData = jsonDataRepository.findById(jsonData.getId());
              jsonData.setVersion(jData.map(EntityDoc::getVersion).orElse(null));
            } else {
              log.info(
                  "Communication XXXXXXX  JsonData is not saved , Json Type is not found : "
                      + jsonData.getCommunicationRelatedEntityId());
            }
          });
      jsonDataStage = jsonDataRepository.saveAll(jsonDataStage);
      log.info("Communication #######  JsonData saved");
    }
  }

  /** Method to save loadable study algo status */
  private void saveLoadableStudyAlgoStatus() {
    if (null != loadableStudyAlgoStatusStage) {
      Optional<LoadableStudyStatus> loadableStudyStatus =
          loadableStudyStatusRepository.findById(
              loadableStudyAlgoStatusStage.getCommunicationRelatedEntityId());
      if (loadableStudyStatus.isPresent()) {
        loadableStudyAlgoStatusStage.setLoadableStudyStatus(loadableStudyStatus.get());
        loadableStudyAlgoStatusStage.setLoadableStudy(loadableStudyStage);
        Optional<LoadableStudyAlgoStatus> algoStatus =
            loadableStudyAlgoStatusRepository.findById(loadableStudyAlgoStatusStage.getId());
        loadableStudyAlgoStatusStage.setVersion(algoStatus.map(EntityDoc::getVersion).orElse(null));

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
  }

  /** Method to save loadable plan */
  private void saveLoadablePlan() {
    if (null == loadablePlanStage) {
      log.info("Communication XXXXXXX  LoadablePlan is empty");
      return;
    }
    for (LoadablePlan lPlan : loadablePlanStage) {
      Optional<LoadablePlan> loadablePlanOptional = loadablePlanRepository.findById(lPlan.getId());
      lPlan.setVersion(loadablePlanOptional.map(EntityDoc::getVersion).orElse(null));
      lPlan.setLoadablePatternXId(
          getLoadablePattern(lPlan.getCommunicationRelatedEntityId()).orElse(null));

      loadablePlanRepository.saveAll(loadablePlanStage);
      log.info("Communication #######  LoadablePlan saved");
    }
  }

  /** Method to save loadable pattern */
  private void saveLoadablePattern() {
    if (null == loadablePatternStage) {
      log.info("Communication XXXXXXX  loadablePattern is empty");
      return;
    }
    for (LoadablePattern lp : loadablePatternStage) {
      Optional<LoadablePattern> loadablePatternOptional =
          loadablePatternRepository.findById(lp.getId());
      lp.setLoadableStudy(loadableStudyStage);
      lp.setVersion(loadablePatternOptional.map(EntityDoc::getVersion).orElse(null));
    }

    loadablePatternStage = loadablePatternRepository.saveAll(loadablePatternStage);
    log.info("Communication #######  loadablePatterns saved");
  }

  /** Method to save algo error heading */
  private void saveAlgoErrorHeading() {
    if (null == algoErrorHeadingStage) {
      log.info("Communication XXXXXXX  AlgoErrorHeading  is empty");
      return;
    }
    for (AlgoErrorHeading arStage : algoErrorHeadingStage) {
      arStage.setLoadableStudy(loadableStudyStage);
      Optional<AlgoErrorHeading> algoErrorHeadingOptional =
          algoErrorHeadingRepository.findById(arStage.getId());
      arStage.setVersion(algoErrorHeadingOptional.map(EntityDoc::getVersion).orElse(null));
      arStage.setLoadablePattern(
          getLoadablePattern(arStage.getCommunicationRelatedEntityId()).orElse(null));
    }

    algoErrorHeadingStage = algoErrorHeadingRepository.saveAll(algoErrorHeadingStage);
    log.info("Communication #######  AlgoErrorHeading  saved");
  }

  /** Method to save algo errors */
  private void saveAlgoErrors() {
    if (algoErrorsStage != null) {
      for (AlgoErrors ae : algoErrorsStage) {
        Optional<AlgoErrors> algoErrorsOptional = algoErrorsRepository.findById(ae.getId());
        ae.setVersion(algoErrorsOptional.map(EntityDoc::getVersion).orElse(null));

        for (AlgoErrorHeading aeh : algoErrorHeadingStage) {
          if (Objects.equals(aeh.getId(), ae.getCommunicationRelatedEntityId())) {
            ae.setAlgoErrorHeading(aeh);
          }
        }
      }

      algoErrorsStage = algoErrorsRepository.saveAll(algoErrorsStage);
      log.info("Communication #######  AlgoErrors  saved");
    } else {
      log.info("Communication XXXXXXX  AlgoErrors  is empty");
    }
  }

  /** Method to save loadable plan constraints */
  private void saveLoadablePlanConstraints() {
    if (null == loadablePlanConstraintsStage) {
      log.info("Communication XXXXXXX  LoadablePlanConstraints is empty");
      return;
    }
    for (LoadablePlanConstraints lpConstraint : loadablePlanConstraintsStage) {
      Optional<LoadablePlanConstraints> loadablePlanConstraintsOptional =
          loadablePlanConstraintsRespository.findById(lpConstraint.getId());
      lpConstraint.setVersion(
          loadablePlanConstraintsOptional.map(EntityDoc::getVersion).orElse(null));
      lpConstraint.setLoadablePattern(
          getLoadablePattern(lpConstraint.getCommunicationRelatedEntityId()).orElse(null));
    }

    loadablePlanConstraintsStage =
        loadablePlanConstraintsRespository.saveAll(loadablePlanConstraintsStage);
    log.info("Communication #######  LoadablePlanConstraints are saved");
  }

  /** Method to save loadable plan quantity */
  private void saveLoadablePlanQuantity() {
    if (null == loadablePlanQuantityStage) {
      log.info("Communication XXXXXXX  LoadablePlanQuantity is empty");
      return;
    }
    for (LoadablePlanQuantity lpQuantity : loadablePlanQuantityStage) {
      Optional<LoadablePlanQuantity> loadablePlanConstraintsOptional =
          loadablePlanQuantityRepository.findById(lpQuantity.getId());
      lpQuantity.setVersion(
          loadablePlanConstraintsOptional.map(EntityDoc::getVersion).orElse(null));
      lpQuantity.setLoadablePattern(
          getLoadablePattern(lpQuantity.getCommunicationRelatedEntityId()).orElse(null));
    }

    loadablePlanQuantityStage = loadablePlanQuantityRepository.saveAll(loadablePlanQuantityStage);
    log.info("Communication #######  LoadablePlanQuantity are saved");
  }

  /** Method to save loadable plan commingle details */
  private void saveLoadablePlanCommingleDetails() {
    if (null == loadablePlanCommingleDetailsStage) {
      log.info("Communication XXXXXXX  LoadablePlanCommingleDetails is empty");
      return;
    }
    for (LoadablePlanCommingleDetails lpCommingDetail : loadablePlanCommingleDetailsStage) {
      Optional<LoadablePlanCommingleDetails> lpCommingDetailsOptional =
          loadablePlanCommingleDetailsRepository.findById(lpCommingDetail.getId());
      lpCommingDetail.setVersion(lpCommingDetailsOptional.map(EntityDoc::getVersion).orElse(null));
    }

    loadablePlanCommingleDetailsStage =
        loadablePlanCommingleDetailsRepository.saveAll(loadablePlanCommingleDetailsStage);
    log.info("Communication #######  LoadablePlanCommingleDetails are saved");
  }

  /** Method to save loadable pattern cargo topping off sequence */
  private void saveLoadablePatternCargoToppingOffSequence() {
    if (null == loadablePatternCargoToppingOffSequenceStage) {
      log.info("Communication XXXXXXX  LoadablePatternCargoToppingOffSequence is empty");
      return;
    }
    for (LoadablePatternCargoToppingOffSequence lpCargoToppingOffSequence :
        loadablePatternCargoToppingOffSequenceStage) {
      Optional<LoadablePatternCargoToppingOffSequence> lpCargoToppingOffSequenceOptional =
          loadablePatternCargoToppingOffSequenceRepository.findById(
              lpCargoToppingOffSequence.getId());
      lpCargoToppingOffSequence.setVersion(
          lpCargoToppingOffSequenceOptional.map(EntityDoc::getVersion).orElse(null));
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
  private void saveLoadablePlanStowageDetails() {
    if (null == loadablePlanStowageDetailsStage || loadablePlanStowageDetailsStage.isEmpty()) {
      log.info("Communication XXXXXXX  LoadablePlanStowageDetails is empty");
      return;
    }
    for (LoadablePlanStowageDetails lpStowageDetail : loadablePlanStowageDetailsStage) {
      Optional<LoadablePlanStowageDetails> lpStowageDetailsOptional =
          loadablePlanStowageDetailsRespository.findById(lpStowageDetail.getId());
      lpStowageDetail.setVersion(lpStowageDetailsOptional.map(EntityDoc::getVersion).orElse(null));

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
  private void saveLoadablePlanBallastDetails() {
    if (loadablePlanBallastDetailsStage == null) {
      log.info("Communication XXXXXXX  LoadablePlanBallastDetails is empty");
      return;
    }
    for (LoadablePlanBallastDetails lpBallastDetail : loadablePlanBallastDetailsStage) {
      Optional<LoadablePlanBallastDetails> lpBallastDetailsOptional =
          loadablePlanBallastDetailsRepository.findById(lpBallastDetail.getId());
      lpBallastDetail.setVersion(lpBallastDetailsOptional.map(EntityDoc::getVersion).orElse(null));
      lpBallastDetail.setLoadablePattern(
          getLoadablePattern(lpBallastDetail.getCommunicationRelatedEntityId()).orElse(null));
    }

    loadablePlanBallastDetailsStage =
        loadablePlanBallastDetailsRepository.saveAll(loadablePlanBallastDetailsStage);
    log.info("Communication #######  LoadablePlanBallastDetails are saved");
  }

  /** Method to save loadable plan commingle details port-wise */
  private void saveLoadablePlanCommingleDetailsPortwise() {
    if (null == loadablePlanComminglePortwiseDetailsStage) {
      log.info("Communication XXXXXXX  LoadablePlanComminglePortwiseDetails is empty");
      return;
    }
    for (LoadablePlanComminglePortwiseDetails lpComminglePortwiseDetail :
        loadablePlanComminglePortwiseDetailsStage) {
      Optional<LoadablePlanComminglePortwiseDetails> lpComminglePortwiseDetailsOptional =
          loadablePlanCommingleDetailsPortwiseRepository.findById(
              lpComminglePortwiseDetail.getId());
      lpComminglePortwiseDetail.setVersion(
          lpComminglePortwiseDetailsOptional.map(EntityDoc::getVersion).orElse(null));
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
  private void saveCommunicationStatusUpdate() {
    if (null != loadableStudyCommunicationStatusStage
        && !loadableStudyCommunicationStatusStage.isEmpty()) {
      loadableStudyCommunicationStatusRepository.saveAll(loadableStudyCommunicationStatusStage);
      log.info("Communication #######  communication_status_update table saved");
    }
  }

  /** Method to save stability parameter */
  private void saveStabilityParameter() {
    if (null == stabilityParametersStage) {
      log.info("Communication XXXXXXX  StabilityParameters is empty");
      return;
    }
    for (StabilityParameters stabilityParameter : stabilityParametersStage) {
      Optional<StabilityParameters> stabilityParametersOptional =
          stabilityParameterRepository.findById(stabilityParameter.getId());
      stabilityParameter.setVersion(
          stabilityParametersOptional.map(EntityDoc::getVersion).orElse(null));
      stabilityParameter.setLoadablePattern(
          getLoadablePattern(stabilityParameter.getCommunicationRelatedEntityId()).orElse(null));
    }

    stabilityParameterRepository.saveAll(stabilityParametersStage);
    log.info("Communication #######  StabilityParameters are saved");
  }

  /** Method to save loadable pattern cargo details */
  private void saveLoadablePatternCargoDetails() {
    if (null == loadablePatternCargoDetailsStage) {
      log.info("Communication XXXXXXX  LoadablePatternCargoDetails is empty");
      return;
    }
    for (LoadablePatternCargoDetails lpCargoDetail : loadablePatternCargoDetailsStage) {
      Optional<LoadablePatternCargoDetails> loadablePatternCargoDetailsOptional =
          loadablePatternCargoDetailsRepository.findById(lpCargoDetail.getId());
      lpCargoDetail.setVersion(
          loadablePatternCargoDetailsOptional.map(EntityDoc::getVersion).orElse(null));
    }

    loadablePatternCargoDetailsRepository.saveAll(loadablePatternCargoDetailsStage);
    log.info("Communication #######  LoadablePatternCargoDetails are saved");
  }

  /** Method to save loadable plan stowage ballast details */
  private void saveLoadablePlanStowageBallastDetails() {
    if (null == loadablePlanStowageBallastDetailsStage) {
      log.info("Communication XXXXXXX  LoadablePlanStowageBallastDetails is empty");
      return;
    }
    for (LoadablePlanStowageBallastDetails lpStowageBallastDetail :
        loadablePlanStowageBallastDetailsStage) {
      Optional<LoadablePlanStowageBallastDetails> loadablePatternCargoDetailsOptional =
          loadablePlanStowageBallastDetailsRepository.findById(lpStowageBallastDetail.getId());
      lpStowageBallastDetail.setVersion(
          loadablePatternCargoDetailsOptional.map(EntityDoc::getVersion).orElse(null));
      lpStowageBallastDetail.setLoadablePlan(
          getLoadablePlan(lpStowageBallastDetail.getCommunicationRelatedEntityId()).orElse(null));
    }

    loadablePlanStowageBallastDetailsRepository.saveAll(loadablePlanStowageBallastDetailsStage);
    log.info("Communication #######  LoadablePatternCargoDetails are saved");
  }

  /** Method to save synoptical table loadicator data */
  private void saveSynopticalTableLoadicatorData() {
    if (null == synopticalTableLoadicatorDataStage) {
      log.info("Communication XXXXXXX  SynopticalTableLoadicatorData is empty");
      return;
    }
    for (SynopticalTableLoadicatorData sTableLoadicatorData : synopticalTableLoadicatorDataStage) {
      Optional<SynopticalTableLoadicatorData> sTableLoadicatorDataOptional =
          synopticalTableLoadicatorDataRepository.findById(sTableLoadicatorData.getId());
      sTableLoadicatorData.setVersion(
          sTableLoadicatorDataOptional.map(EntityDoc::getVersion).orElse(null));
      for (SynopticalTable st : synopticalTableStage) {
        if (Objects.equals(st.getCommunicationRelatedEntityId(), sTableLoadicatorData.getId())) {
          sTableLoadicatorData.setSynopticalTable(st);
        }
      }
    }
    synopticalTableLoadicatorDataStage =
        synopticalTableLoadicatorDataRepository.saveAll(synopticalTableLoadicatorDataStage);
    log.info("Communication #######  SynopticalTableLoadicatorData are saved");
  }

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
    loadableStudyStagingService.updateStatusAndStatusDescriptionForId(
        id, status, statusDescription, LocalDateTime.now());
    loadableStudyStagingService.updateStatusAndModifiedDateTimeForProcessId(
        processId, status, LocalDateTime.now());
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
  private JsonArray removeJsonFields(JsonArray array, HashMap<String, String> map, String... xIds) {
    List<String> xIdList = xIds == null ? null : List.of(xIds);
    return removeJsonFields(array, map, xIdList);
  }

  private JsonArray removeJsonFields(
      JsonArray array, HashMap<String, String> map, List<String> xIds) {
    JsonArray json = loadableStudyStagingService.getAsEntityJson(map, array);
    JsonArray jsonArray = new JsonArray();
    for (JsonElement jsonElement : json) {
      final JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (xIds != null) {
        for (String xId : xIds) {
          if (xIds.size() == 1) {
            jsonObj.add("communicationRelatedEntityId", jsonObj.get(xId));
          }
          jsonObj.remove(xId);
        }
      }
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
  // endregion
}
