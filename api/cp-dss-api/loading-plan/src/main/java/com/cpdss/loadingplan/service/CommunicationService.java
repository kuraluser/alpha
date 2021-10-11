/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;
import static com.cpdss.loadingplan.utility.LoadingPlanConstants.*;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.StagingStatus;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.utility.ProcessIdentifiers;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Log4j2
@Service
public class CommunicationService {

  @Autowired private LoadingPlanStagingService loadingPlanStagingService;
  @Autowired private LoadingInformationRepository loadingInformationRepository;
  @Autowired private CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @Autowired private LoadingBerthDetailsRepository loadingBerthDetailsRepository;
  @Autowired private LoadingDelayRepository loadingDelayRepository;
  @Autowired private LoadingMachineryInUseRepository loadingMachineryInUseRepository;
  @Autowired private LoadingPlanAlgoService loadingPlanAlgoService;
  @Autowired private PortLoadingPlanStabilityParametersRepository portLoadingPlanStabilityParametersRepository;
  @Autowired private PortLoadingPlanRobDetailsRepository portLoadingPlanRobDetailsRepository;
  @Autowired private LoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @Autowired private LoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;
  @Autowired private LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;
  @Autowired private PortLoadingPlanBallastDetailsRepository portLoadingPlanBallastDetailsRepository;
  @Autowired private PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastTempDetailsRepository;
  @Autowired private PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;
  @Autowired private PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageTempDetailsRepository;
  @Autowired private LoadingSequenceRepository loadingSequenceRepository;
  @Autowired private LoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;
  @Autowired private LoadingSequenceStabiltyParametersRepository loadingSequenceStabiltyParametersRepository;
  @Autowired private LoadingPlanStabilityParametersRepository loadingPlanStabilityParametersRepository;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("envoyReaderService")
  private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

  @GrpcClient("envoyWriterService")
  private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;


  public void getDataFromCommunication(
          Map<String, String> taskReqParams, String messageType)
          throws GenericServiceException {
    log.info("Inside getDataFromCommunication messageType " + messageType);
    try {
      EnvoyReader.EnvoyReaderResultReply erReply =
              getResultFromEnvoyReaderShore(taskReqParams, messageType);
      if (!SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
                "Failed to get Result from Communication Server for: "+ messageType,
                erReply.getResponseStatus().getCode(),
                HttpStatusCode.valueOf(Integer.valueOf(erReply.getResponseStatus().getCode())));
      }
      if (erReply != null && !erReply.getPatternResultJson().isEmpty()) {
        log.info("Data received from envoy reader for: "+ messageType);
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

  /*public void getDataFromCommInShipSide(
      Map<String, String> taskReqParams, EnumSet<MessageTypes> ship)
      throws GenericServiceException {
    log.info("inside getDataFromCommInShipSide ");
    for (MessageTypes messageType : ship) {
      try {
        EnvoyReader.EnvoyReaderResultReply erReply =
                getResultFromEnvoyReaderShore(taskReqParams, messageType);
        if (!SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
          throw new GenericServiceException(
                  "Failed to get Result from Communication Server",
                  erReply.getResponseStatus().getCode(),
                  HttpStatusCode.valueOf(Integer.valueOf(erReply.getResponseStatus().getCode())));
        }
        if (messageType.getMessageType().equals("LoadingPlan_AlgoResult")) {
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
  }*/

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
    log.info("inside getStagingData");
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
      for (Map.Entry<String, List<DataTransferStage>> entry : dataTransferByProcessId.entrySet()) {
        HashMap<String, Long> idMap = new HashMap<>();
        String processId = entry.getKey();
        LoadingInformation loadingInformation = null;
        StageOffset stageOffset=null;
        StageDuration stageDuration=null;
        LoadingInformationStatus loadingInformationStatus=null;
        LoadingInformationStatus arrivalStatus=null;
        LoadingInformationStatus departureStatus=null;
        //loading plan tables
        List<CargoToppingOffSequence> cargoToppingOffSequences = null;
        List<LoadingBerthDetail> loadingBerthDetails = null;
        List<LoadingDelay> loadingDelays = null;
        List<LoadingMachineryInUse> loadingMachineryInUses = null;
        //Pattern save tables
        List<LoadingSequence> loadingSequencesList=null;
        List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsList =null;
        List<PortLoadingPlanStabilityParameters> portLoadingPlanStabilityParamList =null;
        List<PortLoadingPlanRobDetails> portLoadingPlanRobDetailsList = null;
        List<LoadingPlanBallastDetails> loadingPlanBallastDetailsList =null;
        List<LoadingPlanRobDetails> loadingPlanRobDetailsList =null;
        List<PortLoadingPlanBallastDetails> portLoadingPlanBallastDetailsList =null;
        List<PortLoadingPlanBallastTempDetails> portLoadingPlanBallastTempDetailsList =null;
        List<PortLoadingPlanStowageDetails> portLoadingPlanStowageDetailsList =null;
        List<PortLoadingPlanStowageTempDetails> portLoadingPlanStowageTempDetailsList =null;
        List<LoadingPlanStowageDetails> loadingPlanStowageDetailsList =null;
        List<LoadingSequenceStabilityParameters> loadingSequenceStabilityParametersList =null;
        List<LoadingPlanStabilityParameters> loadingPlanStabilityParametersList =null;

        loadingPlanStagingService.updateStatusForProcessId(
                processId, StagingStatus.IN_PROGRESS.getStatus());
        for (DataTransferStage dataTransferStage : entry.getValue()) {
          // loadingPlanStagingService.updateStatus(dataTransferStage.getId(),StagingStatus.IN_PROGRESS.getStatus());
          ObjectMapper objectMapper = new ObjectMapper();
          objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
          objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
          objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
          //objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
          objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
          String data = dataTransferStage.getData();
          switch (ProcessIdentifiers.valueOf(dataTransferStage.getProcessIdentifier()))
          {
            case loading_information:
            {
              try {
                loadingInformation = objectMapper.readValue(data, LoadingInformation.class);
                idMap.put(
                        LoadingPlanTables.LOADING_INFORMATION.getTable(), dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case stages_min_amount:
            {
              try {
                stageOffset= objectMapper.readValue(data, StageOffset.class);
                idMap.put(
                        LoadingPlanTables.STAGES_MIN_AMOUNT.getTable(), dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case stages_duration:
            {
              try {
                stageDuration = objectMapper.readValue(data, StageDuration.class);
                idMap.put(
                        LoadingPlanTables.STAGES_DURATION.getTable(), dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_information_status:
            {
              try {
                loadingInformationStatus = objectMapper.readValue(data, LoadingInformationStatus.class);
                idMap.put(
                        LoadingPlanTables.LOADING_INFORMATION_STATUS.getTable(), dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_information_arrival_status:
            {
              try {
                arrivalStatus = objectMapper.readValue(data, LoadingInformationStatus.class);
                idMap.put(
                        LoadingPlanTables.LOADING_INFORMATION_ARRIVAL_STATUS.getTable(), dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_information_departure_status:
            {
              try {
                departureStatus = objectMapper.readValue(data, LoadingInformationStatus.class);
                idMap.put(
                        LoadingPlanTables.LOADING_INFORMATION_DEPARTURE_STATUS.getTable(), dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case cargo_topping_off_sequence:
            {
              try {
                cargoToppingOffSequences = objectMapper.readValue(data, new TypeReference<List<CargoToppingOffSequence>>(){});
                idMap.put(
                        LoadingPlanTables.CARGO_TOPPING_OFF_SEQUENCE.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_berth_details:
            {
              try {
                loadingBerthDetails = objectMapper.readValue(data, new TypeReference<List<LoadingBerthDetail>>(){});
                idMap.put(
                        LoadingPlanTables.LOADING_BERTH_DETAILS.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_delay:
            {
              try {
                loadingDelays = objectMapper.readValue(data, new TypeReference<List<LoadingDelay>>(){});
                idMap.put(LoadingPlanTables.LOADING_DELAY.getTable(), dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_machinary_in_use:
            {
              try {
                loadingMachineryInUses = objectMapper.readValue(data, new TypeReference<List<LoadingMachineryInUse>>(){});
                idMap.put(
                        LoadingPlanTables.LOADING_MACHINARY_IN_USE.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_sequence:
            {
              try {
                loadingSequencesList = objectMapper.readValue(data, new TypeReference<List<LoadingSequence>>(){});
                idMap.put(
                        LoadingPlanTables.LOADING_SEQUENCE.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_plan_portwise_details:
            {
              try {
                loadingPlanPortWiseDetailsList = objectMapper.readValue(data, new TypeReference<List<LoadingPlanPortWiseDetails>>(){});
                idMap.put(
                        LoadingPlanTables.LOADING_PLAN_PORTWISE_DETAILS.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case port_loading_plan_stability_parameters:
            {
              try {
                portLoadingPlanStabilityParamList = objectMapper.readValue(data, new TypeReference<List<PortLoadingPlanStabilityParameters>>(){});
                idMap.put(
                        LoadingPlanTables.PORT_LOADING_PLAN_STABILITY_PARAMETERS.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case port_loading_plan_rob_details:
            {
              try {
                portLoadingPlanRobDetailsList = objectMapper.readValue(data, new TypeReference<List<PortLoadingPlanRobDetails>>(){});
                idMap.put(
                        LoadingPlanTables.PORT_LOADING_PLAN_ROB_DETAILS.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_plan_ballast_details:
            {
              try {
                loadingPlanBallastDetailsList = objectMapper.readValue(data, new TypeReference<List<LoadingPlanBallastDetails>>(){});
                idMap.put(
                        LoadingPlanTables.LOADING_PLAN_BALLAST_DETAILS.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_plan_rob_details:
            {
              try {
                loadingPlanRobDetailsList = objectMapper.readValue(data, new TypeReference<List<LoadingPlanRobDetails>>(){});
                idMap.put(
                        LoadingPlanTables.LOADING_PLAN_ROB_DETAILS.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case port_loading_plan_stowage_ballast_details:
            {
              try {
                portLoadingPlanBallastDetailsList = objectMapper.readValue(data, new TypeReference<List<PortLoadingPlanBallastDetails>>(){});
                idMap.put(
                        LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case port_loading_plan_stowage_ballast_details_temp:
            {
              try {
                portLoadingPlanBallastTempDetailsList = objectMapper.readValue(data, new TypeReference<List<PortLoadingPlanBallastTempDetails>>(){});
                idMap.put(
                        LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS_TEMP.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case port_loading_plan_stowage_details:
            {
              try {
                portLoadingPlanStowageDetailsList = objectMapper.readValue(data, new TypeReference<List<PortLoadingPlanStowageDetails>>(){});
                idMap.put(
                        LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case port_loading_plan_stowage_details_temp:
            {
              try {
                portLoadingPlanStowageTempDetailsList = objectMapper.readValue(data, new TypeReference<List<PortLoadingPlanStowageTempDetails>>(){});
                idMap.put(
                        LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_DETAILS_TEMP.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_plan_stowage_details:
            {
              try {
                loadingPlanStowageDetailsList = objectMapper.readValue(data, new TypeReference<List<LoadingPlanStowageDetails>>(){});
                idMap.put(
                        LoadingPlanTables.LOADING_PLAN_STOWAGE_DETAILS.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_sequence_stability_parameters:
            {
              try {
                loadingSequenceStabilityParametersList = objectMapper.readValue(data, new TypeReference<List<LoadingSequenceStabilityParameters>>(){});
                idMap.put(
                        LoadingPlanTables.LOADING_SEQUENCE_STABILITY_PARAMETERS.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
            case loading_plan_stability_parameters:
            {
              try {
                loadingPlanStabilityParametersList = objectMapper.readValue(data, new TypeReference<List<LoadingPlanStabilityParameters>>(){});
                idMap.put(
                        LoadingPlanTables.LOADING_PLAN_STABILITY_PARAMETERS.getTable(),
                        dataTransferStage.getId());
              } catch (JsonProcessingException e) {
                throw new GenericServiceException(
                        "Error in calling converting json string to entity" + e.getMessage(),
                        CommonErrorCodes.E_GEN_INTERNAL_ERR,
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
              }
              break;
            }
          }
        }
        LoadingInformation loadingInfo = null;
        if (loadingInformation !=null){
          try {
            if(stageOffset !=null) {
              loadingInformation.setStageOffset(stageOffset);
            }
            if(stageDuration !=null) {
              loadingInformation.setStageDuration(stageDuration);
            }
            loadingInformation.setLoadingInformationStatus(loadingInformationStatus);
            loadingInformation.setArrivalStatus(arrivalStatus);
            loadingInformation.setDepartureStatus(departureStatus);
            loadingInfo = loadingInformationRepository.save(loadingInformation);
          } catch (ResourceAccessException e) {
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
        if (cargoToppingOffSequences != null) {
          try {
            for (CargoToppingOffSequence cargoToppingOffSequence: cargoToppingOffSequences) {
              cargoToppingOffSequence.setLoadingInformation(loadingInfo);
            }
            cargoToppingOffSequenceRepository.saveAll(cargoToppingOffSequences);
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
            for (LoadingBerthDetail loadingBerthDetail:loadingBerthDetails) {
              loadingBerthDetail.setLoadingInformation(loadingInfo);
            }
            loadingBerthDetailsRepository.saveAll(loadingBerthDetails);
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
            for (LoadingDelay loadingDelay:loadingDelays) {
              loadingDelay.setLoadingInformation(loadingInfo);
            }
            loadingDelayRepository.saveAll(loadingDelays);
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
            for (LoadingMachineryInUse loadingMachineryInUse:loadingMachineryInUses) {
              loadingMachineryInUse.setLoadingInformation(loadingInfo);
            }
            loadingMachineryInUseRepository.saveAll(loadingMachineryInUses);
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
        if (loadingSequencesList != null) {
          try {
            for (LoadingSequence loadingSequence:loadingSequencesList) {
              loadingSequence.setLoadingInformation(loadingInfo);
            }
            loadingSequenceRepository.saveAll(loadingSequencesList);
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
        if (loadingPlanPortWiseDetailsList != null) {
          try {
            for (LoadingSequence loadingSequence:loadingSequencesList) {
              for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails:loadingPlanPortWiseDetailsList) {
                if(loadingSequence.getId().equals(loadingPlanPortWiseDetails.getCommunicationSequenceId())){
                  loadingPlanPortWiseDetails.setLoadingSequence(loadingSequence);
                }
              }
            }
            loadingPlanPortWiseDetailsRepository.saveAll(loadingPlanPortWiseDetailsList);
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
        if (portLoadingPlanStabilityParamList != null) {
          try {
            for (PortLoadingPlanStabilityParameters portLoadingPlanStabilityParameters:portLoadingPlanStabilityParamList) {
              portLoadingPlanStabilityParameters.setLoadingInformation(loadingInfo);
            }
            portLoadingPlanStabilityParametersRepository.saveAll(portLoadingPlanStabilityParamList);
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
        if (portLoadingPlanRobDetailsList != null) {
          try {
            for (PortLoadingPlanRobDetails portLoadingPlanRobDetails:portLoadingPlanRobDetailsList) {
              portLoadingPlanRobDetails.setLoadingInformation(loadingInfo.getId());
            }
            portLoadingPlanRobDetailsRepository.saveAll(portLoadingPlanRobDetailsList);
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
        if (loadingPlanBallastDetailsList != null) {
          try {
            for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails:loadingPlanPortWiseDetailsList) {
              for (LoadingPlanBallastDetails loadingPlanBallastDetails : loadingPlanBallastDetailsList) {
                if(loadingPlanPortWiseDetails.getId().equals(loadingPlanBallastDetails.getCommunicationPortWiseId())) {
                  loadingPlanBallastDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
                }
              }
            }
            loadingPlanBallastDetailsRepository.saveAll(loadingPlanBallastDetailsList);
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
        if (loadingPlanRobDetailsList != null) {
          try {
            for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails:loadingPlanPortWiseDetailsList) {
              for (LoadingPlanRobDetails loadingPlanRobDetails:loadingPlanRobDetailsList) {
                if(loadingPlanPortWiseDetails.getId().equals(loadingPlanRobDetails.getCommunicationPortWiseId())) {
                  loadingPlanRobDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
                }
              }
            }
            loadingPlanRobDetailsRepository.saveAll(loadingPlanRobDetailsList);
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
        if (portLoadingPlanBallastDetailsList != null) {
          try {
            for (PortLoadingPlanBallastDetails portLoadingPlanBallastDetails:portLoadingPlanBallastDetailsList) {
              portLoadingPlanBallastDetails.setLoadingInformation(loadingInfo);
            }
            portLoadingPlanBallastDetailsRepository.saveAll(portLoadingPlanBallastDetailsList);
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
        if (portLoadingPlanBallastTempDetailsList != null) {
          try {
            for (PortLoadingPlanBallastTempDetails portLoadingPlanBallastTempDetails:portLoadingPlanBallastTempDetailsList) {
              portLoadingPlanBallastTempDetails.setLoadingInformation(loadingInfo.getId());
            }
            portLoadingPlanBallastTempDetailsRepository.saveAll(portLoadingPlanBallastTempDetailsList);
          } catch (ResourceAccessException e) {
            updateStatusInExceptionCase(
                    idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS_TEMP.getTable()),
                    processId,
                    retryStatus,
                    e.getMessage());
          } catch (Exception e) {
            updateStatusInExceptionCase(
                    idMap.get(LoadingPlanTables.PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS_TEMP.getTable()),
                    processId,
                    StagingStatus.FAILED.getStatus(),
                    e.getMessage());
          }
        }
        if (portLoadingPlanStowageDetailsList != null) {
          try {
            for (PortLoadingPlanStowageDetails portLoadingPlanStowageDetails:portLoadingPlanStowageDetailsList) {
              portLoadingPlanStowageDetails.setLoadingInformation(loadingInfo);
            }
            portLoadingPlanStowageDetailsRepository.saveAll(portLoadingPlanStowageDetailsList);
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
        if (portLoadingPlanStowageTempDetailsList != null) {
          try {
            for (PortLoadingPlanStowageTempDetails portLoadingPlanStowageTempDetails:portLoadingPlanStowageTempDetailsList) {
              portLoadingPlanStowageTempDetails.setLoadingInformation(loadingInfo.getId());
            }
            portLoadingPlanStowageTempDetailsRepository.saveAll(portLoadingPlanStowageTempDetailsList);
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
        if (loadingPlanStowageDetailsList != null) {
          try {
            for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails:loadingPlanPortWiseDetailsList) {
              for (LoadingPlanStowageDetails loadingPlanStowageDetails:loadingPlanStowageDetailsList) {
                if(loadingPlanPortWiseDetails.getId().equals(loadingPlanStowageDetails.getCommunicationPortWiseId())){
                  loadingPlanStowageDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
                }
              }}
            loadingPlanStowageDetailsRepository.saveAll(loadingPlanStowageDetailsList);
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
        if (loadingSequenceStabilityParametersList != null) {
          try {
            for (LoadingSequenceStabilityParameters loadingSequenceStabilityParameters:loadingSequenceStabilityParametersList) {
              loadingSequenceStabilityParameters.setLoadingInformation(loadingInfo);
            }
            loadingSequenceStabiltyParametersRepository.saveAll(loadingSequenceStabilityParametersList);
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
        if (loadingPlanStabilityParametersList != null) {
          try {
            for (LoadingPlanPortWiseDetails loadingPlanPortWiseDetails:loadingPlanPortWiseDetailsList) {
              for (LoadingPlanStabilityParameters loadingPlanStabilityParameters:loadingPlanStabilityParametersList) {
                if(loadingPlanPortWiseDetails.getId().equals(loadingPlanStabilityParameters.getCommunicationPortWiseId())) {
                  loadingPlanStabilityParameters.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
                }
              }}
            loadingPlanStabilityParametersRepository.saveAll(loadingPlanStabilityParametersList);
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
        loadingPlanStagingService.updateStatusForProcessId(
                processId, StagingStatus.COMPLETED.getStatus());
        if (env.equals("shore")) {
          log.info("Algo call started");
          LoadingPlanModels.LoadingInfoAlgoRequest.Builder builder =
                  LoadingPlanModels.LoadingInfoAlgoRequest.newBuilder();
          builder.setLoadingInfoId(loadingInfo.getId());
          LoadingPlanModels.LoadingInfoAlgoReply.Builder algoReplyBuilder =
                  LoadingPlanModels.LoadingInfoAlgoReply.newBuilder();
          loadingPlanAlgoService.generateLoadingPlan(builder.build(), algoReplyBuilder);
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
