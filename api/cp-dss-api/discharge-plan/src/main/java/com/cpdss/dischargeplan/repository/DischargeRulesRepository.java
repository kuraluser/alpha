/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargePlanRules;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface DischargeRulesRepository extends CommonCrudRepository<DischargePlanRules, Long> {

  @Query(
      "FROM DischargePlanRules DPR WHERE DPR.dischargeInformation.id = ?1 AND DPR.parentRuleXId = ?3 AND DPR.isActive = ?2")
  Optional<DischargePlanRules> checkIsRuleTemplateExist(
      Long dischargeStudyId, Boolean isActive, Long ruleTemplateId);

  List<DischargePlanRules>
      findByDischargeInformationAndVesselXIdAndIsActiveAndVesselRuleXIdInOrderById(
          DischargeInformation dischargeInformation,
          long vesselId,
          boolean isActive,
          List<Long> ruleListId);
}
