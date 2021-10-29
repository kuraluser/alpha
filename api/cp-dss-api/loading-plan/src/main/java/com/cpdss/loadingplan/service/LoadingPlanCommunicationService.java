/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import static com.cpdss.loadingplan.utility.LoadingPlanConstants.*;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.common.utils.StagingStatus;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.loadicator.UllageUpdateLoadicatorService;
import com.cpdss.loadingplan.utility.ProcessIdentifiers;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Log4j2
@Service
@Transactional
public class LoadingPlanCommunicationService {

  @Autowired private LoadingPlanStagingService loadingPlanStagingService;
  @Autowired private LoadingInformationRepository loadingInformationRepository;
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

  public void getDataFromCommunication(
      Map<String, String> taskReqParams, EnumSet<MessageTypes> messageTypesEnum)
      throws GenericServiceException {
    log.info("Inside getDataFromCommunication messageTypes:" + messageTypesEnum);
    for (MessageTypes messageType : messageTypesEnum) {
      try {
        String messageTypeGet = messageType.getMessageType();
        log.info("Inside getDataFromCommunication messageType : " + messageType);
        EnvoyReader.EnvoyReaderResultReply erReply =
            getResultFromEnvoyReaderShore(taskReqParams, messageTypeGet);
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

  private EnvoyReader.EnvoyReaderResultReply getResultFromEnvoyReaderShore(
      Map<String, String> taskReqParams, String messageType) {
    log.info("inside getResultFromEnvoyReader");
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

  public void getStagingData(String status, String env) throws GenericServiceException {
    log.info("Inside getStagingData for env:" + env);
    String retryStatus = StagingStatus.RETRY.getStatus();
    if (status.equals(retryStatus)) {
      retryStatus = StagingStatus.FAILED.getStatus();
    }
    List<DataTransferStage> dataTransferStages = null;
    if (status.equals(StagingStatus.IN_PROGRESS.getStatus())) {
      dataTransferStages =
          loadingPlanStagingService.getAllWithStatusAndTime(
              status, LocalDateTime.now().minusMinutes(2));
    } else {
      dataTransferStages = loadingPlanStagingService.getAllWithStatus(status);
    }
    if (dataTransferStages != null && !dataTransferStages.isEmpty()) {
      Map<String, List<DataTransferStage>> dataTransferByProcessId =
          dataTransferStages.stream()
              .collect(Collectors.groupingBy(DataTransferStage::getProcessId));
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
        List<LoadingMachineryInUse> loadingMachineryInUses = null;
        // Pattern save tables
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
        // Ullage update tables
        List<PortLoadingPlanCommingleTempDetails> portLoadingPlanCommingleTempDetailsList = null;
        List<PortLoadingPlanCommingleDetails> portLoadingPlanCommingleDetailsList = null;
        List<BillOfLanding> billOfLandingList = null;
        PyUser pyUser = null;
        loadingPlanStagingService.updateStatusForProcessId(
            processId, StagingStatus.IN_PROGRESS.getStatus());
        log.info("updated status to in_progress for processId:" + processId);
        String processGroupId = null;
        Integer arrivalDeparture = null;
        processGroupId = entry.getValue().get(0).getProcessGroupId();
        for (DataTransferStage dataTransferStage : entry.getValue()) {
          Type listType = null;
          String dataTransferString = dataTransferStage.getData();
          String data = null;
          if (dataTransferStage.getProcessIdentifier().equals("loading_information")
              || dataTransferStage.getProcessIdentifier().equals("pyuser")) {
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
                //                listType = new TypeToken<ArrayList<LoadingInformation>>()
                // {}.getType();
                //                List<LoadingInformation> listLoadingInformation =
                //                    new Gson().fromJson(data, listType);
                loadingInformation = new Gson().fromJson(data, LoadingInformation.class);
                idMap.put(
                    LoadingPlanTables.LOADING_INFORMATION.getTable(), dataTransferStage.getId());
                break;
              }
            case stages_min_amount:
              {
                listType = new TypeToken<ArrayList<StageOffset>>() {}.getType();
                List<StageOffset> listStageOffset = new Gson().fromJson(data, listType);
                stageOffset = listStageOffset.get(0);
                idMap.put(
                    LoadingPlanTables.STAGES_MIN_AMOUNT.getTable(), dataTransferStage.getId());
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
                listType = new TypeToken<ArrayList<CargoToppingOffSequence>>() {}.getType();
                cargoToppingOffSequences = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.CARGO_TOPPING_OFF_SEQUENCE.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case loading_berth_details:
              {
                listType = new TypeToken<ArrayList<LoadingBerthDetail>>() {}.getType();
                loadingBerthDetails = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.LOADING_BERTH_DETAILS.getTable(), dataTransferStage.getId());
                break;
              }
            case loading_delay:
              {
                listType = new TypeToken<ArrayList<LoadingDelay>>() {}.getType();
                loadingDelays = new Gson().fromJson(data, listType);
                idMap.put(LoadingPlanTables.LOADING_DELAY.getTable(), dataTransferStage.getId());
                break;
              }
            case loading_machinary_in_use:
              {
                listType = new TypeToken<ArrayList<LoadingMachineryInUse>>() {}.getType();
                loadingMachineryInUses = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.LOADING_MACHINARY_IN_USE.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case loading_sequence:
              {
                listType = new TypeToken<ArrayList<LoadingSequence>>() {}.getType();
                loadingSequencesList = new Gson().fromJson(data, listType);
                idMap.put(LoadingPlanTables.LOADING_SEQUENCE.getTable(), dataTransferStage.getId());
                break;
              }
            case loading_plan_portwise_details:
              {
                listType = new TypeToken<ArrayList<LoadingPlanPortWiseDetails>>() {}.getType();
                loadingPlanPortWiseDetailsList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.LOADING_PLAN_PORTWISE_DETAILS.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case port_loading_plan_stability_parameters:
              {
                listType =
                    new TypeToken<ArrayList<PortLoadingPlanStabilityParameters>>() {}.getType();
                portLoadingPlanStabilityParamList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.PORT_LOADING_PLAN_STABILITY_PARAMETERS.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case port_loading_plan_rob_details:
              {
                listType = new TypeToken<ArrayList<PortLoadingPlanRobDetails>>() {}.getType();
                portLoadingPlanRobDetailsList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.PORT_LOADING_PLAN_ROB_DETAILS.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case loading_plan_ballast_details:
              {
                listType = new TypeToken<ArrayList<LoadingPlanBallastDetails>>() {}.getType();
                loadingPlanBallastDetailsList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.LOADING_PLAN_BALLAST_DETAILS.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case loading_plan_rob_details:
              {
                listType = new TypeToken<ArrayList<LoadingPlanRobDetails>>() {}.getType();
                loadingPlanRobDetailsList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.LOADING_PLAN_ROB_DETAILS.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case port_loading_plan_stowage_ballast_details:
              {
                listType = new TypeToken<ArrayList<PortLoadingPlanBallastDetails>>() {}.getType();
                portLoadingPlanBallastDetailsList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case port_loading_plan_stowage_ballast_details_temp:
              {
                listType =
                    new TypeToken<ArrayList<PortLoadingPlanBallastTempDetails>>() {}.getType();
                portLoadingPlanBallastTempDetailsList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS_TEMP.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case port_loading_plan_stowage_details:
              {
                listType = new TypeToken<ArrayList<PortLoadingPlanStowageDetails>>() {}.getType();
                portLoadingPlanStowageDetailsList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case port_loading_plan_stowage_details_temp:
              {
                listType =
                    new TypeToken<ArrayList<PortLoadingPlanStowageTempDetails>>() {}.getType();
                portLoadingPlanStowageTempDetailsList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS_TEMP.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case loading_plan_stowage_details:
              {
                listType = new TypeToken<ArrayList<LoadingPlanStowageDetails>>() {}.getType();
                loadingPlanStowageDetailsList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.LOADING_PLAN_STOWAGE_DETAILS.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case loading_sequence_stability_parameters:
              {
                listType =
                    new TypeToken<ArrayList<LoadingSequenceStabilityParameters>>() {}.getType();
                loadingSequenceStabilityParametersList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.LOADING_SEQUENCE_STABILITY_PARAMETERS.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case loading_plan_stability_parameters:
              {
                listType = new TypeToken<ArrayList<LoadingPlanStabilityParameters>>() {}.getType();
                loadingPlanStabilityParametersList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.LOADING_PLAN_STABILITY_PARAMETERS.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case port_loadable_plan_commingle_details_temp:
              {
                listType =
                    new TypeToken<ArrayList<PortLoadingPlanCommingleTempDetails>>() {}.getType();
                portLoadingPlanCommingleTempDetailsList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.PORT_LOADABLE_PLAN_COMMINGLE_DETAILS_TEMP.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case port_loadable_plan_commingle_details:
              {
                listType = new TypeToken<ArrayList<PortLoadingPlanCommingleDetails>>() {}.getType();
                portLoadingPlanCommingleDetailsList = new Gson().fromJson(data, listType);
                idMap.put(
                    LoadingPlanTables.PORT_LOADABLE_PLAN_COMMINGLE_DETAILS.getTable(),
                    dataTransferStage.getId());
                break;
              }
            case bill_of_ladding:
              {
                listType = new TypeToken<ArrayList<BillOfLanding>>() {}.getType();
                billOfLandingList = new Gson().fromJson(data, listType);
                idMap.put(LoadingPlanTables.BILL_OF_LADDING.getTable(), dataTransferStage.getId());
                break;
              }
            case pyuser:
              {
                pyUser = new Gson().fromJson(data, PyUser.class);
                idMap.put(LoadingPlanTables.PYUSER.getTable(), dataTransferStage.getId());
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
                loadingInformationRepository.findById(loadingInformation.getId());
            if (loadingInfoObj.isPresent()) {
              version = loadingInfoObj.get().getVersion();
            }
            loadingInformation.setVersion(version);
            loadingInfo = loadingInformationRepository.save(loadingInformation);
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
                      .equals(loadingPlanPortWiseDetails.getCommunicationSequenceId())) {
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
            if (loadingPlanPortWiseDetailsList != null
                && !loadingPlanPortWiseDetailsList.isEmpty()) {
              for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails :
                  loadingPlanPortWiseDetailsList) {
                for (LoadingPlanBallastDetails loadingPlanBallastDetails :
                    loadingPlanBallastDetailsList) {
                  Long version = null;
                  if (loadingPlanPortWiseDetails
                      .getId()
                      .equals(loadingPlanBallastDetails.getCommunicationPortWiseId())) {
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
            if (loadingPlanPortWiseDetailsList != null
                && !loadingPlanPortWiseDetailsList.isEmpty()) {
              for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails :
                  loadingPlanPortWiseDetailsList) {
                for (LoadingPlanRobDetails loadingPlanRobDetails : loadingPlanRobDetailsList) {
                  Long version = null;
                  if (loadingPlanPortWiseDetails
                      .getId()
                      .equals(loadingPlanRobDetails.getCommunicationPortWiseId())) {
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
                "Saved PortLoadingPlanBallastTempDetails: "
                    + portLoadingPlanBallastTempDetailsList);
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
            if (loadingPlanPortWiseDetailsList != null
                && !loadingPlanPortWiseDetailsList.isEmpty()) {
              for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails :
                  loadingPlanPortWiseDetailsList) {
                for (LoadingPlanStowageDetails loadingPlanStowageDetails :
                    loadingPlanStowageDetailsList) {
                  Long version = null;
                  if (loadingPlanPortWiseDetails
                      .getId()
                      .equals(loadingPlanStowageDetails.getCommunicationPortWiseId())) {
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
              log.info("Saved LoadingPlanStowageDetails:" + portLoadingPlanStowageTempDetailsList);
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
                "Saved LoadingSequenceStabilityParameters:"
                    + portLoadingPlanStowageTempDetailsList);
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
            if (loadingPlanPortWiseDetailsList != null
                && !loadingPlanPortWiseDetailsList.isEmpty()) {
              for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails :
                  loadingPlanPortWiseDetailsList) {
                for (LoadingPlanStabilityParameters loadingPlanStabilityParameters :
                    loadingPlanStabilityParametersList) {
                  Long version = null;
                  if (loadingPlanPortWiseDetails
                      .getId()
                      .equals(loadingPlanStabilityParameters.getCommunicationPortWiseId())) {
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
              log.info(
                  "Saved LoadingPlanStabilityParameters:" + portLoadingPlanStowageTempDetailsList);
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
                    + portLoadingPlanStowageTempDetailsList);
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
            log.info(
                "Saved PortLoadingPlanCommingleDetails:" + portLoadingPlanCommingleDetailsList);
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
}
