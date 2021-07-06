/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.domain.VesselDetails;
import com.cpdss.vesselinfo.domain.VesselInfo;
import com.cpdss.vesselinfo.domain.VesselRule;
import com.cpdss.vesselinfo.entity.Vessel;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Vessel repository
 *
 * @author suhail.k
 */
public interface VesselRepository extends CommonCrudRepository<Vessel, Long> {

  public List<Vessel> findByIsActive(boolean isActive);

  public Vessel findByIdAndIsActive(Long vesselId, boolean isActive);

  @Query(
      "SELECT new com.cpdss.vesselinfo.domain.VesselDetails"
          + "(VDC.displacement, V.lightweight, VDC.deadweight, VDC.draftCondition.name, V.deadweightConstant) "
          + "from Vessel V "
          + "LEFT JOIN VesselDraftCondition VDC on V.id = VDC.vessel.id "
          + "WHERE V.id = ?1 AND VDC.draftCondition.id = ?2 AND VDC.draftExtreme = ?3")
  public VesselDetails findVesselDetailsById(
      Long id, Long vesselDraftConditionId, BigDecimal draftExtreme);

  @Query(
      "SELECT new com.cpdss.vesselinfo.domain.VesselInfo"
          + "(V.id, V.name, V.imoNumber,V.typeOfShip,V.code,V.deadweightConstant,V.provisionalConstant, V.maxLoadRate) "
          + "from Vessel V "
          + "WHERE V.id = ?1 AND V.isActive = ?2")
  public VesselInfo findVesselDetailsByVesselId(Long id, Boolean isActive);

  String FIND_ID_AND_NAME = "select vs.id, vs.name from vessel vs";

  @Query(value = FIND_ID_AND_NAME, nativeQuery = true)
  List<Object[]> findVesselIdAndNames();

  @Query(
      "SELECT new com.cpdss.vesselinfo.domain.VesselRule(ruleListMaster.ruleListMaster as header, "
          + " ruleTemplate.id as id, ruleTemplate.id as templateId, ruleTemplate.isEnable as templateIsEnable, "
          + " ruleTemplate.displayInSettings as templateDisplayInSettings, ruleTemplate.ruleType.ruleType as templateRuleType,"
          + " ruleTemplateInput.id as templateInputId, ruleTemplateInput.ruleTemplate.id as templateFId,"
          + " ruleTemplateInput.prefix as templateInputPrefix, ruleTemplateInput.suffix as templateInputSuffix, "
          + " ruleTemplateInput.defaultValue as templateInputDefaultValue, ruleTemplateInput.maxValue as templateInputMaxValue,"
          + " ruleTemplateInput.minValue as templateInputMinValue, ruleTemplateInput.typeValue as templateInputTypeValue, "
          + " ruleTemplate.isHardRule as isHardRule, ruleTemplateInput.isMandatory as isMandatory, ruleListMaster.ruleOrder as ruleOrder, "
          + " ruleTemplate.numericPrecision as numericPrecision, ruleTemplate.numericScale as numericScale ) "
          + " FROM RuleMasterSection ruleMasterSection "
          + " INNER JOIN RuleListMaster ruleListMaster ON ruleMasterSection.id = ruleListMaster.ruleMasterSection "
          + " INNER JOIN RuleTemplate ruleTemplate ON ruleListMaster.id = ruleTemplate.ruleListMaster"
          + " INNER JOIN RuleType ruleType ON ruleTemplate.ruleType = ruleType.id "
          + " INNER JOIN RuleTemplateInput ruleTemplateInput ON ruleTemplate.id = ruleTemplateInput.ruleTemplate "
          + " WHERE ruleMasterSection.id = :sectionId ORDER BY ruleListMaster.ruleOrder, ruleTemplateInput.id ASC")
  public List<VesselRule> findRuleTemplateForNoVessel(@Param("sectionId") Long sectionId);

  @Query(
      "SELECT new com.cpdss.vesselinfo.domain.VesselRule(ruleListMaster.ruleListMaster as header, "
          + " ruleVesselMapping.id as id, ruleVesselMapping.ruleTemplate.id as templateId, ruleVesselMapping.isEnable as templateIsEnable, "
          + " ruleVesselMapping.displayInSettings as templateDisplayInSettings, ruleVesselMapping.ruleType.ruleType as templateRuleType,"
          + " ruleVesselInput.id as templateInputId, ruleVesselInput.ruleVesselMapping.id as templateFId,"
          + " ruleVesselInput.prefix as templateInputPrefix, ruleVesselInput.suffix as templateInputSuffix, "
          + " ruleVesselInput.defaultValue as templateInputDefaultValue, ruleVesselInput.maxValue as templateInputMaxValue,"
          + " ruleVesselInput.minValue as templateInputMinValue, ruleVesselInput.typeValue as templateInputTypeValue, "
          + " ruleVesselMapping.isHardRule as isHardRule, ruleVesselInput.isMandatory as isMandatory, ruleListMaster.ruleOrder as ruleOrder, "
          + " ruleVesselMapping.numericPrecision as numericPrecision, ruleVesselMapping.numericScale as numericScale ) "
          + " FROM Vessel vessel "
          + " INNER JOIN RuleVesselMapping ruleVesselMapping ON vessel.id = ruleVesselMapping.vessel "
          + " INNER JOIN RuleVesselMappingInput ruleVesselInput on ruleVesselMapping.id = ruleVesselInput.ruleVesselMapping "
          + " INNER JOIN RuleType ruleType ON ruleVesselMapping.ruleType = ruleType.id "
          + " INNER JOIN RuleTemplate ruleTemplate ON ruleVesselMapping.ruleTemplate =  ruleTemplate.id "
          + " INNER JOIN RuleListMaster ruleListMaster ON ruleTemplate.ruleListMaster = ruleListMaster.id "
          + " INNER JOIN RuleMasterSection ruleMasterSection ON ruleListMaster.ruleMasterSection = ruleMasterSection.id "
          + " WHERE vessel.id = :vesselId AND ruleMasterSection.id = :sectionId ORDER BY ruleListMaster.ruleOrder, ruleVesselInput.id ASC")
  public List<VesselRule> findVesselRuleByVesselIdAndSectionId(
      @Param("vesselId") Long vesselId, @Param("sectionId") Long sectionId);

  //  @Query(
  //      "SELECT new com.cpdss.vesselinfo.domain.VesselRuleMappingVessel(vessel.id as vesselId )"
  //          + " FROM Vessel vessel INNER JOIN RuleVesselMapping ruleVesselMapping ON vessel.id =
  // ruleVesselMapping.vessel "
  //          + " WHERE vessel.id = :vesselId AND vessel.isActive = :isVesselActive AND
  // ruleVesselMapping.isActive = :isRuleActive")
  //  public List<VesselRuleMappingVessel> findVesselInRuleVesselMapping(
  //      @Param("vesselId") Long vesselId,
  //      @Param("isVesselActive") boolean isVesselActive,
  //      @Param("isRuleActive") boolean isRuleActive);
}
