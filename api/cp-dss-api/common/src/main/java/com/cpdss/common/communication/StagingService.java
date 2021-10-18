/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.communication.repository.StagingRepository;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.StagingStatus;
import com.google.gson.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;

/**
 * StagingService service class for DataTransferStage entity operations
 *
 * @author Selvy Thomas
 */
@Log4j2
public class StagingService {

  private StagingRepository stagingRepository;

  private static final String PROCESS_TYPE = "table";
  private static final String STATUS = "draft";
  private static final String CREATED_OR_UPDATED_BY = "communication";
  private static final String DATA = "data";
  private static final String META_DATA = "meta_data";

  public StagingService(StagingRepository stagingRepository) {
    this.stagingRepository = stagingRepository;
  }

  /**
   * Method save used to save json to data_transfer_stage table.
   *
   * @param jsonResult - the json string
   * @return DataTransferStage - object
   */
  public void save(String jsonResult) throws GenericServiceException {
    try {
      log.info("Json string get in save method: " + jsonResult);
      DataTransferStage dataTransferStageobj = null;
      if (!StringUtils.isEmpty(jsonResult)) {
        JsonArray jsonArrayObj = new Gson().fromJson(jsonResult, JsonArray.class);
        for (JsonElement jsonElement : jsonArrayObj) {
          final JsonObject obj = jsonElement.getAsJsonObject();
          final JsonArray data = obj.getAsJsonArray(DATA);
          final JsonObject metaData = obj.getAsJsonObject(META_DATA);
          DataTransferStage dataTransferStage = new Gson().fromJson(metaData, DataTransferStage.class);
          //Gson gson = new GsonBuilder().setPrettyPrinting().create();
          //dataTransferStage.setData(gson.toJson(data));
          dataTransferStage.setData(data.toString());
          dataTransferStage.setStatus(STATUS);
          dataTransferStage.setProcessType(PROCESS_TYPE);
          dataTransferStage.setCreatedBy(CREATED_OR_UPDATED_BY);
          dataTransferStage.setLastModifiedBy(CREATED_OR_UPDATED_BY);
          dataTransferStageobj=stagingRepository.save(dataTransferStage);
        }
       this.updateStatusForProcessId(dataTransferStageobj.getProcessId(), StagingStatus.READY_TO_PROCESS.getStatus());
      }
    } catch (ResourceAccessException e) {
      log.info("Error when saving into DB ", e);
      throw new GenericServiceException(
          "Exception occurs in save method",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } catch (Exception e) {
      log.error("Exception occurs in save method  ", e);
      throw new GenericServiceException(
          "Exception occurs in save method",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }
  /**
   * Method getByProcessId used to all entries corresponding to processId.
   *
   * @param processId - the processId
   * @return List<DataTransferStage> - list of DataTransferStage
   */
  public List<DataTransferStage> getByProcessId(String processId) {
    return stagingRepository.findByProcessId(processId);
  }

  /**
   * Method updateStatus used to update the status
   *
   * @param Id - Id
   * @param status - the status
   * @return int
   */
  public int updateStatus(Long Id, String status) {
    return stagingRepository.updateStatus(Id, status);
  }

  /**
   * Method isAllDataReceived used to check all data received in the table.
   *
   * @param processId - Id
   * @param processIdentifierList - the list of processIdentifier
   * @return boolean
   */
  public boolean isAllDataReceived(String processId, List<String> processIdentifierList) {
    List<DataTransferStage> list = this.getByProcessId(processId);
    if (list != null && !list.isEmpty()) {
      List<String> processIdentifierFromDB =
          list.stream().map(DataTransferStage::getProcessIdentifier).collect(Collectors.toList());
      if (processIdentifierFromDB != null
          && !processIdentifierFromDB.isEmpty()
          && processIdentifierFromDB.containsAll(processIdentifierList)) {
        log.info("All data received in the data_transfer_stage table  " + list);
        return true;
      }
      return false;
    }
    return false;
  }
  /**
   * Method updateStatusForProcessId used to status to In progress
   *
   * @param processId - Id
   */
  public void updateStatusForProcessId(String processId, String status) {
    stagingRepository.updateStatusForProcessId(processId, status);
  }
  public Optional<DataTransferStage> getById(Long id) {
    return stagingRepository.findById(id);
  }

  public List<DataTransferStage> getAllWithStatus(String status){
    return stagingRepository.getAllWithStatus(status);
  }

  public List<DataTransferStage> getAllWithStatusAndTime(String status, LocalDateTime dateTime){
    return stagingRepository.getAllWithStatusAndTime(status, dateTime);
  }

  public void updateStatusAndModifiedDateTimeForProcessId(String processId, String status, LocalDateTime modifiedDateTime) {
  stagingRepository.updateStatusAndModifiedDateTimeForProcessId(processId,status,modifiedDateTime);
  }

  public void updateStatusAndStatusDescriptionForId(Long id, String status, String statusDescription, LocalDateTime modifiedDateTime) {
    stagingRepository.updateStatusAndStatusDescriptionForId(id,status,statusDescription,modifiedDateTime);
  }

  public void updateStatusCompletedForProcessId(String processId, String status) {
    stagingRepository.updateStatusCompletedForProcessId(processId, status);
  }

}
