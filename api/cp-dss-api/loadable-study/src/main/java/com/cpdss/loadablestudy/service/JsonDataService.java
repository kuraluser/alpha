/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.loadablestudy.entity.JsonData;
import com.cpdss.loadablestudy.entity.JsonType;
import com.cpdss.loadablestudy.repository.JsonDataRepository;
import com.cpdss.loadablestudy.repository.JsonTypeRepository;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class JsonDataService {
  @Autowired private JsonDataRepository jsonDataRepository;
  @Autowired private JsonTypeRepository jsonTypeRepository;
  /**
   * @param referenceId
   * @param jsonTypeId
   * @param json
   */
  public void saveJsonToDatabase(Long referenceId, Long jsonTypeId, String json) {
    Optional<JsonType> jsonTypeOpt = jsonTypeRepository.findByIdAndIsActive(jsonTypeId, true);

    if (jsonTypeOpt.isPresent()) {
      JsonData jsonData = new JsonData();
      jsonData.setReferenceXId(referenceId);
      jsonData.setJsonTypeXId(jsonTypeOpt.get());
      jsonData.setJsonData(json);
      jsonData = jsonDataRepository.save(jsonData);
      log.info(String.format("Saved %s JSON in database.", jsonTypeOpt.get().getTypeName()));
      log.info("====" + jsonData.getId() + " ===" + jsonData.getReferenceXId());
    } else {
      log.error("Cannot find JSON type in database.");
    }
  }

  public JsonData getJsonData(Long id, Long typeId) {
    JsonData patternJson = null;
    Optional<JsonType> type = this.jsonTypeRepository.findByIdAndIsActive(typeId, true);
    if (type.isPresent()) {
      patternJson = this.jsonDataRepository.findTopByReferenceXIdAndJsonTypeXIdOrderByIdDesc( id, type.get());
    }
    return patternJson;
  }
}
