/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.common.communication.StagingService.setEntityDocFields;

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
  @Autowired private CargoOperationRepository cargoOperationRepository;

  @Autowired
  private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  @Autowired private SynopticalTableRepository synopticalTableRepository;
  @Autowired private JsonDataRepository jsonDataRepository;
  @Autowired private JsonTypeRepository jsonTypeRepository;
  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired
  private LoadableStudyPortRotationCommuncationRepository
      loadableStudyPortRotationCommuncationRepository;

  Type listType = null;
  List<LoadablePattern> loadablePatterns = null;
  List<SynopticalTableLoadicatorData> synopticalTableLoadicatorDatas = null;
  List<JsonData> jsonDatas = null;
  List<SynopticalTable> synopticalTables = null;
  List<LoadableStudyPortRotationCommunication> loadableStudyPortRotations = null;

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
      try {
        for (LoadablePattern loadablePattern : loadablePatterns) {
          log.info(
              "loadableStudy id from loadablePattern staging id:{}",
              loadablePattern.getCommunicationRelatedEntityId());
          LoadableStudy ls =
              updateLoadableStudyStatus(loadablePattern.getCommunicationRelatedEntityId());
          if (ls != null) {
            try {
              loadablePattern.setLoadableStudyStatus(2L);
              loadablePattern.setLoadableStudy(ls);
              log.info("LoadablePattern id to fetch:{}", loadablePattern.getId());
              Optional<LoadablePattern> loadablePatternOpt =
                  loadablePatternRepository.findById(loadablePattern.getId());
              log.info("LoadablePattern get:{}", loadablePatternOpt);
              setEntityDocFields(loadablePattern, loadablePatternOpt);
              log.info("Saved LoadablePattern:{}", loadablePattern);
              loadablePatternRepository.save(loadablePattern);
            } catch (Exception e) {
              log.error("error when updating LoadablePattern:{}", e);
            }
          }
        }
      } catch (Exception e) {
        log.error("error when updating:{}", e);
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
        Optional<LoadableStudyStatus> loadableStudyStatus =
            loadableStudyStatusRepository.findById(2L);
        if (loadableStudyStatus.isPresent()) {
          log.info("LoadableStudyStatus get:{}", loadableStudyStatus.get());
          loadableStudy.setLoadableStudyStatus(loadableStudyStatus.get());
          ls = loadableStudyRepository.save(loadableStudy);
        }
      } catch (Exception e) {
        log.error("error when updating LoadableStudy statys:{}", e);
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
          setEntityDocFields(synopticalTableLoadicatorData, synopticalTableLoadicatorDataOpt);
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

  /**
   * save json Data for loadingplan/dischargeplan communication
   *
   * @param dataJson
   */
  public void saveJsonDataForCommunication(String dataJson) {
    try {
      HashMap<String, String> map = loadableStudyStagingService.getAttributeMapping(new JsonData());
      JsonArray jsonArray =
          removeJsonFields(JsonParser.parseString(dataJson).getAsJsonArray(), map, "json_type_xid");
      log.info("JsonData json array:{}", jsonArray);
      listType = new TypeToken<ArrayList<JsonData>>() {}.getType();
      jsonDatas = new Gson().fromJson(jsonArray, listType);
      log.info("jsonData list:{}", jsonDatas);
      if (jsonDatas != null && !jsonDatas.isEmpty()) {
        for (JsonData jsonData : jsonDatas) {
          log.info("JsonType id :{}", jsonData.getCommunicationRelatedEntityId());
          Optional<JsonType> jsonType =
              jsonTypeRepository.findById(jsonData.getCommunicationRelatedEntityId());
          log.info("jsonType:{}", jsonType);
          if (jsonType.isPresent()) {
            Optional<JsonData> jsonDataOpt = jsonDataRepository.findById(jsonData.getId());
            log.info("jsonDataOpt get:{}", jsonDataOpt);
            setEntityDocFields(jsonData, jsonDataOpt);
            jsonData.setJsonTypeXId(jsonType.get());
          }
        }
        jsonDataRepository.saveAll(jsonDatas);
        log.info("Saved JsonData:{}", jsonDatas);
      }
    } catch (Exception e) {
      log.error(
          "Error occurred when saving json data part of loadingplan/dischargeplan communication",
          e);
    }
  }

  public void saveSynopticalTableData(String dataJson) {
    try {
      HashMap<String, String> map =
          loadableStudyStagingService.getAttributeMapping(new SynopticalTable());
      JsonArray jsonArray =
          removeJsonFields(
              JsonParser.parseString(dataJson).getAsJsonArray(), map, "port_rotation_xid");
      log.info("SynopticalTable json array:{}", jsonArray);
      listType = new TypeToken<ArrayList<SynopticalTable>>() {}.getType();
      synopticalTables = new Gson().fromJson(jsonArray, listType);
      log.info("SynopticalTable list:{}", synopticalTables);
      if (synopticalTables != null && !synopticalTables.isEmpty()) {
        for (SynopticalTable synopticalTable : synopticalTables) {
          log.info(
              "LoadableStudyPortRotation id :{}",
              synopticalTable.getCommunicationRelatedEntityId());
          Optional<LoadableStudyPortRotation> loadableStudyPortRotation =
              loadableStudyPortRotationRepository.findById(
                  synopticalTable.getCommunicationRelatedEntityId());
          if (loadableStudyPortRotation.isPresent()) {
            Optional<SynopticalTable> synopticalTableOpt =
                synopticalTableRepository.findById(synopticalTable.getId());
            log.info("SynopticalTable get by id:{}", synopticalTableOpt);
            setEntityDocFields(synopticalTable, synopticalTableOpt);
            synopticalTable.setLoadableStudyPortRotation(loadableStudyPortRotation.get());
          }
        }
        synopticalTableRepository.saveAll(synopticalTables);
        log.info("Saved SynopticalTable:{}", synopticalTables);
      }
    } catch (Exception e) {
      log.error("Error occurred when saving SynopticalTable part of loadingplan communication", e);
    }
  }

  public void saveLoadableStudyPortRotationData(String dataJson) {
    try {
      HashMap<String, String> map =
          loadableStudyStagingService.getAttributeMapping(
              new LoadableStudyPortRotationCommunication());
      JsonArray jsonArray =
          removeJsonFieldsForLoadableStudyPortRotation(
              JsonParser.parseString(dataJson).getAsJsonArray(),
              map,
              "loadable_study_xid",
              "operation_xid");
      log.info("LoadableStudyPortRotation json array:{}", jsonArray);
      listType = new TypeToken<ArrayList<LoadableStudyPortRotationCommunication>>() {}.getType();
      loadableStudyPortRotations = new Gson().fromJson(jsonArray, listType);
      log.info("LoadableStudyPortRotation list:{}", loadableStudyPortRotations);
      for (LoadableStudyPortRotationCommunication loadableStudyPortRotation :
          loadableStudyPortRotations) {
        Long loadableStudyIdForPort = loadableStudyPortRotation.getCommunicationLoadbleStudyId();
        Long cargoOperationIdForPort = loadableStudyPortRotation.getCommunicationRelatedEntityId();
        log.info(
            "LoadableStudy id: {} and CargoOperation id: {} ",
            loadableStudyIdForPort,
            cargoOperationIdForPort);
        if (loadableStudyIdForPort != null && cargoOperationIdForPort != null) {
          Optional<LoadableStudy> loadableStudy =
              loadableStudyRepository.findById(loadableStudyIdForPort);
          Optional<CargoOperation> cargoOperation =
              cargoOperationRepository.findById(cargoOperationIdForPort);
          if (loadableStudy.isPresent() && cargoOperation.isPresent()) {
            Optional<LoadableStudyPortRotationCommunication> loadableStudyPortRotationOpt =
                loadableStudyPortRotationCommuncationRepository.findById(
                    loadableStudyPortRotation.getId());
            log.info(
                "LoadableStudyPortRotation get by id:{}",
                loadableStudyPortRotationOpt.get().getId());
            setEntityDocFields(loadableStudyPortRotation, loadableStudyPortRotationOpt);
            loadableStudyPortRotation.setLoadableStudy(loadableStudy.get());
            loadableStudyPortRotation.setOperation(cargoOperation.get());
          }
        }
      }
      loadableStudyPortRotationCommuncationRepository.saveAll(loadableStudyPortRotations);
      log.info("Saved LoadableStudyPortRotation:{}", loadableStudyPortRotations);
    } catch (Exception e) {
      log.error(
          "Error occurred when saving LoadableStudyPortRotation as part of loadingplan communication",
          e);
    }
  }

  private JsonArray removeJsonFieldsForLoadableStudyPortRotation(
      JsonArray array, HashMap<String, String> map, String... xIds) {
    JsonArray json = loadableStudyStagingService.getAsEntityJson(map, array);
    JsonArray jsonArray = new JsonArray();
    for (JsonElement jsonElement : json) {
      final JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (xIds != null) {
        for (String xId : xIds) {
          if (xId.equals("loadable_study_xid")) {
            jsonObj.add("communicationLoadbleStudyId", jsonObj.get(xId));
          } else if (xId.equals("operation_xid")) {
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
