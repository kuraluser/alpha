/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyStatus;
import com.cpdss.loadablestudy.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      LoadableStudyCommunicationData.class,
    })
public class LoadableStudyCommunicationDataTest {
  @Autowired LoadableStudyCommunicationData loadableStudyCommunicationData;

  @MockBean private LoadableStudyStagingService loadableStudyStagingService;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private LoadableStudyStatusRepository loadableStudyStatusRepository;
  @MockBean private CargoOperationRepository cargoOperationRepository;

  @MockBean private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @MockBean private JsonDataRepository jsonDataRepository;
  @MockBean private JsonTypeRepository jsonTypeRepository;
  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @MockBean
  private LoadableStudyPortRotationCommuncationRepository
      loadableStudyPortRotationCommuncationRepository;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean private LoadablePlanRepository loadablePlanRepository;
  @MockBean private OnHandQuantityRepository onHandQuantityRepository;
  @MockBean private OnBoardQuantityRepository onBoardQuantityRepository;

  // not completed
  @Disabled
  @Test
  void testSaveLoadablePattern() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String str = mapper.writeValueAsString(Arrays.asList(getLoadablePattern()));
    JsonArray jsonArray = new JsonArray();
    JsonElement element = new Gson().toJsonTree(getLoadablePattern());
    jsonArray.add(element);

    when(loadableStudyStagingService.getAttributeMapping(any(LoadablePattern.class)))
        .thenReturn(new HashMap<String, String>());
    when(loadablePatternRepository.findById(anyLong()))
        .thenReturn(Optional.of(getLoadablePattern()));
    when(loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(getLoadableStudy()));
    when(loadableStudyStatusRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadableStudyStatus()));
    when(loadableStudyStagingService.getAsEntityJson(any(HashMap.class), any(JsonArray.class)))
        .thenReturn(jsonArray);

    loadableStudyCommunicationData.saveLoadablePattern(str);
    //        verify(loadablePatternRepository).save(any(LoadablePattern.class));
    //        verify(loadableStudyRepository).save(any(LoadableStudy.class));
  }

  private LoadablePattern getLoadablePattern() {
    LoadablePattern loadablePattern = new LoadablePattern();
    // loadablePattern.setCommunicationRelatedEntityId(1l);
    loadablePattern.setLoadableStudyStatus(1l);
    loadablePattern.setVersion(1l);
    loadablePattern.setLoadableStudy(getLoadableStudy());
    return loadablePattern;
  }

  private LoadableStudy getLoadableStudy() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1l);
    return loadableStudy;
  }
}
