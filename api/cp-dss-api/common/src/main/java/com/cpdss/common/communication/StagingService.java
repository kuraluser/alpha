/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.communication.repository.StagingRepository;
import com.cpdss.common.exception.GenericServiceException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;

/**
 * StagingService service class for DataTransferStage entity operations
 *
 * @author Selvy Thomas
 */
@Log4j2
@Service
public class StagingService {

  @Autowired private StagingRepository stagingRepository;

  private static final String PROCESS_TYPE = "table";
  private static final String STATUS = "draft";
  private static final String CREATED_OR_UPDATED_BY = "communication";
  private static final String DATA = "data";
  private static final String META_DATA = "meta_data";

  /**
   * Method save used to save json to data_transfer_stage table.
   *
   * @param jsonResult - the json string
   * @return DataTransferStage - object
   */
  public DataTransferStage save(String jsonResult) throws GenericServiceException {
    try {
      log.info("Json string get in save method: " + jsonResult);
      if (!StringUtils.isEmpty(jsonResult)) {
        final JsonObject obj = new Gson().fromJson(jsonResult, JsonObject.class);
        final JsonArray data = obj.getAsJsonArray(DATA);
        final JsonObject metaData = obj.getAsJsonObject(META_DATA);
        DataTransferStage dataTransferStage =
            new Gson().fromJson(metaData, DataTransferStage.class);
        dataTransferStage.setData(data);
        dataTransferStage.setStatus(STATUS);
        dataTransferStage.setProcessType(PROCESS_TYPE);
        dataTransferStage.setCreatedBy(CREATED_OR_UPDATED_BY);
        dataTransferStage.setLastModifiedBy(CREATED_OR_UPDATED_BY);
        return stagingRepository.save(dataTransferStage);
      }
    } catch (ResourceAccessException e) {
      log.info("Error when saving into DB ", e);
    } catch (Exception e) {
      log.error("Exception occurs in save method  ", e);
    }
    return null;
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
    if (list != null
            && !list.isEmpty()){
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
}
