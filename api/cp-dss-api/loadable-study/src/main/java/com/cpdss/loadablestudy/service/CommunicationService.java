/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.domain.AlgoResponse;
import com.cpdss.loadablestudy.domain.CommunicationStatus;
import com.cpdss.loadablestudy.domain.LoadabalePatternValidateRequest;
import com.cpdss.loadablestudy.domain.LoadablePatternAlgoRequest;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyCommunicationStatus;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import javax.transaction.Transactional;
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
@Transactional
public class CommunicationService {
  @Autowired private LoadableStudyServiceShore loadableStudyServiceShore;
  @Autowired VoyageService voyageService;
  @Autowired LoadableQuantityService loadableQuantityService;
  @Autowired LoadableStudyService loadableStudyService;
  @Autowired LoadablePatternService loadablePatternService;
  @Autowired JsonDataService jsonDataService;
  @Autowired private RestTemplate restTemplate;
  @Autowired private LoadableStudyRepository loadableStudyRepository;
  @Autowired private LoadablePatternRepository loadablePatternRepository;
  @Autowired private LoadablePlanService loadablePlanService;
  @Autowired private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;
  @Autowired private LoadicatorService loadicatorService;

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

  public void getDataFromCommInShoreSide(
      Map<String, String> taskReqParams, EnumSet<MessageTypes> shore)
      throws GenericServiceException {
    log.info("inside getDataFromCommInShoreSide ");
    for (MessageTypes messageType : shore) {
      try {
        log.info("inside getDataFromCommInShoreSide messageType " + messageType.getMessageType());
        if (messageType.getMessageType().equals("LoadableStudy")) {
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
            log.info("LoadableStudy received at shore ");
            saveLoadableStudyShore(erReply);
          }
        } else if (messageType.getMessageType().equals("ValidatePlan")) {
          log.info(
              "--------LoadableStudy received at shore side for validating plan "
                  + taskReqParams.toString());
          EnvoyReader.EnvoyReaderResultReply erReply =
              getResultFromEnvoyReaderShore(taskReqParams, messageType);
          if (!SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
            throw new GenericServiceException(
                "Failed to get Result from Communication Server",
                erReply.getResponseStatus().getCode(),
                HttpStatusCode.valueOf(Integer.valueOf(erReply.getResponseStatus().getCode())));
          }
          if (erReply != null && !erReply.getPatternResultJson().isEmpty()) {
            log.info("@@@@@@@@@@@LoadableStudy received at shore side ");
            saveValidatePlanRequestShore(erReply);
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

  @Autowired private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @Autowired private AlgoErrorsRepository algoErrorsRepository;

  @Autowired private AlgoErrorHeadingRepository algoErrorHeadingRepository;

  private void savePatternInShipSide(EnvoyReader.EnvoyReaderResultReply erReply)
      throws GenericServiceException {
    String jsonResult = erReply.getPatternResultJson();
    log.info("Pattern has reached in ship side from shore side");
    // log.info("------Pattern details payload : " + jsonResult);
    LoadablePatternAlgoRequest loadablePatternAlgoRequest =
        new Gson()
            .fromJson(jsonResult, com.cpdss.loadablestudy.domain.LoadablePatternAlgoRequest.class);
    Optional<LoadablePattern> loadablePatternOpt =
        this.loadablePatternRepository.findByIdAndIsActive(
            loadablePatternAlgoRequest.getLoadablePatternId(), true);
    if (!loadablePatternOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable pattern does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    if (!loadablePatternAlgoRequest.getValidated()) {
      loadablePatternAlgoStatusRepository.updateLoadablePatternAlgoStatus(
          LOADABLE_PATTERN_VALIDATION_FAILED_ID, loadablePatternAlgoRequest.getProcessId(), true);

      algoErrorsRepository.deleteAlgoError(
          false, loadablePatternAlgoRequest.getLoadablePatternId());
      algoErrorHeadingRepository.deleteAlgoErrorHeading(
          false, loadablePatternAlgoRequest.getLoadablePatternId());
      if (loadablePatternAlgoRequest.getAlgoError() != null) {
        loadablePatternAlgoRequest
            .getAlgoError()
            .forEach(
                algoErrors -> {
                  com.cpdss.loadablestudy.entity.AlgoErrors algoErrorsEntity =
                      new com.cpdss.loadablestudy.entity.AlgoErrors();
                  algoErrorsEntity.setAlgoErrorHeading(algoErrors.getAlgoErrorHeading());
                  algoErrorsEntity.setErrorMessage(algoErrors.getErrorMessage());
                  algoErrorsEntity.setIsActive(true);
                  algoErrorsRepository.save(algoErrors);
                });
      }
    } else {
      loadablePatternService.deleteExistingPlanDetails(loadablePatternOpt.get());
      if (loadablePatternAlgoRequest.getPatternDetails() != null) {
        loadableStudyServiceShore.savePatternInShipSide(
            loadablePatternAlgoRequest.getPatternDetails(), loadablePatternOpt.get());
      }
      if (!loadablePatternAlgoRequest.getHasLoadicator()) {
        loadablePatternAlgoStatusRepository.updateLoadablePatternAlgoStatus(
            LOADABLE_PATTERN_VALIDATION_SUCCESS_ID,
            loadablePatternAlgoRequest.getProcessId(),
            true);
        log.info("----pattern persisted in ship without loadicator");
      } else {
        loadicatorService.updateFeedbackLoopParameters(
            loadablePatternAlgoRequest.getLoadablePatternId(),
            true,
            false,
            loadablePatternAlgoRequest.getFeedBackLoopCount(),
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID);
        loadablePatternAlgoStatusRepository.updateLoadablePatternAlgoStatus(
            LOADABLE_PATTERN_VALIDATION_SUCCESS_ID,
            loadablePatternAlgoRequest.getProcessId(),
            true);
        log.info("----pattern persisted in ship with loadicator");
      }
    }
    log.info("Pattern has successfully updated in ship");
  }

  private void saveValidatePlanRequestShore(EnvoyReader.EnvoyReaderResultReply erReply) {
    try {
      String jsonResult = erReply.getPatternResultJson();
      // log.info("Json payload :" + jsonResult);
      LoadabalePatternValidateRequest loadabalePatternValidateRequest =
          new Gson()
              .fromJson(
                  jsonResult, com.cpdss.loadablestudy.domain.LoadabalePatternValidateRequest.class);
      LoadableStudy loadableStudyEntity =
          loadableStudyServiceShore.persistShipPayloadInShoreSide(
              erReply.getMessageId(), loadabalePatternValidateRequest);
      if (loadableStudyEntity != null) {
        voyageService.checkIfVoyageClosed(loadableStudyEntity.getVoyage().getId());
        this.loadableQuantityService.validateLoadableStudyWithLQ(loadableStudyEntity);
        log.info("algo process started in shore");
        processAlgoFromShoreSide(loadabalePatternValidateRequest, loadableStudyEntity);
      }
    } catch (Exception e) {
      log.error(
          "Exception has occurred updating payload in shore side which is received from ship  ", e);
    }
  }

  public void getDataFromCommInShipSide(
      Map<String, String> taskReqParams, EnumSet<MessageTypes> ship)
      throws GenericServiceException {
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
        if (messageType.getMessageType().equals("AlgoResult")) {
          saveAlgoPatternFromShore(erReply);
        } else if (messageType.getMessageType().equals("PatternDetail")) {
          if (erReply != null && !erReply.getPatternResultJson().isEmpty()) {
            log.info("-------Pattern received  received at ship side ");
            savePatternInShipSide(erReply);
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

  public void saveLoadableStudyShore(EnvoyReader.EnvoyReaderResultReply erReply) {

    try {

      String jsonResult = erReply.getPatternResultJson();
      LoadableStudy loadableStudyEntity =
          loadableStudyServiceShore.setLoadableStudyShore(jsonResult, erReply.getMessageId());
      if (loadableStudyEntity != null) {
        voyageService.checkIfVoyageClosed(loadableStudyEntity.getVoyage().getId());
        this.loadableQuantityService.validateLoadableStudyWithLQ(loadableStudyEntity);
        log.info("processAlgo started");
        processAlgoFromShore(loadableStudyEntity);
      }

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when generating pattern", e);

    } catch (ResourceAccessException e) {
      log.info("Error calling ALGO ");

    } catch (Exception e) {
      log.error("Exception when when calling algo  ", e);
    }
  }

  private void processAlgoFromShoreSide(
      LoadabalePatternValidateRequest lPValidateRequest, LoadableStudy loadableStudyEntity)
      throws IOException, GenericServiceException {
    LoadabalePatternValidateRequest loadabalePatternValidateRequest =
        new LoadabalePatternValidateRequest();
    ModelMapper modelMapper = new ModelMapper();
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    ObjectMapper objectMapper = new ObjectMapper();
    Optional<LoadablePattern> loadablePatternOpt =
        this.loadablePatternRepository.findByIdAndIsActive(
            lPValidateRequest.getLoadablePatternId(), true);
    loadableStudyService.buildLoadableStudy(
        loadableStudyEntity.getId(), loadableStudyEntity, loadableStudy, modelMapper);
    loadablePlanService.buildLoadablePlanPortWiseDetails(
        loadablePatternOpt.get(), loadabalePatternValidateRequest);
    loadabalePatternValidateRequest.setLoadableStudy(loadableStudy);
    loadabalePatternValidateRequest.setBallastEdited(
        stowageDetailsTempRepository.isBallastEdited(loadablePatternOpt.get().getId(), true));
    loadabalePatternValidateRequest.setLoadablePatternId(loadablePatternOpt.get().getId());
    loadabalePatternValidateRequest.setCaseNumber(loadablePatternOpt.get().getCaseNumber());
    jsonDataService.saveJsonToDatabase(
        lPValidateRequest.getLoadablePatternId(),
        LOADABLE_PATTERN_EDIT_REQUEST,
        objectMapper.writeValueAsString(loadabalePatternValidateRequest));
    objectMapper.writeValue(
        new File(
            this.rootFolder
                + "/json/loadablePattern_request_"
                + loadablePatternOpt.get().getId()
                + ".json"),
        loadabalePatternValidateRequest);
    AlgoResponse algoResponse =
        restTemplate.postForObject(
            loadableStudyUrl, loadabalePatternValidateRequest, AlgoResponse.class);

    loadablePlanService.updateProcessIdForLoadablePattern(
        algoResponse.getProcessId(),
        loadablePatternOpt.get(),
        LOADABLE_PATTERN_VALIDATION_STARTED_ID);
    log.info("Algo response : " + algoResponse.toString());
  }

  private void processAlgoFromShore(LoadableStudy loadableStudyEntity)
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
        LoadableStudiesConstants.LOADABLE_STUDY_PROCESSING_STARTED_ID,
        "",
        true);
    log.info("Algo response : " + algoResponse.toString());

    loadableStudyRepository.updateLoadableStudyStatus(
        LoadableStudiesConstants.LOADABLE_STUDY_PROCESSING_STARTED_ID, loadableStudyEntity.getId());
  }

  private EnvoyReader.EnvoyReaderResultReply getResultFromEnvoyReaderShore(
      Map<String, String> taskReqParams, MessageTypes messageType) {
    log.info("inside getResultFromEnvoyReaderShore ");
    EnvoyReader.EnvoyReaderResultRequest.Builder request =
        EnvoyReader.EnvoyReaderResultRequest.newBuilder();
    request.setMessageType(messageType.getMessageType());
    request.setClientId(taskReqParams.get("ClientId"));
    request.setShipId(taskReqParams.get("ShipId"));
    return this.envoyReaderGrpcService.getResultFromCommServer(request.build());
  }

  public void saveAlgoPatternFromShore(EnvoyReader.EnvoyReaderResultReply erReply) {

    String jsonResult = erReply.getPatternResultJson();
    com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder load =
        com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.newBuilder();
    if (!jsonResult.isEmpty())
      loadablePatternService.saveLoadablePatternDetails(erReply.getPatternResultJson(), load);
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
                } catch (GenericServiceException e) {
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
      // writerRequest.setClientId("KAZUSA_VINOTH");
      writerRequest.setClientId(vesselReply.getName());
      writerRequest.setImoNumber(vesselReply.getImoNumber());
      writerRequest.setMessageType(MessageTypes.ALGORESULT.getMessageType());
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
      String requestJson, Long VesselId, String messageType) throws GenericServiceException {
    VesselInfo.VesselDetail vesselReply = this.getVesselDetailsForEnvoy(VesselId);
    EnvoyWriter.EnvoyWriterRequest.Builder writerRequest =
        EnvoyWriter.EnvoyWriterRequest.newBuilder();
    writerRequest.setJsonPayload(requestJson);
    // writerRequest.setClientId("KAZUSA_VINOTH");
    writerRequest.setClientId(vesselReply.getName());
    writerRequest.setMessageType(messageType);
    writerRequest.setImoNumber(vesselReply.getImoNumber());
    return this.envoyWriterService.getCommunicationServer(writerRequest.build());
  }
}
