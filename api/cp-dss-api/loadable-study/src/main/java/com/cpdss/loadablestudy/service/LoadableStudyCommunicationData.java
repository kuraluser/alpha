/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.common.communication.StagingService.setEntityDocFields;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.CONFIRMED_STATUS_ID;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID;

import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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

  @Autowired
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @Autowired
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @Autowired private LoadablePlanRepository loadablePlanRepository;
  @Autowired private OnHandQuantityRepository onHandQuantityRepository;
  @Autowired private OnBoardQuantityRepository onBoardQuantityRepository;

  Type listType = null;
  List<LoadablePattern> loadablePatterns = null;
  List<SynopticalTableLoadicatorData> synopticalTableLoadicatorDatas = null;
  List<JsonData> jsonDatas = null;
  List<SynopticalTable> synopticalTables = null;
  List<LoadableStudyPortRotationCommunication> loadableStudyPortRotations = null;
  List<LoadablePlanStowageBallastDetails> loadablePlanStowageBallastDetails = null;
  List<LoadablePlanComminglePortwiseDetails> loadablePlanComminglePortwiseDetails = null;
  List<LoadablePatternCargoDetails> loadablePatternCargoDetails = null;
  List<OnBoardQuantity> onBoardQuantities = null;
  List<OnHandQuantity> onHandQuantities = null;

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
        // Changing confirmed to plan generated status
        List<LoadablePattern> loadablePatternConfirmed =
            loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
                loadableStudy.getVoyage().getId(), CONFIRMED_STATUS_ID, true);
        if (!CollectionUtils.isEmpty(loadablePatternConfirmed)) {
          log.info("changing status of other confirmed plan to plan generated");
          loadablePatternRepository.updateLoadablePatternStatusToPlanGenerated(
              LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadablePatternConfirmed.get(0).getId());
          loadablePatternRepository.updateLoadableStudyStatusToPlanGenerated(
              LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID,
              loadablePatternConfirmed.get(0).getLoadableStudy().getId());
        }
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
      JsonObject communicationRelatedIdMap = new JsonObject();
      final JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (xIds != null) {
        for (String xId : xIds) {
          if (xIds.length == 1) {
            jsonObj.add("communicationRelatedEntityId", jsonObj.get(xId));
          } else {
            if (!"null".equals(jsonObj.get(xId).toString())) {
              communicationRelatedIdMap.addProperty(xId, jsonObj.get(xId).getAsLong());
            }
          }
          jsonObj.remove(xId);
        }
      }
      jsonObj.add("communicationRelatedIdMap", communicationRelatedIdMap);
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

  public void saveLoadablePlanStowageBallastDetails(String dataJson) {
    try {
      HashMap<String, String> map =
          loadableStudyStagingService.getAttributeMapping(new LoadablePlanStowageBallastDetails());
      JsonArray jsonArray =
          removeJsonFields(
              JsonParser.parseString(dataJson).getAsJsonArray(), map, "loadable_plan_xid");
      log.info("LoadablePlanStowageBallastDetails json array:{}", jsonArray);
      listType = new TypeToken<ArrayList<LoadablePlanStowageBallastDetails>>() {}.getType();
      loadablePlanStowageBallastDetails = new Gson().fromJson(jsonArray, listType);
      log.info("LoadablePlanStowageBallastDetails list:{}", loadablePlanStowageBallastDetails);
      if (!CollectionUtils.isEmpty(loadablePlanStowageBallastDetails)) {
        for (LoadablePlanStowageBallastDetails lpStowageBallastDetail :
            loadablePlanStowageBallastDetails) {
          Optional<LoadablePlanStowageBallastDetails> loadablePatternCargoDetailsOptional =
              loadablePlanStowageBallastDetailsRepository.findById(lpStowageBallastDetail.getId());
          setEntityDocFields(lpStowageBallastDetail, loadablePatternCargoDetailsOptional);
          lpStowageBallastDetail.setLoadablePlan(
              getLoadablePlan(lpStowageBallastDetail.getCommunicationRelatedEntityId())
                  .orElse(null));
        }

        loadablePlanStowageBallastDetailsRepository.saveAll(loadablePlanStowageBallastDetails);
        log.info("Saved LoadablePlanStowageBallastDetails:{}", loadablePlanStowageBallastDetails);
      }
    } catch (Exception e) {
      log.error("Error occurred when saving LoadablePlanStowageBallast", e);
    }
  }

  /**
   * Method to get loadable plan from loadable_plan Id
   *
   * @param loadablePlanId loadablePlanId
   * @return LoadablePlan entity
   */
  private Optional<LoadablePlan> getLoadablePlan(Long loadablePlanId) {
    return null != loadablePlanId
        ? loadablePlanRepository.findById(loadablePlanId)
        : Optional.empty();
  }

  public void saveLoadablePlanCommingleDetailsPortwise(String dataJson) {
    try {
      HashMap<String, String> map =
          loadableStudyStagingService.getAttributeMapping(
              new LoadablePlanComminglePortwiseDetails());
      JsonArray jsonArray =
          removeJsonFields(
              JsonParser.parseString(dataJson).getAsJsonArray(), map, "loadable_pattern_xid");
      log.info("LoadablePlanComminglePortwiseDetails json array:{}", jsonArray);
      listType = new TypeToken<ArrayList<LoadablePlanStowageBallastDetails>>() {}.getType();
      loadablePlanComminglePortwiseDetails = new Gson().fromJson(jsonArray, listType);
      log.info(
          "LoadablePlanComminglePortwiseDetails list:{}", loadablePlanComminglePortwiseDetails);
      if (!CollectionUtils.isEmpty(loadablePlanComminglePortwiseDetails)) {
        for (LoadablePlanComminglePortwiseDetails lpComminglePortwiseDetail :
            loadablePlanComminglePortwiseDetails) {
          Optional<LoadablePlanComminglePortwiseDetails> lpComminglePortwiseDetailsOptional =
              loadablePlanCommingleDetailsPortwiseRepository.findById(
                  lpComminglePortwiseDetail.getId());
          setEntityDocFields(lpComminglePortwiseDetail, lpComminglePortwiseDetailsOptional);
          lpComminglePortwiseDetail.setLoadablePattern(
              loadablePatternRepository
                  .findById(lpComminglePortwiseDetail.getCommunicationRelatedEntityId())
                  .orElse(null));
        }
        loadablePlanComminglePortwiseDetails =
            loadablePlanCommingleDetailsPortwiseRepository.saveAll(
                loadablePlanComminglePortwiseDetails);
        log.info(
            "Saved LoadablePlanComminglePortwiseDetails:{}", loadablePlanComminglePortwiseDetails);
      }
    } catch (Exception e) {
      log.error("Error occurred when saving LoadablePlanComminglePortwiseDetails", e);
    }
  }

  public void saveLoadablePatternCargoDetails(String dataJson) {
    try {
      HashMap<String, String> map =
          loadableStudyStagingService.getAttributeMapping(new LoadablePatternCargoDetails());
      JsonArray jsonArray =
          removeJsonFields(JsonParser.parseString(dataJson).getAsJsonArray(), map, null);
      log.info("LoadablePatternCargoDetails json array:{}", jsonArray);
      listType = new TypeToken<ArrayList<LoadablePatternCargoDetails>>() {}.getType();
      loadablePatternCargoDetails = new Gson().fromJson(jsonArray, listType);
      log.info("LoadablePatternCargoDetails list:{}", loadablePatternCargoDetails);
      if (!CollectionUtils.isEmpty(loadablePatternCargoDetails)) {
        for (LoadablePatternCargoDetails lpCargoDetail : loadablePatternCargoDetails) {
          Optional<LoadablePatternCargoDetails> loadablePatternCargoDetailsOptional =
              loadablePatternCargoDetailsRepository.findById(lpCargoDetail.getId());
          setEntityDocFields(lpCargoDetail, loadablePatternCargoDetailsOptional);
        }
        loadablePatternCargoDetailsRepository.saveAll(loadablePatternCargoDetails);
        log.info("Saved LoadablePatternCargoDetails:{}", loadablePatternCargoDetails);
      }
    } catch (Exception e) {
      log.error("Error occurred when saving LoadablePatternCargoDetails", e);
    }
  }

  public void saveOnBoardQuantity(String dataJson) {
    try {
      HashMap<String, String> map =
          loadableStudyStagingService.getAttributeMapping(new OnBoardQuantity());
      JsonArray jsonArray =
          removeJsonFields(
              JsonParser.parseString(dataJson).getAsJsonArray(), map, "loadable_study_xid");
      log.info("OnBoardQuantity json array:{}", jsonArray);
      listType = new TypeToken<ArrayList<OnBoardQuantity>>() {}.getType();
      onBoardQuantities = new Gson().fromJson(jsonArray, listType);
      log.info("OnBoardQuantity list:{}", onBoardQuantities);
      if (!CollectionUtils.isEmpty(onBoardQuantities)) {
        for (OnBoardQuantity obqStage : onBoardQuantities) {
          Optional<OnBoardQuantity> obqOpt = onBoardQuantityRepository.findById(obqStage.getId());
          setEntityDocFields(obqStage, obqOpt);
          obqStage.setLoadableStudy(
              loadableStudyRepository
                  .findById(obqStage.getCommunicationRelatedIdMap().get("loadable_study_xid"))
                  .orElse(null));
        }
        // Save data
        onBoardQuantities = onBoardQuantityRepository.saveAll(onBoardQuantities);
        log.info("Saved OnBoardQuantity:{}", onBoardQuantities);
      }
    } catch (Exception e) {
      log.error("Error occurred when saving OnBoardQuantity", e);
    }
  }

  public void saveOnHandQuantity(String dataJson) {
    try {
      HashMap<String, String> map =
          loadableStudyStagingService.getAttributeMapping(new OnHandQuantity());
      JsonArray jsonArray =
          removeJsonFields(
              JsonParser.parseString(dataJson).getAsJsonArray(),
              map,
              "loadable_study_xid",
              "port_rotation_xid");
      log.info("OnHandQuantity json array:{}", jsonArray);
      listType = new TypeToken<ArrayList<OnHandQuantity>>() {}.getType();
      onHandQuantities = new Gson().fromJson(jsonArray, listType);
      log.info("OnHandQuantity list:{}", onHandQuantities);
      if (!CollectionUtils.isEmpty(onHandQuantities)) {
        for (OnHandQuantity ohqStage : onHandQuantities) {
          Optional<OnHandQuantity> ohq = onHandQuantityRepository.findById(ohqStage.getId());
          setEntityDocFields(ohqStage, ohq);
          ohqStage.setLoadableStudy(
              loadableStudyRepository
                  .findById(ohqStage.getCommunicationRelatedIdMap().get("loadable_study_xid"))
                  .orElse(null));
          ohqStage.setPortRotation(
              loadableStudyPortRotationRepository
                  .findById(ohqStage.getCommunicationRelatedIdMap().get("port_rotation_xid"))
                  .orElse(null));
        }
        // Save data
        onHandQuantities = onHandQuantityRepository.saveAll(onHandQuantities);
        log.info("Saved OnHandQuantity:{}", onHandQuantities);
      }
    } catch (Exception e) {
      log.error("Error occurred when saving OnBoardQuantity", e);
    }
  }
}
