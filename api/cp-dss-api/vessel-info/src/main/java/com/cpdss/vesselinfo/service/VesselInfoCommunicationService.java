/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import static com.cpdss.common.communication.StagingService.isValidStageEntity;
import static com.cpdss.common.communication.StagingService.logSavedEntity;
import static com.cpdss.common.communication.StagingService.setEntityDocFields;

import com.cpdss.vesselinfo.communication.VesselInfoStagingService;
import com.cpdss.vesselinfo.constants.VesselInfoConstants.VESSEL_INFO_COLUMNS;
import com.cpdss.vesselinfo.constants.VesselInfoConstants.VESSEL_INFO_TABLES;
import com.cpdss.vesselinfo.entity.RuleVesselMapping;
import com.cpdss.vesselinfo.entity.RuleVesselMappingInput;
import com.cpdss.vesselinfo.repository.RuleTemplateRepository;
import com.cpdss.vesselinfo.repository.RuleTypeRepository;
import com.cpdss.vesselinfo.repository.RuleVesselMappingInputRespository;
import com.cpdss.vesselinfo.repository.RuleVesselMappingRepository;
import com.cpdss.vesselinfo.repository.VesselRepository;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class VesselInfoCommunicationService {

  // region Autowired

  @Autowired private VesselInfoStagingService stagingService;
  @Autowired private RuleVesselMappingRepository ruleVesselMappingRepository;
  @Autowired private RuleTemplateRepository ruleTemplateRepository;
  @Autowired private VesselRepository vesselRepository;
  @Autowired private RuleTypeRepository ruleTypeRepository;
  @Autowired private RuleVesselMappingInputRespository ruleVesselMappingInputRespository;

  // endregion

  // region Declaration

  private HashMap<String, Long> idMap = new HashMap<>();

  // endregion

  // region Save Methods

  /**
   * Method to save rule_vessel_mapping table
   *
   * @param jsonData JSON string of array of records to be saved
   */
  public void saveRuleVesselMapping(final String jsonData) {

    // Convert object
    final Type type = new TypeToken<ArrayList<RuleVesselMapping>>() {}.getType();
    List<RuleVesselMapping> ruleVesselMappingList =
        bindDataToEntity(
            new RuleVesselMapping(),
            type,
            VESSEL_INFO_TABLES.RULE_VESSEL_MAPPING,
            jsonData,
            null,
            VESSEL_INFO_COLUMNS.RULE_TEMPLATE_XID.getColumnName(),
            VESSEL_INFO_COLUMNS.VESSEL_XID.getColumnName(),
            VESSEL_INFO_COLUMNS.RULETYPE_XID.getColumnName());

    if (isValidStageEntity(
        ruleVesselMappingList, VESSEL_INFO_TABLES.RULE_VESSEL_MAPPING.getTableName())) {
      Objects.requireNonNull(ruleVesselMappingList)
          .forEach(
              ruleVesselMapping -> {
                Optional<RuleVesselMapping> ruleVesselMappingOpt =
                    ruleVesselMappingRepository.findById(ruleVesselMapping.getId());
                setEntityDocFields(ruleVesselMapping, ruleVesselMappingOpt);

                // Set foreign keys
                ruleVesselMapping.setRuleTemplate(
                    ruleTemplateRepository
                        .findById(
                            ruleVesselMapping
                                .getCommunicationRelatedIdMap()
                                .get(VESSEL_INFO_COLUMNS.RULE_TEMPLATE_XID.getColumnName()))
                        .orElse(null));
                ruleVesselMapping.setVessel(
                    vesselRepository
                        .findById(
                            ruleVesselMapping
                                .getCommunicationRelatedIdMap()
                                .get(VESSEL_INFO_COLUMNS.VESSEL_XID.getColumnName()))
                        .orElse(null));
                ruleVesselMapping.setRuleType(
                    ruleTypeRepository
                        .findById(
                            ruleVesselMapping
                                .getCommunicationRelatedIdMap()
                                .get(VESSEL_INFO_COLUMNS.RULETYPE_XID.getColumnName()))
                        .orElse(null));
              });

      // Save data
      ruleVesselMappingList = ruleVesselMappingRepository.saveAll(ruleVesselMappingList);
      logSavedEntity(ruleVesselMappingList);
    }
  }

  /**
   * Method to save rule_vessel_mapping_input table
   *
   * @param jsonData JSON string of array of records to be saved
   */
  public void saveRuleVesselMappingInput(String jsonData) {

    // Convert object
    final Type type = new TypeToken<ArrayList<RuleVesselMappingInput>>() {}.getType();
    List<RuleVesselMappingInput> ruleVesselMappingInputList =
        bindDataToEntity(
            new RuleVesselMappingInput(),
            type,
            VESSEL_INFO_TABLES.RULE_VESSEL_MAPPING_INPUT,
            jsonData,
            null,
            VESSEL_INFO_COLUMNS.RULE_VESSEL_MAPPING_XID.getColumnName());

    if (isValidStageEntity(
        ruleVesselMappingInputList, VESSEL_INFO_TABLES.RULE_VESSEL_MAPPING_INPUT.getTableName())) {
      Objects.requireNonNull(ruleVesselMappingInputList)
          .forEach(
              ruleVesselMappingInput -> {
                Optional<RuleVesselMappingInput> ruleVesselMappingInputOpt =
                    ruleVesselMappingInputRespository.findById(ruleVesselMappingInput.getId());
                setEntityDocFields(ruleVesselMappingInput, ruleVesselMappingInputOpt);

                // Set foreign keys
                ruleVesselMappingInput.setRuleVesselMapping(
                    ruleVesselMappingRepository
                        .findById(
                            ruleVesselMappingInput
                                .getCommunicationRelatedIdMap()
                                .get(VESSEL_INFO_COLUMNS.RULE_VESSEL_MAPPING_XID.getColumnName()))
                        .orElse(null));
              });

      // Save data
      ruleVesselMappingInputList =
          ruleVesselMappingInputRespository.saveAll(ruleVesselMappingInputList);
      logSavedEntity(ruleVesselMappingInputList);
    }
  }

  // endregion

  // region Data Binding

  /**
   * Method to convert JSON string to entity
   *
   * @param entity entity object
   * @param listType type value
   * @param table table name
   * @param jsonData JSON string to be converted
   * @param dataTransferStageId stage id
   * @param columnsToRemove columns to be removed on conversion
   * @param <T> generic type
   * @return converted entity object
   */
  private <T> T bindDataToEntity(
      Object entity,
      Type listType,
      VESSEL_INFO_TABLES table,
      String jsonData,
      Long dataTransferStageId,
      String... columnsToRemove) {
    try {
      HashMap<String, String> map = stagingService.getAttributeMapping(entity);
      JsonArray jsonArray =
          removeJsonFields(JsonParser.parseString(jsonData).getAsJsonArray(), map, columnsToRemove);
      idMap.put(table.getTableName(), dataTransferStageId);

      if (listType.getTypeName().startsWith(ArrayList.class.getTypeName())) {
        return new Gson().fromJson(jsonArray, listType);
      }
      return new Gson().fromJson(jsonArray.get(0).getAsJsonObject(), listType);
    } catch (Exception e) {
      log.error(
          "Communication XXXXXXX Unable to bind the Json to object : {} :::",
          entity.getClass().getName(),
          e);
    }
    return null;
  }

  /**
   * Method to remove JSON fields
   *
   * @param array JSON array object
   * @param map attribute mapping
   * @param xIds fields to be removed
   * @return JSON array after removing specified fields
   */
  private JsonArray removeJsonFields(JsonArray array, HashMap<String, String> map, String... xIds) {
    List<String> xIdList = xIds == null ? null : List.of(xIds);
    return removeJsonFields(array, map, xIdList);
  }

  /**
   * Method to remove JSON fields
   *
   * @param array JSON array object
   * @param map attribute mapping
   * @param xIds fields to be removed
   * @return JSON array after removing specified fields
   */
  private JsonArray removeJsonFields(
      JsonArray array, HashMap<String, String> map, List<String> xIds) {
    JsonArray json = stagingService.getAsEntityJson(map, array);
    JsonArray jsonArray = new JsonArray();

    for (JsonElement jsonElement : json) {
      JsonObject communicationRelatedIdMap = new JsonObject();
      final JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (xIds != null) {
        for (String xId : xIds) {
          jsonObj.add("communicationRelatedEntityId", jsonObj.get(xId));
          if (!"null".equals(jsonObj.get(xId).toString())) {
            communicationRelatedIdMap.addProperty(xId, jsonObj.get(xId).getAsLong());
          }
          jsonObj.remove(xId);
        }
      }
      jsonObj.add("communicationRelatedIdMap", communicationRelatedIdMap);
      jsonArray.add(jsonObj);
    }
    return jsonArray;
  }

  // endregion
}
