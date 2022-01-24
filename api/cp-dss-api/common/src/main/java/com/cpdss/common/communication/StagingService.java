/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication;

import static com.cpdss.common.communication.CommunicationConstants.*;
import static com.cpdss.common.communication.CommunicationConstants.CommunicationModule.getCommunicationModule;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.cpdss.common.communication.entity.DataTransferInBound;
import com.cpdss.common.communication.entity.DataTransferOutBound;
import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.communication.repository.DataTransferInBoundRepository;
import com.cpdss.common.communication.repository.DataTransferOutBoundRepository;
import com.cpdss.common.communication.repository.StagingRepository;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.EntityDoc;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.common.utils.StagingStatus;
import com.google.common.base.Strings;
import com.google.gson.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.persistence.Table;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;

/**
 * StagingService service class for DataTransferStage entity operations
 *
 * @author Selvy Thomas
 * @author mahesh.k
 * @author Mathew Gillory
 */
@Log4j2
@Service
public class StagingService {

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @GrpcClient("loadingPlanService")
  private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanGrpcService;

  @Value("${cpdss.communication.module}")
  private String currentModule;

  private StagingRepository stagingRepository;
  private DataTransferOutBoundRepository dataTransferOutBoundRepository;
  private DataTransferInBoundRepository dataTransferInBoundRepository;

  private static final String PROCESS_TYPE = "table";
  private static final String STATUS = "draft";
  private static final String CREATED_OR_UPDATED_BY = "communication";
  private static final String DATA = "data";
  private static final String META_DATA = "meta_data";
  private String[] exceptionalAttributesNames = {"eta", "etd"};
  private static final String UNKNOWN_CONSTANT = "unknown";
  private static final String COMM_DATA_SAVED_PREFIX_MSG_TEMPLATE =
      "Communication ####### Saved Data - Table: {} >> ";
  private static final String COMM_DATA_EMPTY_MSG_TEMPLATE =
      "Communication XXXXXXX Data Empty - Table: {}";

  public StagingService(StagingRepository stagingRepository) {
    this.stagingRepository = stagingRepository;
  }

  /**
   * Overloaded constructor
   *
   * @param stagingRepository staging repo object
   * @param dataTransferOutBoundRepository outbound repo object
   * @param dataTransferInBoundRepository inbound repo object
   */
  public StagingService(
      StagingRepository stagingRepository,
      DataTransferOutBoundRepository dataTransferOutBoundRepository,
      DataTransferInBoundRepository dataTransferInBoundRepository) {
    this.stagingRepository = stagingRepository;
    this.dataTransferOutBoundRepository = dataTransferOutBoundRepository;
    this.dataTransferInBoundRepository = dataTransferInBoundRepository;
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
        String processId = "";
        for (JsonElement jsonElement : jsonArrayObj) {
          final JsonObject obj = jsonElement.getAsJsonObject();
          final JsonArray data = obj.getAsJsonArray(DATA);
          final JsonObject metaData = obj.getAsJsonObject(META_DATA);
          DataTransferStage dataTransferStage =
              new Gson().fromJson(metaData, DataTransferStage.class);
          dataTransferStage.setData(data.toString());
          dataTransferStage.setStatus(STATUS);
          dataTransferStage.setProcessType(PROCESS_TYPE);
          dataTransferStage.setCreatedBy(CREATED_OR_UPDATED_BY);
          dataTransferStage.setLastModifiedBy(CREATED_OR_UPDATED_BY);
          dataTransferStageobj = stagingRepository.save(dataTransferStage);

          // Set only processId once and save to inbound table
          if (!processId.equals(dataTransferStage.getProcessId())) {
            processId = dataTransferStage.getProcessId();

            final String dependantProcessModule =
                getString(
                    metaData.get(CommunicationMetadata.DEPENDANT_PROCESS_MODULE.getFieldName()));
            final String dependantProcessId =
                getString(metaData.get(CommunicationMetadata.DEPENDANT_PROCESS_ID.getFieldName()));
            String inboundStatus = StagingStatus.DRAFT.getStatus();

            if (!StringUtils.hasLength(dependantProcessModule)
                && !StringUtils.hasLength(dependantProcessId)) {
              inboundStatus = StagingStatus.COMPLETED.getStatus();
            }
            saveDataTransferInBound(
                processId,
                MessageTypes.getMessageType(dataTransferStageobj.getProcessGroupId()),
                dependantProcessId,
                dependantProcessModule,
                inboundStatus);
          }
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
   * @param id - id
   * @param status - the status
   * @return int
   */
  public int updateStatus(Long id, String status) {
    return stagingRepository.updateStatus(id, status);
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
    String dbField = "";
    String entityField = "";
    for (int i = 0; i < fields.length; i++) {
      dbField = fields[i].getName();
      entityField = fields[i].getName();

      Annotation[] annotations = fields[i].getDeclaredAnnotations();
      for (Annotation ann : annotations) {
        if (ann.annotationType().getName().equals("javax.persistence.Column")) {
          dbField =
              Arrays.asList(ann.toString().split(","))
                  .get(3)
                  .replace("name=", "")
                  .replace("\"", "")
                  .trim();
          continue;
        }
      }
      attributeMap.put(dbField, entityField);
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
      for (String key : attributeMap.keySet()) {
        if (!key.equals(attributeMap.get(key))) {
          modifyAttributeValue(jsonObj, key, attributeMap.get(key));
          jsonObj.remove(key);
        } else if (Arrays.stream(exceptionalAttributesNames).anyMatch(key::equals)) {
          modifyAttributeValue(jsonObj, key, attributeMap.get(key));
        }
      }
      array.add(jsonObj);
    }
    return array;
  }

  private void modifyAttributeValue(JsonObject jsonObj, String sourceKey, String targetKey) {
    String[] dateTimeFields = {
      "created_date_time",
      "last_modified_date_time",
      "voyage_start_date",
      "voyage_end_date",
      "eta_actual",
      "etd_actual",
      "actual_start_date",
      "actual_end_date",
      "eta",
      "etd",
      "updated_time"
    };
    String[] timeFields = {
      "sunrise_time",
      "sunset_time",
      "start_time",
      "tide_time",
      "time_of_sunrise",
      "time_of_sunset",
      "hw_tide_time_from",
      "hw_tide_time_to",
      "lw_tide_time_from",
      "lw_tide_time_to"
    };
    String[] dateFields = {
      "created_date", "last_modified_date", "lay_can_from", "lay_can_to", "common_date"
    };
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
      log.error("Exception in getJsonObjectFromTimeStamp. dateTimeStr: {}", dateTimeStr, e);
    }
    return null;
  }

  /**
   * Method to set entity doc fields to staging entity
   *
   * @param stagingEntity staging entity value
   * @param actualEntity actual entity value - from table
   */
  public static void setEntityDocFields(
      final EntityDoc stagingEntity, final Optional<? extends EntityDoc> actualEntity) {
    actualEntity.ifPresentOrElse(
        entityDoc -> {
          stagingEntity.setCreatedBy(entityDoc.getCreatedBy());
          stagingEntity.setCreatedDate(entityDoc.getCreatedDate());
          stagingEntity.setCreatedDateTime(entityDoc.getCreatedDateTime());
          stagingEntity.setVersion(entityDoc.getVersion());
        },
        () -> {
          stagingEntity.setCreatedBy(CREATED_OR_UPDATED_BY);
          stagingEntity.setVersion(null);
        });
    stagingEntity.setLastModifiedBy(CREATED_OR_UPDATED_BY);
  }

  /**
   * Method to check if stage entity is valid
   *
   * @param stageEntity stageEntity value
   * @param tableName tableName value
   * @return true on valid entity and false on invalid
   */
  public static boolean isValidStageEntity(final EntityDoc stageEntity, final String tableName) {
    if (null == stageEntity) {
      log.info(COMM_DATA_EMPTY_MSG_TEMPLATE, tableName);
      return false;
    }
    return true;
  }

  /**
   * Method to check if stage entity is valid
   *
   * @param stageEntity stageEntity value
   * @param tableName tableName value
   * @return true on valid entity and false on invalid
   */
  public static boolean isValidStageEntity(
      final List<? extends EntityDoc> stageEntity, final String tableName) {
    if (isEmpty(stageEntity)) {
      log.info(COMM_DATA_EMPTY_MSG_TEMPLATE, tableName);
      return false;
    }
    return true;
  }

  /**
   * Method to check if stage entity is valid
   *
   * @param stageEntity stageEntity JSON string value
   * @param tableName tableName value
   * @return true on valid entity and false on invalid
   */
  public static boolean isValidStageEntity(final String stageEntity, final String tableName) {
    if (Strings.isNullOrEmpty(stageEntity)) {
      log.info(COMM_DATA_EMPTY_MSG_TEMPLATE, tableName);
      return false;
    }
    return true;
  }

  /**
   * Method to log saved entities
   *
   * @param stageEntity stageEntity value
   */
  public static void logSavedEntity(final EntityDoc stageEntity) {
    final String tableName = stageEntity.getClass().getAnnotation(Table.class).name();
    log.info(COMM_DATA_SAVED_PREFIX_MSG_TEMPLATE + "Id: {}", tableName, stageEntity.getId());
  }

  /**
   * Method to log external DB saved entities
   *
   * @param stageEntity stageEntity value
   */
  public static void logSavedEntity(final String tableName) {
    log.info(COMM_DATA_SAVED_PREFIX_MSG_TEMPLATE + "Id: <External DB>", tableName);
  }

  /**
   * Method to log saved entities
   *
   * @param stageEntity stageEntity stageEntity value
   */
  public static void logSavedEntity(final List<? extends EntityDoc> stageEntity) {
    AtomicReference<String> tableName = new AtomicReference<>(UNKNOWN_CONSTANT);
    stageEntity.stream()
        .findFirst()
        .ifPresent(
            entityDoc -> tableName.set(entityDoc.getClass().getAnnotation(Table.class).name()));

    log.info(
        COMM_DATA_SAVED_PREFIX_MSG_TEMPLATE + "Entries: {}", tableName.get(), stageEntity.size());
  }

  /**
   * Method for checking outbound data is communicated successfully or not
   *
   * @param reference reference type
   * @param referenceId reference id LS -> loadablePatternId
   * @param module module to be checked for communication status
   * @return boolean , true or false
   * @throws GenericServiceException Exception if module not configured
   */
  public boolean isCommunicated(final String reference, final Long referenceId, final String module)
      throws GenericServiceException {

    CommunicationModule requestModule = getCommunicationModule(module);

    if (module.equals(currentModule)) {
      Optional<DataTransferOutBound> dataTransferOutBoundOptional =
          dataTransferOutBoundRepository.findByReferenceAndReferenceId(reference, referenceId);
      if (dataTransferOutBoundOptional.isPresent()
          && dataTransferOutBoundOptional.get().getIsCommunicated()) {
        return true;
      }
    }
    // Call GRPC for other modules
    else {

      Common.CommunicationStatusCheckRequest communicationStatusCheckRequest =
          Common.CommunicationStatusCheckRequest.newBuilder().setReferenceId(referenceId).build();
      Common.CommunicationCheckResponse communicationCheckResponse = null;

      switch (requestModule) {
        case LOADABLE_STUDY:
          communicationCheckResponse =
              loadableStudyGrpcService.checkCommunicated(communicationStatusCheckRequest);
          break;
        case LOADING_PLAN:
          communicationCheckResponse =
              loadingPlanGrpcService.checkCommunicated(communicationStatusCheckRequest);
          break;
        case DISCHARGE_PLAN:
        default:
          // module not configured
          log.error("Not configured. Module: {}", currentModule);
          throw new GenericServiceException(
              "Not configured. Module: " + currentModule,
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
      }

      return communicationCheckResponse.getIsCompleted();
    }
    return false;
  }

  /**
   * Method for saving out bound communication status
   *
   * @param reference reference type
   * @param referenceId reference id isCommunicated boolean , always pass false value to the
   *     overloaded method.
   * @return Long value , out bound id
   */
  public Long saveDataTransferOutBound(String reference, Long referenceId) {
    return saveDataTransferOutBound(reference, referenceId, false);
  }

  /**
   * Method for saving out bound communication status
   *
   * @param reference reference type
   * @param referenceId reference id
   * @param isCommunicated boolean , true or false
   * @return Long value , out bound id
   */
  public Long saveDataTransferOutBound(String reference, Long referenceId, boolean isCommunicated) {

    MessageTypes messageType = MessageTypes.getMessageType(reference);

    DataTransferOutBound dataTransferOutBound;
    Optional<DataTransferOutBound> dataTransferOutBoundOptional =
        dataTransferOutBoundRepository.findByReferenceAndReferenceId(
            messageType.getSequenceMessageType(), referenceId);

    if (dataTransferOutBoundOptional.isPresent()) {
      dataTransferOutBound = dataTransferOutBoundOptional.get();
    } else {
      dataTransferOutBound = new DataTransferOutBound();
      dataTransferOutBound.setReference(messageType.getSequenceMessageType());
      dataTransferOutBound.setReferenceId(referenceId);
    }
    dataTransferOutBound.setIsCommunicated(isCommunicated);
    return dataTransferOutBoundRepository.save(dataTransferOutBound).getId();
  }

  /**
   * method for getting DataTransferInBound entity
   *
   * @param processId processId value
   * @return Entity of DataTransferInBound
   */
  private DataTransferInBound getInBoundProcess(String processId) throws GenericServiceException {

    return dataTransferInBoundRepository
        .findByProcessId(processId)
        .orElseThrow(
            () -> {
              log.error(
                  "Data not found in DataTransferInBound. Process Id: {}. Current Module: {}",
                  processId,
                  currentModule);
              return new GenericServiceException(
                  "Data not found in DataTransferInBound. Process Id: " + processId,
                  CommonErrorCodes.E_GEN_INTERNAL_ERR,
                  HttpStatusCode.INTERNAL_SERVER_ERROR);
            });
  }

  /**
   * Method for checking the dependant process status
   *
   * @param processId processId value
   * @param module module to be checked
   * @return is completed or not
   */
  public boolean dependantProcessIsCompleted(String processId, String module)
      throws GenericServiceException {
    DataTransferInBound dataTransferInBound = getInBoundProcess(processId);

    return checkDependantProcessStatus(module, dataTransferInBound);
  }

  /**
   * Method for checking the dependant process status and have the grpc implementation for
   * collecting data from other modules
   *
   * @param module Module to be checked
   * @param dataTransferInBound Inbound entity
   * @return true if process completed and false otherwise
   * @throws GenericServiceException Exception on failure
   */
  private boolean checkDependantProcessStatus(
      String module, DataTransferInBound dataTransferInBound) throws GenericServiceException {

    // Check in other modules when there is value in dependantProcessModule
    if (StringUtils.hasLength(dataTransferInBound.getDependantProcessModule())
        && StringUtils.hasLength(dataTransferInBound.getDependantProcessId())) {
      if (!module.equals(dataTransferInBound.getDependantProcessModule())) {
        // Check other modules
        Common.DependentProcessCheckRequestComm dependentProcessCheckRequestComm =
            Common.DependentProcessCheckRequestComm.newBuilder()
                .setDependantProcessId(dataTransferInBound.getDependantProcessId())
                .build();
        Common.CommunicationCheckResponse response = null;
        CommunicationModule dependantProcessModuleConst =
            getCommunicationModule(dataTransferInBound.getDependantProcessModule());

        // call GRPC methods
        switch (dependantProcessModuleConst) {
          case LOADABLE_STUDY:
            response =
                loadableStudyGrpcService.checkDependentProcess(dependentProcessCheckRequestComm);
            log.debug("LS Response checkDependantProcessStatus: {}", response);
            break;
          case LOADING_PLAN:
            response =
                loadingPlanGrpcService.checkDependentProcess(dependentProcessCheckRequestComm);
            break;
          case DISCHARGE_PLAN:
          default:
            // module not configured
            log.error("Not configured. Module: {}", module);
            throw new GenericServiceException(
                "Not configured. Module: " + module,
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
        if (SUCCESS.equals(response.getResponseStatus().getStatus())) {
          return response.getIsCompleted();
        }
      }
    } else {
      return StagingStatus.COMPLETED.getStatus().equals(dataTransferInBound.getStatus());
    }
    return false;
  }

  /**
   * Method for saving inbound data transfer
   *
   * @param processId processId value
   * @param process process name
   * @param dependantProcessId dependantProcessId value
   * @param dependantProcessModule dependantProcessModule value
   * @param status status value to be save
   * @return Long id of DataTransferInBound
   */
  public Long saveDataTransferInBound(
      final String processId,
      final MessageTypes messageType,
      final String dependantProcessId,
      final String dependantProcessModule,
      final String status) {

    DataTransferInBound dataTransferInBound =
        dataTransferInBoundRepository
            .findByProcessId(processId)
            .orElseGet(
                () -> {
                  DataTransferInBound dataTransferInBoundNew = new DataTransferInBound();
                  dataTransferInBoundNew.setProcessId(processId);
                  dataTransferInBoundNew.setProcess(messageType.getSequenceMessageType());
                  return dataTransferInBoundNew;
                });

    dataTransferInBound.setDependantProcessId(dependantProcessId);
    dataTransferInBound.setDependantProcessModule(dependantProcessModule);
    dataTransferInBound.setStatus(status);

    return dataTransferInBoundRepository.save(dataTransferInBound).getId();
  }

  /**
   * Method to add to JSON array
   *
   * @param array
   * @param objectList
   * @param processIdentifier
   * @param processId
   * @param processGroupId
   * @param processedList
   * @param jsonArray
   * @param dependantProcessId
   * @param dependantProcessModule
   */
  protected void addIntoProcessedList(
      JsonArray array,
      List<Object> objectList,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      JsonArray jsonArray,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {
    array.add(
        createJsonObject(
            objectList,
            processIdentifier,
            processId,
            processGroupId,
            jsonArray,
            dependantProcessId,
            dependantProcessModule));
    processedList.add(processIdentifier);
  }

  /**
   * Method to create JSON object
   *
   * @param list
   * @param processIdentifier
   * @param processId
   * @param processGroupId
   * @param jsonArray
   * @param dependantProcessId
   * @param dependantProcessModule
   * @return JsonObject value
   */
  public JsonObject createJsonObject(
      List<Object> list,
      String processIdentifier,
      String processId,
      String processGroupId,
      JsonArray jsonArray,
      @Nullable final String dependantProcessId,
      @Nullable final String dependantProcessModule) {

    JsonObject jsonObject = new JsonObject();

    // Add metadata fields
    JsonObject metaData = new JsonObject();
    metaData.addProperty(
        CommunicationMetadata.PROCESS_IDENTIFIER.getFieldName(), processIdentifier);
    metaData.addProperty(CommunicationMetadata.PROCESS_ID.getFieldName(), processId);
    metaData.addProperty(CommunicationMetadata.PROCESS_GROUP_ID.getFieldName(), processGroupId);
    metaData.addProperty(
        CommunicationMetadata.DEPENDANT_PROCESS_ID.getFieldName(), dependantProcessId);
    metaData.addProperty(
        CommunicationMetadata.DEPENDANT_PROCESS_MODULE.getFieldName(), dependantProcessModule);
    jsonObject.add(META_DATA, metaData);

    if (jsonArray != null) {
      jsonObject.add(DATA, jsonArray);
    } else {
      JsonArray array = new JsonArray();
      for (Object obj : list) {
        array.add(new Gson().toJson(obj));
      }
      jsonObject.add(DATA, array);
    }
    return jsonObject;
  }

  /**
   * Method to get string from JsonElement
   *
   * @param jsonElement jsonElement value
   * @return String value
   */
  private static String getString(JsonElement jsonElement) {
    if (null == jsonElement) {
      return null;
    }
    return jsonElement.isJsonNull() ? null : jsonElement.getAsString();
  }
}
