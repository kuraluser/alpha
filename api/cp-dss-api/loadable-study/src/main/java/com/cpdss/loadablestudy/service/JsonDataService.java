/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadablestudy.domain.LoadableStudyAlgoJson;
import com.cpdss.loadablestudy.entity.JsonData;
import com.cpdss.loadablestudy.entity.JsonType;
import com.cpdss.loadablestudy.repository.JsonDataRepository;
import com.cpdss.loadablestudy.repository.JsonTypeRepository;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.FAILED;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;

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

  public Optional<JsonData> getJsonData(Long id, Long typeId) {
    Optional<JsonData> patternJson = Optional.empty();
    Optional<JsonType> type = this.jsonTypeRepository.findByIdAndIsActive(typeId, true);
    System.out.println(type.isPresent());
    System.out.println(id);
    if(type.isPresent()) {
      patternJson =
              this.jsonDataRepository.findByJsonTypeXIdAndReferenceXId(type.get(), id);
    }
    return patternJson;
  }

}
