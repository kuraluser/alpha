/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.domain.AlgoResponse;
import com.cpdss.loadablestudy.domain.CommunicationStatus;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyCommunicationStatus;
import com.cpdss.loadablestudy.repository.LoadableStudyCommunicationStatusRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class CommunicationService {
  @Autowired private LoadableStudyServiceShore loadableStudyServiceShore;
  @Autowired VoyageService voyageService;
  @Autowired LoadableQuantityService loadableQuantityService;
  @Autowired LoadableStudyService loadableStudyService;
  @Autowired LoadablePatternService loadablePatternService;
  @Autowired JsonDataService jsonDataService;
  @Autowired private RestTemplate restTemplate;
  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

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

  public void saveLoadableStudyShore(Map<String, String> taskReqParams) {

    try {
      EnvoyReader.EnvoyReaderResultReply erReply = getResultFromEnvoyReaderShore(taskReqParams);
      if (!SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
            "Failed to get Result from Communication Server",
            erReply.getResponseStatus().getCode(),
            HttpStatusCode.valueOf(Integer.valueOf(erReply.getResponseStatus().getCode())));
      }
      String jsonResult = erReply.getPatternResultJson();
      LoadableStudy loadableStudyEntity =
          loadableStudyServiceShore.setLoadablestudyShore(jsonResult, erReply.getMessageId());
      if (loadableStudyEntity != null) {
        voyageService.checkIfVoyageClosed(loadableStudyEntity.getVoyage().getId());
        this.loadableQuantityService.validateLoadableStudyWithLQ(loadableStudyEntity);

        processAlgoFromShip(loadableStudyEntity);
      }

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when generating pattern", e);

    } catch (ResourceAccessException e) {
      log.info("Error calling ALGO ");

    } catch (Exception e) {
      log.error("Exception when when calling algo  ", e);
    }
  }

  private void processAlgoFromShip(LoadableStudy loadableStudyEntity)
      throws GenericServiceException, IOException {
    ModelMapper modelMapper = new ModelMapper();
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();

    loadableStudyService.buildLoadableStudy(
        loadableStudyEntity.getId(), loadableStudyEntity, loadableStudy, modelMapper);
    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.writeValue(
        new File(
            this.rootFolder
                + "/json/loadableStudyFromShip_"
                + loadableStudyEntity.getId()
                + ".json"),
        loadableStudy);

    this.jsonDataService.saveJsonToDatabase(
        loadableStudyEntity.getId(),
        LoadableStudiesConstants.LOADABLE_STUDY_REQUEST,
        objectMapper.writeValueAsString(loadableStudy));

    AlgoResponse algoResponse =
        restTemplate.postForObject(loadableStudyUrl, loadableStudy, AlgoResponse.class);
    loadablePatternService.updateProcessIdForLoadableStudy(
        algoResponse.getProcessId(),
        loadableStudyEntity,
        LoadableStudiesConstants.LOADABLE_STUDY_PROCESSING_STARTED_ID);

    loadableStudyRepository.updateLoadableStudyStatus(
        LoadableStudiesConstants.LOADABLE_STUDY_PROCESSING_STARTED_ID, loadableStudyEntity.getId());
  }

  private EnvoyReader.EnvoyReaderResultReply getResultFromEnvoyReaderShore(
      Map<String, String> taskReqParams) {
    EnvoyReader.EnvoyReaderResultRequest.Builder request =
        EnvoyReader.EnvoyReaderResultRequest.newBuilder();
    request.setMessageType(taskReqParams.get("messageType"));
    request.setClientId(taskReqParams.get("ClientId"));
    request.setShipId(taskReqParams.get("ShipId"));
    return this.envoyReaderGrpcService.getResultFromCommServer(request.build());
  }

  public void saveAlgoPatternFromShore(Map<String, String> taskReqParams) {
    try {
      EnvoyReader.EnvoyReaderResultReply erReply = getResultFromEnvoyReaderShore(taskReqParams);
      if (!SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
            "Failed to get Result from Communication Server",
            erReply.getResponseStatus().getCode(),
            HttpStatusCode.valueOf(Integer.valueOf(erReply.getResponseStatus().getCode())));
      }
      String jsonResult = erReply.getPatternResultJson();
      com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder load =
          com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.newBuilder();
      // load.setLoadableStudyId(request.getLoadableStudyId());
      if (!jsonResult.isEmpty())
        loadablePatternService.saveLoadablePatternDetails(erReply.getPatternResultJson(), load);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving pattern", e);
    }
  }

  public void checkLoadableStudyStatus(Map<String, String> taskReqParams) {
    List<LoadableStudyCommunicationStatus> communicationStatusList =
        loadableStudyCommunicationStatusRepository
            .findByCommunicationStatusOrderByCommunicationDateTimeASC(
                CommunicationStatus.UPLOAD_WITH_HASH_VERIFIED.getId());
    if (!communicationStatusList.isEmpty()) {
      communicationStatusList
          .parallelStream()
          .forEach(
              communicationStatusRow -> {
                try {
                  EnvoyWriter.EnvoyWriterRequest.Builder request =
                      EnvoyWriter.EnvoyWriterRequest.newBuilder();
                  request.setMessageId(communicationStatusRow.getMessageUUID());
                  request.setClientId(taskReqParams.get("ClientId"));
                  request.setImoNumber(taskReqParams.get("ShipId"));
                  EnvoyWriter.WriterReply statusReply =
                      this.envoyWriterService.statusCheck(request.build());
                  if (!SUCCESS.equals(statusReply.getResponseStatus().getStatus())) {

                    throw new GenericServiceException(
                        "Loadable pattern does not exist",
                        CommonErrorCodes.E_HTTP_BAD_REQUEST,
                        HttpStatusCode.BAD_REQUEST);
                  }
                  Optional<LoadableStudy> loadableStudy =
                      loadableStudyRepository.findByIdAndIsActive(
                          communicationStatusRow.getReferenceId(), true);

                  if (!(statusReply.getEventDownloadStatus() != null
                      && statusReply
                          .getEventDownloadStatus()
                          .equals(CommunicationStatus.RECEIVED_WITH_HASH_VERIFIED.getId()))) {
                    processAlgoFromShip(loadableStudy.get());
                  } else {
                    loadableStudyCommunicationStatusRepository
                        .updateLoadableStudyCommunicationStatus(
                            statusReply.getEventDownloadStatus(), loadableStudy.get().getId());
                  }
                  long start =
                      Timestamp.valueOf(communicationStatusRow.getCommunicationDateTime())
                          .getTime();
                  long end = start + timeLimit * 1000; // 60 seconds * 1000 ms/sec
                  if (System.currentTimeMillis() > end) {
                    loadableStudyCommunicationStatusRepository
                        .updateLoadableStudyCommunicationStatus(
                            CommunicationStatus.TIME_OUT.getId(), loadableStudy.get().getId());
                  }
                } catch (GenericServiceException | IOException e) {
                  e.printStackTrace();
                }
              });
    }
  }

  @Async
  public EnvoyWriter.WriterReply passResultPayloadToEnvoyWriter(
      com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder
          algoResponseCommunication,
      LoadableStudy loadableStudy)
      throws GenericServiceException {
    String jsonPayload = null;
    try {
      VesselInfo.VesselDetail vesselReply =
          this.getVesselDetailsForEnvoy(loadableStudy.getVesselXId());

      jsonPayload = JsonFormat.printer().print(algoResponseCommunication);
      EnvoyWriter.EnvoyWriterRequest.Builder writerRequest =
          EnvoyWriter.EnvoyWriterRequest.newBuilder();
      writerRequest.setJsonPayload(jsonPayload);
      writerRequest.setClientId(vesselReply.getName());
      writerRequest.setImoNumber(vesselReply.getImoNumber());
      writerRequest.setMessageType(String.valueOf(MessageTypes.ALGORESULT));
      writerRequest.setMessageId(algoResponseCommunication.getMessageId());
      return this.envoyWriterService.getCommunicationServer(writerRequest.build());

    } catch (InvalidProtocolBufferException e) {
      log.error("Exception when calling passResultPayloadToEnvoyWriter  ", e);
    }
    return null;
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

  public EnvoyWriter.WriterReply passRequestPayloadToEnvoyWriter(
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy)
      throws GenericServiceException, IOException {
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String loadableStudyJson = null;
    try {
      VesselInfo.VesselDetail vesselReply =
          this.getVesselDetailsForEnvoy(loadableStudy.getVesselId());
      loadableStudyJson = ow.writeValueAsString(loadableStudy);
      EnvoyWriter.EnvoyWriterRequest.Builder writerRequest =
          EnvoyWriter.EnvoyWriterRequest.newBuilder();
      writerRequest.setJsonPayload(loadableStudyJson);
      writerRequest.setClientId(vesselReply.getName());
      writerRequest.setMessageType(String.valueOf(MessageTypes.LOADABLESTUDY));
      writerRequest.setImoNumber(vesselReply.getImoNumber());
      return this.envoyWriterService.getCommunicationServer(writerRequest.build());

    } catch (JsonProcessingException e) {
      log.error("Exception when when calling EnvoyWriter", e);
    }
    return null;
  }
}
