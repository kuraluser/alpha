/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.communication;

import com.cpdss.common.communication.StagingService;
import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @Author Selvy Thomas */
@Log4j2
@Service
public class LoadableStagingService extends StagingService {

  @Autowired private VoyageRepository voyageRepository;

  public LoadableStagingService(@Autowired LoadableStagingRepository loadableStagingRepository) {
    super(loadableStagingRepository);
  }

  /**
   * method for get JsonArray from processIdentifierList
   *
   * @param processIdentifierList - list of processIdentifier
   * @param processId - processId
   * @param processGroupId - processGroupId
   * @param Id- id
   * @return JsonArray
   */
  public JsonArray getJsonArrayFromList(
      List<String> processIdentifierList, String processId, String processGroupId, Long Id) {
    JsonObject jsonObject = null;
    JsonArray array = new JsonArray();
    for (String processIdentifier : processIdentifierList) {
      switch (processIdentifier) {
        case "voyage":
          Voyage voyageGet = voyageRepository.findByIdAndIsActive(Id, true);
          voyageGet.setLoadableStudies(null);
          jsonObject =
              createJsonObject(
                  new Gson().toJson(voyageGet), processIdentifier, processId, processGroupId);
          array.add(jsonObject);
          break;
      }
    }
    return array;
  }

  /**
   * method for create JsonObject
   *
   * @param jsonData - string jsonData
   * @param processIdentifier - processId
   * @param processId - processGroupId
   * @param processGroupId- processGroupId
   * @return JsonObject
   */
  public JsonObject createJsonObject(
      String jsonData, String processIdentifier, String processId, String processGroupId) {
    JsonObject jsonObject = new JsonObject();
    JsonObject metaData = new JsonObject();
    metaData.addProperty("processIdentifier", processIdentifier);
    metaData.addProperty("processId", processId);
    metaData.addProperty("processGroupId", processGroupId);
    jsonObject.add("meta_data", metaData);
    JsonArray array = new JsonArray();
    array.add(jsonData);
    jsonObject.add("data", array);
    return jsonObject;
  }

  public void add(Long id) throws GenericServiceException, JsonProcessingException {
    Optional<DataTransferStage> dataTransferStageOptional = this.getById(id);
    if (!dataTransferStageOptional.isPresent()) {
      throw new GenericServiceException(
          "DataTransferStage with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    DataTransferStage dataTransferStage = dataTransferStageOptional.get();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
    objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    String j = dataTransferStage.getData();
    // String k= j.replace("\\","").replace("[","");
    // String k = j.replaceAll("[\\\\|\\[|\\]]", "").replaceFirst("\"", "");
    switch (dataTransferStage.getProcessIdentifier()) {
      case "voyage":
        Voyage voyage = objectMapper.readValue(j, Voyage.class);
        voyageRepository.save(voyage);
        break;
    }

    // Voyage voyage = new ObjectMapper().readValue(json,Voyage.class);

  }

  public void getTableData(Object serviceClass, String methodName, Long id)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
          IllegalAccessException {
    Method method = serviceClass.getClass().getMethod(methodName, new Class[] {Long.class});
    method.invoke(serviceClass, id);
  }
}
