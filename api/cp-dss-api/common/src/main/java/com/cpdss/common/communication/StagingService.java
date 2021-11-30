/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.communication.repository.StagingRepository;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.StagingStatus;
import com.google.gson.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
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
          DataTransferStage dataTransferStage =
              new Gson().fromJson(metaData, DataTransferStage.class);
          // Gson gson = new GsonBuilder().setPrettyPrinting().create();
          // dataTransferStage.setData(gson.toJson(data));
          dataTransferStage.setData(data.toString());
          dataTransferStage.setStatus(STATUS);
          dataTransferStage.setProcessType(PROCESS_TYPE);
          dataTransferStage.setCreatedBy(CREATED_OR_UPDATED_BY);
          dataTransferStage.setLastModifiedBy(CREATED_OR_UPDATED_BY);
          dataTransferStageobj = stagingRepository.save(dataTransferStage);
        }
        log.info(
            "All data saved in data_transfer_stage table processId:{}",
            dataTransferStageobj.getProcessId());
        this.updateStatusReadyToProcessForProcessId(
            dataTransferStageobj.getProcessId(), StagingStatus.READY_TO_PROCESS.getStatus());
        log.info(
            "status changed to ready_to_process for processId:{}",
            dataTransferStageobj.getProcessId());
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

  /**
   * Method updateStatusForProcessId used to status to ready_to_process
   *
   * @param processId - Id
   * @param status - Id
   */
  public void updateStatusReadyToProcessForProcessId(String processId, String status) {
    stagingRepository.updateStatusReadyToProcessForProcessId(processId, status);
  }

  public Optional<DataTransferStage> getById(Long id) {
    return stagingRepository.findById(id);
  }

  public List<DataTransferStage> getAllWithStatus(String status) {
    return stagingRepository.getAllWithStatus(status);
  }

  public List<DataTransferStage> getAllWithStatusAndTime(String status, LocalDateTime dateTime) {
    return stagingRepository.getAllWithStatusAndTime(status, dateTime);
  }

  public void updateStatusAndModifiedDateTimeForProcessId(
      String processId, String status, LocalDateTime modifiedDateTime) {
    stagingRepository.updateStatusAndModifiedDateTimeForProcessId(
        processId, status, modifiedDateTime);
  }

  public void updateStatusAndStatusDescriptionForId(
      Long id, String status, String statusDescription, LocalDateTime modifiedDateTime) {
    stagingRepository.updateStatusAndStatusDescriptionForId(
        id, status, statusDescription, modifiedDateTime);
  }

  public void updateStatusCompletedForProcessId(String processId, String status) {
    stagingRepository.updateStatusCompletedForProcessId(processId, status);
  }

  public HashMap<String, String> getAttributeMapping(Object classInstance) {

    Class ftClass = classInstance.getClass();
    HashMap<String, String> attributeMap = new HashMap<>();
    Field[] fields = ftClass.getDeclaredFields();
    String db_field = "";
    String entity_field = "";
    for (int i = 0; i < fields.length; i++) {
      db_field = fields[i].getName();
      entity_field = fields[i].getName();

      Annotation[] annotations = fields[i].getDeclaredAnnotations();
      for (Annotation ann : annotations) {
        if (ann.annotationType().getName().equals("javax.persistence.Column")) {
          db_field =
              Arrays.asList(ann.toString().split(","))
                  .get(3)
                  .replace("name=", "")
                  .replace("\"", "")
                  .trim();
          continue;
        }
      }
      attributeMap.put(db_field, entity_field);
    }
    attributeMap.put("id", "id");
    attributeMap.put("version", "version");
    attributeMap.put("created_by", "createdBy");
    attributeMap.put("created_date", "createdDate");
    attributeMap.put("created_date_time", "createdDateTime");
    attributeMap.put("last_modified_by", "lastModifiedBy");
    attributeMap.put("last_modified_date", "lastModifiedDate");
    attributeMap.put("last_modified_date_time", "lastModifiedDateTime");
    return attributeMap;
  }

  public JsonArray getAsEntityJson(HashMap<String, String> attributeMap, JsonArray jsonArrayObj) {
    JsonArray array = new JsonArray();
    for (JsonElement jsonElement : jsonArrayObj) {
      final JsonObject jsonObj = jsonElement.getAsJsonObject();
      Set<String> keySet = jsonObj.keySet();
      for (String key : attributeMap.keySet()) {
        if (!key.equals(attributeMap.get(key))) {
          modifyAttributeValue(jsonObj, key, attributeMap.get(key));
          jsonObj.remove(key);
        }
      }
      array.add(jsonObj);
    }
    return array;
  }

  private void modifyAttributeValue(JsonObject jsonObj, String sourceKey, String targetKey) {
    String[] dateTimeFields = {
            "created_date_time", "last_modified_date_time", "voyage_start_date", "voyage_end_date"
    };
    String[] timeFields = {"sunrise_time", "sunset_time", "start_time", "tide_time"};
    String[] dateFields = {"created_date", "last_modified_date"};
    String value = jsonObj.get(sourceKey) == null ? null : jsonObj.get(sourceKey).toString();
    if (Arrays.stream(dateTimeFields).anyMatch(sourceKey::equals)) {
      jsonObj.add(targetKey, getJsonObjectFromTimeStamp(value, true));
    } else if (Arrays.stream(timeFields).anyMatch(sourceKey::equals)
        && value != null
        && value.split(":").length == 3) {
      jsonObj.add(targetKey, getJsonObjectFromTimeStamp(value, true, true));
    } else if (Arrays.stream(dateFields).anyMatch(sourceKey::equals)) {
      jsonObj.add(targetKey, getJsonObjectFromTimeStamp(value, false));
    } else {
      jsonObj.add(targetKey, jsonObj.get(sourceKey));
    }
  }

  public JsonObject getJsonObjectFromTimeStamp(String dateTimeStr, Boolean haveTime) {
    return getJsonObjectFromTimeStamp(dateTimeStr, haveTime, false);
  }

  public JsonObject getJsonObjectFromTimeStamp(
      String dateTimeStr, Boolean haveTime, Boolean onlyTime) {
    try {
      dateTimeStr = dateTimeStr.replace("\\", "").replace("/", "").replaceAll("^\"|\"$", "");
      JsonObject dateTimeObject = new JsonObject();
      JsonObject dateObject = new JsonObject();
      if (onlyTime) {
        String[] time = dateTimeStr.split(":");
        JsonObject timeObject = new JsonObject();
        timeObject.addProperty("hour", Integer.valueOf(time[0]));
        timeObject.addProperty("minute", Integer.valueOf(time[1]));
        timeObject.addProperty("second", Integer.valueOf(time[2]));
        return timeObject;
      }

      String format = haveTime ? "yyyy-MM-dd'T'HH:mm:ss.SSSSSS" : "yyyy-MM-dd";
      SimpleDateFormat sourceFormat = new SimpleDateFormat(format);
      TimeZone utc = TimeZone.getTimeZone("UTC");
      sourceFormat.setTimeZone(utc);
      Date convertedDate = null;
      try {
        convertedDate = sourceFormat.parse(dateTimeStr);
      } catch (ParseException e) {
        convertedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateTimeStr);
      }
      Calendar calendar = new GregorianCalendar();
      calendar.setTime(convertedDate);
      dateObject.addProperty("year", calendar.get(Calendar.YEAR));
      dateObject.addProperty("month", calendar.get(Calendar.MONTH) + 1);
      dateObject.addProperty("day", calendar.get(Calendar.DAY_OF_MONTH));
      if (haveTime) {
        dateTimeObject.add("date", dateObject);
        JsonObject timeObject = new JsonObject();
        timeObject.addProperty("hour", calendar.get(Calendar.HOUR_OF_DAY));
        timeObject.addProperty("minute", calendar.get(Calendar.MINUTE));
        timeObject.addProperty("second", calendar.get(Calendar.SECOND));
        timeObject.addProperty("nano", 217485000);
        dateTimeObject.add("time", timeObject);
        return dateTimeObject;
      } else {
        return dateObject;
      }

    } catch (Exception e) {

    }
    return null;
  }
}
