/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.CPDSS_BUILD_ENV_SHIP;
import static com.cpdss.loadingplan.utility.LoadingPlanConstants.*;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.EntityDoc;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.common.utils.StagingStatus;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.domain.CommunicationStatus;
import com.cpdss.loadingplan.domain.VoyageActivate;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Log4j2
@Service
@Transactional
@Scope(value = "prototype")
public class LoadingPlanCommunicationService {

  @Value("${cpdss.build.env}")
  private String env;

  @Value("${loadingplan.communication.timelimit}")
  private Long timeLimit;

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

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("envoyReaderService")
  private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

  @GrpcClient("envoyWriterService")
  private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;

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

  @Autowired
  private LoadingPlanCommunicationStatusRepository loadingPlanCommunicationStatusRepository;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

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
                            MessageTypes.LOADINGPLAN_ALGORESULT.getMessageType())
                        .contains(dataTransfer.getProcessGroupId()))
            .collect(Collectors.toList());
    log.info("DataTransferStages in LOADING_DATA_UPDATE task:" + dataTransferStages);
    if (dataTransferStages != null && !dataTransferStages.isEmpty()) {
      getStagingData(dataTransferStages, env, retryStatus);
    }
  }

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
                            MessageTypes.ULLAGE_UPDATE_LOADICATOR_ON_LGORESULT.getMessageType())
                        .contains(dataTransfer.getProcessGroupId()))
            .collect(Collectors.toList());
    log.info("DataTransferStages in ULLAGE_DATA_UPDATE task:" + dataTransferStages);
    if (dataTransferStages != null && !dataTransferStages.isEmpty()) {
      getStagingData(dataTransferStages, env, retryStatus);
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
          loadingPlanStagingService.getAllWithStatusAndTime(
              status, LocalDateTime.now().minusMinutes(2));
    } else {
      dataTransferStagesWithStatus = loadingPlanStagingService.getAllWithStatus(status);
    }
    return dataTransferStagesWithStatus;
  }

  public void getStagingData(
      List<DataTransferStage> dataTransferStages, String env, String retryStatus)
      throws GenericServiceException {
    log.info("Inside getStagingData");
    Map<String, List<DataTransferStage>> dataTransferByProcessId =
        dataTransferStages.stream().collect(Collectors.groupingBy(DataTransferStage::getProcessId));
    log.info("processId group:" + dataTransferByProcessId);
    for (Map.Entry<String, List<DataTransferStage>> entry : dataTransferByProcessId.entrySet()) {
      HashMap<String, Long> idMap = new HashMap<>();
      String processId = entry.getKey();
      LoadingInformation loadingInformation = null;
      StageOffset stageOffset = null;
      StageDuration stageDuration = null;
      LoadingInformationStatus loadingInformationStatus = null;
      LoadingInformationStatus arrivalStatus = null;
      LoadingInformationStatus departureStatus = null;
      // loading plan tables
      List<CargoToppingOffSequence> cargoToppingOffSequences = null;
      List<LoadingBerthDetail> loadingBerthDetails = null;
      List<LoadingDelay> loadingDelays = null;
      List<LoadingDelayReason> loadingDelayReasons = null;
      List<LoadingMachineryInUse> loadingMachineryInUses = null;
      VoyageActivate voyageActivate = null;
      // Pattern save tablesloadingPlanPortWiseDetailsList
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
      // Ullage update tables
      List<PortLoadingPlanCommingleTempDetails> portLoadingPlanCommingleTempDetailsList = null;
      List<PortLoadingPlanCommingleDetails> portLoadingPlanCommingleDetailsList = null;
      List<BillOfLanding> billOfLandingList = null;
      PyUser pyUser = null;
      String loadablePattern = null;
      String loadicatorDataForSynoptical = null;
      String jsonData = null;
      String synopticalData = null;
      String loadableStudyPortRotationData = null;
      List<PortTideDetail> portTideDetailList = null;
      List<AlgoErrorHeading> algoErrorHeadings = null;
      List<AlgoErrors> algoErrors = null;
      List<LoadingInstruction> loadingInstructions = null;
      LoadingInformation loadingInfoError = null;
      LoadingInformationAlgoStatus loadingInformationAlgoStatus = null;
      loadingPlanStagingService.updateStatusForProcessId(
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
                      new PortLoadingPlanCommingleDetails());
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
              loadingInfoError = getLoadingInformation(dataTransferString);
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
        }
      }
      LoadingInformation loadingInfo = null;
      if (loadingInformation != null) {
        try {
          if (stageOffset != null) {
            Optional<StageOffset> defaultOffsetOpt =
                stageOffsetRepository.findByIdAndIsActiveTrue(stageOffset.getId());
            if (defaultOffsetOpt.isPresent()) {
              loadingInformation.setStageOffset(defaultOffsetOpt.get());
            }
          }
          if (stageDuration != null) {
            Optional<StageDuration> defaultDurationOpt =
                stageDurationRepository.findByIdAndIsActiveTrue(stageDuration.getId());
            if (defaultDurationOpt.isPresent()) {
              loadingInformation.setStageDuration(defaultDurationOpt.get());
            }
          }
          if (loadingInformationStatus != null) {
            Optional<LoadingInformationStatus> informationStatusOpt =
                loadingInfoStatusRepository.findByIdAndIsActive(
                    loadingInformationStatus.getId(), true);
            if (informationStatusOpt.isPresent()) {
              loadingInformation.setLoadingInformationStatus(informationStatusOpt.get());
            }
          }

          if (arrivalStatus != null) {
            Optional<LoadingInformationStatus> arrivalStatusOpt =
                loadingInfoStatusRepository.findByIdAndIsActive(arrivalStatus.getId(), true);
            if (arrivalStatusOpt.isPresent()) {
              loadingInformation.setArrivalStatus(arrivalStatusOpt.get());
            }
          }
          if (departureStatus != null) {
            Optional<LoadingInformationStatus> departureStatusOpt =
                loadingInfoStatusRepository.findByIdAndIsActive(departureStatus.getId(), true);
            if (departureStatusOpt.isPresent()) {
              loadingInformation.setDepartureStatus(departureStatusOpt.get());
            }
          }
          Long version = null;
          Optional<LoadingInformation> loadingInfoObj =
              loadingInformationCommunicationRepository.findById(loadingInformation.getId());
          if (loadingInfoObj.isPresent()) {
            version = loadingInfoObj.get().getVersion();
          }
          loadingInformation.setVersion(version);
          loadingInfo = loadingInformationCommunicationRepository.save(loadingInformation);
          log.info("LoadingInformation saved with id:" + loadingInfo.getId());
        } catch (ResourceAccessException e) {
          log.info("ResourceAccessException for LOADING_INFORMATION" + e.getMessage());
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_INFORMATION.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_INFORMATION.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingInfo != null) {
        if (cargoToppingOffSequences != null) {
          try {
            for (CargoToppingOffSequence cargoToppingOffSequence : cargoToppingOffSequences) {
              Long version = null;
              Optional<CargoToppingOffSequence> cargoToppOffSeqObj =
                  cargoToppingOffSequenceRepository.findById(cargoToppingOffSequence.getId());
              if (cargoToppOffSeqObj.isPresent()) {
                version = cargoToppOffSeqObj.get().getVersion();
              }
              cargoToppingOffSequence.setVersion(version);
              cargoToppingOffSequence.setLoadingInformation(loadingInfo);
            }
            cargoToppingOffSequenceRepository.saveAll(cargoToppingOffSequences);
            log.info("CargoToppingOffSequence saved :" + cargoToppingOffSequences);
          } catch (ResourceAccessException e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.CARGO_TOPPING_OFF_SEQUENCE.getTable()),
                processId,
                retryStatus,
                e.getMessage());
          } catch (Exception e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.CARGO_TOPPING_OFF_SEQUENCE.getTable()),
                processId,
                StagingStatus.FAILED.getStatus(),
                e.getMessage());
          }
        }
        if (loadingBerthDetails != null) {
          try {
            for (LoadingBerthDetail loadingBerthDetail : loadingBerthDetails) {
              Long version = null;
              Optional<LoadingBerthDetail> loadingBerthDetailObj =
                  loadingBerthDetailsRepository.findById(loadingBerthDetail.getId());
              if (loadingBerthDetailObj.isPresent()) {
                version = loadingBerthDetailObj.get().getVersion();
              }
              loadingBerthDetail.setVersion(version);
              loadingBerthDetail.setLoadingInformation(loadingInfo);
            }
            loadingBerthDetailsRepository.saveAll(loadingBerthDetails);
            log.info("LoadingBerthDetail saved :" + loadingBerthDetails);
          } catch (ResourceAccessException e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.LOADING_BERTH_DETAILS.getTable()),
                processId,
                retryStatus,
                e.getMessage());
          } catch (Exception e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.LOADING_BERTH_DETAILS.getTable()),
                processId,
                StagingStatus.FAILED.getStatus(),
                e.getMessage());
          }
        }
        if (loadingDelays != null) {
          try {
            for (LoadingDelay loadingDelay : loadingDelays) {
              Long version = null;
              Optional<LoadingDelay> loadingDelayObj =
                  loadingDelayRepository.findById(loadingDelay.getId());
              if (loadingDelayObj.isPresent()) {
                version = loadingDelayObj.get().getVersion();
              }
              loadingDelay.setVersion(version);
              loadingDelay.setLoadingInformation(loadingInfo);
            }
            loadingDelayRepository.saveAll(loadingDelays);
            log.info("LoadingDelay saved :" + loadingDelays);
          } catch (ResourceAccessException e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.LOADING_DELAY.getTable()),
                processId,
                retryStatus,
                e.getMessage());
          } catch (Exception e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.LOADING_DELAY.getTable()),
                processId,
                StagingStatus.FAILED.getStatus(),
                e.getMessage());
          }
        }

        if (loadingDelayReasons != null) {
          try {
            for (LoadingDelayReason loadingDelayReason : loadingDelayReasons) {
              Optional<LoadingDelayReason> loadingDelayReasonObj =
                  loadingDelayReasonRepository.findById(loadingDelayReason.getId());
              loadingDelayReason.setVersion(
                  loadingDelayReasonObj.map(EntityDoc::getVersion).orElse(null));
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
              Long reasonForDelay =
                  loadingDelayReason.getCommunicationRelatedIdMap().get("reason_xid");
              if (reasonForDelay != null) {
                Optional<ReasonForDelay> reasonForDelayOpt =
                    reasonForDelayRepository.findByIdAndIsActiveTrue(reasonForDelay);
                if (reasonForDelayOpt.isPresent()) {
                  loadingDelayReason.setReasonForDelay(reasonForDelayOpt.get());
                }
              }
            }
            loadingDelayReasonRepository.saveAll(loadingDelayReasons);
            log.info("LoadingDelayReason saved :" + loadingDelayReasons);
          } catch (ResourceAccessException e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.LOADING_DELAY_REASON.getTable()),
                processId,
                retryStatus,
                e.getMessage());
          } catch (Exception e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.LOADING_DELAY_REASON.getTable()),
                processId,
                StagingStatus.FAILED.getStatus(),
                e.getMessage());
          }
        }

        if (loadingMachineryInUses != null) {
          try {
            for (LoadingMachineryInUse loadingMachineryInUse : loadingMachineryInUses) {
              Long version = null;
              Optional<LoadingMachineryInUse> loadingMachineryInUseObj =
                  loadingMachineryInUseRepository.findById(loadingMachineryInUse.getId());
              if (loadingMachineryInUseObj.isPresent()) {
                version = loadingMachineryInUseObj.get().getVersion();
              }
              loadingMachineryInUse.setVersion(version);
              loadingMachineryInUse.setLoadingInformation(loadingInfo);
            }
            loadingMachineryInUseRepository.saveAll(loadingMachineryInUses);
            log.info("LoadingMachineryInUse saved :" + loadingMachineryInUses);
          } catch (ResourceAccessException e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.LOADING_MACHINARY_IN_USE.getTable()),
                processId,
                retryStatus,
                e.getMessage());
          } catch (Exception e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.LOADING_MACHINARY_IN_USE.getTable()),
                processId,
                StagingStatus.FAILED.getStatus(),
                e.getMessage());
          }
        }
        if (loadingSequencesList != null && !loadingSequencesList.isEmpty()) {
          try {
            for (LoadingSequence loadingSequence : loadingSequencesList) {
              Long version = null;
              Optional<LoadingSequence> loadingSequenceObj =
                  loadingSequenceRepository.findById(loadingSequence.getId());
              if (loadingSequenceObj.isPresent()) {
                version = loadingSequenceObj.get().getVersion();
              }
              loadingSequence.setVersion(version);
              loadingSequence.setLoadingInformation(loadingInfo);
            }
            loadingSequenceRepository.saveAll(loadingSequencesList);
            log.info("Saved LoadingSequence:" + loadingSequencesList);
          } catch (ResourceAccessException e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.LOADING_SEQUENCE.getTable()),
                processId,
                retryStatus,
                e.getMessage());
          } catch (Exception e) {
            updateStatusInExceptionCase(
                idMap.get(LoadingPlanTables.LOADING_SEQUENCE.getTable()),
                processId,
                StagingStatus.FAILED.getStatus(),
                e.getMessage());
          }
        }
      }
      if (loadingPlanPortWiseDetailsList != null && !loadingPlanPortWiseDetailsList.isEmpty()) {
        try {
          if (loadingSequencesList != null && !loadingSequencesList.isEmpty()) {
            for (LoadingSequence loadingSequence : loadingSequencesList) {
              for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails :
                  loadingPlanPortWiseDetailsList) {
                Long version = null;
                if (loadingSequence
                    .getId()
                    .equals(
                        Long.valueOf(
                            loadingPlanPortWiseDetails.getCommunicationRelatedEntityId()))) {
                  Optional<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailObj =
                      loadingPlanPortWiseDetailsRepository.findById(
                          loadingPlanPortWiseDetails.getId());
                  if (loadingPlanPortWiseDetailObj.isPresent()) {
                    version = loadingPlanPortWiseDetailObj.get().getVersion();
                  }
                  loadingPlanPortWiseDetails.setVersion(version);
                  loadingPlanPortWiseDetails.setLoadingSequence(loadingSequence);
                }
              }
            }
            loadingPlanPortWiseDetailsRepository.saveAll(loadingPlanPortWiseDetailsList);
            log.info("Saved LoadingPlanPortWiseDetails: " + loadingPlanPortWiseDetailsList);
          }
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PLAN_PORTWISE_DETAILS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PLAN_PORTWISE_DETAILS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }

      if (loadingInfo != null
          && portLoadingPlanStabilityParamList != null
          && !portLoadingPlanStabilityParamList.isEmpty()) {
        try {
          for (PortLoadingPlanStabilityParameters portLoadingPlanStabilityParameters :
              portLoadingPlanStabilityParamList) {
            Long version = null;
            Optional<PortLoadingPlanStabilityParameters> portLoadingPlanStabilityParamObj =
                portLoadingPlanStabilityParametersRepository.findById(
                    portLoadingPlanStabilityParameters.getId());
            if (portLoadingPlanStabilityParamObj.isPresent()) {
              version = portLoadingPlanStabilityParamObj.get().getVersion();
            }
            portLoadingPlanStabilityParameters.setVersion(version);
            portLoadingPlanStabilityParameters.setLoadingInformation(loadingInfo);
          }
          portLoadingPlanStabilityParametersRepository.saveAll(portLoadingPlanStabilityParamList);
          log.info(
              "Saved PortLoadingPlanStabilityParameters: " + portLoadingPlanStabilityParamList);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_STABILITY_PARAMETERS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_STABILITY_PARAMETERS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingInfo != null
          && portLoadingPlanRobDetailsList != null
          && !portLoadingPlanRobDetailsList.isEmpty()) {
        try {
          for (PortLoadingPlanRobDetails portLoadingPlanRobDetails :
              portLoadingPlanRobDetailsList) {
            Long version = null;
            Optional<PortLoadingPlanRobDetails> portLoadingPlanRobDetaObj =
                portLoadingPlanRobDetailsRepository.findById(portLoadingPlanRobDetails.getId());
            if (portLoadingPlanRobDetaObj.isPresent()) {
              version = portLoadingPlanRobDetaObj.get().getVersion();
            }
            portLoadingPlanRobDetails.setVersion(version);
            portLoadingPlanRobDetails.setLoadingInformation(loadingInfo.getId());
          }
          portLoadingPlanRobDetailsRepository.saveAll(portLoadingPlanRobDetailsList);
          log.info("Saved PortLoadingPlanRobDetails: " + portLoadingPlanRobDetailsList);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_ROB_DETAILS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_ROB_DETAILS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingPlanBallastDetailsList != null && !loadingPlanBallastDetailsList.isEmpty()) {
        try {
          if (loadingPlanPortWiseDetailsList != null && !loadingPlanPortWiseDetailsList.isEmpty()) {
            for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails :
                loadingPlanPortWiseDetailsList) {
              for (LoadingPlanBallastDetails loadingPlanBallastDetails :
                  loadingPlanBallastDetailsList) {
                Long version = null;
                if (loadingPlanPortWiseDetails
                    .getId()
                    .equals(
                        Long.valueOf(
                            loadingPlanBallastDetails.getCommunicationRelatedEntityId()))) {
                  Optional<LoadingPlanBallastDetails> loadingPlanBallastDetaObj =
                      loadingPlanBallastDetailsRepository.findById(
                          loadingPlanBallastDetails.getId());
                  if (loadingPlanBallastDetaObj.isPresent()) {
                    version = loadingPlanBallastDetaObj.get().getVersion();
                  }
                  loadingPlanBallastDetails.setVersion(version);
                  loadingPlanBallastDetails.setLoadingPlanPortWiseDetails(
                      loadingPlanPortWiseDetails);
                }
              }
            }
            loadingPlanBallastDetailsRepository.saveAll(loadingPlanBallastDetailsList);
            log.info("Saved LoadingPlanBallastDetails:" + loadingPlanBallastDetailsList);
          }
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PLAN_BALLAST_DETAILS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PLAN_BALLAST_DETAILS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingPlanRobDetailsList != null && !loadingPlanRobDetailsList.isEmpty()) {
        try {
          if (loadingPlanPortWiseDetailsList != null && !loadingPlanPortWiseDetailsList.isEmpty()) {
            for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails :
                loadingPlanPortWiseDetailsList) {
              for (LoadingPlanRobDetails loadingPlanRobDetails : loadingPlanRobDetailsList) {
                Long version = null;
                if (loadingPlanPortWiseDetails
                    .getId()
                    .equals(
                        Long.valueOf(loadingPlanRobDetails.getCommunicationRelatedEntityId()))) {
                  Optional<LoadingPlanRobDetails> loadingPlanRobDetaObj =
                      loadingPlanRobDetailsRepository.findById(loadingPlanRobDetails.getId());
                  if (loadingPlanRobDetaObj.isPresent()) {
                    version = loadingPlanRobDetaObj.get().getVersion();
                  }
                  loadingPlanRobDetails.setVersion(version);
                  loadingPlanRobDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
                }
              }
            }
            loadingPlanRobDetailsRepository.saveAll(loadingPlanRobDetailsList);
            log.info("Saved LoadingPlanRobDetails:" + loadingPlanRobDetailsList);
          }
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PLAN_ROB_DETAILS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PLAN_ROB_DETAILS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingInfo != null
          && portLoadingPlanBallastDetailsList != null
          && !portLoadingPlanBallastDetailsList.isEmpty()) {
        try {
          for (PortLoadingPlanBallastDetails portLoadingPlanBallastDetails :
              portLoadingPlanBallastDetailsList) {
            Long version = null;
            Optional<PortLoadingPlanBallastDetails> portLoadingPlanBallastDetaObj =
                portLoadingPlanBallastDetailsRepository.findById(
                    portLoadingPlanBallastDetails.getId());
            if (portLoadingPlanBallastDetaObj.isPresent()) {
              version = portLoadingPlanBallastDetaObj.get().getVersion();
            }
            portLoadingPlanBallastDetails.setVersion(version);
            portLoadingPlanBallastDetails.setLoadingInformation(loadingInfo);
          }
          portLoadingPlanBallastDetailsRepository.saveAll(portLoadingPlanBallastDetailsList);
          log.info("Saved PortLoadingPlanBallastDetails:" + portLoadingPlanBallastDetailsList);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingInfo != null
          && portLoadingPlanBallastTempDetailsList != null
          && !portLoadingPlanBallastTempDetailsList.isEmpty()) {
        try {
          arrivalDeparture = portLoadingPlanBallastTempDetailsList.get(0).getConditionType();
          for (PortLoadingPlanBallastTempDetails portLoadingPlanBallastTempDetails :
              portLoadingPlanBallastTempDetailsList) {
            Long version = null;
            Optional<PortLoadingPlanBallastTempDetails> portLoadingPlanBallastTempDetaObj =
                portLoadingPlanBallastTempDetailsRepository.findById(
                    portLoadingPlanBallastTempDetails.getId());
            if (portLoadingPlanBallastTempDetaObj.isPresent()) {
              version = portLoadingPlanBallastTempDetaObj.get().getVersion();
            }
            portLoadingPlanBallastTempDetails.setVersion(version);
            portLoadingPlanBallastTempDetails.setLoadingInformation(loadingInfo.getId());
          }
          portLoadingPlanBallastTempDetailsRepository.saveAll(
              portLoadingPlanBallastTempDetailsList);
          log.info(
              "Saved PortLoadingPlanBallastTempDetails: " + portLoadingPlanBallastTempDetailsList);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(
                  LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS_TEMP.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(
                  LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS_TEMP.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingInfo != null
          && portLoadingPlanStowageDetailsList != null
          && !portLoadingPlanStowageDetailsList.isEmpty()) {
        try {
          for (PortLoadingPlanStowageDetails portLoadingPlanStowageDetails :
              portLoadingPlanStowageDetailsList) {
            Long version = null;
            Optional<PortLoadingPlanStowageDetails> portLoadingPlanStowageDetaObj =
                portLoadingPlanStowageDetailsRepository.findById(
                    portLoadingPlanStowageDetails.getId());
            if (portLoadingPlanStowageDetaObj.isPresent()) {
              version = portLoadingPlanStowageDetaObj.get().getVersion();
            }
            portLoadingPlanStowageDetails.setVersion(version);
            portLoadingPlanStowageDetails.setLoadingInformation(loadingInfo);
          }
          portLoadingPlanStowageDetailsRepository.saveAll(portLoadingPlanStowageDetailsList);
          log.info("Saved PortLoadingPlanStowageDetails:" + portLoadingPlanStowageDetailsList);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingInfo != null
          && portLoadingPlanStowageTempDetailsList != null
          && !portLoadingPlanStowageTempDetailsList.isEmpty()) {
        try {
          for (PortLoadingPlanStowageTempDetails portLoadingPlanStowageTempDetails :
              portLoadingPlanStowageTempDetailsList) {
            Long version = null;
            Optional<PortLoadingPlanStowageTempDetails> portLoadingPlanStowageTempDetaObj =
                portLoadingPlanStowageTempDetailsRepository.findById(
                    portLoadingPlanStowageTempDetails.getId());
            if (portLoadingPlanStowageTempDetaObj.isPresent()) {
              version = portLoadingPlanStowageTempDetaObj.get().getVersion();
            }
            portLoadingPlanStowageTempDetails.setVersion(version);
            portLoadingPlanStowageTempDetails.setLoadingInformation(loadingInfo.getId());
          }
          portLoadingPlanStowageTempDetailsRepository.saveAll(
              portLoadingPlanStowageTempDetailsList);
          log.info(
              "Saved PortLoadingPlanStowageTempDetails:" + portLoadingPlanStowageTempDetailsList);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS_TEMP.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS_TEMP.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingPlanStowageDetailsList != null && !loadingPlanStowageDetailsList.isEmpty()) {
        try {
          if (loadingPlanPortWiseDetailsList != null && !loadingPlanPortWiseDetailsList.isEmpty()) {
            for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails :
                loadingPlanPortWiseDetailsList) {
              for (LoadingPlanStowageDetails loadingPlanStowageDetails :
                  loadingPlanStowageDetailsList) {
                Long version = null;
                if (loadingPlanPortWiseDetails
                    .getId()
                    .equals(
                        Long.valueOf(
                            loadingPlanStowageDetails.getCommunicationRelatedEntityId()))) {
                  Optional<LoadingPlanStowageDetails> loadingPlanStowageDetaObj =
                      loadingPlanStowageDetailsRepository.findById(
                          loadingPlanStowageDetails.getId());
                  if (loadingPlanStowageDetaObj.isPresent()) {
                    version = loadingPlanStowageDetaObj.get().getVersion();
                  }
                  loadingPlanStowageDetails.setVersion(version);
                  loadingPlanStowageDetails.setLoadingPlanPortWiseDetails(
                      loadingPlanPortWiseDetails);
                }
              }
            }
            loadingPlanStowageDetailsRepository.saveAll(loadingPlanStowageDetailsList);
            log.info("Saved LoadingPlanStowageDetails:" + loadingPlanStowageDetailsList);
          }
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PLAN_STOWAGE_DETAILS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PLAN_STOWAGE_DETAILS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingInfo != null
          && loadingSequenceStabilityParametersList != null
          && !loadingSequenceStabilityParametersList.isEmpty()) {
        try {
          for (LoadingSequenceStabilityParameters loadingSequenceStabilityParameters :
              loadingSequenceStabilityParametersList) {
            Long version = null;
            Optional<LoadingSequenceStabilityParameters> loadingSequenceStabilityParamObj =
                loadingSequenceStabiltyParametersRepository.findById(
                    loadingSequenceStabilityParameters.getId());
            if (loadingSequenceStabilityParamObj.isPresent()) {
              version = loadingSequenceStabilityParamObj.get().getVersion();
            }
            loadingSequenceStabilityParameters.setVersion(version);
            loadingSequenceStabilityParameters.setLoadingInformation(loadingInfo);
          }
          loadingSequenceStabiltyParametersRepository.saveAll(
              loadingSequenceStabilityParametersList);
          log.info(
              "Saved LoadingSequenceStabilityParameters:" + loadingSequenceStabilityParametersList);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_SEQUENCE_STABILITY_PARAMETERS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_SEQUENCE_STABILITY_PARAMETERS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingPlanStabilityParametersList != null
          && !loadingPlanStabilityParametersList.isEmpty()) {
        try {
          if (loadingPlanPortWiseDetailsList != null && !loadingPlanPortWiseDetailsList.isEmpty()) {
            for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails :
                loadingPlanPortWiseDetailsList) {
              for (LoadingPlanStabilityParameters loadingPlanStabilityParameters :
                  loadingPlanStabilityParametersList) {
                Long version = null;
                if (loadingPlanPortWiseDetails
                    .getId()
                    .equals(
                        Long.valueOf(
                            loadingPlanStabilityParameters.getCommunicationRelatedEntityId()))) {
                  Optional<LoadingPlanStabilityParameters> loadingPlanStabilityParamObj =
                      loadingPlanStabilityParametersRepository.findById(
                          loadingPlanStabilityParameters.getId());
                  if (loadingPlanStabilityParamObj.isPresent()) {
                    version = loadingPlanStabilityParamObj.get().getVersion();
                  }
                  loadingPlanStabilityParameters.setVersion(version);
                  loadingPlanStabilityParameters.setLoadingPlanPortWiseDetails(
                      loadingPlanPortWiseDetails);
                }
              }
            }
            loadingPlanStabilityParametersRepository.saveAll(loadingPlanStabilityParametersList);
            log.info("Saved LoadingPlanStabilityParameters:" + loadingPlanStabilityParametersList);
          }
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PLAN_STABILITY_PARAMETERS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PLAN_STABILITY_PARAMETERS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingInfo != null
          && portLoadingPlanCommingleTempDetailsList != null
          && !portLoadingPlanCommingleTempDetailsList.isEmpty()) {
        try {
          for (PortLoadingPlanCommingleTempDetails portLoadingPlanCommingleTempDetails :
              portLoadingPlanCommingleTempDetailsList) {
            Long version = null;
            Optional<PortLoadingPlanCommingleTempDetails> portLoadingPlanCommingleTempDetaObj =
                portLoadingPlanCommingleTempDetailsRepository.findById(
                    portLoadingPlanCommingleTempDetails.getId());
            if (portLoadingPlanCommingleTempDetaObj.isPresent()) {
              version = portLoadingPlanCommingleTempDetaObj.get().getVersion();
            }
            portLoadingPlanCommingleTempDetails.setVersion(version);
            portLoadingPlanCommingleTempDetails.setLoadingInformation(loadingInfo.getId());
          }
          portLoadingPlanCommingleTempDetailsRepository.saveAll(
              portLoadingPlanCommingleTempDetailsList);
          log.info(
              "Saved PortLoadingPlanCommingleTempDetails:"
                  + portLoadingPlanCommingleTempDetailsList);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADABLE_PLAN_COMMINGLE_DETAILS_TEMP.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADABLE_PLAN_COMMINGLE_DETAILS_TEMP.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingInfo != null
          && portLoadingPlanCommingleDetailsList != null
          && !portLoadingPlanCommingleDetailsList.isEmpty()) {
        try {
          arrivalDeparture = portLoadingPlanCommingleDetailsList.get(0).getConditionType();
          for (PortLoadingPlanCommingleDetails portLoadingPlanCommingleDetails :
              portLoadingPlanCommingleDetailsList) {
            Long version = null;
            Optional<PortLoadingPlanCommingleDetails> portLoadingPlanCommingleDetaObj =
                portLoadingPlanCommingleDetailsRepository.findById(
                    portLoadingPlanCommingleDetails.getId());
            if (portLoadingPlanCommingleDetaObj.isPresent()) {
              version = portLoadingPlanCommingleDetaObj.get().getVersion();
            }
            portLoadingPlanCommingleDetails.setVersion(version);
            portLoadingPlanCommingleDetails.setLoadingInformation(loadingInfo);
          }
          portLoadingPlanCommingleDetailsRepository.saveAll(portLoadingPlanCommingleDetailsList);
          log.info("Saved PortLoadingPlanCommingleDetails:" + portLoadingPlanCommingleDetailsList);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADABLE_PLAN_COMMINGLE_DETAILS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PORT_LOADABLE_PLAN_COMMINGLE_DETAILS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingInfo != null && billOfLandingList != null && !billOfLandingList.isEmpty()) {
        try {
          for (BillOfLanding billOfLanding : billOfLandingList) {
            Long version = null;
            Optional<BillOfLanding> BillOfLandingObj =
                billOfLandingRepository.findById(billOfLanding.getId());
            if (BillOfLandingObj.isPresent()) {
              version = BillOfLandingObj.get().getVersion();
            }
            billOfLanding.setVersion(version);
            billOfLanding.setLoadingId(loadingInfo.getId());
          }
          billOfLandingRepository.saveAll(billOfLandingList);
          log.info("Saved BillOfLanding:" + billOfLandingList);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.BILL_OF_LADDING.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.BILL_OF_LADDING.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (pyUser != null) {
        try {
          pyUserRepository.save(pyUser);
          log.info("Saved PyUser:" + pyUser);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PYUSER.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.PYUSER.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (voyageActivate != null) {
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
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.VOYAGE.getTable()),
              processId,
              retryStatus,
              reply.getResponseStatus().getMessage());
        } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.VOYAGE.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              reply.getResponseStatus().getMessage());
        }
      }
      if (loadablePattern != null) {
        LoadableStudy.LoadableStudyPatternCommunicationRequest.Builder builder =
            LoadableStudy.LoadableStudyPatternCommunicationRequest.newBuilder();
        log.info("loadablePattern get form staging table:{}", loadablePattern);
        builder.setDataJson(loadablePattern);
        LoadableStudy.LoadableStudyPatternCommunicationReply reply =
            loadableStudyServiceBlockingStub.saveLoadablePatternForCommunication(builder.build());
        if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
          log.info("LoadablePattern saved in LoadableStudy");
        } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADABLE_PATTERN.getTable()),
              processId,
              retryStatus,
              reply.getResponseStatus().getMessage());
        } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADABLE_PATTERN.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              reply.getResponseStatus().getMessage());
        }
      }
      if (loadicatorDataForSynoptical != null) {
        LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
            LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
        log.info(
            "loadicatorDataForSynoptical get form staging table:{}", loadicatorDataForSynoptical);
        builder.setDataJson(loadicatorDataForSynoptical);
        LoadableStudy.LoadableStudyCommunicationReply reply =
            loadableStudyServiceBlockingStub.saveLoadicatorDataSynopticalForCommunication(
                builder.build());
        if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
          log.info("SynopticalTableLoadicatorData saved in LoadableStudy ");
        } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADICATOR_DATA_FOR_SYNOPTICAL_TABLE.getTable()),
              processId,
              retryStatus,
              reply.getResponseStatus().getMessage());
        } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADICATOR_DATA_FOR_SYNOPTICAL_TABLE.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              reply.getResponseStatus().getMessage());
        }
      }
      if (ballastOperationList != null && !ballastOperationList.isEmpty()) {
        try {
          if (loadingSequencesList != null && !loadingSequencesList.isEmpty()) {
            for (LoadingSequence loadingSequence : loadingSequencesList) {
              for (BallastOperation ballastOperation : ballastOperationList) {
                Long version = null;
                if (loadingSequence
                    .getId()
                    .equals(Long.valueOf(ballastOperation.getCommunicationRelatedEntityId()))) {
                  Optional<BallastOperation> ballastOperationObj =
                      ballastOperationRepository.findById(ballastOperation.getId());
                  if (ballastOperationObj.isPresent()) {
                    version = ballastOperationObj.get().getVersion();
                  }
                  ballastOperation.setVersion(version);
                  ballastOperation.setLoadingSequence(loadingSequence);
                }
              }
            }
            ballastOperationRepository.saveAll(ballastOperationList);
            log.info("Saved BallastOperation: " + ballastOperationList);
          }
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.BALLAST_OPERATION.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.BALLAST_OPERATION.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (eductionOperationList != null && !eductionOperationList.isEmpty()) {
        try {
          if (loadingSequencesList != null && !loadingSequencesList.isEmpty()) {
            for (LoadingSequence loadingSequence : loadingSequencesList) {
              for (EductionOperation eductionOperation : eductionOperationList) {
                Long version = null;
                if (loadingSequence
                    .getId()
                    .equals(Long.valueOf(eductionOperation.getCommunicationRelatedEntityId()))) {
                  Optional<EductionOperation> eductionOperationObj =
                      eductionOperationRepository.findById(eductionOperation.getId());
                  if (eductionOperationObj.isPresent()) {
                    version = eductionOperationObj.get().getVersion();
                  }
                  eductionOperation.setVersion(version);
                  eductionOperation.setLoadingSequence(loadingSequence);
                }
              }
            }
            eductionOperationRepository.saveAll(eductionOperationList);
            log.info("Saved EductionOperation: " + eductionOperationList);
          }
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.EDUCTION_OPERATION.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.EDUCTION_OPERATION.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (cargoLoadingRateList != null && !cargoLoadingRateList.isEmpty()) {
        try {
          if (loadingSequencesList != null && !loadingSequencesList.isEmpty()) {
            for (LoadingSequence loadingSequence : loadingSequencesList) {
              for (CargoLoadingRate cargoLoadingRate : cargoLoadingRateList) {
                Long version = null;
                if (loadingSequence
                    .getId()
                    .equals(Long.valueOf(cargoLoadingRate.getCommunicationRelatedEntityId()))) {
                  Optional<CargoLoadingRate> cargoLoadingRateObj =
                      cargoLoadingRateRepository.findById(cargoLoadingRate.getId());
                  if (cargoLoadingRateObj.isPresent()) {
                    version = cargoLoadingRateObj.get().getVersion();
                  }
                  cargoLoadingRate.setVersion(version);
                  cargoLoadingRate.setLoadingSequence(loadingSequence);
                }
              }
            }
            cargoLoadingRateRepository.saveAll(cargoLoadingRateList);
            log.info("Saved CargoLoadingRate: " + cargoLoadingRateList);
          }
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.CARGO_LOADING_RATE.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.CARGO_LOADING_RATE.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (jsonData != null) {
        LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
            LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
        log.info("jsonData from staging table:{}", jsonData);
        builder.setDataJson(jsonData);
        LoadableStudy.LoadableStudyCommunicationReply reply =
            loadableStudyServiceBlockingStub.saveJsonDataForCommunication(builder.build());
        if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
          log.info("JsonData saved in LoadableStudy ");
        } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.JSON_DATA.getTable()),
              processId,
              retryStatus,
              reply.getResponseStatus().getMessage());
        } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.JSON_DATA.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              reply.getResponseStatus().getMessage());
        }
      }
      if (loadingInfo != null && portTideDetailList != null && !portTideDetailList.isEmpty()) {
        try {
          for (PortTideDetail portTideDetail : portTideDetailList) {
            Long version = null;
            Optional<PortTideDetail> portTideDetailObj =
                portTideDetailsRepository.findById(portTideDetail.getId());
            if (portTideDetailObj.isPresent()) {
              version = portTideDetailObj.get().getVersion();
            }
            portTideDetail.setVersion(version);
            portTideDetail.setLoadingXid(loadingInfo.getId());
          }
          portTideDetailsRepository.saveAll(portTideDetailList);
          log.info("Saved PortTideDetail:" + portTideDetailList);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PORT_TIDE_DETAILS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_PORT_TIDE_DETAILS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (algoErrorHeadings != null && !algoErrorHeadings.isEmpty()) {
        try {
          Optional<LoadingInformationStatus> loadingInfoErrorStatus =
              loadingInfoStatusRepository.findById(
                  LoadingPlanConstants.LOADING_INFORMATION_ERROR_OCCURRED_ID);
          loadingInfoError.setLoadingInformationStatus(loadingInfoErrorStatus.get());
          loadingInfoError = loadingInformationRepository.save(loadingInfoError);
          for (AlgoErrorHeading algoErrorHeading : algoErrorHeadings) {
            Optional<AlgoErrorHeading> algoErrorHeadingOptional =
                algoErrorHeadingRepository.findById(algoErrorHeading.getId());
            algoErrorHeading.setVersion(
                algoErrorHeadingOptional.isPresent()
                    ? algoErrorHeadingOptional.get().getVersion()
                    : null);
            algoErrorHeading.setLoadingInformation(loadingInfoError);
          }
          algoErrorHeadingRepository.saveAll(algoErrorHeadings);
          log.info("Saved AlgoErrorHeading:" + algoErrorHeadings);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.ALGO_ERROR_HEADING.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.ALGO_ERROR_HEADING.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (algoErrors != null && !algoErrors.isEmpty()) {
        try {
          if (algoErrorHeadings != null && !algoErrorHeadings.isEmpty()) {
            for (AlgoErrorHeading algoErrorHeading : algoErrorHeadings) {
              for (AlgoErrors algoError : algoErrors) {
                if (algoErrorHeading
                    .getId()
                    .equals(Long.valueOf(algoError.getCommunicationRelatedEntityId()))) {
                  Optional<AlgoErrors> algoErrorsOptional =
                      algoErrorsRepository.findById(algoError.getId());
                  algoError.setVersion(
                      algoErrorsOptional.isPresent()
                          ? algoErrorsOptional.get().getVersion()
                          : null);
                  algoError.setAlgoErrorHeading(algoErrorHeading);
                }
              }
            }
            algoErrorsRepository.saveAll(algoErrors);
            log.info("Saved AlgoErrors: " + algoErrors);
          }
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.ALGO_ERRORS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.ALGO_ERRORS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (loadingInfo != null && loadingInstructions != null && !loadingInstructions.isEmpty()) {
        try {
          for (LoadingInstruction loadingInstruction : loadingInstructions) {
            Optional<LoadingInstruction> loadingInstructionOptional =
                loadingInstructionRepository.findById(loadingInstruction.getId());
            loadingInstruction.setVersion(
                loadingInstructionOptional.isPresent()
                    ? loadingInstructionOptional.get().getVersion()
                    : null);
            loadingInstruction.setLoadingXId(loadingInfo.getId());
          }
          loadingInstructionRepository.saveAll(loadingInstructions);
          log.info("Saved LoadingInstruction:" + loadingInstructions);
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_INSTRUCTIONS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_INSTRUCTIONS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      if (synopticalData != null) {
        LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
            LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
        log.info("SynopticalData get from staging table:{}", synopticalData);
        builder.setDataJson(synopticalData);
        LoadableStudy.LoadableStudyCommunicationReply reply =
            loadableStudyServiceBlockingStub.saveSynopticalDataForCommunication(builder.build());
        if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
          log.info("SynopticalData saved in LoadableStudy ");
        } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.SYNOPTICAL_TABLE.getTable()),
              processId,
              retryStatus,
              reply.getResponseStatus().getMessage());
        } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.SYNOPTICAL_TABLE.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              reply.getResponseStatus().getMessage());
        }
      }
      if (loadableStudyPortRotationData != null) {
        LoadableStudy.LoadableStudyCommunicationRequest.Builder builder =
            LoadableStudy.LoadableStudyCommunicationRequest.newBuilder();
        log.info(
            "LoadableStudyPortRotation get from staging table:{}", loadableStudyPortRotationData);
        builder.setDataJson(loadableStudyPortRotationData);
        LoadableStudy.LoadableStudyCommunicationReply reply =
            loadableStudyServiceBlockingStub.saveLoadableStudyPortRotationDataForCommunication(
                builder.build());
        if (SUCCESS.equals(reply.getResponseStatus().getStatus())) {
          log.info("LoadableStudyPortRotation saved in LoadableStudy ");
        } else if (FAILED_WITH_RESOURCE_EXC.equals(reply.getResponseStatus().getStatus())) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADABLE_STUDY_PORT_ROTATION.getTable()),
              processId,
              retryStatus,
              reply.getResponseStatus().getMessage());
        } else if (FAILED_WITH_EXC.equals(reply.getResponseStatus().getStatus())) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADABLE_STUDY_PORT_ROTATION.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              reply.getResponseStatus().getMessage());
        }
      }
      if (loadingInformationAlgoStatus != null) {
        try {
          Optional<LoadingInformationStatus> loadingInfoStatus =
              loadingInfoStatusRepository.findById(
                  loadingInformationAlgoStatus.getCommunicationRelatedEntityId());
          if (loadingInfoStatus.isPresent()) {
            LoadingInformationAlgoStatus algoStatus =
                loadingInformationAlgoStatusRepository.findByLoadingInformationId(
                    loadingInfo.getId());
            if (algoStatus != null) {
              algoStatus.setLoadingInformationStatus(loadingInfoStatus.get());
              if (loadingInformationAlgoStatus.getProcessId() != null) {
                algoStatus.setProcessId(loadingInformationAlgoStatus.getProcessId());
              }
              loadingInformationAlgoStatusRepository.save(algoStatus);
              log.info(
                  "Communication #######  LoadingInformationAlgoStatus saved with id:"
                      + algoStatus.getId());
            }
            loadingInformationAlgoStatus.setLoadingInformationStatus(loadingInfoStatus.get());
            loadingInformationAlgoStatus.setLoadingInformation(loadingInfo);
            loadingInformationAlgoStatus.setVersion(null);
            loadingInformationAlgoStatus =
                loadingInformationAlgoStatusRepository.save(loadingInformationAlgoStatus);
            log.info(
                "Communication #######  LoadingInformationAlgoStatus saved with id:"
                    + loadingInformationAlgoStatus.getId());
          }
        } catch (ResourceAccessException e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_INFORMATION_ALGO_STATUS.getTable()),
              processId,
              retryStatus,
              e.getMessage());
        } catch (Exception e) {
          updateStatusInExceptionCase(
              idMap.get(LoadingPlanTables.LOADING_INFORMATION_ALGO_STATUS.getTable()),
              processId,
              StagingStatus.FAILED.getStatus(),
              e.getMessage());
        }
      }
      loadingPlanStagingService.updateStatusCompletedForProcessId(
          processId, StagingStatus.COMPLETED.getStatus());
      log.info("updated status to completed for processId:" + processId);
      if (!env.equals("ship") && loadingInfo != null) {
        if (processGroupId.equals(MessageTypes.LOADINGPLAN.getMessageType())) {
          log.info("Algo call started for LoadingPlan");
          LoadingPlanModels.LoadingInfoAlgoRequest.Builder builder =
              LoadingPlanModels.LoadingInfoAlgoRequest.newBuilder();
          builder.setLoadingInfoId(loadingInfo.getId());
          LoadingPlanModels.LoadingInfoAlgoReply.Builder algoReplyBuilder =
              LoadingPlanModels.LoadingInfoAlgoReply.newBuilder();
          loadingPlanAlgoService.generateLoadingPlan(builder.build(), algoReplyBuilder);
        }
        if (processGroupId.equals(MessageTypes.ULLAGE_UPDATE.getMessageType())) {
          log.info("Algo call started for Update Ullage");
          try {
            LoadingPlanModels.UllageBillRequest.Builder builder =
                LoadingPlanModels.UllageBillRequest.newBuilder();
            LoadingPlanModels.UpdateUllage.Builder updateUllageBuilder =
                LoadingPlanModels.UpdateUllage.newBuilder();
            updateUllageBuilder.setLoadingInformationId(loadingInfo.getId());
            updateUllageBuilder.setArrivalDepartutre(arrivalDeparture);
            builder.addUpdateUllage(updateUllageBuilder.build());
            ullageUpdateLoadicatorService.saveLoadicatorInfoForUllageUpdate(builder.build());
          } catch (InvocationTargetException | IllegalAccessException e) {
            throw new GenericServiceException(
                e.getMessage(),
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR,
                e);
          }
        }
      } else if (isShip()) {
        if (MessageTypes.LOADINGPLAN_ALGORESULT.getMessageType().equals(processGroupId)
            || MessageTypes.ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT
                .getMessageType()
                .equals(processGroupId)
            || MessageTypes.ULLAGE_UPDATE_LOADICATOR_ON_LGORESULT
                .getMessageType()
                .equals(processGroupId)) {
          // Update communication status table with final state
          loadingPlanCommunicationStatusRepository.updateCommunicationStatus(
              CommunicationStatus.COMPLETED.getId(), loadingInfo.getId());
        }
      }
    }
  }

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

  private JsonArray removeJsonFields(JsonArray array, HashMap<String, String> map, String... xIds) {
    JsonArray json = loadingPlanStagingService.getAsEntityJson(map, array);
    JsonArray jsonArray = new JsonArray();
    JsonObject communicationRelatedIdMap = new JsonObject();
    for (JsonElement jsonElement : json) {
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
   * @throws GenericServiceException Exception on failure
   */
  public void checkCommunicationStatus(final Map<String, String> taskReqParams)
      throws GenericServiceException {

    // Status check only enabled for ship. Shore not implemented as retrial not done at shore
    if (isShip()) {

      // Get loadable study messages in envoy-client
      List<LoadingPlanCommunicationStatus> communicationStatusList =
          loadingPlanCommunicationStatusRepository
              .findByCommunicationStatusAndMessageTypeOrderByCommunicationDateTimeAsc(
                  CommunicationStatus.UPLOAD_WITH_HASH_VERIFIED.getId(),
                  MessageTypes.LOADINGPLAN.getMessageType())
              .orElse(Collections.emptyList());

      for (LoadingPlanCommunicationStatus communicationStatusRow : communicationStatusList) {

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
                .orElseThrow(RuntimeException::new);

        // Check timer and update timeout
        if (isCommunicationTimedOut(
            loadingInformation.getId(), communicationStatusRow.getCreatedDateTime())) {
          loadingPlanCommunicationStatusRepository.updateCommunicationStatus(
              CommunicationStatus.TIME_OUT.getId(), loadingInformation.getId());

          // Call fallback mechanism on timeout
          log.info("Algo call started for LoadingPlan");
          LoadingPlanModels.LoadingInfoAlgoRequest.Builder builder =
              LoadingPlanModels.LoadingInfoAlgoRequest.newBuilder();
          builder.setLoadingInfoId(loadingInformation.getId());
          LoadingPlanModels.LoadingInfoAlgoReply.Builder algoReplyBuilder =
              LoadingPlanModels.LoadingInfoAlgoReply.newBuilder();
          loadingPlanAlgoService.generateLoadingPlan(builder.build(), algoReplyBuilder);

          loadingPlanCommunicationStatusRepository.updateCommunicationStatus(
              CommunicationStatus.RETRY_AT_SOURCE.getId(), loadingInformation.getId());
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
}
