/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cpdss.loadablestudy.entity.JsonData;
import com.cpdss.loadablestudy.entity.JsonType;
import com.cpdss.loadablestudy.repository.JsonDataRepository;
import com.cpdss.loadablestudy.repository.JsonTypeRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      JsonDataService.class,
    })
public class JsonDataServiceTest {
  @Autowired JsonDataService jsonDataService;

  @MockBean private JsonDataRepository jsonDataRepository;
  @MockBean private JsonTypeRepository jsonTypeRepository;

  @Test
  void testSaveJsonToDatabase() {
    JsonType jsonType = new JsonType();
    jsonType.setTypeName("1");
    JsonData jsonData = new JsonData();
    jsonData.setId(1l);
    jsonData.setReferenceXId(1l);
    when(jsonTypeRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(jsonType));
    when(jsonDataRepository.save(any(JsonData.class))).thenReturn(jsonData);
    jsonDataService.saveJsonToDatabase(1l, 1l, "1");
    verify(jsonDataRepository).save(any(JsonData.class));
  }

  @Test
  void testGetJsonData() {
    JsonType jsonType = new JsonType();
    jsonType.setTypeName("1");
    JsonData jsonData = new JsonData();
    jsonData.setId(1l);
    when(this.jsonTypeRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(jsonType));
    when(this.jsonDataRepository.findTopByReferenceXIdAndJsonTypeXIdOrderByIdDesc(
            anyLong(), any(JsonType.class)))
        .thenReturn(jsonData);
    var result = jsonDataService.getJsonData(1l, 1l);
    assertEquals(1l, result.getId());
  }
}
