/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.entity.SynopticalTableLoadicatorData;
import com.cpdss.loadablestudy.repository.LoadablePatternRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.SynopticalTableLoadicatorDataRepository;
import com.cpdss.loadablestudy.repository.SynopticalTableRepository;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Log4j2
@Service
@Transactional
public class LoadableStudyCommunicationData {
  @Autowired private LoadableStudyStagingService loadableStudyStagingService;
  @Autowired private LoadableStudyRepository loadableStudyRepository;
  @Autowired private LoadablePatternRepository loadablePatternRepository;

  @Autowired
  private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  @Autowired private SynopticalTableRepository synopticalTableRepository;

  Type listType = null;
  List<LoadablePattern> loadablePatterns = null;
  List<SynopticalTableLoadicatorData> synopticalTableLoadicatorDatas = null;

  public void saveLoadablePattern(String dataJson) throws ResourceAccessException, Exception {
    HashMap<String, String> map =
        loadableStudyStagingService.getAttributeMapping(new LoadablePattern());
    JsonArray jsonArray =
        removeJsonFields(
            JsonParser.parseString(dataJson).getAsJsonArray(), map, "loadablestudy_xid");
    listType = new TypeToken<ArrayList<LoadablePattern>>() {}.getType();
    loadablePatterns = new Gson().fromJson(jsonArray, listType);
    if (loadablePatterns != null && !loadablePatterns.isEmpty()) {
      for (LoadablePattern loadablePattern : loadablePatterns) {
        Optional<LoadableStudy> loadableStudy =
            loadableStudyRepository.findById(loadablePattern.getCommunicationRelatedEntityId());
        if (loadableStudy.isPresent()) {
          loadablePattern.setLoadableStudy(loadableStudy.get());
          Optional<LoadablePattern> loadablePatternOpt =
              loadablePatternRepository.findById(loadablePattern.getId());
          loadablePattern.setVersion(
              loadablePatternOpt.isPresent() ? loadablePatternOpt.get().getVersion() : null);
        }
      }
      loadablePatternRepository.saveAll(loadablePatterns);
    }
  }

  public void saveSynopticalTableLoadicatorData(String dataJson)
      throws ResourceAccessException, Exception {
    HashMap<String, String> map =
        loadableStudyStagingService.getAttributeMapping(new SynopticalTableLoadicatorData());
    JsonArray jsonArray =
        removeJsonFields(
            JsonParser.parseString(dataJson).getAsJsonArray(), map, "synoptical_table_xid");
    listType = new TypeToken<ArrayList<LoadablePattern>>() {}.getType();
    synopticalTableLoadicatorDatas = new Gson().fromJson(jsonArray, listType);
    if (synopticalTableLoadicatorDatas != null && !synopticalTableLoadicatorDatas.isEmpty()) {
      for (SynopticalTableLoadicatorData synopticalTableLoadicatorData :
          synopticalTableLoadicatorDatas) {
        Optional<SynopticalTable> synopticalTable =
            synopticalTableRepository.findById(
                synopticalTableLoadicatorData.getCommunicationRelatedEntityId());
        if (synopticalTable.isPresent()) {
          synopticalTableLoadicatorData.setSynopticalTable(synopticalTable.get());
          Optional<SynopticalTableLoadicatorData> synopticalTableLoadicatorDataOpt =
              synopticalTableLoadicatorDataRepository.findById(
                  synopticalTableLoadicatorData.getId());
          synopticalTableLoadicatorData.setVersion(
              synopticalTableLoadicatorDataOpt.isPresent()
                  ? synopticalTableLoadicatorDataOpt.get().getVersion()
                  : null);
        }
      }
      synopticalTableLoadicatorDataRepository.saveAll(synopticalTableLoadicatorDatas);
    }
  }

  private JsonArray removeJsonFields(JsonArray array, HashMap<String, String> map, String... xIds) {
    JsonArray json = loadableStudyStagingService.getAsEntityJson(map, array);
    JsonArray jsonArray = new JsonArray();
    for (JsonElement jsonElement : json) {
      final JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (xIds != null) {
        for (String xId : xIds) {
          if (xIds.length == 1) {
            jsonObj.add("communicationRelatedEntityId", jsonObj.get(xId));
          }
          jsonObj.remove(xId);
        }
      }
      jsonArray.add(jsonObj);
    }
    return jsonArray;
  }
}
