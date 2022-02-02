/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import static com.cpdss.common.communication.CommunicationConstants.*;
import static com.cpdss.common.communication.CommunicationConstants.SUCCESS;
import static com.cpdss.common.communication.StagingService.setEntityDocFields;
import static com.cpdss.loadingplan.common.LoadingPlanConstants.CPDSS_BUILD_ENV_SHIP;
import static com.cpdss.loadingplan.utility.LoadingPlanConstants.*;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.common.utils.StagingStatus;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.domain.CommunicationStatus;
import com.cpdss.loadingplan.domain.VoyageActivate;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.repository.communication.LoadingPlanDataTransferInBoundRepository;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.loadicator.UllageUpdateLoadicatorService;
import com.cpdss.loadingplan.utility.ProcessIdentifiers;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;

@Log4j2
@Service
@Transactional
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoadingPlanCommunicationService {

  @Value("${cpdss.build.env}")
  private String env;

  @Value("${loadingplan.communication.timelimit}")
  private Long timeLimit;

  // region Autowired
  @Autowired private LoadingPlanStagingService loadingPlanStagingService;
  @Autowired private LoadingInformationRepository loadingInformationRepository;

  @Autowired
  private LoadingInformationCommunicationRepository loadingInformationCommunicationRepository;

  @Autowired private CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @Autowired private LoadingBerthDetailsRepository loadingBerthDetailsRepository;
  @Autowired private LoadingDelayRepository loadingDelayRepository;
  @Autowired private LoadingMachineryInUseRepository loadingMachineryInUseRepository;
  @Autowired private LoadingPlanAlgoService loadingPlanAlgoService;
  @Autowired private UllageUpdateLoadicatorService ullageUpdateLoadicatorService;

  @Autowired
  private PortLoadingPlanStabilityParametersRepository portLoadingPlanStabilityParametersRepository;

  @Autowired private PortLoadingPlanRobDetailsRepository portLoadingPlanRobDetailsRepository;
  @Autowired private LoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @Autowired private LoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;
  @Autowired private LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;

  @Autowired
  private PortLoadingPlanBallastDetailsRepository portLoadingPlanBallastDetailsRepository;

  @Autowired
  private PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastTempDetailsRepository;

  @Autowired
  private PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;

  @Autowired
  private PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageTempDetailsRepository;

  @Autowired private LoadingSequenceRepository loadingSequenceRepository;
  @Autowired private LoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;

  @Autowired
  private LoadingSequenceStabiltyParametersRepository loadingSequenceStabiltyParametersRepository;

  @Autowired
  private LoadingPlanStabilityParametersRepository loadingPlanStabilityParametersRepository;

  @Autowired
  private PortLoadingPlanCommingleTempDetailsRepository
      portLoadingPlanCommingleTempDetailsRepository;

  @Autowired
  private PortLoadingPlanCommingleDetailsRepository portLoadingPlanCommingleDetailsRepository;

  @Autowired private BillOfLandingRepository billOfLandingRepository;

  @Autowired private StageOffsetRepository stageOffsetRepository;
  @Autowired private StageDurationRepository stageDurationRepository;
  @Autowired private LoadingInformationStatusRepository loadingInfoStatusRepository;
  @Autowired private PyUserRepository pyUserRepository;
  @Autowired private LoadingInformationAlgoStatusRepository loadingInformationAlgoStatusRepository;
  @Autowired private BallastOperationRepository ballastOperationRepository;
  @Autowired private EductionOperationRepository eductionOperationRepository;
  @Autowired private CargoLoadingRateRepository cargoLoadingRateRepository;
  @Autowired private PortTideDetailsRepository portTideDetailsRepository;
  @Autowired private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @Autowired private AlgoErrorsRepository algoErrorsRepository;
  @Autowired private LoadingInstructionRepository loadingInstructionRepository;
  @Autowired private LoadingDelayReasonRepository loadingDelayReasonRepository;
  @Autowired private ReasonForDelayRepository reasonForDelayRepository;
  @Autowired private LoadingPlanCommingleDetailsRepository loadingPlanCommingleDetailsRepository;

  @Autowired
  private LoadingPlanCommunicationStatusRepository loadingPlanCommunicationStatusRepository;

  @Autowired private LoadingRuleRepository loadingRuleRepository;
  @Autowired private LoadingRuleInputRepository loadingRuleInputRepository;
  @Autowired private LoadingPlanDataTransferInBoundRepository dataTransferInBoundRepository;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("envoyReaderService")
  private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

  @GrpcClient("envoyWriterService")
  private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  // endregion

  HashMap<String, Long> idMap = new HashMap<>();
  LoadingInformation loadingInformation = null;
  StageOffset stageOffset = null;
  StageDuration stageDuration = null;
  LoadingInformationStatus loadingInformationStatus = null;
  LoadingInformationStatus arrivalStatus = null;
  LoadingInformationStatus departureStatus = null;
  List<CargoToppingOffSequence> cargoToppingOffSequences = null;
  List<LoadingBerthDetail> loadingBerthDetails = null;
  List<LoadingDelay> loadingDelays = null;
  List<LoadingDelayReason> loadingDelayReasons = null;
  List<LoadingMachineryInUse> loadingMachineryInUses = null;
  VoyageActivate voyageActivate = null;
  List<LoadingSequence> loadingSequencesList = null;
  List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsList = null;
  List<PortLoadingPlanStabilityParameters> portLoadingPlanStabilityParamList = null;
  List<PortLoadingPlanRobDetails> portLoadingPlanRobDetailsList = null;
  List<LoadingPlanBallastDetails> loadingPlanBallastDetailsList = null;
  List<LoadingPlanRobDetails> loadingPlanRobDetailsList = null;
  List<PortLoadingPlanBallastDetails> portLoadingPlanBallastDetailsList = null;
  List<PortLoadingPlanBallastTempDetails> portLoadingPlanBallastTempDetailsList = null;
  List<PortLoadingPlanStowageDetails> portLoadingPlanStowageDetailsList = null;
  List<PortLoadingPlanStowageTempDetails> portLoadingPlanStowageTempDetailsList = null;
  List<LoadingPlanStowageDetails> loadingPlanStowageDetailsList = null;
  List<LoadingSequenceStabilityParameters> loadingSequenceStabilityParametersList = null;
  List<LoadingPlanStabilityParameters> loadingPlanStabilityParametersList = null;
  List<BallastOperation> ballastOperationList = null;
  List<EductionOperation> eductionOperationList = null;
  List<CargoLoadingRate> cargoLoadingRateList = null;
  List<LoadingPlanCommingleDetails> loadingPlanCommingleDetailsList = null;
  List<PortLoadingPlanCommingleTempDetails> portLoadingPlanCommingleTempDetailsList = null;
  List<PortLoadingPlanCommingleDetails> portLoadingPlanCommingleDetailsList = null;
  List<BillOfLanding> billOfLandingList = null;
  PyUser pyUser = null;
  String loadablePattern = null;
  String loadicatorDataForSynoptical = null;
  String jsonData = null;
  String synopticalData = null;
  String loadablePlanStowageBallastDetailsData = null;
  String loadablePatternCargoDetailsData = null;
  String loadablePlanComminglePortwiseDetailsData = null;
  String onBoardQuantityData = null;
  String onHandQuantityData = null;
  String loadableStudyPortRotationData = null;
  List<PortTideDetail> portTideDetailList = null;
  List<AlgoErrorHeading> algoErrorHeadings = null;
  List<AlgoErrors> algoErrors = null;
  List<LoadingInstruction> loadingInstructions = null;
  LoadingInformationAlgoStatus loadingInformationAlgoStatus = null;
  String current_table_name = "";
  List<LoadingRule> loadingRules = null;
  List<LoadingRuleInput> loadingRuleInputs = null;
  // region Communication get data
  public void getDataFromCommunication(
      Map<String, String> taskReqParams, EnumSet<MessageTypes> messageTypesEnum)
      throws GenericServiceException {
    log.info("Inside getDataFromCommunication messageTypes:" + messageTypesEnum);
    for (MessageTypes messageType : messageTypesEnum) {
      try {
        String messageTypeGet = messageType.getMessageType();
        log.info("Inside getDataFromCommunication messageType : " + messageTypeGet);
        EnvoyReader.EnvoyReaderResultReply erReply =
            getResultFromEnvoyReader(taskReqParams, messageTypeGet);
        if (!SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
          throw new GenericServiceException(
              "Failed to get Result from Communication Server for: " + messageTypeGet,
              erReply.getResponseStatus().getCode(),
              HttpStatusCode.valueOf(Integer.valueOf(erReply.getResponseStatus().getCode())));
        }
        if (erReply != null && !erReply.getPatternResultJson().isEmpty()) {
          log.info("Data received from envoy reader for: " + messageType);
          saveLoadingPlanIntoStagingTable(erReply);
        }
      } catch (GenericServiceException e) {
        throw new GenericServiceException(
            e.getMessage(),
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR,
            e);
      }
    }
  }
  // endregion

  // region Stage table save
  private void saveLoadingPlanIntoStagingTable(EnvoyReader.EnvoyReaderResultReply erReply) {
    try {
      String jsonResult = erReply.getPatternResultJson();
      loadingPlanStagingService.save(jsonResult);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when save into the Loadingplan datatransfer table", e);

    } catch (ResourceAccessException e) {
      log.info("ResourceAccessException when save into the Loadingplan datatransfer table  ", e);

    } catch (Exception e) {
      log.error("Exception when save into the Loadingplan datatransfer table  ", e);
    }
  }
  // endregion

  // region Results From Envoy Reader
  private EnvoyReader.EnvoyReaderResultReply getResultFromEnvoyReader(
      Map<String, String> taskReqParams, String messageType) {
    log.info("Inside getResultFromEnvoyReaderwith messageType:{}", messageType);
    EnvoyReader.EnvoyReaderResultRequest.Builder request =
        EnvoyReader.EnvoyReaderResultRequest.newBuilder();
    request.setMessageType(messageType);
    request.setClientId(taskReqParams.get("ClientId"));
    request.setShipId(taskReqParams.get("ShipId"));
    return this.envoyReaderGrpcService.getResultFromCommServer(request.build());
  }
  // endregion

  // region pass reguest payload to Envoy writer
  public EnvoyWriter.WriterReply passRequestPayloadToEnvoyWriter(
      String requestJson, Long VesselId, String messageType) throws GenericServiceException {
    VesselInfo.VesselDetail vesselReply = this.getVesselDetailsForEnvoy(VesselId);
    EnvoyWriter.EnvoyWriterRequest.Builder writerRequest =
        EnvoyWriter.EnvoyWriterRequest.newBuilder();
    writerRequest.setJsonPayload(requestJson);
    writerRequest.setClientId(vesselReply.getName());
    writerRequest.setMessageType(messageType);
    writerRequest.setImoNumber(vesselReply.getImoNumber());
    return this.envoyWriterService.getCommunicationServer(writerRequest.build());
  }
  // endregion

  // region get vesseel details from envoy
  public VesselInfo.VesselDetail getVesselDetailsForEnvoy(Long vesselId)
      throws GenericServiceException {
    VesselInfo.VesselIdRequest replyBuilder =
        VesselInfo.VesselIdRequest.newBuilder().setVesselId(vesselId).build();
    VesselInfo.VesselIdResponse vesselResponse =
        this.vesselInfoGrpcService.getVesselInfoByVesselId(replyBuilder);
    if (!SUCCESS.equalsIgnoreCase(vesselResponse.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling vessel service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return vesselResponse.getVesselDetail();
  }
  // endregion

  // region Get Loading plan stage
  public void getLoadingPlanStagingData(String status, String env, String taskName)
      throws GenericServiceException {
    log.info("Inside getLoadingPlanStagingData for env:{} and status:{}", env, status);
    String retryStatus = getRetryStatus(status);
    List<DataTransferStage> dataTransferStagesWithStatus = getDataTransferWithStatus(status);
    List<DataTransferStage> dataTransferStages =
        dataTransferStagesWithStatus.stream()
            .filter(
                dataTransfer ->
                    Arrays.asList(
                            MessageTypes.LOADINGPLAN_SAVE.getMessageType(),
                            MessageTypes.LOADINGPLAN.getMessageType(),
                            MessageTypes.LOADINGPLAN_ALGORESULT.getMessageType(),
                            MessageTypes.LOADINGPLAN_WITHOUT_ALGO.getMessageType())
                        .contains(dataTransfer.getProcessGroupId()))
            .collect(Collectors.toList());
    log.info("DataTransferStages in LOADING_DATA_UPDATE task:" + dataTransferStages);
    if (dataTransferStages != null && !dataTransferStages.isEmpty()) {
      getStagingData(dataTransferStages, env, retryStatus);
    }
  }
  // endregion

  // region Get Loading plan stage
  public void getUllageUpdateStagingData(String status, String env, String taskName)
      throws GenericServiceException {
    log.info("Inside getUllageUpdateStagingData for env:{} and status:{}", env, status);
    String retryStatus = getRetryStatus(status);
    List<DataTransferStage> dataTransferStagesWithStatus = getDataTransferWithStatus(status);
    List<DataTransferStage> dataTransferStages =
        dataTransferStagesWithStatus.stream()
            .filter(
                dataTransfer ->
                    Arrays.asList(
                            MessageTypes.ULLAGE_UPDATE.getMessageType(),
                            MessageTypes.ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT.getMessageType(),
                            MessageTypes.ULLAGE_UPDATE_LOADICATOR_ON_ALGORESULT.getMessageType())
                        .contains(dataTransfer.getProcessGroupId()))
            .collect(Collectors.toList());
    log.info("DataTransferStages in ULLAGE_DATA_UPDATE task:" + dataTransferStages);
    if (dataTransferStages != null && !dataTransferStages.isEmpty()) {
      getStagingData(dataTransferStages, env, retryStatus);
    }
  }
  // endregion

  // region get retry status
  private String getRetryStatus(String status) {
    String retryStatus = StagingStatus.RETRY.getStatus();
    if (status.equals(retryStatus)) {
      retryStatus = StagingStatus.FAILED.getStatus();
    }
    return retryStatus;
  }
  // endregion

  // region get data transfer with status
  private List<DataTransferStage> getDataTransferWithStatus(String status) {
    List<DataTransferStage> dataTransferStagesWithStatus = null;
    if (status.equals(StagingStatus.IN_PROGRESS.getStatus())) {
      dataTransferStagesWithStatus =
          loadingPlanStagingService.getAllWithStatusAndTime(
              status, LocalDateTime.now().minusMinutes(2));
    } else {
      dataTransferStagesWithStatus = loadingPlanStagingService.getAllWithStatus(status);
    }
    return dataTransferStagesWithStatus;
  }
  // endregion

  // region save data to tables from stage table
  public void getStagingData(
      List<DataTransferStage> dataTransferStages, String env, String retryStatus)
      throws GenericServiceException {
    log.info("Inside getStagingData");
    Map<String, List<DataTransferStage>> dataTransferByProcessId =
        dataTransferStages.stream().collect(Collectors.groupingBy(DataTransferStage::getProcessId));
    log.info("processId group:" + dataTransferByProcessId);
    for (Map.Entry<String, List<DataTransferStage>> entry : dataTransferByProcessId.entrySet()) {
      // Resetting all the global variables
      clear();
      String processId = entry.getKey();
      loadingPlanStagingService.updateStatusForProcessId(
          processId, StagingStatus.IN_PROGRESS.getStatus());
      log.info(
          "updated status to in_progress for processId:{} and time:{}",
          processId,
          LocalDateTime.now());
      String processGroupId = entry.getValue().get(0).getProcessGroupId();

      if (MessageTypes.LOADINGPLAN.getMessageType().equals(processGroupId)
          || MessageTypes.LOADINGPLAN_WITHOUT_ALGO.getMessageType().equals(processGroupId)) {
        if (!loadingPlanStagingService.dependantProcessIsCompleted(
            processId, CommunicationModule.LOADING_PLAN.getModuleName())) {
          loadingPlanStagingService.updateStatusForProcessId(
              processId, StagingStatus.READY_TO_PROCESS.getStatus());
          continue;
        }
      }

      for (DataTransferStage dataTransferStage : entry.getValue()) {
        Type listType = null;
        String dataTransferString = dataTransferStage.getData();
        String data = null;
        if (dataTransferStage.getProcessIdentifier().equals("loading_information")
            || dataTransferStage.getProcessIdentifier().equals("pyuser")
            || dataTransferStage.getProcessIdentifier().equals("voyage")) {
          data = JsonParser.parseString(dataTransferString).getAsJsonArray().get(0).getAsString();
        } else {
          data =
              dataTransferString
                  .replace("[\"", "[")
                  .replace("\"]", "]")
                  .replace("\"{", "{")
                  .replace("}\"", "}")
                  .replace("\\", "");
        }
        switch (ProcessIdentifiers.valueOf(dataTransferStage.getProcessIdentifier())) {
          case loading_information:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingInformation());
              JsonArray array = new JsonArray();
              array.add((JsonElement) JsonParser.parseString(data));
              JsonArray jsonArray =
                  removeJsonFields(
                      array,
                      map,
                      "stages_min_amount_xid",
                      "stages_duration_xid",
                      "loading_status_xid",
                      "arrival_status_xid",
                      "departure_status_xid");
              loadingInformation = new Gson().fromJson(jsonArray.get(0), LoadingInformation.class);
              idMap.put(
                  LoadingPlanTables.LOADING_INFORMATION.getTable(), dataTransferStage.getId());
              break;
            }
          case stages_min_amount:
            {
              listType = new TypeToken<ArrayList<StageOffset>>() {}.getType();
              List<StageOffset> listStageOffset = new Gson().fromJson(data, listType);
              stageOffset = listStageOffset.get(0);
              idMap.put(LoadingPlanTables.STAGES_MIN_AMOUNT.getTable(), dataTransferStage.getId());
              break;
            }
          case stages_duration:
            {
              listType = new TypeToken<ArrayList<StageDuration>>() {}.getType();
              List<StageDuration> listStageDuration = new Gson().fromJson(data, listType);
              stageDuration = listStageDuration.get(0);
              idMap.put(LoadingPlanTables.STAGES_DURATION.getTable(), dataTransferStage.getId());
              break;
            }
          case loading_information_status:
            {
              listType = new TypeToken<ArrayList<LoadingInformationStatus>>() {}.getType();
              List<LoadingInformationStatus> listLoadingInformationStatus =
                  new Gson().fromJson(data, listType);
              loadingInformationStatus = listLoadingInformationStatus.get(0);
              idMap.put(
                  LoadingPlanTables.LOADING_INFORMATION_STATUS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loading_information_arrival_status:
            {
              listType = new TypeToken<ArrayList<LoadingInformationStatus>>() {}.getType();
              List<LoadingInformationStatus> listLoadingInformationStatus =
                  new Gson().fromJson(data, listType);
              arrivalStatus = listLoadingInformationStatus.get(0);
              idMap.put(
                  LoadingPlanTables.LOADING_INFORMATION_ARRIVAL_STATUS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loading_information_departure_status:
            {
              listType = new TypeToken<ArrayList<LoadingInformationStatus>>() {}.getType();
              List<LoadingInformationStatus> listLoadingInformationStatus =
                  new Gson().fromJson(data, listType);
              departureStatus = listLoadingInformationStatus.get(0);
              idMap.put(
                  LoadingPlanTables.LOADING_INFORMATION_DEPARTURE_STATUS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case cargo_topping_off_sequence:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new CargoToppingOffSequence());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_xid");
              listType = new TypeToken<ArrayList<CargoToppingOffSequence>>() {}.getType();
              cargoToppingOffSequences = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.CARGO_TOPPING_OFF_SEQUENCE.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loading_berth_details:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingBerthDetail());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_xid");
              listType = new TypeToken<ArrayList<LoadingBerthDetail>>() {}.getType();
              loadingBerthDetails = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.LOADING_BERTH_DETAILS.getTable(), dataTransferStage.getId());
              break;
            }
          case loading_delay:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingDelay());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_xid");
              listType = new TypeToken<ArrayList<LoadingDelay>>() {}.getType();
              loadingDelays = new Gson().fromJson(jsonArray, listType);
              idMap.put(LoadingPlanTables.LOADING_DELAY.getTable(), dataTransferStage.getId());
              break;
            }
          case loading_delay_reason:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingDelayReason());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_delay_xid",
                      "reason_xid");
              listType = new TypeToken<ArrayList<LoadingDelayReason>>() {}.getType();
              loadingDelayReasons = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.LOADING_DELAY_REASON.getTable(), dataTransferStage.getId());
              break;
            }
          case loading_machinary_in_use:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingMachineryInUse());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_xid");
              listType = new TypeToken<ArrayList<LoadingMachineryInUse>>() {}.getType();
              loadingMachineryInUses = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.LOADING_MACHINARY_IN_USE.getTable(), dataTransferStage.getId());
              break;
            }
          case loading_sequence:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingSequence());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_information_xid");
              listType = new TypeToken<ArrayList<LoadingSequence>>() {}.getType();
              loadingSequencesList = new Gson().fromJson(jsonArray, listType);
              idMap.put(LoadingPlanTables.LOADING_SEQUENCE.getTable(), dataTransferStage.getId());
              break;
            }
          case loading_plan_portwise_details:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingPlanPortWiseDetails());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_sequences_xid");
              listType = new TypeToken<ArrayList<LoadingPlanPortWiseDetails>>() {}.getType();
              loadingPlanPortWiseDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.LOADING_PLAN_PORTWISE_DETAILS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case port_loading_plan_stability_parameters:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(
                      new PortLoadingPlanStabilityParameters());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_information_xid");
              listType =
                  new TypeToken<ArrayList<PortLoadingPlanStabilityParameters>>() {}.getType();
              portLoadingPlanStabilityParamList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.PORT_LOADING_PLAN_STABILITY_PARAMETERS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case port_loading_plan_rob_details:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new PortLoadingPlanRobDetails());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(), map, null);
              listType = new TypeToken<ArrayList<PortLoadingPlanRobDetails>>() {}.getType();
              portLoadingPlanRobDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.PORT_LOADING_PLAN_ROB_DETAILS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loading_plan_ballast_details:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingPlanBallastDetails());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_plan_portwise_details_xid");
              listType = new TypeToken<ArrayList<LoadingPlanBallastDetails>>() {}.getType();
              loadingPlanBallastDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.LOADING_PLAN_BALLAST_DETAILS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loading_plan_rob_details:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingPlanRobDetails());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_plan_portwise_details_xid");
              listType = new TypeToken<ArrayList<LoadingPlanRobDetails>>() {}.getType();
              loadingPlanRobDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.LOADING_PLAN_ROB_DETAILS.getTable(), dataTransferStage.getId());
              break;
            }
          case port_loading_plan_stowage_ballast_details:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(
                      new PortLoadingPlanBallastDetails());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_information_xid");
              listType = new TypeToken<ArrayList<PortLoadingPlanBallastDetails>>() {}.getType();
              portLoadingPlanBallastDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case port_loading_plan_stowage_ballast_details_temp:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(
                      new PortLoadingPlanBallastTempDetails());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(), map, null);
              listType = new TypeToken<ArrayList<PortLoadingPlanBallastTempDetails>>() {}.getType();
              portLoadingPlanBallastTempDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS_TEMP.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case port_loading_plan_stowage_details:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(
                      new PortLoadingPlanStowageDetails());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_information_xid");
              listType = new TypeToken<ArrayList<PortLoadingPlanStowageDetails>>() {}.getType();
              portLoadingPlanStowageDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case port_loading_plan_stowage_details_temp:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(
                      new PortLoadingPlanStowageTempDetails());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(), map, null);
              listType = new TypeToken<ArrayList<PortLoadingPlanStowageTempDetails>>() {}.getType();
              portLoadingPlanStowageTempDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS_TEMP.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loading_plan_stowage_details:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingPlanStowageDetails());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_plan_portwise_details_xid");
              listType = new TypeToken<ArrayList<LoadingPlanStowageDetails>>() {}.getType();
              loadingPlanStowageDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.LOADING_PLAN_STOWAGE_DETAILS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loading_sequence_stability_parameters:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(
                      new LoadingSequenceStabilityParameters());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_information_xid");
              listType =
                  new TypeToken<ArrayList<LoadingSequenceStabilityParameters>>() {}.getType();
              loadingSequenceStabilityParametersList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.LOADING_SEQUENCE_STABILITY_PARAMETERS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loading_plan_stability_parameters:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(
                      new LoadingPlanStabilityParameters());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_plan_portwise_details_xid");
              listType = new TypeToken<ArrayList<LoadingPlanStabilityParameters>>() {}.getType();
              loadingPlanStabilityParametersList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.LOADING_PLAN_STABILITY_PARAMETERS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case port_loadable_plan_commingle_details_temp:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(
                      new PortLoadingPlanCommingleTempDetails());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(), map, null);
              listType =
                  new TypeToken<ArrayList<PortLoadingPlanCommingleTempDetails>>() {}.getType();
              portLoadingPlanCommingleTempDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.PORT_LOADABLE_PLAN_COMMINGLE_DETAILS_TEMP.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case port_loadable_plan_commingle_details:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(
                      new PortLoadingPlanCommingleEntityDoc());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_xid");
              listType = new TypeToken<ArrayList<PortLoadingPlanCommingleDetails>>() {}.getType();
              portLoadingPlanCommingleDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.PORT_LOADABLE_PLAN_COMMINGLE_DETAILS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case bill_of_ladding:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new BillOfLanding());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(), map, null);
              listType = new TypeToken<ArrayList<BillOfLanding>>() {}.getType();
              billOfLandingList = new Gson().fromJson(jsonArray, listType);
              idMap.put(LoadingPlanTables.BILL_OF_LADDING.getTable(), dataTransferStage.getId());
              break;
            }
          case pyuser:
            {
              pyUser = new Gson().fromJson(data, PyUser.class);
              idMap.put(LoadingPlanTables.PYUSER.getTable(), dataTransferStage.getId());
              break;
            }
          case voyage:
            {
              voyageActivate = new Gson().fromJson(data, VoyageActivate.class);
              idMap.put(LoadingPlanTables.VOYAGE.getTable(), dataTransferStage.getId());
              break;
            }
          case loadable_pattern:
            {
              loadablePattern = dataTransferString;
              idMap.put(LoadingPlanTables.LOADABLE_PATTERN.getTable(), dataTransferStage.getId());
              break;
            }
          case loadicator_data_for_synoptical_table:
            {
              loadicatorDataForSynoptical = dataTransferString;
              idMap.put(
                  LoadingPlanTables.LOADICATOR_DATA_FOR_SYNOPTICAL_TABLE.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case ballast_operation:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new BallastOperation());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_sequences_xid");
              listType = new TypeToken<ArrayList<BallastOperation>>() {}.getType();
              ballastOperationList = new Gson().fromJson(jsonArray, listType);
              idMap.put(LoadingPlanTables.BALLAST_OPERATION.getTable(), dataTransferStage.getId());
              break;
            }
          case eduction_operation:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new EductionOperation());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_sequences_xid");
              listType = new TypeToken<ArrayList<EductionOperation>>() {}.getType();
              eductionOperationList = new Gson().fromJson(jsonArray, listType);
              idMap.put(LoadingPlanTables.EDUCTION_OPERATION.getTable(), dataTransferStage.getId());
              break;
            }
          case cargo_loading_rate:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new CargoLoadingRate());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_sequences_xid");
              listType = new TypeToken<ArrayList<CargoLoadingRate>>() {}.getType();
              cargoLoadingRateList = new Gson().fromJson(jsonArray, listType);
              idMap.put(LoadingPlanTables.CARGO_LOADING_RATE.getTable(), dataTransferStage.getId());
              break;
            }
          case json_data:
            {
              jsonData = dataTransferString;
              idMap.put(LoadingPlanTables.JSON_DATA.getTable(), dataTransferStage.getId());
              break;
            }
          case loading_port_tide_details:
            {
              try {
                HashMap<String, String> map =
                    loadingPlanStagingService.getAttributeMapping(new PortTideDetail());
                JsonArray jsonArray =
                    removeJsonFields(
                        JsonParser.parseString(dataTransferString).getAsJsonArray(), map, null);
                listType = new TypeToken<ArrayList<PortTideDetail>>() {}.getType();
                portTideDetailList = new Gson().fromJson(jsonArray, listType);
                idMap.put(
                    LoadingPlanTables.LOADING_PORT_TIDE_DETAILS.getTable(),
                    dataTransferStage.getId());
              } catch (Exception e) {
                log.error("Error getting in PortTideDetail:", e);
              }
              break;
            }
          case algo_error_heading:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new AlgoErrorHeading());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_information_xid");
              listType = new TypeToken<ArrayList<AlgoErrorHeading>>() {}.getType();
              algoErrorHeadings = new Gson().fromJson(jsonArray, listType);
              idMap.put(LoadingPlanTables.ALGO_ERROR_HEADING.getTable(), dataTransferStage.getId());
              break;
            }
          case algo_errors:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new AlgoErrors());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "error_heading_xid");
              listType = new TypeToken<ArrayList<AlgoErrors>>() {}.getType();
              algoErrors = new Gson().fromJson(jsonArray, listType);
              idMap.put(LoadingPlanTables.ALGO_ERRORS.getTable(), dataTransferStage.getId());
              break;
            }
          case loading_instructions:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingInstruction());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(), map, null);
              listType = new TypeToken<ArrayList<LoadingInstruction>>() {}.getType();
              loadingInstructions = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.LOADING_INSTRUCTIONS.getTable(), dataTransferStage.getId());
              break;
            }
          case synoptical_table:
            {
              synopticalData = dataTransferString;
              idMap.put(LoadingPlanTables.SYNOPTICAL_TABLE.getTable(), dataTransferStage.getId());
              break;
            }
          case loadable_study_port_rotation:
            {
              loadableStudyPortRotationData = dataTransferString;
              idMap.put(
                  LoadingPlanTables.LOADABLE_STUDY_PORT_ROTATION.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loading_information_algo_status:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingInformationAlgoStatus());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_information_status_xid");
              listType = new TypeToken<LoadingInformationAlgoStatus>() {}.getType();
              loadingInformationAlgoStatus =
                  new Gson().fromJson(jsonArray.get(0).getAsJsonObject(), listType);
              idMap.put(
                  LoadingPlanTables.LOADING_INFORMATION_ALGO_STATUS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loading_plan_commingle_details:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingPlanCommingleDetails());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_plan_portwise_details_xid");
              listType = new TypeToken<ArrayList<LoadingPlanCommingleDetails>>() {}.getType();
              loadingPlanCommingleDetailsList = new Gson().fromJson(jsonArray, listType);
              idMap.put(
                  LoadingPlanTables.LOADING_PLAN_COMMINGLE_DETAILS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loadable_plan_stowage_ballast_details:
            {
              loadablePlanStowageBallastDetailsData = dataTransferString;
              idMap.put(
                  LoadingPlanTables.LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loadable_pattern_cargo_details:
            {
              loadablePatternCargoDetailsData = dataTransferString;
              idMap.put(
                  LoadingPlanTables.LOADABLE_PATTERN_CARGO_DETAILS.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case loadable_plan_commingle_details_portwise:
            {
              loadablePlanComminglePortwiseDetailsData = dataTransferString;
              idMap.put(
                  LoadingPlanTables.LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE.getTable(),
                  dataTransferStage.getId());
              break;
            }
          case on_board_quantity:
            {
              onBoardQuantityData = dataTransferString;
              idMap.put(LoadingPlanTables.ON_BOARD_QUANTITY.getTable(), dataTransferStage.getId());
              break;
            }
          case on_hand_quantity:
            {
              onHandQuantityData = dataTransferString;
              idMap.put(LoadingPlanTables.ON_HAND_QUANTITY.getTable(), dataTransferStage.getId());
              break;
            }
          case loading_rules:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingRule());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(), map, null);
              listType = new TypeToken<ArrayList<LoadingRule>>() {}.getType();
              loadingRules = new Gson().fromJson(jsonArray, listType);
              idMap.put(LoadingPlanTables.LOADING_RULES.getTable(), dataTransferStage.getId());
              break;
            }
          case loading_rule_input:
            {
              HashMap<String, String> map =
                  loadingPlanStagingService.getAttributeMapping(new LoadingRuleInput());
              JsonArray jsonArray =
                  removeJsonFields(
                      JsonParser.parseString(dataTransferString).getAsJsonArray(),
                      map,
                      "loading_rule_xid");
              listType = new TypeToken<ArrayList<LoadingRuleInput>>() {}.getType();
              loadingRuleInputs = new Gson().fromJson(jsonArray, listType);
              idMap.put(LoadingPlanTables.LOADING_RULE_INPUT.getTable(), dataTransferStage.getId());
              break;
            }
        }
      }
      LoadingInformation loadingInfo = null;
      boolean saved = false;
      try {
        loadingInfo = saveLoadingInformation();
        saveCargoToppingOffSequence(loadingInfo);
        saveLoadingBerthDetail(loadingInfo);
        loadingDelays = saveLoadingDelay(loadingInfo);
        saveLoadingDelayReason(loadingDelays);
        saveLoadingMachineryInUse(loadingInfo);
        loadingSequencesList = saveLoadingSequence(loadingInfo);
        loadingPlanPortWiseDetailsList = saveLoadingPlanPortWiseDetails(loadingSequencesList);
        savePortLoadingPlanStabilityParameters(loadingInfo);
        savePortLoadingPlanRobDetails(loadingInfo);
        saveLoadingPlanBallastDetails(loadingPlanPortWiseDetailsList);
        saveLoadingPlanRobDetails(loadingPlanPortWiseDetailsList);
        savePortLoadingPlanBallastDetails(loadingInfo);
        savePortLoadingPlanBallastTempDetails(loadingInfo);
        savePortLoadingPlanStowageDetails(loadingInfo);
        savePortLoadingPlanStowageTempDetails(loadingInfo);
        saveLoadingPlanStowageDetails(loadingPlanPortWiseDetailsList);
        saveLoadingSequenceStabilityParameters(loadingInfo);
        saveLoadingPlanStabilityParameters(loadingPlanPortWiseDetailsList);
        savePortLoadingPlanCommingleTempDetails(loadingInfo);
        savePortLoadingPlanCommingleDetails(loadingInfo);
        saveBillOfLanding(loadingInfo);
        savePyUser();
        activateVoyage();
        saveLoadablePattern();
        saveLoadicatorDataForSynoptical();
        saveBallastOperation(loadingSequencesList);
        saveEductionOperation(loadingSequencesList);
        saveCargoLoadingRate(loadingSequencesList);
        saveJsonData();
        savePortTideDetail(loadingInfo);
        algoErrorHeadings = saveAlgoErrorHeading(loadingInfo);
        saveAlgoErrors(algoErrorHeadings);
        saveLoadingInstruction(loadingInfo);
        saveSynopticalData();
        saveLoadableStudyPortRotationData();
        saveLoadingInformationAlgoStatus(loadingInfo);
        saveLoadingPlanCommingleDetails(loadingPlanPortWiseDetailsList);
        saveLoadablePlanStowageBallastDetailsData();
        saveLoadablePatternCargoDetailsData();
        saveLoadablePlanComminglePortwiseDetailsData();
        saveOnBoardQuantityData();
        saveOnHandQuantityData();
        saveLoadingRule(loadingInfo);
        saveLoadingRuleInput(loadingRules);
        loadingPlanStagingService.updateStatusCompletedForProcessId(
            processId, StagingStatus.COMPLETED.getStatus());
        log.info("updated status to completed for processId:" + processId);
        saved = true;
      } catch (ResourceAccessException e) {
        log.info("Communication ++++++++++++ Failed to save data for  : " + current_table_name);
        log.info("Communication ++++++++++++ ResourceAccessException : " + e.getMessage());
        updateStatusInExceptionCase(
            idMap.get(current_table_name), processId, retryStatus, e.getMessage());
      } catch (Exception e) {
        log.info("Communication ++++++++++++ Failed to save data for  : " + current_table_name);
        log.info("Communication ++++++++++++ Exception : " + e.getMessage());
        updateStatusInExceptionCase(
            idMap.get(current_table_name),
            processId,
            StagingStatus.FAILED.getStatus(),
            e.getMessage());
      }

      if (saved) {
        MessageTypes messageType = MessageTypes.getMessageType(processGroupId);
        if (CPDSS_BUILD_ENV_SHORE.equals(env)) {
          // Generate pattern with communicated data at shore
          try {
            switch (messageType) {
              case LOADINGPLAN:
                {
                  generateLoadingPlan(loadingInformation.getId());
                  break;
                }
              case ULLAGE_UPDATE:
                {
                  log.info("Algo call started for Update Ullage");
                  Integer arrivalDeparture = null;
                  if (!CollectionUtils.isEmpty(portLoadingPlanCommingleDetailsList)) {
                    arrivalDeparture =
                        portLoadingPlanCommingleDetailsList.get(0).getConditionType();
                  } else if (!CollectionUtils.isEmpty(portLoadingPlanBallastTempDetailsList)) {
                    arrivalDeparture =
                        portLoadingPlanBallastTempDetailsList.get(0).getConditionType();
                  }
                  LoadingPlanModels.UllageBillRequest.Builder builder =
                      LoadingPlanModels.UllageBillRequest.newBuilder();
                  LoadingPlanModels.UpdateUllage.Builder updateUllageBuilder =
                      LoadingPlanModels.UpdateUllage.newBuilder();
                  updateUllageBuilder.setLoadingInformationId(loadingInfo.getId());
                  updateUllageBuilder.setArrivalDepartutre(arrivalDeparture);
                  builder.addUpdateUllage(updateUllageBuilder.build());
                  ullageUpdateLoadicatorService.saveLoadicatorInfoForUllageUpdate(builder.build());
                  break;
                }
              case LOADINGPLAN_WITHOUT_ALGO:
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
                      env);
                  break;
                }
            }
          } catch (InvocationTargetException | IllegalAccessException e) {
            throw new GenericServiceException(
                e.getMessage(),
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR,
                e);
          }
        } else if (isShip()) {
          // Save communicated to outbound store
          loadingPlanStagingService.saveDataTransferOutBound(
              processGroupId, loadingInfo.getId(), true);

          if (MessageTypes.LOADINGPLAN_ALGORESULT.getMessageType().equals(processGroupId)
              || MessageTypes.ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT
                  .getMessageType()
                  .equals(processGroupId)
              || MessageTypes.ULLAGE_UPDATE_LOADICATOR_ON_ALGORESULT
                  .getMessageType()
                  .equals(processGroupId)) {
            // Update communication status table with final state
            loadingPlanCommunicationStatusRepository.updateCommunicationStatus(
                CommunicationStatus.COMPLETED.getId(), false, loadingInfo.getId());
          }
        }
      }
    }
  }

  private LoadingInformation saveLoadingInformation() {
    LoadingInformation loadingInfo = null;
    current_table_name = LoadingPlanTables.LOADING_INFORMATION.getTable();
    if (loadingInformation == null) {
      log.info("Communication ++++ LoadingInformation is empty");
      return null;
    }
    setStageOffset();
    setStageDuration();
    setLoadingInformationStatus();
    setArrivalStatus();
    setDepartureStatus();
    Optional<LoadingInformation> loadingInfoObj =
        loadingInformationCommunicationRepository.findById(loadingInformation.getId());
    setEntityDocFields(loadingInformation, loadingInfoObj);
    loadingInfo = loadingInformationCommunicationRepository.save(loadingInformation);
    log.info("Communication ====  LoadingInformation saved with id:" + loadingInfo.getId());
    return loadingInfo;
  }

  private void setDepartureStatus() {
    if (departureStatus != null) {
      Optional<LoadingInformationStatus> departureStatusOpt =
          loadingInfoStatusRepository.findByIdAndIsActive(departureStatus.getId(), true);
      if (departureStatusOpt.isPresent()) {
        loadingInformation.setDepartureStatus(departureStatusOpt.get());
      }
    }
  }

  private void setArrivalStatus() {
    if (arrivalStatus != null) {
      Optional<LoadingInformationStatus> arrivalStatusOpt =
          loadingInfoStatusRepository.findByIdAndIsActive(arrivalStatus.getId(), true);
      if (arrivalStatusOpt.isPresent()) {
        loadingInformation.setArrivalStatus(arrivalStatusOpt.get());
      }
    }
  }

  private void setLoadingInformationStatus() {
    if (loadingInformationStatus != null) {
      Optional<LoadingInformationStatus> informationStatusOpt =
          loadingInfoStatusRepository.findByIdAndIsActive(loadingInformationStatus.getId(), true);
      if (informationStatusOpt.isPresent()) {
        loadingInformation.setLoadingInformationStatus(informationStatusOpt.get());
      }
    }
  }

  private void setStageDuration() {
    if (stageDuration != null) {
      Optional<StageDuration> defaultDurationOpt =
          stageDurationRepository.findByIdAndIsActiveTrue(stageDuration.getId());
      if (defaultDurationOpt.isPresent()) {
        loadingInformation.setStageDuration(defaultDurationOpt.get());
      }
    }
  }

  private void setStageOffset() {
    if (stageOffset != null) {
      Optional<StageOffset> defaultOffsetOpt =
          stageOffsetRepository.findByIdAndIsActiveTrue(stageOffset.getId());
      if (defaultOffsetOpt.isPresent()) {
        loadingInformation.setStageOffset(defaultOffsetOpt.get());
      }
    }
  }

  private void saveCargoToppingOffSequence(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.CARGO_TOPPING_OFF_SEQUENCE.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(cargoToppingOffSequences)) {
      log.info("Communication ++++ LoadingInformation or CargoToppingOffSequence is empty");
      return;
    }
    for (CargoToppingOffSequence cargoToppingOffSequence : cargoToppingOffSequences) {
      Optional<CargoToppingOffSequence> cargoToppOffSeqObj =
          cargoToppingOffSequenceRepository.findById(cargoToppingOffSequence.getId());
      setEntityDocFields(cargoToppingOffSequence, cargoToppOffSeqObj);
      cargoToppingOffSequence.setLoadingInformation(loadingInfo);
    }
    cargoToppingOffSequenceRepository.saveAll(cargoToppingOffSequences);
    log.info("Communication ====  CargoToppingOffSequence saved :" + cargoToppingOffSequences);
  }

  private void saveLoadingBerthDetail(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.LOADING_BERTH_DETAILS.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(loadingBerthDetails)) {
      log.info("Communication ++++ LoadingInformation or LoadingBerthDetail is empty");
      return;
    }
    for (LoadingBerthDetail loadingBerthDetail : loadingBerthDetails) {
      Optional<LoadingBerthDetail> loadingBerthDetailObj =
          loadingBerthDetailsRepository.findById(loadingBerthDetail.getId());
      setEntityDocFields(loadingBerthDetail, loadingBerthDetailObj);
      loadingBerthDetail.setLoadingInformation(loadingInfo);
    }
    loadingBerthDetailsRepository.saveAll(loadingBerthDetails);
    log.info("Communication ==== LoadingBerthDetail saved :" + loadingBerthDetails);
  }

  private List<LoadingDelay> saveLoadingDelay(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.LOADING_DELAY.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(loadingDelays)) {
      log.info("Communication ++++ LoadingInformation or LoadingDelay is empty");
      return null;
    }
    for (LoadingDelay loadingDelay : loadingDelays) {
      Optional<LoadingDelay> loadingDelayObj =
          loadingDelayRepository.findById(loadingDelay.getId());
      setEntityDocFields(loadingDelay, loadingDelayObj);
      loadingDelay.setLoadingInformation(loadingInfo);
    }
    loadingDelays = loadingDelayRepository.saveAll(loadingDelays);
    log.info("Communication ====  LoadingDelay saved :" + loadingDelays);
    return loadingDelays;
  }

  private void saveLoadingDelayReason(List<LoadingDelay> loadingDelays) {
    current_table_name = LoadingPlanTables.LOADING_DELAY_REASON.getTable();
    if (CollectionUtils.isEmpty(loadingDelays) || CollectionUtils.isEmpty(loadingDelayReasons)) {
      log.info("Communication ++++ LoadingDelay or LoadingDelayReason is empty");
      return;
    }
    for (LoadingDelayReason loadingDelayReason : loadingDelayReasons) {
      Optional<LoadingDelayReason> loadingDelayReasonObj =
          loadingDelayReasonRepository.findById(loadingDelayReason.getId());
      setEntityDocFields(loadingDelayReason, loadingDelayReasonObj);
      // Set Loading Delay details
      loadingDelayReason.setLoadingDelay(
          emptyIfNull(loadingDelays).stream()
              .filter(
                  loadingDelay ->
                      loadingDelay
                          .getId()
                          .equals(
                              loadingDelayReason
                                  .getCommunicationRelatedIdMap()
                                  .get("loading_delay_xid")))
              .findFirst()
              .orElse(null));
      Long reasonForDelay = loadingDelayReason.getCommunicationRelatedIdMap().get("reason_xid");
      if (reasonForDelay != null) {
        Optional<ReasonForDelay> reasonForDelayOpt =
            reasonForDelayRepository.findByIdAndIsActiveTrue(reasonForDelay);
        if (reasonForDelayOpt.isPresent()) {
          loadingDelayReason.setReasonForDelay(reasonForDelayOpt.get());
        }
      }
    }
    loadingDelayReasonRepository.saveAll(loadingDelayReasons);
    log.info("Communication ====  LoadingDelayReason saved :" + loadingDelayReasons);
  }

  private void saveLoadingMachineryInUse(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.LOADING_MACHINARY_IN_USE.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(loadingMachineryInUses)) {
      log.info("Communication ++++ LoadingInformation or LoadingMachineryInUse is empty");
      return;
    }
    for (LoadingMachineryInUse loadingMachineryInUse : loadingMachineryInUses) {
      Optional<LoadingMachineryInUse> loadingMachineryInUseObj =
          loadingMachineryInUseRepository.findById(loadingMachineryInUse.getId());
      setEntityDocFields(loadingMachineryInUse, loadingMachineryInUseObj);
      loadingMachineryInUse.setLoadingInformation(loadingInfo);
    }
    loadingMachineryInUseRepository.saveAll(loadingMachineryInUses);
    log.info("Communication ====  LoadingMachineryInUse saved :" + loadingMachineryInUses);
  }

  private List<LoadingSequence> saveLoadingSequence(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.LOADING_SEQUENCE.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(loadingSequencesList)) {
      log.info("Communication ++++ LoadingInformation or LoadingSequence is empty");
      return null;
    }
    for (LoadingSequence loadingSequence : loadingSequencesList) {
      Optional<LoadingSequence> loadingSequenceObj =
          loadingSequenceRepository.findById(loadingSequence.getId());
      setEntityDocFields(loadingSequence, loadingSequenceObj);
      loadingSequence.setLoadingInformation(loadingInfo);
    }
    loadingSequencesList = loadingSequenceRepository.saveAll(loadingSequencesList);
    log.info("Communication ====  Saved LoadingSequence:" + loadingSequencesList);
    return loadingSequencesList;
  }

  private List<LoadingPlanPortWiseDetails> saveLoadingPlanPortWiseDetails(
      List<LoadingSequence> loadingSequencesList) {
    current_table_name = LoadingPlanTables.LOADING_PLAN_PORTWISE_DETAILS.getTable();
    if (CollectionUtils.isEmpty(loadingSequencesList)
        || CollectionUtils.isEmpty(loadingPlanPortWiseDetailsList)) {
      log.info("Communication ++++ LoadingSequence or LoadingPlanPortWiseDetails is empty");
      return null;
    }
    for (LoadingSequence loadingSequence : loadingSequencesList) {
      for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails : loadingPlanPortWiseDetailsList) {
        if (loadingSequence
            .getId()
            .equals(Long.valueOf(loadingPlanPortWiseDetails.getCommunicationRelatedEntityId()))) {
          Optional<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailObj =
              loadingPlanPortWiseDetailsRepository.findById(loadingPlanPortWiseDetails.getId());
          setEntityDocFields(loadingPlanPortWiseDetails, loadingPlanPortWiseDetailObj);
          loadingPlanPortWiseDetails.setLoadingSequence(loadingSequence);
        }
      }
    }
    loadingPlanPortWiseDetailsList =
        loadingPlanPortWiseDetailsRepository.saveAll(loadingPlanPortWiseDetailsList);
    log.info(
        "Communication ====  Saved LoadingPlanPortWiseDetails: " + loadingPlanPortWiseDetailsList);
    return loadingPlanPortWiseDetailsList;
  }

  private void savePortLoadingPlanStabilityParameters(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.PORT_LOADING_PLAN_STABILITY_PARAMETERS.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(portLoadingPlanStabilityParamList)) {
      log.info(
          "Communication ++++ LoadingInformation or PortLoadingPlanStabilityParameters is empty");
      return;
    }
    for (PortLoadingPlanStabilityParameters portLoadingPlanStabilityParameters :
        portLoadingPlanStabilityParamList) {
      Optional<PortLoadingPlanStabilityParameters> portLoadingPlanStabilityParamObj =
          portLoadingPlanStabilityParametersRepository.findById(
              portLoadingPlanStabilityParameters.getId());
      setEntityDocFields(portLoadingPlanStabilityParameters, portLoadingPlanStabilityParamObj);
      portLoadingPlanStabilityParameters.setLoadingInformation(loadingInfo);
    }
    portLoadingPlanStabilityParametersRepository.saveAll(portLoadingPlanStabilityParamList);
    log.info(
        "Communication ====  Saved PortLoadingPlanStabilityParameters: "
            + portLoadingPlanStabilityParamList);
  }

  private void savePortLoadingPlanRobDetails(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.PORT_LOADING_PLAN_ROB_DETAILS.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(portLoadingPlanRobDetailsList)) {
      log.info("Communication ++++ LoadingInformation or PortLoadingPlanRobDetails is empty");
      return;
    }
    for (PortLoadingPlanRobDetails portLoadingPlanRobDetails : portLoadingPlanRobDetailsList) {
      Optional<PortLoadingPlanRobDetails> portLoadingPlanRobDetaObj =
          portLoadingPlanRobDetailsRepository.findById(portLoadingPlanRobDetails.getId());
      setEntityDocFields(portLoadingPlanRobDetails, portLoadingPlanRobDetaObj);
      portLoadingPlanRobDetails.setLoadingInformation(loadingInfo.getId());
    }
    portLoadingPlanRobDetailsRepository.saveAll(portLoadingPlanRobDetailsList);
    log.info(
        "Communication ====  Saved PortLoadingPlanRobDetails: " + portLoadingPlanRobDetailsList);
  }

  private void saveLoadingPlanBallastDetails(
      List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsList) {
    current_table_name = LoadingPlanTables.LOADING_PLAN_BALLAST_DETAILS.getTable();
    if (CollectionUtils.isEmpty(loadingPlanPortWiseDetailsList)
        || CollectionUtils.isEmpty(loadingPlanBallastDetailsList)) {
      log.info(
          "Communication ++++ LoadingPlanPortWiseDetails or LoadingPlanBallastDetails is empty");
      return;
    }
    for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails : loadingPlanPortWiseDetailsList) {
      for (LoadingPlanBallastDetails loadingPlanBallastDetails : loadingPlanBallastDetailsList) {
        if (loadingPlanPortWiseDetails
            .getId()
            .equals(Long.valueOf(loadingPlanBallastDetails.getCommunicationRelatedEntityId()))) {
          Optional<LoadingPlanBallastDetails> loadingPlanBallastDetaObj =
              loadingPlanBallastDetailsRepository.findById(loadingPlanBallastDetails.getId());
          setEntityDocFields(loadingPlanBallastDetails, loadingPlanBallastDetaObj);
          loadingPlanBallastDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
        }
      }
    }
    loadingPlanBallastDetailsRepository.saveAll(loadingPlanBallastDetailsList);
    log.info(
        "Communication ====  Saved LoadingPlanBallastDetails:" + loadingPlanBallastDetailsList);
  }

  private void saveLoadingPlanRobDetails(
      List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsList) {
    current_table_name = LoadingPlanTables.LOADING_PLAN_ROB_DETAILS.getTable();
    if (CollectionUtils.isEmpty(loadingPlanPortWiseDetailsList)
        || CollectionUtils.isEmpty(loadingPlanRobDetailsList)) {
      log.info("Communication ++++ LoadingPlanPortWiseDetails or LoadingPlanRobDetails is empty");
      return;
    }
    for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails : loadingPlanPortWiseDetailsList) {
      for (LoadingPlanRobDetails loadingPlanRobDetails : loadingPlanRobDetailsList) {
        if (loadingPlanPortWiseDetails
            .getId()
            .equals(Long.valueOf(loadingPlanRobDetails.getCommunicationRelatedEntityId()))) {
          Optional<LoadingPlanRobDetails> loadingPlanRobDetaObj =
              loadingPlanRobDetailsRepository.findById(loadingPlanRobDetails.getId());
          setEntityDocFields(loadingPlanRobDetails, loadingPlanRobDetaObj);
          loadingPlanRobDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
        }
      }
    }
    loadingPlanRobDetailsRepository.saveAll(loadingPlanRobDetailsList);
    log.info("Communication ====  Saved LoadingPlanRobDetails:" + loadingPlanRobDetailsList);
  }

  private void savePortLoadingPlanBallastDetails(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(portLoadingPlanBallastDetailsList)) {
      log.info("Communication ++++ LoadingInformation or PortLoadingPlanBallastDetails is empty");
      return;
    }
    for (PortLoadingPlanBallastDetails portLoadingPlanBallastDetails :
        portLoadingPlanBallastDetailsList) {
      Optional<PortLoadingPlanBallastDetails> portLoadingPlanBallastDetaObj =
          portLoadingPlanBallastDetailsRepository.findById(portLoadingPlanBallastDetails.getId());
      setEntityDocFields(portLoadingPlanBallastDetails, portLoadingPlanBallastDetaObj);
      portLoadingPlanBallastDetails.setLoadingInformation(loadingInfo);
    }
    portLoadingPlanBallastDetailsRepository.saveAll(portLoadingPlanBallastDetailsList);
    log.info(
        "Communication ====  Saved PortLoadingPlanBallastDetails:"
            + portLoadingPlanBallastDetailsList);
  }

  private void savePortLoadingPlanBallastTempDetails(LoadingInformation loadingInfo) {
    current_table_name =
        LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS_TEMP.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(portLoadingPlanBallastTempDetailsList)) {
      log.info(
          "Communication ++++ LoadingInformation or PortLoadingPlanBallastTempDetails is empty");
      return;
    }
    for (PortLoadingPlanBallastTempDetails portLoadingPlanBallastTempDetails :
        portLoadingPlanBallastTempDetailsList) {
      Optional<PortLoadingPlanBallastTempDetails> portLoadingPlanBallastTempDetaObj =
          portLoadingPlanBallastTempDetailsRepository.findById(
              portLoadingPlanBallastTempDetails.getId());
      setEntityDocFields(portLoadingPlanBallastTempDetails, portLoadingPlanBallastTempDetaObj);
      portLoadingPlanBallastTempDetails.setLoadingInformation(loadingInfo.getId());
    }
    portLoadingPlanBallastTempDetailsRepository.saveAll(portLoadingPlanBallastTempDetailsList);
    log.info(
        "Communication ====  Saved PortLoadingPlanBallastTempDetails: "
            + portLoadingPlanBallastTempDetailsList);
  }

  private void savePortLoadingPlanStowageDetails(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(portLoadingPlanStowageDetailsList)) {
      log.info("Communication ++++ LoadingInformation or PortLoadingPlanStowageDetails is empty");
      return;
    }
    for (PortLoadingPlanStowageDetails portLoadingPlanStowageDetails :
        portLoadingPlanStowageDetailsList) {
      Optional<PortLoadingPlanStowageDetails> portLoadingPlanStowageDetaObj =
          portLoadingPlanStowageDetailsRepository.findById(portLoadingPlanStowageDetails.getId());
      setEntityDocFields(portLoadingPlanStowageDetails, portLoadingPlanStowageDetaObj);
      portLoadingPlanStowageDetails.setLoadingInformation(loadingInfo);
    }
    portLoadingPlanStowageDetailsRepository.saveAll(portLoadingPlanStowageDetailsList);
    log.info(
        "Communication ====  Saved PortLoadingPlanStowageDetails:"
            + portLoadingPlanStowageDetailsList);
  }

  private void savePortLoadingPlanStowageTempDetails(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS_TEMP.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(portLoadingPlanStowageTempDetailsList)) {
      log.info(
          "Communication ++++ LoadingInformation or PortLoadingPlanStowageTempDetails is empty");
      return;
    }
    for (PortLoadingPlanStowageTempDetails portLoadingPlanStowageTempDetails :
        portLoadingPlanStowageTempDetailsList) {
      Optional<PortLoadingPlanStowageTempDetails> portLoadingPlanStowageTempDetaObj =
          portLoadingPlanStowageTempDetailsRepository.findById(
              portLoadingPlanStowageTempDetails.getId());
      setEntityDocFields(portLoadingPlanStowageTempDetails, portLoadingPlanStowageTempDetaObj);
      portLoadingPlanStowageTempDetails.setLoadingInformation(loadingInfo.getId());
    }
    portLoadingPlanStowageTempDetailsRepository.saveAll(portLoadingPlanStowageTempDetailsList);
    log.info(
        "Communication ====  Saved PortLoadingPlanStowageTempDetails:"
            + portLoadingPlanStowageTempDetailsList);
  }

  private void saveLoadingPlanStowageDetails(
      List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsList) {
    current_table_name = LoadingPlanTables.LOADING_PLAN_STOWAGE_DETAILS.getTable();
    if (CollectionUtils.isEmpty(loadingPlanPortWiseDetailsList)
        || CollectionUtils.isEmpty(loadingPlanStowageDetailsList)) {
      log.info(
          "Communication ++++ LoadingPlanPortWiseDetails or LoadingPlanStowageDetails is empty");
      return;
    }
    for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails : loadingPlanPortWiseDetailsList) {
      for (LoadingPlanStowageDetails loadingPlanStowageDetails : loadingPlanStowageDetailsList) {
        if (loadingPlanPortWiseDetails
            .getId()
            .equals(Long.valueOf(loadingPlanStowageDetails.getCommunicationRelatedEntityId()))) {
          Optional<LoadingPlanStowageDetails> loadingPlanStowageDetaObj =
              loadingPlanStowageDetailsRepository.findById(loadingPlanStowageDetails.getId());
          setEntityDocFields(loadingPlanStowageDetails, loadingPlanStowageDetaObj);
          loadingPlanStowageDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
        }
      }
    }
    loadingPlanStowageDetailsRepository.saveAll(loadingPlanStowageDetailsList);
    log.info(
        "Communication ====  Saved LoadingPlanStowageDetails:" + loadingPlanStowageDetailsList);
  }

  private void saveLoadingSequenceStabilityParameters(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.LOADING_SEQUENCE_STABILITY_PARAMETERS.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(loadingSequenceStabilityParametersList)) {
      log.info(
          "Communication ++++ LoadingInformation or LoadingSequenceStabilityParameters is empty");
      return;
    }
    for (LoadingSequenceStabilityParameters loadingSequenceStabilityParameters :
        loadingSequenceStabilityParametersList) {
      Optional<LoadingSequenceStabilityParameters> loadingSequenceStabilityParamObj =
          loadingSequenceStabiltyParametersRepository.findById(
              loadingSequenceStabilityParameters.getId());
      setEntityDocFields(loadingSequenceStabilityParameters, loadingSequenceStabilityParamObj);
      loadingSequenceStabilityParameters.setLoadingInformation(loadingInfo);
    }
    loadingSequenceStabiltyParametersRepository.saveAll(loadingSequenceStabilityParametersList);
    log.info(
        "Communication ====  Saved LoadingSequenceStabilityParameters:"
            + loadingSequenceStabilityParametersList);
  }

  private void saveLoadingPlanStabilityParameters(
      List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsList) {
    current_table_name = LoadingPlanTables.LOADING_PLAN_STABILITY_PARAMETERS.getTable();
    if (CollectionUtils.isEmpty(loadingPlanPortWiseDetailsList)
        || CollectionUtils.isEmpty(loadingPlanStabilityParametersList)) {
      log.info(
          "Communication ++++ LoadingPlanPortWiseDetails or LoadingPlanStabilityParameters is empty");
      return;
    }
    for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails : loadingPlanPortWiseDetailsList) {
      for (LoadingPlanStabilityParameters loadingPlanStabilityParameters :
          loadingPlanStabilityParametersList) {
        if (loadingPlanPortWiseDetails
            .getId()
            .equals(
                Long.valueOf(loadingPlanStabilityParameters.getCommunicationRelatedEntityId()))) {
          Optional<LoadingPlanStabilityParameters> loadingPlanStabilityParamObj =
              loadingPlanStabilityParametersRepository.findById(
                  loadingPlanStabilityParameters.getId());
          setEntityDocFields(loadingPlanStabilityParameters, loadingPlanStabilityParamObj);
          loadingPlanStabilityParameters.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
        }
      }
    }
    loadingPlanStabilityParametersRepository.saveAll(loadingPlanStabilityParametersList);
    log.info(
        "Communication ====  Saved LoadingPlanStabilityParameters:"
            + loadingPlanStabilityParametersList);
  }

  private void savePortLoadingPlanCommingleTempDetails(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.PORT_LOADABLE_PLAN_COMMINGLE_DETAILS_TEMP.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(portLoadingPlanCommingleTempDetailsList)) {
      log.info(
          "Communication ++++ LoadingInformation or PortLoadingPlanCommingleTempDetails is empty");
      return;
    }
    for (PortLoadingPlanCommingleTempDetails portLoadingPlanCommingleTempDetails :
        portLoadingPlanCommingleTempDetailsList) {
      Optional<PortLoadingPlanCommingleTempDetails> portLoadingPlanCommingleTempDetaObj =
          portLoadingPlanCommingleTempDetailsRepository.findById(
              portLoadingPlanCommingleTempDetails.getId());
      setEntityDocFields(portLoadingPlanCommingleTempDetails, portLoadingPlanCommingleTempDetaObj);
      portLoadingPlanCommingleTempDetails.setLoadingInformation(loadingInfo.getId());
    }
    portLoadingPlanCommingleTempDetailsRepository.saveAll(portLoadingPlanCommingleTempDetailsList);
    log.info(
        "Communication ====  Saved PortLoadingPlanCommingleTempDetails:"
            + portLoadingPlanCommingleTempDetailsList);
  }

  private void savePortLoadingPlanCommingleDetails(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.PORT_LOADABLE_PLAN_COMMINGLE_DETAILS.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(portLoadingPlanCommingleDetailsList)) {
      log.info("Communication ++++ LoadingInformation or PortLoadingPlanCommingleDetails is empty");
      return;
    }
    // deleting existing commingle data against loading info
    portLoadingPlanCommingleDetailsRepository.deleteByLoadingInformationId(loadingInfo.getId());
    log.info("Deleted existing Commingle data against loading info Id:{}", loadingInfo.getId());
    for (PortLoadingPlanCommingleDetails portLoadingPlanCommingleDetails :
        portLoadingPlanCommingleDetailsList) {
      Optional<PortLoadingPlanCommingleDetails> portLoadingPlanCommingleDetaObj =
          portLoadingPlanCommingleDetailsRepository.findById(
              portLoadingPlanCommingleDetails.getId());
      setEntityDocFields(portLoadingPlanCommingleDetails, portLoadingPlanCommingleDetaObj);
      log.info("isActive value:{}", portLoadingPlanCommingleDetails.getIsActive());
      if (portLoadingPlanCommingleDetails.getIsActive() == null) {
        portLoadingPlanCommingleDetails.setIsActive(false);
      }
      portLoadingPlanCommingleDetails.setLoadingInformation(loadingInfo);
    }
    portLoadingPlanCommingleDetailsRepository.saveAll(portLoadingPlanCommingleDetailsList);
    log.info(
        "Communication ====  Saved PortLoadingPlanCommingleDetails:"
            + portLoadingPlanCommingleDetailsList);
  }

  private void saveBillOfLanding(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.BILL_OF_LADDING.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(billOfLandingList)) {
      log.info("Communication ++++ LoadingInformation or BillOfLanding is empty");
      return;
    }
    for (BillOfLanding billOfLanding : billOfLandingList) {
      Optional<BillOfLanding> BillOfLandingObj =
          billOfLandingRepository.findById(billOfLanding.getId());
      setEntityDocFields(billOfLanding, BillOfLandingObj);
      billOfLanding.setLoadingId(loadingInfo.getId());
    }
    billOfLandingRepository.saveAll(billOfLandingList);
    log.info("Communication ====  Saved BillOfLanding:" + billOfLandingList);
  }

  private void savePyUser() {
    current_table_name = LoadingPlanTables.PYUSER.getTable();
    if (pyUser == null) {
      log.info("Communication ++++ pyUser is null");
      return;
    }
    pyUserRepository.save(pyUser);
    log.info("Communication ====  Saved PyUser:" + pyUser);
  }

  private void activateVoyage() throws Exception {
    current_table_name = LoadingPlanTables.VOYAGE.getTable();
    if (voyageActivate == null) {
      log.info("Communication ++++ Voyage is null");
      return;
    }
    LoadableStudy.VoyageActivateRequest.Builder builder =
        LoadableStudy.VoyageActivateRequest.newBuilder();
    builder.setId(voyageActivate.getId());
    builder.setVoyageStatus(voyageActivate.getVoyageStatus());
    LoadableStudy.VoyageActivateReply reply = saveActivatedVoyage(builder.build());
    if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      log.info(
          "Voyage activated with status: {} and id:{} ",
          voyageActivate.getVoyageStatus(),
          voyageActivate.getId());
    } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("ResourceAccessException occurred when Voyage activated");
      throw new ResourceAccessException(reply.getResponseStatus().getMessage());
    } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("Exception occurred when Voyage activated");
      throw new Exception(reply.getResponseStatus().getMessage());
    }
  }

  private void saveLoadablePattern() throws Exception {
    current_table_name = LoadingPlanTables.LOADABLE_PATTERN.getTable();
    if (loadablePattern == null) {
      log.info("Communication ++++ LoadablePattern is null");
      return;
    }
    LoadableStudy.LoadableStudyPatternCommunicationRequest.Builder builder =
        LoadableStudy.LoadableStudyPatternCommunicationRequest.newBuilder();
    log.info("loadablePattern get form staging table:{}", loadablePattern);
    builder.setDataJson(loadablePattern);
    LoadableStudy.LoadableStudyPatternCommunicationReply reply =
        loadableStudyServiceBlockingStub.saveLoadablePatternForCommunication(builder.build());
    if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      log.info("LoadablePattern saved in LoadableStudy");
    } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("ResourceAccessException occurred in save loadablePattern");
      throw new ResourceAccessException(reply.getResponseStatus().getMessage());
    } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("Exception occurred in save loadablePattern");
      throw new Exception(reply.getResponseStatus().getMessage());
    }
  }

  private void saveLoadicatorDataForSynoptical() throws Exception {
    current_table_name = LoadingPlanTables.LOADICATOR_DATA_FOR_SYNOPTICAL_TABLE.getTable();
    if (loadicatorDataForSynoptical == null) {
      log.info("Communication ++++ LoadicatorDataForSynoptical is null");
      return;
    }
    LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
        LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
    log.info("loadicatorDataForSynoptical get form staging table:{}", loadicatorDataForSynoptical);
    builder.setDataJson(loadicatorDataForSynoptical);
    LoadableStudy.LoadableStudyCommunicationReply reply =
        loadableStudyServiceBlockingStub.saveLoadicatorDataSynopticalForCommunication(
            builder.build());
    if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      log.info("SynopticalTableLoadicatorData saved in LoadableStudy ");
    } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("ResourceAccessException occurred in save SynopticalTableLoadicatorData");
      throw new ResourceAccessException(reply.getResponseStatus().getMessage());
    } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("Exception occurred in save SynopticalTableLoadicatorData");
      throw new Exception(reply.getResponseStatus().getMessage());
    }
  }

  private void saveBallastOperation(List<LoadingSequence> loadingSequencesList) {
    current_table_name = LoadingPlanTables.BALLAST_OPERATION.getTable();
    if (CollectionUtils.isEmpty(loadingSequencesList)
        || CollectionUtils.isEmpty(ballastOperationList)) {
      log.info("Communication ++++ LoadingSequence or BallastOperation is empty");
      return;
    }
    for (LoadingSequence loadingSequence : loadingSequencesList) {
      for (BallastOperation ballastOperation : ballastOperationList) {
        if (loadingSequence
            .getId()
            .equals(Long.valueOf(ballastOperation.getCommunicationRelatedEntityId()))) {
          Optional<BallastOperation> ballastOperationObj =
              ballastOperationRepository.findById(ballastOperation.getId());
          setEntityDocFields(ballastOperation, ballastOperationObj);
          ballastOperation.setLoadingSequence(loadingSequence);
        }
      }
    }
    ballastOperationRepository.saveAll(ballastOperationList);
    log.info("Communication ====  Saved BallastOperation: " + ballastOperationList);
  }

  private void saveEductionOperation(List<LoadingSequence> loadingSequencesList) {
    current_table_name = LoadingPlanTables.EDUCTION_OPERATION.getTable();
    if (CollectionUtils.isEmpty(loadingSequencesList)
        || CollectionUtils.isEmpty(eductionOperationList)) {
      log.info("Communication ++++ LoadingSequence or EductionOperation is empty");
      return;
    }
    for (LoadingSequence loadingSequence : loadingSequencesList) {
      for (EductionOperation eductionOperation : eductionOperationList) {
        if (loadingSequence
            .getId()
            .equals(Long.valueOf(eductionOperation.getCommunicationRelatedEntityId()))) {
          Optional<EductionOperation> eductionOperationObj =
              eductionOperationRepository.findById(eductionOperation.getId());
          setEntityDocFields(eductionOperation, eductionOperationObj);
          eductionOperation.setLoadingSequence(loadingSequence);
        }
      }
    }
    eductionOperationRepository.saveAll(eductionOperationList);
    log.info("Communication ====  Saved EductionOperation: " + eductionOperationList);
  }

  private void saveCargoLoadingRate(List<LoadingSequence> loadingSequencesList) {
    current_table_name = LoadingPlanTables.CARGO_LOADING_RATE.getTable();
    if (CollectionUtils.isEmpty(loadingSequencesList)
        || CollectionUtils.isEmpty(cargoLoadingRateList)) {
      log.info("Communication ++++ LoadingSequence or CargoLoadingRate is empty");
      return;
    }
    for (LoadingSequence loadingSequence : loadingSequencesList) {
      for (CargoLoadingRate cargoLoadingRate : cargoLoadingRateList) {
        if (loadingSequence
            .getId()
            .equals(Long.valueOf(cargoLoadingRate.getCommunicationRelatedEntityId()))) {
          Optional<CargoLoadingRate> cargoLoadingRateObj =
              cargoLoadingRateRepository.findById(cargoLoadingRate.getId());
          setEntityDocFields(cargoLoadingRate, cargoLoadingRateObj);
          cargoLoadingRate.setLoadingSequence(loadingSequence);
        }
      }
    }
    cargoLoadingRateRepository.saveAll(cargoLoadingRateList);
    log.info("Communication ====  Saved CargoLoadingRate: " + cargoLoadingRateList);
  }

  private void saveJsonData() throws Exception {
    current_table_name = LoadingPlanTables.JSON_DATA.getTable();
    if (jsonData == null) {
      log.info("Communication ++++ Json Data is null");
      return;
    }
    LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
        LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
    log.info("jsonData from staging table:{}", jsonData);
    builder.setDataJson(jsonData);
    LoadableStudy.LoadableStudyCommunicationReply reply =
        loadableStudyServiceBlockingStub.saveJsonDataForCommunication(builder.build());
    if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      log.info("JsonData saved in LoadableStudy ");
    } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("ResourceAccessException occurred when json_data save");
      throw new ResourceAccessException(reply.getResponseStatus().getMessage());
    } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("Exception occurred when json_data save");
      throw new Exception(reply.getResponseStatus().getMessage());
    }
  }

  private void savePortTideDetail(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.LOADING_PORT_TIDE_DETAILS.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(portTideDetailList)) {
      log.info("Communication ++++ LoadingInformation or PortTideDetail is empty");
      return;
    }
    for (PortTideDetail portTideDetail : portTideDetailList) {
      Optional<PortTideDetail> portTideDetailObj =
          portTideDetailsRepository.findById(portTideDetail.getId());
      setEntityDocFields(portTideDetail, portTideDetailObj);
      portTideDetail.setLoadingXid(loadingInfo.getId());
    }
    portTideDetailsRepository.saveAll(portTideDetailList);
    log.info("Communication ====  Saved PortTideDetail:" + portTideDetailList);
  }

  private List<AlgoErrorHeading> saveAlgoErrorHeading(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.ALGO_ERROR_HEADING.getTable();
    if (CollectionUtils.isEmpty(algoErrorHeadings)) {
      log.info("Communication ++++ AlgoErrorHeadingis empty");
      return null;
    }
    Optional<LoadingInformationStatus> loadingInfoErrorStatus =
        loadingInfoStatusRepository.findById(
            LoadingPlanConstants.LOADING_INFORMATION_ERROR_OCCURRED_ID);
    for (AlgoErrorHeading algoErrorHeading : algoErrorHeadings) {
      Optional<AlgoErrorHeading> algoErrorHeadingOptional =
          algoErrorHeadingRepository.findById(algoErrorHeading.getId());
      setEntityDocFields(algoErrorHeading, algoErrorHeadingOptional);
      algoErrorHeading.setLoadingInformation(loadingInfo);
    }
    algoErrorHeadings = algoErrorHeadingRepository.saveAll(algoErrorHeadings);
    log.info("Communication ====  Saved AlgoErrorHeading:" + algoErrorHeadings);
    return algoErrorHeadings;
  }

  private void saveAlgoErrors(List<AlgoErrorHeading> algoErrorHeadings) {
    current_table_name = LoadingPlanTables.ALGO_ERRORS.getTable();
    if (CollectionUtils.isEmpty(algoErrorHeadings) || CollectionUtils.isEmpty(algoErrors)) {
      log.info("Communication ++++ AlgoErrorHeading or AlgoErrors is empty");
      return;
    }
    for (AlgoErrorHeading algoErrorHeading : algoErrorHeadings) {
      for (AlgoErrors algoError : algoErrors) {
        if (algoErrorHeading
            .getId()
            .equals(Long.valueOf(algoError.getCommunicationRelatedEntityId()))) {
          Optional<AlgoErrors> algoErrorsOptional =
              algoErrorsRepository.findById(algoError.getId());
          setEntityDocFields(algoError, algoErrorsOptional);
          algoError.setAlgoErrorHeading(algoErrorHeading);
        }
      }
    }
    algoErrorsRepository.saveAll(algoErrors);
    log.info("Communication ====  Saved AlgoErrors: " + algoErrors);
  }

  private void saveLoadingInstruction(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.LOADING_INSTRUCTIONS.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(loadingInstructions)) {
      log.info("Communication ++++ LoadingInformation or LoadingInstruction is empty");
      return;
    }
    for (LoadingInstruction loadingInstruction : loadingInstructions) {
      Optional<LoadingInstruction> loadingInstructionOptional =
          loadingInstructionRepository.findById(loadingInstruction.getId());
      setEntityDocFields(loadingInstruction, loadingInstructionOptional);
      loadingInstruction.setLoadingXId(loadingInfo.getId());
    }
    loadingInstructionRepository.saveAll(loadingInstructions);
    log.info("Communication ====  Saved LoadingInstruction:" + loadingInstructions);
  }

  private void saveSynopticalData() throws Exception {
    current_table_name = LoadingPlanTables.SYNOPTICAL_TABLE.getTable();
    if (synopticalData == null) {
      log.info("Communication ++++ SynopticalData  is null");
      return;
    }
    LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
        LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
    log.info("SynopticalData get from staging table:{}", synopticalData);
    builder.setDataJson(synopticalData);
    LoadableStudy.LoadableStudyCommunicationReply reply =
        loadableStudyServiceBlockingStub.saveSynopticalDataForCommunication(builder.build());
    if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      log.info("SynopticalData saved in LoadableStudy ");
    } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("ResourceAccessException occurred when SynopticalData save");
      throw new ResourceAccessException(reply.getResponseStatus().getMessage());
    } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("Exception occurred when SynopticalData save");
      throw new Exception(reply.getResponseStatus().getMessage());
    }
  }

  private void saveLoadableStudyPortRotationData() throws Exception {
    current_table_name = LoadingPlanTables.LOADABLE_STUDY_PORT_ROTATION.getTable();
    if (loadableStudyPortRotationData == null) {
      log.info("Communication ++++ LoadableStudyPortRotationData is null");
      return;
    }
    LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
        LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
    log.info("LoadableStudyPortRotation get from staging table:{}", loadableStudyPortRotationData);
    builder.setDataJson(loadableStudyPortRotationData);
    LoadableStudy.LoadableStudyCommunicationReply reply =
        loadableStudyServiceBlockingStub.saveLoadableStudyPortRotationDataForCommunication(
            builder.build());
    if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      log.info("LoadableStudyPortRotation saved in LoadableStudy ");
    } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("ResourceAccessException occurred when LoadableStudyPortRotation save");
      throw new ResourceAccessException(reply.getResponseStatus().getMessage());
    } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("Exception occurred when LoadableStudyPortRotation save");
      throw new Exception(reply.getResponseStatus().getMessage());
    }
  }

  private void saveLoadingInformationAlgoStatus(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.LOADING_INFORMATION_ALGO_STATUS.getTable();
    if (loadingInfo == null || loadingInformationAlgoStatus == null) {
      log.info("Communication ++++ LoadingInformation or LoadingInformationAlgoStatus is null");
      return;
    }
    Optional<LoadingInformationStatus> loadingInfoStatus =
        loadingInfoStatusRepository.findById(
            loadingInformationAlgoStatus.getCommunicationRelatedEntityId());
    if (loadingInfoStatus.isPresent()) {
      LoadingInformationAlgoStatus algoStatus =
          loadingInformationAlgoStatusRepository.findByLoadingInformationId(loadingInfo.getId());
      if (algoStatus != null) {
        algoStatus.setLoadingInformationStatus(loadingInfoStatus.get());
        if (loadingInformationAlgoStatus.getProcessId() != null) {
          algoStatus.setProcessId(loadingInformationAlgoStatus.getProcessId());
        }
        loadingInformationAlgoStatusRepository.save(algoStatus);
        log.info(
            "Communication ====  LoadingInformationAlgoStatus saved with id:" + algoStatus.getId());
      }
      loadingInformationAlgoStatus.setLoadingInformationStatus(loadingInfoStatus.get());
      loadingInformationAlgoStatus.setLoadingInformation(loadingInfo);
      setEntityDocFields(loadingInformationAlgoStatus, Optional.ofNullable(algoStatus));
      loadingInformationAlgoStatus =
          loadingInformationAlgoStatusRepository.save(loadingInformationAlgoStatus);
      log.info(
          "Communication ====  LoadingInformationAlgoStatus saved with id:"
              + loadingInformationAlgoStatus.getId());
    }
  }

  private void saveLoadingPlanCommingleDetails(
      List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsList) {
    current_table_name = LoadingPlanTables.LOADING_PLAN_COMMINGLE_DETAILS.getTable();
    if (CollectionUtils.isEmpty(loadingPlanPortWiseDetailsList)
        || CollectionUtils.isEmpty(loadingPlanCommingleDetailsList)) {
      log.info(
          "Communication ++++ LoadingPlanPortWiseDetails or LoadingPlanCommingleDetails is empty");
      return;
    }
    for (LoadingPlanCommingleDetails loadingPlanCommingleDetails :
        loadingPlanCommingleDetailsList) {
      Optional<LoadingPlanCommingleDetails> loadingPlanCommingleDetailsObj =
          loadingPlanCommingleDetailsRepository.findById(loadingPlanCommingleDetails.getId());
      setEntityDocFields(loadingPlanCommingleDetails, loadingPlanCommingleDetailsObj);
      // Set LoadingPlanPortWiseDetails
      loadingPlanCommingleDetails.setLoadingPlanPortWiseDetails(
          emptyIfNull(loadingPlanPortWiseDetailsList).stream()
              .filter(
                  loadingPlanPortWiseDetails ->
                      loadingPlanPortWiseDetails
                          .getId()
                          .equals(
                              Long.valueOf(
                                  loadingPlanCommingleDetails.getCommunicationRelatedEntityId())))
              .findFirst()
              .orElse(null));
    }
    loadingPlanCommingleDetailsRepository.saveAll(loadingPlanCommingleDetailsList);
    log.info(
        "Communication ====  Saved LoadingPlanCommingleDetails:" + loadingPlanCommingleDetailsList);
  }

  private void saveLoadablePlanStowageBallastDetailsData() throws Exception {
    current_table_name = LoadingPlanTables.LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS.getTable();
    if (loadablePlanStowageBallastDetailsData == null) {
      log.info("Communication ++++ LoadablePlanStowageBallastDetailsData  is null");
      return;
    }
    LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
        LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
    log.info(
        "LoadablePlanStowageBallastDetails get from staging table:{}",
        loadablePlanStowageBallastDetailsData);
    builder.setDataJson(loadablePlanStowageBallastDetailsData);
    LoadableStudy.LoadableStudyCommunicationReply reply =
        loadableStudyServiceBlockingStub.saveLoadablePlanStowageBallastDetailsForCommunication(
            builder.build());
    if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      log.info("LoadablePlanStowageBallastDetails saved in LoadableStudy ");
    } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("ResourceAccessException occurred when LoadablePlanStowageBallastDetails save");
      throw new ResourceAccessException(reply.getResponseStatus().getMessage());
    } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("Exception occurred when LoadablePlanStowageBallastDetails save");
      throw new Exception(reply.getResponseStatus().getMessage());
    }
  }

  private void saveLoadablePatternCargoDetailsData() throws Exception {
    current_table_name = LoadingPlanTables.LOADABLE_PATTERN_CARGO_DETAILS.getTable();
    if (loadablePatternCargoDetailsData == null) {
      log.info("Communication ++++ LoadablePatternCargoDetailsData  is null");
      return;
    }
    LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
        LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
    log.info(
        "LoadablePatternCargoDetails get from staging table:{}", loadablePatternCargoDetailsData);
    builder.setDataJson(loadablePatternCargoDetailsData);
    LoadableStudy.LoadableStudyCommunicationReply reply =
        loadableStudyServiceBlockingStub.saveLoadablePatternCargoDetailsForCommunication(
            builder.build());
    if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      log.info("LoadablePatternCargoDetails saved in LoadableStudy ");
    } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("ResourceAccessException occurred when LoadablePatternCargoDetails save");
      throw new ResourceAccessException(reply.getResponseStatus().getMessage());
    } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("Exception occurred when LoadablePatternCargoDetails save");
      throw new Exception(reply.getResponseStatus().getMessage());
    }
  }

  private void saveLoadablePlanComminglePortwiseDetailsData() throws Exception {
    current_table_name = LoadingPlanTables.LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE.getTable();
    if (loadablePlanComminglePortwiseDetailsData == null) {
      log.info("Communication ++++ LoadablePlanComminglePortwiseDetailsData  is null");
      return;
    }
    if (loadablePlanComminglePortwiseDetailsData != null) {
      LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
          LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
      log.info(
          "LoadablePlanComminglePortwiseDetails get from staging table:{}",
          loadablePlanComminglePortwiseDetailsData);
      builder.setDataJson(loadablePlanComminglePortwiseDetailsData);
      LoadableStudy.LoadableStudyCommunicationReply reply =
          loadableStudyServiceBlockingStub.saveLoadablePlanCommingleDetailsPortwiseForCommunication(
              builder.build());
      if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
        log.info("LoadablePlanComminglePortwiseDetails saved in LoadableStudy ");
      } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
        log.error(
            "ResourceAccessException occurred when LoadablePlanComminglePortwiseDetails save");
        throw new ResourceAccessException(reply.getResponseStatus().getMessage());
      } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
        log.error("Exception occurred when LoadablePlanComminglePortwiseDetails save");
        throw new Exception(reply.getResponseStatus().getMessage());
      }
    }
  }

  private void saveOnBoardQuantityData() throws Exception {
    current_table_name = LoadingPlanTables.ON_BOARD_QUANTITY.getTable();
    if (onBoardQuantityData == null) {
      log.info("Communication ++++ HnBoardQuantityData  is null");
      return;
    }
    LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
        LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
    log.info("OnBoardQuantity get from staging table:{}", onBoardQuantityData);
    builder.setDataJson(onBoardQuantityData);
    LoadableStudy.LoadableStudyCommunicationReply reply =
        loadableStudyServiceBlockingStub.saveOnBoardQuantityForCommunication(builder.build());
    if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      log.info("OnBoardQuantity saved in LoadableStudy ");
    } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("ResourceAccessException occurred when OnBoardQuantity save");
      throw new ResourceAccessException(reply.getResponseStatus().getMessage());
    } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("Exception occurred when OnBoardQuantity save");
      throw new Exception(reply.getResponseStatus().getMessage());
    }
  }

  private void saveOnHandQuantityData() throws Exception {
    current_table_name = LoadingPlanTables.ON_HAND_QUANTITY.getTable();
    if (onHandQuantityData == null) {
      log.info("Communication ++++ OnHandQuantityData  is null");
      return;
    }
    LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
        LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
    log.info("OnHandQuantity get from staging table:{}", onHandQuantityData);
    builder.setDataJson(onHandQuantityData);
    LoadableStudy.LoadableStudyCommunicationReply reply =
        loadableStudyServiceBlockingStub.saveOnHandQuantityForCommunication(builder.build());
    if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      log.info("OnHandQuantity saved in LoadableStudy ");
    } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("ResourceAccessException occurred when OnHandQuantity save");
      throw new ResourceAccessException(reply.getResponseStatus().getMessage());
    } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
      log.error("Exception occurred when OnHandQuantity save");
      throw new Exception(reply.getResponseStatus().getMessage());
    }
  }

  private void saveLoadingRule(LoadingInformation loadingInfo) {
    current_table_name = LoadingPlanTables.LOADING_RULES.getTable();
    if (loadingInfo == null || CollectionUtils.isEmpty(loadingRules)) {
      log.info("Communication ++++ LoadingInformation or LoadingRule is empty");
      return;
    }
    for (LoadingRule loadingRule : loadingRules) {
      Optional<LoadingRule> loadingRuleOptional =
          loadingRuleRepository.findById(loadingRule.getId());
      setEntityDocFields(loadingRule, loadingRuleOptional);
      loadingRule.setLoadingXid(loadingInfo.getId());
    }
    loadingRuleRepository.saveAll(loadingRules);
    log.info("Communication ====  Saved LoadingRule :{}", loadingRules.size());
  }

  private void saveLoadingRuleInput(List<LoadingRule> loadingRules) {
    current_table_name = LoadingPlanTables.LOADING_RULE_INPUT.getTable();
    if (CollectionUtils.isEmpty(loadingRules) || CollectionUtils.isEmpty(loadingRuleInputs)) {
      log.info("Communication ++++ LoadingRule or LoadingRuleInput is empty");
      return;
    }
    for (LoadingRuleInput loadingRuleInput : loadingRuleInputs) {
      Optional<LoadingRuleInput> loadingRuleInputOptional =
          loadingRuleInputRepository.findById(loadingRuleInput.getId());
      setEntityDocFields(loadingRuleInput, loadingRuleInputOptional);
      // setting loading rule to loading rule input
      if (!isEmpty(loadingRules)) {
        loadingRules.forEach(
            loadingRule -> {
              if (loadingRule.getId().equals(loadingRuleInput.getCommunicationRelatedEntityId())) {
                loadingRuleInput.setLoadingRule(loadingRule);
              }
            });
      }
    }
    loadingRuleInputRepository.saveAll(loadingRuleInputs);
    log.info("Communication ====  Saved LoadingRuleInput:{}", loadingRuleInputs.size());
  }
  // endregion

  // region update status in the case exception
  private void updateStatusInExceptionCase(
      Long id, String processId, String status, String statusDescription) {
    loadingPlanStagingService.updateStatusAndStatusDescriptionForId(
        id, status, statusDescription, LocalDateTime.now());
    loadingPlanStagingService.updateStatusAndModifiedDateTimeForProcessId(
        processId, status, LocalDateTime.now());
  }

  public LoadableStudy.VoyageActivateReply saveActivatedVoyage(
      LoadableStudy.VoyageActivateRequest grpcRequest) {
    return this.loadableStudyServiceBlockingStub.saveActivatedVoyage(grpcRequest);
  }
  // endregion

  private JsonArray removeJsonFields(JsonArray array, HashMap<String, String> map, String... xIds) {
    JsonArray json = loadingPlanStagingService.getAsEntityJson(map, array);
    JsonArray jsonArray = new JsonArray();
    for (JsonElement jsonElement : json) {
      JsonObject communicationRelatedIdMap = new JsonObject();
      final JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (xIds != null) {
        for (String xId : xIds) {
          if (xIds.length == 1) {
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

  private LoadingInformation getLoadingInformation(String json) {
    Long loadingInfo =
        JsonParser.parseString(json)
            .getAsJsonArray()
            .get(0)
            .getAsJsonObject()
            .get("loading_information_xid")
            .getAsLong();
    return loadingInformationRepository.findById(loadingInfo).get();
  }

  /**
   * Method to check communication status and call fallback mechanism on timeout
   *
   * @param taskReqParams map of params used by the scheduler
   * @param messageType messageType value
   */
  public void checkCommunicationStatus(
      final Map<String, String> taskReqParams, MessageTypes messageType) {

    // Status check only enabled for ship. Shore not implemented as retrial not done at shore
    if (isShip()) {

      // Get loadable study messages in envoy-client
      List<LoadingPlanCommunicationStatus> communicationStatusList =
          loadingPlanCommunicationStatusRepository
              .findByCommunicationStatusInAndMessageTypeOrderByCommunicationDateTimeAsc(
                  Arrays.asList(
                      CommunicationStatus.UPLOAD_WITH_HASH_VERIFIED.getId(),
                      CommunicationStatus.TIME_OUT.getId()),
                  messageType.getMessageType())
              .orElse(Collections.emptyList());

      for (LoadingPlanCommunicationStatus communicationStatusRow : communicationStatusList) {

        try {
          // TODO call cancel API of envoy-client

          // Get status from envoy-client
          checkEnvoyStatus(
              communicationStatusRow.getMessageUUID(),
              taskReqParams.get("ClientId"),
              taskReqParams.get("ShipId"),
              communicationStatusRow.getReferenceId());

          LoadingInformation loadingInformation =
              loadingInformationRepository
                  .findByIdAndIsActiveTrue(communicationStatusRow.getReferenceId())
                  .orElseThrow(
                      () -> {
                        log.error(
                            "Loading Info not found. LoadingInformation Id: {}, MessageType: {}",
                            communicationStatusRow.getReferenceId(),
                            messageType.getMessageType());
                        return new GenericServiceException(
                            "Loading Info not found. LoadingInformation Id: "
                                + communicationStatusRow.getReferenceId(),
                            CommonErrorCodes.E_GEN_INTERNAL_ERR,
                            HttpStatusCode.INTERNAL_SERVER_ERROR);
                      });

          // Check timer and update timeout
          if (isCommunicationTimedOut(
              loadingInformation.getId(), communicationStatusRow.getCreatedDateTime())) {
            loadingPlanCommunicationStatusRepository.updateCommunicationStatus(
                CommunicationStatus.TIME_OUT.getId(), loadingInformation.getId());

            // Call fallback mechanism on timeout
            log.info(
                "Retrying {} at {}. Id: {}",
                messageType.getMessageType(),
                env,
                loadingInformation.getId());
            if (MessageTypes.LOADINGPLAN.equals(messageType)) {
              loadingPlanAlgoService.processAlgoLoading(loadingInformation);
            } else if (MessageTypes.ULLAGE_UPDATE.equals(messageType)) {
              updateUllage(loadingInformation);
            } else {
              log.warn("Message Type: {} not configured", messageType);
            }

            loadingPlanCommunicationStatusRepository.updateCommunicationStatus(
                CommunicationStatus.RETRY_AT_SOURCE.getId(), loadingInformation.getId());
          }
        } catch (GenericServiceException e) {
          log.error("Retrial failed. Reference Id: {}", communicationStatusRow.getReferenceId(), e);
        }
      }
    }
  }

  /**
   * Method to generate loading plan
   *
   * @param loadingInfoId loadingPlanId value
   * @throws GenericServiceException Exception on fialure
   */
  private void generateLoadingPlan(final long loadingInfoId) throws GenericServiceException {
    log.info("Generating loading plan. Id: {}", loadingInfoId);

    LoadingPlanModels.LoadingInfoAlgoRequest.Builder builder =
        LoadingPlanModels.LoadingInfoAlgoRequest.newBuilder();
    builder.setLoadingInfoId(loadingInfoId);
    LoadingPlanModels.LoadingInfoAlgoReply.Builder algoReplyBuilder =
        LoadingPlanModels.LoadingInfoAlgoReply.newBuilder();
    loadingPlanAlgoService.generateLoadingPlan(builder.build(), algoReplyBuilder);
  }

  /**
   * Method to update ullage
   *
   * @param loadingInformation loadingInformation entity
   * @throws GenericServiceException Exception on ullage update value failure
   */
  private void updateUllage(final LoadingInformation loadingInformation)
      throws GenericServiceException {

    Optional<List<LoadingInformationAlgoStatus>> loadingInfoAlgoStatus =
        loadingInformationAlgoStatusRepository.getLoadingInfoAlgoStatus(
            loadingInformation.getId(), LoadingPlanConstants.UPDATE_ULLAGE_COMMUNICATED_TO_SHORE);

    if (loadingInfoAlgoStatus.isPresent()) {
      for (LoadingInformationAlgoStatus loadingInformationAlgoStatus :
          loadingInfoAlgoStatus.get()) {
        LoadingPlanModels.UllageBillRequest.Builder builder =
            LoadingPlanModels.UllageBillRequest.newBuilder();
        LoadingPlanModels.UpdateUllage.Builder updateUllageBuilder =
            LoadingPlanModels.UpdateUllage.newBuilder();
        updateUllageBuilder.setLoadingInformationId(loadingInformation.getId());
        updateUllageBuilder.setArrivalDepartutre(loadingInformationAlgoStatus.getConditionType());
        builder.addUpdateUllage(updateUllageBuilder.build());
        try {
          log.info(
              "Ullage update running for LoadingInfoId: {} ::: Arr/Dep condition: {}",
              loadingInformation.getId(),
              loadingInformationAlgoStatus.getConditionType());
          ullageUpdateLoadicatorService.processAlgoUpdateUllage(
              loadingInformation, builder.build());
        } catch (IllegalAccessException | InvocationTargetException e) {
          log.error("Update ullage failed. Loading Info Id: {}", loadingInformation.getId(), e);
          throw new GenericServiceException(
              "Update ullage failed. Loading Info Id: " + loadingInformation.getId(),
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR,
              e);
        }
      }
    }
  }

  /**
   * Method to check message status in envoy service
   *
   * @param messageId messageId value
   * @param clientId clientId value
   * @param shipId shipId value
   * @param referenceId referenceId value
   * @throws GenericServiceException Exception on invalid response
   */
  private void checkEnvoyStatus(
      final String messageId, final String clientId, final String shipId, final long referenceId)
      throws GenericServiceException {
    EnvoyWriter.EnvoyWriterRequest.Builder request = EnvoyWriter.EnvoyWriterRequest.newBuilder();
    request.setMessageId(messageId);
    request.setClientId(clientId);
    request.setImoNumber(shipId);
    EnvoyWriter.WriterReply statusReply = this.envoyWriterService.statusCheck(request.build());

    // Check response status, code and message id
    if (!SUCCESS.equals(statusReply.getResponseStatus().getStatus())
        || !Integer.toString(HttpStatusCode.OK.value()).equals(statusReply.getStatusCode())
        || !messageId.equals(statusReply.getMessageId())) {
      log.error(
          "Invalid response from envoy-writer for retrial. Ref Id: {}, Message Id: {}, Response: {}",
          referenceId,
          messageId,
          statusReply);
      throw new GenericServiceException(
          "Invalid response from envoy-writer for retrial. Message Id: " + messageId,
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Method to check if communication timed out
   *
   * @param referenceId reference id value
   * @param createdTime communication record created time
   * @return true if timed out and false otherwise
   */
  private boolean isCommunicationTimedOut(final long referenceId, final LocalDateTime createdTime) {
    final long start = Timestamp.valueOf(createdTime).getTime();
    // timeLimit is in seconds
    final long end = start + timeLimit * 1000; // Convert time to ms
    final long currentTime = System.currentTimeMillis();

    if (currentTime > end) {
      log.warn(
          "Communication Timeout: {} minutes reached. Communication ignored. Generating at: {}. Ref Id: {}. Unix Times ::: Start: {} ms, End: {} ms, Current Time: {} ms.",
          timeLimit / 60,
          env,
          referenceId,
          start,
          end,
          currentTime);
      return true;
    }
    return false;
  }

  /**
   * Method to check whether the build env is ship or not
   *
   * @return true if the build env is ship and false otherwise
   */
  private boolean isShip() {
    return CPDSS_BUILD_ENV_SHIP.equals(env);
  }

  // endregion
  private void clear() {
    idMap = new HashMap<>();
    loadingInformation = null;
    stageOffset = null;
    stageDuration = null;
    loadingInformationStatus = null;
    arrivalStatus = null;
    departureStatus = null;
    cargoToppingOffSequences = null;
    loadingBerthDetails = null;
    loadingDelays = null;
    loadingDelayReasons = null;
    loadingMachineryInUses = null;
    voyageActivate = null;
    loadingSequencesList = null;
    loadingPlanPortWiseDetailsList = null;
    portLoadingPlanStabilityParamList = null;
    portLoadingPlanRobDetailsList = null;
    loadingPlanBallastDetailsList = null;
    loadingPlanRobDetailsList = null;
    portLoadingPlanBallastDetailsList = null;
    portLoadingPlanBallastTempDetailsList = null;
    portLoadingPlanStowageDetailsList = null;
    portLoadingPlanStowageTempDetailsList = null;
    loadingPlanStowageDetailsList = null;
    loadingSequenceStabilityParametersList = null;
    loadingPlanStabilityParametersList = null;
    ballastOperationList = null;
    eductionOperationList = null;
    cargoLoadingRateList = null;
    loadingPlanCommingleDetailsList = null;
    portLoadingPlanCommingleTempDetailsList = null;
    portLoadingPlanCommingleDetailsList = null;
    billOfLandingList = null;
    pyUser = null;
    loadablePattern = null;
    loadicatorDataForSynoptical = null;
    jsonData = null;
    synopticalData = null;
    loadablePlanStowageBallastDetailsData = null;
    loadablePatternCargoDetailsData = null;
    loadablePlanComminglePortwiseDetailsData = null;
    onBoardQuantityData = null;
    onHandQuantityData = null;
    loadableStudyPortRotationData = null;
    portTideDetailList = null;
    algoErrorHeadings = null;
    algoErrors = null;
    loadingInstructions = null;
    loadingInformationAlgoStatus = null;
    loadingRules = null;
    loadingRuleInputs = null;
  }
}
