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
import com.cpdss.common.utils.MessageTypes;


import com.cpdss.common.utils.StagingStatus;
import com.cpdss.loadingplan.communication.LoadingPlanStagingRepository;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.grpc.LoadingInformationGrpcService;
import com.cpdss.loadingplan.utility.ProcessIdentifiers;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;


@Log4j2
@Service
public class CommunicationService {

  @Autowired private RestTemplate restTemplate;
  @Autowired private LoadingPlanStagingService loadingPlanStagingService;
  @Autowired private LoadingInformationRepository loadingInformationRepository;
  @Autowired private CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @Autowired private LoadingBerthDetailsRepository loadingBerthDetailsRepository;
  @Autowired private LoadingDelayRepository loadingDelayRepository;
  @Autowired private LoadingMachineryInUseRepository loadingMachineryInUseRepository;
  @Autowired private LoadingPlanAlgoService loadingPlanAlgoService;

  @Value("${loadablestudy.attachement.rootFolder}")
  private String rootFolder;

  @Value("${algo.loadablestudy.api.url}")
  private String loadableStudyUrl;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("envoyReaderService")
  private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

  @GrpcClient("envoyWriterService")
  private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;

  @Value("${loadablestudy.communication.timelimit}")
  private Long timeLimit;

  public void getDataFromCommInShoreSide(
      Map<String, String> taskReqParams, EnumSet<MessageTypes> shore)
      throws GenericServiceException {
    log.info("inside getDataFromCommInShoreSide ");
      for (MessageTypes messageType : shore) {
        try {
          log.info("inside getDataFromCommInShoreSide messageType " + messageType.getMessageType());
          if (messageType.getMessageType().equals("LoadingPlan")) {
            log.info("inside getDataFromCommInShoreSide messageType ");
            EnvoyReader.EnvoyReaderResultReply erReply =
                    getResultFromEnvoyReaderShore(taskReqParams, messageType);
            if (!SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
              throw new GenericServiceException(
                      "Failed to get Result from Communication Server",
                      erReply.getResponseStatus().getCode(),
                      HttpStatusCode.valueOf(Integer.valueOf(erReply.getResponseStatus().getCode())));
            }
            if (erReply != null && !erReply.getPatternResultJson().isEmpty()) {
              log.info("LoadingPlan received at shore ");
              saveLoadingPlanIntoStagingTable(erReply);
            }
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
          Map<String, String> taskReqParams, MessageTypes messageType) {
    log.info("inside getResultFromEnvoyReader");
    EnvoyReader.EnvoyReaderResultRequest.Builder request =
            EnvoyReader.EnvoyReaderResultRequest.newBuilder();
    request.setMessageType(messageType.getMessageType());
    request.setClientId(taskReqParams.get("ClientId"));
    request.setShipId(taskReqParams.get("ShipId"));
    return this.envoyReaderGrpcService.getResultFromCommServer(request.build());
  }

    public void getDataFromCommInShipSide(
      Map<String, String> taskReqParams, EnumSet<MessageTypes> ship)
      throws GenericServiceException {
      log.info("inside getDataFromCommInShipSide ");

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

  public void getStagingData(String status) throws GenericServiceException {
    log.info("inside getStagingData");
    String retryStatus= StagingStatus.RETRY.getStatus();
    if (status.equals(retryStatus)){
      retryStatus = StagingStatus.FAILED.getStatus();
    }
    List<DataTransferStage> dataTransferStages = null;
    if (status.equals(StagingStatus.IN_PROGRESS.getStatus())){
      dataTransferStages = loadingPlanStagingService.getAllWithStatusAndTime(status,LocalDateTime.now().minusMinutes(2));
    }else{
      dataTransferStages = loadingPlanStagingService.getAllWithStatus(status);
     }
    if(dataTransferStages != null && !dataTransferStages.isEmpty()){
      Map<String, List<DataTransferStage>> dataTransferByProcessId =
              dataTransferStages.stream().collect(Collectors.groupingBy(DataTransferStage::getProcessId));
      for(Map.Entry<String, List<DataTransferStage>> entry : dataTransferByProcessId.entrySet()) {
        HashMap<String, Long> idMap = new HashMap<>();
        String processId = entry.getKey();
        LoadingInformation loadingInformation=null;
        CargoToppingOffSequence cargoToppingOffSequence=null;
        LoadingBerthDetail loadingBerthDetail=null;
        LoadingDelay loadingDelay=null;
        LoadingMachineryInUse loadingMachineryInUse=null;
          loadingPlanStagingService.updateStatusForProcessId(processId,StagingStatus.IN_PROGRESS.getStatus());
          for (DataTransferStage dataTransferStage : entry.getValue()) {
            //loadingPlanStagingService.updateStatus(dataTransferStage.getId(),StagingStatus.IN_PROGRESS.getStatus());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            String data = dataTransferStage.getData();
            switch (ProcessIdentifiers.valueOf(dataTransferStage.getProcessIdentifier())) {
              case loading_information:{
                try {
                  loadingInformation = objectMapper.readValue(data, LoadingInformation.class);
                  idMap.put(LoadingPlanTables.LOADING_INFORMATION.getTable(),dataTransferStage.getId());
                }catch(JsonProcessingException e){
                  throw new GenericServiceException(
                          "Error in calling converting json string to entity"+e.getMessage(),
                          CommonErrorCodes.E_GEN_INTERNAL_ERR,
                          HttpStatusCode.INTERNAL_SERVER_ERROR);
                }
                break;}
              case cargo_topping_off_sequence: {
                try {
                  cargoToppingOffSequence = objectMapper.readValue(data, CargoToppingOffSequence.class);
                  idMap.put(LoadingPlanTables.CARGO_TOPPING_OFF_SEQUENCE.getTable(), dataTransferStage.getId());
                } catch (JsonProcessingException e) {
                  throw new GenericServiceException(
                          "Error in calling converting json string to entity" + e.getMessage(),
                          CommonErrorCodes.E_GEN_INTERNAL_ERR,
                          HttpStatusCode.INTERNAL_SERVER_ERROR);
                }
                break;
              }
              case loading_berth_details: {
                try {
                  loadingBerthDetail = objectMapper.readValue(data, LoadingBerthDetail.class);
                  idMap.put(LoadingPlanTables.LOADING_BERTH_DETAILS.getTable(), dataTransferStage.getId());
                } catch (JsonProcessingException e) {
                  throw new GenericServiceException(
                          "Error in calling converting json string to entity" + e.getMessage(),
                          CommonErrorCodes.E_GEN_INTERNAL_ERR,
                          HttpStatusCode.INTERNAL_SERVER_ERROR);
                }
                break;
              }
              case loading_delay: {
                try {
                  loadingDelay = objectMapper.readValue(data, LoadingDelay.class);
                  idMap.put(LoadingPlanTables.LOADING_DELAY.getTable(), dataTransferStage.getId());
                } catch (JsonProcessingException e) {
                  throw new GenericServiceException(
                          "Error in calling converting json string to entity" + e.getMessage(),
                          CommonErrorCodes.E_GEN_INTERNAL_ERR,
                          HttpStatusCode.INTERNAL_SERVER_ERROR);
                }
                break;
              }
              case loading_machinary_in_use: {
                try {
                  loadingMachineryInUse = objectMapper.readValue(data, LoadingMachineryInUse.class);
                  idMap.put(LoadingPlanTables.LOADING_MACHINARY_IN_USE.getTable(), dataTransferStage.getId());
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
        LoadingInformation loadingInfo =null;
          try {
            loadingInfo = loadingInformationRepository.save(loadingInformation);
          }catch (ResourceAccessException e){
            updateStatusInExceptionCase(idMap.get(LoadingPlanTables.LOADING_INFORMATION.getTable()),processId,retryStatus,e.getMessage());
          }catch (Exception e){
            updateStatusInExceptionCase(idMap.get(LoadingPlanTables.LOADING_INFORMATION.getTable()),processId,StagingStatus.FAILED.getStatus(),e.getMessage());
        }
        if(cargoToppingOffSequence !=null) {
          try {
            cargoToppingOffSequence.setLoadingInformation(loadingInfo);
            cargoToppingOffSequenceRepository.save(cargoToppingOffSequence);
          } catch (ResourceAccessException e) {
            updateStatusInExceptionCase(idMap.get(LoadingPlanTables.CARGO_TOPPING_OFF_SEQUENCE.getTable()), processId, retryStatus, e.getMessage());
          } catch (Exception e) {
            updateStatusInExceptionCase(idMap.get(LoadingPlanTables.CARGO_TOPPING_OFF_SEQUENCE.getTable()), processId, StagingStatus.FAILED.getStatus(), e.getMessage());
          }
        }
        if(loadingBerthDetail!=null){
          try{
            loadingBerthDetail.setLoadingInformation(loadingInfo);
            loadingBerthDetailsRepository.save(loadingBerthDetail);
          }catch (ResourceAccessException e) {
            updateStatusInExceptionCase(idMap.get(LoadingPlanTables.LOADING_BERTH_DETAILS.getTable()), processId, retryStatus, e.getMessage());
          } catch (Exception e) {
            updateStatusInExceptionCase(idMap.get(LoadingPlanTables.LOADING_BERTH_DETAILS.getTable()), processId, StagingStatus.FAILED.getStatus(), e.getMessage());
          }
        }
        if(loadingDelay!=null){
          try{
            loadingDelay.setLoadingInformation(loadingInfo);
            loadingDelayRepository.save(loadingDelay);
          }catch (ResourceAccessException e) {
            updateStatusInExceptionCase(idMap.get(LoadingPlanTables.LOADING_DELAY.getTable()), processId, retryStatus, e.getMessage());
          } catch (Exception e) {
            updateStatusInExceptionCase(idMap.get(LoadingPlanTables.LOADING_DELAY.getTable()), processId, StagingStatus.FAILED.getStatus(), e.getMessage());
          }
        }
        if(loadingMachineryInUse!=null){
          try {
            loadingMachineryInUse.setLoadingInformation(loadingInfo);
            loadingMachineryInUseRepository.save(loadingMachineryInUse);
          }catch (ResourceAccessException e) {
            updateStatusInExceptionCase(idMap.get(LoadingPlanTables.LOADING_MACHINARY_IN_USE.getTable()), processId, retryStatus, e.getMessage());
          } catch (Exception e) {
            updateStatusInExceptionCase(idMap.get(LoadingPlanTables.LOADING_MACHINARY_IN_USE.getTable()), processId, StagingStatus.FAILED.getStatus(), e.getMessage());
          }
        }
        loadingPlanStagingService.updateStatusForProcessId(processId,StagingStatus.COMPLETED.getStatus());
        log.info("Algo call started");
        LoadingPlanModels.LoadingInfoAlgoRequest.Builder builder = LoadingPlanModels.LoadingInfoAlgoRequest.newBuilder();
        builder.setLoadingInfoId(loadingInfo.getId());
        LoadingPlanModels.LoadingInfoAlgoReply.Builder algoReplyBuilder = LoadingPlanModels.LoadingInfoAlgoReply.newBuilder();
        loadingPlanAlgoService.generateLoadingPlan(builder.build(), algoReplyBuilder);
      }
    }
  }

  private void updateStatusInExceptionCase(Long id, String processId, String status, String statusDescription){
    loadingPlanStagingService.updateStatusAndStatusDescriptionForId(id,status, statusDescription,LocalDateTime.now());
    loadingPlanStagingService.updateStatusAndModifiedDateTimeForProcessId(processId,status,LocalDateTime.now());
  }

}
