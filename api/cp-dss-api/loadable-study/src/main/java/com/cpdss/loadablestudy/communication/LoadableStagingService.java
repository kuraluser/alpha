/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.communication;

import com.cpdss.common.communication.StagingService;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @Author Selvy Thomas */
@Log4j2
@Service
public class LoadableStagingService extends StagingService {

  @Autowired private VoyageRepository voyageRepository;

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

  public void getTableData(Object serviceClass, String methodName, Long id)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
          IllegalAccessException {
    Method method = serviceClass.getClass().getMethod(methodName, new Class[] {Long.class});
    method.invoke(serviceClass, id);
  }
}
