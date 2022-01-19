/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.communication;

import com.cpdss.common.communication.StagingService;
import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.EnvoyReader;
import com.cpdss.common.generated.EnvoyReaderServiceGrpc;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.common.utils.StagingStatus;
import com.cpdss.gateway.entity.FileRepo;
import com.cpdss.gateway.repository.FileRepoRepository;
import com.cpdss.gateway.service.communication.models.FileData;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;

@Service
@Transactional
@Log4j2
public class FileSharingStagingAndDownloaderService extends StagingService {

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  public FileSharingStagingAndDownloaderService(
      @Autowired FileSharingStagingRepository fileSharingStagingRepository) {
    super(fileSharingStagingRepository);
  }

  // region Autowired

  @Autowired private FileSharingStagingRepository fileSharingStagingRepository;

  @GrpcClient("envoyReaderService")
  private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

  @Autowired FileRepoRepository fileRepoRepository;
  // endregion

  // region declaration
  public static final String SUCCESS = "SUCCESS";
  private static final String PROCESS_TYPE = "table";
  private static final String PROCESS_IDENTIFIER = "file_repo";
  // endregion

  // region save file details to stage and write file data
  public void saveToStage(Map<String, String> taskReqParams) throws GenericServiceException {
    var erReply =
        getResultFromEnvoyReader(taskReqParams, MessageTypes.FILE_SHAREING.getMessageType());
    if (!SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to get Result from Communication Server for: "
              + MessageTypes.FILE_SHAREING.getMessageType(),
          erReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(erReply.getResponseStatus().getCode())));
    }
    if (erReply != null && !erReply.getPatternResultJson().isEmpty()) {
      log.info(
          "Data received from envoy reader for: " + MessageTypes.FILE_SHAREING.getMessageType());
      FileData fileData = new Gson().fromJson(erReply.getPatternResultJson(), FileData.class);

      FileRepo fileRepo =
          bindDataToEntity(
              new FileRepo(), new TypeToken<FileRepo>() {}.getType(), fileData.getDetails());
      // save File data details to stage
      saveFileDetailsIntoStagingTable(fileRepo.toString());
      log.info("File details saved into staging table with Id :{} ", fileRepo.getId());
      // write file data to corresponding path
      writeDataToFile(fileRepo, fileData.getData());
    } else {
      log.info(
          "No data found for the message type: " + MessageTypes.FILE_SHAREING.getMessageType());
    }
  }
  // endregion

  // region write Data To File
  private void writeDataToFile(FileRepo fileRepo, String data) {
    log.info("Before writing file data");
    String folderLocation;
    try {
      if (!fileRepo.getIsSystemGenerated()) {
        folderLocation = rootFolder + "/file-repo/" + fileRepo.getVoyageNumber() + "/";
      } else {
        folderLocation = fileRepo.getFilePath() + "/";
      }
      Files.writeString(Path.of(folderLocation + fileRepo.getFileName()), data);
      log.info("File data wrote into :{}", folderLocation);
    } catch (IOException e) {
      log.error("failed to write file ", e);
    }
  }
  // endregion

  // region saveFileDetailsIntoStagingTable
  private void saveFileDetailsIntoStagingTable(String fileDetails) {
    try {
      DataTransferStage dataTransferStage = new DataTransferStage();
      dataTransferStage.setProcessId(UUID.randomUUID().toString());
      dataTransferStage.setProcessGroupId(MessageTypes.FILE_SHAREING.getMessageType());
      dataTransferStage.setProcessIdentifier(PROCESS_IDENTIFIER);
      dataTransferStage.setProcessType(PROCESS_TYPE);
      dataTransferStage.setData(fileDetails);
      dataTransferStage.setStatus(StagingStatus.READY_TO_PROCESS.getStatus());
      dataTransferStage = fileSharingStagingRepository.save(dataTransferStage);
      log.info(
          "Data saved in data_transfer_stage table processId:{} and Id:{}",
          dataTransferStage.getProcessId(),
          dataTransferStage.getId());
    } catch (ResourceAccessException e) {
      log.error("Error when saving into DB ", e);
    } catch (Exception e) {
      log.error("Exception occurs in save method  ", e);
    }
  }
  // endregion

  // region save file details to FileRepo
  public void saveToFileRepo() {
    log.info("Inside saveToFileRepo");
    List<DataTransferStage> readyStatusFiles =
        this.getAllWithStatus(StagingStatus.READY_TO_PROCESS.getStatus());
    if (!CollectionUtils.isEmpty(readyStatusFiles)) {
      log.info("File details to save:{}", readyStatusFiles.size());
      Map<String, List<DataTransferStage>> groupedData =
          readyStatusFiles.stream()
              .collect(
                  Collectors.groupingBy(dataTransferStage -> dataTransferStage.getProcessId()));
      groupedData
          .entrySet()
          .forEach(
              fileDownloadStage -> {
                var details =
                    fileDownloadStage.getValue().stream()
                        .filter(
                            dataTransferStage ->
                                dataTransferStage
                                    .getProcessGroupId()
                                    .equals(MessageTypes.FILE_SHAREING.getMessageType()))
                        .collect(Collectors.toList())
                        .get(0);
                this.updateStatusForProcessId(
                    details.getProcessId(), StagingStatus.IN_PROGRESS.getStatus());
                log.info("updated status to in_progress for processId:{}", details.getProcessId());
                FileRepo repo = new Gson().fromJson(details.getData(), FileRepo.class);
                try {
                  repo = fileRepoRepository.save(repo);
                  log.info("Communication ====  Saved File Repo:" + repo.getId());
                  this.updateStatusCompletedForProcessId(
                      details.getProcessId(), StagingStatus.COMPLETED.getStatus());
                  log.info("updated status to completed for processId:" + details.getProcessId());
                } catch (Exception e) {
                  log.info("Communication ++++++++++++ Exception : " + e.getMessage());
                  updateStatusInExceptionCase(
                      details.getId(),
                      details.getProcessId(),
                      StagingStatus.FAILED.getStatus(),
                      e.getMessage());
                }
              });
    }
  }
  // endregion

  // region Results From Envoy Reader
  private EnvoyReader.EnvoyReaderResultReply getResultFromEnvoyReader(
      Map<String, String> taskReqParams, String messageType) {
    log.info("Inside getResultFromEnvoyReader with messageType:{}", messageType);
    EnvoyReader.EnvoyReaderResultRequest.Builder request =
        EnvoyReader.EnvoyReaderResultRequest.newBuilder();
    request.setMessageType(messageType);
    request.setClientId(taskReqParams.get("ClientId"));
    request.setShipId(taskReqParams.get("ShipId"));
    return this.envoyReaderGrpcService.getResultFromCommServer(request.build());
  }
  // endregion

  // region Data Binding
  private <T> T bindDataToEntity(Object entity, Type listType, String jsonData) {
    try {
      HashMap<String, String> map = this.getAttributeMapping(entity);
      JsonArray jsonArray =
          convertToEntityFields(JsonParser.parseString(jsonData).getAsJsonArray(), map);
      return new Gson().fromJson(jsonArray.get(0).getAsJsonObject(), listType);
    } catch (Exception e) {
      log.error(
          "Communication XXXXXXX Unable to bind the Json to object : "
              + entity.getClass().getName());
      log.error(e);
    }
    return null;
  }

  private JsonArray convertToEntityFields(JsonArray array, HashMap<String, String> map) {
    JsonArray json = this.getAsEntityJson(map, array);
    JsonArray jsonArray = new JsonArray();
    for (JsonElement jsonElement : json) {
      final JsonObject jsonObj = jsonElement.getAsJsonObject();
      jsonArray.add(jsonObj);
    }
    return jsonArray;
  }
  // endregion

  // region update status in the case exception
  private void updateStatusInExceptionCase(
      Long id, String processId, String status, String statusDescription) {
    this.updateStatusAndStatusDescriptionForId(id, status, statusDescription, LocalDateTime.now());
    this.updateStatusAndModifiedDateTimeForProcessId(processId, status, LocalDateTime.now());
  }
  // endregion
}
