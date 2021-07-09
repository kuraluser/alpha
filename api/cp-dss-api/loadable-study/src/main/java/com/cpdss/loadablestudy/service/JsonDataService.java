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
      jsonDataRepository.save(jsonData);
      log.info(String.format("Saved %s JSON in database.", jsonTypeOpt.get().getTypeName()));
    } else {
      log.error("Cannot find JSON type in database.");
    }
  }

  public Optional<JsonData> getJsonData(Long id, Long resultId) {
    Optional<JsonData> patternJson =
        this.jsonDataRepository.findByJsonTypeXIdAndReferenceXId(resultId, id);
    return patternJson;
  }
}
