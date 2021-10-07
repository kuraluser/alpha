/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import static com.cpdss.loadingplan.utility.LoadingPlanConstants.*;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;


import com.cpdss.loadingplan.communication.LoadingPlanStagingRepository;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
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
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Log4j2
@Service
public class CommunicationService {

  @Autowired private RestTemplate restTemplate;
  @Autowired private LoadingPlanStagingService loadingPlanStagingService;

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

  public void getDataFromStagingTable() {
    log.info("inside getDataFromStagingTable");
    List<DataTransferStage> dataTransferStages = loadingPlanStagingService.getAllNotStarted("draft");
  }
}
