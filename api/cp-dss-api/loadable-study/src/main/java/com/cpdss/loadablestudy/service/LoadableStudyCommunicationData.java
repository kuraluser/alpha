/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import io.grpc.LoadBalancer;
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
  @Autowired private LoadableStudyStatusRepository loadableStudyStatusRepository;
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
    log.info("saveLoadablePattern json array:{}", jsonArray);
    listType = new TypeToken<ArrayList<LoadablePattern>>() {}.getType();
    loadablePatterns = new Gson().fromJson(jsonArray, listType);
    log.info("loadablePatterns list:{}", loadablePatterns);
    if (loadablePatterns != null && !loadablePatterns.isEmpty()) {
     try{
      for (LoadablePattern loadablePattern : loadablePatterns) {
        log.info(
                "loadableStudy id from loadablePattern staging id:{}",
                loadablePattern.getCommunicationRelatedEntityId());
        LoadableStudy ls = updateLoadableStudyStatus(loadablePattern.getCommunicationRelatedEntityId());
        if (ls != null) {
          try {
            loadablePattern.setLoadableStudyStatus(2L);
            loadablePattern.setLoadableStudy(ls);
            log.info("LoadablePattern id to fetch:{}", loadablePattern.getId());
            Optional<LoadablePattern> loadablePatternOpt =
                    loadablePatternRepository.findById(loadablePattern.getId());
            log.info("LoadablePattern get:{}", loadablePatternOpt);
            loadablePattern.setVersion(
                    loadablePatternOpt.isPresent() ? loadablePatternOpt.get().getVersion() : null);
            log.info("Saved LoadablePattern:{}", loadablePattern);
            loadablePatternRepository.save(loadablePattern);
          }catch(Exception e){
            log.error("error when updating LoadablePattern:{}",e);
          }
        }
      }
      }catch(Exception e){
        log.error("error when updating:{}",e);
     }
    }
  }

  private LoadableStudy updateLoadableStudyStatus(Long communicationRelatedEntityId) {
    LoadableStudy ls = null;
    Optional<LoadableStudy> loadableStudyOpt =
            loadableStudyRepository.findById(communicationRelatedEntityId);
    log.info("loadableStudy get:{}", loadableStudyOpt);
    if (loadableStudyOpt.isPresent()) {
      try {
        LoadableStudy loadableStudy = loadableStudyOpt.get();
        Optional<LoadableStudyStatus> loadableStudyStatus = loadableStudyStatusRepository.findById(2L);
        if (loadableStudyStatus.isPresent()) {
          log.info("LoadableStudyStatus get:{}", loadableStudyStatus.get());
          loadableStudy.setLoadableStudyStatus(loadableStudyStatus.get());
          ls = loadableStudyRepository.save(loadableStudy);
        }
      }catch (Exception e){
        log.error("error when updating LoadableStudy statys:{}",e);
      }
    }
    return ls;
  }


  public void saveSynopticalTableLoadicatorData(String dataJson)
      throws ResourceAccessException, Exception {
    HashMap<String, String> map =
        loadableStudyStagingService.getAttributeMapping(new SynopticalTableLoadicatorData());
    JsonArray jsonArray =
        removeJsonFields(
            JsonParser.parseString(dataJson).getAsJsonArray(), map, "synoptical_table_xid");
    log.info("saveSynopticalTableLoadicatorData json array:{}", jsonArray);
    listType = new TypeToken<ArrayList<SynopticalTableLoadicatorData>>() {}.getType();
    synopticalTableLoadicatorDatas = new Gson().fromJson(jsonArray, listType);
    log.info("synopticalTableLoadicatorDatas list:{}", synopticalTableLoadicatorDatas);
    if (synopticalTableLoadicatorDatas != null && !synopticalTableLoadicatorDatas.isEmpty()) {
      for (SynopticalTableLoadicatorData synopticalTableLoadicatorData :
          synopticalTableLoadicatorDatas) {
        log.info(
            "synopticalTable id from synopticalTableLoadicatorDatas staging id:{}",
            synopticalTableLoadicatorData.getCommunicationRelatedEntityId());
        Optional<SynopticalTable> synopticalTable =
            synopticalTableRepository.findById(
                synopticalTableLoadicatorData.getCommunicationRelatedEntityId());
        log.info("synopticalTable:{}", synopticalTable);
        if (synopticalTable.isPresent()) {
          synopticalTableLoadicatorData.setSynopticalTable(synopticalTable.get());
          Optional<SynopticalTableLoadicatorData> synopticalTableLoadicatorDataOpt =
              synopticalTableLoadicatorDataRepository.findById(
                  synopticalTableLoadicatorData.getId());
          log.info("SynopticalTableLoadicatorData get:{}", synopticalTableLoadicatorDataOpt);
          synopticalTableLoadicatorData.setVersion(
              synopticalTableLoadicatorDataOpt.isPresent()
                  ? synopticalTableLoadicatorDataOpt.get().getVersion()
                  : null);
        }
      }
      log.info("Saved SynopticalTableLoadicatorDatas:{}", synopticalTableLoadicatorDatas);
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
