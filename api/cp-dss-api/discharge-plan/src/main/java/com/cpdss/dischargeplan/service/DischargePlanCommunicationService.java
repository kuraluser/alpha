/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.EntityDoc;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.common.utils.StagingStatus;
import com.cpdss.dischargeplan.communication.DischargePlanStagingService;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.*;
import com.cpdss.dischargeplan.service.grpc.DischargePlanRPCService;
import com.cpdss.dischargeplan.service.utility.DischargePlanConstants;
import com.cpdss.dischargeplan.service.utility.DischargePlanConstants.DischargingPlanTables;
import com.cpdss.dischargeplan.service.utility.ProcessIdentifiers;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Log4j2
@Service
@Transactional
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DischargePlanCommunicationService {
  // region Autowired
  @Autowired private DischargePlanStagingService dischargePlanStagingService;
  @Autowired private DischargePlanRPCService dischargePlanRPCService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("envoyReaderService")
  private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

  @GrpcClient("envoyWriterService")
  private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;

  @Autowired private DischargeInformationRepository dischargeInformationRepository;

  @Autowired
  @Qualifier("dischargeCommunicationInformationRepository")
  private DischargeCommunicationInformationRepository dischargeCommunicationInformationRepository;

  @Autowired private CowPlanDetailRepository cowPlanDetailRepository;
  @Autowired private CowWithDifferentCargoRepository cowWithDifferentCargoRepository;
  @Autowired private CowTankDetailRepository cowTankDetailRepository;
  @Autowired private DischargingDelayRepository dischargingDelayRepository;
  @Autowired private DischargingMachineryInUseRepository dischargingMachineryInUseRepository;
  @Autowired private DischargingDelayReasonRepository dischargingDelayReasonRepository;
  @Autowired private ReasonForDelayRepository reasonForDelayRepository;
  @Autowired private PortTideDetailsRepository portTideDetailsRepository;
  @Autowired private DischargingSequenceRepository dischargingSequenceRepository;
  @Autowired private DischargeBerthDetailRepository dischargeBerthDetailRepository;
  @Autowired private DischargeInformationStatusRepository dischargeInformationStatusRepository;
  @Autowired private DischargeStageDurationRepository dischargeStageDurationRepository;
  @Autowired private DischargeStageMinAmountRepository dischargeStageMinAmountRepository;

  @Autowired
  private DischargingInformationAlgoStatusRepository dischargingInformationAlgoStatusRepository;

  @Autowired private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @Autowired private AlgoErrorsRepository algoErrorsRepository;
  @Autowired private BallastValveRepository ballastValveRepository;
  @Autowired private CargoValveRepository cargoValveRepository;
  @Autowired private BallastingRateRepository ballastingRateRepository;
  @Autowired private DeballastingRateRepository deballastingRateRepository;

  @Autowired
  private DischargingPlanPortWiseDetailsRepository dischargingPlanPortWiseDetailsRepository;

  @Autowired
  private DischargingPlanBallastDetailsRepository dischargingPlanBallastDetailsRepository;

  @Autowired private DischargingPlanRobDetailsRepository dischargingPlanRobDetailsRepository;

  @Autowired
  private DischargingPlanStowageDetailsRepository dischargingPlanStowageDetailsRepository;

  @Autowired
  private DischargingPlanStabilityParametersRepository dischargingPlanStabilityParametersRepository;

  @Autowired
  private DischargePlanCommingleDetailsRepository dischargePlanCommingleDetailsRepository;

  @Autowired private CargoDischargingRateRepository cargoDischargingRateRepository;
  @Autowired private BallastOperationRepository ballastOperationRepository;

  @Autowired
  private DischargingSequenceStabiltyParametersRepository
      dischargingSequenceStabiltyParametersRepository;

  @Autowired
  private PortDischargingPlanBallastDetailsRepository portDischargingPlanBallastDetailsRepository;

  @Autowired
  private PortDischargingPlanRobDetailsRepository portDischargingPlanRobDetailsRepository;

  @Autowired
  private PortDischargingPlanStabilityParametersRepository
      portDischargingPlanStabilityParametersRepository;

  @Autowired
  private PortDischargingPlanStowageDetailsRepository portDischargingPlanStowageDetailsRepository;

  @Autowired
  private PortDischargingPlanCommingleDetailsRepository
      portDischargingPlanCommingleDetailsRepository;

  @Autowired
  private DischargingPlanCommingleDetailsRepository dischargingPlanCommingleDetailsRepository;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;
  // endregion
  // region declaration
  HashMap<String, Long> idMap = new HashMap<>();
  DischargeInformation dischargeInformation = null;
  List<DischargingInformationStatus> dischargingInformationStatus = null;
  List<DischargingStagesMinAmount> dischargingStagesMinAmount = null;
  List<DischargingStagesDuration> dischargingStagesDuration = null;
  // Discharge plan tables
  List<CowPlanDetail> cowPlanDetail = null;
  List<CowWithDifferentCargo> cowWithDifferentCargos = null;
  List<CowTankDetail> cowTankDetails = null;
  List<DischargingDelay> dischargingDelays = null;
  List<DischargingDelayReason> dischargingDelayReasons = null;
  List<DischargingMachineryInUse> dischargingMachineryInUses = null;
  List<PortTideDetail> portTideDetails = null;
  List<DischargingSequence> dischargingSequences = null;
  List<DischargingBerthDetail> dischargingBerthDetails = null;
  List<DischargingInformationAlgoStatus> dischargingInfoAlgoStatuses = null;
  List<AlgoErrorHeading> algoErrorHeadings = null;
  List<BallastValve> ballastValves = null;
  List<CargoValve> cargoValves = null;
  List<AlgoErrors> algoErrors = null;
  List<DischargingPlanPortWiseDetails> dischargingPlanPortWiseDetailsList = null;
  List<BallastingRate> ballastingRates = null;
  List<DeballastingRate> deballastingRates = null;
  List<DischargingPlanStowageDetails> dischargingPlanStowageDetailsList = null;
  List<DischargingPlanBallastDetails> dischargingPlanBallastDetailsList = null;
  List<DischargingPlanRobDetails> dischargingPlanRobDetailsList = null;
  List<DischargingPlanStabilityParameters> dischargingPlanStabilityParametersList = null;
  List<DischargingPlanCommingleDetails> dischargingPlanCommingleDetails = null;
  List<CargoDischargingRate> cargoDischargingRates = null;
  List<BallastOperation> ballastOperations = null;
  List<DischargingSequenceStabilityParameters> dischargingSequenceStabilityParamList = null;
  List<PortDischargingPlanBallastDetails> portDischargingPlanBallastDetailsList = null;
  List<PortDischargingPlanRobDetails> portDischargingPlanRobDetailsList = null;
  List<PortDischargingPlanStabilityParameters> portDischargingPlanStabilityParamList = null;
  List<PortDischargingPlanStowageDetails> portDischargingPlanStowageDetailsList = null;
  List<PortDischargingPlanCommingleDetails> portDischargingPlanCommingleDetailsList = null;
  String jsonData = null;
  String current_table_name = "";

  // endregion
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
        if (!DischargePlanConstants.SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
          throw new GenericServiceException(
              "Failed to get Result from Communication Server for: " + messageTypeGet,
              erReply.getResponseStatus().getCode(),
              HttpStatusCode.valueOf(Integer.valueOf(erReply.getResponseStatus().getCode())));
        }
        if (erReply != null && !erReply.getPatternResultJson().isEmpty()) {
          log.info("Data received from envoy reader for: " + messageType);
          saveDischargePlanIntoStagingTable(erReply);
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
  private void saveDischargePlanIntoStagingTable(EnvoyReader.EnvoyReaderResultReply erReply) {
    try {
      String jsonResult = erReply.getPatternResultJson();
      dischargePlanStagingService.save(jsonResult);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when save into the DischargePlan datatransfer table", e);
    } catch (ResourceAccessException e) {
      log.info("ResourceAccessException when save into the DischargePlan datatransfer table  ", e);

    } catch (Exception e) {
      log.error("Exception when save into the DischargePlan datatransfer table  ", e);
    }
  }

  // endregion
  // region Results From Envoy Reader
  private EnvoyReader.EnvoyReaderResultReply getResultFromEnvoyReader(
      Map<String, String> taskReqParams, String messageType) {
    log.info("Inside getResultFromEnvoyReader for message type:{}", messageType);
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
    if (!DischargePlanConstants.SUCCESS.equalsIgnoreCase(
        vesselResponse.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling vessel service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return vesselResponse.getVesselDetail();
  }

  // endregion
  // region Get Discharge plan stage
  public void getDischargePlanStagingData(String status, String env, String taskName)
      throws GenericServiceException {
    log.info("Inside getDischargePlanStagingData for env:{} and status:{}", env, status);
    String retryStatus = getRetryStatus(status);
    List<DataTransferStage> dataTransferStagesWithStatus = getDataTransferWithStatus(status);
    List<DataTransferStage> dataTransferStages =
        dataTransferStagesWithStatus.stream()
            .filter(
                dataTransfer ->
                    Arrays.asList(
                            MessageTypes.DISCHARGEPLAN.getMessageType(),
                            MessageTypes.DISCHARGEPLAN_ALGORESULT.getMessageType())
                        .contains(dataTransfer.getProcessGroupId()))
            .collect(Collectors.toList());
    log.info("DataTransferStages in DISCHARGE_PLAN_DATA_UPDATE task:" + dataTransferStages);
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
          dischargePlanStagingService.getAllWithStatusAndTime(
              status, LocalDateTime.now().minusMinutes(2));
    } else {
      dataTransferStagesWithStatus = dischargePlanStagingService.getAllWithStatus(status);
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
      dischargePlanStagingService.updateStatusForProcessId(
          processId, StagingStatus.IN_PROGRESS.getStatus());
      log.info(
          "updated status to in_progress for processId:{} and time:{}",
          processId,
          LocalDateTime.now());
      String processGroupId = entry.getValue().get(0).getProcessGroupId();
      createEntityObjects(entry);
      DischargeInformation dischargeInfo = null;
      try {
        dischargeInfo = saveDischargeInformation();
        CowPlanDetail cowPlanDetailObj = saveCowPlanDetail(dischargeInfo);
        saveCowWithDifferentCargos(cowPlanDetailObj);
        saveCowTankDetails(cowPlanDetailObj);
        saveDischargeDelay(dischargeInfo);
        saveDischargeDelayReason();
        saveDischargeMachineryInUse(dischargeInfo);
        savePortTideDetails();
        saveDischargeSequence(dischargeInfo);
        saveDischargeBerthDetails(dischargeInfo);
        saveDischargingInfoAlgoStatus(dischargeInfo);
        saveAlgoErrorHeadings(dischargeInfo);
        saveAlgoErrors();
        saveBallastValves();
        saveDischargeSequence();
        saveCargoDischargingRate();
        saveBallastOperation();
        saveDischargingPlanPortWiseDetails();
        saveBallastingRate();
        saveDeballastingRate();
        saveDischargingPlanStowageDetails();
        saveDischargePlanBallastDetailsList();
        saveDischargingPlanRobDetails();
        saveDischargingPlanStabilityParameters();
        saveDischargingPlanCommingleDetails();
        saveDischargeSequenceStabilityParam(dischargeInfo);
        savePortDischargingPlanBallastDetails(dischargeInfo);
        savePortDischargingPlanRobDetails(dischargeInfo);
        savePortDischargingPlanStabilityParam(dischargeInfo);
        savePortDischargingPlanStowageDetails(dischargeInfo);
        savePortDischargingPlanCommingDetails(dischargeInfo);
        saveJsonData();
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
      dischargePlanStagingService.updateStatusCompletedForProcessId(
          processId, StagingStatus.COMPLETED.getStatus());
      log.info("updated status to completed for processId:" + processId);
      if (!env.equals("ship") && dischargeInfo != null) {
        if (processGroupId.equals(MessageTypes.DISCHARGEPLAN.getMessageType())) {
          log.info("Algo call started for DischargePlan");
          DischargeInformationRequest.Builder builder = DischargeInformationRequest.newBuilder();
          builder.setDischargeInfoId(dischargeInfo.getId());
          dischargePlanRPCService.generateDischargePlan(builder.build(), null);
        }
      }
    }
  }

  // endregion
  // region create entity from json
  private void createEntityObjects(Map.Entry<String, List<DataTransferStage>> entry) {
    for (DataTransferStage dataTransferStage : entry.getValue()) {
      Type listType = null;
      String dataTransferString = dataTransferStage.getData();
      String data = replaceSpecialCharacters(dataTransferStage, dataTransferString);
      switch (ProcessIdentifiers.valueOf(dataTransferStage.getProcessIdentifier())) {
        case discharging_information:
          {
            HashMap<String, String> map =
                dischargePlanStagingService.getAttributeMapping(new DischargeInformation());
            JsonArray jsonArray =
                removeJsonFields(
                    JsonParser.parseString("[" + data + "]").getAsJsonArray(),
                    map,
                    "discharging_status_xid",
                    "stages_min_amount_xid",
                    "stages_duration_xid");

            idMap.put(
                DischargingPlanTables.DISCHARGING_INFORMATION.getTable(),
                dataTransferStage.getId());
            dischargeInformation =
                new Gson().fromJson(jsonArray.get(0), DischargeInformation.class);
            break;
          }
        case discharging_information_status:
          {
            Type type = new TypeToken<ArrayList<DischargingInformationStatus>>() {}.getType();
            dischargingInformationStatus =
                bindDataToEntity(
                    new DischargingInformationStatus(),
                    type,
                    DischargingPlanTables.DISCHARGING_INFORMATION_STATUS,
                    data,
                    dataTransferStage.getId(),
                    null);
            break;
          }
        case discharging_stages_min_amount:
          {
            Type type = new TypeToken<ArrayList<DischargingStagesMinAmount>>() {}.getType();
            dischargingStagesMinAmount =
                bindDataToEntity(
                    new DischargingStagesMinAmount(),
                    type,
                    DischargingPlanTables.DISCHARGING_STAGES_MIN_AMOUNT,
                    data,
                    dataTransferStage.getId(),
                    null);
            break;
          }
        case discharging_stages_duration:
          {
            Type type = new TypeToken<ArrayList<DischargingStagesDuration>>() {}.getType();
            dischargingStagesDuration =
                bindDataToEntity(
                    new DischargingStagesDuration(),
                    type,
                    DischargingPlanTables.DISCHARGING_STAGES_DURATION,
                    data,
                    dataTransferStage.getId(),
                    null);
            break;
          }
        case cow_plan_details:
          {
            Type type = new TypeToken<ArrayList<CowPlanDetail>>() {}.getType();
            cowPlanDetail =
                bindDataToEntity(
                    new CowPlanDetail(),
                    type,
                    DischargingPlanTables.COW_PLAN_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_xid");
            break;
          }
        case cow_with_different_cargo:
          {
            Type type = new TypeToken<ArrayList<CowWithDifferentCargo>>() {}.getType();
            cowWithDifferentCargos =
                bindDataToEntity(
                    new CowWithDifferentCargo(),
                    type,
                    DischargingPlanTables.COW_WITH_DIFFERENT_CARGO,
                    data,
                    dataTransferStage.getId(),
                    "cow_plan_details_xid");
            break;
          }
        case cow_tank_details:
          {
            Type type = new TypeToken<ArrayList<CowTankDetail>>() {}.getType();
            cowTankDetails =
                bindDataToEntity(
                    new CowTankDetail(),
                    type,
                    DischargingPlanTables.COW_TANK_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    "cow_plan_details_xid");
            break;
          }
        case discharging_delay:
          {
            Type type = new TypeToken<ArrayList<DischargingDelay>>() {}.getType();
            dischargingDelays =
                bindDataToEntity(
                    new DischargingDelay(),
                    type,
                    DischargingPlanTables.DISCHARGING_DELAY,
                    data,
                    dataTransferStage.getId(),
                    "discharging_xid");
            break;
          }
        case discharging_delay_reason:
          {
            Type type = new TypeToken<ArrayList<DischargingDelayReason>>() {}.getType();
            dischargingDelayReasons =
                bindDataToEntity(
                    new DischargingDelayReason(),
                    type,
                    DischargingPlanTables.DISCHARGING_DELAY_REASON,
                    data,
                    dataTransferStage.getId(),
                    "discharging_delay_xid",
                    "reason_xid");
            break;
          }
        case discharging_machinary_in_use:
          {
            Type type = new TypeToken<ArrayList<DischargingMachineryInUse>>() {}.getType();
            dischargingMachineryInUses =
                bindDataToEntity(
                    new DischargingMachineryInUse(),
                    type,
                    DischargingPlanTables.DISCHARGING_MACHINARY_IN_USE,
                    data,
                    dataTransferStage.getId(),
                    "discharging_xid");
            break;
          }
        case discharging_port_tide_details:
          {
            Type type = new TypeToken<ArrayList<PortTideDetail>>() {}.getType();
            portTideDetails =
                bindDataToEntity(
                    new PortTideDetail(),
                    type,
                    DischargingPlanTables.DISCHARGING_PORT_TIDE_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    null);

            break;
          }
        case discharging_berth_details:
          {
            Type type = new TypeToken<ArrayList<DischargingBerthDetail>>() {}.getType();
            dischargingBerthDetails =
                bindDataToEntity(
                    new DischargingBerthDetail(),
                    type,
                    DischargingPlanTables.DISCHARGING_BERTH_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_xid");
            break;
          }
        case discharging_information_algo_status:
          {
            Type type = new TypeToken<ArrayList<DischargingInformationAlgoStatus>>() {}.getType();
            dischargingInfoAlgoStatuses =
                bindDataToEntity(
                    new DischargingInformationAlgoStatus(),
                    type,
                    DischargingPlanTables.DISCHARGING_INFORMATION_ALGO_STATUS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_information_xid",
                    "discharging_information_status_xid");
            break;
          }
        case algo_error_heading:
          {
            Type type = new TypeToken<ArrayList<AlgoErrorHeading>>() {}.getType();
            algoErrorHeadings =
                bindDataToEntity(
                    new AlgoErrorHeading(),
                    type,
                    DischargingPlanTables.ALGO_ERROR_HEADING,
                    data,
                    dataTransferStage.getId(),
                    "discharging_information_xid");
            break;
          }
        case algo_errors:
          {
            Type type = new TypeToken<ArrayList<AlgoErrors>>() {}.getType();
            algoErrors =
                bindDataToEntity(
                    new AlgoErrors(),
                    type,
                    DischargingPlanTables.ALGO_ERRORS,
                    data,
                    dataTransferStage.getId(),
                    null);
            break;
          }
        case discharging_sequence:
          {
            Type type = new TypeToken<ArrayList<DischargingSequence>>() {}.getType();
            dischargingSequences =
                bindDataToEntity(
                    new DischargingSequence(),
                    type,
                    DischargingPlanTables.DISCHARGING_SEQUENCE,
                    data,
                    dataTransferStage.getId(),
                    "discharging_information_xid");
            break;
          }
        case ballast_valves:
          {
            Type type = new TypeToken<ArrayList<BallastValve>>() {}.getType();
            ballastValves =
                bindDataToEntity(
                    new BallastValve(),
                    type,
                    DischargingPlanTables.BALLAST_VALVES,
                    data,
                    dataTransferStage.getId(),
                    "discharging_sequence_xid");
            break;
          }
        case cargo_valves:
          {
            Type type = new TypeToken<ArrayList<CargoValve>>() {}.getType();
            cargoValves =
                bindDataToEntity(
                    new CargoValve(),
                    type,
                    DischargingPlanTables.CARGO_VALVES,
                    data,
                    dataTransferStage.getId(),
                    "discharging_sequence_xid");
            break;
          }
        case cargo_discharging_rate:
          {
            Type type = new TypeToken<ArrayList<CargoDischargingRate>>() {}.getType();
            cargoDischargingRates =
                bindDataToEntity(
                    new CargoDischargingRate(),
                    type,
                    DischargingPlanTables.CARGO_DISCHARGING_RATE,
                    data,
                    dataTransferStage.getId(),
                    "discharging_sequence_xid");
            break;
          }
        case ballast_operation:
          {
            Type type = new TypeToken<ArrayList<BallastOperation>>() {}.getType();
            ballastOperations =
                bindDataToEntity(
                    new BallastOperation(),
                    type,
                    DischargingPlanTables.BALLAST_OPERATION,
                    data,
                    dataTransferStage.getId(),
                    "discharging_sequences_xid");
            break;
          }
        case discharging_plan_portwise_details:
          {
            Type type = new TypeToken<ArrayList<DischargingPlanPortWiseDetails>>() {}.getType();
            dischargingPlanPortWiseDetailsList =
                bindDataToEntity(
                    new DischargingPlanPortWiseDetails(),
                    type,
                    DischargingPlanTables.DISCHARGING_PLAN_PORTWISE_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_sequences_xid");
            break;
          }
        case ballasting_rate:
          {
            Type type = new TypeToken<ArrayList<BallastingRate>>() {}.getType();
            ballastingRates =
                bindDataToEntity(
                    new BallastingRate(),
                    type,
                    DischargingPlanTables.BALLASTING_RATE,
                    data,
                    dataTransferStage.getId(),
                    "discharging_sequences_xid",
                    "discharging_plan_portwise_details_xid");
            break;
          }
        case deballasting_rate:
          {
            Type type = new TypeToken<ArrayList<DeballastingRate>>() {}.getType();
            deballastingRates =
                bindDataToEntity(
                    new DeballastingRate(),
                    type,
                    DischargingPlanTables.DEBALLASTING_RATE,
                    data,
                    dataTransferStage.getId(),
                    "discharging_sequences_xid",
                    "discharging_plan_portwise_details_xid");
            break;
          }
        case discharging_plan_stowage_details:
          {
            Type type = new TypeToken<ArrayList<DischargingPlanStowageDetails>>() {}.getType();
            dischargingPlanStowageDetailsList =
                bindDataToEntity(
                    new DischargingPlanStowageDetails(),
                    type,
                    DischargingPlanTables.DISCHARGING_PLAN_STOWAGE_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_plan_portwise_details_xid");
            break;
          }
        case discharging_plan_ballast_details:
          {
            Type type = new TypeToken<ArrayList<DischargingPlanBallastDetails>>() {}.getType();
            dischargingPlanBallastDetailsList =
                bindDataToEntity(
                    new DischargingPlanBallastDetails(),
                    type,
                    DischargingPlanTables.DISCHARGING_PLAN_BALLAST_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_plan_portwise_details_xid");
            break;
          }
        case discharging_plan_rob_details:
          {
            Type type = new TypeToken<ArrayList<DischargingPlanRobDetails>>() {}.getType();
            dischargingPlanRobDetailsList =
                bindDataToEntity(
                    new DischargingPlanRobDetails(),
                    type,
                    DischargingPlanTables.DISCHARGING_PLAN_ROB_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_plan_portwise_details_xid");
            break;
          }
        case discharging_plan_stability_parameters:
          {
            Type type = new TypeToken<ArrayList<DischargingPlanStabilityParameters>>() {}.getType();
            dischargingPlanStabilityParametersList =
                bindDataToEntity(
                    new DischargingPlanStabilityParameters(),
                    type,
                    DischargingPlanTables.DISCHARGING_PLAN_STABILITY_PARAMETERS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_plan_portwise_details_xid");
            break;
          }
        case discharging_plan_commingle_details:
          {
            Type type = new TypeToken<ArrayList<DischargingPlanCommingleDetails>>() {}.getType();
            dischargingPlanCommingleDetails =
                bindDataToEntity(
                    new DischargingPlanCommingleDetails(),
                    type,
                    DischargingPlanTables.DISCHARGING_PLAN_COMMINGLE_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_plan_portwise_details_xid");
            break;
          }
        case discharging_sequence_stability_parameters:
          {
            Type type =
                new TypeToken<ArrayList<DischargingSequenceStabilityParameters>>() {}.getType();
            dischargingPlanCommingleDetails =
                bindDataToEntity(
                    new DischargingSequenceStabilityParameters(),
                    type,
                    DischargingPlanTables.DISCHARGING_SEQUENCE_STABILITY_PARAMETERS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_information_xid");
            break;
          }
        case port_discharging_plan_stowage_ballast_details:
          {
            Type type = new TypeToken<ArrayList<PortDischargingPlanBallastDetails>>() {}.getType();
            portDischargingPlanBallastDetailsList =
                bindDataToEntity(
                    new PortDischargingPlanBallastDetails(),
                    type,
                    DischargingPlanTables.PORT_DISCHARGING_PLAN_STOWAGE_BALLAST_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_information_xid");
          }
        case port_discharging_plan_rob_details:
          {
            Type type = new TypeToken<ArrayList<PortDischargingPlanRobDetails>>() {}.getType();
            portDischargingPlanRobDetailsList =
                bindDataToEntity(
                    new PortDischargingPlanRobDetails(),
                    type,
                    DischargingPlanTables.PORT_DISCHARGING_PLAN_ROB_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    null);
            break;
          }
        case port_discharging_plan_stability_parameters:
          {
            Type type =
                new TypeToken<ArrayList<PortDischargingPlanStabilityParameters>>() {}.getType();
            portDischargingPlanStabilityParamList =
                bindDataToEntity(
                    new PortDischargingPlanStabilityParameters(),
                    type,
                    DischargingPlanTables.PORT_DISCHARGING_PLAN_STABILITY_PARAMETERS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_information_xid");
            break;
          }
        case port_discharging_plan_stowage_details:
          {
            Type type = new TypeToken<ArrayList<PortDischargingPlanStowageDetails>>() {}.getType();
            portDischargingPlanStowageDetailsList =
                bindDataToEntity(
                    new PortDischargingPlanStowageDetails(),
                    type,
                    DischargingPlanTables.PORT_DISCHARGING_PLAN_STOWAGE_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_information_xid");
            break;
          }
        case port_discharge_plan_commingle_details:
          {
            Type type =
                new TypeToken<ArrayList<PortDischargingPlanCommingleDetails>>() {}.getType();
            portDischargingPlanCommingleDetailsList =
                bindDataToEntity(
                    new PortDischargingPlanCommingleDetails(),
                    type,
                    DischargingPlanTables.PORT_DISCHARGE_PLAN_COMMINGLE_DETAILS,
                    data,
                    dataTransferStage.getId(),
                    "discharging_xid");
            break;
          }
        case json_data:
          {
            jsonData = dataTransferString;
            idMap.put(DischargingPlanTables.JSON_DATA.getTable(), dataTransferStage.getId());
            break;
          }
      }
    }
  }

  // endregion
  // region save to db
  private void savePortDischargingPlanCommingDetails(DischargeInformation dischargeInfo) {
    current_table_name = DischargingPlanTables.PORT_DISCHARGE_PLAN_COMMINGLE_DETAILS.getTable();
    if (dischargeInfo == null
        || portDischargingPlanCommingleDetailsList == null
        || portDischargingPlanCommingleDetailsList.isEmpty()) {
      log.info("Communication ++++ PORT_DISCHARGE_PLAN_COMMINGLE_DETAILS is empty");
      return;
    }
    for (PortDischargingPlanCommingleDetails portDischargingPlanCommingleDetails :
        portDischargingPlanCommingleDetailsList) {
      Long version = null;
      Optional<PortDischargingPlanCommingleDetails> portDischargingPlanCommingleDetailsObj =
          portDischargingPlanCommingleDetailsRepository.findById(
              portDischargingPlanCommingleDetails.getId());
      if (portDischargingPlanCommingleDetailsObj.isPresent()) {
        version = portDischargingPlanCommingleDetailsObj.get().getVersion();
      }
      portDischargingPlanCommingleDetails.setVersion(version);
      portDischargingPlanCommingleDetails.setDischargingInformation(dischargeInfo);
    }
    portDischargingPlanCommingleDetailsRepository.saveAll(portDischargingPlanCommingleDetailsList);
    log.info(
        "Communication ====  Saved PortDischargingPlanCommingleDetails:"
            + portDischargingPlanCommingleDetailsList);
  }

  private void savePortDischargingPlanStowageDetails(DischargeInformation dischargeInfo) {
    current_table_name = DischargingPlanTables.PORT_DISCHARGING_PLAN_STOWAGE_DETAILS.getTable();
    if (dischargeInfo == null
        || portDischargingPlanStowageDetailsList == null
        || portDischargingPlanStowageDetailsList.isEmpty()) {
      log.info(
          "Communication ++++ dischargeInfo or PORT_DISCHARGING_PLAN_STOWAGE_DETAILS is empty");
      return;
    }
    for (PortDischargingPlanStowageDetails portDischargingPlanStowageDetails :
        portDischargingPlanStowageDetailsList) {
      Long version = null;
      Optional<PortDischargingPlanStowageDetails> portDischargingPlanStowageDetailsObj =
          portDischargingPlanStowageDetailsRepository.findById(
              portDischargingPlanStowageDetails.getId());
      if (portDischargingPlanStowageDetailsObj.isPresent()) {
        version = portDischargingPlanStowageDetailsObj.get().getVersion();
      }
      portDischargingPlanStowageDetails.setVersion(version);
      portDischargingPlanStowageDetails.setDischargingInformation(dischargeInfo);
    }
    portDischargingPlanStowageDetailsRepository.saveAll(portDischargingPlanStowageDetailsList);
    log.info(
        "Communication ====  Saved PortDischargingPlanStowageDetails:"
            + portDischargingPlanStowageDetailsList);
  }

  private void savePortDischargingPlanStabilityParam(DischargeInformation dischargeInfo) {
    current_table_name =
        DischargingPlanTables.PORT_DISCHARGING_PLAN_STABILITY_PARAMETERS.getTable();
    if (dischargeInfo == null
        || portDischargingPlanStabilityParamList == null
        || portDischargingPlanStabilityParamList.isEmpty()) {
      log.info(
          "Communication ++++ dischargeInfo or PORT_DISCHARGING_PLAN_STABILITY_PARAMETERS is empty");
      return;
    }
    for (PortDischargingPlanStabilityParameters portDischargingPlanStabilityParameters :
        portDischargingPlanStabilityParamList) {
      Long version = null;
      Optional<PortDischargingPlanStabilityParameters> portDischargingPlanStabilityParamObj =
          portDischargingPlanStabilityParametersRepository.findById(
              portDischargingPlanStabilityParameters.getId());
      if (portDischargingPlanStabilityParamObj.isPresent()) {
        version = portDischargingPlanStabilityParamObj.get().getVersion();
      }
      portDischargingPlanStabilityParameters.setVersion(version);
      portDischargingPlanStabilityParameters.setDischargingInformation(dischargeInfo);
    }
    portDischargingPlanStabilityParametersRepository.saveAll(portDischargingPlanStabilityParamList);
    log.info(
        "Communication ====  Saved PortDischargingPlanStabilityParameters:"
            + portDischargingPlanStabilityParamList);
  }

  private void savePortDischargingPlanRobDetails(DischargeInformation dischargeInfo) {
    current_table_name = DischargingPlanTables.PORT_DISCHARGING_PLAN_ROB_DETAILS.getTable();
    if (dischargeInfo == null
        || portDischargingPlanRobDetailsList == null
        || portDischargingPlanRobDetailsList.isEmpty()) {
      log.info("Communication ++++ dischargeInfo or PORT_DISCHARGING_PLAN_ROB_DETAILS is empty");
      return;
    }
    for (PortDischargingPlanRobDetails portDischargingPlanRobDetails :
        portDischargingPlanRobDetailsList) {
      Long version = null;
      Optional<PortDischargingPlanRobDetails> portDischargingPlanRobDetailsObj =
          portDischargingPlanRobDetailsRepository.findById(portDischargingPlanRobDetails.getId());
      if (portDischargingPlanRobDetailsObj.isPresent()) {
        version = portDischargingPlanRobDetailsObj.get().getVersion();
      }
      portDischargingPlanRobDetails.setVersion(version);
      portDischargingPlanRobDetails.setDischargingInformation(dischargeInfo.getId());
    }
    portDischargingPlanRobDetailsRepository.saveAll(portDischargingPlanRobDetailsList);
    log.info(
        "Communication ====  Saved PortDischargingPlanRobDetails:"
            + portDischargingPlanRobDetailsList);
  }

  private void savePortDischargingPlanBallastDetails(DischargeInformation dischargeInfo) {
    current_table_name =
        DischargingPlanTables.PORT_DISCHARGING_PLAN_STOWAGE_BALLAST_DETAILS.getTable();
    if (dischargeInfo == null
        || portDischargingPlanBallastDetailsList == null
        || portDischargingPlanBallastDetailsList.isEmpty()) {
      log.info("Communication ++++ PORT_DISCHARGING_PLAN_STOWAGE_BALLAST_DETAILS is empty");
      return;
    }
    for (PortDischargingPlanBallastDetails portDischargingPlanBallastDetails :
        portDischargingPlanBallastDetailsList) {
      Long version = null;
      Optional<PortDischargingPlanBallastDetails> portDischargingPlanBallastDetailsObj =
          portDischargingPlanBallastDetailsRepository.findById(
              portDischargingPlanBallastDetails.getId());
      if (portDischargingPlanBallastDetailsObj.isPresent()) {
        version = portDischargingPlanBallastDetailsObj.get().getVersion();
      }
      portDischargingPlanBallastDetails.setVersion(version);
      portDischargingPlanBallastDetails.setDischargingInformation(dischargeInfo);
    }
    portDischargingPlanBallastDetailsRepository.saveAll(portDischargingPlanBallastDetailsList);
    log.info(
        "Communication ====  Saved PortDischargingPlanBallastDetails:"
            + portDischargingPlanBallastDetailsList);
  }

  private void saveDischargeSequenceStabilityParam(DischargeInformation dischargeInfo) {
    current_table_name = DischargingPlanTables.DISCHARGING_SEQUENCE_STABILITY_PARAMETERS.getTable();
    if (dischargeInfo == null
        || dischargingSequenceStabilityParamList == null
        || dischargingSequenceStabilityParamList.isEmpty()) {
      log.info("Communication ++++ DISCHARGING_SEQUENCE_STABILITY_PARAMETERS is empty");
      return;
    }
    for (DischargingSequenceStabilityParameters dischargingSequenceStabilityParam :
        dischargingSequenceStabilityParamList) {
      Long version = null;
      Optional<DischargingSequenceStabilityParameters> dischargingSequenceStabilityParamObj =
          dischargingSequenceStabiltyParametersRepository.findById(
              dischargingSequenceStabilityParam.getId());
      if (dischargingSequenceStabilityParamObj.isPresent()) {
        version = dischargingSequenceStabilityParamObj.get().getVersion();
      }
      dischargingSequenceStabilityParam.setVersion(version);
      dischargingSequenceStabilityParam.setDischargingInformation(dischargeInfo);
    }
    dischargingSequenceStabiltyParametersRepository.saveAll(dischargingSequenceStabilityParamList);
    log.info(
        "Communication ====  Saved DischargingSequenceStabilityParameters:"
            + dischargingSequenceStabilityParamList);
  }

  private void saveDischargingPlanCommingleDetails() {
    current_table_name = DischargingPlanTables.DISCHARGING_PLAN_COMMINGLE_DETAILS.getTable();
    if (dischargingPlanCommingleDetails == null || dischargingPlanCommingleDetails.isEmpty()) {
      log.info("Communication ++++ DISCHARGING_PLAN_COMMINGLE_DETAILS is empty");
      return;
    }
    if (dischargingPlanPortWiseDetailsList != null
        && !dischargingPlanPortWiseDetailsList.isEmpty()) {
      for (DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails :
          dischargingPlanPortWiseDetailsList) {
        for (DischargingPlanCommingleDetails dischargingPlanCommingleDetail :
            dischargingPlanCommingleDetails) {
          Long version = null;
          if (dischargingPlanPortWiseDetails
              .getId()
              .equals(dischargingPlanCommingleDetail.getDischargingPlanPortWiseDetails().getId())) {
            Optional<DischargingPlanCommingleDetails> dischargingPlanCommingleDetailObj =
                dischargingPlanCommingleDetailsRepository.findById(
                    dischargingPlanCommingleDetail.getId());
            if (dischargingPlanCommingleDetailObj.isPresent()) {
              version = dischargingPlanCommingleDetailObj.get().getVersion();
            }
            dischargingPlanCommingleDetail.setVersion(version);
            dischargingPlanCommingleDetail.setDischargingPlanPortWiseDetails(
                dischargingPlanPortWiseDetails);
          }
        }
      }
      dischargingPlanCommingleDetailsRepository.saveAll(dischargingPlanCommingleDetails);
      log.info(
          "Communication ====  Saved DischargingPlanCommingleDetails:"
              + dischargingPlanCommingleDetails);
    }
  }

  private void saveDischargingPlanStabilityParameters() {
    current_table_name = DischargingPlanTables.DISCHARGING_PLAN_STABILITY_PARAMETERS.getTable();
    if (dischargingPlanStabilityParametersList == null
        || dischargingPlanStabilityParametersList.isEmpty()) {
      log.info("Communication ++++ DISCHARGING_PLAN_STABILITY_PARAMETERS is empty");
      return;
    }
    if (dischargingPlanPortWiseDetailsList != null
        && !dischargingPlanPortWiseDetailsList.isEmpty()) {
      for (DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails :
          dischargingPlanPortWiseDetailsList) {
        for (DischargingPlanStabilityParameters dischargingPlanStabilityParameters :
            dischargingPlanStabilityParametersList) {
          Long version = null;
          if (dischargingPlanPortWiseDetails
              .getId()
              .equals(
                  dischargingPlanStabilityParameters.getDischargingPlanPortWiseDetails().getId())) {
            Optional<DischargingPlanStabilityParameters> dischargingPlanStabilityParametersObj =
                dischargingPlanStabilityParametersRepository.findById(
                    dischargingPlanStabilityParameters.getId());
            if (dischargingPlanStabilityParametersObj.isPresent()) {
              version = dischargingPlanStabilityParametersObj.get().getVersion();
            }
            dischargingPlanStabilityParameters.setVersion(version);
            dischargingPlanStabilityParameters.setDischargingPlanPortWiseDetails(
                dischargingPlanPortWiseDetails);
          }
        }
      }
      dischargingPlanStabilityParametersRepository.saveAll(dischargingPlanStabilityParametersList);
      log.info(
          "Communication ====  Saved DischargingPlanStabilityParameters:"
              + dischargingPlanStabilityParametersList);
    }
  }

  private void saveDischargingPlanRobDetails() {
    current_table_name = DischargingPlanTables.DISCHARGING_PLAN_ROB_DETAILS.getTable();
    if (dischargingPlanRobDetailsList == null || dischargingPlanRobDetailsList.isEmpty()) {
      log.info("Communication ++++ DISCHARGING_PLAN_ROB_DETAILS is empty");
      return;
    }
    if (dischargingPlanPortWiseDetailsList != null
        && !dischargingPlanPortWiseDetailsList.isEmpty()) {
      for (DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails :
          dischargingPlanPortWiseDetailsList) {
        for (DischargingPlanRobDetails dischargingPlanRobDetails : dischargingPlanRobDetailsList) {
          Long version = null;
          if (dischargingPlanPortWiseDetails
              .getId()
              .equals(dischargingPlanRobDetails.getDischargingPlanPortWiseDetails().getId())) {
            Optional<DischargingPlanRobDetails> dischargingPlanRobDetailsObj =
                dischargingPlanRobDetailsRepository.findById(dischargingPlanRobDetails.getId());
            if (dischargingPlanRobDetailsObj.isPresent()) {
              version = dischargingPlanRobDetailsObj.get().getVersion();
            }
            dischargingPlanRobDetails.setVersion(version);
            dischargingPlanRobDetails.setDischargingPlanPortWiseDetails(
                dischargingPlanPortWiseDetails);
          }
        }
      }
      dischargingPlanRobDetailsRepository.saveAll(dischargingPlanRobDetailsList);
      log.info(
          "Communication ====  Saved DischargingPlanRobDetails:" + dischargingPlanRobDetailsList);
    }
  }

  private void saveDischargePlanBallastDetailsList() {
    current_table_name = DischargingPlanTables.DISCHARGING_PLAN_BALLAST_DETAILS.getTable();
    if (dischargingPlanBallastDetailsList == null || dischargingPlanBallastDetailsList.isEmpty()) {
      log.info("Communication ++++ DISCHARGING_PLAN_BALLAST_DETAILS is empty");
      return;
    }
    if (dischargingPlanPortWiseDetailsList != null
        && !dischargingPlanPortWiseDetailsList.isEmpty()) {
      for (DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails :
          dischargingPlanPortWiseDetailsList) {
        for (DischargingPlanBallastDetails dischargingPlanBallastDetails :
            dischargingPlanBallastDetailsList) {
          Long version = null;
          if (dischargingPlanPortWiseDetails
              .getId()
              .equals(dischargingPlanBallastDetails.getDischargingPlanPortWiseDetails().getId())) {
            Optional<DischargingPlanBallastDetails> dischargingPlanBallastDetailsObj =
                dischargingPlanBallastDetailsRepository.findById(
                    dischargingPlanBallastDetails.getId());
            if (dischargingPlanBallastDetailsObj.isPresent()) {
              version = dischargingPlanBallastDetailsObj.get().getVersion();
            }
            dischargingPlanBallastDetails.setVersion(version);
            dischargingPlanBallastDetails.setDischargingPlanPortWiseDetails(
                dischargingPlanPortWiseDetails);
          }
        }
      }
      dischargingPlanBallastDetailsRepository.saveAll(dischargingPlanBallastDetailsList);
      log.info(
          "Communication ====  Saved DischargingPlanBallastDetails:"
              + dischargingPlanBallastDetailsList);
    }
  }

  private void saveDischargingPlanStowageDetails() {
    current_table_name = DischargingPlanTables.DISCHARGING_PLAN_STOWAGE_DETAILS.getTable();
    if (dischargingPlanStowageDetailsList == null || dischargingPlanStowageDetailsList.isEmpty()) {
      log.info("Communication ++++ DISCHARGING_PLAN_STOWAGE_DETAILS is empty");
      return;
    }
    if (dischargingPlanPortWiseDetailsList != null
        && !dischargingPlanPortWiseDetailsList.isEmpty()) {
      for (DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails :
          dischargingPlanPortWiseDetailsList) {
        for (DischargingPlanStowageDetails dischargingPlanStowageDetails :
            dischargingPlanStowageDetailsList) {
          Long version = null;
          if (dischargingPlanPortWiseDetails
              .getId()
              .equals(dischargingPlanStowageDetails.getDischargingPlanPortWiseDetails().getId())) {
            Optional<DischargingPlanStowageDetails> dischargingPlanStowageDetailsObj =
                dischargingPlanStowageDetailsRepository.findById(
                    dischargingPlanStowageDetails.getId());
            if (dischargingPlanStowageDetailsObj.isPresent()) {
              version = dischargingPlanStowageDetailsObj.get().getVersion();
            }
            dischargingPlanStowageDetails.setVersion(version);
            dischargingPlanStowageDetails.setDischargingPlanPortWiseDetails(
                dischargingPlanPortWiseDetails);
          }
        }
      }
      dischargingPlanStowageDetailsRepository.saveAll(dischargingPlanStowageDetailsList);
      log.info("Communication ====  Saved DischargingPlanStowageDetails:" + deballastingRates);
    }
  }

  private void saveDeballastingRate() {
    current_table_name = DischargingPlanTables.DEBALLASTING_RATE.getTable();
    if (deballastingRates == null || deballastingRates.isEmpty()) {
      log.info("Communication ++++ DEBALLASTING_RATE is empty");
      return;
    }
    if (dischargingSequences != null && !dischargingSequences.isEmpty()) {
      for (DischargingSequence dischargingSequence : dischargingSequences) {
        for (DeballastingRate deballastingRate : deballastingRates) {
          Long version = null;
          if (dischargingSequence
              .getId()
              .equals(deballastingRate.getDischargingSequence().getId())) {
            Optional<DeballastingRate> deballastingRateObj =
                deballastingRateRepository.findById(deballastingRate.getId());
            if (deballastingRateObj.isPresent()) {
              version = deballastingRateObj.get().getVersion();
            }
            deballastingRate.setVersion(version);
            deballastingRate.setDischargingSequence(dischargingSequence);
          }
        }
      }
      deballastingRateRepository.saveAll(deballastingRates);
      log.info("Communication ====  Saved DeballastingRate:" + deballastingRates);
    }
  }

  private void saveBallastingRate() {
    current_table_name = DischargingPlanTables.BALLASTING_RATE.getTable();
    if (ballastingRates == null || ballastingRates.isEmpty()) {
      log.info("Communication ++++ BALLASTING_RATE is empty");
      return;
    }
    if (dischargingSequences != null && !dischargingSequences.isEmpty()) {
      for (DischargingSequence dischargingSequence : dischargingSequences) {
        for (BallastingRate ballastingRate : ballastingRates) {
          Long version = null;
          if (dischargingSequence.getId().equals(ballastingRate.getDischargingSequence().getId())) {
            Optional<BallastingRate> ballastingRateObj =
                ballastingRateRepository.findById(ballastingRate.getId());
            if (ballastingRateObj.isPresent()) {
              version = ballastingRateObj.get().getVersion();
            }
            ballastingRate.setVersion(version);
            ballastingRate.setDischargingSequence(dischargingSequence);
          }
        }
      }
      ballastingRateRepository.saveAll(ballastingRates);
      log.info("Communication ====  Saved BallastingRate:" + ballastingRates);
    }
  }

  private void saveDischargingPlanPortWiseDetails() {
    current_table_name = DischargingPlanTables.DISCHARGING_PLAN_PORTWISE_DETAILS.getTable();
    if (dischargingPlanPortWiseDetailsList == null
        || dischargingPlanPortWiseDetailsList.isEmpty()) {
      log.info("Communication ++++ DISCHARGING_PLAN_PORTWISE_DETAILS is empty");
      return;
    }
    if (dischargingSequences != null && !dischargingSequences.isEmpty()) {
      for (DischargingSequence dischargingSequence : dischargingSequences) {
        for (DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails :
            dischargingPlanPortWiseDetailsList) {
          Long version = null;
          if (dischargingSequence
              .getId()
              .equals(dischargingPlanPortWiseDetails.getDischargingSequence().getId())) {
            Optional<DischargingPlanPortWiseDetails> ballastOperationObj =
                dischargingPlanPortWiseDetailsRepository.findById(
                    dischargingPlanPortWiseDetails.getId());
            if (ballastOperationObj.isPresent()) {
              version = ballastOperationObj.get().getVersion();
            }
            dischargingPlanPortWiseDetails.setVersion(version);
            dischargingPlanPortWiseDetails.setDischargingSequence(dischargingSequence);
          }
        }
      }
      dischargingPlanPortWiseDetailsRepository.saveAll(dischargingPlanPortWiseDetailsList);
      log.info(
          "Communication ====  Saved DischargingPlanPortWiseDetails:"
              + dischargingPlanPortWiseDetailsList);
    }
  }

  private void saveBallastOperation() {
    current_table_name = DischargingPlanTables.BALLAST_OPERATION.getTable();
    if (ballastOperations == null || ballastOperations.isEmpty()) {
      log.info("Communication ++++ BALLAST_OPERATION is empty");
      return;
    }
    if (dischargingSequences != null && !dischargingSequences.isEmpty()) {
      for (DischargingSequence dischargingSequence : dischargingSequences) {
        for (BallastOperation ballastOperation : ballastOperations) {
          Long version = null;
          if (dischargingSequence
              .getId()
              .equals(ballastOperation.getDischargingSequence().getId())) {
            Optional<BallastOperation> ballastOperationObj =
                ballastOperationRepository.findById(ballastOperation.getId());
            if (ballastOperationObj.isPresent()) {
              version = ballastOperationObj.get().getVersion();
            }
            ballastOperation.setVersion(version);
            ballastOperation.setDischargingSequence(dischargingSequence);
          }
        }
      }
      ballastOperationRepository.saveAll(ballastOperations);
      log.info("Communication ====  Saved BallastOperation:" + ballastOperations);
    }
  }

  private void saveCargoDischargingRate() {
    current_table_name = DischargingPlanTables.CARGO_DISCHARGING_RATE.getTable();
    if (cargoDischargingRates == null || cargoDischargingRates.isEmpty()) {
      log.info("Communication ++++ CARGO_DISCHARGING_RATE is empty");
      return;
    }
    if (dischargingSequences != null && !dischargingSequences.isEmpty()) {
      for (DischargingSequence dischargingSequence : dischargingSequences) {
        for (CargoDischargingRate cargoDischargingRate : cargoDischargingRates) {
          Long version = null;
          if (dischargingSequence
              .getId()
              .equals(cargoDischargingRate.getDischargingSequence().getId())) {
            Optional<CargoDischargingRate> cargoDischargingRateObj =
                cargoDischargingRateRepository.findById(cargoDischargingRate.getId());
            if (cargoDischargingRateObj.isPresent()) {
              version = cargoDischargingRateObj.get().getVersion();
            }
            cargoDischargingRate.setVersion(version);
            cargoDischargingRate.setDischargingSequence(dischargingSequence);
          }
        }
      }
      cargoDischargingRateRepository.saveAll(cargoDischargingRates);
      log.info("Communication ====  Saved CargoDischargingRate:" + cargoDischargingRates);
    }
  }

  private void saveDischargeSequence() {
    current_table_name = DischargingPlanTables.DISCHARGING_SEQUENCE.getTable();
    if (dischargingSequences == null || dischargingSequences.isEmpty()) {
      log.info("Communication ++++ DISCHARGING_SEQUENCE is empty");
      return;
    }
    for (DischargingSequence dischargingSequence : dischargingSequences) {
      for (CargoValve cargoValve : cargoValves) {
        Long version = null;
        if (dischargingSequence.getId().equals(cargoValve.getDischargingSequence().getId())) {
          Optional<CargoValve> cargoValveObj = cargoValveRepository.findById(cargoValve.getId());
          if (cargoValveObj.isPresent()) {
            version = cargoValveObj.get().getVersion();
          }
          cargoValve.setVersion(version);
          cargoValve.setDischargingSequence(dischargingSequence);
        }
      }
    }
    cargoValveRepository.saveAll(cargoValves);
    log.info("Communication ====  Saved CargoValve:" + cargoValves);
  }

  private void saveBallastValves() {
    current_table_name = DischargingPlanTables.BALLAST_VALVES.getTable();
    if (ballastValves == null || ballastValves.isEmpty()) {
      log.info("Communication ++++ BALLAST_VALVES is empty");
      return;
    }
    if (dischargingSequences != null && !dischargingSequences.isEmpty()) {
      for (DischargingSequence dischargingSequence : dischargingSequences) {
        for (BallastValve ballastValve : ballastValves) {
          Long version = null;
          if (dischargingSequence.getId().equals(ballastValve.getDischargingSequence().getId())) {
            Optional<BallastValve> ballastValveObj =
                ballastValveRepository.findById(ballastValve.getId());
            if (ballastValveObj.isPresent()) {
              version = ballastValveObj.get().getVersion();
            }
            ballastValve.setVersion(version);
            ballastValve.setDischargingSequence(dischargingSequence);
          }
        }
      }
      ballastValveRepository.saveAll(ballastValves);
      log.info("Communication ====  Saved BallastValve:" + ballastValves);
    }
  }

  private void saveAlgoErrors() {
    current_table_name = DischargingPlanTables.ALGO_ERRORS.getTable();
    if (algoErrors == null || algoErrors.isEmpty()) {
      log.info("Communication ++++ ALGO_ERRORS is empty");
      return;
    }
    if (algoErrorHeadings != null && !algoErrorHeadings.isEmpty()) {
      for (AlgoErrorHeading algoErrorHeading : algoErrorHeadings) {
        for (AlgoErrors algoError : algoErrors) {
          Long version = null;
          if (algoErrorHeading.getId().equals(algoError.getAlgoErrorHeading().getId())) {
            Optional<AlgoErrors> algoErrorObj = algoErrorsRepository.findById(algoError.getId());
            if (algoErrorObj.isPresent()) {
              version = algoErrorObj.get().getVersion();
            }
            algoError.setVersion(version);
            algoError.setAlgoErrorHeading(algoErrorHeading);
          }
        }
      }
      algoErrorsRepository.saveAll(algoErrors);
      log.info("Communication ====  Saved AlgoErrors:" + algoErrors);
    }
  }

  private void saveAlgoErrorHeadings(DischargeInformation dischargeInfo) {
    current_table_name = DischargingPlanTables.ALGO_ERROR_HEADING.getTable();
    if (dischargeInfo == null || algoErrorHeadings == null || algoErrorHeadings.isEmpty()) {
      log.info("Communication ++++ dischargeInfo or ALGO_ERROR_HEADING is empty");
      return;
    }
    for (AlgoErrorHeading algoErrorHeading : algoErrorHeadings) {
      Long version = null;
      Optional<AlgoErrorHeading> algoErrorHeadingObj =
          algoErrorHeadingRepository.findById(algoErrorHeading.getId());
      if (algoErrorHeadingObj.isPresent()) {
        version = algoErrorHeadingObj.get().getVersion();
      }
      algoErrorHeading.setVersion(version);
      algoErrorHeading.setDischargingInformation(dischargeInfo);
    }
    algoErrorHeadingRepository.saveAll(algoErrorHeadings);
    log.info("Communication ====  Saved AlgoErrorHeading:" + algoErrorHeadings);
  }

  private void saveDischargingInfoAlgoStatus(DischargeInformation dischargeInfo) {
    current_table_name = DischargingPlanTables.DISCHARGING_INFORMATION_ALGO_STATUS.getTable();
    if (dischargeInfo == null
        || dischargingInfoAlgoStatuses == null
        || dischargingInfoAlgoStatuses.isEmpty()) {
      log.info("Communication ++++ dischargeInfo or dischargingInfoAlgoStatus is empty");
      return;
    }
    for (DischargingInformationAlgoStatus dischargingInformationAlgoStatus :
        dischargingInfoAlgoStatuses) {
      Long version = null;
      Optional<DischargingInformationAlgoStatus> dischargingInfoAlgoStatusObj =
          dischargingInformationAlgoStatusRepository.findById(
              dischargingInformationAlgoStatus.getId());
      if (dischargingInfoAlgoStatusObj.isPresent()) {
        version = dischargingInfoAlgoStatusObj.get().getVersion();
      }
      Optional<DischargingInformationStatus> dischargingInformationStatusOpt =
          dischargeInformationStatusRepository.findByIdAndIsActive(
              dischargingInformationAlgoStatus.getDischargingInformationStatus().getId(), true);
      if (dischargingInformationStatusOpt.isPresent()) {
        dischargingInformationAlgoStatus.setDischargingInformationStatus(
            dischargingInformationStatusOpt.get());
      }
      dischargingInformationAlgoStatus.setVersion(version);
      dischargingInformationAlgoStatus.setDischargeInformation(dischargeInfo);
    }
    dischargingInformationAlgoStatusRepository.saveAll(dischargingInfoAlgoStatuses);
    log.info(
        "Communication ====  Saved DischargingInformationAlgoStatus:"
            + dischargingInfoAlgoStatuses);
  }

  private void saveDischargeBerthDetails(DischargeInformation dischargeInfo) {
    current_table_name = DischargingPlanTables.DISCHARGING_BERTH_DETAILS.getTable();
    if (dischargeInfo == null
        || dischargingBerthDetails == null
        || dischargingBerthDetails.isEmpty()) {
      log.info("Communication ++++ dischargeInfo or DISCHARGING_BERTH_DETAILS is empty");
      return;
    }
    for (DischargingBerthDetail dischargingBerthDetail : dischargingBerthDetails) {
      Long version = null;
      Optional<DischargingBerthDetail> dischargingBerthDetailObj =
          dischargeBerthDetailRepository.findById(dischargingBerthDetail.getId());
      if (dischargingBerthDetailObj.isPresent()) {
        version = dischargingBerthDetailObj.get().getVersion();
      }
      dischargingBerthDetail.setVersion(version);
      dischargingBerthDetail.setDischargingInformation(dischargeInfo);
    }
    dischargeBerthDetailRepository.saveAll(dischargingBerthDetails);
    log.info("Communication ====  Saved DischargingBerthDetail:" + dischargingBerthDetails);
  }

  private void saveDischargeSequence(DischargeInformation dischargeInfo) {
    current_table_name = DischargingPlanTables.DISCHARGING_SEQUENCE.getTable();
    if (dischargeInfo == null || dischargingSequences == null || !dischargingSequences.isEmpty()) {
      log.info("Communication ++++ dischargeInfo or DISCHARGING_SEQUENCE is empty");
      return;
    }
    for (DischargingSequence dischargingSequence : dischargingSequences) {
      Long version = null;
      Optional<DischargingSequence> dischargingSequenceObj =
          dischargingSequenceRepository.findById(dischargingSequence.getId());
      if (dischargingSequenceObj.isPresent()) {
        version = dischargingSequenceObj.get().getVersion();
      }
      dischargingSequence.setVersion(version);
      dischargingSequence.setDischargeInformation(dischargeInfo);
    }
    dischargingSequenceRepository.saveAll(dischargingSequences);
    log.info("Communication ====  Saved DischargingSequence:" + dischargingSequences);
  }

  private void savePortTideDetails() {
    current_table_name = DischargingPlanTables.DISCHARGING_PORT_TIDE_DETAILS.getTable();
    if (portTideDetails == null || portTideDetails.isEmpty()) {
      log.info("Communication ++++ portTideDetails is empty");
      return;
    }
    for (PortTideDetail portTideDetail : portTideDetails) {
      Long version = null;
      Optional<PortTideDetail> portTideDetailObj =
          portTideDetailsRepository.findById(portTideDetail.getId());
      if (portTideDetailObj.isPresent()) {
        version = portTideDetailObj.get().getVersion();
      }
      portTideDetail.setVersion(version);
    }
    portTideDetailsRepository.saveAll(portTideDetails);
    log.info("Communication ====  Saved PortTideDetail:" + portTideDetails);
  }

  private void saveDischargeMachineryInUse(DischargeInformation dischargeInfo) {
    if (dischargeInfo == null
        || dischargingMachineryInUses == null
        || dischargingMachineryInUses.isEmpty()) {
      log.info("Communication ++++ dischargeInfo or dischargingMachineryInUse is empty");
      return;
    }
    for (DischargingMachineryInUse dischargingMachineryInUse : dischargingMachineryInUses) {
      Long version = null;
      Optional<DischargingMachineryInUse> dischargingMachineryInUseObj =
          dischargingMachineryInUseRepository.findById(dischargingMachineryInUse.getId());
      if (dischargingMachineryInUseObj.isPresent()) {
        version = dischargingMachineryInUseObj.get().getVersion();
      }
      dischargingMachineryInUse.setVersion(version);
      dischargingMachineryInUse.setDischargingInformation(dischargeInfo);
    }
    dischargingMachineryInUseRepository.saveAll(dischargingMachineryInUses);
    log.info("Communication ====  Saved DischargingMachineryInUse:" + dischargingMachineryInUses);
  }

  private void saveDischargeDelayReason() {
    current_table_name = DischargingPlanTables.DISCHARGING_DELAY_REASON.getTable();
    if (dischargingDelayReasons == null || dischargingDelayReasons.isEmpty()) {
      log.info("Communication ++++ dDischargingDelayReason is empty");
      return;
    }
    if (dischargingDelays != null && !dischargingDelays.isEmpty()) {
      for (DischargingDelayReason dischargingDelayReason : dischargingDelayReasons) {
        Optional<DischargingDelayReason> dischargingDelayReasonObj =
            dischargingDelayReasonRepository.findById(dischargingDelayReason.getId());
        dischargingDelayReason.setVersion(
            dischargingDelayReasonObj.map(EntityDoc::getVersion).orElse(null));
        // Set Loading Delay details
        dischargingDelayReason.setDischargingDelay(
            emptyIfNull(dischargingDelays).stream()
                .filter(
                    dischargingDelay ->
                        dischargingDelay
                            .getId()
                            .equals(
                                dischargingDelayReason
                                    .getCommunicationRelatedIdMap()
                                    .get("discharging_delay_xid")))
                .findFirst()
                .orElse(null));
        Long reasonForDelay =
            dischargingDelayReason.getCommunicationRelatedIdMap().get("reason_xid");
        if (reasonForDelay != null) {
          Optional<ReasonForDelay> reasonForDelayOpt =
              reasonForDelayRepository.findByIdAndIsActiveTrue(reasonForDelay);
          if (reasonForDelayOpt.isPresent()) {
            dischargingDelayReason.setReasonForDelay(reasonForDelayOpt.get());
          }
        }
      }
      dischargingDelayReasonRepository.saveAll(dischargingDelayReasons);
      log.info("Communication ====  Saved DischargingDelayReason:" + dischargingDelayReasons);
    }
  }

  private void saveDischargeDelay(DischargeInformation dischargeInfo) {
    current_table_name = DischargingPlanTables.DISCHARGING_DELAY.getTable();
    if (dischargeInfo == null || dischargingDelays == null || dischargingDelays.isEmpty()) {
      log.info("Communication ++++ dischargeInfo or DISCHARGING_DELAY is empty");
      return;
    }
    for (DischargingDelay dischargingDelay : dischargingDelays) {
      Long version = null;
      Optional<DischargingDelay> dischargingDelayObj =
          dischargingDelayRepository.findById(dischargingDelay.getId());
      if (dischargingDelayObj.isPresent()) {
        version = dischargingDelayObj.get().getVersion();
      }
      dischargingDelay.setVersion(version);
      dischargingDelay.setDischargingInformation(dischargeInfo);
    }
    dischargingDelayRepository.saveAll(dischargingDelays);
    log.info("Communication ====  Saved DischargingDelay:" + dischargingDelays);
  }

  private void saveCowTankDetails(CowPlanDetail cowPlanDetailObj) {
    current_table_name = DischargingPlanTables.COW_TANK_DETAILS.getTable();
    if (cowTankDetails == null || cowTankDetails.isEmpty()) {
      log.info("Communication ++++ CowTankDetail is empty");
      return;
    }

    for (CowTankDetail cowTankDetail : cowTankDetails) {
      Long version = null;
      Optional<CowTankDetail> cowTankDetailObj =
          cowTankDetailRepository.findById(cowTankDetail.getId());
      if (cowTankDetailObj.isPresent()) {
        version = cowTankDetailObj.get().getVersion();
      }
      cowTankDetail.setVersion(version);
      cowTankDetail.setCowPlanDetail(cowPlanDetailObj);
    }
    cowTankDetailRepository.saveAll(cowTankDetails);
    log.info("Communication ====  Saved CowTankDetail:" + cowTankDetails);
  }

  private void saveCowWithDifferentCargos(CowPlanDetail cowPlanDetailObj) {
    current_table_name = DischargingPlanTables.COW_WITH_DIFFERENT_CARGO.getTable();
    if (cowWithDifferentCargos == null || cowWithDifferentCargos.isEmpty()) {
      log.info("Communication ++++ CowWithDifferentCargo is empty");
      return;
    }
    for (CowWithDifferentCargo cowWithDifferentCargo : cowWithDifferentCargos) {
      Long version = null;
      Optional<CowWithDifferentCargo> cowWithDifferentCargoObj =
          cowWithDifferentCargoRepository.findById(cowWithDifferentCargo.getId());
      if (cowWithDifferentCargoObj.isPresent()) {
        version = cowWithDifferentCargoObj.get().getVersion();
      }
      cowWithDifferentCargo.setVersion(version);
      cowWithDifferentCargo.setCowPlanDetail(cowPlanDetailObj);
    }
    cowWithDifferentCargoRepository.saveAll(cowWithDifferentCargos);
    log.info("Communication ====  Saved CowWithDifferentCargo:" + cowWithDifferentCargos);
  }

  // region save cow plan
  private CowPlanDetail saveCowPlanDetail(DischargeInformation dischargeInfo) {
    CowPlanDetail cowPlanDetailObj = null;
    current_table_name = DischargingPlanTables.COW_PLAN_DETAILS.getTable();
    if (dischargeInfo == null || cowPlanDetail == null) {
      log.info("Communication ++++ CowPlanDetail or Discharge Info is empty");
      return null;
    }
    cowPlanDetail.get(0).setDischargeInformation(dischargeInfo);
    cowPlanDetailObj = cowPlanDetailRepository.save(cowPlanDetail.get(0));
    log.info("Communication ==== Saved CowPlanDetail:" + cowPlanDetail);
    return cowPlanDetailObj;
  }
  // region dave Json Data
  private void saveJsonData() throws Exception {
    current_table_name = DischargingPlanTables.JSON_DATA.getTable();
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
    if (DischargePlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      log.info("JsonData saved in LoadableStudy ");
    } else if (DischargePlanConstants.FAILED_WITH_RESOURCE_EXC.equals(
        reply.getResponseStatus().getStatus())) {
      log.error("ResourceAccessException occurred when json_data save");
      throw new ResourceAccessException(reply.getResponseStatus().getMessage());
    } else if (DischargePlanConstants.FAILED_WITH_EXC.equals(
        reply.getResponseStatus().getStatus())) {
      log.error("Exception occurred when json_data save");
      throw new Exception(reply.getResponseStatus().getMessage());
    }
  }
  // endregion

  private DischargeInformation saveDischargeInformation() {
    DischargeInformation dischargeInfo = null;
    current_table_name = DischargingPlanTables.DISCHARGING_INFORMATION.getTable();
    if (dischargeInformation == null) {
      log.info("Communication ++++ DischargeInformation is empty");
      return null;
    }
    setDischargeInformationStatus();
    setDischargeStatusMinAmount();
    setDischargeStatusDuration();
    Long version = null;
    Optional<DischargeInformation> dischargeInfoObj =
        dischargeCommunicationInformationRepository.findById(dischargeInformation.getId());
    if (dischargeInfoObj.isPresent()) {
      version = dischargeInfoObj.get().getVersion();
    }
    dischargeInformation.setVersion(version);
    dischargeInfo = dischargeCommunicationInformationRepository.save(dischargeInformation);
    log.info(
        "Communication ====  DischargeInformation saved with id:" + dischargeInformation.getId());
    return dischargeInfo;
  }

  // endregion
  // region set discharge Status duration
  private void setDischargeStatusDuration() {
    if (dischargingStagesDuration != null) {
      Optional<DischargingStagesDuration> dischargingStagesDurationOpt =
          dischargeStageDurationRepository.findByIdAndIsActiveTrue(
              dischargingStagesDuration.get(0).getId());
      if (dischargingStagesDurationOpt.isPresent()) {
        dischargeInformation.setDischargingStagesDuration(dischargingStagesDurationOpt.get());
      }
    }
  }

  // endregion
  // region set discharge status min amount
  private void setDischargeStatusMinAmount() {
    if (dischargingStagesMinAmount != null) {
      Optional<DischargingStagesMinAmount> dischargingStagesMinAmountOpt =
          dischargeStageMinAmountRepository.findByIdAndIsActiveTrue(
              dischargingStagesMinAmount.get(0).getId());
      if (dischargingStagesMinAmountOpt.isPresent()) {
        dischargeInformation.setDischargingStagesMinAmount(dischargingStagesMinAmountOpt.get());
      }
    }
  }

  // endregion
  // region set discharge information status
  private void setDischargeInformationStatus() {
    if (dischargingInformationStatus != null) {
      Optional<DischargingInformationStatus> dischargingInformationStatusOpt =
          dischargeInformationStatusRepository.findByIdAndIsActive(
              dischargingInformationStatus.get(0).getId(), true);
      if (dischargingInformationStatusOpt.isPresent()) {
        dischargeInformation.setDischargingInformationStatus(dischargingInformationStatusOpt.get());
      }
    }
  }

  // endregion
  // region update status in the case exception
  private void updateStatusInExceptionCase(
      Long id, String processId, String status, String statusDescription) {
    dischargePlanStagingService.updateStatusAndStatusDescriptionForId(
        id, status, statusDescription, LocalDateTime.now());
    dischargePlanStagingService.updateStatusAndModifiedDateTimeForProcessId(
        processId, status, LocalDateTime.now());
  }

  // endregion
  // region Data Binding
  private <T> T bindDataToEntity(
      Object entity,
      Type listType,
      DischargingPlanTables table,
      String jsonData,
      Long dataTransferStageId,
      String... columnsToRemove) {
    try {
      HashMap<String, String> map = dischargePlanStagingService.getAttributeMapping(entity);
      JsonArray jsonArray =
          removeJsonFields(JsonParser.parseString(jsonData).getAsJsonArray(), map, columnsToRemove);
      idMap.put(table.getTable(), dataTransferStageId);
      return new Gson().fromJson(jsonArray, listType);
    } catch (Exception e) {
      log.error(
          "Communication XXXXXXX Unable to bind the Json to object : "
              + entity.getClass().getName());
      log.error(e);
    }
    return null;
  }

  // endregion
  // region Utilities
  private String replaceSpecialCharacters(
      DataTransferStage dataTransferStage, String dataTransferString) {
    String data = null;
    if (dataTransferStage.getProcessIdentifier().equals("discharging_information")) {
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
    return data;
  }

  private JsonArray removeJsonFields(JsonArray array, HashMap<String, String> map, String... xIds) {
    JsonArray json = dischargePlanStagingService.getAsEntityJson(map, array);
    JsonArray jsonArray = new JsonArray();
    JsonObject communicationRelatedIdMap = new JsonObject();
    for (JsonElement jsonElement : json) {
      final JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (xIds != null) {
        for (String xId : xIds) {
          if (xIds.length == 1) {
            jsonObj.add("communicationRelatedEntityId", jsonObj.get(xId));
          } else {
            communicationRelatedIdMap.addProperty(xId, jsonObj.get(xId).getAsLong());
          }
          jsonObj.remove(xId);
        }
      }
      jsonObj.add("communicationRelatedIdMap", communicationRelatedIdMap);
      jsonArray.add(jsonObj);
    }
    return jsonArray;
  }

  private void clear() {
    idMap = new HashMap<>();
    dischargeInformation = null;
    dischargingInformationStatus = null;
    dischargingStagesMinAmount = null;
    dischargingStagesDuration = null;
    // Discharge plan tables
    cowPlanDetail = null;
    cowWithDifferentCargos = null;
    cowTankDetails = null;
    dischargingDelays = null;
    dischargingDelayReasons = null;
    dischargingMachineryInUses = null;
    portTideDetails = null;
    dischargingSequences = null;
    dischargingBerthDetails = null;
    dischargingInfoAlgoStatuses = null;
    algoErrorHeadings = null;
    ballastValves = null;
    cargoValves = null;
    algoErrors = null;
    dischargingPlanPortWiseDetailsList = null;
    ballastingRates = null;
    deballastingRates = null;
    dischargingPlanStowageDetailsList = null;
    dischargingPlanBallastDetailsList = null;
    dischargingPlanRobDetailsList = null;
    dischargingPlanStabilityParametersList = null;
    dischargingPlanCommingleDetails = null;
    cargoDischargingRates = null;
    ballastOperations = null;
    dischargingSequenceStabilityParamList = null;
    portDischargingPlanBallastDetailsList = null;
    portDischargingPlanRobDetailsList = null;
    portDischargingPlanStabilityParamList = null;
    portDischargingPlanStowageDetailsList = null;
    portDischargingPlanCommingleDetailsList = null;
    current_table_name = "";
    jsonData = null;
  }
  // endregion
}
